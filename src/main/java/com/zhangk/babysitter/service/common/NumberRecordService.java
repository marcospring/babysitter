package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.NumberRecord;

public interface NumberRecordService {
	NumberRecord getBabysitterNewNumber();

	NumberRecord getOrderNewNumber(int recordDate);

}
