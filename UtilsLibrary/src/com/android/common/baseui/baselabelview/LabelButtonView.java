package com.android.common.baseui.baselabelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @TiTle LabelButtonView.java
 * @Package com.android.common.baseui.baselabelview
 * @Description 加标签的Button
 * @Date 2016年10月17日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 * 
 <com.android.common.baseui.baselabelview.LabelButtonView
    android:id="@+id/labelbutton"
    android:layout_width="200dp"
    android:layout_height="48dp"
    android:background="#03a9f4"
    android:gravity="center"
    android:text="Button"
    android:textColor="#ffffff"
    app:label_backgroundColor="#C2185B"
    app:label_distance="20dp"
    app:label_height="20dp"
    app:label_orientation="RIGHT_TOP"
    app:label_text="HD"
    app:label_textSize="12sp" />
 */
public class LabelButtonView extends Button {

    LabelViewHelper utils;

    public LabelButtonView(Context context) {
        this(context, null);
    }

    public LabelButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public boolean isLabelVisual() {
        return utils.isLabelVisual();
    }

    public void setLabelVisual(boolean enable) {
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


