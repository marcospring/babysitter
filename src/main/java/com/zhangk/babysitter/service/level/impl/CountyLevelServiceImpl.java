package com.zhangk.babysitter.service.level.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.viewmodel.CountyLevelView;

@Service
public class CountyLevelServiceImpl implements CountyLevelService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addCountyLevel(CountyLevel level) {
		dao.add(level);
	}

	public List<CountyLevel> getCountyLevels() {
		String hql = "from CountyLevel";
		return dao.getListResultByHQL(CountyLevel.class, hql);
	}

	public CountyLevel getCountyLevel(String guid) {
		return dao.getResultByGUID(CountyLevel.class, guid);
	}

	public CountyLevel getCountyLevel(long id) {
		return dao.getResultById(CountyLevel.class, id);
	}

	public List<CountyLevelView> countyLevels(String countyGuid) {
		List<CountyLevelView> result = new ArrayList<CountyLevelView>();
		String levelHql = "from CountyLevel cl where cl.county.guid = ?";
		String countHql = "select count(b.id) from Babysitter b where b.level.guid = ?";
		List<CountyLevel> listLevels = dao.getListResultByHQL(
				CountyLevel.class, levelHql, countyGuid);
		for (CountyLevel countyLevel : listLevels) {
			CountyLevelView view = new CountyLevelView();
			view.setGuid(countyLevel.getGuid());
			view.setName(countyLevel.getLevel().getName());
			view.setPrice(String.valueOf(countyLevel.getMoney()));
			long count = dao.getSingleResultByHQL(Long.class, countHql,
					countyLevel.getGuid());
			view.setCount(count);
			result.add(view);
		}
		return result;
	}

}
