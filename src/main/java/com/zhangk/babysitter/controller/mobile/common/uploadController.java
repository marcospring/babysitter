package com.zhangk.babysitter.controller.mobile.common;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zhangk.babysitter.controller.web.BaseController;

@Controller
@RequestMapping("/file")
public class uploadController extends BaseController {

	private final int HEAD = 1;
	private final int LIFE = 2;

	@ResponseBody
	@RequestMapping("/image")
	public PageResult uploadHead(HttpServletRequest request,
			HttpServletResponse response, int type, String guid)
			throws IllegalStateException, IOException {
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
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						// 重命名上传后的文件名
						String fileName = "demoUpload"
								+ file.getOriginalFilename();
						// 定义上传路径
						StringBuffer path = null;
						if (type == HEAD) {
							path = new StringBuffer(request.getServletContext()
									.getRealPath("/resource/user/head/"));
							File p = new File(path.toString());
							if (!p.exists()) {
								p.mkdirs();
							}
							path.append("/").append(fileName);
						} else if (type == LIFE) {
							path = new StringBuffer(request.getServletContext()
									.getRealPath("/resource/user/life/"));
							File p = new File(path.toString());
							if (!p.exists()) {
								p.mkdirs();
							}
							path.append("/").append(fileName);
						}

						File localFile = new File(path.toString());
						file.transferTo(localFile);
					}
				}
			}
		}
		return res;
	}

	private String makeFileName(String name) {
		return null;
	}
}
