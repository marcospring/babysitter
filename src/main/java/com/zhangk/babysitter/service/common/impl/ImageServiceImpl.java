package com.zhangk.babysitter.service.common.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Image;
import com.zhangk.babysitter.service.common.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private BaseDao dao;

	@Transactional
	public void addImage(Image image) {
		dao.add(image);
	}

	public List<Image> imageList(String countyGuid) {
		String hql = "from Image m where m.topIndex = ? and m.county.guid = ?";
		List<Image> list = dao.getListResultByHQL(Image.class, hql, 1,
				countyGuid);
		return list;
	}

	public List<Image> allImageList() {
		String hql = "from Image m";
		List<Image> list = dao.getListResultByHQL(Image.class, hql);
		return list;
	}

	public Image getImage(String guid) {

		return dao.getResultByGUID(Image.class, guid);
	}

	public Image getImage(long id) {
		return dao.getResultById(Image.class, id);
	}

}
