package com.zhangk.babysitter.service.exployer.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterEvaluate;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
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
			County county = dao.getResultByGUID(County.class, countyGuid);
			if (employer == null) {
				employer = Employer.getInstance();
				employer.setMobilePhone(mobile);
				employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(name);
				employerService.addEmployer(employer);
			}
			ServiceOrder order = ServiceOrder.getInstance();
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price));
			order.setAddress(address);
			order.setCounty(county);
			order.setMobilePhone(mobile);
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
		Employer employer = dao.getResultByGUID(Employer.class, employGuid);
		if (employer == null)
			return ResultInfo.EMPLOYER_NULL;
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
				orderGuid);
		if (order == null)
			return ResultInfo.BABYSITTER_ORDER_NULL;
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		if (babysitter == null)
			return ResultInfo.BABYSITTER_NULL;
		BabysitterEvaluate evaluate = BabysitterEvaluate.getInstance();
		evaluate.setBabysitter(babysitter);
		evaluate.setEmployer(employer);
		evaluate.setOrder(order);
		evaluate.setMsg(msg);
		evaluate.setScore(Integer.valueOf(score));
		dao.add(evaluate);
		int count = 0;
		List<BabysitterEvaluate> evas = babysitter.getEvaluates();
		for (BabysitterEvaluate babysitterEvaluate : evas) {
			count += babysitterEvaluate.getScore();
		}
		int avg = count / evas.size();
		babysitter.setScore(avg);
		dao.update(babysitter);
		return ResultInfo.SUCCESS;
	}
}
