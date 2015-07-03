package com.zhangk.babysitter.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl implements BaseDao {
	// private Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Autowired
	private SessionFactory factory;

	public Session getSession() {
		return factory.getCurrentSession();
	}

	public <T> T getSingleResultByHQL(Class<T> clazz, String hql,
			Object... param) throws RuntimeException {
		// try {
		// Session session = getSession();
		// log.info("执行HQL：{}", hql);
		// Query query = session.createQuery(hql);
		// buildParam(query, hql, param);
		// // T result = clazz.cast(arg0)
		// } catch (RuntimeException e) {
		// throw e;
		// }
		return null;
	}

}
