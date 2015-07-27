package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.FeedBack;
import com.zhangk.babysitter.utils.common.Pagination;

public interface FeedBackService {
	void addFeedBack(FeedBack feedback);

	void deleteFeedBack(long id);

	Pagination<FeedBack> getPageFeedBack(Pagination<FeedBack> page);
}
