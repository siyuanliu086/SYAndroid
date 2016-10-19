package com.android.common.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * @TiTle BitmapUtil.java
 * @Package com.android.common.utils
 * @Description 图片处理工具类
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-19
 * @Company ISoftStone ZHHB
 */
public class BitmapUtil {
	public static BlockingQueue queue; // 缓冲队列
	private static ThreadPoolExecutor executor;
	private static int ThreadCount = 5; // 线程个数
	private static int coreThreadCount = 2; // 核心线程
	private static HashMap<String, String> urls = new HashMap<String, String>();

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 * @param f
	 * @return
	 */
	public static Bitmap zoom(Bitmap bitmap, float zf) {
		Matrix matrix = new Matrix();
		matrix.postScale(zf, zf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 * @param f
	 * @return
	 */
	public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * 缩放图片
	 * 
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);

		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	/**
	 * 图片圆角处理
	 * 
	 * @param bitmap
	 * @param roundPX
	 * @return
	 */
	public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
		// RCB means
		// Rounded
		// Corner Bitmap
		Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(dstbmp);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return dstbmp;
	}

	static {
		queue = new LinkedBlockingQueue();
		executor = new ThreadPoolExecutor(coreThreadCount, ThreadCount, 180,
				TimeUnit.SECONDS, queue);
	}

	public static void asynLoadBitmap(final String url, int itemPositon,
			Handler handler) {
		if (urls.get(url) != null)
			return;
		final Handler h = handler;
		final Message message = h.obtainMessage();
		message.arg1 = itemPositon;
		executor.execute(new Runnable() {
			public void run() {
				urls.put(url, url);
				// 从网络下载图片
				Bitmap bitmap = BitmapUtil.getBitmapFromNetWork(url);
				if (bitmap != null) {
					message.obj = bitmap;
					h.sendMessage(message);
				}
				urls.remove(url);
			}
		});
	}

	/**
	 * drawable转向bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * bitmap转向drawable
	 * 
	 * @param drawable
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	/**
	 * bitmap转16进制字符串
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmaptoString(Bitmap bitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		// bitmap.compress(CompressFormat.PNG, 100, bStream);
		bitmap.compress(CompressFormat.JPEG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = byte2HexString(bytes);
		// string = Base64.encodeToString(bytes, Base64.DEFAULT);
		Log.i("chenliang", string);
		return string;
	}

	public static Bitmap base64StrToBitmap(String base64Str) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(base64Str, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
			// bitmap = BitmapFactory.decodeFile(string);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 截屏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap getScreenshotForCurrentWindow(Activity activity) {
		View cv = activity.getWindow().getDecorView();
		Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(), Bitmap.Config.ARGB_4444);
		cv.draw(new Canvas(bmp));
		return bmp;
	}
	
	/**
	 * 获取和保存当前屏幕的截图
	 */
	public static void SaveCurrentImage(Context mctx) {
		Activity mActivity=(Activity)mctx;
		// 创建Bitmap
		WindowManager windowManager = mActivity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();

		Bitmap bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		// 获取屏幕
		View decorview = mActivity.getWindow().getDecorView();
		decorview.setDrawingCacheEnabled(true);
		bmp = decorview.getDrawingCache();

		String SavePath = AppUtils.getSDPath() + "/ScreenImage";

		// 存储为Bitmap
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
					Locale.CHINA);

			File path = new File(SavePath);
			// 文件
			String filepath = SavePath + "/" + sdf.format(new Date()) + ".png";
			File file = new File(filepath);
			if (!path.exists()) {
				path.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
				LogUtils.d("截屏文件已保存至SDCard/ScreenImage/下");
				ToastUtil.showShortToast(mctx, "截屏文件已保存至SDCard/ScreenImage/下");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置透明度
	 * 
	 * @param sourceImg
	 * @param number
	 * @return
	 */
	public static Bitmap setAlpha(Bitmap sourceImg, int number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0,
				sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
		number = number * 255 / 100;
		for (int i = 0; i < argb.length; i++) {
			argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);// 修改最高2位的值
		}
		sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(),
				sourceImg.getHeight(), Config.ARGB_8888);

		return sourceImg;
	}

	/**
	 * 图片缩放
	 * 
	 * @param bgimage
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {
		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height, matrix, true);
		return bitmap;

	}

	/**
	 * 旋转图片
	 * 
	 * @param b
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 创建圆角图片
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 从网络下载图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromNetWork(String path) {
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url = new URL(path);
			Log.i("path", "path:" + path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(10000);
			conn.connect();
			is = conn.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 0;
			bitmap = BitmapFactory.decodeStream(is, null, options);
			conn.disconnect();
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 保存图片到sdcard
	 * 
	 * @param path
	 * @param bp
	 * @return
	 */
	public static boolean saveBitmapToSdcard(String path, Bitmap bp) {
		boolean saveStatus = true;
		File file = new File(path);
		if (!file.isDirectory())
			file.mkdirs();
		File cacheFile = new File(path);
		if (!cacheFile.isDirectory())
			cacheFile.mkdir();
		File bitmapPath = new File(path);
		try {
			if (bitmapPath.createNewFile()) {
				FileOutputStream fOut = null;
				fOut = new FileOutputStream(bitmapPath);
				if (fOut != null) {
					bp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
					fOut.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			saveStatus = false;
		}
		return saveStatus;
	}

	/**
	 * 从sdcard获得图片
	 * 
	 * @param path
	 * @param SampleSize
	 * @return
	 */
	public static Bitmap getBitmapfromSdcardDepressed(String filePath, int maxNUmberOfPixels){  
	     Bitmap bitmap = null;  
	     BitmapFactory.Options opts = new BitmapFactory.Options();  
	     opts.inJustDecodeBounds = true;  
	     BitmapFactory.decodeFile(filePath, opts);  
	  
	     opts.inSampleSize = computeSampleSize(opts, -1, maxNUmberOfPixels);  
	     opts.inJustDecodeBounds = false;    
	     try {  
	         bitmap = BitmapFactory.decodeFile(filePath, opts);  
	     }catch (Exception e) {  
	        e.printStackTrace();
	    }  
	    return bitmap;  
	}  
	  
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {  
	    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);  
	    int roundedSize;  
	    if (initialSize <= 8) {  
	        roundedSize = 1;  
	        while (roundedSize < initialSize) {  
	            roundedSize <<= 1;  
	        }  
	    } else {  
	        roundedSize = (initialSize + 7) / 8 * 8;  
	    }  
	    return roundedSize;  
	}  
	  
	private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {  
	    double w = options.outWidth;  
	    double h = options.outHeight;  
	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));  
	    int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));  
	    if (upperBound < lowerBound) {  
	        // return the larger one when there is no overlapping zone.  
	        return lowerBound;  
	    }  
	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {  
	        return 1;  
	    } else if (minSideLength == -1) {  
	        return lowerBound;  
	    } else {  
	        return upperBound;  
	    }  
	} 

	/**
	 * 从sdcard获得图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapfromSdcard(String path) {
		Bitmap bp = null;
		try {
			File file = new File(path);
			if (!file.exists())
				return null;
			FileInputStream fileInputStream = new FileInputStream(file);
			bp = BitmapFactory.decodeStream(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bp;
	}

	/**
	 * 创建倒影
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createDaoying(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		final int reflectionGap = 0;
		Bitmap originalImage = bitmap;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 3), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(originalImage, 0, 0, null);
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 3, width, height / 3, matrix, false);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		BitmapDrawable bd = new BitmapDrawable(bitmapWithReflection);

		return bd.getBitmap();
	}

	/**
	 * 获得图片的缩略图
	 * 
	 * @param bitmap
	 * @param widht
	 *            ：生成目标的宽度
	 * @param height
	 *            ：生成目标的高度
	 * @return
	 */
	public static Bitmap getThumbnail(Bitmap bitmap, int widht, int height) {
		if (bitmap == null)
			return null;
		return ThumbnailUtils.extractThumbnail(bitmap, widht, height);
	}

	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap getGrayBitmap(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	public static BitmapDrawable getRepeatBitmap(Context context, int resId) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		drawable.setDither(true);
		return drawable;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2HexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String stmp = Integer.toHexString(b[i] & 0xff);
			if (stmp.length() == 1)
				sb.append("0" + stmp);
			else
				sb.append(stmp);
		}
		return sb.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static Bitmap getBitmapFromUri(Uri uri, Context context) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(
					context.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}

	public static boolean saveImage(Bitmap photo, String spath, boolean isCompression) {
		try {
			File imageFile = new File(spath);
			File imageDirectory = new File(imageFile.getParent());
			if (!imageDirectory.exists()) {
				imageDirectory.mkdirs();
			}
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}

			BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(spath, false));
			photo.compress(Bitmap.CompressFormat.JPEG, isCompression ? 50 : 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 圆角图片，做头像用
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {// 参数为图片
		// 和其需要变化的角度
		// 创建一个带有特定宽度、高度和颜色格式的位图。
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

    /*
     * 旋转图片 
     * @param angle 
     * @param bitmap 
     * @return Bitmap 
     */ 
    public static Bitmap rotaingImage(int angle, Bitmap bitmap) {  
        //旋转图片 动作   
        Matrix matrix = new Matrix();;  
        matrix.postRotate(angle);  
        // 创建新的图片   
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
        return resizedBitmap;  
    }
    
    /**
     * 读取图片的旋转角度，拍照之后直接读取，若经过其他操作，可能读不到了
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
    
    // 从sd卡上加载缩略图片
    public static Bitmap decodeSampledBitmapFromSd(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }
    
    private static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    
    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }
}
