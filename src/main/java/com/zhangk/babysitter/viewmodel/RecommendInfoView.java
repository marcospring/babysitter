package com.zhangk.babysitter.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class RecommendInfoView {
	private String guid;
	private Date createDate;
	private long babysitterCount;
	private int recommendCount;
	private List<BabysitterView> babysitters;

	public RecommendInfoView(RecommendInfo info) {
		setGuid(info.getGuid());
		setCreateDate(info.getCreateDate());
		List<BabysitterView> views = new ArrayList<BabysitterView>();
		for (Babysitter babysitter : info.getBabysitters()) {
			views.add(babysitter.view());
		}
		setBabysitters(views);
		setRecommendCount(views.size());
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<BabysitterView> getBabysitters() {
		return babysitters;
	}

	public void setBabysitters(List<BabysitterView> babysitters) {
		this.babysitters = babysitters;
	}

	public long getBabysitterCount() {
		return babysitterCount;
	}

	public void setBabysitterCount(long babysitterCount) {
		this.babysitterCount = babysitterCount;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

}
