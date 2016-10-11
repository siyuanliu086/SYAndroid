package com.android.common.baseui.audiorecodertoolkit;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import com.common.android.utilslibrary.R;

public class ErrorCode {
	public final static int SUCCESS = 1000;
	public final static int E_NOSDCARD = 1001;
	public final static int E_STATE_RECODING = 1002;
	public final static int E_UNKOWN = 1003;

	public static String getErrorInfo(Context vContext, int vType)
			throws NotFoundException {
		switch (vType) {
		case SUCCESS:
			return "success";
		case E_NOSDCARD:
			return vContext.getResources().getString(R.string.utilslib_common_error_nosdcard);
		case E_STATE_RECODING:
			return vContext.getResources().getString(R.string.utilslib_audiorecoder_state_record_error);
		case E_UNKOWN:
		default:
			return vContext.getResources().getString(R.string.utilslib_audiorecoder_unknown_type);

		}
	}
}
