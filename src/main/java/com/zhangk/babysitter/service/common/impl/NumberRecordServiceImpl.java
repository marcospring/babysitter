package com.zhangk.babysitter.service.common.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.NumberRecord;
import com.zhangk.babysitter.service.common.NumberRecordService;
import com.zhangk.babysitter.utils.common.Constants;

@Service
public class NumberRecordServiceImpl implements NumberRecordService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public NumberRecord getBabysitterNewNumber() {
		NumberRecord record = null;
		String hql = "from NumberRecord r where r.ovld = true and type =? order by r.number desc";
		List<NumberRecord> listRecord = dao.getListResultByHQL(NumberRecord.class, hql, Constants.BABYSITTER_ID);
		if (listRecord == null || listRecord.size() == 0) {
			record = NumberRecord.getInstance();
			record.setNumber(11111);
			record.setType(Constants.BABYSITTER_ID);
			dao.add(record);
		} else {
			record = listRecord.get(0);
		}
		// 获取数据当前值
		long number = record.getNumber();
		// 当前值加1
		number += 1;
		// 去掉4，如果含有4则在4的位置变为5
		number = getNumber(number);
		// 更新record数值
		record.setNumber(number);
		dao.update(record);
		return record;
	}

	@Transactional
	public NumberRecord getOrderNewNumber(Date recordDate) {
		NumberRecord record = null;
		String hql = "from NumberRecord r where r.ovld = true and r.type =? and r.recordDate = ? order by r.number desc";
		List<NumberRecord> listRecord = dao.getListResultByHQL(NumberRecord.class, hql, Constants.BABYSITTER_ID, recordDate);
		if (listRecord == null || listRecord.size() == 0) {
			record = NumberRecord.getInstance();
			record.setRecordDate(recordDate);
			record.setNumber(88888);
			record.setType(Constants.ORDER_ID);
			dao.add(record);
		} else {
			record = listRecord.get(0);
		}
		// 获取数据当前值
		long number = record.getNumber();
		// 当前值加1
		number += 1;
		// 去掉4，如果含有4则在4的位置变为5
		number = getNumber(number);
		// 更新record数值
		record.setNumber(number);
		dao.update(record);
		return record;
	}

	private long getNumber(long number) {
		String numStr = String.valueOf(number);
		char chars[] = numStr.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String s = String.valueOf(chars[i]);
			if ("4".equals(s)) {
				int n = Integer.valueOf(s);
				n += 1;
				s = String.valueOf(n);
			}
			chars[i] = s.toCharArray()[0];
		}
		return Long.valueOf(new String(chars));
	}
}
