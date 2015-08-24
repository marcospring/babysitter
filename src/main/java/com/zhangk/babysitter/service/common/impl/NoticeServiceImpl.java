package com.zhangk.babysitter.service.common.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CompanyNoticeView;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public Pagination<CompanyNoticeView> getPaginationNotice(
			Pagination<CompanyNotice> page, String guid) {
		String hql = "from CompanyNotice c where c.ovld = true and c.babysitter.guid=? order by c.createDate desc";
		String countHql = "select count(c.id) from CompanyNotice c where c.ovld = true and c.babysitter.guid=?";
		Pagination<CompanyNotice> notices = dao.getPageResult(
				CompanyNotice.class, hql, page.getPageNo(), page.getPageSize(),
				guid);
		List<CompanyNotice> list = notices.getResult();
		List<CompanyNoticeView> views = new ArrayList<CompanyNoticeView>();
		for (CompanyNotice notice : list) {
			notice.setState(Constants.READ);
			notice.setUpdateDate(new Date());
			dao.update(notice);
			views.add(notice.view());
		}
		Pagination<CompanyNoticeView> pa = new Pagination<CompanyNoticeView>(
				views, notices.getPageNo(), notices.getPageSize());
		long count = dao.getSingleResultByHQL(Long.class, countHql, guid);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public PageResult readedNotice(String noticeGuid, PageResult res) {
		CompanyNotice notice = dao.getResultByGUID(CompanyNotice.class,
				noticeGuid);
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
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		CompanyNotice notice = CompanyNotice.getInstance();
		notice.setBabysitter(babysitter);
		notice.setMemo(memo);
		notice.setState(Constants.NON_READ);
		notice.setTitle(title);
		dao.add(notice);
	}

	public PageResult getNONReadNoticeCount(String guid, PageResult res) {
		String hql = "select count(c.id) from CompanyNotice c where c.ovld = true and c.babysitter.guid = ? and c.state = ?";
		Long count = dao.getSingleResultByHQL(Long.class, hql, guid,
				Constants.NON_READ);
		String promotionHql = "from PromotionInfo c where c.ovld = true and c.isCheck=?";
		List<PromotionInfo> list = dao.getListResultByHQL(PromotionInfo.class,
				promotionHql, Constants.PASS);
		long promoCount = 0;
		Calendar c = Calendar.getInstance();
		int now = c.get(Calendar.DAY_OF_MONTH);
		for (PromotionInfo promotionInfo : list) {
			c.setTime(promotionInfo.getCreateDate());
			int day = c.get(Calendar.DAY_OF_MONTH);
			if (now - day <= 7)
				promoCount++;
		}
		Map<String, Long> result = new HashMap<String, Long>();
		result.put("adviceCount", count == null ? 0 : count.longValue());
		result.put("promotionCount", promoCount);
		// 学习中心更新数量
		result.put("studyCount", 0l);
		res.put("result", result);
		return res;
	}

	@Transactional
	public ResultInfo addNotices(String title, String memo, String ids) {
		if (StringUtils.isEmpty(ids))
			return ResultInfo.INF_EMPTY;
		String[] idsStr = ids.split(",");
		if (idsStr == null || idsStr.length == 0)
			return ResultInfo.INF_EMPTY;
		for (String id : idsStr) {
			long idl = Long.valueOf(id);
			Babysitter babysitter = dao.getResultById(Babysitter.class, idl);
			CompanyNotice notice = CompanyNotice.getInstance();
			notice.setState(Constants.NON_READ);
			notice.setTitle(title);
			notice.setMemo(memo);
			notice.setBabysitter(babysitter);
			dao.add(notice);
		}
		return ResultInfo.SUCCESS;
	}

	public Pagination<CompanyNoticeView> getManageNotices(
			Pagination<CompanyNotice> notices, String name) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from CompanyNotice r where ovld = true ");

		if (!StringUtils.isEmpty(name)) {
			hql.append(" and r.babysitter.name like ? ");
			params.add("%" + name + "%");
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<CompanyNotice> p = dao.getPageResultObjectParams(
				CompanyNotice.class, hql.toString(), notices.getPageNo(),
				notices.getPageSize(), objParams);
		List<CompanyNotice> list = p.getResult();
		List<CompanyNoticeView> viewList = new ArrayList<CompanyNoticeView>();
		for (CompanyNotice notice : list) {
			CompanyNoticeView view = notice.view();
			viewList.add(view);
		}

		Pagination<CompanyNoticeView> pa = new Pagination<CompanyNoticeView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}
}
