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

/**
 * 优惠卷领取信息表，行为从表，优惠卷可能有多种行为，如扣减、返现等。
 *
 * @author marcospring
 *
 */
@Entity
@Table(name = "babysitter_receive_behavior")
public class ReceiveBehavior implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private PreferentialReceiveInfo info;
	private PreferentialBehavior beavior;
	private int state;

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
	@JoinColumn(name = "info_id")
	public PreferentialReceiveInfo getInfo() {
		return info;
	}

	public void setInfo(PreferentialReceiveInfo info) {
		this.info = info;
	}

	@ManyToOne
	@JoinColumn(name = "behavior_id")
	public PreferentialBehavior getBeavior() {
		return beavior;
	}

	public void setBeavior(PreferentialBehavior beavior) {
		this.beavior = beavior;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static ReceiveBehavior getInstance() {
		ReceiveBehavior o = new ReceiveBehavior();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
