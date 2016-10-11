package com.android.common.utils;

/**
 * @author LuoJ
 * @date 2014-5-9
 * @package com.anhry.android.utils -- DebugUtil.java
 * @Description Debug工具类
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


