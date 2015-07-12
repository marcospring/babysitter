package com.zhangk.babysitter.service.role.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.utils.common.Pagination;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private BaseDao dao;

	public List<Role> roleList() {
		String hql = "from Role r ";
		List<Role> list = dao.getListResultByHQL(Role.class, hql);
		return list;
	}

	public Pagination<Role> getPageRoleList(Pagination<Role> page) {
		String hql = "from Role r";
		String countHql = "select count(r.id) from Role r";

		Pagination<Role> p = dao.getPageResult(Role.class, hql,
				page.getPageNo(), page.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addRole(Role role) {
		dao.add(role);
	}

	@Transactional
	public void deleteRole(long id) {
		dao.delete(Role.class, id);
	}

	@Transactional
	public void updateRole(Role role) {
		dao.update(role);
	}

	public Role getRole(long id) {
		return dao.getResultById(Role.class, id);
	}
}
