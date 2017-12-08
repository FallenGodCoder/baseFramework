package com.tc.common;

import com.alibaba.fastjson.JSON;
import com.tc.common.utils.DateUtils;
import com.tc.common.utils.PropertyTools;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class UploadController extends BaseController {

	@RequestMapping(value = "/multiUpload")
	@ResponseBody
	public String multiUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile[] files)
			throws Exception {
		StringBuilder result = new StringBuilder("");
		File path = getTempPath(request);

		if (!path.exists()) {
			path.mkdirs();
		}
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile item = files[i];
				result.append(uploadFile(request, item, path));
			}
		}
		Response res = new Response();
		try {
			PrintWriter writer = response.getWriter();
			res.setState(Response.STATE_SUCCESS);
			res.setResult(result.toString());
			writer.write(JSON.toJSONString(res));
			writer.flush();
		} catch (IOException e) {
			logger.error("error get printWriter", e);
		}
		return null;
	}

	@RequestMapping(value = "/singleUpload")
	public String singleUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		File path = getTempPath(request);
		if (!path.exists()) {
			path.mkdirs();
		}
		Response res = new Response();
		try {
			PrintWriter writer = response.getWriter();
			res.setState(Response.STATE_SUCCESS);
			res.setResult(uploadFile(request, file, path));
			writer.write(JSON.toJSONString(res));
			writer.flush();
		} catch (IOException e) {
			logger.error("error get printWriter", e);
		}
		return null;
	}

	public abstract String read(HttpServletRequest request, InputStream in, String fileName) throws Exception;

	private File getTempPath(HttpServletRequest request) {
		String uploadPath = PropertyTools.getValue("upload.path");
		String path = request.getSession().getServletContext().getRealPath(uploadPath);
		File file = new File(new File(path), DateUtils.getNowDate("yyyyMMdd"));
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	private String uploadFile(HttpServletRequest request, MultipartFile file, File path) throws Exception {
		String result = "";
		String itemName = file.getOriginalFilename();
		int start = itemName.lastIndexOf("\\");
		String tmpFilename = DateUtils.getNowDate("yyyyMMddHHmmssSSS") + "_"
				+ itemName.substring(start + 1);
		if (!tmpFilename.endsWith("xls") && !tmpFilename.endsWith("xlsx")) {
			throw new Exception("文件格式不正确!");
		}
		InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(new File(path, tmpFilename));
		int length = 0;
		byte[] buf = new byte[1024];
		while ((length = in.read(buf)) != -1) {
			out.write(buf, 0, length);
		}
		result = read(request, new FileInputStream(new File(path, tmpFilename)), itemName);
		in.close();
		out.close();
		return result;
	}
}
