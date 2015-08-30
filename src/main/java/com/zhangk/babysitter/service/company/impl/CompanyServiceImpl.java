package com.zhangk.babysitter.service.company.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Company;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.service.company.CompanyService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CompanyView;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private BaseDao dao;

	public Pagination<CompanyView> manageCompanyList(Pagination<Company> page,
			String companyName, String countyId, String type, String state) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from Company r where ovld = true ");
		if (!StringUtils.isEmpty(countyId) && !"0".equals(countyId)) {
			hql.append(" and r.county.id = ? ");
			params.add(Long.valueOf(countyId));
		}
		if (!StringUtils.isEmpty(companyName)) {
			hql.append(" and r.name like ? ");
			params.add("%" + companyName + "%");
		}
		if (!StringUtils.isEmpty(state)) {
			hql.append(" and r.state = ? ");
			params.add(Integer.valueOf(state));
		}
		if (!StringUtils.isEmpty(type)) {
			hql.append(" and r.type = ? ");
			params.add(Integer.parseInt(type));
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<Company> p = dao
				.getPageResultObjectParams(Company.class, hql.toString(),
						page.getPageNo(), page.getPageSize(), objParams);
		List<Company> list = p.getResult();
		List<CompanyView> viewList = new ArrayList<CompanyView>();
		for (Company company : list) {
			CompanyView view = new CompanyView(company);
			viewList.add(view);
		}

		Pagination<CompanyView> pa = new Pagination<CompanyView>(viewList,
				p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	public ResultInfo manageAddCompany(String name, String telephone,
			long countyid, int type, String address, String countyLevel) {
		String hql = "from Company t where ovld = true and t.telephone = ?";
		Company company = dao.getSingleResultByHQL(Company.class, hql,
				telephone);
		if (company != null)
			return ResultInfo.COMPANY_NOT_NULL;
		County county = dao.getResultById(County.class, countyid);
		company = Company.getInstance();
		company.setAddress(address);
		company.setCounty(county);
		company.setCountyLevel(Integer.valueOf(countyLevel));
		company.setName(name);
		company.setTelephone(telephone);
		company.setType(type);
		dao.add(company);
		return ResultInfo.SUCCESS;
	}

	@Transactional
	public void deleteCompany(String ids) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			Company company = dao.getResultById(Company.class, idl);
			company.setOvld(false);
			dao.update(company);
		}
	}

	public Company getCompany(String id) {
		return dao.getResultById(Company.class, Long.parseLong(id));
	}

	@Transactional
	public ResultInfo manageEditCompany(String id, String name,
			String telephone, int countyid, int type, String address,
			String countyLevel) {
		long idl = Long.valueOf(id);
		Company company = dao.getResultById(Company.class, idl);
		if (company == null)
			return ResultInfo.COMPANY_NULL;
		if (!StringUtils.isEmpty(name))
			company.setName(name);
		if (!StringUtils.isEmpty(telephone))
			company.setTelephone(telephone);
		if (!StringUtils.isEmpty(address))
			company.setAddress(address);
		if (!StringUtils.isEmpty(countyLevel)
				&& Integer.valueOf(countyLevel) != 0)
			company.setCountyLevel(Integer.valueOf(countyLevel));

		if (!StringUtils.isEmpty(countyid) && Long.valueOf(countyid) != 0) {
			County county = dao.getResultById(County.class,
					Long.valueOf(countyid));
			if (county != null)
				company.setCounty(county);
		}
		if (type != 0) {
			company.setType(type);
		}
		company.setUpdateDate(new Date());
		dao.update(company);
		return ResultInfo.SUCCESS;
	}

	@Transactional
	public void verify(String ids, String state) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			Company company = dao.getResultById(Company.class, idl);
			company.setState(Integer.valueOf(state));
			dao.update(company);
		}

	}

}
