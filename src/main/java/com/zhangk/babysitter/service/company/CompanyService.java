package com.zhangk.babysitter.service.company;

import com.zhangk.babysitter.entity.Company;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CompanyView;

public interface CompanyService {
	Pagination<CompanyView> manageCompanyList(Pagination<Company> page,
			String companyName, String countyId, String type, String state);

	ResultInfo manageAddCompany(String name, String telephone, long countyid,
			int type, String address, String countyLevel);

	void deleteCompany(String ids);

	Company getCompany(String id);

	ResultInfo manageEditCompany(String id, String name, String telephone,
			int countyid, int type, String address, String countyLevel);

	void verify(String ids, String state);
}
