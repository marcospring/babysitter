package com.zhangk.babysitter.service.common.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.service.common.PromotionService;
import com.zhangk.babysitter.utils.common.Pagination;

@Service
public class PromotinServiceImpl implements PromotionService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addPromotion(PromotionInfo info) {
		dao.add(info);
	}

	public Pagination<PromotionInfo> getPagePromotionInfo(
			Pagination<PromotionInfo> page) {
		String hql = "from PromotionInfo p";
		String countHql = "select count(p.id) from PromotionInfo p";

		Pagination<PromotionInfo> p = dao.getPageResult(PromotionInfo.class,
				hql, page.getPageNo(), page.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		p.setResultSize(count);
		return p;
	}

	public PromotionInfo getPromotionInfo(String guid) {
		return dao.getResultByGUID(PromotionInfo.class, guid);
	}

	public PromotionInfo getPromotionInfo(long id) {
		return dao.getResultById(PromotionInfo.class, id);
	}

}
