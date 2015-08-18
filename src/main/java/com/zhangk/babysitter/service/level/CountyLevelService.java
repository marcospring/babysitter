package com.zhangk.babysitter.service.level;

import java.util.List;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CountyLevelView;

public interface CountyLevelService {
	ResultInfo addCountyLevel(long countyId, long levelId, long score,
			long money);

	List<CountyLevel> getCountyLevels();

	CountyLevel getCountyLevel(String guid);

	CountyLevel getCountyLevel(long id);

	List<CountyLevelView> countyLevels(String countyGuid);

	List<CountyLevelView> countyLevels(long countyid);

	void deleteCountyLevel(long id);

	void updateCountyLevel(long id, long countyId, long levelId, long score,
			long money);

	PageResult countyLevelsXY(String x, String y, PageResult result);
}
