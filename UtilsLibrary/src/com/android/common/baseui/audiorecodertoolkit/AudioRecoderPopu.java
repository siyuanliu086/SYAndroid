package com.android.common.baseui.audiorecodertoolkit;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.android.utilslibrary.R;

/**
 * @TiTle PickMediaPopupWindow.java
 * @Package com.iss.zhhblsnt.views
 * @Description 底部菜单
 * @Date 2016年4月26日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class AudioRecoderPopu extends PopupWindow implements OnClickListener, OnTouchListener {
	private RelativeLayout outLayout;
	private TextView stateTextView, infoTextView;
	private Button mButton;
	private Rect btnRect;
	private Activity activity;
	private String baseDir;

	/**
	 * @param activity 发起的activity
	 * @param parent 父布局View，锚点
	 * @param filePath 拍照图片路径（如果要拍照）
	 */
	public AudioRecoderPopu(Activity activity, String baseDir) {
		super(activity);
		this.activity = activity;
		this.baseDir = baseDir;
		initRecorder();
		
		View view = View.inflate(activity, R.layout.utilslib_layout_audio_recorder, null);
		
		infoTextView = (TextView) view.findViewById(R.id.utils_audiorecorder_text1);
		stateTextView = (TextView) view.findViewById(R.id.utils_audiorecorder_text2);
		mButton = (Button) view.findViewById(R.id.utils_audiorecorder_btn);
		outLayout = (RelativeLayout)view.findViewById(R.id.utils_popup_media_layout);
		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.utils_popuwindowstyle);
		// 实例化一个ColorDrawable颜色为半透明 
	    ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.utilslib_transparent)); 
	    // 设置弹出窗体的背景 
	    setBackgroundDrawable(dw); 
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		setListener();
	}

	private void setListener() {
		mButton.setOnTouchListener(this);
		outLayout.setOnClickListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		btnRect = new Rect(0, 0, mButton.getWidth(), mButton.getHeight());
		if(v.getId() == R.id.utils_audiorecorder_btn) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				// 按下，开始录音
				if(!isRecording) {
					isRecording = true;
					mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_start);
					record(FLAG_AMR);
				}
			} else if(event.getAction() == MotionEvent.ACTION_MOVE) {
				System.out.println("Rect : (" + btnRect.left + ","+ btnRect.bottom + "-" + btnRect.right + "," + btnRect.top +") MotionEvent :" + event.getX() + "," + event.getY());
				if(!btnRect.contains((int)event.getX(), (int)event.getY())) {
					// 移动，取消
					stateTextView.setText("取消录音");
				} else {
					stateTextView.setText("抬起停止录音，上滑取消");
				} 
			} else if(event.getAction() == MotionEvent.ACTION_UP){
				// 结束
				if(!btnRect.contains((int)event.getX(), (int)event.getY())) {
					// 上滑，取消
					uiHandler.sendEmptyMessageDelayed(CMD_CANCEL_RECORD, 400);
				} else {
					uiHandler.sendEmptyMessageDelayed(CMD_STOP_RECORD, 400);
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.utils_audiorecorder_btn) {
//			// 播放
//			if(!isRecording) {
//				mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_start);
//				record(FLAG_AMR);
//			} else {
//				mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_stop);
//				stop();
//			}
//			isRecording = !isRecording;
		} else {			
			dismiss();
		}
	}
	
	public void dismissMenu() {
		dismiss();
	}

	private final static int FLAG_AMR = 1;
	private boolean isRecording;
	private boolean isStop;
	private int mState = -1;    //-1:没再录制，0：录制wav，1：录制amr
	private UIHandler uiHandler;
	private UIThread uiThread; 
	
	private MediaRecordFunc mRecorder;
	private void initRecorder() {
    	mRecorder = MediaRecordFunc.getInstance();
    	String filePath = baseDir + System.currentTimeMillis() + "_audio.amr";
		mRecorder.setAMRFilePath(filePath);
        uiHandler = new UIHandler();        
    }
	
	/**
     * 开始录音
     * @param mFlag，1：录音amr格式
     */
    private void record(int mFlag) {
        if(mState != -1){
            Message msg = new Message();
            msg.what = CMD_RECORDFAIL;
            msg.obj = ErrorCode.E_STATE_RECODING;
            uiHandler.sendMessage(msg);
            
//            Bundle b = new Bundle();// 存放数据
//            b.putInt("cmd",CMD_RECORDFAIL);
//            b.putInt("msg", ErrorCode.E_STATE_RECODING);
//            msg.setData(b); 
//            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            return;
        } 
        int mResult = -1;
        
        mResult = mRecorder.startRecordAndFile();
            
        if(mResult == ErrorCode.SUCCESS){
            uiThread = new UIThread();
            new Thread(uiThread).start();
            mState = mFlag;
        }else{
            Message msg = new Message();
            msg.what = CMD_RECORDFAIL;
            msg.obj = mResult;
            uiHandler.sendMessage(msg);
            
//            Bundle b = new Bundle();// 存放数据
//            b.putInt("cmd",CMD_RECORDFAIL);
//            b.putInt("msg", mResult);
//            msg.setData(b); 
//            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
        }
    }
    
    boolean stopRecord = false;
    public void stopRecord() {
    	stopRecord = true;
    	stop();
    	if(uiThread != null) {    		
    		uiThread.stopThread();  
    	}
    	uiHandler.removeMessages(CMD_RECORDING_TIME);
    	uiHandler.removeMessages(CMD_RECORDFAIL);
    	uiHandler.removeMessages(CMD_STOP);
    	uiHandler.removeMessages(CMD_TIME_SHORT);
    }
    
    /**
     * 停止录音
     */
    private void stop(){
    	mRecorder.stopRecordAndFile();
    	
    	if(uiThread != null){
    		uiThread.stopThread();
    	}
    	if(uiHandler != null)
    		uiHandler.removeCallbacks(uiThread); 
    	Message msg = new Message();
//    	Bundle b = new Bundle();// 存放数据
//    	b.putInt("cmd",CMD_STOP);
//    	b.putInt("msg", mState);
//    	msg.setData(b);
    	
    	msg.what = CMD_STOP;
    	msg.obj = mState;
    	uiHandler.sendMessageDelayed(msg, 600); // 向Handler发送消息,更新UI 
    	mState = -1;
    }    
    
    class UIThread implements Runnable {        
        int mTimeMill = 0;
        boolean vRun = true;
        public void stopThread(){
            vRun = false;
        }
        public void run() {
        	mTimeMill = 0;
            while(vRun){
                mTimeMill ++;
                Log.d("thread", "mThread........"+mTimeMill);
                Message msg = new Message();
//                Bundle b = new Bundle();// 存放数据
//                b.putInt("cmd",CMD_RECORDING_TIME);
//                b.putInt("msg", mTimeMill);
//                msg.setData(b); 
 
                System.out.println("Amplitude : " + getAmplitude());
                
                msg.what = CMD_RECORDING_TIME;
                msg.obj = mTimeMill;
                uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } 
        }
    } 
    
    public double getAmplitude() {
		if (mRecorder != null)
			return (mRecorder.getMaxAmplitude() / 6000.0);
		else
			return 0;
	}
    
    private int spendTime = 0;
    
    private final static int CMD_RECORDING_TIME = 2000;
    private final static int CMD_RECORDFAIL = 2001;
    private final static int CMD_STOP = 2002;
    private final static int CMD_TIME_SHORT = 2003;
    private final static int CMD_STOP_RECORD = 2004;
    private final static int CMD_CANCEL_RECORD = 2005;
    class UIHandler extends Handler{
        public UIHandler() {
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what)
            {
            case CMD_RECORDING_TIME:
                int vTime = (Integer) msg.obj;
                spendTime = vTime;
                infoTextView.setText("正在录音中，已录制："+vTime+" 秒");
                break;
            case CMD_RECORDFAIL:
                int vErrorCode = (Integer) msg.obj;
                String vMsg = ErrorCode.getErrorInfo(activity, vErrorCode);
                mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_stop);
                infoTextView.setText("");
                stateTextView.setText("录音失败："+vMsg);
                if(completedListener != null) {
                	completedListener.onAudioFailed(vErrorCode);
                }
                break;
            case CMD_STOP:    
            	if(spendTime <= 2) {
            		uiHandler.sendEmptyMessage(CMD_TIME_SHORT);
            	} else {
            		String filePath = mRecorder.getAMRFilePath();
//            	String newFilePath = filePath.replace("_+_", "_+_" + spendTime);
//            	System.out.println("newFilePath : " + newFilePath);
//            	File file = new File(filePath);
//            	file.renameTo(new File(newFilePath));
            		mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_stop);
            		infoTextView.setText("已录制："+spendTime+" 秒");
            		stateTextView.setText("录音已完成");
            		if(completedListener != null && !stopRecord) {
            			completedListener.onAudioCompleted(filePath, spendTime);
            		}
            	}
            	break;
            case CMD_TIME_SHORT:
            	mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_stop);
            	infoTextView.setText("已录制："+spendTime+" 秒");
            	stateTextView.setText("录音时间太短不可用！按下重新开始");
            	if(completedListener != null) {
            		completedListener.onAudioFailed(CMD_TIME_SHORT);
            	}
            	break;
            case CMD_CANCEL_RECORD:
            	mRecorder.stopRecordAndFile();
            	
            	if(uiThread != null){
            		uiThread.stopThread();
            	}
            	
            	mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_stop);
            	infoTextView.setText("已录制："+spendTime+" 秒");
            	stateTextView.setText("取消录音！");
            	if(completedListener != null) {
            		completedListener.onAudioFailed(CMD_CANCEL_RECORD);
            	}
            	break;
            case CMD_STOP_RECORD:
            	if(isRecording) {
					isRecording = false;
					mButton.setBackgroundResource(R.drawable.utils_audio_voice_button_stop);
					stop();	
				}
            	break;
            default:
                break;
            }
        }
    };
    
    private OnAudioCompletedListener completedListener; 
    public interface OnAudioCompletedListener {
    	void onAudioCompleted(String filePath, int fileSize);
    	void onAudioFailed(int state);
    }
    
    public void setOnAudioCompletedListener(OnAudioCompletedListener listener) {
    	this.completedListener = listener;
    }
}
