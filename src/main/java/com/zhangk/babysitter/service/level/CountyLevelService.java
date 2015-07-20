package com.zhangk.babysitter.service.level;

import java.util.List;

import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.viewmodel.CountyLevelView;

public interface CountyLevelService {
	void addCountyLevel(CountyLevel level);

	List<CountyLevel> getCountyLevels();

	CountyLevel getCountyLevel(String guid);

	CountyLevel getCountyLevel(long id);

	List<CountyLevelView> countyLevels(String countyGuid);
}
