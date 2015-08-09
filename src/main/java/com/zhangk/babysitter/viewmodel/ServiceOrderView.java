package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;

public class ServiceOrderView {
	private String guid;
	private String name;
	private String orderId;
	private String address;
	private String telephone;
	private String createDate;

	public ServiceOrderView() {
		// TODO Auto-generated constructor stub
	}

	public ServiceOrderView(ServiceOrder order) {
		setGuid(order.getGuid());
		setName(order.getEmployer().getUsername());
		setAddress(order.getAddress());
		setTelephone(order.getMobilePhone());
		setCreateDate(ExpectedDateCreate.formatDate(order.getCreateDate()));
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

}
