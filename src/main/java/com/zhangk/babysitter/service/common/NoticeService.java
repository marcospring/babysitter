package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.utils.common.Pagination;

public interface NoticeService {
	Pagination<CompanyNotice> getPaginationNotice(String guid);

	void readedNotice(String noticeGuid);
}
