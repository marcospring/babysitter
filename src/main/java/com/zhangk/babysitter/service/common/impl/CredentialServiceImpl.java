package com.zhangk.babysitter.service.common.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.service.common.CredentialService;

@Service
public class CredentialServiceImpl implements CredentialService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addCredential(Credential credential) {
		dao.add(credential);
	}

	public List<Credential> getCredentials() {
		String hql = "from Credential";
		return dao.getListResultByHQL(Credential.class, hql);
	}

	public Credential getCredential(String guid) {
		return dao.getResultByGUID(Credential.class, guid);
	}

	public Credential getCredential(long id) {
		return dao.getResultById(Credential.class, id);
	}

}
