package com.android.common.baseui.popupwindow;

import java.io.File;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.android.utilslibrary.R;

/**
 * @TiTle PickMediaPopupWindow.java
 * @Package com.iss.zhhblsnt.views
 * @Description 媒体选择（拍照、录音）
 * @Date 2016年4月26日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class PickMediaPopu extends PopupWindow implements OnClickListener {
	/**拍摄视频 ResultCode*/
	public static final int POPUP_RECORD_VIDEOS = 0x11;
	/**拍照 ResultCode*/
	public static final int POPUP_TAKE_PHOTOS = 0x12;
	/**录音 ResultCode*/
	public static final int POPUP_RECORD_AUDIO = 0x13;
	/**从相册选择 ResultCode*/
	public static final int POPUP_GALLERY_PHOTO = 0x14;

	private Activity activity;
	private Intent photoIntent, videoIntent;
	private String filePath;
	private RelativeLayout outLayout;
	private TextView takePhoto, addAudio, video, openPhoto, cancel;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMddhhmmss");

	/**
	 * @param activity 发起的activity
	 * @param parent 父布局View，锚点
	 * @param filePath 拍照图片路径（如果要拍照）
	 */
	public PickMediaPopu(Activity activity, View parent, String filePath) {
		super(activity);
		this.filePath = filePath;
		this.activity = activity;
		View view = View.inflate(activity, R.layout.utils_layout_popup_pickmedia, null);
		
		takePhoto = (TextView) view.findViewById(R.id.utils_popup_media_takephoto);
		addAudio = (TextView) view.findViewById(R.id.utils_popup_media_record);
		video = (TextView) view.findViewById(R.id.utils_popup_media_video);
		openPhoto = (TextView) view.findViewById(R.id.utils_popup_media_gallery);
		cancel = (TextView) view.findViewById(R.id.utils_popup_media_cancel);
		outLayout = (RelativeLayout)view.findViewById(R.id.utils_popup_media_layout);
		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.utils_popuwindowstyle);
		// 实例化一个ColorDrawable颜色为半透明 
	    ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.utils_transparent)); 
	    // 设置弹出窗体的背景 
	    setBackgroundDrawable(dw); 
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();
		setListener();
	}

	private void setListener() {
		takePhoto.setOnClickListener(this);
		addAudio.setOnClickListener(this);
		video.setOnClickListener(this);
		openPhoto.setOnClickListener(this);
		cancel.setOnClickListener(this);
		outLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.utils_popup_media_gallery) {
			doPickPhotoFromGallery();
		} else if(v.getId() == R.id.utils_popup_media_takephoto) {
			takePhoto();
		} else if(v.getId() == R.id.utils_popup_media_record) {
			recordVideo();
		} else if(v.getId() == R.id.utils_popup_media_video) {
			recordVideo();
		} else {
			dismiss();
		}
	}

	/**
	 * 拍照
	 */
	private void takePhoto() {// 拍照
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 预防有些手机返回uri等于null
			
			File file = new File(filePath);
			if (!file.exists())// 第一次拍照创建照片文件夹
				file.mkdirs();
			
			photoIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			activity.startActivityForResult(photoIntent, POPUP_TAKE_PHOTOS);
		} else {
			Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * Description:录像
	 */
	private void recordVideo() {// 录像
		videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		activity.startActivityForResult(videoIntent, POPUP_RECORD_VIDEOS);
	}

	/**
	 * 从相册选择照片
	 */
	protected void doPickPhotoFromGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		activity.startActivityForResult(intent, POPUP_GALLERY_PHOTO);
	}

}
