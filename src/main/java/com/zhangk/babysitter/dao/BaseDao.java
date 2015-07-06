package com.zhangk.babysitter.dao;

import java.util.List;

import org.hibernate.Session;

import com.zhangk.babysitter.utils.common.Pagination;

public interface BaseDao {
	Session getSession() throws RuntimeException;

	void add(Object o) throws RuntimeException;

	void update(Object o) throws RuntimeException;

	<T> void delete(Class<T> clazz, long id) throws RuntimeException;

	<T> void delete(Class<T> clazz, String guid) throws RuntimeException;

	<T> T getResultByGUID(Class<T> clazz, String guid) throws RuntimeException;

	<T> T getResultById(Class<T> clazz, long id) throws RuntimeException;

	<T> T getSingleResultByHQL(Class<T> clazz, String hql, Object... param)
			throws RuntimeException;

	<T> List<T> getListResultByHQL(Class<T> clazz, String hql, Object... param)
			throws RuntimeException;

	<T> List<T> getListResultByHQL(Class<T> clazz, String hql)
			throws RuntimeException;

	<T> Pagination<T> getPageResult(Class<T> clazz, String hql, int pageNo,
			int pageSize, Object... param) throws RuntimeException;

}
