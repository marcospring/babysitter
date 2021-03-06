package com.zhangk.babysitter.service.exployer.impl;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.BabysitterOrderRecordInfo;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.entity.CustomerManagerDuty;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.PanicBuyingBabysitterAdvice;
import com.zhangk.babysitter.entity.PanicBuyingOrder;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.babysitter.BabysitterOrderService;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.common.NumberRecordService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.CheckCodeUtil;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.data.RequestXMLCreater;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.EmployerView;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

@SuppressWarnings("deprecation")
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
			// 月嫂提交私单不需要向月嫂抢单表中添加数据正常添加私单。
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
	public ResultInfo addBabysitterOrderEvaluate(String orderGuid, String msg,
			String score) {
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
				orderGuid);
		if (order == null)
			return ResultInfo.BABYSITTER_ORDER_NULL;
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, order
				.getBabysitter().getGuid());
		if (babysitter == null)
			return ResultInfo.BABYSITTER_NULL;
		order.setScore(Integer.valueOf(score));
		order.setEvaluation(msg);
		order.setState(Constants.ORDER_OVER);
		dao.update(order);
		BabysitterOrderRecordInfo info = BabysitterOrderRecordInfo
				.getInstance();
		info.setBabysitterOrder(order);
		info.setState(order.getState());
		dao.add(info);

		// BabysitterEvaluate evaluate = BabysitterEvaluate.getInstance();
		// evaluate.setBabysitter(babysitter);
		// evaluate.setEmployer(employer);
		// evaluate.setOrder(order);
		// evaluate.setMsg(msg);
		// evaluate.setScore(Integer.valueOf(score));
		// dao.add(evaluate);
		int sumSocre = 0;
		int count = 0;
		List<BabysitterOrder> orders = babysitter.getOrders();

		for (BabysitterOrder o : orders) {
			if (o.getState() == Constants.ORDER_OVER) {
				sumSocre += o.getScore();
				count += 1;
			}
		}
		int avg = 0;
		if (count != 0) {
			avg = sumSocre / count;
		}
		babysitter.setScore(avg);
		dao.update(babysitter);
		return ResultInfo.SUCCESS;
	}

	public PageResult orderList(String mobile, String openid, PageResult result) {
		Employer employer = null;
		List<ServiceOrderView> view = new ArrayList<ServiceOrderView>();
		if (StringUtils.isEmpty(openid)) {
			String mobileHql = "from Employer t where t.ovld = true and t.mobilePhone = ?";
			employer = dao.getSingleResultByHQL(Employer.class, mobileHql,
					mobile);
		} else {
			String openidHql = "from Employer t where t.ovld = true and t.openid = ?";
			List<Employer> listEmployers = dao.getListResultByHQL(
					Employer.class, openidHql, openid);
			if (listEmployers == null || listEmployers.size() == 0) {
				result.setResult(ResultInfo.SUCCESS);
				result.put("result", view);
				return result;
			}
			if (listEmployers.size() > 1) {
				result.setResult(ResultInfo.MULTI_EMPLYER);
				List<EmployerView> listViews = new ArrayList<EmployerView>();
				for (Employer e : listEmployers) {
					listViews.add(e.view());
				}
				result.put("result", listViews);
				return result;
			} else {
				employer = listEmployers.get(0);
			}

		}
		if (employer == null) {
			result.setResult(ResultInfo.SUCCESS);
			result.put("result", view);
			return result;
		}
		String hql = "from ServiceOrder t where t.ovld = true and t.employer.id=?";
		List<ServiceOrder> orders = dao.getListResultByHQL(ServiceOrder.class,
				hql, employer.getId());
		if (orders == null)
			orders = new ArrayList<ServiceOrder>();
		for (ServiceOrder order : orders) {
			view.add(order.view());
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", view);
		return result;
	}

	public Pagination<ServiceOrderView> manageOrderList(
			Pagination<ServiceOrder> page, String exployerName, String telephone) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from ServiceOrder r where ovld = true ");
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
		Pagination<ServiceOrder> p = dao.getPageResultObjectParams(
				ServiceOrder.class, hql.toString(), page.getPageNo(),
				page.getPageSize(), objParams);
		List<ServiceOrder> list = p.getResult();
		List<ServiceOrderView> viewList = new ArrayList<ServiceOrderView>();
		for (ServiceOrder order : list) {
			viewList.add(order.view());
		}

		Pagination<ServiceOrderView> pa = new Pagination<ServiceOrderView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public ResultInfo manageAddOrder(String guid, String beginDate,
			String endDate, String price, String address, String employerName,
			String telephone, String rate, String countyGuid) {
		try {
			String hql = "from Employer e where e.ovld = true and e.guid = ?";
			Employer employer = dao.getSingleResultByHQL(Employer.class, hql,
					guid);
			County county = dao.getResultByGUID(County.class, countyGuid);
			ServiceOrder order = ServiceOrder.getInstance();
			order.setAddress(address);
			order.setEmployer(employer);
			order.setMobilePhone(telephone);
			order.setOrderPrice(Long.valueOf(price));
			order.setRate(Double.valueOf(rate));
			order.setCounty(county);
			order.setServiceBeginDate(ExpectedDateCreate.parseDate(beginDate));
			order.setServiceEndDate(ExpectedDateCreate.addDays(
					ExpectedDateCreate.parseDate(beginDate),
					Integer.valueOf(endDate)));
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
	public ResultInfo manageEditOrder(String id, String employerAddress,
			String price, String beginDate, String endDate) {
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
				order.setServiceBeginDate(ExpectedDateCreate
						.parseDate(beginDate));
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
	public PageResult wechatAddServiceOrder(String date,
			String countyLevelGuid, String countyGuid, String address,
			String name, String mobile, String checkCode, String openid,
			PageResult result, String isNotAdvice) {
		try {
			// boolean flag = codeService.updateCheckCode(mobile, checkCode,
			// CheckCodeService.PUBLISH_ORDER);
			// if (!flag) {
			// throw new CheckErrorException();
			// }

			// 添加雇主订单
			ServiceOrder order = addEmployerServiceOrder(date, countyLevelGuid,
					countyGuid, address, name, mobile, checkCode, openid);
			if (order == null) {
				result.setResult(ResultInfo.COUNTY_LEVEL_NULL);
				return result;
			}
			Map<String, Date> expectedDate = ExpectedDateCreate
					.getExpectedDate(date);
			if (StringUtils.isEmpty(isNotAdvice)) {
				addBabysitterAdvice(countyGuid, order, expectedDate);
			}
			result.setResult(ResultInfo.SUCCESS);
			result.put("result", order.view());
			// 短信通知客户经理
			String telephone = Constants.MSG_TEMPLATE_ADVICE_PHONE;
			String telephones[] = telephone.split(",");
			for (String phone : telephones) {
				CheckCodeUtil.sendMessage(phone, Constants.MSG_TEMPLATE_ADVICE,
						ExpectedDateCreate.formatDateTime(new Date()),
						String.valueOf(order.getId()));
			}
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
	public ServiceOrder addEmployerServiceOrder(String date,
			String countyLevelGuid, String countyGuid, String address,
			String name, String mobile, String checkCode, String openid) {
		Employer employer = employerService.getEmployerByMobile(mobile);
		County county = dao.getResultByGUID(County.class, countyGuid);
		CountyLevel countyLevel = dao.getResultByGUID(CountyLevel.class,
				countyLevelGuid);
		if (countyLevel == null)
			return null;
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
		order.setOrderPrice(Long.valueOf(countyLevel.getMoney()));
		order.setRate(countyLevel.getRate());
		order.setAddress(address);
		order.setMobilePhone(mobile);
		order.setEmployerName(name);
		order.setMobilePhone(mobile);
		Map<String, Date> expectedDate = ExpectedDateCreate
				.getExpectedDate(date);
		order.setServiceBeginDate(expectedDate
				.get(ExpectedDateCreate.BEGIN_DATE));
		order.setServiceEndDate(expectedDate.get(ExpectedDateCreate.END_DATE));
		dao.add(order);
		return order;
	}

	@Transactional
	public PageResult markBabysitter(String babysitterGuid, String orderGuid,
			PageResult result) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		ServiceOrder serviceOrder = dao.getResultByGUID(ServiceOrder.class,
				orderGuid);
		BabysitterOrder order = updateBabysitterOrder(orderGuid, babysitter,
				serviceOrder);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", order.view());
		return result;
	}

	private BabysitterOrder updateBabysitterOrder(String orderGuid,
			Babysitter babysitter, ServiceOrder serviceOrder) {
		// 添加月嫂订单
		BabysitterOrder babysitterOrder = BabysitterOrder.getInstance();
		babysitterOrder.setBabysitter(babysitter);
		babysitterOrder.setEmployer(serviceOrder.getEmployer());
		babysitterOrder.setEmployerAddress(serviceOrder.getAddress());
		babysitterOrder.setEmployerName(serviceOrder.getEmployerName());
		babysitterOrder.setEmployerTelephone(serviceOrder.getMobilePhone());
		babysitterOrder.setOrderId(recordService.createOrderId());
		babysitterOrder.setOrderPrice(serviceOrder.getOrderPrice());
		long frontPrice = getFrontPrice(serviceOrder.getOrderPrice());
		babysitterOrder.setOrderFrontPrice(frontPrice);
		babysitterOrder.setServiceBeginDate(serviceOrder.getServiceBeginDate());
		babysitterOrder.setServiceEndDate(serviceOrder.getServiceEndDate());
		babysitterOrder.setState(Constants.NEW_ORDER);
		dao.add(babysitterOrder);
		// 添加月嫂订单变更信息
		BabysitterOrderRecordInfo recordInfo = BabysitterOrderRecordInfo
				.getInstance();
		recordInfo.setBabysitterOrder(babysitterOrder);
		recordInfo.setState(babysitterOrder.getState());
		dao.add(recordInfo);

		// 更新雇主订单
		serviceOrder.setOrderGuid(babysitterOrder.getGuid());
		dao.update(serviceOrder);

		// 更新月嫂抢单通知表中该订单的所有记录为不可用即订单已经被抢
		String hql = "from PanicBuyingBabysitterAdvice t where t.ovld = true and t.serviceOrder.guid = ?";
		List<PanicBuyingBabysitterAdvice> list = dao.getListResultByHQL(
				PanicBuyingBabysitterAdvice.class, hql, orderGuid);
		for (PanicBuyingBabysitterAdvice advice : list) {
			advice.setIsOver(true);
			dao.update(advice);
		}
		return babysitterOrder;
	}

	private long getFrontPrice(long orderPrice) {
		BigDecimal frontPrice = new BigDecimal(orderPrice);
		frontPrice = frontPrice.multiply(new BigDecimal(0.2));
		// Double frontPrice = new Double(orderPrice * 0.2);
		String frontPriceStr = frontPrice.toString();
		frontPriceStr = frontPriceStr.substring(0, frontPriceStr.indexOf("."));
		return new BigDecimal(frontPriceStr).longValue();
	}

	@Transactional
	public void addBabysitterAdvice(String countyGuid, ServiceOrder order,
			Map<String, Date> expectedDate) {
		// 添加需要通知的月嫂
		// CountyLevel countyLevel = dao.getResultByGUID(CountyLevel.class,
		// countyLevelGuid);
		String hql = "from Babysitter b where b.county.guid=? and b.level.level.money>=? and b.state = 1 ";
		List<Babysitter> babysitters = dao.getListResultByHQL(Babysitter.class,
				hql, countyGuid, order.getOrderPrice());
		// 添加可以抢单月嫂策略,SQL已经判断了所属城市、级别、审核状态三个条件
		// 1.排除最低薪水不符合条件的
		List<Babysitter> removeBabysitters = new ArrayList<Babysitter>();
		if (babysitters.size() == 0)
			return;
		BigDecimal salary = new BigDecimal(order.getOrderPrice());
		salary = salary.add(new BigDecimal(-1000));
		double rate = 1;
		if (order.getRate() != 0)
			rate = order.getRate();
		salary = salary.multiply(new BigDecimal(rate));
		long salaryLong = salary.longValue();
		for (Babysitter babysitter : babysitters) {
			if (babysitter.getLowerSalary() > salaryLong)
				removeBabysitters.add(babysitter);
		}
		babysitters.removeAll(removeBabysitters);
		// 2.排除档期不符合条件
		if (babysitters.size() == 0)
			return;
		if (removeBabysitters.size() != 0)
			removeBabysitters.clear();
		for (Babysitter babysitter : babysitters) {
			if (!ExpectedDateCreate.checkBabysitterOrder(babysitter,
					expectedDate)) {
				removeBabysitters.add(babysitter);
			}
		}
		babysitters.removeAll(removeBabysitters);
		// 添加月嫂通知
		if (babysitters.size() == 0)
			return;
		for (Babysitter babysitter : babysitters) {
			PanicBuyingBabysitterAdvice advice = PanicBuyingBabysitterAdvice
					.getInstance();
			advice.setBabysitter(babysitter);
			advice.setServiceOrder(order);
			advice.setIsAdvice(false);
			advice.setIsOver(false);
			dao.add(advice);
		}
	}

	@Transactional
	public void addServiceOrderAdvice(String id) {
		long idl = Long.valueOf(id);
		ServiceOrder order = dao.getResultById(ServiceOrder.class, idl);
		// 更新所有通知为已通知
		String hql = "from PanicBuyingBabysitterAdvice t where t.ovld = true and t.serviceOrder.id = ?";
		List<PanicBuyingBabysitterAdvice> advices = dao.getListResultByHQL(
				PanicBuyingBabysitterAdvice.class, hql, order.getId());
		for (PanicBuyingBabysitterAdvice advice : advices) {
			advice.setIsAdvice(true);
			dao.update(advice);
		}
		Map<String, Date> dateMap = new HashMap<String, Date>();
		dateMap.put(ExpectedDateCreate.BEGIN_DATE, order.getServiceBeginDate());
		dateMap.put(ExpectedDateCreate.END_DATE, order.getServiceEndDate());
		// 重新加入月嫂通知
		addBabysitterAdvice(order.getCounty().getGuid(), order, dateMap);
		// 更新雇主订单没有完成抢单
		order.setOver(true);
		dao.update(order);
	}

	@Transactional
	public void addServiceOrderPanic(String babysitterId, String serviceOrderId) {
		long babysitterIdl = Long.valueOf(babysitterId);
		long serviceOrderIdl = Long.valueOf(serviceOrderId);

		Babysitter babysitter = dao.getResultById(Babysitter.class,
				babysitterIdl);
		ServiceOrder order = dao.getResultById(ServiceOrder.class,
				serviceOrderIdl);
		if (order != null) {

			PanicBuyingOrder buyingOrder = PanicBuyingOrder.getInstance();
			buyingOrder.setBabysitter(babysitter);
			buyingOrder.setServiceOrder(order);
			dao.add(buyingOrder);

		}
	}

	public List<BabysitterView> getPanicBabysitters(String serviceOrderId) {
		long serviceOrderIdl = Long.valueOf(serviceOrderId);
		String hql = "from PanicBuyingOrder t where t.ovld = true and t.serviceOrder.id = ? and t.serviceOrder.orderGuid is null";
		List<PanicBuyingOrder> orders = dao.getListResultByHQL(
				PanicBuyingOrder.class, hql, serviceOrderIdl);
		List<BabysitterView> views = new ArrayList<BabysitterView>();
		for (PanicBuyingOrder order : orders) {
			views.add(order.getBabysitter().view());
		}

		return views;
	}

	@Transactional
	public PageResult markBabysitterId(String babysitterId,
			String serviceOrderId, PageResult result) {
		long babysitterIdl = Long.valueOf(babysitterId);
		long serviceOrderIdl = Long.valueOf(serviceOrderId);
		Babysitter babysitter = dao.getResultById(Babysitter.class,
				babysitterIdl);
		ServiceOrder serviceOrder = dao.getResultById(ServiceOrder.class,
				serviceOrderIdl);
		updateBabysitterOrder(serviceOrder.getGuid(), babysitter, serviceOrder);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", babysitter.view());
		return result;
	}

	public CustomerManager getCustomerManager(String countyGuid, String week) {
		String hql = "from CustomerManagerDuty t where t.ovld = true and t.county.guid = ? and t.week = ?";
		CustomerManagerDuty duty = dao.getSingleResultByHQL(
				CustomerManagerDuty.class, hql, countyGuid, week);
		return duty == null ? null : duty.getManager();
	}

	public PageResult payFrontMoney(String orderNo, String ip,
			PageResult result, String openid) {
		String hql = "from BabysitterOrder t where t.orderId = ?";
		BabysitterOrder order = dao.getSingleResultByHQL(BabysitterOrder.class,
				hql, orderNo);
		if (order == null) {
			result.setResult(ResultInfo.BABYSITTER_ORDER_NULL);
			return result;
		}
		if (order.getState() != Constants.NEW_ORDER) {
			result.setResult(ResultInfo.INVALID_ORDER);
			return result;
		}

		Map<String, String> params = RequestXMLCreater.getInstance()
				.getPayRequestMap(order.getGuid(), "月嫂定金",
						order.getOrderId() + BabysitterOrderService.FRONT,
						String.valueOf(order.getOrderFrontPrice() * 100), ip,
						openid);
		String xml = RequestXMLCreater.getInstance().buildXmlString(params,
				Constants.WECHAT_OPENID_PAY_APPSECRET);
		System.out.println("======================>" + xml);
		sendPaymentToWeChatServer(result, order, xml);
		return result;
	}

	public PageResult endService(String orderGuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public PageResult addMakeBabysitterOrder(String date, String address,
			String name, String mobile, String checkCode, String openid,
			String countyGuid, String babysitterGuid, PageResult result) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		if (babysitter == null) {
			result.setResult(ResultInfo.BABYSITTER_NULL);
			return result;
		}
		ServiceOrder order = addEmployerServiceOrder(date, babysitter
				.getLevel().getGuid(), countyGuid, address, name, mobile,
				checkCode, openid);
		if (order == null) {
			result.setResult(ResultInfo.COUNTY_LEVEL_NULL);
			return result;
		}
		result = markBabysitter(babysitterGuid, order.getGuid(), result);
		return result;
	}

	public PageResult getBabysitterOrderInfo(String serviceOrderGuid,
			PageResult result) {
		ServiceOrder serviceOrder = dao.getResultByGUID(ServiceOrder.class,
				serviceOrderGuid);
		if (serviceOrder == null) {
			result.setResult(ResultInfo.SERVICE_ORDER_NULL);
			return result;
		}
		if (StringUtils.isEmpty(serviceOrder.getOrderGuid())) {
			result.setResult(ResultInfo.NO_BABYSITTER_SERVICE_ORDER);
			return result;
		}
		String orderGuid = serviceOrder.getOrderGuid();
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
				orderGuid);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", order.view());
		return result;
	}

	@Transactional
	public PageResult serviceEnd(String orderGuid, PageResult result) {
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
				orderGuid);
		if (order == null) {
			result.setResult(ResultInfo.BABYSITTER_ORDER_NULL);
			return result;
		}
		order.setState(Constants.OUT_ORDER);
		// 设置订单日志
		BabysitterOrderRecordInfo info = BabysitterOrderRecordInfo
				.getInstance();
		info.setBabysitterOrder(order);
		info.setState(order.getState());
		dao.add(info);
		result.setResult(ResultInfo.SUCCESS);
		return result;
	}

	public PageResult orderHistoryRecord(String orderGuid, PageResult result) {
		BabysitterOrder order = dao.getResultByGUID(BabysitterOrder.class,
				orderGuid);
		if (order == null) {
			result.setResult(ResultInfo.BABYSITTER_ORDER_NULL);
			return result;
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", order.getInfos());
		return result;
	}

	public PageResult endWechatOrder(String orderNo, String ip,
			PageResult result, String openid) {
		String hql = "from BabysitterOrder t where t.orderId = ?";
		BabysitterOrder order = dao.getSingleResultByHQL(BabysitterOrder.class,
				hql, orderNo);
		if (order == null) {
			result.setResult(ResultInfo.BABYSITTER_ORDER_NULL);
			return result;
		}
		if (order.getState() != Constants.EARNEST_MONEY) {
			result.setResult(ResultInfo.INVALID_ORDER);
			return result;
		}
		BigDecimal orderPrice = new BigDecimal(order.getOrderPrice());
		orderPrice = orderPrice.add(new BigDecimal(0 - order
				.getOrderFrontPrice()));
		orderPrice = orderPrice.multiply(new BigDecimal(100));
		Map<String, String> params = RequestXMLCreater.getInstance()
				.getPayRequestMap(order.getGuid(), "月嫂尾款",
						order.getOrderId() + BabysitterOrderService.END,
						orderPrice.toString(), ip, openid);
		String xml = RequestXMLCreater.getInstance().buildXmlString(params,
				Constants.WECHAT_OPENID_PAY_APPSECRET);
		System.out.println("======================>" + xml);
		sendPaymentToWeChatServer(result, order, xml);
		return result;
	}

	@SuppressWarnings("resource")
	private void sendPaymentToWeChatServer(PageResult result,
			BabysitterOrder order, String xml) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constants.WECHAT_OPENID_PAY_URL);
		StringEntity myEntity = new StringEntity(xml, "UTF-8");
		httppost.addHeader("Content-Type", "text/xml");
		httppost.setEntity(myEntity);
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			InputStreamReader reader = new InputStreamReader(
					resEntity.getContent(), "UTF-8");
			StringBuffer resultStr = new StringBuffer();
			char[] buff = new char[1024];
			int length = 0;
			while ((length = reader.read(buff)) != -1) {
				resultStr.append(new String(buff, 0, length));
			}
			System.out.println(resultStr.toString());
			httpclient.getConnectionManager().shutdown();
			Map<String, String> responseResult = RequestXMLCreater
					.getInstance().getXmlMapResult(resultStr.toString());
			Map<String, String> resultMap;
			if (Constants.PAY_SUCCESS.equals(responseResult.get("return_code"))
					&& Constants.PAY_MESSAGE_OK.equals(responseResult
							.get("return_msg"))
					&& Constants.PAY_SUCCESS.equals(responseResult
							.get("result_code"))) {
				resultMap = RequestXMLCreater.getInstance().getPayResponseMap(
						responseResult.get("prepay_id"), order.getGuid());

				result.setResult(ResultInfo.SUCCESS);
				result.put("result", resultMap);
			} else {
				result.put("code", responseResult.get("err_code"));
				result.put("msg", responseResult.get("err_code_des"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
