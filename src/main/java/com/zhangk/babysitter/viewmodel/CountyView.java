package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.County;

public class CountyView {
	private long id;
	private String guid;
	private String name;
	private String shortName;
	private String beginCharacter;

	public CountyView() {
	}

	public CountyView(County county) {
		setId(county.getId());
		setGuid(county.getGuid());
		setName(county.getName());
		setShortName(county.getShortName());
		setBeginCharacter(getCountyBeginCharacter(county));
	}

	private String getCountyBeginCharacter(County county) {
		String shortName = county.getShortName();
		return shortName.substring(0, 1);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBeginCharacter() {
		return beginCharacter;
	}

	public void setBeginCharacter(String beginCharacter) {
		this.beginCharacter = beginCharacter;
	}

}
