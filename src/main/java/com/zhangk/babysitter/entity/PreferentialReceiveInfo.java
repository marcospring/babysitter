package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;

/**
 * 优惠卷领取信息表
 *
 * @author marcospring
 *
 */
@Entity
@Table(name = "babysitter_preferential_receiver")
public class PreferentialReceiveInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private String toUserPhone;
	private PreferentialPromoter promoter;
	private List<ReceiveBehavior> behaviors;

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

	public String getToUserPhone() {
		return toUserPhone;
	}

	public void setToUserPhone(String toUserPhone) {
		this.toUserPhone = toUserPhone;
	}

	@ManyToOne
	@JoinColumn(name = "promoter_id")
	public PreferentialPromoter getPromoter() {
		return promoter;
	}

	public void setPromoter(PreferentialPromoter promoter) {
		this.promoter = promoter;
	}

	@OneToMany(mappedBy = "info")
	public List<ReceiveBehavior> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(List<ReceiveBehavior> behaviors) {
		this.behaviors = behaviors;
	}

	public static PreferentialReceiveInfo getInstantce() {
		PreferentialReceiveInfo o = new PreferentialReceiveInfo();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}

}
