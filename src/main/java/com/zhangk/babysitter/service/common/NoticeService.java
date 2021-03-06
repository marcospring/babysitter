package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CompanyNoticeView;

public interface NoticeService {

	PageResult getNONReadNoticeCount(String guid, PageResult res);

	Pagination<CompanyNoticeView> getPaginationNotice(
			Pagination<CompanyNotice> page, String guid);

	PageResult readedNotice(String noticeGuid, PageResult res);

	void addNotice(String title, String memo, String babysitterGuid);

	ResultInfo addNotices(String title, String memo, String ids);

	Pagination<CompanyNoticeView> getManageNotices(
			Pagination<CompanyNotice> notices, String name);
}
