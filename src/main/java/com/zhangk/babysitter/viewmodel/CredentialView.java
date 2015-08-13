package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.BabysitterCredential;
import com.zhangk.babysitter.utils.common.Constants;

public class CredentialView {
	private String guid;
	private String dicGuid;
	private String name;
	private int check;
	private int credentialType;
	private int score;
	private String url;

	public CredentialView() {
		// TODO Auto-generated constructor stub
	}

	public CredentialView(BabysitterCredential credential) {
		setName(credential.getCredential() == null ? "无" : credential
				.getCredential().getName());
		setGuid(credential.getGuid());
		setDicGuid(credential.getCredential() == null ? "" : credential
				.getCredential().getGuid());
		setCheck(credential.getIscheck());
		setScore(credential.getCredential() == null ? 0 : credential
				.getCredential().getScore());
		setCredentialType(credential.getCredential() == null ? Constants.CREDENTIAL_TYPE_CREDENTIAL
				: credential.getCredential().getCredentialType());
		setUrl(Constants.IMG_DOMAIN + credential.getCredentialUrl());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDicGuid() {
		return dicGuid;
	}

	public void setDicGuid(String dicGuid) {
		this.dicGuid = dicGuid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public int getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}

}
