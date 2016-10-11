package com.google.zxing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * POD used in the AsyncTask which saves an image in the background.
 */
public class SaveImageInBackgroundData {
	public Context context;
	public Bitmap image;
	public Uri imageUri;
	public int iconSize;
	public int result;
}
