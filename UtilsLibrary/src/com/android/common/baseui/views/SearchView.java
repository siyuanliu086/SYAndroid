package com.android.common.baseui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.common.android.utilslibrary.R;

/**
 * @TiTle ClearEditText.java
 * @Package com.android.common.baseui.views
 * @Description 点击输入框右侧清空图标，可清除输入框内所有内容
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor FIX siyuan 2016-10-31
 * @Company ISoftStone ZHHB
 */
public class SearchView extends EditText implements OnFocusChangeListener, TextWatcher { 
	/**
	 * 删除按钮的引用
	 */
    private Drawable mClearDrawable; 
    private String lastChar = "";
 
    public SearchView(Context context) { 
    	this(context, null); 
    } 
 
    public SearchView(Context context, AttributeSet attrs) { 
    	//这里构造方法也很重要，不加这个很多属性不能再XML里面定义
    	this(context, attrs, android.R.attr.editTextStyle); 
    } 
    
    public SearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() { 
    	//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
    	mClearDrawable = getCompoundDrawables()[2]; 
        if (mClearDrawable == null) { 
        	mClearDrawable = getResources().getDrawable(R.drawable.utilslib_searchview_delete); 
        } 
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight()); 
        setClearIconVisible(false); 
        setOnFocusChangeListener(this); 
        addTextChangedListener(this); 
    } 
 
    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override 
    public boolean onTouchEvent(MotionEvent event) { 
        if (getCompoundDrawables()[2] != null) { 
            if (event.getAction() == MotionEvent.ACTION_UP) { 
            	boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) 
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) { 
                	if(onSearchListener != null) {
                		onSearchListener.onClearClick();
                	}
                    this.setText(""); 
                } 
            } 
        } 
        return super.onTouchEvent(event); 
    } 
 
    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override 
    public void onFocusChange(View v, boolean hasFocus) { 
        if (hasFocus) { 
            setClearIconVisible(getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        } 
    } 
 
 
    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) { 
        Drawable right = visible ? mClearDrawable : null; 
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
    } 
     
    
    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, int after) {} 
 
    @Override 
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {} 
 
    @Override 
    public void afterTextChanged(Editable s) { 
    	setClearIconVisible(s.length() > 0); 
    	if(onSearchListener != null) {
    		if(!s.toString().equals(lastChar) && s.length() > 0) {
    			onSearchListener.afterTextChanged(s.toString());
    		} else if(!s.toString().equals(lastChar) && s.length() == 0) {
    			onSearchListener.onClearClick();
    		}
    	} 
        lastChar = s.toString();
    } 
    
   
    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
    	this.setAnimation(shakeAnimation(5));
    }
    
    
    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
    	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    	translateAnimation.setInterpolator(new CycleInterpolator(counts));
    	translateAnimation.setDuration(1000);
    	return translateAnimation;
    }
 
    // 回调接口
    private OnSearchListener onSearchListener;
    public interface OnSearchListener {
    	/**
    	 * 搜索框文本变化回调
    	 * @param text
    	 */
    	void afterTextChanged(String text);
    	/**
    	 * 清除搜索关键词（重置数据）回调
    	 */
    	void onClearClick();
    }
    public void setOnOnSearchListener(OnSearchListener listener) {
    	onSearchListener = listener;
    }
 
}
