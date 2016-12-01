package com.android.common.utils;

/**
 * @TiTle DebugUtil.java
 * @Package com.android.common.utils
 * @Description 性能分析工具（时间）
 * @Date 2016年12月1日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-12-01
 * @Company ISoftStone ZHHB
 */
public class DebugUtil {

	public static MethodExecTimeTrace startMethodExecTimeTrace(){
		return new MethodExecTimeTrace();
	}
	
	public static class MethodExecTimeTrace{
		long startPoint;

		public MethodExecTimeTrace() {
			startPoint=System.currentTimeMillis();
		}
		
		public void stopTrace(){
			long stopPoint=System.currentTimeMillis();
			long execTime=stopPoint-startPoint;
			LogUtils.d("执行时间：["+execTime+"ms]、["+(execTime/1000)+"s]");
		}
	}
	
}


