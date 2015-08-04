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
		List<NumberRecord> listRecord = dao.getListResultByHQL(
				NumberRecord.class, hql, Constants.BABYSITTER_ID);
		if (listRecord == null || listRecord.size() == 0) {
			record = NumberRecord.getInstance();
			record.setNumber(11111);
			record.setType(Constants.BABYSITTER_ID);
			dao.add(record);
		} else {
			record = listRecord.get(0);
		}
		String number = String.valueOf(record.getNumber());

		return listRecord.get(0);
	}

	@Transactional
	public NumberRecord getOrderNewNumber(Date recordDate) {
		NumberRecord record = null;
		String hql = "from NumberRecord r where r.ovld = true and r.type =? and r.recordDate = ? order by r.number desc";
		List<NumberRecord> listRecord = dao.getListResultByHQL(
				NumberRecord.class, hql, Constants.BABYSITTER_ID, recordDate);
		if (listRecord == null || listRecord.size() == 0) {
			record = NumberRecord.getInstance();
			record.setRecordDate(recordDate);
			record.setNumber(88888);
			record.setType(Constants.ORDER_ID);
			dao.add(record);
		} else {
			record = listRecord.get(0);
		}

		return record;
	}

	@Transactional
	public void updateBabysitterNumber(String guid, long number) {
		NumberRecord record = dao.getResultByGUID(NumberRecord.class, guid);
		record.setNumber(number);
	}

	public void updateOrderNumber(String guid, long number) {
		// TODO Auto-generated method stub

	}

	private long getNumber(long number) {
		String numStr = String.valueOf(number);
		char nums[] = numStr.toCharArray();
		for (int i = 0; i < nums.length; i++) {
			char c = nums[i];
			long cnum = Character.valueOf(c);
			if (cnum == 4)
				cnum += 1;
			nums[i] = (char) cnum;
		}
		return Long.valueOf(nums.toString());
	}
}
