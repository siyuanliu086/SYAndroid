package com.android.common.baseui.alertview;

import android.view.Gravity;

import com.common.android.utilslibrary.R;

/**
 * Created by Sai on 15/8/9.
 */
public class AlertAnimateUtil {
    private static final int INVALID = -1;
    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the gravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.utilslib_slide_in_bottom : R.anim.utilslib_slide_out_bottom;
            case Gravity.CENTER:
                return isInAnimation ? R.anim.utilslib_fade_in_center : R.anim.utilslib_fade_out_center;
        }
        return INVALID;
    }
}
