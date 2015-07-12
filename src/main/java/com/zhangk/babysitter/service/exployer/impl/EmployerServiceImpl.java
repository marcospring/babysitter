package com.zhangk.babysitter.service.exployer.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.utils.common.Pagination;

@Service
public class EmployerServiceImpl implements EmployerService {
	@Autowired
	private BaseDao dao;

	public List<Employer> ExployerList() {
		String hql = "from Employer r ";
		List<Employer> list = dao.getListResultByHQL(Employer.class, hql);
		return list;
	}

	public Pagination<Employer> getPageEmployerList(Pagination<Employer> page) {
		String hql = "from Employer r";
		String countHql = "select count(r.id) from Employer r";

		Pagination<Employer> p = dao.getPageResult(Employer.class, hql,
				page.getPageNo(), page.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addEmployer(Employer employer) {
		dao.add(employer);
	}

	@Transactional
	public void deleteEmployer(long id) {
		dao.delete(Employer.class, id);
	}

	@Transactional
	public void updateEmployer(Employer employer) {
		dao.update(employer);
	}

	public Employer getEmployer(long id) {
		return dao.getResultById(Employer.class, id);
	}

}
