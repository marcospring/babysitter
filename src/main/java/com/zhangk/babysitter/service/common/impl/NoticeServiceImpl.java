package com.zhangk.babysitter.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.utils.common.Pagination;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private BaseDao dao;

	public Pagination<CompanyNotice> getPaginationNotice(String guid) {
		String hql = "from CompanyNotice c where c.ovld = true and ";
		return null;
	}

	public void readedNotice(String noticeGuid) {
		// TODO Auto-generated method stub

	}

}
