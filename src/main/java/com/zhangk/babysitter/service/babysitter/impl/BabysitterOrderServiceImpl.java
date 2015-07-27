package com.zhangk.babysitter.service.babysitter.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.exception.BabysitterNullException;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.babysitter.BabysitterOrderService;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Service
public class BabysitterOrderServiceImpl implements BabysitterOrderService {

	@Autowired
	private BaseDao dao;
	@Autowired
	private CheckCodeService codeService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private BabysitterService babysitterService;

	public ResultInfo addBabysitterOrder(String babysitterGuid,
			String countyGuid, String address, String name, String mobile,
			String date, String price) {
		try {
			// boolean flag = codeService.updateCheckCode(mobile, checkCode,
			// CheckCodeService.PUBLISH_ORDER);
			// if (!flag) {
			// throw new CheckErrorException();
			// }
			Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
					babysitterGuid);
			if (babysitter == null) {
				throw new BabysitterNullException();
			}
			Employer employer = employerService.getEmployerByMobile(mobile);
			if (employer == null) {
				employer = Employer.getInstance();
				employer.setMobilePhone(mobile);
				County county = dao.getResultByGUID(County.class, countyGuid);
				employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(name);
				employerService.addEmployer(employer);
			}
			BabysitterOrder order = BabysitterOrder.getInstance();
			order.setBabysitter(babysitter);
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price));
			Map<String, Date> expectedDate = ExpectedDateCreate
					.getExpectedDate(date);
			order.setServiceBeginDate(expectedDate
					.get(ExpectedDateCreate.BEGIN_DATE));
			order.setServiceEndDate(expectedDate
					.get(ExpectedDateCreate.END_DATE));
			order.setState(Constants.NEW_ORDER);
			dao.add(order);
		} catch (BabysitterNullException e) {
			return ResultInfo.BABYSITTER_NULL;
		} catch (CheckErrorException e) {
			return ResultInfo.CHECK_CODE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultInfo.SUCCESS;
	}

	public int updateBabysitterOrder(String orderGuid, int state) {
		try {
			BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
					orderGuid);
			if (order == null) {
				return ResultInfo.BABYSITTER_ORDER_NULL.getCode();
			}
			order.setState(state);
			dao.update(order);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResultInfo.BAD_REQUEST.getCode();
		}
		return 0;
	}

}
