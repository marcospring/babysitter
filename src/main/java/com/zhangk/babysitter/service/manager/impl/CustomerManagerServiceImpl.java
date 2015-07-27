package com.zhangk.babysitter.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.service.manager.CustomerManagerService;

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

}
