package com.zhangk.babysitter.service.common.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.FeedBack;
import com.zhangk.babysitter.service.common.FeedBackService;
import com.zhangk.babysitter.utils.common.Pagination;

@Service
public class FeedBackServiceImpl implements FeedBackService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addFeedBack(FeedBack feedback) {
		dao.add(feedback);
	}

	public void deleteFeedBack(long id) {
		dao.delete(FeedBack.class, id);
	}

	public Pagination<FeedBack> getPageFeedBack(Pagination<FeedBack> page) {
		String hql = "from FeedBack";
		String hqlCount = "select count(f.id) from FeedBack f";
		Pagination<FeedBack> p = dao.getPageResult(FeedBack.class, hql,
				page.getPageNo(), page.getPageSize());
		long count = dao.getSingleResultByHQL(Long.class, hqlCount);
		p.setResultSize(count);
		return p;
	}

}
