package com.zhangk.babysitter.service.common.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.CheckCode;
import com.zhangk.babysitter.service.common.CheckCodeService;

@Service
public class CheckCodeServiceImpl implements CheckCodeService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addCheckCode(CheckCode code) {
		dao.add(code);
	}

	@Transactional
	public boolean updateCheckCode(String mobilePhone, String code, int type) {
		String hql = "from CheckCode c where ovld = true c.mobilePhone = ? and type=?";
		CheckCode DBcode = dao.getSingleResultByHQL(CheckCode.class, hql,
				mobilePhone, type);
		if (code != null && DBcode.getCode().equals(code)) {
			DBcode.setOvld(false);
			dao.update(DBcode);
			return true;
		}
		return false;
	}
}
