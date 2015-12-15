package com.zhangk.babysitter.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.PreferentialType;
import com.zhangk.babysitter.entity.PreferentialTypeBehavior;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class PreferentialTypeView {
	private String guid;
	private String name;
	private Date endDate;
	private List<PreferentialTypeBehaviorView> behavior;

	public PreferentialTypeView(PreferentialType type) {
		this.guid = type.getGuid();
		this.name = type.getName();
		this.endDate = type.getEndDate();
		this.behavior = getBehaviorView(type.getBehaviors());
	}

	private List<PreferentialTypeBehaviorView> getBehaviorView(
			List<PreferentialTypeBehavior> c) {
		List<PreferentialTypeBehaviorView> behaviors = new ArrayList<PreferentialTypeBehaviorView>();
		for (PreferentialTypeBehavior behavior : c) {
			behaviors.add(behavior.view());
		}
		return behaviors;
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

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<PreferentialTypeBehaviorView> getBehavior() {
		return behavior;
	}

	public void setBehavior(List<PreferentialTypeBehaviorView> behavior) {
		this.behavior = behavior;
	}

}
