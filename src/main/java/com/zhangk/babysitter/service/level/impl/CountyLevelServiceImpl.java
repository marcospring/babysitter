package com.zhangk.babysitter.service.level.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.Level;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.MapAddress;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.httpclient.HttpHelper;
import com.zhangk.babysitter.utils.httpclient.ResponseContent;
import com.zhangk.babysitter.viewmodel.CountyLevelView;

@Service
public class CountyLevelServiceImpl implements CountyLevelService {

	@Autowired
	private BaseDao dao;
	Gson gson = new Gson();

	@Transactional
	public ResultInfo addCountyLevel(long countyId, long levelId, long score,
			long money) {
		String hql = "from CountyLevel t where t.ovld = true and t.county.id = ? and t.level.id = ?";
		CountyLevel countyLevel = dao.getSingleResultByHQL(CountyLevel.class,
				hql, countyId, levelId);
		if (countyLevel != null)
			return ResultInfo.COUNTYLEVEL_NOT_NULL;
		countyLevel = CountyLevel.getInstance();
		County county = dao.getResultById(County.class, countyId);
		Level level = dao.getResultById(Level.class, levelId);
		countyLevel.setCounty(county);
		countyLevel.setLevel(level);
		countyLevel.setScore(score);
		countyLevel.setMoney(money);
		dao.add(countyLevel);
		return ResultInfo.SUCCESS;
	}

	public List<CountyLevel> getCountyLevels() {
		String hql = "from CountyLevel";
		return dao.getListResultByHQL(CountyLevel.class, hql);
	}

	public CountyLevel getCountyLevel(String guid) {
		return dao.getResultByGUID(CountyLevel.class, guid);
	}

	public CountyLevel getCountyLevel(long id) {
		return dao.getResultById(CountyLevel.class, id);
	}

	public List<CountyLevelView> countyLevels(String countyGuid) {
		List<CountyLevelView> result = new ArrayList<CountyLevelView>();
		String levelHql = "from CountyLevel cl where cl.county.guid = ?";
		String countHql = "select count(b.id) from Babysitter b where b.level.guid = ?";
		List<CountyLevel> listLevels = dao.getListResultByHQL(
				CountyLevel.class, levelHql, countyGuid);
		for (CountyLevel countyLevel : listLevels) {
			CountyLevelView view = countyLevel.view();
			long count = dao.getSingleResultByHQL(Long.class, countHql,
					countyLevel.getGuid());
			view.setCount(count);
			result.add(view);
		}
		return result;
	}

	public List<CountyLevelView> countyLevels(long countyid) {
		List<CountyLevelView> result = new ArrayList<CountyLevelView>();
		String levelHql = null;
		List<CountyLevel> listLevels = null;
		if (countyid == 0) {
			levelHql = "from CountyLevel cl";
			listLevels = dao.getListResultByHQL(CountyLevel.class, levelHql);
		} else {
			levelHql = "from CountyLevel cl where cl.county.id = ?";
			listLevels = dao.getListResultByHQL(CountyLevel.class, levelHql,
					countyid);
		}

		for (CountyLevel countyLevel : listLevels) {
			result.add(countyLevel.view());
		}
		return result;
	}

	@Transactional
	public void deleteCountyLevel(long id) {
		dao.delete(CountyLevel.class, id);
	}

	@Transactional
	public void updateCountyLevel(long id, long countyId, long levelId,
			long score, long money) {
		CountyLevel countyLevel = dao.getResultById(CountyLevel.class, id);
		County county = dao.getResultById(County.class, countyId);
		Level level = dao.getResultById(Level.class, levelId);
		countyLevel.setCounty(county);
		countyLevel.setLevel(level);
		countyLevel.setScore(score);
		countyLevel.setMoney(money);
		countyLevel.setUpdateDate(new Date());
		dao.update(countyLevel);
	}

	public PageResult countyLevelsXY(String x, String y, PageResult result) {
		StringBuffer url = new StringBuffer(Constants.MAP_URL);
		url.append("?").append("location=").append(x).append(",").append(y)
				.append("&output=json");
		ResponseContent ret = HttpHelper.getUrlRespContent(url.toString());
		try {
			String address = ret.getContent("UTF-8");
			MapAddress mapAddress = gson.fromJson(address, MapAddress.class);
			String city = mapAddress.getResult().getAddressComponent()
					.getCity();
			if (StringUtils.isEmpty(city))
				throw new RuntimeException("定位失败");
			String hql = "from County t where t.name like ?";
			List<County> list = dao.getListResultByHQL(County.class, hql, "%"
					+ city + "%");
			if (list == null || list.size() == 0)
				throw new RuntimeException("定位失败");
			County county = list.get(0);
			List<CountyLevelView> viewList = countyLevels(county.getGuid());
			result.setResult(ResultInfo.SUCCESS);
			result.put("result", viewList);
		} catch (RuntimeException e) {
			result.setResult(ResultInfo.ADDRESS_EXCEPTION);
		} catch (UnsupportedEncodingException e) {
			result.setResult(ResultInfo.ADDRESS_EXCEPTION);
		}
		return result;
	}
}
