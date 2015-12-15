package com.zhangk.babysitter.service.babysitter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterCredential;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.PanicBuyingBabysitterAdvice;
import com.zhangk.babysitter.entity.PanicBuyingOrder;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.entity.ServiceOrder;
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
import com.zhangk.babysitter.viewmodel.RecommendInfoView;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

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

	public Pagination<BabysitterView> getManageBabysitters(
			Pagination<Babysitter> page, String countyId, String name,
			String levelid, String telephone, String cardNo,
			String identificationNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"from Babysitter r where ovld = true ");
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
		if (!StringUtils.isEmpty(identificationNo)) {
			hql.append(" and r.identificationNo like ? ");
			params.add("%" + identificationNo + "%");
		}
		StringBuffer countHql = new StringBuffer(" select count(r.id) ");
		countHql.append(hql);
		Object[] objParams = new Object[params.size()];
		for (int i = 0; i < objParams.length; i++) {
			objParams[i] = params.get(i);
		}
		Pagination<Babysitter> p = dao.getPageResultObjectParams(
				Babysitter.class, hql.toString(), page.getPageNo(),
				page.getPageSize(), objParams);
		List<Babysitter> list = p.getResult();
		List<BabysitterView> viewList = new ArrayList<BabysitterView>();
		for (Babysitter babysitter : list) {
			viewList.add(babysitter.view());
		}

		Pagination<BabysitterView> pa = new Pagination<BabysitterView>(
				viewList, p.getPageNo(), p.getPageSize());
		Long count = dao.getSingleResultByHQLObjectParams(Long.class,
				countHql.toString(), objParams);
		pa.setResultSize(count);
		return pa;
	}

	public BabysitterView getBabysitterView(String guid) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null)
			return null;
		// 去掉新建订单
		List<BabysitterOrder> resultOrders = new ArrayList<BabysitterOrder>();
		List<BabysitterOrder> orders = babysitter.getOrders();
		for (BabysitterOrder babysitterOrder : orders) {
			if (babysitterOrder.getState() != Constants.NEW_ORDER) {
				resultOrders.add(babysitterOrder);
			}
		}
		babysitter.setOrders(resultOrders);
		// 证件中去掉相册
		// List<BabysitterCredential> resultCredentials = new
		// ArrayList<BabysitterCredential>();
		// List<BabysitterCredential> credentials = babysitter.getCredentials();
		// for (BabysitterCredential babysitterCredential : credentials) {
		// if (babysitterCredential.getCredential() != null
		// && !babysitterCredential.getCredential().getName()
		// .contains("相册") && babysitterCredential.isOvld()) {
		// resultCredentials.add(babysitterCredential);
		// }
		// }
		// babysitter.setCredentials(resultCredentials);
		BabysitterView view = babysitter.view();
		view.setOrderCountIndex(getOrderCountIndex(view.getId()));
		view.setScoreIndex(getScoreCountIndex(view.getScore()));
		view.setLastLevelScore(getLastLevelScore(babysitter));

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

	public PageResult getNewBabysitterRecommend(String countyGuid,
			PageResult result) {
		String hql = "from RecommendInfo r where r.county.guid = ? order by r.createDate desc";
		List<RecommendInfo> infos = dao.getListResultByHQL(RecommendInfo.class,
				hql, countyGuid);
		if (infos == null || infos.size() == 0) {
			result.setResult(ResultInfo.RECOMMEND_NULL);
			return result;
		}
		RecommendInfoView view = infos.get(0).view();
		String babysitterCountHql = "select count (t.id) from Babysitter t where t.county.guid = ?";
		long babysitterCount = dao.getSingleResultByHQL(Long.class,
				babysitterCountHql, countyGuid);
		view.setBabysitterCount(babysitterCount);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", view);
		return result;
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
			String hql = "from Babysitter b where b.ovld = true and b.identificationNo=?";
			String mobileHql = "from Babysitter b where b.ovld = true and b.mobilePhone=?";

			Babysitter valideBabysitter = dao.getSingleResultByHQL(
					Babysitter.class, hql, cardNo);
			if (valideBabysitter == null) {
				valideBabysitter = dao.getSingleResultByHQL(Babysitter.class,
						mobileHql, telephone);
			}

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
				babysitter.setCardNo(recordService.getCardNo(county));
				babysitter.setIdentificationNo(cardNo);
				babysitter.setCounty(county);
				dao.add(babysitter);
				// 设置月嫂的银行卡信息和相册信息
				// Credential image = dao.getResultByGUID(Credential.class,
				// Constants.LIFE_IMAGE_GUID);
				// Credential bankCard = dao.getResultByGUID(Credential.class,
				// Constants.BANK_CARD_GUID);
				// BabysitterCredential babysitterImage = BabysitterCredential
				// .getInstance();
				// babysitterImage.setBabysitter(babysitter);
				// babysitterImage.setCredential(image);
				// babysitterImage.setIscheck(Constants.NO_PASS);
				// dao.add(babysitterImage);
				// BabysitterCredential babysitterBankCard =
				// BabysitterCredential
				// .getInstance();
				// babysitterBankCard.setBabysitter(babysitter);
				// babysitterBankCard.setCredential(bankCard);
				// babysitterBankCard.setIscheck(Constants.NO_PASS);
				// dao.add(babysitterBankCard);
				// List<BabysitterCredential> credentials = new
				// ArrayList<BabysitterCredential>();
				// credentials.add(babysitterImage);
				// credentials.add(babysitterBankCard);
				// babysitter.setCredentials(credentials);
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
			res.setResult(ResultInfo.BAD_REQUEST);
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
		String hql = "from Babysitter b where b.ovld = true and mobilePhone = ?";
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
		res.setResult(ResultInfo.SUCCESS);
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
			String cardGuid, PageResult res) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null) {
			res.setResult(ResultInfo.BABYSITTER_NULL);
			return res;
		}
		Credential credential = dao.getResultByGUID(Credential.class, cardGuid);
		if (credential == null) {
			res.setResult(ResultInfo.CREDENTIAL_NULL);
			return res;
		}
		List<MultipartFile> files = fileUtil.getFiles();
		List<BabysitterImageView> images = new ArrayList<BabysitterImageView>();
		if (files != null && files.size() > 0) {
			MultipartFile file = files.get(0);
			BabysitterImage image = BabysitterImage.getInstance();
			String url = fileUtil.getFileUrl(file, Constants.URL_LIFE,
					image.getGuid());
			image.setBabysitter(babysitter);
			image.setUrl(url);
			dao.add(image);
			images.add(image.view());
		} else {
			res.setResult(ResultInfo.FILE_NULL);
			return res;
		}
		boolean flag = false;
		List<BabysitterCredential> credentials = babysitter.getCredentials();
		for (BabysitterCredential babysitterCredential : credentials) {
			if (babysitterCredential.getCredential().getGuid()
					.equals(credential.getGuid())) {
				flag = !flag;
			}
		}
		if (!flag) {
			BabysitterCredential babysitterCredential = BabysitterCredential
					.getInstance();
			babysitterCredential.setBabysitter(babysitter);
			babysitterCredential.setCredential(credential);
			babysitterCredential.setIscheck(Constants.PASS);
			dao.add(babysitterCredential);
			long score = babysitter.getCredentialScore();
			score += credential.getScore();
			babysitter.setCredentialScore(score);
			dao.update(babysitter);
		}
		// 更新相册信息
		// List<BabysitterCredential> credentials = babysitter
		// .getCredentials();
		// for (BabysitterCredential credential : credentials) {
		// if (Constants.LIFE_IMAGE_GUID.equals(credential.getCredential()
		// .getGuid())
		// && credential.getIscheck() == Constants.NO_PASS) {
		// credential.setIscheck(Constants.PASS);
		// dao.update(credential);
		// break;
		// }
		// }
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("images", images);
		result.put("domain", Constants.IMG_DOMAIN);
		res.setResult(ResultInfo.SUCCESS);
		res.put("result", result);

		return res;
	}

	@Transactional
	public PageResult updateBankCard(String guid, String bankName,
			String bankCardNo, String bankUserName, String cardGuid,
			PageResult result) {
		try {
			Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
			if (babysitter == null) {
				result.setResult(ResultInfo.BABYSITTER_NULL);
				return result;
			}
			babysitter.setBankCardNo(bankCardNo);
			babysitter.setBankName(bankName);
			babysitter.setBankUserName(bankUserName);
			babysitter.setUpdateDate(new Date());
			dao.update(babysitter);
			Credential credential = dao.getResultByGUID(Credential.class,
					cardGuid);
			if (credential == null)
				result.setResult(ResultInfo.CREDENTIAL_NULL);
			boolean flag = true;
			List<BabysitterCredential> babysitterCredentials = babysitter
					.getCredentials();
			for (BabysitterCredential babysitterCredential : babysitterCredentials) {
				if (babysitterCredential.getCredential().getGuid()
						.equals(credential.getGuid())) {
					flag = !flag;
				}
			}
			if (flag) {
				BabysitterCredential babysitterCredential = BabysitterCredential
						.getInstance();
				babysitterCredential.setBabysitter(babysitter);
				babysitterCredential.setCredential(credential);
				babysitterCredential.setIscheck(Constants.PASS);
				dao.add(babysitterCredential);
				long score = babysitter.getCredentialScore();
				score += credential.getScore();
				babysitter.setCredentialScore(score);
				dao.update(babysitter);
			}
			// List<BabysitterCredential> credentials = babysitter
			// .getCredentials();
			// 更新银行卡信息
			// for (BabysitterCredential credential : credentials) {
			// if (Constants.BANK_CARD_GUID.equals(credential.getCredential()
			// .getGuid())
			// && credential.getIscheck() == Constants.NO_PASS) {
			// credential.setIscheck(Constants.PASS);
			// dao.update(credential);
			// break;
			// }
			// }
			result.setResult(ResultInfo.SUCCESS);
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setResult(ResultInfo.BAD_REQUEST);
		}
		return result;
	}

	@Transactional
	public PageResult addOrder(String guid, String beginDate, String endDate,
			String price, String address, String employerName,
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
			// 添加雇主订单
			ServiceOrder serviceOrder = ServiceOrder.getInstance();
			serviceOrder.setAddress(address);
			serviceOrder.setEmployer(employer);
			serviceOrder.setEmployerName(employerName);
			serviceOrder.setMobilePhone(telephone.replace(" ", ""));
			serviceOrder.setOrderPrice(Long.valueOf(price.trim()));
			serviceOrder.setServiceBeginDate(ExpectedDateCreate
					.parseDate(beginDate));
			serviceOrder.setServiceEndDate(ExpectedDateCreate
					.parseDate(endDate));
			dao.add(serviceOrder);

			Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
			if (babysitter == null) {
				res.setResult(ResultInfo.BABYSITTER_NULL);
				return res;
			}
			BabysitterOrder order = BabysitterOrder.getInstance();
			order.setEmployer(employer);
			order.setOrderPrice(Long.valueOf(price.trim()));
			order.setBabysitter(babysitter);
			order.setState(Constants.NEW_ORDER);
			order.setOrderId(recordService.createOrderId());
			order.setServiceBeginDate(ExpectedDateCreate.parseDate(beginDate));
			order.setServiceEndDate(ExpectedDateCreate.parseDate(endDate));
			order.setEmployerAddress(address);
			order.setEmployerName(employerName);
			order.setEmployerTelephone(telephone.replace(" ", ""));
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

	@Transactional
	public ResultInfo manageAddBabysitter(String name, String password,
			String identificationNo, long lowerSalary, String mobilePhone,
			long countyId, long levelId, String birthday, String nativePlace,
			String bankName, String bankCardNo, String bankUserName,
			String introduce) {
		String hql = "from Babysitter t where ovld = true and t.mobilePhone = ?";
		Babysitter babysitter = dao.getSingleResultByHQL(Babysitter.class, hql,
				mobilePhone);
		if (babysitter == null) {
			String nohql = "from Babysitter t where ovld = true and t.identificationNo = ?";
			babysitter = dao.getSingleResultByHQL(Babysitter.class, nohql,
					identificationNo);
		}
		if (babysitter != null)
			return ResultInfo.BABYSITTER_NOT_NULL;
		County county = dao.getResultById(County.class, countyId);
		babysitter = Babysitter.getInstance();
		babysitter.setName(name);
		babysitter.setPassword(password);
		babysitter.setIdentificationNo(identificationNo);
		babysitter.setLowerSalary(lowerSalary);
		babysitter.setMobilePhone(mobilePhone);
		babysitter.setCardNo(recordService.getCardNo(county));
		babysitter.setCounty(county);
		babysitter.setLevel(dao.getResultById(CountyLevel.class, levelId));
		babysitter.setBankName(bankName);
		babysitter.setBankCardNo(bankCardNo);
		babysitter.setBankUserName(bankUserName);
		try {
			babysitter.setBirthday(ExpectedDateCreate.parseDate(birthday));
		} catch (Exception e) {
			e.printStackTrace();
		}
		babysitter.setNativePlace(nativePlace);
		babysitter.setIntroduce(introduce);
		dao.add(babysitter);
		return ResultInfo.SUCCESS;
	}

	@Transactional
	public ResultInfo manageUpdateBabysitter(String id, String name,
			String password, String identificationNo, long lowerSalary,
			String mobilePhone, long countyId, long levelId, String birthday,
			String nativePlace, String introduce, String height, String weight,
			String hobbies, String bankName, String bankCardNo,
			String bankUserName, String mandarin, String isV) {
		long idl = Long.valueOf(id);
		Babysitter babysitter = dao.getResultById(Babysitter.class, idl);
		if (babysitter == null)
			return ResultInfo.BABYSITTER_NULL;
		if (!StringUtils.isEmpty(name))
			babysitter.setName(name);
		if (!StringUtils.isEmpty(password))
			babysitter.setPassword(password);
		if (!StringUtils.isEmpty(identificationNo))
			babysitter.setIdentificationNo(identificationNo);
		if (lowerSalary != 0)
			babysitter.setLowerSalary(lowerSalary);
		if (!StringUtils.isEmpty(mobilePhone))
			babysitter.setMobilePhone(mobilePhone);
		if (!StringUtils.isEmpty(birthday)) {
			try {
				babysitter.setBirthday(ExpectedDateCreate.parseDate(birthday));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!StringUtils.isEmpty(nativePlace))
			babysitter.setNativePlace(nativePlace);
		if (!StringUtils.isEmpty(height))
			babysitter.setHeight(height);
		if (!StringUtils.isEmpty(weight))
			babysitter.setWeight(weight);
		if (!StringUtils.isEmpty(hobbies))
			babysitter.setHobbies(hobbies);
		if (!StringUtils.isEmpty(bankName))
			babysitter.setBankName(bankName);
		if (!StringUtils.isEmpty(bankCardNo))
			babysitter.setBankCardNo(bankCardNo);
		if (!StringUtils.isEmpty(bankUserName))
			babysitter.setBankUserName(bankUserName);
		if (!StringUtils.isEmpty(mandarin))
			babysitter.setMandarin(mandarin);
		if (!StringUtils.isEmpty(isV))
			babysitter.setIsV(Integer.valueOf(isV));
		if (!StringUtils.isEmpty(introduce))
			babysitter.setIntroduce(introduce);
		if (countyId != 0) {
			County county = dao.getResultById(County.class, countyId);
			if (county != null)
				babysitter.setCounty(county);
		}
		if (levelId != 0) {
			CountyLevel level = dao.getResultById(CountyLevel.class, levelId);
			if (level != null)
				babysitter.setLevel(level);
		}
		babysitter.setUpdateDate(new Date());
		dao.update(babysitter);
		return ResultInfo.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public PageResult orderScore(PageResult result, String countyGuid) {
		County county = dao.getResultByGUID(County.class, countyGuid);
		if (county == null) {
			result.setResult(ResultInfo.COUNTY_NULL);
			return result;
		}
		long countyId = county.getId();
		String sql = "select babysitter_id bid,count(t.id) counter from babysitter_babysitter_order t join babysitter_babysitter t1 on t.babysitter_id = t1.id where t1.county_id =? and t.state >=5  group by babysitter_id order by count(t.id) desc limit 10;";
		List<Object[]> list = dao.getSession().createSQLQuery(sql)
				.addScalar("bid", LongType.INSTANCE)
				.addScalar("counter", LongType.INSTANCE).setLong(0, countyId)
				.list();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int index = 1;
		for (Object[] o : list) {
			long bid = Long.valueOf(o[0].toString());
			long count = Long.valueOf(o[1].toString());
			BabysitterView view = dao.getResultById(Babysitter.class, bid)
					.view();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("headUrl", view.getHeadUrl());
			m.put("name", view.getName());
			m.put("cardNo", view.getCardNo());
			m.put("orderCount", count);
			m.put("level", view.getLevel());
			m.put("index", index);
			resultList.add(m);
			index++;
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	public PageResult scoreSort(PageResult result, String countyGuid) {
		County county = dao.getResultByGUID(County.class, countyGuid);
		if (county == null) {
			result.setResult(ResultInfo.COUNTY_NULL);
			return result;
		}
		long countyId = county.getId();
		String sql = "select * from babysitter_babysitter t where t.county_id = ? order by t.score desc limit 10";
		List<Babysitter> list = dao.getSession().createSQLQuery(sql)
				.addEntity(Babysitter.class).setLong(0, countyId).list();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int index = 1;
		for (Babysitter babysitter : list) {
			BabysitterView view = babysitter.view();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("headUrl", view.getHeadUrl());
			m.put("name", view.getName());
			m.put("cardNo", view.getCardNo());
			m.put("score", view.getScore());
			m.put("level", view.getLevel());
			m.put("index", index);
			resultList.add(m);
			index++;
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultList);
		return result;
	}

	private long getOrderCountIndex(long babysitterId) {
		String sql = "select count(t.id) counter from babysitter_babysitter_order t where t.ovld = true and t.babysitter_id=? and state >=5";
		long counter = (Long) dao.getSession().createSQLQuery(sql)
				.addScalar("counter", LongType.INSTANCE)
				.setLong(0, babysitterId).uniqueResult();
		String indexSql = "select count(t.bid) counter from (select babysitter_id bid from babysitter_babysitter_order t where t.ovld = true and t.state>=5 group by babysitter_id having(count(t.id)>?)) t;";
		long index = (Long) dao.getSession().createSQLQuery(indexSql)
				.addScalar("counter", LongType.INSTANCE).setLong(0, counter)
				.uniqueResult();
		return index + 1;
	}

	private long getScoreCountIndex(long score) {
		String sql = "select count(t.id) counter from babysitter_babysitter t where t.ovld = true and t.score> ?";
		long index = (Long) dao.getSession().createSQLQuery(sql)
				.addScalar("counter", LongType.INSTANCE).setLong(0, score)
				.uniqueResult();
		return index + 1;
	}

	private long getLastLevelScore(Babysitter babysitter) {
		County county = babysitter.getCounty();
		if (county == null)
			return -1;
		String hql = "from CountyLevel t where t.county.id=? order by t.score";
		List<CountyLevel> levelList = dao.getListResultByHQL(CountyLevel.class,
				hql, county.getId());
		if (levelList == null || levelList.size() == 0)
			return -1;
		if (babysitter.getLevel() == null) {
			return levelList.get(0).getScore();
		} else {
			CountyLevel babysitterLevel = babysitter.getLevel();
			int levelSize = levelList.size();
			int index = 0;
			for (CountyLevel countyLevel : levelList) {
				if (babysitterLevel.getId() == countyLevel.getId()) {
					break;
				}
				index++;
			}
			if (index == levelSize - 1) {
				return 0;
			} else if (index < levelSize) {
				return levelList.get(index + 1).getScore()
						- babysitterLevel.getScore();
			} else {
				return -1;
			}
		}
	}

	@Transactional
	public ResultInfo addBankInfo(long id, String bankName, String bankCardNo,
			String bankUserName) {
		Babysitter babysitter = dao.getResultById(Babysitter.class, id);
		if (babysitter == null)
			return ResultInfo.BABYSITTER_NULL;
		if (!StringUtils.isEmpty(bankName)) {
			babysitter.setBankName(bankName);
		}
		if (!StringUtils.isEmpty(bankCardNo)) {
			babysitter.setBankCardNo(bankCardNo);
		}
		if (!StringUtils.isEmpty(bankUserName)) {
			babysitter.setBankUserName(bankUserName);
		}
		babysitter.setUpdateDate(new Date());
		dao.update(babysitter);
		return ResultInfo.SUCCESS;
	}

	public PageResult addCredential(HttpServletRequest request,
			String babysitterGuid, String credentialGuid, PageResult result) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		if (babysitter == null) {
			result.setResult(ResultInfo.BABYSITTER_NULL);
			return result;
		}
		Credential dicCredential = dao.getResultByGUID(Credential.class,
				credentialGuid);
		if (dicCredential == null) {
			result.setResult(ResultInfo.CREDENTIAL_NULL);
			return result;
		}
		BabysitterCredential credential = BabysitterCredential.getInstance();
		credential.setBabysitter(babysitter);
		credential.setCredential(dicCredential);
		credential.setIscheck(Constants.NO_PASS);
		List<MultipartFile> files = fileUtil.getFiles();
		if (files != null && files.size() > 0) {
			MultipartFile file = files.get(0);
			String url = fileUtil.getFileUrl(file, Constants.URL_CARD,
					credential.getGuid());
			credential.setCredentialUrl(url);
			dao.update(credential);

			result.put("result", credential.view());
		} else {
			result.setResult(ResultInfo.FILE_NULL);
			return result;
		}

		return result;
	}

	public PageResult search(String countyGuid, String expectedDate,
			String level, PageResult result) {
		String hql = "from Babysitter b where b.county.guid=? and b.level.guid=?";
		List<Babysitter> list = dao.getListResultByHQL(Babysitter.class, hql,
				countyGuid, level);
		List<BabysitterView> viewList = new ArrayList<BabysitterView>();
		Map<String, Date> dates = ExpectedDateCreate
				.getExpectedDate(expectedDate);
		for (Babysitter babysitter : list) {
			if (ExpectedDateCreate.checkBabysitterOrder(babysitter, dates)) {
				viewList.add(babysitter.view());
			}
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", viewList);
		return result;
	}

	public PageResult nameSearch(String name, PageResult result) {
		String hql = "from Babysitter b where b.ovld = true and b.name like ?";
		List<Babysitter> list = dao.getListResultByHQL(Babysitter.class, hql,
				"%" + name + "%");
		List<BabysitterView> viewList = new ArrayList<BabysitterView>();

		for (Babysitter babysitter : list) {
			viewList.add(babysitter.view());
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", viewList);
		return result;
	}

	@Transactional
	public void manageDeleteBabysitter(String ids) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			Babysitter babysitter = dao.getResultById(Babysitter.class, idl);
			babysitter.setOvld(false);
			dao.update(babysitter);
		}
	}

	@Transactional
	public void verify(String ids, String state) {
		String idArr[] = ids.split(",");
		for (String id : idArr) {
			long idl = Long.valueOf(id);
			Babysitter babysitter = dao.getResultById(Babysitter.class, idl);
			babysitter.setState(Integer.valueOf(state));
			dao.update(babysitter);
		}

	}

	@Transactional
	public PageResult getAdvice(String babysitterGuid, PageResult result) {
		String hql = "from PanicBuyingBabysitterAdvice t where t.ovld = true and t.babysitter.guid = ? and t.isOver = false and t.isAdvice = false order by t.createDate desc";
		List<PanicBuyingBabysitterAdvice> advices = dao.getListResultByHQL(
				PanicBuyingBabysitterAdvice.class, hql, babysitterGuid);
		List<ServiceOrderView> views = new ArrayList<ServiceOrderView>();
		// for (PanicBuyingBabysitterAdvice advice : advices) {
		if (advices != null && advices.size() > 0) {
			PanicBuyingBabysitterAdvice advice = advices.get(0);
			advice.setIsAdvice(true);
			dao.update(advice);
			views.add(advice.getServiceOrder().view());
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", views);
		return result;
	}

	@Transactional
	public PageResult panic(String babysitterGuid, String orderGuid,
			PageResult result) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class,
				babysitterGuid);
		ServiceOrder order = dao.getResultByGUID(ServiceOrder.class, orderGuid);
		if (order != null && order.getOver()) {
			String orderCountHql = "select count(t.id) from PanicBuyingOrder t where t.serviceOrder.guid = ?";
			long count = dao.getSingleResultByHQL(Long.class, orderCountHql,
					orderGuid);
			if (Constants.SERVICE_ORDER_COUNT >= count) {
				PanicBuyingOrder buyingOrder = PanicBuyingOrder.getInstance();
				buyingOrder.setBabysitter(babysitter);
				buyingOrder.setServiceOrder(order);
				dao.add(buyingOrder);
				// 更新通知表
				String hql = "from PanicBuyingBabysitterAdvice t where t.ovld = true and t.babysitter.guid = ? and t.serviceOrder.guid = ?";
				PanicBuyingBabysitterAdvice advice = dao.getSingleResultByHQL(
						PanicBuyingBabysitterAdvice.class, hql, babysitterGuid,
						orderGuid);
				advice.setIsAdvice(true);
				dao.update(advice);
				if (Constants.SERVICE_ORDER_COUNT == count) {
					order.setOver(false);
					dao.update(order);
				}
				result.setResult(ResultInfo.SUCCESS);
			}
		} else {
			result.setResult(ResultInfo.SERVICE_ORDER_OVER);
		}
		return result;
	}

	public PageResult panicOrders(int pageNo, int pageSize,
			String babysitterGuid, PageResult result) {
		String hql = "from PanicBuyingOrder t where t.ovld = true and t.babysitter.guid = ? order by t.createDate desc";
		Pagination<PanicBuyingOrder> page = dao.getPageResult(
				PanicBuyingOrder.class, hql, pageNo, pageSize, babysitterGuid);
		List<PanicBuyingOrder> list = page.getResult();
		List<ServiceOrderView> orders = new ArrayList<ServiceOrderView>();
		for (PanicBuyingOrder order : list) {
			orders.add(order.getServiceOrder().view());
		}
		StringBuffer countHql = new StringBuffer(" select count(t.id) ");
		countHql.append(hql);
		Pagination<ServiceOrderView> pa = new Pagination<ServiceOrderView>(
				orders, pageNo, pageSize);
		Long count = dao.getSingleResultByHQL(Long.class, countHql.toString(),
				babysitterGuid);
		pa.setResultSize(count);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", pa);
		return result;
	}

	public PageResult getRecommondCount(String countyGuid, PageResult result) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String babysitterCountHql = "select count (t.id) from Babysitter t where t.county.guid = ?";
		long babysitterCount = dao.getSingleResultByHQL(Long.class,
				babysitterCountHql, countyGuid);
		resultMap.put("babysitterCount", babysitterCount);
		String hql = "from RecommendInfo r where r.county.guid = ? order by r.createDate desc";
		List<RecommendInfo> infos = dao.getListResultByHQL(RecommendInfo.class,
				hql, countyGuid);
		if (infos == null || infos.size() == 0)
			return null;
		resultMap.put("recommondCount", infos.get(0).getBabysitters().size());
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultMap);
		return result;
	}

	public PageResult checkBabysitterMobile(String mobile, PageResult result) {
		String mobileHql = "from Babysitter b where b.ovld = true and b.mobilePhone=?";
		Babysitter valideBabysitter = dao.getSingleResultByHQL(
				Babysitter.class, mobileHql, mobile);
		if (valideBabysitter != null)
			result.setResult(ResultInfo.BABYSITTER_NOT_NULL);
		else
			result.setResult(ResultInfo.SUCCESS);
		return result;
	}

}
