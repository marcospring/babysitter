package com.zhangk.babysitter.service.babysitter;

import java.util.List;

import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.BabysitterView;

public interface BabysitterService {
	String level = "level";
	String orderCount = "orderCount";
	String score = "score";

	List<Babysitter> BabysitterList();

	Pagination<Babysitter> getPageBabysitterList(Pagination<Babysitter> page);

	void addBabysitter(Babysitter babysitter);

	void deleteBabysitter(long id);

	void updateBabysitter(Babysitter babysitter);

	Babysitter getBabysitter(long id);

	Pagination<BabysitterOrder> getPageOrderList(
			Pagination<BabysitterOrder> page, String name);

	void addOrder(BabysitterOrder order);

	Pagination<BabysitterView> getMobileBabysitters(String countyGuid,
			Pagination<BabysitterView> page, String name, String orderStr);

	BabysitterView getBabysitterView(String guid);

}
