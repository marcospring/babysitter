package com.zhangk.babysitter.service.common.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.CheckCode;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.utils.common.CheckCodeType;
import com.zhangk.babysitter.utils.common.CheckCodeUtil;

@Service
public class CheckCodeServiceImpl implements CheckCodeService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public CheckCode addCheckCode(String telephone, int type) {
		String hql = "from CheckCode t where t.ovld = true and mobilePhone=? and type=?";
		List<CheckCode> codes = dao.getListResultByHQL(CheckCode.class, hql,
				telephone, type);
		for (CheckCode checkCode : codes) {
			if (checkCode != null) {
				checkCode.setOvld(false);
				dao.update(checkCode);
			}
		}
		String code = CheckCodeUtil.code();
		CheckCode DBcode = CheckCode.getInstance();
		DBcode.setMobilePhone(telephone);
		DBcode.setType(type);
		DBcode.setCode(code);
		dao.add(DBcode);
		// 发短信
		CheckCodeUtil.sendMessage(telephone, code, CheckCodeType.getMsg(type),
				"5");
		//
		return DBcode;
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
