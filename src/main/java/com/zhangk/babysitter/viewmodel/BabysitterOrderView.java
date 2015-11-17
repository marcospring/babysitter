package com.zhangk.babysitter.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.BabysitterOrderRecordInfo;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class BabysitterOrderView {
	private long id;
	private String guid;
	private Date beginDate;
	private Date endDate;
	private String orderId;
	private String employerName;
	private String babysitterName;
	private String orderFrontPrice;
	private String price;
	private int state;
	private String address;
	private String telephone;
	private String evaluation;
	private int score;
	private String babysitterGuid;
	private List<BabysitterOrderRecordInfoView> orderRecords;

	public BabysitterOrderView() {
		// TODO Auto-generated constructor stub
	}

	public BabysitterOrderView(BabysitterOrder order) {
		setId(order.getId());
		setGuid(order.getGuid());
		setBeginDate(order.getServiceBeginDate());
		setEndDate(order.getServiceEndDate());
		setOrderFrontPrice(String.valueOf(order.getOrderFrontPrice()));
		setPrice(String.valueOf(order.getOrderPrice()));
		setEmployerName(order.getEmployerName());
		setBabysitterName(order.getBabysitter().getName());
		setState(order.getState());
		setAddress(order.getEmployerAddress());
		setTelephone(order.getEmployerTelephone());
		setEvaluation(order.getEvaluation());
		setScore(order.getScore());
		setOrderId(order.getOrderId());
		setBabysitterGuid(order.getBabysitter().getGuid());
		List<BabysitterOrderRecordInfoView> views = new ArrayList<BabysitterOrderRecordInfoView>();
		for (BabysitterOrderRecordInfo babysitterOrderRecordInfo : order
				.getInfos()) {
			views.add(babysitterOrderRecordInfo.view());
		}
		setOrderRecords(views);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderFrontPrice() {
		return orderFrontPrice;
	}

	public void setOrderFrontPrice(String orderFrontPrice) {
		this.orderFrontPrice = orderFrontPrice;
	}

	public String getBabysitterGuid() {
		return babysitterGuid;
	}

	public void setBabysitterGuid(String babysitterGuid) {
		this.babysitterGuid = babysitterGuid;
	}

	public String getBabysitterName() {
		return babysitterName;
	}

	public void setBabysitterName(String babysitterName) {
		this.babysitterName = babysitterName;
	}

	public List<BabysitterOrderRecordInfoView> getOrderRecords() {
		return orderRecords;
	}

	public void setOrderRecords(List<BabysitterOrderRecordInfoView> orderRecords) {
		this.orderRecords = orderRecords;
	}

}
