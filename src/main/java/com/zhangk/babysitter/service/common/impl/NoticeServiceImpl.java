package com.zhangk.babysitter.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CompanyNoticeView;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private BaseDao dao;

	public Pagination<CompanyNoticeView> getPaginationNotice(Pagination<CompanyNotice> page, String guid) {
		String hql = "from CompanyNotice c where c.ovld = true and c.babysitter.guid=? order by c.createDate desc";
		String countHql = "select count(c.id) from CompanyNotice c where c.ovld = true and c.babysitter.guid=?";
		Pagination<CompanyNotice> notices = dao.getPageResult(CompanyNotice.class, hql, page.getPageNo(), page.getPageSize(), guid);
		List<CompanyNotice> list = notices.getResult();
		List<CompanyNoticeView> views = new ArrayList<CompanyNoticeView>();
		for (CompanyNotice notice : list) {
			views.add(notice.view());
		}
		Pagination<CompanyNoticeView> pa = new Pagination<CompanyNoticeView>(views, notices.getPageNo(), notices.getPageSize());
		long count = dao.getSingleResultByHQL(Long.class, countHql, guid);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public PageResult readedNotice(String noticeGuid, PageResult res) {
		CompanyNotice notice = dao.getResultByGUID(CompanyNotice.class, noticeGuid);
		if (notice == null) {
			res.setResult(ResultInfo.NOTICE_NULL);
			return res;
		}
		notice.setState(Constants.READ);
		dao.update(notice);
		return res;
	}

	@Transactional
	public void addNotice(String title, String memo, String babysitterGuid) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, babysitterGuid);
		CompanyNotice notice = CompanyNotice.getInstance();
		notice.setBabysitter(babysitter);
		notice.setMemo(memo);
		notice.setState(Constants.NON_READ);
		notice.setTitle(title);
		dao.add(notice);
	}

	public PageResult getNONReadNoticeCount(String guid, PageResult res) {
		String hql = "select count(c.id) from CompanyNotice c where c.ovld = true and c.babysitter.guid = ? and c.state = ?";
		Long count = dao.getSingleResultByHQL(Long.class, hql, guid, Constants.NON_READ);
		res.put("result", count == null ? 0 : count.longValue());
		return res;
	}
}
