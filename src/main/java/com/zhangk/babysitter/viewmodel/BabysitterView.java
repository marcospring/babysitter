package com.zhangk.babysitter.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.entity.PromotionInfo;

public class BabysitterView {
	private String guid;
	private String headUrl;
	private long price;
	private String name;
	private String cardNo;
	private String level;
	private long score;
	private int orderCount;

	private List<Map<String, String>> credentials;
	private List<Map<String, String>> promotions;
	private List<Map<String, String>> images;

	public BabysitterView(Babysitter babysitter) {
		setGuid(babysitter.getGuid());
		setHeadUrl(babysitter.getHeadUrl());
		setPrice(babysitter.getLevel().getMoney());
		setName(babysitter.getName());
		setLevel(babysitter.getLevel().getLevel().getName());
		setScore(babysitter.getScore());
		setCredentials(getCredentialView(babysitter.getCredentials()));
		setPromotions(getPromotionView(babysitter.getPromotions()));
		setImages(getImageView(babysitter.getImages()));
	}

	private List<Map<String, String>> getImageView(List<BabysitterImage> images) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (BabysitterImage img : images) {
			result.add(img.view());
		}
		return result;
	}

	private List<Map<String, String>> getPromotionView(
			List<PromotionInfo> promotions) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (PromotionInfo info : promotions) {
			result.add(info.view());
		}
		return result;
	}

	private List<Map<String, String>> getCredentialView(
			List<Credential> credentials) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (Credential credential : credentials) {
			result.add(credential.view());
		}
		return result;
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
		return headUrl;
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

	public List<Map<String, String>> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<Map<String, String>> credentials) {
		this.credentials = credentials;
	}

	public List<Map<String, String>> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Map<String, String>> promotions) {
		this.promotions = promotions;
	}

	public List<Map<String, String>> getImages() {
		return images;
	}

	public void setImages(List<Map<String, String>> images) {
		this.images = images;
	}

}
