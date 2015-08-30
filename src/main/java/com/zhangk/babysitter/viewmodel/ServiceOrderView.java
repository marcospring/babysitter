package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;

public class ServiceOrderView {
	private long id;
	private String guid;
	private String name;
	private String address;
	private String telephone;
	private String createDate;
	private String serviceBeginDate;
	private String serviceEndDate;
	private String orderPrice;

	public ServiceOrderView() {
	}

	public ServiceOrderView(ServiceOrder order) {
		setId(order.getId());
		setGuid(order.getGuid());
		setName(order.getEmployer().getUsername());
		setAddress(order.getAddress());
		setTelephone(order.getMobilePhone());
		setCreateDate(ExpectedDateCreate.formatDate(order.getCreateDate()));
		setServiceBeginDate(ExpectedDateCreate.formatDate(order
				.getServiceBeginDate()));
		setServiceEndDate(ExpectedDateCreate.formatDate(order
				.getServiceEndDate()));
		setOrderPrice(String.valueOf(order.getOrderPrice()));
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServiceBeginDate() {
		return serviceBeginDate;
	}

	public void setServiceBeginDate(String serviceBeginDate) {
		this.serviceBeginDate = serviceBeginDate;
	}

	public String getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

}
