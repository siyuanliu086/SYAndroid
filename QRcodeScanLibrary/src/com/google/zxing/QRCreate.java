package com.google.zxing;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.google.zxing.common.BitMatrix;

/**
 * @TiTle QRCreate.java
 * @Package com.google.zxing
 * @Description 二维码生成工具
 * @Date 2016年6月22日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class QRCreate {
		public static final int IMAGE_HALFWIDTH = 20;
		// ---二维码的颜色
		public static final int COLOR_BLUE = 0xFF3366CC;
		public static final int COLOR_RED = 0xFFFD3C3C;
		public static final int COLOR_PURPLE = 0xFF68228B;
		public static final int COLOR_BLACK = 0xEE000000;
		public static final int[] COLORS = { COLOR_BLUE, COLOR_RED,
				COLOR_PURPLE, COLOR_BLACK };

		public static final int STYLE_SIMPLE = 0;
		public static final int STYLE_COLORS = 1;
		public static final int STYLE_FLOWER = 2;

		private boolean mIsShowLogo = false;
		// 二维码颜色
		private int mQRColor;
		private int mQRStyle;
		// private int mClickCount;
		private BitMatrix mQRBitMatrix;
		// 保存生成的二维码
		private Bitmap mQRBitmap = null;

		// 插入到二维码里面的图片对象
		private Bitmap mLogoInsideBitmap;

		public ImageView mQRView;
		
		Bitmap logoBitmap = null;
		
		/**
		 * 直接生成二维码
		 * @param mContext
		 * @param content
		 * @return
		 */
		public Bitmap createQRCode(Context mContext, String content) {
			mQRStyle = STYLE_SIMPLE;
			mQRColor = COLOR_BLACK;
			return initData(content);
		}
		
		/**
		 * 设置中间图标，生成二维码
		 * @param mContext
		 * @param content 
		 * @param centerDrawable 中间图片，不需要可设置0
		 * @return
		 */
		public Bitmap createQRCode(Context mContext, String content, int centerDrawable) {
			mIsShowLogo = true;
			if(centerDrawable <= 0) {
				mIsShowLogo = false;
			} else {		
				mIsShowLogo = true;
				logoBitmap = BitmapFactory.decodeResource(mContext.getResources(), centerDrawable);
			}
			mQRStyle = STYLE_SIMPLE;
			mQRColor = COLOR_BLACK;
			return initData(content);
		}
		
		/**
		 * 设置中间图标，设置颜色，生成二维码
		 * @param mContext
		 * @param content
		 * @param centerDrawable 中间图片，不需要可设置0
		 * @param color 二维码颜色
		 * @return
		 */
		public Bitmap createQRCode(Context mContext, String content, int centerDrawable, int color) {
			if(centerDrawable <= 0) {
				mIsShowLogo = false;
			} else {		
				mIsShowLogo = true;
				logoBitmap = BitmapFactory.decodeResource(mContext.getResources(), centerDrawable);
			}
			mQRStyle = STYLE_SIMPLE;
			mQRColor = color;
			return initData(content);
		}
		
		private Bitmap initData(String content) {
			if(mIsShowLogo) {
				// 缩放图片
				Matrix m = new Matrix();
				float sx = (float) 2 * IMAGE_HALFWIDTH / logoBitmap.getWidth();
				float sy = (float) 2 * IMAGE_HALFWIDTH / logoBitmap.getHeight();
				
				m.setScale(sx, sy);
				// 重新构造一个40*40的图片
				mLogoInsideBitmap = Bitmap.createBitmap(logoBitmap, 0, 0,
						logoBitmap.getWidth(), logoBitmap.getHeight(), m, false);
			}

			return createQRcode(content, mQRStyle);
		}
		
		public Bitmap createQRcode(String content, int style) {
			try {
				content = new String(content.getBytes(), "ISO-8859-1");
				mQRBitMatrix = createQRBitMatrix(content);
				mQRBitmap = createQRBitmap(mQRBitMatrix, style);
			} catch (WriterException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return mQRBitmap;
		}

		private BitMatrix createQRBitMatrix(String content)
				throws WriterException {
			// SecretKey key = mApp.getSecretKey();
			// if (key == null) {
			// T.showShort(this, "密钥不存在，无法生成二维码");
			// return null;
			// }
			// content = DesEncrypt.getEncString(content, key);// 对二维码信息加密

			// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
			// 返回BitMatrix对象，其实就是一个布尔型的数组
			// encode()方法传入的参数：1、编码的字符串 2、编码的类型(二维码，条形码...) 3、返回数组的大小(类似于分辨率)
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, 300, 300);
			return bitMatrix;
		}

		/**
		 * 生成二维码图片，包括生成一定花式效果
		 * 
		 * @param bitMatrix
		 * @param style
		 * @return
		 */
		private Bitmap createQRBitmap(BitMatrix bitMatrix, int style) {
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			// 二维矩阵转为一维像素数组,也就是一直横着排了
			int halfW = width / 2;
			int halfH = height / 2;
			int[] pixels = new int[width * height];

			int a = -150;
			int b = 150;
			int c = 150;
			int d = 450;
			Random random = new Random();
			int leftTop = COLORS[random.nextInt(4)];
			int leftBottom = COLORS[random.nextInt(4)];
			int rightTop = COLORS[random.nextInt(4)];
			int rightBottom = COLORS[random.nextInt(4)];
			int center = COLORS[random.nextInt(4)];

			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {
					// 将图片绘制到指定区域中
					// 就是将图片像素的颜色值写入到相应下标的数组中
					if (w > halfW - IMAGE_HALFWIDTH
							&& w < halfW + IMAGE_HALFWIDTH
							&& h > halfH - IMAGE_HALFWIDTH
							&& h < halfH + IMAGE_HALFWIDTH && mIsShowLogo) {
						pixels[h * width + w] = mLogoInsideBitmap.getPixel(w
								- halfW + IMAGE_HALFWIDTH, h - halfH
								+ IMAGE_HALFWIDTH);
					} else {
						// 判断当前位置在二维矩阵中存储的boolean值
						if (bitMatrix.get(w, h)) {
							if (style == STYLE_COLORS) {
								// ---左下角
								if (h >= 150 && w <= a) {
									mQRColor = leftBottom;
									// ---右上角
								} else if (h <= 150 && w >= b) {
									mQRColor = rightTop;
									// ---左上角
								} else if (h <= 150 && w <= c) {
									mQRColor = leftTop;
									// ---右下角
								} else if (h >= 150 && w >= d) {
									mQRColor = rightBottom;
								} else {
									mQRColor = center;
								}
							} else if (style == STYLE_FLOWER) {
								mQRColor = COLORS[random.nextInt(4)];
							}
							pixels[h * width + w] = mQRColor;
						} else {
							pixels[h * width + w] = 0xffffffff;
						}
					}
				}
				a++;
				b++;
				c--;
				d--;
			}

			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			// 通过像素数组生成bitmap
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		}

//		public void save2File(Bitmap qrBmp) {
//			SaveImageInBackgroundData data = new SaveImageInBackgroundData();
//			data.context = this;
//			data.image = qrBmp;
//			data.iconSize = this.getResources().getDimensionPixelSize(
//					android.R.dimen.notification_large_icon_height);
//			new SaveImageInBackgroundTask(this, data).execute(data);
			// long fileNamePath = new Date().getTime();
			// // 创建目录
			// File fileDir = new
			// File(Environment.getExternalStorageDirectory(),
			// File.separator + "QRCode" + File.separator);
			// if (!fileDir.exists()) {
			// fileDir.mkdirs();
			// }
			// // 创建文件
			// File jpgFile = new File(fileDir.getAbsolutePath() +
			// File.separator
			// + fileNamePath + ".JPG");
			// if (!jpgFile.exists()) {
			// jpgFile.createNewFile();
			// }
			// BufferedOutputStream bosPNG = new BufferedOutputStream(
			// new FileOutputStream(jpgFile));
			// qrBmp.compress(Bitmap.CompressFormat.JPEG, 80, bosPNG);
			// bosPNG.flush();
			// bosPNG.close();
			// return fileDir.getPath() + fileNamePath;
//		}

		// public void onClick(View v) {
		// switch (v.getId()) {
		// case R.id.simple_style_btn:
		// mQRStyle = STYLE_SIMPLE;
		// mClickCount++;
		// mQRColor = COLORS[mClickCount % 4];
		// mSimpleStyleBtn.setBackgroundColor(mQRColor);
		// break;
		// case R.id.color_style_btn:
		// mQRStyle = STYLE_COLORS;
		// break;
		// case R.id.flower_style_btn:
		// mQRStyle = STYLE_FLOWER;
		// break;
		// case R.id.show_logo_btn:
		// mIsShowLogo = !mIsShowLogo;
		// if (mIsShowLogo)
		// mShowLogoBtn.setText("隐藏Logo");
		// else
		// mShowLogoBtn.setText("显示Logo");
		// break;
		// default:
		// break;
		// }
		// mQRBitmap = createQRBitmap(mQRBitMatrix, mQRStyle);
		// mQRView.setImageBitmap(mQRBitmap);
		// }
}
