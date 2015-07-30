package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;
import com.zhangk.babysitter.viewmodel.RecommendInfoView;

@Entity
@Table(name = "babysitter_recommend_info")
public class RecommendInfo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private County county;
	private List<Babysitter> babysitters;

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

	@ManyToOne
	@JoinColumn(name = "county_id")
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@ManyToMany
	@JoinTable(name = "recommend_babysitter", joinColumns = @JoinColumn(name = "recommend_id"), inverseJoinColumns = @JoinColumn(name = "babysitter_id"))
	public List<Babysitter> getBabysitters() {
		return babysitters;
	}

	public void setBabysitters(List<Babysitter> babysitters) {
		this.babysitters = babysitters;
	}

	public RecommendInfoView view() {
		return new RecommendInfoView(this);
	}

	public static RecommendInfo getInstance() {
		RecommendInfo o = new RecommendInfo();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
