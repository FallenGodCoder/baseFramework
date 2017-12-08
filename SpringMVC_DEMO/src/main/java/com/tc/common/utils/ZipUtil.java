package com.tc.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 方法1.利用apache的ZipOutputStream类解压文件或文件夹中的文件
 * 方法2.利用java的ZipOutputStream类压缩解压单个文件成字节数组
 */
public abstract class ZipUtil {
	private static final Log logger = LogFactory.getLog(ZipUtil.class);
	static final int BUFFER = 2048;

	// 压缩成字节数组
	public static byte[] zipBytes(byte[] bytes) throws IOException {
		ByteArrayOutputStream tempOStream = null;
		BufferedOutputStream tempBOStream = null;
		ZipOutputStream tempZStream = null;
		ZipEntry tempEntry = null;
		byte[] tempBytes = null;

		tempOStream = new ByteArrayOutputStream(bytes.length);
		tempBOStream = new BufferedOutputStream(tempOStream);
		tempZStream = new ZipOutputStream(tempBOStream);
		tempEntry = new ZipEntry(String.valueOf(bytes.length));
		tempEntry.setMethod(ZipEntry.DEFLATED);
		tempEntry.setSize((long) bytes.length);

		tempZStream.putNextEntry(tempEntry);
		tempZStream.write(bytes, 0, bytes.length);
		tempZStream.flush();
		tempBOStream.flush();
		tempOStream.flush();
		tempZStream.close();
		tempBytes = tempOStream.toByteArray();
		tempOStream.close();
		tempBOStream.close();
		return tempBytes;
	}

	// 根据字节数组解压
	public static void unzipBytes(byte[] bytes, OutputStream os)
			throws IOException {
		ByteArrayInputStream tempIStream = null;
		BufferedInputStream tempBIStream = null;
		ZipInputStream tempZIStream = null;
		ZipEntry tempEntry = null;
		long tempDecompressedSize = -1;
		byte[] tempUncompressedBuf = null;

		tempIStream = new ByteArrayInputStream(bytes, 0, bytes.length);
		tempBIStream = new BufferedInputStream(tempIStream);
		tempZIStream = new ZipInputStream(tempBIStream);
		tempEntry = tempZIStream.getNextEntry();

		if (tempEntry != null) {
			tempDecompressedSize = tempEntry.getCompressedSize();
			if (tempDecompressedSize < 0) {
				tempDecompressedSize = Long.parseLong(tempEntry.getName());
			}

			int size = (int) tempDecompressedSize;
			tempUncompressedBuf = new byte[size];
			int num = 0, count = 0;
			while (true) {
				count = tempZIStream.read(tempUncompressedBuf, 0, size - num);
				num += count;
				os.write(tempUncompressedBuf, 0, count);
				os.flush();
				if (num >= size)
					break;
			}
		}
		tempZIStream.close();
	}

	public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static byte[] zip(String fileName) throws IOException {
		return zipBytes(getBytesFromFile(new File(fileName)));
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		// File base=new File(baseDir);
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
				ret.add(tmp[i]);
			}
			if (tmp[i].isDirectory()) {
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else {
				ret = real.getName() + "/" + ret;
			}
		}
		return ret;
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	private static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {
			boolean mkdirFlag = ret.mkdirs();
			if(!mkdirFlag){
				throw new RuntimeException("创建目录失败！~");
			}
		}
		ret = new File(ret, dirs[dirs.length - 1]);
		return ret;
	}

	/**
	 * zip压缩功能测. 将目录下的所有文件连同子目录压缩到目录文件中
	 * 
	 * @param baseDir
	 *            所要压缩的目录名（包含绝对路径）
	 * @param objFileName
	 *            压缩后的文件名
	 * @throws Exception
	 */
	public static void createZip(String baseDir, String objFileName)
			throws Exception {
		File folderObject = new File(baseDir);

		if (folderObject.exists()) {
			List<File> fileList = getSubFiles(new File(baseDir));

			if (fileList == null || fileList.isEmpty()) {
				logger.info("目录：" + baseDir + "中无子文件");
				return;
			}
			// 压缩文件名
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					objFileName));
			ZipEntry ze = null;
			byte[] buf = new byte[BUFFER];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				// 创建一个ZipEntry，并设置Name和其它的一些属性
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				// 将ZipEntry加到zos中，再写入实际的文件内容
				zos.putNextEntry(ze);
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
			}
			zos.close();
		} else {
			throw new Exception("this folder isnot exist!");
		}
	}

	public static void releaseZipToFile(File sourceZip, String outFileName)
			throws IOException {
		org.apache.tools.zip.ZipFile zfile = new org.apache.tools.zip.ZipFile(sourceZip);
		Enumeration<?> zList = zfile.getEntries();
		org.apache.tools.zip.ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		while (zList.hasMoreElements()) {
			// 从ZipFile中得到一个ZipEntry
			ze = (org.apache.tools.zip.ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				continue;
			}
			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					getRealFileName(outFileName, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
		}
		zfile.close();

	}
	/**
	 * zip压缩功能测. 将目录下的所有文件连同子目录压缩到目录文件中
	 * 
	 * @param sourceDir
	 *            所要压缩的目录
	 * @param desFilename
	 *            压缩后的文件名(路径+文件名)
	 * @throws Exception
	 */
	public static void zip(String sourceDir ,String desFilename) throws Exception {
		createZip(sourceDir, desFilename);
	}
	/**
	 * zip压缩功能测. 将目录下的所有文件连同子目录压缩到目录文件中
	 * 
	 * @param sourceFileName
	 *            解压文件(路径+文件名)
	 * @param desDir
	 *            解压后的文件路径
	 * @throws Exception
	 */
	public static void unzip(String sourceFileName ,String desDir) throws Exception {
		releaseZipToFile(new File(sourceFileName),desDir);
	}

}