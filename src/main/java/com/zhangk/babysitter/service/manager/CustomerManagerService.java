package com.zhangk.babysitter.service.manager;

import com.zhangk.babysitter.entity.CustomerManager;

public interface CustomerManagerService {
	CustomerManager login(String username, String password);

	void addCustomerManager(CustomerManager manager);
}
