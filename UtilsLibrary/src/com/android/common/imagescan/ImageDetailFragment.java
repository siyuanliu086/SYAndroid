package com.android.common.imagescan;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.common.imagescan.PhotoViewAttacher.OnPhotoTapListener;
import com.android.common.utils.toast.ToastUtil;
import com.common.android.utilslibrary.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
/**
 * 多图浏览-查看其中一张图
 * @author ddwangs
 *
 */
public class ImageDetailFragment extends Fragment {
	/**图片路径*/
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	private BitmapUtils bitmapUtils;  
	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
		bitmapUtils = new BitmapUtils(getActivity());  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.utilslib_fragment_image_detail, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);
		
		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
			
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});
		
		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		bitmapUtils.display(mImageView, mImageUrl,  new BitmapLoadCallBack<View>() {
            @Override
            public void onLoadStarted(View container, String uri, BitmapDisplayConfig config) {
            	progressBar.setVisibility(View.VISIBLE);
            }
			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3,
					BitmapLoadFrom arg4) {
				mImageView.setImageBitmap(arg2);
				mAttacher.update();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				progressBar.setVisibility(View.GONE);
				ToastUtil.showShortToast(getActivity(), R.string.utilslib_common_image_loadfaild);
			}
		});	
		
		
	}

}
