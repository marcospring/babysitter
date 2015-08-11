package com.zhangk.babysitter.service.menu;

import java.util.List;

import com.zhangk.babysitter.entity.Menu;

public interface MenuService {
	void addMenu(Menu menu);

	List<Menu> menuList();

	void deleteMenu(long id);

	void updateMenu(Menu menu);

	void updateMenu(String title, String url, String pid, String id);

	Object getMenu(long id);

	void addMenu(String title, String url, String pid);

	List<Menu> roleMenus(long id);
}
