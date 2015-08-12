package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.CountyLevel;

public class CountyLevelView {
	private long id;
	private String guid;
	private long count;
	private String countyName;
	private String name;
	private String price;
	private String score;

	public CountyLevelView() {
	}

	public CountyLevelView(CountyLevel level) {
		setId(level.getId());
		setGuid(level.getGuid());
		setCountyName(level.getCounty() == null ? "无" : level.getCounty().getName());
		setName(level.getLevel() == null ? "无" : level.getLevel().getName());
		setPrice(String.valueOf(level.getMoney()));
		setScore(String.valueOf(level.getScore()));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
