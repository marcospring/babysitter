package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.BabysitterCredential;
import com.zhangk.babysitter.utils.common.Constants;

public class CredentialView {
	private String id;
	private String guid;
	private String dicGuid;
	private String name;
	private String yuesaoid;
	private String babysitterLevel;
	private String babysitterTelephone;
	private String babysitterCounty;
	private String babysitterNo;
	private int check;
	private int credentialType;
	private String babysitterName;
	private int score;
	private String url;

	public CredentialView() {
	}

	public CredentialView(BabysitterCredential credential) {
		setId(String.valueOf(credential.getId()));
		setName(credential.getCredential() == null ? "无" : credential
				.getCredential().getName());
		setBabysitterName(credential.getBabysitter() == null ? "无" : credential
				.getBabysitter().getName());
		setGuid(credential.getGuid());
		setDicGuid(credential.getCredential() == null ? "" : credential
				.getCredential().getGuid());
		setCheck(credential.getIscheck());
		setScore(credential.getCredential() == null ? 0 : credential
				.getCredential().getScore());
		setCredentialType(credential.getCredential() == null ? Constants.CREDENTIAL_TYPE_CREDENTIAL
				: credential.getCredential().getCredentialType());
		if (credential.getCredential().getIsDisplayPhoto() == Constants.Y) {
			setUrl(Constants.IMG_DOMAIN + credential.getCredentialUrl());
		}
		setBabysitterCounty(credential.getBabysitter() == null ? "未知"
				: credential.getBabysitter().getCounty() == null ? "未知"
						: credential.getBabysitter().getCounty().getName());
		setBabysitterLevel(credential.getBabysitter() == null ? "未知"
				: credential.getBabysitter().getLevel() != null ? credential
						.getBabysitter().getLevel().getLevel().getName() : "无");
		setBabysitterTelephone(credential.getBabysitter() == null ? "未知"
				: credential.getBabysitter().getMobilePhone());
		setBabysitterNo(credential.getBabysitter() == null ? "未知" : credential
				.getBabysitter().getCardNo());
		setYuesaoid(credential.getBabysitter() == null ? "未知" : String
				.valueOf(credential.getBabysitter().getId()));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getBabysitterName() {
		return babysitterName;
	}

	public void setBabysitterName(String babysitterName) {
		this.babysitterName = babysitterName;
	}

	public String getBabysitterLevel() {
		return babysitterLevel;
	}

	public void setBabysitterLevel(String babysitterLevel) {
		this.babysitterLevel = babysitterLevel;
	}

	public String getBabysitterTelephone() {
		return babysitterTelephone;
	}

	public void setBabysitterTelephone(String babysitterTelephone) {
		this.babysitterTelephone = babysitterTelephone;
	}

	public String getBabysitterCounty() {
		return babysitterCounty;
	}

	public void setBabysitterCounty(String babysitterCounty) {
		this.babysitterCounty = babysitterCounty;
	}

	public String getBabysitterNo() {
		return babysitterNo;
	}

	public void setBabysitterNo(String babysitterNo) {
		this.babysitterNo = babysitterNo;
	}

	public String getYuesaoid() {
		return yuesaoid;
	}

	public void setYuesaoid(String yuesaoid) {
		this.yuesaoid = yuesaoid;
	}

}
