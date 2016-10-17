package com.android.common.baseui.baselabelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @TiTle LabelTextView.java
 * @Package com.android.common.baseui.baselabelview
 * @Description
 * @Date 2016年10月17日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 * 
<com.lid.lib.LabelTextView
    android:id="@+id/text"
    android:layout_width="wrap_content"
    android:layout_height="48dp"
    android:layout_gravity="center"
    android:layout_marginTop="8dp"
    android:background="#212121"
    android:gravity="center"
    android:padding="16dp"
    android:text="TextView"
    android:textColor="#ffffff"
    app:label_backgroundColor="#03A9F4"
    app:label_distance="15dp"
    app:label_orientation="LEFT_TOP"
    app:label_text="POP"
    app:label_textSize="10sp" />
 */
public class LabelTextView extends TextView {

    LabelViewHelper utils;

    public LabelTextView(Context context) {
        this(context, null);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        utils = new LabelViewHelper(context, attrs, defStyleAttr);
    }

    @SuppressLint("WrongCall")
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        utils.onDraw(canvas, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setLabelHeight(int height) {
        utils.setLabelHeight(this, height);
    }

    public int getLabelHeight() {
        return utils.getLabelHeight();
    }

    public void setLabelDistance(int distance) {
        utils.setLabelDistance(this, distance);
    }

    public int getLabelDistance() {
        return utils.getLabelDistance();
    }

    public boolean isLabelEnable() {
        return utils.isLabelVisual();
    }

    public void setLabelEnable(boolean enable) {
        utils.setLabelVisual(this, enable);
    }

    public int getLabelOrientation() {
        return utils.getLabelOrientation();
    }

    public void setLabelOrientation(int orientation) {
        utils.setLabelOrientation(this, orientation);
    }

    public int getLabelTextColor() {
        return utils.getLabelTextColor();
    }

    public void setLabelTextColor(int textColor) {
        utils.setLabelTextColor(this, textColor);
    }

    public int getLabelBackgroundColor() {
        return utils.getLabelBackgroundColor();
    }

    public void setLabelBackgroundColor(int backgroundColor) {
        utils.setLabelBackgroundColor(this, backgroundColor);
    }

    public String getLabelText() {
        return utils.getLabelText();
    }

    public void setLabelText(String text) {
        utils.setLabelText(this, text);
    }

    public int getLabelTextSize() {
        return utils.getLabelTextSize();
    }

    public void setLabelTextSize(int textSize) {
        utils.setLabelTextSize(this, textSize);
    }
}


