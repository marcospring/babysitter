package com.zhangk.babysitter.service.babysitter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zhangk.babysitter.controller.web.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.common.UploadFileUtils;
import com.zhangk.babysitter.viewmodel.BabysitterImageView;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Service
public class BabysitterServiceImpl implements BabysitterService {
	@Autowired
	private BaseDao dao;

	public List<Babysitter> BabysitterList() {
		String hql = "from Babysitter r ";
		List<Babysitter> list = dao.getListResultByHQL(Babysitter.class, hql);
		return list;
	}

	public Pagination<Babysitter> getPageBabysitterList(
			Pagination<Babysitter> page) {
		String hql = "from Babysitter r";
		String countHql = "select count(r.id) from Babysitter r";

		Pagination<Babysitter> p = dao.getPageResult(Babysitter.class, hql,
				page.getPageNo(), page.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addBabysitter(Babysitter babysitter) {
		dao.add(babysitter);
	}

	@Transactional
	public void deleteBabysitter(long id) {
		dao.delete(Babysitter.class, id);
	}

	@Transactional
	public void updateBabysitter(Babysitter babysitter) {
		dao.update(babysitter);
	}

	public Babysitter getBabysitter(long id) {
		return dao.getResultById(Babysitter.class, id);
	}

	public Pagination<BabysitterOrder> getPageOrderList(
			Pagination<BabysitterOrder> page, String name) {
		String hql = "from BabysitterOrder r ";
		String countHql = "select count(r.id) from BabysitterOrder r ";
		if (!StringUtils.isEmpty(name)) {
			hql = "from BabysitterOrder r where r.babysitter.name like ?";
			countHql = "select count(r.id) from BabysitterOrder r where r.babysitter.name like ?";
			name = "'%" + name + "%'";
		}
		Pagination<BabysitterOrder> p = dao.getPageResult(
				BabysitterOrder.class, hql, page.getPageNo(),
				page.getPageSize(), name);
		Long count = dao.getSingleResultByHQL(Long.class, countHql, name);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addOrder(BabysitterOrder order) {
		dao.add(order);
	}

	public Pagination<BabysitterView> getMobileBabysitters(String countyGuid,
			Pagination<BabysitterView> page, String name, String orderStr) {
		String hql = "from Babysitter r where r.county.guid = ?";
		String countHql = "select count(r.id) from Babysitter r where r.county.guid = ?";
		Pagination<Babysitter> p = dao.getPageResult(Babysitter.class, hql,
				page.getPageNo(), page.getPageSize(), countyGuid);
		List<Babysitter> list = p.getResult();
		List<BabysitterView> viewList = new ArrayList<BabysitterView>();
		for (Babysitter babysitter : list) {
			BabysitterView view = new BabysitterView(babysitter);
			viewList.add(view);
		}
		Pagination<BabysitterView> pa = new Pagination<BabysitterView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQL(Long.class, countHql, countyGuid);
		pa.setResultSize(count);
		pa.setPageStr("");
		return pa;
	}

	public BabysitterView getBabysitterView(String guid) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null)
			return null;
		BabysitterView view = new BabysitterView(babysitter);
		return view;
	}

	public Babysitter getBabysitter(String guid) {
		return dao.getResultByGUID(Babysitter.class, guid);
	}

	public void deleteBabysitterImage(String imageGuid) {
		dao.delete(BabysitterImage.class, imageGuid);
	}

	public void addBabysitterImage(BabysitterImage image) {
		dao.add(image);
	}

	public RecommendInfo getNewBabysitterRecommend(String countyGuid) {
		String hql = "from RecommendInfo r where r.county.guid = ? order by r.createDate desc";
		List<RecommendInfo> infos = dao.getListResultByHQL(RecommendInfo.class,
				hql, countyGuid);
		if (infos == null || infos.size() == 0)
			return null;
		return infos.get(0);
	}

	@Transactional
	public void addRecommendInfo(RecommendInfo info) {
		dao.add(info);
	}

	public List<BabysitterView> getExpectedBabysitter(String countyGuid,
			String expectedDate) {
		List<BabysitterView> result = new ArrayList<BabysitterView>();
		String hql = "from Babysitter b where b.county.guid=?";
		List<Babysitter> babysitters = dao.getListResultByHQL(Babysitter.class,
				hql, countyGuid);
		Map<String, Date> dates = ExpectedDateCreate
				.getExpectedDate(expectedDate);
		for (Babysitter babysitter : babysitters) {
			if (ExpectedDateCreate.checkBabysitterOrder(babysitter, dates)) {
				result.add(babysitter.view());
			}
		}
		return result;
	}

	public long getBabysitterCountByCounty(String countyGuid) {
		String hql = "select count(b.id) from Babysitter b where b.county.guid = ?";
		long count = dao.getSingleResultByHQL(Long.class, hql, countyGuid);
		return count;
	}

	@Transactional
	public PageResult register(String telephone, String password, String name,
			String cardNo, String countyGuid, String verifyCode, PageResult res) {
		try {
			String hql = "from Babysitter b where b.ovld = true and b.mobilePhone=?";

			Babysitter valideBabysitter = dao.getSingleResultByHQL(
					Babysitter.class, hql, telephone);
			if (valideBabysitter == null) {
				County county = dao.getResultByGUID(County.class, countyGuid);
				if (county == null) {
					res.put("code", ResultInfo.COUNTY_NULL.getCode());
					res.put("msg", ResultInfo.COUNTY_NULL.getMsg());
					res.remove("result");
					return res;
				}
				// 验证code
				// String hql =
				// "from CheckCode t where t.ovld = true and mobilePhone=? and type=?";
				// CheckCode DBcode = dao.getSingleResultByHQL(CheckCode.class,
				// hql,
				// telephone, CheckCodeService.REGISTER);
				//
				// if (DBcode != null && verifyCode.equals(DBcode.getCode())) {
				// DBcode.setOvld(false);
				// dao.update(DBcode);
				Babysitter babysitter = Babysitter.getInstance();
				babysitter.setMobilePhone(telephone);
				babysitter.setPassword(password);
				babysitter.setName(name);
				babysitter.setCardNo(cardNo);
				babysitter.setIdentificationNo(cardNo);
				babysitter.setCounty(county);
				dao.add(babysitter);
				res.put("code", ResultInfo.SUCCESS.getCode());
				res.put("msg", ResultInfo.SUCCESS.getMsg());
				res.put("result", babysitter.view());
			} else {
				res.put("code", ResultInfo.EXIST_USER.getCode());
				res.put("msg", ResultInfo.EXIST_USER.getMsg());
				res.remove("result");
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// } else {
		// res.put("code", ResultInfo.CHECK_CODE_ERROR.getCode());
		// res.put("msg", ResultInfo.CHECK_CODE_ERROR.getCode());
		// return res;
		// }
		return res;
	}

	public PageResult login(String telephone, String password, PageResult res) {
		try {
			String hql = "from Babysitter b where b.ovld = true and b.mobilePhone = ?";
			Babysitter babysitter = dao.getSingleResultByHQL(Babysitter.class,
					hql, telephone);
			if (babysitter == null
					|| !password.equals(babysitter.getPassword())) {
				res.put("code", ResultInfo.VALID_USER_PASS.getCode());
				res.put("msg", ResultInfo.VALID_USER_PASS.getMsg());
				res.remove("result");
				return res;
			}
			res.put("code", ResultInfo.SUCCESS.getCode());
			res.put("msg", ResultInfo.SUCCESS.getMsg());
			res.put("result", babysitter.view());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Transactional
	public PageResult changePass(String telephone, String password,
			String code, PageResult res) {
		// 验证code
		// String hql =
		// "from CheckCode t where t.ovld = true and mobilePhone=? and type=?";
		// CheckCode DBcode = dao.getSingleResultByHQL(CheckCode.class, hql,
		// telephone, CheckCodeService.CHANGE_PASS);
		//
		// if (DBcode != null && verifyCode.equals(DBcode.getCode())) {
		// DBcode.setOvld(false);
		// dao.update(DBcode);
		String hql = "from Babysitter b where b.ovld = true and telephone = ?";
		Babysitter babysitter = dao.getSingleResultByHQL(Babysitter.class, hql,
				telephone);
		if (babysitter == null) {
			res.put("code", ResultInfo.BABYSITTER_NULL.getCode());
			res.put("msg", ResultInfo.BABYSITTER_NULL.getMsg());
			return res;
		}
		babysitter.setPassword(password);
		babysitter.setUpdateDate(new Date());
		dao.update(babysitter);
		res.put("result", babysitter.view());
		// } else {
		// res.put("code", ResultInfo.CHECK_CODE_ERROR.getCode());
		// res.put("msg", ResultInfo.CHECK_CODE_ERROR.getCode());
		// return res;
		// }
		return res;
	}

	@Transactional
	public RestInfo addRestInfo(String guid, Date beginDate, Date endDate,
			String memo) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null)
			return null;
		RestInfo info = RestInfo.getInstance();
		info.setRestBeginDate(beginDate);
		info.setRestEndDate(endDate);
		info.setMemo(memo);
		info.setBabysitter(babysitter);
		dao.add(info);
		return info;
	}

	@Transactional
	public PageResult addLowerSalary(String guid, String money, PageResult res) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null) {
			res.put("code", ResultInfo.BABYSITTER_NULL.getCode());
			res.put("msg", ResultInfo.BABYSITTER_NULL.getMsg());
			return res;
		}
		babysitter.setLowerSalary(Long.parseLong(money));
		dao.update(babysitter);
		return res;
	}

	@Transactional
	public PageResult joinPromotion(String guid, String promotionGuid,
			PageResult res) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null) {
			res.put("code", ResultInfo.BABYSITTER_NULL.getCode());
			res.put("msg", ResultInfo.BABYSITTER_NULL.getMsg());
			return res;
		}
		PromotionInfo info = dao.getResultByGUID(PromotionInfo.class,
				promotionGuid);
		if (info == null) {
			res.put("code", ResultInfo.PROMOTION_NULL.getCode());
			res.put("msg", ResultInfo.PROMOTION_NULL.getMsg());
			return res;
		}
		List<PromotionInfo> infos = babysitter.getPromotions();
		infos.add(info);
		babysitter.setPromotions(infos);
		dao.update(babysitter);
		return res;
	}

	@Transactional
	public PageResult updateHeadImage(String guid, HttpServletRequest request,
			PageResult res) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter != null) {
			List<MultipartFile> files = fileUtil.getFiles();
			if (files != null && files.size() > 0) {
				MultipartFile file = files.get(0);
				String url = fileUtil.getFileUrl(file, Constants.URL_HEAD,
						babysitter.getGuid());
				babysitter.setHeadUrl(url);
				dao.update(babysitter);
				Map<String, String> result = new HashMap<String, String>();
				result.put("headurl", babysitter.getHeadUrl());
				result.put("domain", Constants.IMG_DOMAIN);
				res.put("result", result);
			} else {
				res.setResult(ResultInfo.FILE_NULL);
				return res;
			}
		} else {
			res.setResult(ResultInfo.BABYSITTER_NULL);
			return res;
		}
		return res;
	}

	@Transactional
	public PageResult addLifeImage(String guid, HttpServletRequest request,
			PageResult res) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter != null) {
			List<MultipartFile> files = fileUtil.getFiles();
			List<BabysitterImageView> images = new ArrayList<BabysitterImageView>();
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					BabysitterImage image = BabysitterImage.getInstance();
					String url = fileUtil.getFileUrl(file, Constants.URL_LIFE,
							image.getGuid());
					image.setBabysitter(babysitter);
					image.setUrl(url);
					dao.add(image);
					images.add(image.view());
				}
			} else {
				res.setResult(ResultInfo.FILE_NULL);
				return res;
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("images", images);
			result.put("domain", Constants.IMG_DOMAIN);
			res.setResult(ResultInfo.SUCCESS);
			res.put("result", result);
		} else {
			res.setResult(ResultInfo.BABYSITTER_NULL);
			return res;
		}
		return res;
	}
}
