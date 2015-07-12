package com.zhangk.babysitter.service.babysitter.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.Pagination;

@Service
public class BabysitterServiceImpl implements BabysitterService {
	@Autowired
	private BaseDao dao;

	public List<Babysitter> BabysitterList() {
		String hql = "from Babysitter r ";
		List<Babysitter> list = dao.getListResultByHQL(Babysitter.class, hql);
		return list;
	}

	public Pagination<Babysitter> getPageBabysitterList(
			Pagination<Babysitter> page) {
		String hql = "from Babysitter r";
		String countHql = "select count(r.id) from Babysitter r";

		Pagination<Babysitter> p = dao.getPageResult(Babysitter.class, hql,
				page.getPageNo(), page.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addBabysitter(Babysitter babysitter) {
		dao.add(babysitter);
	}

	@Transactional
	public void deleteBabysitter(long id) {
		dao.delete(Babysitter.class, id);
	}

	@Transactional
	public void updateBabysitter(Babysitter babysitter) {
		dao.update(babysitter);
	}

	public Babysitter getBabysitter(long id) {
		return dao.getResultById(Babysitter.class, id);
	}

	public Pagination<BabysitterOrder> getPageOrderList(
			Pagination<BabysitterOrder> page, String name) {
		String hql = "from BabysitterOrder r ";
		String countHql = "select count(r.id) from BabysitterOrder r ";
		if (!StringUtils.isEmpty(name)) {
			hql = "from BabysitterOrder r where r.babysitter.name like ?";
			countHql = "select count(r.id) from BabysitterOrder r where r.babysitter.name like ?";
			name = "'%" + name + "%'";
		}
		Pagination<BabysitterOrder> p = dao.getPageResult(
				BabysitterOrder.class, hql, page.getPageNo(),
				page.getPageSize(), name);
		Long count = dao.getSingleResultByHQL(Long.class, countHql, name);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addOrder(BabysitterOrder order) {
		dao.add(order);
	}
}
