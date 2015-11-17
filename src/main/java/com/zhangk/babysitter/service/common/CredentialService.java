package com.zhangk.babysitter.service.common;

import java.util.List;

import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CredentialView;
import com.zhangk.babysitter.viewmodel.DicCredentialView;

public interface CredentialService {
	void addCredential(Credential credential);

	List<DicCredentialView> getCredentials();

	Credential getCredential(String guid);

	Credential getCredential(long id);

	Pagination<CredentialView> getManageCredentials(
			Pagination<CredentialView> page, String credentialName,
			String babysitterNo, String name);

	ResultInfo manageCheckCredential(long credentialId, int flag);
}
