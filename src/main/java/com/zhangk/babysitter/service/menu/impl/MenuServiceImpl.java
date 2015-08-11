package com.zhangk.babysitter.service.menu.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.service.menu.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addMenu(Menu menu) {
		dao.add(menu);
	}

	public List<Menu> menuList() {
		String hql = "from Menu";
		List<Menu> list = dao.getListResultByHQL(Menu.class, hql);
		return list;
	}

	@Transactional
	public void deleteMenu(long id) {
		dao.delete(Menu.class, id);
	}

	@Transactional
	public void updateMenu(Menu menu) {
		dao.update(menu);
	}

	@Transactional
	public void updateMenu(String title, String url, String pid, String id) {
		if (StringUtils.isEmpty(id)) {
			return;
		}
		long idl = Long.valueOf(id);
		Menu menu = dao.getResultById(Menu.class, idl);
		menu.setTitle(title);
		menu.setUrl(url);
		if (StringUtils.isEmpty(pid)) {
			pid = "0";
		}
		long parentId = Long.valueOf(pid);
		Menu parent = dao.getResultById(Menu.class, parentId);
		menu.setParent(parent);
		dao.update(menu);

	}

	public Menu getMenu(long id) {
		return dao.getResultById(Menu.class, id);
	}

	@Transactional
	public void addMenu(String title, String url, String pid) {
		Menu menu = Menu.getInstance();
		menu.setTitle(title);
		menu.setUrl(url);
		if (StringUtils.isEmpty(pid)) {
			pid = "0";
		}
		long parentId = Long.valueOf(pid);
		Menu parent = dao.getResultById(Menu.class, parentId);
		menu.setParent(parent);
		dao.add(menu);
	}

	public List<Menu> roleMenus(long id) {
		Role role = dao.getResultById(Role.class, id);
		List<Menu> menus = role.getMenus();
		return menus;
	}

}
