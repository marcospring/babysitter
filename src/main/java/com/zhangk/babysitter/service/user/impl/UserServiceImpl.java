package com.zhangk.babysitter.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.user.UserService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.MenuView;
import com.zhangk.babysitter.viewmodel.UserInfoView;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BaseDao dao;

	public UserInfo login(String username, String password) {

		String hql = "from UserInfo u where u.username = ?";
		UserInfo userinfo = dao.getSingleResultByHQL(UserInfo.class, hql, username);
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

	public List<MenuView> getUserMenus(UserInfo user) {
		long id = user.getId();
		user = dao.getResultById(UserInfo.class, id);
		List<Role> roles = user.getRoles();
		List<Menu> menus = new ArrayList<Menu>();
		for (Role role : roles) {
			menus.addAll(role.getMenus());
		}
		List<MenuView> menuViews = new ArrayList<MenuView>();
		for (Menu menu : menus) {
			menuViews.add(menu.view());
		}
		return menuViews;
	}

	@Transactional
	public void updateUser(UserInfo user) {
		dao.update(user);
	}

	public Pagination<UserInfoView> getPageRoleList(Pagination<UserInfo> page) {
		String hql = "from UserInfo u ";
		String countHql = "select count(r.id) from UserInfo r ";

		Pagination<UserInfo> p = dao.getPageResult(UserInfo.class, hql, page.getPageNo(), page.getPageSize());
		List<UserInfo> list = p.getResult();
		List<UserInfoView> result = new ArrayList<UserInfoView>();
		for (UserInfo user : list) {
			result.add(user.view());
		}
		Pagination<UserInfoView> pageView = new Pagination<UserInfoView>(result, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		pageView.setResultSize(count);
		return pageView;
	}

	@Transactional
	public UserInfo addUser(String username, String password, String name, long[] roleids) {
		String hql = "from UserInfo u where u.username = ?";
		UserInfo user = dao.getSingleResultByHQL(UserInfo.class, hql, username);
		if (user != null)
			return null;
		user = UserInfo.getInstance();
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		List<Role> roles = new ArrayList<Role>();
		if (roleids != null) {
			for (long rid : roleids) {
				roles.add(dao.getResultById(Role.class, rid));
			}
		}
		user.setRoles(roles);
		dao.add(user);
		return user;
	}

	public UserInfo getUser(long id) {
		return dao.getResultById(UserInfo.class, id);
	}

	@Transactional
	public void deleteMenu(long id) {
		dao.delete(UserInfo.class, id);
	}

	@Transactional
	public void updateUser(String id, String username, String password, String name, long[] roleids) {
		if (StringUtils.isEmpty(id))
			return;
		long idl = Long.valueOf(id);
		UserInfo user = dao.getResultById(UserInfo.class, idl);
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		List<Role> roles = new ArrayList<Role>();
		if (roleids != null) {
			for (long rid : roleids) {
				roles.add(dao.getResultById(Role.class, rid));
			}
		}
		user.setRoles(roles);
		dao.update(user);
	}
}
