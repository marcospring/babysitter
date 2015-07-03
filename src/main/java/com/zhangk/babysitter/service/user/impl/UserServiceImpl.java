package com.zhangk.babysitter.service.user.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Transactional
	public UserInfo login(String username, String password) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserInfo u where u.username = :username";
		UserInfo userinfo = (UserInfo) session.createQuery(hql)
				.setString("username", username).uniqueResult();
		if (userinfo != null) {
			String dbPassword = userinfo.getPassword();
			if (password.equals(dbPassword)) {
				return userinfo;
			}
		}
		return null;
	}

}
