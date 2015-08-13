package com.zhangk.babysitter.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.service.common.CredentialService;
import com.zhangk.babysitter.viewmodel.DicCredentialView;

@Service
public class CredentialServiceImpl implements CredentialService {

	@Autowired
	private BaseDao dao;

	@Transactional
	public void addCredential(Credential credential) {
		dao.add(credential);
	}

	public List<DicCredentialView> getCredentials() {
		List<DicCredentialView> result = new ArrayList<DicCredentialView>();
		String hql = "from Credential";
		List<Credential> list = dao.getListResultByHQL(Credential.class, hql);
		for (Credential credential : list) {
			result.add(credential.view());
		}
		return result;
	}

	public Credential getCredential(String guid) {
		return dao.getResultByGUID(Credential.class, guid);
	}

	public Credential getCredential(long id) {
		return dao.getResultById(Credential.class, id);
	}

}
