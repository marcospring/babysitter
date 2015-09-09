package com.zhangk.babysitter.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.viewmodel.CountyView;

@Service
public class CountyServiceImpl implements CountyService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addCounty(County county, long parentId) {
		County parent = null;
		if (parentId != 0)
			parent = dao.getResultById(County.class, parentId);
		county.setParent(parent);
		dao.add(county);
	}

	public List<County> getCountyList() {
		String hql = "from County";
		List<County> list = dao.getListResultByHQL(County.class, hql);

		return list;
	}

	public County getCounty(String guid) {
		County county = dao.getResultByGUID(County.class, guid);
		return county;
	}

	public County getCounty(long id) {
		County county = dao.getResultById(County.class, id);
		return county;
	}

	@Transactional
	public void deleteCounty(long id) {
		dao.delete(County.class, id);
	}

	@Transactional
	public void updateCounty(County county) {
		dao.update(county);
	}

	public List<CountyView> getCountyViewList() {
		List<County> list = getCountyList();
		List<CountyView> views = new ArrayList<CountyView>();
		for (County county : list) {
			views.add(county.view());
		}
		return views;
	}

	@Transactional
	public void updateCounty(String id, String name, String shortName, String pid) {
		long idl = Long.valueOf(id);
		County county = dao.getResultById(County.class, idl);
		if (!StringUtils.isEmpty(name))
			county.setName(name);
		if (!StringUtils.isEmpty(shortName))
			county.setShortName(shortName);
		if (StringUtils.isEmpty(pid)) {
			pid = "0";
		}
		long parentId = Long.valueOf(pid);
		County parent = dao.getResultById(County.class, parentId);
		county.setParent(parent);
		dao.update(county);
	}

}
