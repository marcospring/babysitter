package com.zhangk.babysitter.controller.mobile.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.zhangk.babysitter.utils.common.PropertiesUtils;

/**
 * Servlet implementation class UpoadServlet
 */
public class UpoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String TEMP_DIR = PropertiesUtils.getString("dir.temp",
			"D://temp");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpoadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		File tempDir = new File(TEMP_DIR);
		// DiskFileItemFactory类作用为将请求消息是挺中的每一个项目封装成单独的DiskFileItem对象。
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置是否将上传文件以临时文件的形式保存在磁盘的临界值
		factory.setSizeThreshold(30 * 1024);
		// 设置缓存路径
		factory.setRepository(tempDir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(3 * 1024 * 1024 * 1024L);
		upload.setSizeMax(3 * 1024 * 1024 * 1024L);
		upload.setHeaderEncoding("UTF-8");
		// List<FileItem> fileItems = upload.parseRequest(request);
		// for (FileItem item : fileItems) {
		// if (!item.isFormField()) {
		// // 获取文件真实名称
		// String realName = item.getName();
		// // 生成唯一文件名称
		// String suffix = realName.substring(realName.lastIndexOf("."))
		// .toLowerCase();
		// StringBuffer photoName = new StringBuffer();
		// photoName.append(memberCode).append(suffix);
		// url.append("D://pic").append("/").append("2014").append("/")
		// .append("03").append("/").append("28");
		// // 处理文件上传路径，如果没有该路径创建路径并授权
		// File fileDir = new File(url.toString());
		// if (!fileDir.exists()) {
		// fileDir.mkdirs();
		// // 授权操作chmod 777
		//
		// RunCommendUtil.chmod(url.toString(), "777");
		// }
		// // 在url路径下新建一个名字为photoName的文件
		// File photoFile = new File(url.toString(), photoName.toString());
		// // 将item写入文件
		// item.write(photoFile);
		// }
		// }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

}
