package com.zhangk.babysitter.service.babysitter;

import com.zhangk.babysitter.utils.common.ResultInfo;

public interface BabysitterOrderService {

	ResultInfo addBabysitterOrder(String babysitterGuid, String countyGuid,
			String address, String name, String mobile, String date,
			String price);

	int updateBabysitterOrder(String orderGuid, int state);
}
