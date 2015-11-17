package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.zhangk.babysitter.viewmodel.BabysitterOrderView;

@Entity
@Table(name = "babysitter_babysitter_order")
public class BabysitterOrder implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean ovld;
	private String guid;
	private String orderId;
	private Date createDate;
	private Date updateDate;
	private int state;
	private Date serviceBeginDate;
	private Date serviceEndDate;
	private long orderPrice;
	private long orderFrontPrice;
	private Babysitter babysitter;
	private Employer employer;
	private String employerName;
	private String employerAddress;
	private String employerTelephone;
	private String evaluation;
	private List<BabysitterOrderRecordInfo> infos;
	private int score;

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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
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
	@JoinColumn(name = "employer_id")
	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public BabysitterOrderView view() {
		return new BabysitterOrderView(this);
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

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmployerAddress() {
		return employerAddress;
	}

	public void setEmployerAddress(String employerAddress) {
		this.employerAddress = employerAddress;
	}

	public String getEmployerTelephone() {
		return employerTelephone;
	}

	public void setEmployerTelephone(String employerTelephone) {
		this.employerTelephone = employerTelephone;
	}

	public long getOrderFrontPrice() {
		return orderFrontPrice;
	}

	public void setOrderFrontPrice(long orderFrontPrice) {
		this.orderFrontPrice = orderFrontPrice;
	}

	@OneToMany(mappedBy = "babysitterOrder")
	public List<BabysitterOrderRecordInfo> getInfos() {
		if (infos == null)
			infos = new ArrayList<BabysitterOrderRecordInfo>();
		return infos;
	}

	public void setInfos(List<BabysitterOrderRecordInfo> infos) {
		this.infos = infos;
	}

	public static BabysitterOrder getInstance() {
		BabysitterOrder o = new BabysitterOrder();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
