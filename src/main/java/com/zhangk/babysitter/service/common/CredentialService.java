package com.zhangk.babysitter.service.common;

import java.util.List;

import com.zhangk.babysitter.entity.Credential;

public interface CredentialService {
	void addCredential(Credential credential);

	List<Credential> getCredentials();

	Credential getCredential(String guid);

	Credential getCredential(long id);
}
