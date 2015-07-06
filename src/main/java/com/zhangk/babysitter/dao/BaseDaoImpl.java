package com.zhangk.babysitter.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhangk.babysitter.utils.common.Pagination;

@Repository
public class BaseDaoImpl implements BaseDao {
	private Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Autowired
	private SessionFactory factory;

	public Session getSession() {
		return factory.getCurrentSession();
	}

	public <T> T getSingleResultByHQL(Class<T> clazz, String hql,
			Object... param) throws RuntimeException {
		T result;
		try {
			Session session = getSession();
			log.info("执行HQL：{}", hql);
			Query query = session.createQuery(hql);
			buildParam(query, param);
			result = clazz.cast(query.uniqueResult());

		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "getSingleResultByHQL",
					e.getMessage());
			throw e;
		}
		return result;
	}

	private void buildParam(Query query, Object[] param) {
		if (param == null || param.length == 0)
			return;
		for (int i = 0; i < param.length; i++) {
			query.setParameter(i, param[i]);
		}
	}

	public <T> T getResultByGUID(Class<T> clazz, String guid)
			throws RuntimeException {
		T result = null;
		try {
			Session session = getSession();
			String className = clazz.getName();
			StringBuilder hql = new StringBuilder();
			hql.append("from ").append(className).append(" where ")
					.append(" guid = ").append(":guid ");
			log.info("执行HQL：{}", hql);
			Query query = session.createQuery(hql.toString()).setParameter(
					"guid", guid);
			result = clazz.cast(query.uniqueResult());
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "getResultByGUID", e.getMessage());
			throw e;
		}
		return result;
	}

	public <T> T getResultById(Class<T> clazz, long id) throws RuntimeException {
		T result;
		try {
			Session session = getSession();
			result = clazz.cast(session.get(clazz, id));
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "getResultById", e.getMessage());
			throw e;
		}
		return result;
	}

	public void add(Object o) {
		try {
			Session session = getSession();
			session.save(o);
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "add", e.getMessage());
			throw e;
		}
	}

	public void update(Object o) {
		try {
			Session session = getSession();
			session.saveOrUpdate(o);
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "update", e.getMessage());
			throw e;
		}
	}

	public <T> void delete(Class<T> clazz, long id) {
		try {
			Session session = getSession();
			session.delete(getResultById(clazz, id));
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "delete", e.getMessage());
			throw e;
		}
	}

	public <T> void delete(Class<T> clazz, String guid) {
		try {
			Session session = getSession();
			session.delete(getResultByGUID(clazz, guid));
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "delete", e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> Pagination<T> getPageResult(Class<T> clazz, String hql,
			int pageNo, int pageSize, Object... param) throws RuntimeException {
		List<T> list = null;
		Pagination<T> page;
		try {
			Session session = getSession();
			log.info("执行HQL：{}", hql);
			Query query = session.createQuery(hql);
			buildParam(query, param);
			list = query.list();
			page = new Pagination<T>(list, pageNo, pageSize);
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "getPageResult", e.getMessage());
			throw e;
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListResultByHQL(Class<T> clazz, String hql,
			Object... param) throws RuntimeException {
		List<T> list = null;
		try {
			Session session = getSession();
			log.info("执行HQL：{}", hql);
			Query query = session.createQuery(hql);
			buildParam(query, param);
			list = query.list();
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "getListResultByHQL", e.getMessage());
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListResultByHQL(Class<T> clazz, String hql)
			throws RuntimeException {
		List<T> list = null;
		try {
			Session session = getSession();
			log.info("执行HQL：{}", hql);
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (RuntimeException e) {
			log.error("执行basedao#{}出错：{}", "getListResultByHQL", e.getMessage());
			throw e;
		}
		return list;
	}
}
