package com.zhangk.babysitter.utils.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class UploadFileUtils {

	Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

	private HttpServletRequest request;
	private static UploadFileUtils instance = null;

	private UploadFileUtils() {
	}

	public static UploadFileUtils newInstance() {
		if (instance == null)
			instance = new UploadFileUtils();
		return instance;
	}

	public List<MultipartFile> getFiles() {
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					files.add(file);
				}
			}
		}
		return files;
	}

	public String getFileUrl(MultipartFile file, String baseUrl, String guid) {
		StringBuffer url = null;
		try {
			url = new StringBuffer();
			StringBuffer realPath = new StringBuffer(request
					.getServletContext().getRealPath(baseUrl));
			if (file != null) {
				// 取得当前上传文件的文件名称
				String myFileName = file.getOriginalFilename();
				// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
				if (myFileName.trim() != "") {
					// 获取文件后缀名
					String suffix = myFileName.substring(myFileName
							.lastIndexOf("."));
					// 定义上传路径
					url.append(baseUrl).append(guid).append(suffix);
					File p = new File(realPath.toString());
					if (!p.exists()) {
						p.mkdirs();
					}
					String pathUrl = realPath.append(File.separator)
							.append(guid).append(suffix).toString();
					File localFile = new File(pathUrl);
					file.transferTo(localFile);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url.toString();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
