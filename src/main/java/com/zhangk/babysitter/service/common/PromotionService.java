package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.utils.common.Pagination;

public interface PromotionService {
	void addPromotion(PromotionInfo info);

	Pagination<PromotionInfo> getPagePromotionInfo(
			Pagination<PromotionInfo> page);

	PromotionInfo getPromotionInfo(String guid);

	PromotionInfo getPromotionInfo(long id);
}
