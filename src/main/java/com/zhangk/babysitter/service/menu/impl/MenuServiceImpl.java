package com.zhangk.babysitter.service.menu.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Menu;
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

}
