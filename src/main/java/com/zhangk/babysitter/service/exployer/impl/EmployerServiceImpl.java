package com.zhangk.babysitter.service.exployer.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.PanicBuyingOrder;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.EmployerView;

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
			String countyGuid, String orderGuid) {
		List<BabysitterView> result = new ArrayList<BabysitterView>();
		// 计算档期起止时间
		// Map<String, Date> dates = ExpectedDateCreate.getExpectedDate(date);
		// // 获得今日推荐月嫂列表
		// List<Babysitter> preResult = new ArrayList<Babysitter>();
		// RecommendInfo info =
		// babysitterService.getNewBabysitterRecommend(countyGuid);
		// if (info != null) {
		// List<Babysitter> recommendBabysitter = info.getBabysitters();
		// for (Babysitter babysitter : recommendBabysitter) {
		// if (ExpectedDateCreate.checkBabysitterOrder(babysitter, dates)) {
		// preResult.add(babysitter);
		// }
		// }
		// }
		// 判断请求次数，以及推荐月嫂个数
		// if (page == 0)
		// page = 1;
		// int pageCount = preResult.size() % 5 == 0 ? preResult.size() / 5 :
		// preResult.size() / 5 + 1;
		// int lastCount = preResult.size() % 5;
		// if (page < pageCount) {
		// int begin = (page - 1) * 5;
		// int end = begin + 5;
		// for (int i = begin; i < end; i++) {
		// result.add(preResult.get(i).view());
		// }
		//
		// } else {
		// 获取抢单的月嫂
		String hql = "from PanicBuyingOrder t where t.ovld = true and serviceOrder.guid = ? order by t.createDate desc";
		List<BabysitterView> views = new ArrayList<BabysitterView>();
		Pagination<PanicBuyingOrder> pages = dao.getPageResult(
				PanicBuyingOrder.class, hql, page, 5, orderGuid);
		List<PanicBuyingOrder> list = pages.getResult();
		if (list != null && list.size() > 0) {
			for (PanicBuyingOrder panicBuyingOrder : list) {
				views.add(panicBuyingOrder.getBabysitter().view());
			}
		}
		// if (page == pageCount) {
		// for (int i = preResult.size() - lastCount; i < preResult.size(); i++)
		// {
		// result.add(preResult.get(i).view());
		// }
		// int size = 5 - lastCount;
		// for (int i = 0; i < size; i++) {
		// result.add(views.get(i));
		// }
		// } else {
		// int resultSize = views.size() - lastCount;
		// int viewPageCount = resultSize % 5 == 0 ? resultSize / 5 == 0 ? 1
		// : resultSize / 5 : resultSize / 5 + 1;
		// int viewPage = page - pageCount;
		// if (viewPage > viewPageCount)
		// viewPage = viewPageCount;
		// if (viewPage <= viewPageCount) {
		// int begin = (viewPage - 1) * 5 + lastCount;
		// int end = viewPage == viewPageCount ? views.size() : begin + 5;
		// for (int i = begin; i < end; i++) {
		// result.add(views.get(i));
		// }
		// }
		// }
		// }
		return result;
	}

	public Pagination<EmployerView> getPageEmployerListForOrder(
			Pagination<Employer> page, String employerName,
			String employerTelephone) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from Employer r where ovld = true ");
		if (!StringUtils.isEmpty(employerTelephone)) {
			hql.append(" and r.mobilePhone = ? ");
			params.add(employerTelephone);
		}
		if (!StringUtils.isEmpty(employerName)) {
			hql.append(" and r.username like ? ");
			params.add("%" + employerName + "%");
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<Employer> p = dao
				.getPageResultObjectParams(Employer.class, hql.toString(),
						page.getPageNo(), page.getPageSize(), objParams);
		List<Employer> list = p.getResult();
		List<EmployerView> viewList = new ArrayList<EmployerView>();
		for (Employer order : list) {
			viewList.add(order.view());
		}

		Pagination<EmployerView> pa = new Pagination<EmployerView>(viewList,
				p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	public Employer getEmployer(String guid) {
		return dao.getResultByGUID(Employer.class, guid);
	}

	public PageResult search(String countyGuid, String babysitterName,
			PageResult result) {
		String hql = "from Babysitter t where t.county.guid=? and t.name like ?";
		List<Babysitter> list = dao.getListResultByHQL(Babysitter.class, hql,
				countyGuid, "%" + babysitterName + "%");
		List<BabysitterView> views = new ArrayList<BabysitterView>();
		for (Babysitter babysitter : list) {
			views.add(babysitter.view());
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", views);
		return result;
	}
}
