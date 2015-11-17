package com.zhangk.babysitter.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterCredential;
import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.service.common.CredentialService;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CredentialView;
import com.zhangk.babysitter.viewmodel.DicCredentialView;

@Service
public class CredentialServiceImpl implements CredentialService {

	@Autowired
	private BaseDao dao;
	@Autowired
	private NoticeService noticeService;

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

	public Pagination<CredentialView> getManageCredentials(
			Pagination<CredentialView> page, String credentialName,
			String babysitterNo, String name) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from BabysitterCredential r where ovld = true ");

		if (!StringUtils.isEmpty(credentialName)) {
			hql.append(" and r.credential.name like ? ");
			params.add("%" + credentialName + "%");
		}
		if (!StringUtils.isEmpty(babysitterNo)) {
			hql.append(" and r.babysitter.cardNo like ? ");
			params.add("%" + babysitterNo + "%");
		}
		if (!StringUtils.isEmpty(name)) {
			hql.append(" and r.babysitter.name like ? ");
			params.add("%" + name + "%");
		}

		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];

		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		hql.append(" order by r.ischeck,r.createDate");
		Pagination<BabysitterCredential> p = dao.getPageResultObjectParams(
				BabysitterCredential.class, hql.toString(), page.getPageNo(),
				page.getPageSize(), objParams);
		List<BabysitterCredential> list = p.getResult();
		List<CredentialView> viewList = new ArrayList<CredentialView>();
		for (BabysitterCredential babysitterCredential : list) {
			CredentialView view = babysitterCredential.view();
			viewList.add(view);
		}

		Pagination<CredentialView> pa = new Pagination<CredentialView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	@Transactional
	public ResultInfo manageCheckCredential(long credentialId, int flag) {

		BabysitterCredential credential = dao.getResultById(
				BabysitterCredential.class, credentialId);
		Babysitter babysitter = credential.getBabysitter();
		Credential dicCredential = credential.getCredential();
		if (babysitter == null)
			return ResultInfo.BABYSITTER_NULL;

		if (dicCredential == null)
			return ResultInfo.CREDENTIAL_NULL;

		// 证件是否可以上传多张
		if (dicCredential.getUploadCountFlag() == Constants.Y) {
			// 是否允许多次积分
			if (dicCredential.getScoreFlag() == Constants.Y) {
				checkCredential(flag, babysitter, dicCredential, credential,
						Constants.Y);
			} else {// 否多次积分
				boolean isNotUploadFlag = true;
				// 判断以前是否上传过该证件
				List<BabysitterCredential> credentials = babysitter
						.getCredentials();
				for (BabysitterCredential babysitterCredential : credentials) {
					Credential compareCredential = babysitterCredential
							.getCredential();
					if (compareCredential.getGuid().equals(
							dicCredential.getGuid())
							&& babysitterCredential.isOvld()
							&& babysitterCredential.getIscheck() == Constants.PASS) {
						isNotUploadFlag = !isNotUploadFlag;
						break;
					}
				}
				// 如果以前没有上传过
				if (isNotUploadFlag) {
					checkCredential(flag, babysitter, dicCredential,
							credential, Constants.Y);
				} else {
					checkCredential(flag, babysitter, dicCredential,
							credential, Constants.N);
				}
			}
		} else {
			boolean isNotUploadFlag = true;
			// 判断以前是否上传过该证件
			// babysitterCredentialBefore为用户以前上传的该证件，当新的证件上传后，该证件删除。
			BabysitterCredential babysitterCredentialBefore = null;
			List<BabysitterCredential> credentials = babysitter
					.getCredentials();
			for (BabysitterCredential babysitterCredential : credentials) {
				Credential compareCredential = babysitterCredential
						.getCredential();
				if (compareCredential.getGuid().equals(dicCredential.getGuid())
						&& babysitterCredential.isOvld()
						&& babysitterCredential.getIscheck() == Constants.PASS) {
					isNotUploadFlag = !isNotUploadFlag;
					babysitterCredentialBefore = babysitterCredential;
					break;
				}
			}
			// 如果以前没有上传过
			if (isNotUploadFlag) {
				checkCredential(flag, babysitter, dicCredential, credential,
						Constants.Y);
			} else {
				if (flag == Constants.PASS) {
					babysitterCredentialBefore.setOvld(false);
					dao.update(babysitterCredentialBefore);
				}
				checkCredential(flag, babysitter, dicCredential, credential,
						Constants.N);
			}
		}

		return ResultInfo.SUCCESS;
	}

	private void checkCredential(int flag, Babysitter babysitter,
			Credential dicCredential, BabysitterCredential credential,
			int addScoreFlag) {
		if (flag == Constants.PASS) {
			StringBuffer message = new StringBuffer();
			message.append(babysitter.getName()).append(",您好：您的")
					.append(dicCredential.getName()).append("已经审核通过！");
			if (addScoreFlag == Constants.Y) {
				// 月嫂加分
				long credentialScore = babysitter.getCredentialScore();
				credentialScore += dicCredential.getScore();
				babysitter.setCredentialScore(credentialScore);
				dao.update(babysitter);
			}
			credential.setIscheck(Constants.PASS);
			dao.update(credential);
			// 发站内信
			noticeService.addNotice("证件审核通过", message.toString(),
					babysitter.getGuid());
		} else {
			StringBuffer message = new StringBuffer();
			message.append(babysitter.getName()).append(",您好：您的")
					.append(dicCredential.getName()).append("审核未通过！");
			// 审核不通过则删除当前上传的证件
			credential.setOvld(false);
			dao.update(credential);
			// 发站内信
			noticeService.addNotice("证件审核未通过", message.toString(),
					babysitter.getGuid());
		}
	}

}
