package com.zhangk.babysitter.service.role.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.RoleView;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private BaseDao dao;

	public List<Role> roleList() {
		String hql = "from Role r ";
		List<Role> list = dao.getListResultByHQL(Role.class, hql);
		return list;
	}

	public Pagination<RoleView> getPageRoleList(Pagination<Role> page) {
		String hql = "from Role r";
		String countHql = "select count(r.id) from Role r";

		Pagination<Role> p = dao.getPageResult(Role.class, hql,
				page.getPageNo(), page.getPageSize());
		List<Role> list = p.getResult();
		List<RoleView> result = new ArrayList<RoleView>();
		for (Role role : list) {
			result.add(role.view());
		}
		Pagination<RoleView> pageView = new Pagination<RoleView>(result,
				p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		pageView.setResultSize(count);
		return pageView;
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
		Role editRole = dao.getResultById(Role.class, role.getId());
		editRole.setName(role.getName());
		editRole.setUpdateDate(new Date());
		dao.update(editRole);
	}

	public Role getRole(long id) {
		return dao.getResultById(Role.class, id);
	}

	@Transactional
	public void grant(long id, long[] resourceTreeIds) {
		Role role = dao.getResultById(Role.class, id);
		Set<Long> menuIds = new HashSet<Long>();
		for (Long menuId : resourceTreeIds) {
			menuIds.add(menuId);
		}
		// List<Menu> menus = role.getMenus();
		// for (Menu menu : menus) {
		// menuIds.add(menu.getId());
		// }
		List<Menu> roleMenus = new ArrayList<Menu>();
		for (Iterator<Long> mid = menuIds.iterator(); mid.hasNext();) {
			Menu m = new Menu();
			m.setId(mid.next());
			roleMenus.add(m);
		}
		role.setMenus(roleMenus);
		dao.update(role);
	}
}
