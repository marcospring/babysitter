package com.zhangk.babysitter.service.level;

import java.util.List;

import com.zhangk.babysitter.entity.Level;

public interface LevelService {
	List<Level> LevelList();

	void addLevel(Level level);

	void deleteLevel(long id);

	void updateLevel(Level level);

	Level getLevel(long id);

}
