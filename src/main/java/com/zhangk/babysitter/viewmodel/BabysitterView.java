package com.zhangk.babysitter.viewmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterCredential;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;

public class BabysitterView {
	private long id;
	private String guid;
	private String headUrl;
	private long price;
	private long lowerSalary;
	private String name;
	private String mobilePhone;
	private String cardNo;
	private String identificationNo;
	private String level;
	private long score;
	private long credentialScore;
	private int orderCount;
	private String bankName;
	private String bankCardNo;
	private String bankUserName;
	private CountyView county;
	private String nativePlace;
	private int age;
	private String introduce;
	private long orderCountIndex;
	private long scoreIndex;
	private String nation;
	private String height;
	private String weight;
	private String hobbies;
	private String mandarin;
	private String companyName;
	private String managerName;
	private String companyTelephone;
	private String managerTelephone;
	private long lastLevelScore;
	private int isV;
	private int state;

	private List<CredentialView> credentials;
	private List<PromotionView> promotions;
	private List<BabysitterOrderView> orders;
	private List<RestInfoView> restInfos;
	// private List<BabysitterEvaluateView> evaluates;
	private List<BabysitterImageView> images;

	public BabysitterView(Babysitter babysitter) {
		setId(babysitter.getId());
		setGuid(babysitter.getGuid());
		setHeadUrl(StringUtils.isEmpty(babysitter.getHeadUrl()) ? Constants.DEFAULT_HEAD_URL
				: babysitter.getHeadUrl());
		setPrice(babysitter.getLevel() != null ? babysitter.getLevel()
				.getMoney() : 0);
		setCardNo(babysitter.getCardNo());
		setMobilePhone(babysitter.getMobilePhone());
		setIdentificationNo(babysitter.getIdentificationNo());
		setName(babysitter.getName());
		setLevel(babysitter.getLevel() != null ? babysitter.getLevel()
				.getLevel().getName() : "无");
		setScore(babysitter.getScore());
		setOrderCount(babysitter.getOrders() != null ? babysitter.getOrders()
				.size() : 0);
		setCredentials(getCredentialView(babysitter.getCredentials()));
		setPromotions(getPromotionView(babysitter.getPromotions()));
		setImages(getImageView(babysitter.getCredentials(),
				babysitter.getImages()));
		setOrders(getOrderView(babysitter.getOrders()));
		setRestInfos(getRestView(babysitter.getRestInfos()));
		setBankCardNo(babysitter.getBankCardNo());
		setBankName(babysitter.getBankName());
		setBankUserName(babysitter.getBankUserName());
		setCounty(babysitter.getCounty().view());
		setNativePlace(babysitter.getNativePlace());
		setAge(getTrueAge(babysitter.getBirthday()));
		setIntroduce(babysitter.getIntroduce());
		setCredentialScore(babysitter.getCredentialScore());
		setLowerSalary(babysitter.getLowerSalary());
		setNation(babysitter.getNation());
		setHeight(babysitter.getHeight());
		setWeight(babysitter.getWeight());
		setHobbies(babysitter.getHobbies());
		setMandarin(babysitter.getMandarin());
		setCompanyName(babysitter.getManager() == null ? "无" : babysitter
				.getManager().getCompany() == null ? "无" : babysitter
				.getManager().getCompany().getName());
		setCompanyTelephone(babysitter.getManager() == null ? "无" : babysitter
				.getManager().getCompany() == null ? "无" : babysitter
				.getManager().getCompany().getTelephone());
		setManagerTelephone(babysitter.getManager() == null ? "无" : babysitter
				.getManager().getTelephone());
		setManagerName(babysitter.getManager() == null ? "无" : babysitter
				.getManager().getName());
		setIsV(babysitter.getIsV());
		setState(babysitter.getState());
		// setEvaluates(getEvaluate(babysitter.getEvaluates()));
	}

	private int getTrueAge(Date birthday) {
		if (birthday == null)
			return 0;
		int months = ExpectedDateCreate.monthsBetween(birthday, new Date());
		return months / 12;
	}

	private List<RestInfoView> getRestView(List<RestInfo> restInfos) {
		List<RestInfoView> result = new ArrayList<RestInfoView>();
		for (RestInfo info : restInfos) {
			result.add(info.view());
		}
		return result;
	}

	// private List<BabysitterEvaluateView> getEvaluate(
	// List<BabysitterEvaluate> evaluates) {
	// List<BabysitterEvaluateView> result = new
	// ArrayList<BabysitterEvaluateView>();
	// for (BabysitterEvaluate info : evaluates) {
	// result.add(info.view());
	// }
	// return result;
	// }

	private List<BabysitterOrderView> getOrderView(List<BabysitterOrder> orders) {
		List<BabysitterOrderView> result = new ArrayList<BabysitterOrderView>();
		for (BabysitterOrder info : orders) {
			if (info.isOvld())
				result.add(info.view());
		}
		return result;
	}

	private List<BabysitterImageView> getImageView(
			List<BabysitterCredential> images,
			List<BabysitterImage> babysitterImages) {
		List<BabysitterImageView> result = new ArrayList<BabysitterImageView>();
		// 从月嫂证件中查看是否存在相册。如果存在相册则添加
		for (BabysitterCredential img : images) {
			if (img != null && img.getCredential() != null) {
				if (img.getCredential().getId() == 9
						&& img.getIscheck() == Constants.PASS) {
					BabysitterImage m = new BabysitterImage();
					m.setGuid(img.getGuid());
					m.setUrl(img.getCredentialUrl());
					m.setName(img.getCredential().getName());
					result.add(m.view());
				}
			}
		}
		// 从imageLife中找
		for (BabysitterImage image : babysitterImages) {
			result.add(image.view());
		}

		return result;
	}

	private List<PromotionView> getPromotionView(List<PromotionInfo> promotions) {
		List<PromotionView> result = new ArrayList<PromotionView>();
		Collections.sort(promotions, new Comparator<PromotionInfo>() {
			public int compare(PromotionInfo o1, PromotionInfo o2) {
				return o1.getCreateDate().getTime() >= o2.getCreateDate()
						.getTime() ? 1 : -1;
			};
		});
		for (PromotionInfo info : promotions) {
			result.add(info.view());
		}
		return result;
	}

	private List<CredentialView> getCredentialView(
			List<BabysitterCredential> credentials) {
		List<CredentialView> result = new ArrayList<CredentialView>();
		for (BabysitterCredential credential : credentials) {
			if (credential != null && credential.getCredential() != null
					&& credential.getCredential().getId() != 9) {
				result.add(credential.view());
			}
		}
		return result;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getMandarin() {
		return mandarin;
	}

	public void setMandarin(String mandarin) {
		this.mandarin = mandarin;
	}

	public BabysitterView() {
		// TODO Auto-generated constructor stub
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getHeadUrl() {
		return StringUtils.isEmpty(headUrl) ? "" : Constants.IMG_DOMAIN
				+ headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public List<CredentialView> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<CredentialView> credentials) {
		this.credentials = credentials;
	}

	public List<PromotionView> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<PromotionView> promotions) {
		this.promotions = promotions;
	}

	public List<BabysitterImageView> getImages() {
		return images;
	}

	public void setImages(List<BabysitterImageView> images) {
		this.images = images;
	}

	public List<BabysitterOrderView> getOrders() {
		return orders;
	}

	public void setOrders(List<BabysitterOrderView> orders) {
		this.orders = orders;
	}

	// public List<BabysitterEvaluateView> getEvaluates() {
	// return evaluates;
	// }
	//
	// public void setEvaluates(List<BabysitterEvaluateView> evaluates) {
	// this.evaluates = evaluates;
	// }

	public List<RestInfoView> getRestInfos() {
		return restInfos;
	}

	public void setRestInfos(List<RestInfoView> restInfos) {
		this.restInfos = restInfos;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public CountyView getCounty() {
		return county;
	}

	public void setCounty(CountyView county) {
		this.county = county;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public long getCredentialScore() {
		return credentialScore;
	}

	public void setCredentialScore(long credentialScore) {
		this.credentialScore = credentialScore;
	}

	public long getLowerSalary() {
		return lowerSalary;
	}

	public void setLowerSalary(long lowerSalary) {
		this.lowerSalary = lowerSalary;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public long getOrderCountIndex() {
		return orderCountIndex;
	}

	public void setOrderCountIndex(long orderCountIndex) {
		this.orderCountIndex = orderCountIndex;
	}

	public long getScoreIndex() {
		return scoreIndex;
	}

	public void setScoreIndex(long scoreIndex) {
		this.scoreIndex = scoreIndex;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getCompanyTelephone() {
		return companyTelephone;
	}

	public void setCompanyTelephone(String companyTelephone) {
		this.companyTelephone = companyTelephone;
	}

	public String getManagerTelephone() {
		return managerTelephone;
	}

	public void setManagerTelephone(String managerTelephone) {
		this.managerTelephone = managerTelephone;
	}

	public long getLastLevelScore() {
		return lastLevelScore;
	}

	public void setLastLevelScore(long lastLevelScore) {
		this.lastLevelScore = lastLevelScore;
	}

	public int getIsV() {
		return isV;
	}

	public void setIsV(int isV) {
		this.isV = isV;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
