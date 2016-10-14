package com.android.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

/**
 * 文件工具类
 * @author Administrator
 *
 */
public class FileUtils {
	private static final String TAG = "FileUtils";
	private static final int BUFFER_SIZE = 64 * 1024;
	private static final String SDCardPath=Environment.getExternalStorageDirectory() + "/";  
	/**
	 * 获取cache 缓存目录路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getCacheDir(Context context) {
		return getCacheDir(context, null);
	}

	/**
	 * 获取cache 缓存目录下面的子目录
	 * 
	 * @param context
	 * @param childDirName
	 * @return
	 */
	public static String getCacheDir(Context context, String childDirName) {
		return getDir(context, true, childDirName);
	}

	/**
	 * 获取 files目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getFilesDir(Context context) {
		return getFilesDir(context, null);
	}

	/**
	 * 获取 files 下的子目录
	 * 
	 * @param context
	 * @param childDirName
	 * @return
	 */
	public static String getFilesDir(Context context, String childDirName) {
		return getDir(context, false, childDirName);
	}

	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 从文件路径中获取文件名
	 * @param filename
	 * @return
	 */
	public static String getFileName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			if (filename.contains("/")) {
				return filename.substring(filename.lastIndexOf("/") + 1,
						filename.length());
			}
		}
		return filename;
	}
	
	/**
	 * 创建文件夹
	 * @param folderPath
	 */
	public static void mkDirs(String folderPath) {
		String[] strs = folderPath.split("/");
		int len = strs.length;
		String strPath = AppUtils.getSDPath() + "/";
		File path;
		for (int i = 0; i < len; i++) {
			if (strs[i] != null && !"".equalsIgnoreCase(strs[i])) {
				strPath += strs[i] + "/";
				path = new File(strPath);
				if (!path.exists()) {
					try {
						path.mkdirs();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private static String getDir(Context context, boolean isCache, String childDirName) {
		StringBuilder sb = new StringBuilder();
		sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		sb.append("/Android/data/");
		sb.append(context.getPackageName());
		if (isCache) {
			sb.append("/cache/");
		} else {
			sb.append("/files/");
		}
		if (!TextUtils.isEmpty(childDirName)) {
			sb.append(childDirName);
		}
		
		String path=sb.toString();
	
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	/**
	 * 根据路径删除文件
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 把Assert下的文件拷贝到SDCard 指定目录下
	 * @param context
	 * @param fileName
	 * @param dirPath
	 * @return
	 */
	public static String copyAssetFileToSDCard(Context context, String fileName, String dirPath) {
		try {
			InputStream is = context.getAssets().open(fileName);
			File pathFile = new File(dirPath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			File file = new File(dirPath + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[BUFFER_SIZE];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[BUFFER_SIZE];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
	
	// 复制文件
	public static void copyFile(String sourceFilePath, String targetFilePath) throws IOException {
		File sourceFile = new File(sourceFilePath);
		if(!sourceFile.exists()) {
			sourceFile.getParentFile().mkdirs();
			sourceFile.createNewFile();
		}
		
		File targetFile = new File(targetFilePath);
		if(!targetFile.exists()) {
			targetFile.getParentFile().mkdirs();
			targetFile.createNewFile();
		}
		
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[BUFFER_SIZE];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
	
	/**
     * Description:获取视频文件播放时长
     * @param context
     * @param title
     * @return
     * @author Administrator
     */
    public static String getVideoLength(Context context, String title){
    	ContentResolver resolver = context.getContentResolver();
    	Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
    			new String[]{MediaStore.Video.VideoColumns.DURATION}, 
    			" _display_name = ? ", 
    			new String[]{title}, 
    			null);
    	String time = "";
    	if(cursor != null && cursor.moveToFirst()){
    		time = cursor.getString(0);
    	}
    	cursor.close();
    	return time;
    }

	/**
	 * 将加密的64位字符串转化为本地文件
	 *
	 * @param base64String  base64加密字符串 
	 * @param savePath 		保存地址
	 * @param fileName 		文件名称
	 */
	synchronized public void convertBase64StringToLocalFile(String base64String, String dir, //64字符转化为本地压缩文件
			String fileName) {
		
		try {
			dir = new String(dir.getBytes(), "UTF-8");
			fileName = new String(fileName.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Log.e(TAG, e1.getMessage());
		}
		
		byte[] filebytes = Base64.decode(base64String, Base64.DEFAULT);
		convertByteArrayToLocalFile(filebytes, dir, fileName);
	}
	
	/**
	 * 将加密的64位List转化为本地文件
	 *
	 * @param base64String  base64加密字符串 
	 * @param savePath 		保存地址
	 * @param fileName 		文件名称
	 */
	synchronized public void convertBase64ListToLocalFile(List<String> base64List, String dir, //64字符转化为本地压缩文件
			List<String> ListfileName) {
		int i = 0;
		for(String base64String:base64List){
			String fileName = ListfileName.get(i);
			try {
				dir = new String(dir.getBytes(), "UTF-8");
				fileName = new String(fileName.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				Log.e(TAG, e1.getMessage());
			}
			
			byte[] filebytes = Base64.decode(base64String, Base64.DEFAULT);
			convertByteArrayToLocalFile(filebytes, dir, fileName);	
		i++;
		}
	}
	/**
	 * 将加密的64位Array转化为本地文件
	 *
	 * @param base64String  base64加密字符串 
	 * @param savePath 		保存地址
	 * @param fileName 		文件名称
	 */
	synchronized public void convertBase64ArrayToLocalFile(String[] base64Array, String dir, //64字符转化为本地压缩文件
			List<String> ListfileName) {
		int j = base64Array.length;
		for(int i=0;i<j;i++){
			String fileName = ListfileName.get(i);
			try {
				dir = new String(dir.getBytes(), "UTF-8");
				fileName = new String(fileName.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				Log.e(TAG, e1.getMessage());
			}
			
			byte[] filebytes = Base64.decode(base64Array[i].toString(), Base64.DEFAULT);
			convertByteArrayToLocalFile(filebytes, dir, fileName);	
		}
	}
	
	/**
	 * 将字节转化为本地文件
	 *
	 * @param content  		读取的内容
	 * @param savePath 		保存地址
	 * @param fileName 		文件名称
	 */
	public void convertByteArrayToLocalFile(byte[] content, String dir, String fileName) {
		try {
			dir = new String(dir.getBytes(), "UTF-8");
			fileName = new String(fileName.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Log.e(TAG, e1.getMessage());
		}
		
		File destDir = new File(dir);
		try {
			if (!destDir.exists())
				destDir.mkdir();
			FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
			fos.write(content);
			fos.flush();
			fos.close();
			Log.v(TAG, dir + fileName + " 成功转化为本地文件");
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} 
	}
	
	/**
	 * 将字符串写入文件
	 *
	 * @param path
	 * @param content
	 */
	public void convertStringToLocalFile(String path, String content){
        try {   
             FileWriter fw     = new FileWriter(path, true);   
             BufferedWriter bw = new BufferedWriter(fw);   
			 bw.write(content);
			 bw.close();  
			 fw.close();
			 Log.e(TAG, "写入文件" + path + "成功!!!");
      } catch (IOException e) {   
    	  Log.e(TAG, e.getMessage());
       } 
	}
	
	/**
	 * 将一组文件打包成  zip 压缩包，压缩包中没有文件夹目录
	 *
	 * @param zipedAbsFileNames 1，可以按一组文件的存储绝对路径名称和对应压缩文件夹目录层次设置
	 * 							key: /sdcard/test.txt ; value: test.txt
	 * 							2，可以直接压缩一个文件夹下所有的文件，压缩文件里面的路径为文件夹路径的一部分
	 * 							key: /sdcard/floder/  ; value: floder/
	 * @param zipOutAbsFileName 打包后的  zip 文件名
	 */
	public void zipedFiles(HashMap<String, Object> zipedAbsFileNames, String zipOutAbsFileName) {
		
		try {
			zipOutAbsFileName = new String(zipOutAbsFileName.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Log.e(TAG, e1.getMessage());
		}
		
		byte[] buf = new byte[1024];
		try {
			//创建压缩后文件的文件夹
			zipOutAbsFileName = zipOutAbsFileName.replace("\\", "/");
			File zipOutAbsFileNameFolder = new File(
					zipOutAbsFileName.substring(0, zipOutAbsFileName.lastIndexOf("/")));
			if (!zipOutAbsFileNameFolder.exists()) {
				zipOutAbsFileNameFolder.mkdirs();
			}
			//进行压缩操作
			ZipOutputStream out = new ZipOutputStream(
					new FileOutputStream(zipOutAbsFileName));
			Iterator<Map.Entry<String, Object>> iterator = zipedAbsFileNames.entrySet().iterator();
			while (iterator.hasNext()) { 
				Map.Entry<String, Object> entry = iterator.next();
				
				String inputStr  = new String(entry.getKey().getBytes(), "UTF-8");
				String outputStr = new String(entry.getValue().toString().getBytes(), "UTF-8");
				
				File inputFile 		  = new File(inputStr);
				ArrayList<File> files = new ArrayList<File>();
				getAbsFiles(inputFile, files);
				
				if (files.size() > 1) {
					//是目录
					for (File file : files) {
						inputStr  = file.getAbsolutePath();
						if (inputStr.indexOf(outputStr) != -1) {
							
							outputStr = inputStr.substring(inputStr.indexOf(outputStr) + 1);
							
							FileInputStream in = new FileInputStream(inputStr);
							out.putNextEntry(new ZipEntry(outputStr));
							int len;
							while ((len = in.read(buf)) > 0) {
								out.write(buf, 0, len);
							}
							out.closeEntry();
							in.close();
							
							outputStr = new String(entry.getValue().toString().getBytes(), "UTF-8");
						}
					}
				} else {
					//是一个文件的绝对路径
					outputStr = inputStr.substring(inputStr.indexOf(outputStr) + 1);
					
					FileInputStream in = new FileInputStream(inputFile);
					out.putNextEntry(new ZipEntry(outputStr));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}
			}
			out.close(); 
			Log.v(TAG, zipOutAbsFileName + " 压缩成功");
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 递归遍历文件夹
	 *
	 * @param filepath
	 * @return
	 */
	public void getAbsFiles(File filepath, ArrayList<File> fileslist) {
		
		File[] files = filepath.listFiles();
		
		for (File file : files) {
			if (file.isDirectory()) {
				getAbsFiles(file, fileslist);
			} else {
				fileslist.add(file);
			}
		}
	}
	
	 private static ArrayList<File> allfiles;
		
	    /**
	     * 返回目录下的文件夹集合
	     * @param filePath
	     * @return
	     */
		public static ArrayList<File> getFileDir(String filePath){//返回目录下的文件夹
			ArrayList<File> fileList=new ArrayList<File>();
			File[] files=null;
			File file=new File(filePath);
			if(file.exists())
			files=file.listFiles();
			for(File f:files){
				if(f.isDirectory())//是任务文件夹则添加
				fileList.add(f);
			}
			return fileList;
		}
		
		/**
		 * 判断文件名后缀，是否是多媒体文件
		 * @param fileName
		 * @return
		 */
		public static boolean isMedia(String fileName){//判断文件名后缀是否为多媒体文件(mp3,amr,mp4)
			String behand=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(behand.equals("jpg")||behand.equals("mp4")||behand.equals("amr")||"png".equals(behand)||behand.equals("mp3")||behand.equals("wma"))
				return true;
			else return false;
		}
		
		/**
		 * 获取所有的多媒体文件
		 * @param filelist
		 * @return
		 */
	    public static ArrayList<ArrayList<File>> getAllMedias(ArrayList<File> filelist){
	    	ArrayList<ArrayList<File>> list=new ArrayList<ArrayList<File>>();
			for(File f:filelist){//遍历每个任务文件夹
				ArrayList<File> file=new ArrayList<File>();
				File[] filearray=f.listFiles();
				for(File fi:filearray){//遍历每个任务文件夹中的多媒体文件
					if(isMedia(fi.getName())){
						file.add(fi);
					}
				}
				list.add(file);
			}    	
			return list;
		}
		
	    /**
	     * 获取文件集合中文件数量
	     * @param list
	     * @return
	     */
		public static int getMediaFileNum(ArrayList<ArrayList<File>> list){
			int count=0;
			for(ArrayList<File> flist:list){
				count+=flist.size();
			}
			return count;
		}
		
		/**
		 * Description:获得所有的多媒体文件，存入allfiles
		 * @param path
		 * @return
		 */
		public static ArrayList<File> getAllMediaFiles(String path){
			allfiles=new ArrayList<File>();
			File file=new File(path);
			RecursionDir(file);
			return allfiles;
		}
		/**
		 * Description:获取当前文件夹下所有的html文件
		 * @param path 目标路径
		 * @return html文件集合
		 */
		public static ArrayList<File> getAllHtmlFiles(String path){
			ArrayList<File> allFiles=new ArrayList<File>();
			File file=new File(path);
			if(file.exists()){
				File[] files=file.listFiles();
				for (int i = 0; i < files.length; i++) {
					
					if(files[i].isFile()){
						String fileName=files[i].getName();
						String behand=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
						if(behand.equals("html")){
							allFiles.add(files[i]);
						}
					}
					
				}
				
			}
			
			return allFiles;
		}
		
		/**
		 * Description:递归遍历返回多媒体文件集合
		 * @param file
		 * @author Administrator
		 */
		public static void RecursionDir(File file) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile() && isMedia(files[i].getName())) {
					allfiles.add(files[i]);
				} else if (files[i].isDirectory()) {
					RecursionDir(files[i]);
				}
			}
		}

		/**
		 * Description:获得参数list中的所有文件，存入ArrayList
		 * @param list
		 * @return ArrayList
		 */
		public static ArrayList<File> getAllMediaFilesList(ArrayList<ArrayList<File>> list){
			ArrayList<File> filesList=new ArrayList<File>();
			for(ArrayList<File> f:list){
				for(File file:f){
					filesList.add(file);
				}
			}
			return filesList;
		}
		
		/**
		 * 获取文件类型
		 * @param file
		 * @return
		 */
		public static String getFileType(File file){
			String type=file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length());
			return type;
		}
		
		/**
		 * 检测文件是否存在
		 * @param path 文件路径
		 * @return
		 */
		public static boolean isFileExist(String path){
			return new File(path).exists();
		}
		
		
		   /** 
	     * 通过传递一个url下载对应的文件。 
	     *  
	     * @param strUrl 
	     *            下载文件的url地址 
	     * @param path 
	     *            保存文件的路径 
	     * @param fileName 
	     *            保存文件的文件名 
	     * @return 0:文件下载成功 1:文件下载失败 EXIST:同名文件已存在 
	     */  
	    public int fileDownload(String strUrl, String path, String fileName) {  
	      
	        InputStream inputStream = null;  
	        File file = null;  
	      /*  File file2=new File(SDCardPath+path+fileName);
	        if(file2.exists()){
	        	file2.delete();
	        }*/
	        int result=0;
	         
	           try {  
	                inputStream = getInputStream(strUrl);  
	                file = writeToSDCard(path, fileName, inputStream);  
	                if (file == null) {  
	                	result= 1;  
	                }  
	            } catch (IOException e) {  //网络IO出错
	                e.printStackTrace();  
	                result=1;
	            } catch(Exception e){//filenotfound错误或其他错误
	            	 e.printStackTrace();  
		             result=1;
	            }finally {
	                try {  
	                    inputStream.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        
	        return result;  
	    } 
	    
	    /** 
	     * 将一个输入流中的内容写入到SD卡上生成文件 （如果本地已经存在该文件则删掉重新创建）
	     * @param path 文件目录 
	     * @param fileName 文件名 
	     * @param inputStream 字节输入流 
	     * @return 得到的文件 
	     */  
	    public File writeToSDCard(String path, String fileName, InputStream inputStream) {  
	        File file = null;  
	        OutputStream output = null;  
	        try{  
	            createSDDir(path);  
	            file = createSDFile(path + fileName);  
	            output = new FileOutputStream(file);  
	            byte buffer [] = new byte[4 * 1024]; 
	            int len;
	            while((len=inputStream.read(buffer)) != -1){  
	                output.write(buffer,0,len);  
	            }  
	            output.flush();  
	        }  
	        catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        finally{  
	            try{  
	                output.close();  
	            }  
	            catch(Exception e){  
	                e.printStackTrace();  
	            }  
	        }  
	        return file;  
	    } 
	    /** 
	     * 在SD卡上创建目录 （如果不存在则创建一个）
	     *  
	     * @param dirName 
	     *            要创建的目录名 
	     * @return 创建得到的目录 
	     */  
	    public File createSDDir(String dirName) {  
	        File dir = new File(SDCardPath + dirName);  
	        if(!dir.exists()){
	        	dir.mkdir();
	        }
	        return dir;  
	    }
	    /** 
	     * 在SD卡上创建文件 （已经存在则删掉）
	     *  
	     * @param fileName 
	     *            要创建的文件名 
	     * @return 创建得到的文件 
	     */  
	    public File createSDFile(String fileName) throws IOException {  
	        File file = new File(SDCardPath + fileName); 
	        if(!file.exists()){
	        	file.createNewFile();  
	        }else{
	        	file.delete();
	        	file.createNewFile();
	        }
	        
	        return file;  
	    } 
	   
	    
	    /** 
	     * 通过一个url获取http连接的输入流 
	     *  
	     * @param strUrl 
	     *            目标url 
	     * @return 到该url的http连接的输入流 
	     * @throws IOException 
	     */  
	    private InputStream getInputStream(String strUrl) throws IOException {  
	    	URL url = new URL(strUrl);  
	    	HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();  
	        InputStream is = urlConn.getInputStream();  
	        return is;  
	    } 

	    
	    /**
	     * Description:递归扫描文件
	     * @param filePath 要扫描的目录路径
	     * @param exten 文件后缀名，大小写区分
	     * @param result 
	     */
	    private static void scanFile(String filePath, String exten, ArrayList<File> result){
			File file = new File(filePath);
			if(file.exists()){
				if(file.isDirectory()){
					File[] files = file.listFiles();
					if(files != null){
						for(File f : files){
							scanFile(f.getAbsolutePath(), exten, result);
						}
					}
				} else {
					if(file.getName().endsWith(exten)){
						result.add(file);
					}
				}
			}
			
		}
		
	    /**
	     * Description:扫描并返回特殊后缀名的文件集合
	     * @param filePath 要扫描的目录路径
	     * @param exten 需要的文件后缀名，区分大小写
	     * @return 搜索结果集合
	     */
		public static ArrayList<File> searchFiles(String filePath, String exten){
			ArrayList<File> resultFile = new ArrayList<File>();
			scanFile(filePath, exten, resultFile);
			return resultFile;
		}
		/**
		 * Description: 调用系统共享功能
		 * @param uri 发送文件路径
		 * @param context
		 * @author Administrator
		 */
		public static void sendToBluetooth(String uri,Context context){
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
			shareIntent.putExtra(Intent.EXTRA_TEXT, "body");
			shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri));
			// shareIntent.setType("text/plain");
			shareIntent.setType("image/*");
			context.startActivity(shareIntent);
		}
		
		
		public static final String ATTACH_SUFFIX_IMG = "jpg,png";
		public static final String ATTACH_SUFFIX_AUDO = "amr,arm,wma,mp3";
		public static final String ATTACH_SUFFIX_VIDEO = "mp4";
		public static final String ATTACH_SUFFIX_PDF = "pdf";
		public static final String ATTACH_SUFFIX_WORD = "doc,docx";
		public static final String ATTACH_SUFFIX_XLS = "xls";
		/**
		 * 统一的附件判断方式
		 * @param suffix
		 * @return
		 */
		public static String checkAttachType(String suffix) {
			String type = "";
			if (ATTACH_SUFFIX_IMG.contains(suffix)) {
				type = FileType.IMAGE.getType();
			} else if(ATTACH_SUFFIX_AUDO.contains(suffix)) {
				type = FileType.AUDIO.getType();
			} else if(ATTACH_SUFFIX_VIDEO.contains(suffix)) {
				type = FileType.VIDEO.getType();
			} else if(ATTACH_SUFFIX_PDF.contains(suffix)) {
				type = FileType.PDF.getType();
			} else if(ATTACH_SUFFIX_WORD.contains(suffix)) {
				type = FileType.WORD.getType();
			} else if(ATTACH_SUFFIX_XLS.contains(suffix)) {
				type = FileType.EXCEL.getType();
			}
			return type;
		}

		/**
		 * Description: 打开不同格式的附件
		 * @param context 上下文
		 * @param fileType 文件格式
		 * @param filePath  文件路径
		 * @author 王红娟
		 */
		public static void OpenFile(Context context, String fileType, String filePath){
			FileType filetype = getFileType(fileType);
			Intent intent = new Intent("android.intent.action.VIEW");
			Uri uri = null;
			switch (filetype) {
			case HTML:
				uri = Uri.parse(filePath).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(filePath).build();
				break;
			case IMAGE:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			case PDF:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			case TXT:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			case AUDIO:
				File audioPath = new File(filePath);
				if(audioPath.exists()) {
					intent.addCategory("android.intent.category.DEFAULT");
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("oneshot", 0);
					intent.putExtra("configchange", 0);
					uri = Uri.fromFile(new File(filePath));
				} else {
					uri = Uri.parse(filePath);
				}
				break;
			case VIDEO:
				File videoPath = new File(filePath);
				if(videoPath.exists()) {
					intent.addCategory("android.intent.category.DEFAULT");
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("oneshot", 0);
					intent.putExtra("configchange", 0);
					uri = Uri.fromFile(videoPath);
				} else {
					uri = Uri.parse(filePath);
				}
				break;
			case CHM:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			case WORD:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			case EXCEL:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			case PPT:
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				uri = Uri.fromFile(new File(filePath));
				break;
			}
			intent.setDataAndType(uri, fileType);
			
			try {
				context.startActivity(intent);
			} catch (ActivityNotFoundException  e) {
				e.printStackTrace();
				Toast.makeText(context, "附件无法打开，请下载相关软件", Toast.LENGTH_SHORT).show();
			}
		}
		
		/**
		 * Description: 文件类型的枚举类
		 * @author 王红娟
		 */
		public static enum FileType{
			HTML("text/html"),
			IMAGE("image/*"),
			PDF("application/pdf"),
			TXT("text/plain"),
			AUDIO("audio/*"),
			VIDEO("video/*"),
			CHM("application/x-chm"),
			WORD("application/msword"),
			EXCEL("application/vnd.ms-excel"),
			PPT("application/vnd.ms-powerpoint");
			
			private String type;
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			private FileType(String type){
				this.type = type; 
			}
			
		}
		
		/**
		 * Description: 通过不同的文件类型返回不同的枚举对象
		 * @param type 系统的文件类型
		 * @return 文件类型枚举对象
		 * FileType 
		 * @author 王红娟
		 */
		public static FileType getFileType(String type) {
			if(type.equals("text/html")){
				return FileType.HTML;
			}
			if(type.equals("image/*")){
				return FileType.IMAGE;
			}
			if(type.equals("application/pdf")){
				return FileType.PDF;
			}
			if(type.equals("text/plain")){
				return FileType.TXT;
			}
			if(type.equals("audio/*")){
				return FileType.AUDIO;
			}
			if(type.equals("video/*")){
				return FileType.VIDEO;
			}
			if(type.equals("application/x-chm")){
				return FileType.CHM;
			}
			if(type.equals("application/msword")){
				return FileType.WORD;
			}
			if(type.equals("application/vnd.ms-excel")){
				return FileType.EXCEL;
			}
			if(type.equals("application/vnd.ms-powerpoint")){
				return FileType.PPT;
			}
			return null;
		}
	
}
