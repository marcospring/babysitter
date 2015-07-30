package com.zhangk.babysitter.service.common;

import java.util.List;

import com.zhangk.babysitter.entity.County;

public interface CountyService {
	void addCounty(County county, long parentId);

	List<County> getCountyList();

	County getCounty(String guid);

	County getCounty(long id);

	void deleteCounty(long id);

	void updateCounty(County county);

}
