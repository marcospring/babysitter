package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.NumberRecord;

public interface NumberRecordService {
	NumberRecord getBabysitterNewNumber();

	NumberRecord getOrderNewNumber(String recordDate);

	String createOrderId();

	String getCardNo(County county);
}
