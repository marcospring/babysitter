package com.zhangk.babysitter.service.exployer;

import java.util.List;

import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.BabysitterView;

public interface EmployerService {
	List<Employer> ExployerList();

	Pagination<Employer> getPageEmployerList(Pagination<Employer> page);

	void addEmployer(Employer employer);

	void deleteEmployer(long id);

	void updateEmployer(Employer employer);

	Employer getEmployer(long id);

	Employer getEmployerByMobile(String mobile);

	List<BabysitterView> getRecommendBabysitter(String date, int page,
			String countyGuid);
}
