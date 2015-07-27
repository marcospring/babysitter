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
import com.zhangk.babysitter.viewmodel.BabysitterEvaluateView;

@Entity
@Table(name = "babysitter_evaluate")
public class BabysitterEvaluate implements Serializable {
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
	private Babysitter babysitter;
	private BabysitterOrder order;
	private int score;
	private String msg;

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

	@ManyToOne
	@JoinColumn(name = "babysitter_id")
	public Babysitter getBabysitter() {
		return babysitter;
	}

	public void setBabysitter(Babysitter babysitter) {
		this.babysitter = babysitter;
	}

	@ManyToOne
	@JoinColumn(name = "order_id")
	public BabysitterOrder getOrder() {
		return order;
	}

	public void setOrder(BabysitterOrder order) {
		this.order = order;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BabysitterEvaluateView view() {
		BabysitterEvaluateView view = new BabysitterEvaluateView();
		view.setAddress(employer.getCounty().getName() + " "
				+ employer.getAddress());
		view.setBeginDate(order.getServiceBeginDate());
		view.setEndDate(order.getServiceEndDate());
		view.setEmployerName(employer.getUsername());
		view.setGuid(guid);
		view.setMsg(msg);
		view.setOrderId(String.valueOf(order.getId()));
		view.setScore(score);
		view.setTelephone(employer.getMobilePhone());
		return view;
	}

	public static BabysitterEvaluate getInstance() {
		BabysitterEvaluate o = new BabysitterEvaluate();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
