package com.zhangk.babysitter.service.menu;

import java.util.List;

import com.zhangk.babysitter.entity.Menu;

public interface MenuService {
	void addMenu(Menu menu);

	List<Menu> menuList();

	void deleteMenu(long id);

	void updateMenu(Menu menu);
}
