package com.zhangk.babysitter.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.service.common.PromotionService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.PromotionView;

@Service
public class PromotinServiceImpl implements PromotionService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addPromotion(PromotionInfo info) {
		dao.add(info);
	}

	public Pagination<PromotionInfo> getPagePromotionInfo(Pagination<PromotionInfo> page) {
		String hql = "from PromotionInfo p where ovld = true and isCheck = ?";
		String countHql = "select count(p.id) from PromotionInfo p where ovld = true and isCheck = ?";

		Pagination<PromotionInfo> p = dao.getPageResult(PromotionInfo.class, hql, page.getPageNo(), page.getPageSize(), Constants.PASS);
		Long count = dao.getSingleResultByHQL(Long.class, countHql, Constants.PASS);
		p.setResultSize(count);
		return p;
	}

	public PromotionInfo getPromotionInfo(String guid) {
		return dao.getResultByGUID(PromotionInfo.class, guid);
	}

	public PromotionInfo getPromotionInfo(long id) {
		return dao.getResultById(PromotionInfo.class, id);
	}

	public List<PromotionView> getAllPromotion() {
		List<PromotionView> result = new ArrayList<PromotionView>();
		String hql = "from PromotionInfo where ovld = true and isCheck =?";
		List<PromotionInfo> preResult = dao.getListResultByHQL(PromotionInfo.class, hql, Constants.PASS);
		for (PromotionInfo info : preResult) {
			result.add(info.view());
		}
		return result;
	}

}
