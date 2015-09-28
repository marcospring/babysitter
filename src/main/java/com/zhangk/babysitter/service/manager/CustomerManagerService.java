package com.zhangk.babysitter.service.manager;

import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CustomerManagerView;

public interface CustomerManagerService {
	CustomerManager login(String username, String password);

	void addCustomerManager(CustomerManager manager);

	Pagination<CustomerManagerView> manageCompanyList(
			Pagination<CustomerManager> page, String name, String companyName,
			String countyid, String state);

	ResultInfo manageAddCustomerManager(String username, String password,
			String name, String telephone, long countyid);

	void deleteCustomerManager(String ids);

	CustomerManager getCustomerManager(String id);

	ResultInfo manageEditCustomerManager(String id, String password,
			String name, String telephone, long countyid);

	void verify(String ids, String state);

	void duty(String id, String week, String countyId);
}
