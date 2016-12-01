package com.android.common.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @TiTle SoftKeyBoardListener.java
 * @Package com.siyuan.utilslibrarydemos
 * @Description 监听键盘显示和隐藏（默认、adjustResize，adjustPan，adjustUnspecified 模式可用）
 * @Date 2016年12月1日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 * 
		// 监听键盘现实和隐藏
		SoftKeyBoardListener.setListener(MainActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener(){

			@Override
			public void keyBoardShow(int height) {
				System.out.println("SoftKeyBoardListener keyBoardShow " + height);
			}

			@Override
			public void keyBoardHide(int height) {
				System.out.println("SoftKeyBoardListener keyBoardHide " + height);
			}});
	} 
 */
public class SoftKeyBoardListener {
    private View rootView;//activity的根视图
    private int rootViewVisibleHeight;//纪录根视图的显示高度
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public SoftKeyBoardListener(Activity activity) {
        //获取activity的根视图
        rootView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过400，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 400) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过400，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 400) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

    public static void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(activity);
        softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }
}

