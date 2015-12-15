package com.zhangk.babysitter.viewmodel;

import java.util.Date;
import java.util.List;

import com.zhangk.babysitter.entity.PreferentialPromoter;
import com.zhangk.babysitter.entity.PreferentialTypeBehavior;
import com.zhangk.babysitter.utils.common.Constants;

public class PreferentialPromoterView {
	private String guid;
	// private PreferentialTypeView type;
	private String telephone;
	private String barcodesUrl;
	private int overdue;
	private Date createDate;
	private Date endDate;
	private String behavior;
	private long money;

	public PreferentialPromoterView(PreferentialPromoter preferentialPromoter) {
		this.guid = preferentialPromoter.getGuid();
		// this.type = new PreferentialTypeView(preferentialPromoter.getType());
		this.telephone = preferentialPromoter.getTelephone();
		this.barcodesUrl = Constants.IMG_DOMAIN
				+ preferentialPromoter.getBarcodesUrl();
		this.overdue = preferentialPromoter.getOverdue();
		this.createDate = preferentialPromoter.getCreateDate();
		this.endDate = preferentialPromoter.getType().getEndDate();
		getLocalBehavior(preferentialPromoter.getType().getBehaviors());

	}

	private void getLocalBehavior(List<PreferentialTypeBehavior> behaviors) {
		for (PreferentialTypeBehavior behavior : behaviors) {
			if (behavior.getBehavior() != null
					&& behavior.getBehavior().getToUser() == Constants.SHARE) {
				this.behavior = behavior.getBehavior().getName();
				this.money = behavior.getPreferentialMoney();
			}
		}
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	// public PreferentialTypeView getType() {
	// return type;
	// }
	//
	// public void setType(PreferentialTypeView type) {
	// this.type = type;
	// }

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBarcodesUrl() {
		return barcodesUrl;
	}

	public void setBarcodesUrl(String barcodesUrl) {
		this.barcodesUrl = barcodesUrl;
	}

	public int getOverdue() {
		return overdue;
	}

	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

}
