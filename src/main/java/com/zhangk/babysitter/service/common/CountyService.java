package com.zhangk.babysitter.service.common;

import java.util.List;

import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.viewmodel.CountyView;

public interface CountyService {
	void addCounty(County county, long parentId);

	List<County> getCountyList();

	List<CountyView> getCountyViewList();

	County getCounty(String guid);

	County getCounty(long id);

	void deleteCounty(long id);

	void updateCounty(County county);

}
