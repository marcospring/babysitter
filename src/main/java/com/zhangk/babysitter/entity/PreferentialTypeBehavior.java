package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;
import com.zhangk.babysitter.viewmodel.PreferentialTypeBehaviorView;

/**
 * 优惠卷类型行优惠行为表
 *
 * @author marcospring
 *
 */
@Entity
@Table(name = "babysitter_preferential_type_behavior")
public class PreferentialTypeBehavior implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private PreferentialType type;
	private PreferentialBehavior behavior;
	private long preferentialMoney;

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
	@JoinColumn(name = "type_id")
	public PreferentialType getType() {
		return type;
	}

	public void setType(PreferentialType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "behavior_id")
	public PreferentialBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(PreferentialBehavior behavior) {
		this.behavior = behavior;
	}

	public long getPreferentialMoney() {
		return preferentialMoney;
	}

	public void setPreferentialMoney(long preferentialMoney) {
		this.preferentialMoney = preferentialMoney;
	}

	public PreferentialTypeBehaviorView view() {
		return new PreferentialTypeBehaviorView(this);
	}

	public static PreferentialTypeBehavior getInstance() {
		PreferentialTypeBehavior o = new PreferentialTypeBehavior();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}

}
