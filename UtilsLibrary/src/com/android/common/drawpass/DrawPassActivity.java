package com.android.common.drawpass;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.common.drawpass.LocusPassWordView.OnCompleteListener;
import com.android.common.utils.MD5;
import com.android.common.utils.PreferencesUtils;
import com.common.android.utilslibrary.R;

/**
 * @TiTle DrawPassActivity.java
 * @Package com.android.common.drawpass
 * @Description 手势密码（设置示例）
 * @Date 2016年4月28日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class DrawPassActivity extends Activity {
	private TextView mTextView;
	private LocusPassWordView mPwdView;
	private Context mContext = this;
	private boolean setFlag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.utilslib_layout_draw_pwd);
		String savePass = PreferencesUtils.getSharePreferencesString(mContext, "password");
		
		mTextView = (TextView) findViewById(R.id.multi_tv_token_time_hint);
		mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
		mPwdView.setOnCompleteListener(new OnCompleteListener() {
			@Override
			public void onComplete(String mPassword) {
				if (TextUtils.isEmpty(mPassword) || mPassword.length() < LocusPassWordView.PWD_MIN_LEN 
						|| mPassword.length() > LocusPassWordView.PWD_MAX_LEN) {
					mTextView.setText(R.string.utilslib_gesturepass_pass2short);
					mPwdView.markError();
					mPwdView.delayReset();
					return;
				}
				
				String savePass = PreferencesUtils.getSharePreferencesString(mContext, "password");
				String md5Pass = MD5.GetMD5Code(mPassword);
				
				// 第一次设置密码
				if(setFlag) {
					if(savePass.isEmpty()) {
						PreferencesUtils.putSharePreferences(mContext, "password", md5Pass);
						mTextView.setText(R.string.utilslib_gesturepass_drawagain);
						mPwdView.delayReset();
					} else if(savePass.equals(md5Pass)){
						// 设置成功
						mTextView.setText(R.string.utilslib_gesturepass_drawasuccess);
						// 延时跳转
						mPwdView.delayReset();
					} else {
						mTextView.setText(R.string.utilslib_gesturepass_check_faile);
						mPwdView.delayReset();
					}
					return;
				}
				
				if(savePass.equals(md5Pass)) {
					// 延时跳转
					mTextView.setText(R.string.utilslib_gesturepass_draw_succ);
					mPwdView.delayReset();
				} else {
					mTextView.setText(R.string.utilslib_gesturepass_draw_failed);
					mPwdView.delayReset();
				}
			}
		});
		
		
		if(savePass.isEmpty()) {
			setFlag = true;
			mTextView.setText(R.string.utilslib_gesturepass_please_setting);
		} else {
			setFlag = false;
			mTextView.setText(R.string.utilslib_gesturepass_please_input);
		}
	}

}
