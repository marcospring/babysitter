package com.zhangk.babysitter.service.exployer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Service
public class EmployerServiceImpl implements EmployerService {
	@Autowired
	private BaseDao dao;
	@Autowired
	private BabysitterService babysitterService;

	public List<Employer> ExployerList() {
		String hql = "from Employer r ";
		List<Employer> list = dao.getListResultByHQL(Employer.class, hql);
		return list;
	}

	public Pagination<Employer> getPageEmployerList(Pagination<Employer> page) {
		String hql = "from Employer r";
		String countHql = "select count(r.id) from Employer r";

		Pagination<Employer> p = dao.getPageResult(Employer.class, hql,
				page.getPageNo(), page.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addEmployer(Employer employer) {
		dao.add(employer);
	}

	@Transactional
	public void deleteEmployer(long id) {
		dao.delete(Employer.class, id);
	}

	@Transactional
	public void updateEmployer(Employer employer) {
		dao.update(employer);
	}

	public Employer getEmployer(long id) {
		return dao.getResultById(Employer.class, id);
	}

	public Employer getEmployerByMobile(String mobile) {
		String hql = "from Employer e where e.ovld = true and e.mobilePhone = ?";
		Employer employer = dao.getSingleResultByHQL(Employer.class, hql,
				mobile);
		return employer;
	}

	public List<BabysitterView> getRecommendBabysitter(String date, int page,
			String countyGuid) {
		List<BabysitterView> result = new ArrayList<BabysitterView>();
		// 计算档期起止时间
		Map<String, Date> dates = ExpectedDateCreate.getExpectedDate(date);
		// 获得今日推荐月嫂列表
		List<Babysitter> preResult = new ArrayList<Babysitter>();
		RecommendInfo info = babysitterService
				.getNewBabysitterRecommend(countyGuid);
		if (info != null) {
			List<Babysitter> recommendBabysitter = info.getBabysitters();
			for (Babysitter babysitter : recommendBabysitter) {
				if (ExpectedDateCreate.checkBabysitterOrder(babysitter, dates)) {
					preResult.add(babysitter);
				}
			}
		}
		// 判断请求次数，以及推荐月嫂个数
		if (page == 0)
			page = 1;
		int pageCount = preResult.size() % 5 == 0 ? preResult.size() / 5
				: preResult.size() / 5 + 1;
		int lastCount = preResult.size() % 5;
		if (page < pageCount) {
			int begin = (page - 1) * 5;
			int end = begin + 5;
			for (int i = begin; i < end; i++) {
				result.add(preResult.get(i).view());
			}

		} else {
			List<BabysitterView> views = babysitterService
					.getExpectedBabysitter(countyGuid, date);
			if (page == pageCount) {
				for (int i = preResult.size() - lastCount; i < preResult.size(); i++) {
					result.add(preResult.get(i).view());
				}
				int size = 5 - lastCount;
				for (int i = 0; i < size; i++) {
					result.add(views.get(i));
				}
			} else {
				int resultSize = views.size() - lastCount;
				int viewPageCount = resultSize % 5 == 0 ? resultSize / 5 == 0 ? 1
						: resultSize / 5
						: resultSize / 5 + 1;
				int viewPage = page - pageCount;
				if (viewPage > viewPageCount)
					viewPage = viewPageCount;
				if (viewPage <= viewPageCount) {
					int begin = (viewPage - 1) * 5 + lastCount;
					int end = viewPage == viewPageCount ? views.size()
							: begin + 5;
					for (int i = begin; i < end; i++) {
						result.add(views.get(i));
					}
				}
			}
		}
		return result;
	}
}
