package com.zhangk.babysitter.service.babysitter.impl;

import java.util.ArrayList;
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
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.exception.BabysitterNullException;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.babysitter.BabysitterOrderService;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.common.NumberRecordService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterOrderView;

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
	@Autowired
	private NumberRecordService recordService;

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

	public Pagination<BabysitterOrderView> manageOrderList(
			Pagination<BabysitterOrder> page, String babysitterGuid,
			String babysitterName, String employerName,
			String employerTelephone, String state) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from BabysitterOrder r where ovld = true ");
		if (!StringUtils.isEmpty(babysitterGuid)) {
			hql.append(" and r.babysitter.guid = ? ");
			params.add(babysitterGuid);
		}
		if (!StringUtils.isEmpty(babysitterName)) {
			hql.append(" and r.babysitter.name like ? ");
			params.add("%" + babysitterName + "%");
		}
		if (!StringUtils.isEmpty(employerName)) {
			hql.append(" and r.employerName like ? ");
			params.add("%" + employerName + "%");
		}
		if (!StringUtils.isEmpty(employerTelephone)) {
			hql.append(" and r.employerTelephone = ? ");
			params.add(employerTelephone);
		}
		if (!StringUtils.isEmpty(state)) {
			hql.append(" and r.state = ? ");
			params.add(Integer.valueOf(state));
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<BabysitterOrder> p = dao.getPageResultObjectParams(
				BabysitterOrder.class, hql.toString(), page.getPageNo(),
				page.getPageSize(), objParams);
		List<BabysitterOrder> list = p.getResult();
		List<BabysitterOrderView> viewList = new ArrayList<BabysitterOrderView>();
		for (BabysitterOrder order : list) {
			viewList.add(order.view());
		}

		Pagination<BabysitterOrderView> pa = new Pagination<BabysitterOrderView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public ResultInfo manageAddOrder(String guid, String beginDate,
			String endDate, String price, String address, String employerName,
			String telephone) {
		try {
			// boolean flag = codeService.updateCheckCode(mobile, checkCode,
			// CheckCodeService.PUBLISH_ORDER);
			// if (!flag) {
			// throw new CheckErrorException();
			// }
			Employer employer = employerService.getEmployerByMobile(telephone);
			// County county = dao.getResultByGUID(County.class, countyGuid);
			if (employer == null) {
				employer = Employer.getInstance();
				employer.setMobilePhone(telephone.replace(" ", ""));
				// employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(employerName);
				employerService.addEmployer(employer);
			}
			Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
			if (babysitter == null)
				return ResultInfo.BABYSITTER_NULL;

			BabysitterOrder order = BabysitterOrder.getInstance();
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price));
			order.setBabysitter(babysitter);
			order.setState(Constants.NEW_ORDER);
			order.setOrderId(recordService.createOrderId());
			order.setServiceBeginDate(ExpectedDateCreate.parseDate(beginDate));
			order.setServiceEndDate(ExpectedDateCreate.parseDate(endDate));
			order.setEmployerAddress(address);
			order.setEmployerName(employerName);
			order.setEmployerTelephone(telephone.replace(" ", ""));
			dao.add(order);
		} catch (CheckErrorException e) {
			return ResultInfo.CHECK_CODE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultInfo.SUCCESS;
	}

	@Transactional
	public void deleteOrder(String ids) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			BabysitterOrder order = dao.getResultById(BabysitterOrder.class,
					idl);
			order.setOvld(false);
			dao.update(order);
		}
	}

	public BabysitterOrder getOrder(String id) {
		return dao.getResultById(BabysitterOrder.class, Long.parseLong(id));
	}

	@Transactional
	public ResultInfo manageEditOrder(String id, String employerName,
			String employerTelephone, String employerAddress, String price,
			String beginDate, String endDate, int state) {
		try {
			long idl = Long.valueOf(id);
			BabysitterOrder order = dao.getResultById(BabysitterOrder.class,
					idl);
			if (order == null)
				return ResultInfo.BABYSITTER_ORDER_NULL;
			if (!StringUtils.isEmpty(employerName))
				order.setEmployerName(employerName);
			if (!StringUtils.isEmpty(employerTelephone))
				order.setEmployerTelephone(employerTelephone);
			if (!StringUtils.isEmpty(employerAddress))
				order.setEmployerAddress(employerAddress);
			if (!StringUtils.isEmpty(price))
				order.setOrderPrice(Long.parseLong(price));
			if (!StringUtils.isEmpty(beginDate))
				order.setServiceBeginDate(ExpectedDateCreate
						.parseDate(beginDate));
			if (!StringUtils.isEmpty(endDate))
				order.setServiceEndDate(ExpectedDateCreate.parseDate(endDate));
			if (state != 0)
				order.setState(state);

			order.setUpdateDate(new Date());
			dao.update(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultInfo.SUCCESS;
	}

}
