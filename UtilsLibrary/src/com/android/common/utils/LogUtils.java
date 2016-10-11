package com.android.common.utils;

import java.util.HashMap;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;

/**
 * @author LuoJ
 * @date 2014-7-31
 * @package com.anhry.android.utils -- LogUtils.java
 * @Description 
 */
public class LogUtils {

    public static String customTagPrefix = "";

    private LogUtils() {
    }

//    public static boolean allowD = true;
//    public static boolean allowE = true;
//    public static boolean allowI = true;
//    public static boolean allowV = true;
//    public static boolean allowW = true;
//    public static boolean allowWtf = true;

    public static final int D=0x1,E=0x2,I=0x3,V=0x4,W=0x5,WTF=0x6;
    private static HashMap<String, SparseBooleanArray> controllers=new HashMap<String, SparseBooleanArray>();
    static{
		controllers.put("default", generateDefaultFlag());
    }
    
    private static SparseBooleanArray generateDefaultFlag(){
    	SparseBooleanArray value=new SparseBooleanArray();
    	value.append(D, true);
    	value.append(E, true);
    	value.append(I, true);
    	value.append(V, true);
    	value.append(W, true);
    	value.append(WTF, true);
    	return value;
    }
    
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s[%s, %d]";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content){
    	if (!getCustomFlag(D, "")) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content);
        handleLog(D, tag, content);
    }
    
    public static void d(String content,String moduleName) {
        if (!getCustomFlag(D, moduleName)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content);
        handleLog(D, tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!getDefaultFlag(D)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content, tr);
    }

    public static void e(String content){
    	if (!getCustomFlag(E, "")) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag, content);
        handleLog(E, tag, content);
    }
    
    public static void e(String content,String moduleName) {
        if (!getCustomFlag(E, moduleName)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag, content);
        handleLog(E, tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!getDefaultFlag(E)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag, content, tr);
    }

    public static void i(String content){
    	if (!getCustomFlag(I, "")) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag, content);
        handleLog(E, tag, content);
    }
    
    public static void i(String content,String moduleName) {
        if (!getCustomFlag(I, moduleName)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag, content);
        handleLog(E, tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!getDefaultFlag(I)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag, content, tr);
    }
    
    public static void v(String content){
    	if (!getCustomFlag(V, "")) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag, content);
        handleLog(V, tag, content);
    }
    
    public static void v(String content,String moduleName) {
        if (!getCustomFlag(V, moduleName)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag, content);
        handleLog(V, tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!getDefaultFlag(V)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag, content, tr);
    }

    public static void w(String content){
    	if (!getCustomFlag(W, "")) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, content);
        handleLog(W, tag, content);
    }
    
    public static void w(String content,String moduleName) {
        if (!getCustomFlag(W, moduleName)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, content);
        handleLog(W, tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!getDefaultFlag(W)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!getDefaultFlag(W)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, tr);
    }

    public static void wtf(String content){
    	if (!getCustomFlag(WTF, "")) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, content);
        handleLog(WTF, tag, content);
    }
    
    public static void wtf(String content,String moduleName) {
        if (!getCustomFlag(WTF, moduleName)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, content);
        handleLog(WTF, tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!getDefaultFlag(WTF)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!getDefaultFlag(WTF)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, tr);
    }
    
    private static boolean getDefaultFlag(int type){
    	if (null!=controllers) {
			SparseBooleanArray c = controllers.get("default");
			if (null!=c) {
				return c.get(type);
			}
		}
    	return false;
    }
    
    /**
     * 获取自定义标记
     * @param type log类型
     * @param moduleName 自定义名称
     * @return
     */
    private static boolean getCustomFlag(int type,String moduleName){
    	if (null!=controllers&&!TextUtils.isEmpty(moduleName)) {
    		SparseBooleanArray c = controllers.get(moduleName);
			if(null!=c){
				return c.get(type);
			}
		}
    	return getDefaultFlag(type);
    }
    
    public static void controllCustomFlag(String moduleName,int type,boolean flag){
    	SparseBooleanArray s = controllers.get(moduleName);
    	if (null==s) {
			controllers.put(moduleName, generateDefaultFlag());
			s=controllers.get(moduleName);
		}
    	s.append(type, flag);
    }
    
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
    
    /**
     * 日志监听
     */
    private static HashMap<String, LogListener> logListeners=new HashMap<String, LogListener>();//如果要处理Log信息，实现listener即可
    public static void addLogListener(LogListener listener){
    	logListeners.put(listener.getClass().getCanonicalName(), listener);
    }
    public interface LogListener{
    	void handleLog(int level,String tag,String content);
    }
    private static void handleLog(int level,String tag,String content){
    	if (null!=logListeners&&!logListeners.isEmpty()) {
    		for (LogListener listener : logListeners.values()) {
				listener.handleLog(level, tag, content);
			}
		}
    }
    
}
