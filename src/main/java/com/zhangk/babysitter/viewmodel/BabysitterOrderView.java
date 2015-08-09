package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class BabysitterOrderView {
	private String guid;
	private Date beginDate;
	private Date endDate;
	private String orderId;
	private String employerName;
	private String price;
	private int state;
	private String address;
	private String telephone;
	private String evaluation;
	private int score;

	public BabysitterOrderView() {
		// TODO Auto-generated constructor stub
	}

	public BabysitterOrderView(BabysitterOrder order) {
		BabysitterOrderView view = new BabysitterOrderView();
		view.setGuid(order.getGuid());
		view.setBeginDate(order.getServiceBeginDate());
		view.setEndDate(order.getServiceEndDate());
		view.setPrice(String.valueOf(order.getOrderPrice()));
		view.setEmployerName(order.getEmployer().getUsername());
		view.setState(order.getState());
		view.setAddress(order.getEmployer().getAddress());
		view.setTelephone(order.getEmployer().getMobilePhone());
		view.setEvaluation(order.getEvaluation());
		view.setScore(order.getScore());
		view.setOrderId(order.getOrderId());

	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
