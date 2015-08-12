package com.zhangk.babysitter.service.babysitter.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.NumberRecord;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.exception.CheckErrorException;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.NumberRecordService;
import com.zhangk.babysitter.service.exployer.EmployerService;
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
	@Autowired
	private NumberRecordService recordService;
	@Autowired
	private EmployerService employerService;

	public List<Babysitter> BabysitterList() {
		String hql = "from Babysitter r ";
		List<Babysitter> list = dao.getListResultByHQL(Babysitter.class, hql);
		return list;
	}


		String hql = "from Babysitter r";
		String countHql = "select count(r.id) from Babysitter r";

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

			Pagination<BabysitterOrder> page, String name) {
		String hql = "from BabysitterOrder r ";
		String countHql = "select count(r.id) from BabysitterOrder r ";
		if (!StringUtils.isEmpty(name)) {
			hql = "from BabysitterOrder r where r.babysitter.name like ?";
			countHql = "select count(r.id) from BabysitterOrder r where r.babysitter.name like ?";
			name = "'%" + name + "%'";
		}
				page.getPageSize(), name)
		Long count = dao.getSingleResultByHQL(Long.class, countHql, name);
		p.setResultSize(count);
		return p;
	}

	@Transactional
	public void addOrder(BabysitterOrder order) {
		dao.add(order);
	}


	public Pagination<BabysitterView> getManageBabysitters(Pagination<Babysitter> page, String countyId, String name, String levelid, String telephone, String cardNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from Babysitter r where ovld = true ");
		if (!StringUtils.isEmpty(countyId) && !"0".equals(countyId)) {
			hql.append(" and r.county.id = ? ");
			params.add(Long.valueOf(countyId));
		}
		if (!StringUtils.isEmpty(name)) {
			hql.append(" and r.name like ? ");
			params.add("%" + name + "%");
		}
		if (!StringUtils.isEmpty(levelid) && !"0".equals(levelid)) {
			hql.append(" and r.level.id = ? ");
			params.add(Long.valueOf(levelid));
		}
		if (!StringUtils.isEmpty(telephone)) {
			hql.append(" and r.mobilePhone = ? ");
			params.add(telephone);
		}
		if (!StringUtils.isEmpty(cardNo)) {
			hql.append(" and r.cardNo = ? ");
			params.add(cardNo);
		}
		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<Babysitter> p = dao.getPageResultObjectParams(Babysitter.class, hql.toString(), page.getPageNo(), page.getPageSize(), objParams);
		List<Babysitter> list = p.getResult();
		List<BabysitterView> viewList = new ArrayList<BabysitterView>();
		for (Babysitter babysitter : list) {
			BabysitterView view = new BabysitterView(babysitter);
			viewList.add(view);
		}
	
		Pagination<BabysitterView> pa = new Pagination<BabysitterView>(viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class, countHql.toString(), objParams);
		pa.setResultSize(count);
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
				hql, countyGuid);
		if (infos == null || infos.size() == 0)
			return null;
		return infos.get(0);
	}

	@Transactional
	public void addRecommendInfo(RecommendInfo info) {
		dao.add(info);
	}

			String expectedDate) {
		List<BabysitterView> result = new ArrayList<BabysitterView>();
		String hql = "from Babysitter b where b.county.guid=?";


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
			String cardNo, String countyGuid, String verifyCode, PageResult res) {
		try {
			String hql = "from Babysitter b where b.ovld = true and b.mobilePhone=?";

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
				babysitter.setCardNo(getCardNo(county));
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

	private String getCardNo(County county) {
		String shortName = county.getShortName();
		StringBuffer cardNo = new StringBuffer(shortName);
		NumberRecord record = recordService.getBabysitterNewNumber();
		cardNo.append(record.getNumber());
		return cardNo.toString();
	}

	public PageResult login(String telephone, String password, PageResult res) {
		try {
			String hql = "from Babysitter b where b.ovld = true and b.mobilePhone = ?";
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
			PageResult res) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null) {
			res.put("code", ResultInfo.BABYSITTER_NULL.getCode());
			res.put("msg", ResultInfo.BABYSITTER_NULL.getMsg());
			return res;
		}
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
			PageResult res) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter != null) {
			List<MultipartFile> files = fileUtil.getFiles();
			if (files != null && files.size() > 0) {
				MultipartFile file = files.get(0);
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

	@Transactional
			String bankCardNo, String bankUserName, PageResult result) {
		try {
			Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
			babysitter.setBankCardNo(bankCardNo);
			babysitter.setBankName(bankName);
			babysitter.setBankUserName(bankUserName);
			babysitter.setUpdateDate(new Date());
			dao.update(babysitter);
			result.setResult(ResultInfo.SUCCESS);
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setResult(ResultInfo.BAD_REQUEST);
		}
		return result;
	}

			String telephone, PageResult res) {
		try {
			// boolean flag = codeService.updateCheckCode(mobile, checkCode,
			// CheckCodeService.PUBLISH_ORDER);
			// if (!flag) {
			// throw new CheckErrorException();
			// }
			Employer employer = employerService.getEmployerByMobile(telephone);
			// County county = dao.getResultByGUID(County.class, countyGuid);
			if (employer == null) {
				employer = Employer.getInstance();
				employer.setMobilePhone(telephone.replace(" ", ""));
				// employer.setCounty(county);
				employer.setAddress(address);
				employer.setUsername(employerName);
				employerService.addEmployer(employer);
			}
			Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
			if (babysitter == null) {
				res.setResult(ResultInfo.BABYSITTER_NULL);
				return res;
			}
			BabysitterOrder order = BabysitterOrder.getInstance();
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price));
			order.setBabysitter(babysitter);
			order.setState(Constants.NEW_ORDER);
			order.setOrderId(createOrderId());
			order.setServiceBeginDate(ExpectedDateCreate.parseDate(beginDate));
			order.setServiceEndDate(ExpectedDateCreate.parseDate(endDate));
			dao.add(order);
		} catch (CheckErrorException e) {
			res.setResult(ResultInfo.CHECK_CODE_ERROR);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.setResult(ResultInfo.SUCCESS);
		return res;
	}

	private String createOrderId() {
		StringBuffer orderId = new StringBuffer("Y");
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String monthStr = month < 10 ? "0" + month : month + "";
		String yearStr = String.valueOf(year).substring(2, 4);
		NumberRecord record = recordService.getOrderNewNumber(month);
		orderId.append(yearStr).append(monthStr).append(record.getNumber());
		return orderId.toString();
	}
}
