package com.tc.common.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 文件上传 下载 删除
 * 
 * @author xu.su
 * 
 */
public class FileUtil {

	private static Logger LOG = Logger.getLogger(FileUtil.class);

	/**
	 * upload
	 * 
	 * @param fileItem
	 * @param file
	 * @throws Exception
	 */
	public static void upload(FileItem fileItem, File file) throws Exception {
		InputStream is = fileItem.getInputStream();
		if (!file.getParentFile().exists()) {
			boolean mkdirsFlag = file.getParentFile().mkdirs();
			if(!mkdirsFlag){
				throw new RuntimeException("目录创建失败！");
			}
		}
		OutputStream os = new FileOutputStream(file);
		steam(is, os);
	}
	
	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	public static void download(String path,String fileName, HttpServletResponse response)
		throws Exception {
		File file = new File(path,fileName);
		download(file, response);
	}
	

	/**
	 * download
	 * 
	 * @param file
	 * @param response
	 * @throws java.io.IOException
	 */
	public static void download(File file, HttpServletResponse response)
			throws Exception {
		if(!file.exists()){
			throw new RuntimeException("File not find!");
		}
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("GBK");
		String fileName = file.getName();
	    fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentLength((int)file.length());
		InputStream is = new FileInputStream(file);
		OutputStream os = response.getOutputStream();
		steam(is, os);
	}

	/**
	 * download
	 *
	 * @param file
	 * @param response
	 * @param dispFileName
	 * @throws java.io.IOException
	 */
	public static void download(File file, HttpServletResponse response, String dispFileName)
		throws Exception {
		if(!file.exists()){
			throw new RuntimeException("File not find!");
		}
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("GBK");
		String suffx;
		suffx = file.getName();
		String fileName = dispFileName.concat(suffx.lastIndexOf(".") > 0 ? suffx.substring( suffx.lastIndexOf(".") ) : "" );	//file.getName();
	    fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
	    response.setHeader("Content-type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		response.setContentLength((int)file.length());
		InputStream is = new FileInputStream(file);
		OutputStream os = response.getOutputStream();
		steam(is, os);
	}

	/**
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void steam(InputStream is, OutputStream os) throws Exception {
		byte[] buffer = new byte[512];
		int length = 0;
		try {
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			os.close();
			is.close();
		}
	}
	
	/**
	 * 
	 * @param is
	 * @param os
	 * @param isClose
	 * @param osClose
	 * @throws Exception
	 */
	public static void steam(InputStream is, OutputStream os,boolean isClose,boolean osClose) throws Exception {
		byte[] buffer = new byte[400];
		int length = 0;
		try {
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			if(osClose){
				os.close();
			}
			if(isClose){
				is.close();
			}
		}
	}
	
	/**
	 * 
	 * @param I
	 * @return
	 */
	public static String getSize(Long I){
		DecimalFormat myFormatter = new DecimalFormat("####.##");
		String sizeStr=null;
		double k = (double)(I/1024.00);
		if (k / 1024  < 1) {
			String Size = myFormatter.format(k);
			sizeStr = (Size + "K");
		} else {
			String Size = myFormatter.format(k / 1024 + 0f);
			sizeStr = (Size + "M");
		}
		return sizeStr;
	}
	
	/**
	 * 
	 * @param path
	 * @param oldname
	 * @param newname
	 */
	 public static boolean renameFile(String path,String oldname,String newname){
	       if(!oldname.equals(newname)){
	           File oldfile=new File(path,oldname);
	           File newfile=new File(path,newname);
	           if(newfile.exists()){
	        	    throw new RuntimeException("File exists!");
	           }
	           return  oldfile.renameTo(newfile);
	       }
	      return false;
	 }
	 
	 
	 /**
	  * delete file
	  * @param path
	  */
	 public static void deleteFile(String path,String fileName){
		 File file = new File(path,fileName);
		 deleteFile(file);
	 }
	 
	 /**
	  * 
	  * @param file
	  */
	 public static void deleteFile(File file){
			if (file.isDirectory()) {
				String[] fileNames = file.list();
				if (fileNames != null && fileNames.length > 0)
					for (int i = 0; i < fileNames.length; i++) {
						File f = new File(file.getPath(), fileNames[i]);
						deleteFile(f);
					}
			}
		 boolean deleteFlag = file.delete();
		 if (!deleteFlag) {
			 System.out.println(file.getAbsolutePath() + "删除失败!~");
		 }
	 }
	 
	 
	 public static List<Map<String,String>> getFilesInfo( String path, int contentPathOffset, boolean isRecurs  ) {
		 List<Map<String,String>> list = Lists.newArrayList();
		 
		 File file = new File( path );
		 if( file.exists() ) {
			 File files[] = file.listFiles();
			 if(null != files){
				 for (File file2 : files) {
					 if( file2.isFile() && !file2.isHidden() ) {
						 Map<String,String> map = Maps.newHashMap();
						 String filePath = file2.getParent();

						 map.put("fileName", file2.getName());
						 map.put("size", getSize(file2.length()));

						 if( filePath.length() > contentPathOffset )
							 map.put("path", filePath.substring( contentPathOffset ) );
						 else map.put("path", filePath );

						 map.put("createTime", DateUtils.date2Str( new Date(file2.lastModified()), "yyyy-MM-dd HH:mm:ss"));
						 list.add( map );
					 } else if( isRecurs && file2.isDirectory() ) {
						 list.addAll( getFilesInfo(String.format("%s/%s", path, file2.getName()), contentPathOffset, isRecurs) );
					 }
				 }
			 }
		 }
		 
		 return list;
	 }
	 
	 public static String getPathLastName( String path ) {
		 if( Strings.isNullOrEmpty(path) ) return "";
		 int offset = path.lastIndexOf('/');
		 if( offset < 0 ) offset = path.lastIndexOf("\\");
		 
		 return path.substring( offset + 1 );
	 }
}
