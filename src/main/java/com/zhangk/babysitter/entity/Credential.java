package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;

@Entity
@Table(name = "babysitter_dic_credential")
public class Credential implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private String name;
	private int credentialType;
	private int score;
	private List<BabysitterCredential> babysitters;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isOvld() {
		return ovld;
	}

	public void setOvld(boolean ovld) {
		this.ovld = ovld;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "credential")
	public List<BabysitterCredential> getBabysitters() {
		return babysitters;
	}

	public void setBabysitters(List<BabysitterCredential> babysitters) {
		this.babysitters = babysitters;
	}

	public int getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static Credential getInstance() {
		Credential o = new Credential();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
