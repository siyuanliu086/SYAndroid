package com.android.common.utils;

import android.os.Handler;
import android.os.Message;

/**
 * @author LuoJ
 * @date 2014-7-22
 * @package com.anhry.android.utils -- ThreadUtil.java
 * @Description 线程工具类
 */
public class ThreadUtil {

	/**
	 * 异步实现同步运行.
	 * 实现关键的接口函数即可
	 * @param syncInterface
	 */
	public static void syncRun(final SyncInterface syncInterface){
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(null!=syncInterface)syncInterface.workThreadIsDone(msg.obj);
			}
		};
		new Thread(){public void run() {
			Object params=null;
			if(null!=syncInterface){
				params=syncInterface.runInWorkThread();
			}
			Message msg= handler.obtainMessage(0);
			msg.obj=params;
			handler.sendMessage(msg);
		};}.start();
	}
	
	/**
	 * 同步接口
	 * @author LuoJ
	 * @date 2014-7-22
	 * @package com.anhry.android.utils -- ThreadUtil.java
	 * @Description
	 */
	public interface SyncInterface{
		Object runInWorkThread();
		void workThreadIsDone(Object params);
	}
	
}


