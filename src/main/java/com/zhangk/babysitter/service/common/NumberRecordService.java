package com.zhangk.babysitter.service.common;

import java.util.Date;

import com.zhangk.babysitter.entity.NumberRecord;

public interface NumberRecordService {
	NumberRecord getBabysitterNewNumber();

	NumberRecord getOrderNewNumber(Date recordDate);

	void updateBabysitterNumber(String guid, long number);

	void updateOrderNumber(String guid, long number);
}
