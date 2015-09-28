package com.zhangk.babysitter.service.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.entity.CustomerManagerDuty;
import com.zhangk.babysitter.service.manager.CustomerManagerService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CustomerManagerView;

@Service
public class CustomerManagerServiceImpl implements CustomerManagerService {

	@Autowired
	private BaseDao dao;

	public CustomerManager login(String username, String password) {
		String hql = "from CustomerManager c where c.ovld = true and c.username= ?";
		CustomerManager manager = dao.getSingleResultByHQL(
				CustomerManager.class, hql, username);
		if (manager != null) {
			String dbPassword = manager.getPassword();
			if (password.equals(dbPassword)) {
				return manager;
			}
		}
		return null;
	}

	public void addCustomerManager(CustomerManager manager) {
		dao.add(manager);
	}

	public Pagination<CustomerManagerView> manageCompanyList(
			Pagination<CustomerManager> page, String name, String companyName,
			String countyid, String state) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from CustomerManager r where ovld = true ");
		if (!StringUtils.isEmpty(countyid) && !"0".equals(countyid)) {
			hql.append(" and r.county.id = ? ");
			params.add(Long.valueOf(countyid));
		}
		if (!StringUtils.isEmpty(companyName)) {
			hql.append(" and r.company.name like ? ");
			params.add("%" + companyName + "%");
		}
		if (!StringUtils.isEmpty(state)) {
			hql.append(" and r.state = ? ");
			params.add(Integer.valueOf(state));
		}
		if (!StringUtils.isEmpty(name)) {
			hql.append(" and r.name like ? ");
			params.add("%" + name + "%");
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<CustomerManager> p = dao.getPageResultObjectParams(
				CustomerManager.class, hql.toString(), page.getPageNo(),
				page.getPageSize(), objParams);
		List<CustomerManager> list = p.getResult();
		List<CustomerManagerView> viewList = new ArrayList<CustomerManagerView>();
		for (CustomerManager namager : list) {
			viewList.add(namager.view());
		}

		Pagination<CustomerManagerView> pa = new Pagination<CustomerManagerView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public ResultInfo manageAddCustomerManager(String username,
			String password, String name, String telephone, long countyid) {
		String hql = "from CustomerManager t where ovld = true and t.telephone = ?";
		CustomerManager manager = dao.getSingleResultByHQL(
				CustomerManager.class, hql, telephone);
		if (manager != null)
			return ResultInfo.MANAGER_NOT_NULL;
		County county = dao.getResultById(County.class, countyid);
		manager = CustomerManager.getInstance();
		manager.setState(Constants.NO_PASS);
		manager.setUsername(username);
		manager.setPassword(password);
		manager.setName(name);
		manager.setTelephone(telephone);
		manager.setCounty(county);
		dao.add(manager);
		return ResultInfo.SUCCESS;
	}

	@Transactional
	public void deleteCustomerManager(String ids) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			CustomerManager manager = dao.getResultById(CustomerManager.class,
					idl);
			manager.setOvld(false);
			dao.update(manager);
		}
	}

	public CustomerManager getCustomerManager(String id) {
		return dao.getResultById(CustomerManager.class, Long.parseLong(id));
	}

	@Transactional
	public ResultInfo manageEditCustomerManager(String id, String password,
			String name, String telephone, long countyid) {
		long idl = Long.valueOf(id);
		CustomerManager manager = dao.getResultById(CustomerManager.class, idl);
		if (manager == null)
			return ResultInfo.COMPANY_NULL;
		if (!StringUtils.isEmpty(name))
			manager.setName(name);
		if (!StringUtils.isEmpty(telephone))
			manager.setTelephone(telephone);
		if (!StringUtils.isEmpty(password))
			manager.setPassword(password);

		if (!StringUtils.isEmpty(countyid) && Long.valueOf(countyid) != 0) {
			County county = dao.getResultById(County.class,
					Long.valueOf(countyid));
			if (county != null)
				manager.setCounty(county);
		}

		manager.setUpdateDate(new Date());
		dao.update(manager);
		return ResultInfo.SUCCESS;
	}

	@Transactional
	public void verify(String ids, String state) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			CustomerManager manager = dao.getResultById(CustomerManager.class,
					idl);
			manager.setState(Integer.valueOf(state));
			dao.update(manager);
		}

	}

	@Transactional
	public void duty(String id, String week, String countyId) {
		long countyIdl = Long.valueOf(countyId);
		long idl = Long.valueOf(id);
		String hql = "from CustomerManagerDuty t where t.ovld = true and t.county.id = ? and t.week = ?";
		CustomerManagerDuty duty = dao.getSingleResultByHQL(
				CustomerManagerDuty.class, hql, countyIdl, week);
		CustomerManager manager = dao.getResultById(CustomerManager.class, idl);
		if (duty == null) {
			CustomerManagerDuty insertDuty = CustomerManagerDuty.getInstance();
			County county = dao.getResultById(County.class, countyIdl);
			insertDuty.setCounty(county);
			insertDuty.setWeek(week);
			insertDuty.setManager(manager);
			dao.add(insertDuty);
		} else {
			duty.setWeek(week);
			dao.update(duty);
		}
	}

	// public static void main(String[] args) {
	// System.out.println(ExpectedDateCreate.get);
	// }
}
