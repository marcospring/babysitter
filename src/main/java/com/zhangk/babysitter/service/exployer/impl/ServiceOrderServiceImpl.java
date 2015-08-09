package com.zhangk.babysitter.service.exployer.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

	@Autowired
	private BaseDao dao;
	@Autowired
	private CheckCodeService codeService;
	@Autowired
	private EmployerService employerService;

	@Transactional
	public ResultInfo addServiceOrder(String date, String price,
			String countyGuid, String address, String name, String mobile,
			String checkCode) {
		try {
			// boolean flag = codeService.updateCheckCode(mobile, checkCode,
			// CheckCodeService.PUBLISH_ORDER);
			// if (!flag) {
			// throw new CheckErrorException();
			// }
			Employer employer = employerService.getEmployerByMobile(mobile);
			// County county = dao.getResultByGUID(County.class, countyGuid);
			if (employer == null) {
				employer = Employer.getInstance();
				employer.setMobilePhone(mobile.replace(" ", ""));
				// employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(name);
				employerService.addEmployer(employer);
			}
			ServiceOrder order = ServiceOrder.getInstance();
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price));
			order.setAddress(address);
			// order.setCounty(county);
			order.setMobilePhone(mobile.replace(" ", ""));
			Map<String, Date> expectedDate = ExpectedDateCreate
					.getExpectedDate(date);
			order.setServiceBeginDate(expectedDate
					.get(ExpectedDateCreate.BEGIN_DATE));
			order.setServiceEndDate(expectedDate
					.get(ExpectedDateCreate.END_DATE));
			dao.add(order);
		} catch (CheckErrorException e) {
			return ResultInfo.CHECK_CODE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultInfo.SUCCESS;

	}

	@Transactional
	public ResultInfo addBabysitterOrderEvaluate(String employGuid,
			String orderGuid, String babysitterGuid, String msg, String score) {
		// Employer employer = dao.getResultByGUID(Employer.class, employGuid);
		// if (employer == null)
		// return ResultInfo.EMPLOYER_NULL;
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
				orderGuid);
		if (order == null)
			return ResultInfo.BABYSITTER_ORDER_NULL;
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		if (babysitter == null)
			return ResultInfo.BABYSITTER_NULL;
		order.setScore(Integer.valueOf(score));
		order.setEvaluation(msg);
		order.setState(Constants.ORDER_OVER);
		dao.update(order);

		// BabysitterEvaluate evaluate = BabysitterEvaluate.getInstance();
		// evaluate.setBabysitter(babysitter);
		// evaluate.setEmployer(employer);
		// evaluate.setOrder(order);
		// evaluate.setMsg(msg);
		// evaluate.setScore(Integer.valueOf(score));
		// dao.add(evaluate);
		int count = 0;
		List<BabysitterOrder> orders = babysitter.getOrders();
		for (BabysitterOrder o : orders) {
			count += o.getScore();
		}
		int avg = count / orders.size();
		babysitter.setScore(avg);
		dao.update(babysitter);
		return ResultInfo.SUCCESS;
	}

	public List<ServiceOrder> orderList(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return null;
		}
		String hql = "from ServiceOrder t where t.ovld = true and t.mobilePhone=?";
		List<ServiceOrder> orders = dao.getListResultByHQL(ServiceOrder.class,
				hql, mobile);
		return orders;
	}
}
