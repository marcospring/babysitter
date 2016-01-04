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
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

@Entity
@Table(name = "babysitter_service_order")
public class ServiceOrder implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private Employer employer;
	private String orderGuid;
	private County county;
	private String address;
	private String employerName;
	private String mobilePhone;
	private boolean over = true;
	private long orderPrice;
	private double rate;
	private Date serviceBeginDate;
	private Date serviceEndDate;

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
	@JoinColumn(name = "employer_id")
	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Date getServiceBeginDate() {
		return serviceBeginDate;
	}

	public void setServiceBeginDate(Date serviceBeginDate) {
		this.serviceBeginDate = serviceBeginDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	@ManyToOne
	@JoinColumn(name = "county_id")
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public ServiceOrderView view() {
		return new ServiceOrderView(this);
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getOrderGuid() {
		return orderGuid;
	}

	public void setOrderGuid(String orderGuid) {
		this.orderGuid = orderGuid;
	}

	public boolean getOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public static ServiceOrder getInstance() {
		ServiceOrder o = new ServiceOrder();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
