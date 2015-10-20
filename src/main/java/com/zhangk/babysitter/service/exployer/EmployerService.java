package com.zhangk.babysitter.service.exployer;

import java.util.List;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.EmployerView;

public interface EmployerService {
	List<Employer> ExployerList();

	Pagination<Employer> getPageEmployerList(Pagination<Employer> page);

	void addEmployer(Employer employer);

	void deleteEmployer(long id);

	void updateEmployer(Employer employer);

	Employer getEmployer(long id);

	Employer getEmployer(String guid);

	Employer getEmployerByMobile(String mobile);

	List<BabysitterView> getRecommendBabysitter(String date, int page,
			String countyGuid, String orderGuid);

	Pagination<EmployerView> getPageEmployerListForOrder(
			Pagination<Employer> page, String employerName,
			String employerTelephone);

	PageResult search(String countyGuid, String babysitterName,
			PageResult result);
}
