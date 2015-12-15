package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.PreferentialTypeBehavior;

public class PreferentialTypeBehaviorView {
	private String guid;
	private String behaviorName;
	private long preferentialMoney;

	public PreferentialTypeBehaviorView(PreferentialTypeBehavior behavior) {
		this.guid = behavior.getGuid();
		this.behaviorName = behavior.getBehavior() != null ? behavior
				.getBehavior().getName() : "";
		this.preferentialMoney = behavior.getPreferentialMoney();
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getBehaviorName() {
		return behaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName;
	}

	public long getPreferentialMoney() {
		return preferentialMoney;
	}

	public void setPreferentialMoney(long preferentialMoney) {
		this.preferentialMoney = preferentialMoney;
	}

}
