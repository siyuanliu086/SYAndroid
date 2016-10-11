package com.android.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;


/**
 * @author LuoJ
 * @date 2013-11-29
 * @package com.lidroid.xutils.ext.util -- ExternalUtil.java
 * @Description 外部存储工具类(对文件、路径的操作)
 */
public class ExternalUtil {
	
	/**
	 * 判断sdcard 是否存在。
	 * @return 存在返回true,否则false.
	 */
	public static boolean SDCardExist() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	/**
	 * 获取SDCard 根目录。
	 * @return SDCard根目录
	 */
	public static String getRootDir() {
		if (SDCardExist()) {
			String rootPath = Environment.getExternalStorageDirectory().getPath();
			if (rootPath != null) {
				return rootPath;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 将assets中的文件复制到SDcard中
	 * @param mctx
	 * @param fileName
	 */
	public static void copyFileToFolder(Context mctx, String savePath,String fileName) {
		LogUtils.d("开始复制文件！");
		File s=new File(savePath);
		if (!s.exists()) {
			s.mkdirs();
		}
		AssetManager assets = mctx.getResources().getAssets();
		InputStream dbIs = null;
		OutputStream os = null;
		try {
			dbIs = assets.open(fileName);
			File file = new File(savePath, fileName);                                            
			if (!file.exists()) {
				os = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int length = -1;
				while ((length = dbIs.read(buf)) > 0) {
					os.write(buf, 0, length);
				}
			} else {
				LogUtils.d("文件已存在！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != dbIs) {
					dbIs.close();
				}
				if (null != os) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean flag;
	private static File file;
	/**  
	 *  根据路径删除指定的目录或文件，无论存在与否  
	 *@param sPath  要删除的目录或文件  
	 *@return 删除成功返回 true，否则返回 false。  
	 */  
	public static boolean DeleteFolder(String sPath) {   
	    flag = false;   
	    file = new File(sPath);   
	    // 判断目录或文件是否存在   
	    if (!file.exists()) {  // 不存在返回 false
	    	LogUtils.e("文件夹不存在");
	        return flag;   
	    } else {   
	        // 判断是否为文件   
	        if (file.isFile()) {  // 为文件时调用删除文件方法   
	            return deleteFile(sPath);   
	        } else {  // 为目录时调用删除目录方法   
	            return deleteDirectory(sPath);   
	        }   
	    }   
	}
	
	/**  
	 * 删除单个文件  
	 * @param   sPath    被删除文件的文件名  
	 * @return 单个文件删除成功返回true，否则返回false  
	 */  
	private static boolean deleteFile(String sPath) {   
	    flag = false;   
	    file = new File(sPath);   
	    // 路径为文件且不为空则进行删除   
	    if (file.isFile() && file.exists()) {   
	        file.delete();   
	        flag = true;   
	    }   
	    return flag;   
	}  

	/**  
	 * 删除目录（文件夹）以及目录下的文件  
	 * @param   sPath 被删除目录的文件路径  
	 * @return  目录删除成功返回true，否则返回false  
	 */  
	private static boolean deleteDirectory(String sPath) {   
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符   
	    if (!sPath.endsWith(File.separator)) {   
	        sPath = sPath + File.separator;   
	    }   
	    File dirFile = new File(sPath);   
	    //如果dir对应的文件不存在，或者不是一个目录，则退出   
	    if (!dirFile.exists() || !dirFile.isDirectory()) {   
	        return false;   
	    }   
	    flag = true;   
	    //删除文件夹下的所有文件(包括子目录)   
	    File[] files = dirFile.listFiles();   
	    for (int i = 0; i < files.length; i++) {   
	        //删除子文件   
	        if (files[i].isFile()) {   
	            flag = deleteFile(files[i].getAbsolutePath());   
	            if (!flag) break;   
	        } //删除子目录   
	        else {   
	            flag = deleteDirectory(files[i].getAbsolutePath());   
	            if (!flag) break;   
	        }   
	    }   
	    if (!flag) return false;   
	    //删除当前目录   
	    if (dirFile.delete()) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	} 
}
