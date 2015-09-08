package com.zhangk.babysitter.service.exployer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.PanicBuyingBabysitterAdvice;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.common.NumberRecordService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

	@Autowired
	private BaseDao dao;
	@Autowired
	private CheckCodeService codeService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private NumberRecordService recordService;

	@Transactional
	public ResultInfo addServiceOrder(String date, String price, String countyGuid, String address, String name, String mobile, String checkCode) {
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
			// 月嫂提交私单不需要向月嫂抢单表中添加数据正常添加私单。
			ServiceOrder order = ServiceOrder.getInstance();
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price));
			order.setAddress(address);
			// order.setCounty(county);
			order.setMobilePhone(mobile.replace(" ", ""));
			Map<String, Date> expectedDate = ExpectedDateCreate.getExpectedDate(date);
			order.setServiceBeginDate(expectedDate.get(ExpectedDateCreate.BEGIN_DATE));
			order.setServiceEndDate(expectedDate.get(ExpectedDateCreate.END_DATE));
			dao.add(order);
		} catch (CheckErrorException e) {
			return ResultInfo.CHECK_CODE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultInfo.SUCCESS;

	}

	@Transactional
	public ResultInfo addBabysitterOrderEvaluate(String employGuid, String orderGuid, String babysitterGuid, String msg, String score) {
		// Employer employer = dao.getResultByGUID(Employer.class, employGuid);
		// if (employer == null)
		// return ResultInfo.EMPLOYER_NULL;
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class, orderGuid);
		if (order == null)
			return ResultInfo.BABYSITTER_ORDER_NULL;
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, babysitterGuid);
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

	public PageResult orderList(String mobile, String openid, PageResult result) {
		Employer employer = null;
		if (StringUtils.isEmpty(openid)) {
			String mobileHql = "from Employer t where t.ovld = true and t.mobilePhone = ?";
			employer = dao.getSingleResultByHQL(Employer.class, mobileHql, mobile);
		} else {
			String openidHql = "from Employer t where t.ovld = true and t.openid = ?";
			employer = dao.getSingleResultByHQL(Employer.class, openidHql, openid);
		}
		List<ServiceOrderView> view = new ArrayList<ServiceOrderView>();
		if (employer == null) {
			result.setResult(ResultInfo.SUCCESS);
			result.put("result", view);
		}
		String hql = "from ServiceOrder t where t.ovld = true and t.employer.id=?";
		List<ServiceOrder> orders = dao.getListResultByHQL(ServiceOrder.class, hql, employer.getId());
		if (orders == null)
			orders = new ArrayList<ServiceOrder>();
		for (ServiceOrder order : orders) {
			view.add(order.view());
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", view);
		return result;
	}

	public Pagination<ServiceOrderView> manageOrderList(Pagination<ServiceOrder> page, String exployerName, String telephone) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from ServiceOrder r where ovld = true ");
		if (!StringUtils.isEmpty(telephone)) {
			hql.append(" and r.mobilePhone = ? ");
			params.add(telephone);
		}
		if (!StringUtils.isEmpty(exployerName)) {
			hql.append(" and r.employer.username like ? ");
			params.add("%" + exployerName + "%");
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<ServiceOrder> p = dao.getPageResultObjectParams(ServiceOrder.class, hql.toString(), page.getPageNo(), page.getPageSize(), objParams);
		List<ServiceOrder> list = p.getResult();
		List<ServiceOrderView> viewList = new ArrayList<ServiceOrderView>();
		for (ServiceOrder order : list) {
			viewList.add(order.view());
		}

		Pagination<ServiceOrderView> pa = new Pagination<ServiceOrderView>(viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class, countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public ResultInfo manageAddOrder(String beginDate, String endDate, String price, String address, String employerName, String telephone) {
		try {
			String hql = "from Employer e where e.ovld = true and e.mobilePhone = ?";
			Employer employer = dao.getSingleResultByHQL(Employer.class, hql, telephone);
			if (employer == null) {
				employer = Employer.getInstance();
				employer.setMobilePhone(telephone.replace(" ", ""));
				// employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(employerName);
				dao.add(employer);
			}
			ServiceOrder order = ServiceOrder.getInstance();
			order.setAddress(address);
			order.setEmployer(employer);
			order.setMobilePhone(telephone);
			order.setOrderPrice(Long.valueOf(price));
			order.setServiceBeginDate(ExpectedDateCreate.parseDate(beginDate));
			order.setServiceEndDate(ExpectedDateCreate.parseDate(endDate));
			dao.add(order);
			return ResultInfo.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultInfo.BAD_REQUEST;
		}
	}

	@Transactional
	public void deleteOrder(String ids) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			ServiceOrder order = dao.getResultById(ServiceOrder.class, idl);
			order.setOvld(false);
			dao.update(order);
		}

	}

	public ServiceOrder getOrder(String id) {
		return dao.getResultById(ServiceOrder.class, Long.parseLong(id));
	}

	@Transactional
	public ResultInfo manageEditOrder(String id, String employerAddress, String price, String beginDate, String endDate) {
		try {
			long idl = Long.valueOf(id);
			ServiceOrder order = dao.getResultById(ServiceOrder.class, idl);
			if (order == null)
				return ResultInfo.SERVICE_ORDER_NULL;
			// String hql =
			// "from Employer e where e.ovld = true and e.mobilePhone = ?";
			// Employer employer = dao.getSingleResultByHQL(Employer.class, hql,
			// employerTelephone);
			// if (employer == null)
			// return ResultInfo.EMPLOYER_NULL;
			// if (!StringUtils.isEmpty(employerName))
			// employer.setUsername(employerName);
			// if (!StringUtils.isEmpty(employerTelephone))
			// employer.setMobilePhone(employerTelephone);
			if (!StringUtils.isEmpty(employerAddress))
				order.setAddress(employerAddress);
			if (!StringUtils.isEmpty(price))
				order.setOrderPrice(Long.parseLong(price));
			if (!StringUtils.isEmpty(beginDate))
				order.setServiceBeginDate(ExpectedDateCreate.parseDate(beginDate));
			if (!StringUtils.isEmpty(endDate))
				order.setServiceEndDate(ExpectedDateCreate.parseDate(endDate));
			// order.setEmployer(employer);
			order.setUpdateDate(new Date());
			dao.update(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultInfo.SUCCESS;
	}

	/**
	 * 雇主添加订单，需要根据订单的开始结束时间、订单所属地区查找符合要求的月嫂添加到需要通知的月嫂的数据表中
	 */
	@Transactional
	public PageResult wechatAddServiceOrder(String date, String price, String countyGuid, String address, String name, String mobile, String checkCode, String openid,
			PageResult result) {
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
				employer.setMobilePhone(mobile.replace(" ", ""));
				// employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(name);
				employer.setOpenid(openid);
				employerService.addEmployer(employer);
			}
			// 添加雇主订单
			ServiceOrder order = ServiceOrder.getInstance();
			order.setEmployer(employer);
			order.setCounty(county);
			order.setOrderPrice(Long.valueOf(price));
			order.setAddress(address);
			order.setMobilePhone(mobile);
			order.setEmployerName(name);
			order.setMobilePhone(mobile);
			Map<String, Date> expectedDate = ExpectedDateCreate.getExpectedDate(date);
			order.setServiceBeginDate(expectedDate.get(ExpectedDateCreate.BEGIN_DATE));
			order.setServiceEndDate(expectedDate.get(ExpectedDateCreate.END_DATE));
			dao.add(order);
			// 添加需要通知的月嫂
			String hql = "from Babysitter b where b.county.guid=?";
			List<Babysitter> babysitters = dao.getListResultByHQL(Babysitter.class, hql, countyGuid);
			for (Babysitter babysitter : babysitters) {
				if (ExpectedDateCreate.checkBabysitterOrder(babysitter, expectedDate)) {
					PanicBuyingBabysitterAdvice advice = PanicBuyingBabysitterAdvice.getInstance();
					advice.setBabysitter(babysitter);
					advice.setServiceOrder(order);
					advice.setIsAdvice(false);
					advice.setIsOver(false);
					dao.add(advice);
				}
			}
			result.setResult(ResultInfo.SUCCESS);
			result.put("result", order.view());
			return result;
		} catch (CheckErrorException e) {
			result.setResult(ResultInfo.CHECK_CODE_ERROR);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setResult(ResultInfo.BAD_REQUEST);
		return result;
	}

	@Transactional
	public PageResult markBabysitter(String babysitterGuid, String orderGuid, PageResult result) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, babysitterGuid);
		ServiceOrder serviceOrder = dao.getResultByGUID(ServiceOrder.class, orderGuid);
		// 添加月嫂订单
		BabysitterOrder babysitterOrder = BabysitterOrder.getInstance();
		babysitterOrder.setBabysitter(babysitter);
		babysitterOrder.setEmployer(serviceOrder.getEmployer());
		babysitterOrder.setEmployerAddress(serviceOrder.getAddress());
		babysitterOrder.setEmployerName(serviceOrder.getEmployerName());
		babysitterOrder.setEmployerTelephone(serviceOrder.getMobilePhone());
		babysitterOrder.setOrderId(recordService.createOrderId());
		babysitterOrder.setOrderPrice(serviceOrder.getOrderPrice());
		babysitterOrder.setServiceBeginDate(serviceOrder.getServiceBeginDate());
		babysitterOrder.setServiceEndDate(serviceOrder.getServiceEndDate());
		babysitterOrder.setState(Constants.NEW_ORDER);
		dao.add(babysitterOrder);
		// 更新雇主订单
		serviceOrder.setOrderGuid(babysitterOrder.getGuid());
		dao.update(serviceOrder);
		// 更新月嫂抢单通知表中该订单的所有记录为不可用即订单已经被抢
		String hql = "from PanicBuyingBabysitterAdvice t where t.ovld = true and t.serviceOrder.guid = ?";
		List<PanicBuyingBabysitterAdvice> list = dao.getListResultByHQL(PanicBuyingBabysitterAdvice.class, hql, orderGuid);
		for (PanicBuyingBabysitterAdvice advice : list) {
			advice.setIsOver(true);
			dao.update(advice);
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", babysitter.view());
		return result;
	}
}
