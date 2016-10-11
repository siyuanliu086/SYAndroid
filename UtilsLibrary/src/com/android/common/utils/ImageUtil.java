package com.android.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author LuoJ
 * @date 2013-10-10
 * @package com.lidroid.xutils.util.custom -- ImageUtil.java
 * @Description 图片处理工具类
 */
public class ImageUtil {

	/**
	 * 将指定路径的图片压缩
	 * @param path
	 * @return
	 */
	public static Drawable fitSizeImg(String path) {
		File file = new File(path.toString());
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 1;
		// opts.inSampleSize = 5;
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 1;
		} else if (file.length() < 204800) {
			opts.inSampleSize = 1;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 2;
		} else if (file.length() < 509200) {
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		Drawable drawable = new BitmapDrawable(resizeBmp);
		return drawable;
	}

	/**
	 * bitmap to string
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmaptoString(Bitmap bitmap) {
		String string = null;
		if (null != bitmap) {
			// 将Bitmap转换成字符串
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 90, bStream);
			byte[] bytes = bStream.toByteArray();
			string = Base64.encodeToString(bytes, Base64.DEFAULT);
		}
		return string;
	}

	/**
	 * 简易截屏方法
	 * @param v 视图
	 * @param filePath 保存路径
	 */
	public static void getScreenHot(Context mctx,View v, String filePath) {
		try {
			Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas();
			canvas.setBitmap(bitmap);
			v.draw(canvas);

			try {
				FileOutputStream fos = new FileOutputStream(filePath);
				bitmap.compress(CompressFormat.PNG, 90, fos);
			} catch (FileNotFoundException e) {
				Log.e("errormsg", e.toString());
				ToastUtil.showShortToast(mctx, "找不到路径");
			}
		} catch (Exception e) {
			Log.e("errormsg", e.toString());
			ToastUtil.showShortToast(mctx, "截图失败");
		}
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

		String SavePath = getSDCardPath() + "/ScreenImage";

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
	 * 获取SDCard的目录
	 * @return
	 */
	private static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}
}
