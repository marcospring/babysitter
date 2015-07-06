package com.zhangk.babysitter.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BaseDao dao;

	public UserInfo login(String username, String password) {

		String hql = "from UserInfo u where u.username = ?";
		UserInfo userinfo = dao.getSingleResultByHQL(UserInfo.class, hql,
				username);
		if (userinfo != null) {
			String dbPassword = userinfo.getPassword();
			if (password.equals(dbPassword)) {
				return userinfo;
			}
		}
		return null;
	}

	@Transactional
	public void userRegister(UserInfo user) {
		dao.add(user);
	}

	public List<Menu> getUserMenus(UserInfo user) {
		long id = user.getId();
		user = dao.getResultById(UserInfo.class, id);
		List<Role> roles = user.getRoles();
		List<Menu> menus = new ArrayList<Menu>();
		for (Role role : roles) {
			menus.addAll(role.getMenus());
		}
		return menus;
	}

	@Transactional
	public void updateUser(UserInfo user) {
		dao.update(user);
	}

}
