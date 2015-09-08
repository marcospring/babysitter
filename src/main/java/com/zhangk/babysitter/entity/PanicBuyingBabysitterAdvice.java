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

@Entity
@Table(name = "babysitter_panicbuy_advice")
public class PanicBuyingBabysitterAdvice implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private Babysitter babysitter;
	private ServiceOrder serviceOrder;
	private boolean isAdvice;
	private boolean isOver;

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
	@JoinColumn(name = "babysitter_id")
	public Babysitter getBabysitter() {
		return babysitter;
	}

	public void setBabysitter(Babysitter babysitter) {
		this.babysitter = babysitter;
	}

	@ManyToOne
	@JoinColumn(name = "service_order_id")
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public boolean getIsAdvice() {
		return isAdvice;
	}

	public void setIsAdvice(boolean isAdvice) {
		this.isAdvice = isAdvice;
	}

	public boolean getIsOver() {
		return isOver;
	}

	public void setIsOver(boolean isOver) {
		this.isOver = isOver;
	}

	public static PanicBuyingBabysitterAdvice getInstance() {
		PanicBuyingBabysitterAdvice o = new PanicBuyingBabysitterAdvice();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
