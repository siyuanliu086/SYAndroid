package com.google.zxing.util;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

/**
 * An AsyncTask that saves an image to the media store in the background.
 */
@SuppressLint("NewApi")
public class SaveImageInBackgroundTask extends
		AsyncTask<SaveImageInBackgroundData, Void, SaveImageInBackgroundData> {
	public static final int SCREENSHOT_NOTIFICATION_ID = 789;
	private static final String SCREENSHOTS_DIR_NAME = "QRCode";
	private static final String SCREENSHOT_FILE_NAME_TEMPLATE = "QRCode_%s.png";
	private static final String SCREENSHOT_FILE_PATH_TEMPLATE = "%s/%s";

	private int mNotificationId;
	private NotificationManager mNotificationManager;
	private Notification.Builder mNotificationBuilder;
	private String mImageFileName;
	private String mImageFilePath;
	private long mImageTime;

	// WORKAROUND: We want the same notification across screenshots that we
	// update so that we don't
	// spam a user's notification drawer. However, we only show the ticker for
	// the saving state
	// and if the ticker text is the same as the previous notification, then it
	// will not show. So
	// for now, we just add and remove a space from the ticker text to trigger
	// the animation when
	// necessary.
	private static boolean mTickerAddSpace;

	public SaveImageInBackgroundTask(Context context,
			SaveImageInBackgroundData data) {
		Resources r = context.getResources();

		// Prepare all the output metadata
		mImageTime = System.currentTimeMillis();
		String imageDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(new Date(mImageTime));
		File imageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				File.separator + SCREENSHOTS_DIR_NAME + File.separator);
		if (!imageDir.exists())
			imageDir.mkdir();
		mImageFileName = String
				.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);
		mImageFilePath = String.format(SCREENSHOT_FILE_PATH_TEMPLATE, imageDir,
				mImageFileName);
		Log.i("way", "mImageFilePath = " + mImageFilePath);
		// Create the large notification icon
		int imageWidth = data.image.getWidth();
		int imageHeight = data.image.getHeight();
		int iconWidth = data.iconSize;
		int iconHeight = data.iconSize;
		if (imageWidth > imageHeight) {
			iconWidth = (int) (((float) iconHeight / imageHeight) * imageWidth);
		} else {
			iconHeight = (int) (((float) iconWidth / imageWidth) * imageHeight);
		}
		Bitmap rawIcon = Bitmap.createScaledBitmap(data.image, iconWidth,
				iconHeight, true);
		Bitmap croppedIcon = Bitmap.createBitmap(rawIcon,
				(iconWidth - data.iconSize) / 2,
				(iconHeight - data.iconSize) / 2, data.iconSize, data.iconSize);

		// Show the intermediate notification
		mTickerAddSpace = !mTickerAddSpace;
//		mNotificationId = SCREENSHOT_NOTIFICATION_ID;
//		mNotificationManager = (NotificationManager) context
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//		;
//		mNotificationBuilder = new Notification.Builder(context)
//				.setTicker(
//						r.getString(R.string.screenshot_saving_ticker)
//								+ (mTickerAddSpace ? " " : ""))
//				.setContentTitle(r.getString(R.string.screenshot_saving_title))
//				.setContentText(r.getString(R.string.screenshot_saving_text))
//				.setSmallIcon(R.drawable.stat_notify_image)
//				.setWhen(System.currentTimeMillis());
//		Notification n = mNotificationBuilder.getNotification();
//		n.flags |= Notification.FLAG_NO_CLEAR;
//		mNotificationManager.notify(SCREENSHOT_NOTIFICATION_ID, n);

		// On the tablet, the large icon makes the notification appear as if it
		// is clickable (and
		// on small devices, the large icon is not shown) so defer showing the
		// large icon until
		// we compose the final post-save notification below.
//		mNotificationBuilder.setLargeIcon(croppedIcon);
	}

	@Override
	protected SaveImageInBackgroundData doInBackground(
			SaveImageInBackgroundData... params) {
		if (params.length != 1)
			return null;

		// By default, AsyncTask sets the worker thread to have background
		// thread priority, so bump
		// it back up so that we save a little quicker.
		// Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);

		Context context = params[0].context;
		Bitmap image = params[0].image;

		try {
			// Save the screenshot to the MediaStore
			ContentValues values = new ContentValues();
			ContentResolver resolver = context.getContentResolver();
			values.put(MediaStore.Images.ImageColumns.DATA, mImageFilePath);
			values.put(MediaStore.Images.ImageColumns.TITLE, mImageFileName);
			values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME,
					mImageFileName);
			values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, mImageTime);
			values.put(MediaStore.Images.ImageColumns.DATE_ADDED, mImageTime);
			values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, mImageTime);
			values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
			Uri uri = resolver.insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

			OutputStream out = resolver.openOutputStream(uri);
			image.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();

			// update file size in the database
			values.clear();
			values.put(MediaStore.Images.ImageColumns.SIZE, new File(
					mImageFilePath).length());
			resolver.update(uri, values, null, null);

			params[0].imageUri = uri;
			params[0].result = 0;
		} catch (Exception e) {
			// IOException/UnsupportedOperationException may be thrown if
			// external storage is not
			// mounted
			Log.i("way", e.getMessage());
			params[0].result = 1;
		}

		return params[0];
	}

	@Override
	protected void onPostExecute(SaveImageInBackgroundData params) {
		if (params.result > 0) {
			// Show a message that we've failed to save the image to disk
//			notifyScreenshotError(params.context, mNotificationManager);
		} else {
			// Show the final notification to indicate screenshot saved
			Resources r = params.context.getResources();

			// Create the intent to show the screenshot in gallery
			Intent launchIntent = new Intent(Intent.ACTION_VIEW);
			launchIntent.setDataAndType(params.imageUri, "image/png");
			launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//			mNotificationBuilder
//					.setContentTitle(
//							r.getString(R.string.screenshot_saved_title))
//					.setContentText(r.getString(R.string.screenshot_saved_text))
//					.setContentIntent(
//							PendingIntent.getActivity(params.context, 0,
//									launchIntent, 0))
//					.setWhen(System.currentTimeMillis()).setAutoCancel(true);
//
//			Notification n = mNotificationBuilder.getNotification();
//			n.flags &= ~Notification.FLAG_NO_CLEAR;
//			mNotificationManager.notify(mNotificationId, n);
		}
	}

//	static void notifyScreenshotError(Context context,
//			NotificationManager nManager) {
//		Resources r = context.getResources();
//
//		// Clear all existing notification, compose the new notification and
//		// show it
//		Notification n = new Notification.Builder(context)
//				.setTicker(r.getString(R.string.screenshot_failed_title))
//				.setContentTitle(r.getString(R.string.screenshot_failed_title))
//				.setContentText(r.getString(R.string.screenshot_failed_text))
//				.setSmallIcon(R.drawable.stat_notify_image_error)
//				.setWhen(System.currentTimeMillis()).setAutoCancel(true)
//				.getNotification();
//		nManager.notify(SCREENSHOT_NOTIFICATION_ID, n);
//	}
}
