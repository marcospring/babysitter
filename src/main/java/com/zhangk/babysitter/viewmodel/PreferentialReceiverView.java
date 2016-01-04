package com.zhangk.babysitter.viewmodel;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.PreferentialReceiveInfo;
import com.zhangk.babysitter.entity.PreferentialTypeBehavior;
import com.zhangk.babysitter.entity.ReceiveBehavior;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class PreferentialReceiverView {
	private String guid;
	private String fromPhone;
	private String toPhone;
	private Date createDate;
	private Date endDate;
	private String behavior;
	private long money;
	private String state;

	// from参数用于标识数据方向，为0查询领取，为1查询发放
	public PreferentialReceiverView(PreferentialReceiveInfo receiver, int from) {
		this.guid = receiver.getGuid();
		this.toPhone = receiver.getToUserPhone();
		this.createDate = receiver.getCreateDate();
		this.endDate = receiver.getPromoter().getType().getEndDate();
		this.fromPhone = receiver.getPromoter() == null ? "" : receiver
				.getPromoter().getTelephone();
		if (receiver.getPromoter() != null
				&& receiver.getPromoter().getType() != null)
			getLocalBehavior(receiver.getPromoter().getType().getBehaviors(),
					from);

		if (receiver.getBehaviors() != null
				&& receiver.getBehaviors().size() > 0)
			getLocalReceiverBehavior(receiver.getBehaviors(), from);
	}

	private void getLocalReceiverBehavior(List<ReceiveBehavior> behaviors,
			int from) {
		for (ReceiveBehavior behavior : behaviors) {
			if (behavior != null && behavior.getBeavior().getToUser() == from) {
				state = behavior.getState() == Constants.USE ? "已使用" : "未使用";
			}
		}
	}

	private void getLocalBehavior(List<PreferentialTypeBehavior> behaviors,
			int from) {
		for (PreferentialTypeBehavior behavior : behaviors) {
			if (behavior.getBehavior() != null
					&& behavior.getBehavior().getToUser() == from) {
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

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
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

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
