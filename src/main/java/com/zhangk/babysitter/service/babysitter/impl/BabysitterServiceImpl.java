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
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.Employer;
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

	public Pagination<Babysitter> getPageBabysitterList(Pagination<Babysitter> page) {
		String hql = "from Babysitter r";
		String countHql = "select count(r.id) from Babysitter r";

		Pagination<Babysitter> p = dao.getPageResult(Babysitter.class, hql, page.getPageNo(), page.getPageSize());
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

	public Pagination<BabysitterOrder> getPageOrderList(Pagination<BabysitterOrder> page, String name) {
		String hql = "from BabysitterOrder r ";
		String countHql = "select count(r.id) from BabysitterOrder r ";
		if (!StringUtils.isEmpty(name)) {
			hql = "from BabysitterOrder r where r.babysitter.name like ?";
			countHql = "select count(r.id) from BabysitterOrder r where r.babysitter.name like ?";
			name = "'%" + name + "%'";
		}
		Pagination<BabysitterOrder> p = dao.getPageResult(BabysitterOrder.class, hql, page.getPageNo(), page.getPageSize(), name);
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
		BabysitterView view = babysitter.view();
		view.setOrderCountIndex(getOrderCountIndex(view.getId()));
		view.setScoreIndex(getScoreCountIndex(view.getScore()));
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
		List<RecommendInfo> infos = dao.getListResultByHQL(RecommendInfo.class, hql, countyGuid);
		if (infos == null || infos.size() == 0)
			return null;
		return infos.get(0);
	}

	@Transactional
	public void addRecommendInfo(RecommendInfo info) {
		dao.add(info);
	}

	public List<BabysitterView> getExpectedBabysitter(String countyGuid, String expectedDate) {
		List<BabysitterView> result = new ArrayList<BabysitterView>();
		String hql = "from Babysitter b where b.county.guid=?";
		List<Babysitter> babysitters = dao.getListResultByHQL(Babysitter.class, hql, countyGuid);
		Map<String, Date> dates = ExpectedDateCreate.getExpectedDate(expectedDate);
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
	public PageResult register(String telephone, String password, String name, String cardNo, String countyGuid, String verifyCode, PageResult res) {
		try {
			String hql = "from Babysitter b where b.ovld = true and b.mobilePhone=?";

			Babysitter valideBabysitter = dao.getSingleResultByHQL(Babysitter.class, hql, telephone);
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
			Babysitter babysitter = dao.getSingleResultByHQL(Babysitter.class, hql, telephone);
			if (babysitter == null || !password.equals(babysitter.getPassword())) {
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
	public PageResult changePass(String telephone, String password, String code, PageResult res) {
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
		Babysitter babysitter = dao.getSingleResultByHQL(Babysitter.class, hql, telephone);
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
	public RestInfo addRestInfo(String guid, Date beginDate, Date endDate, String memo) {
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
	public PageResult joinPromotion(String guid, String promotionGuid, PageResult res) {
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter == null) {
			res.put("code", ResultInfo.BABYSITTER_NULL.getCode());
			res.put("msg", ResultInfo.BABYSITTER_NULL.getMsg());
			return res;
		}
		PromotionInfo info = dao.getResultByGUID(PromotionInfo.class, promotionGuid);
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
	public PageResult updateHeadImage(String guid, HttpServletRequest request, PageResult res) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter != null) {
			List<MultipartFile> files = fileUtil.getFiles();
			if (files != null && files.size() > 0) {
				MultipartFile file = files.get(0);
				String url = fileUtil.getFileUrl(file, Constants.URL_HEAD, babysitter.getGuid());
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
	public PageResult addLifeImage(String guid, HttpServletRequest request, PageResult res) {
		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		Babysitter babysitter = dao.getResultByGUID(Babysitter.class, guid);
		if (babysitter != null) {
			List<MultipartFile> files = fileUtil.getFiles();
			List<BabysitterImageView> images = new ArrayList<BabysitterImageView>();
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					BabysitterImage image = BabysitterImage.getInstance();
					String url = fileUtil.getFileUrl(file, Constants.URL_LIFE, image.getGuid());
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
	public PageResult updateBankCard(String guid, String bankName, String bankCardNo, String bankUserName, PageResult result) {
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

	@Transactional
	public PageResult addOrder(String guid, String beginDate, String endDate, String price, String address, String employerName, String telephone, PageResult res) {
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
			order.setOrderId(recordService.createOrderId());
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

	@Transactional
	public ResultInfo manageAddBabysitter(String name, String password, String identificationNo, long lowerSalary, String mobilePhone, long countyId, long levelId,
			String birthday, String nativePlace, String introduce) {
		String hql = "from Babysitter t where ovld = true and t.mobilePhone = ?";
		Babysitter babysitter = dao.getSingleResultByHQL(Babysitter.class, hql, mobilePhone);
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
	public ResultInfo manageUpdateBabysitter(String id, String name, String password, String identificationNo, long lowerSalary, String mobilePhone, long countyId, long levelId,
			String birthday, String nativePlace, String introduce) {
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
		List<Object[]> list = dao.getSession().createSQLQuery(sql).addScalar("bid", LongType.INSTANCE).addScalar("counter", LongType.INSTANCE).setLong(0, countyId).list();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int index = 1;
		for (Object[] o : list) {
			long bid = Long.valueOf(o[0].toString());
			long count = Long.valueOf(o[1].toString());
			BabysitterView view = dao.getResultById(Babysitter.class, bid).view();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("headUrl", view.getHeadUrl());
			m.put("name", view.getName());
			m.put("cardNo", view.getCardNo());
			m.put("orderCount", count);
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
		List<Babysitter> list = dao.getSession().createSQLQuery(sql).addEntity(Babysitter.class).setLong(0, countyId).list();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int index = 1;
		for (Babysitter babysitter : list) {
			BabysitterView view = babysitter.view();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("headUrl", view.getHeadUrl());
			m.put("name", view.getName());
			m.put("cardNo", view.getCardNo());
			m.put("score", view.getScore());
			m.put("index", index);
			resultList.add(m);
			index++;
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultList);
		return result;
	}

	private long getOrderCountIndex(long babysitterId) {
		String sql = "select count(t.id) counter from babysitter_babysitter_order t where t.babysitter_id=? and state >=5";
		long counter = (Long) dao.getSession().createSQLQuery(sql).addScalar("counter", LongType.INSTANCE).setLong(0, babysitterId).uniqueResult();
		String indexSql = "select count(t.bid) counter from (select babysitter_id bid from babysitter_babysitter_order t  group by babysitter_id having(count(t.id)>?)) t;";
		long index = (Long) dao.getSession().createSQLQuery(indexSql).addScalar("counter", LongType.INSTANCE).setLong(0, counter).uniqueResult();
		return index + 1;
	}

	private long getScoreCountIndex(long score) {
		String sql = "select count(t.id) counter from babysitter_babysitter t where t.score> ?";
		long index = (Long) dao.getSession().createSQLQuery(sql).addScalar("counter", LongType.INSTANCE).setLong(0, score).uniqueResult();
		return index + 1;
	}

	@Transactional
	public ResultInfo addBankInfo(long id, String bankName, String bankCardNo, String bankUserName) {
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
}
