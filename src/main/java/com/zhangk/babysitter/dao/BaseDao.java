package com.zhangk.babysitter.dao;

import org.hibernate.Session;

public interface BaseDao {
	Session getSession();

	<T> T getSingleResultByHQL(Class<T> clazz, String hql, Object... param)
			throws RuntimeException;
}
