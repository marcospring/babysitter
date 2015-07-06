package com.zhangk.babysitter.service.role.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.service.role.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private BaseDao dao;

	public List<Role> roleList() {
		String hql = "from Role r ";
		List<Role> list = dao.getListResultByHQL(Role.class, hql);
		return list;
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
