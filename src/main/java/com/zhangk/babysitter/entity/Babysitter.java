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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Entity
@Table(name = "babysister_babysister")
public class Babysitter implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private String username;
	private String password;
	private String name;
	private String mobilePhone;
	private String headUrl;
	private long score;
	private CustomerManager manager;
	private County county;
	private CountyLevel level;
	private String cardNo;
	private long lowerSalary;
	private List<BabysitterCredential> credentials;
	private List<PromotionInfo> promotions;
	private List<BabysitterOrder> orders;
	private List<BabysitterImage> images;
	private List<BabysitterEvaluate> evaluates;
	private List<RecommendInfo> recommends;
	private List<RestInfo> restInfos;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
	@JoinColumn(name = "county_id")
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@ManyToOne
	@JoinColumn(name = "level_id")
	public CountyLevel getLevel() {
		return level;
	}

	public void setLevel(CountyLevel level) {
		this.level = level;
	}

	@OneToMany(mappedBy = "babysitter")
	public List<BabysitterCredential> getCredentials() {
		if (credentials == null)
			credentials = new ArrayList<BabysitterCredential>();
		return credentials;
	}

	public void setCredentials(List<BabysitterCredential> credentials) {
		this.credentials = credentials;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	@ManyToOne
	@JoinColumn(name = "manager_id")
	public CustomerManager getManager() {
		return manager;
	}

	public void setManager(CustomerManager manager) {
		this.manager = manager;
	}

	@ManyToMany
	@JoinTable(name = "babysitter_promotion", joinColumns = @JoinColumn(name = "babysitter_id"), inverseJoinColumns = @JoinColumn(name = "promotioninfo_id"))
	public List<PromotionInfo> getPromotions() {
		if (promotions == null) {
			promotions = new ArrayList<PromotionInfo>();
		}
		return promotions;
	}

	public void setPromotions(List<PromotionInfo> promotions) {
		this.promotions = promotions;
	}

	@OneToMany(mappedBy = "babysitter")
	public List<BabysitterOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<BabysitterOrder> orders) {
		this.orders = orders;
	}

	@OneToMany(mappedBy = "babysitter")
	public List<BabysitterImage> getImages() {
		return images;
	}

	public void setImages(List<BabysitterImage> images) {
		this.images = images;
	}

	@OneToMany(mappedBy = "babysitter")
	public List<BabysitterEvaluate> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(List<BabysitterEvaluate> evaluates) {
		this.evaluates = evaluates;
	}

	@ManyToMany(mappedBy = "babysitters")
	public List<RecommendInfo> getRecommends() {
		return recommends;
	}

	public void setRecommends(List<RecommendInfo> recommends) {
		this.recommends = recommends;
	}

	public BabysitterView view() {
		return new BabysitterView(this);
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@OneToMany(mappedBy = "babysitter")
	public List<RestInfo> getRestInfos() {
		return restInfos;
	}

	public void setRestInfos(List<RestInfo> restInfos) {
		this.restInfos = restInfos;
	}

	public long getLowerSalary() {
		return lowerSalary;
	}

	public void setLowerSalary(long lowerSalary) {
		this.lowerSalary = lowerSalary;
	}

	public static Babysitter getInstance() {
		Babysitter o = new Babysitter();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
