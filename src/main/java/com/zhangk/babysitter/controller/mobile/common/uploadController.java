package com.zhangk.babysitter.controller.mobile.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.GUIDCreator;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Controller
@RequestMapping("/file")
public class uploadController extends BaseController {

	private final int HEAD = 1;
	private final int LIFE = 2;

	@Autowired
	private BabysitterService babysitterService;

	@ResponseBody
	@RequestMapping("/image/head")
	public PageResult uploadHead(HttpServletRequest request, String guid)
			throws IllegalStateException, IOException {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		Babysitter babysitter = babysitterService.getBabysitter(guid);
		if (babysitter != null) {
			List<String> urls = transferFile(request, guid, HEAD);
			babysitter.setHeadUrl(urls.get(0));
			babysitterService.updateBabysitter(babysitter);
		} else {
			return getErrRes(ResultInfo.BABYSITTER_NULL);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/image/life")
	public PageResult uploadLife(HttpServletRequest request, String guid)
			throws IllegalStateException, IOException {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		Babysitter babysitter = babysitterService.getBabysitter(guid);
		if (babysitter != null) {
			List<String> urls = transferFile(request, guid, LIFE);
			for (String url : urls) {
				BabysitterImage image = BabysitterImage.getInstance();
				image.setUrl(url);
				image.setBabysitter(babysitter);
				babysitterService.addBabysitterImage(image);
			}
		} else {
			return getErrRes(ResultInfo.BABYSITTER_NULL);
		}
		return res;
	}

	private List<String> transferFile(HttpServletRequest request, String guid,
			int type) throws IOException {
		List<String> urls = new ArrayList<String>();
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
						// 定义上传路径
						StringBuffer path = null;
						if (type == HEAD) {
							path = new StringBuffer(request.getServletContext()
									.getRealPath("/resource/user/head/"));
						} else if (type == LIFE) {
							path = new StringBuffer(request.getServletContext()
									.getRealPath("/resource/user/life/"));
						}
						File p = new File(path.toString());
						if (!p.exists()) {
							p.mkdirs();
						}
						String fileName = makeFileName(myFileName, guid, HEAD);
						path.append("/").append(fileName);
						File localFile = new File(path.toString());
						file.transferTo(localFile);
						urls.add(path.toString());
					}
				}
			}
		}
		return urls;
	}

	private String makeFileName(String name, String guid, int type) {
		String suffix = name.substring(name.lastIndexOf("."));
		StringBuffer fileName = new StringBuffer();
		if (type == HEAD) {
			fileName.append(guid).append(".").append(suffix);
		} else if (type == LIFE) {
			fileName.append(GUIDCreator.GUID()).append(".").append(suffix);
		}
		return fileName.toString();
	}
}
