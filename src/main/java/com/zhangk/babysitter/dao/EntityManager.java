package com.zhangk.babysitter.dao;

import com.zhangk.babysitter.entity.Role;

public class EntityManager {
	public <T> T createEntity(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public static void main(String[] args) {
		EntityManager a = new EntityManager();
		a.createEntity(Role.class);
	}
}
