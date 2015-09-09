package com.zhangk.babysitter.service.level.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Level;
import com.zhangk.babysitter.service.level.LevelService;

@Service
public class LevelServiceImpl implements LevelService {
	@Autowired
	private BaseDao dao;

	public List<Level> LevelList() {
		String hql = "from  Level r ";
		List<Level> list = dao.getListResultByHQL(Level.class, hql);
		return list;
	}

	@Transactional
	public void addLevel(Level level) {
		dao.add(level);
	}

	@Transactional
	public void deleteLevel(long id) {
		dao.delete(Level.class, id);
	}

	@Transactional
	public void updateLevel(Level level) {
		dao.update(level);
	}

	public Level getLevel(long id) {
		return dao.getResultById(Level.class, id);
	}

	@Transactional
	public void updateLevel(String id, String name, String score, String money) {
		long idl = Long.valueOf(id);
		Level level = dao.getResultById(Level.class, idl);
		if (!StringUtils.isEmpty(name))
			level.setName(name);
		if (!StringUtils.isEmpty(score))
			level.setScore(Long.valueOf(score));
		if (!StringUtils.isEmpty(money))
			level.setScore(Long.valueOf(money));
		dao.update(level);
	}

}
