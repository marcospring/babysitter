package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;
import com.zhangk.babysitter.viewmodel.PreferentialTypeView;

/**
 * 优惠卷种类
 *
 * @author marcospring
 *
 */
@Entity
@Table(name = "babysitter_preferential_type")
public class PreferentialType implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String guid;
	private boolean ovld;
	private Date createDate;
	private Date updateDate;
	private String name;
	private Date endDate;
	private List<PreferentialTypeBehavior> behaviors;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public boolean isOvld() {
		return ovld;
	}

	public void setOvld(boolean ovld) {
		this.ovld = ovld;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@OneToMany(mappedBy = "type")
	public List<PreferentialTypeBehavior> getBehaviors() {
		if (behaviors == null)
			behaviors = new ArrayList<PreferentialTypeBehavior>();
		return behaviors;
	}

	public void setBehaviors(List<PreferentialTypeBehavior> behaviors) {
		this.behaviors = behaviors;
	}

	public PreferentialTypeView view() {
		return new PreferentialTypeView(this);
	}

	public static PreferentialType getInstance() {
		PreferentialType o = new PreferentialType();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
