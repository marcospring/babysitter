package com.zhangk.babysitter.service.common;

import java.util.List;

import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.PromotionView;

public interface PromotionService {
	void addPromotion(PromotionInfo info);

	Pagination<PromotionInfo> getPagePromotionInfo(
			Pagination<PromotionInfo> page);

	PromotionInfo getPromotionInfo(String guid);

	PromotionInfo getPromotionInfo(long id);

	List<PromotionView> getAllPromotion();
}
