package com.siyuan.utilslibrarydemos;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.common.baseui.pulltozoomview.PullToZoomScrollViewEx;

public class PullToZoomScrollActivity extends Activity {

    private PullToZoomScrollViewEx scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_zoom_scroll_view);
        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        scrollView.getPullRootView().findViewById(R.id.tv_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick -->");
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick -->");
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick -->");
            }
        });
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
        
//        scrollView.setZoomEnabled(false);
    }
    

    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//            return true;
//        }
//        else if (id == R.id.action_settings) {
//            loadViewForCode();
//            return true;
//        }
//        else if (id == R.id.action_normal) {
//            scrollView.setParallax(false);
//            return true;
//        } else if (id == R.id.action_parallax) {
//            scrollView.setParallax(true);
//            return true;
//        } else if (id == R.id.action_show_head) {
////            scrollView.showHeaderView();
//            scrollView.setHideHeader(false);
//            return true;
//        } else if (id == R.id.action_hide_head) {
////            scrollView.hideHeaderVie˛w();
//            scrollView.setHideHeader(true);
//            return true;
//        } else if (id == R.id.action_disable_zoom) {
////            scrollView.setEnableZoom(false);
//            scrollView.setZoomEnabled(false);
//            return true;
//        } else if (id == R.id.action_enable_zoom) {
////            scrollView.setEnableZoom(true);
//            scrollView.setZoomEnabled(true);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}

