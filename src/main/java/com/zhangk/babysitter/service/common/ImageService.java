package com.zhangk.babysitter.service.common;

import java.util.List;

import com.zhangk.babysitter.entity.Image;

public interface ImageService {
	void addImage(Image image);

	List<Image> imageList();

	List<Image> allImageList();

	Image getImage(String guid);

	Image getImage(long id);
}
