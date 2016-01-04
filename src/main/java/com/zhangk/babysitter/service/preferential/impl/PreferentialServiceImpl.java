package com.zhangk.babysitter.service.preferential.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.dao.BaseDao;
import com.zhangk.babysitter.entity.PreferentialBehavior;
import com.zhangk.babysitter.entity.PreferentialPromoter;
import com.zhangk.babysitter.entity.PreferentialReceiveInfo;
import com.zhangk.babysitter.entity.PreferentialType;
import com.zhangk.babysitter.entity.PreferentialTypeBehavior;
import com.zhangk.babysitter.entity.ReceiveBehavior;
import com.zhangk.babysitter.service.preferential.PreferentialService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.common.code.MatrixToImageWriter;
import com.zhangk.babysitter.viewmodel.PreferentialReceiverView;
import com.zhangk.babysitter.viewmodel.PreferentialTypeView;
import com.zhangk.babysitter.viewmodel.PreferentialVO;

@Service
public class PreferentialServiceImpl implements PreferentialService {

	private static final String basePath = "/preferential/";
	private static final String endPath = "/promoterUrl.html";

	@Autowired
	private BaseDao dao;

	@Transactional
	public PageResult addPromoter(HttpServletRequest request, String telephone,
			String typeGuid, PageResult result) {
		PreferentialType type = dao.getResultByGUID(PreferentialType.class,
				typeGuid);
		if (type == null) {
			result.setResult(ResultInfo.PREFERENTIAL_TYPE_NULL);
			return result;
		}
		String hql = " from PreferentialPromoter t where t.ovld = true and telephone=? and t.type.guid=?";
		List<PreferentialPromoter> list = dao.getListResultByHQL(
				PreferentialPromoter.class, hql, telephone, typeGuid);
		if (list != null && list.size() > 0) {
			result.setResult(ResultInfo.PROMOTER_NOT_NULL);
			return result;
		}

		PreferentialPromoter promoter = PreferentialPromoter.getInstance();
		promoter.setTelephone(telephone);
		promoter.setType(type);
		String realPath = request.getServletContext().getRealPath(
				Constants.URL_BARCODE);
		StringBuffer content = new StringBuffer();
		content.append(request.getScheme()).append("://")
				.append(request.getServerName())
				.append(request.getContextPath()).append(basePath)
				.append(promoter.getGuid()).append(endPath);
		MatrixToImageWriter.create2DImg(realPath, promoter.getGuid(),
				content.toString());
		StringBuffer url = new StringBuffer();
		url.append(Constants.URL_BARCODE).append(promoter.getGuid());
		promoter.setBarcodesUrl(url.toString());
		dao.add(promoter);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", promoter.view());
		return result;
	}

	public PageResult preferentialTypes(PageResult result) {
		String hql = "from PreferentialType t where ovld = true";
		List<PreferentialType> types = dao.getListResultByHQL(
				PreferentialType.class, hql);
		List<PreferentialTypeView> resultList = new ArrayList<PreferentialTypeView>();
		for (PreferentialType type : types) {
			resultList.add(type.view());
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultList);
		return result;
	}

	public PreferentialVO getPreferential(String guid) {
		PreferentialPromoter promoter = dao.getResultByGUID(
				PreferentialPromoter.class, guid);
		PreferentialVO vo = new PreferentialVO();
		vo.setGuid(promoter.getGuid());
		vo.setTelephone(promoter.getTelephone());
		List<PreferentialTypeBehavior> list = promoter.getType().getBehaviors();
		for (PreferentialTypeBehavior behavior : list) {
			if (behavior.getBehavior().getToUser() == Constants.USERS) {
				vo.setMessage(behavior.getBehavior().getName());
				vo.setMoney(behavior.getPreferentialMoney());
			}
		}
		return vo;
	}

	@Transactional
	public PageResult addPreferentialReceiver(String preferentialValue,
			String toUserTelephone, String verifyCode, PageResult result) {
		// 验证code
		// String hql =
		// "from CheckCode t where t.ovld = true and mobilePhone=? and type=?";
		// CheckCode DBcode = dao.getSingleResultByHQL(CheckCode.class,
		// hql,
		// toUserTelephone, CheckCodeService.REGISTER);
		//
		// if (DBcode != null && verifyCode.equals(DBcode.getCode())) {
		// DBcode.setOvld(false);
		// dao.update(DBcode);
		PreferentialPromoter promoter = dao.getResultByGUID(
				PreferentialPromoter.class, preferentialValue);
		if (promoter == null) {
			result.setResult(ResultInfo.PROMOTER_NULL);
			return result;
		}
		// 一个种类的优惠卷只能领一个
		String hql = "from PreferentialReceiveInfo t where t.ovld = true and t.promoter.id = ? and t.promoter.overdue=? and t.toUserPhone=?";
		List<PreferentialReceiveInfo> list = dao.getListResultByHQL(
				PreferentialReceiveInfo.class, hql, promoter.getId(),
				promoter.getOverdue(), toUserTelephone);
		if (list != null && list.size() > 0) {
			result.setResult(ResultInfo.PROMOTER_NOT_NULL);
			return result;
		}

		PreferentialReceiveInfo receive = PreferentialReceiveInfo
				.getInstantce();
		receive.setPromoter(promoter);
		receive.setToUserPhone(toUserTelephone);
		dao.add(receive);
		for (PreferentialTypeBehavior behavior : promoter.getType()
				.getBehaviors()) {
			ReceiveBehavior receiveBehavior = ReceiveBehavior.getInstance();
			receiveBehavior.setBeavior(behavior.getBehavior());
			receiveBehavior.setInfo(receive);
			dao.add(receiveBehavior);
		}
		PreferentialReceiverView view = new PreferentialReceiverView(receive,
				Constants.USERS);
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", view);
		return result;
	}

	public PageResult preferentialList(String phone, PageResult result) {
		String hql = "from PreferentialReceiveInfo t where t.ovld = true and t.promoter.telephone=?";
		List<PreferentialReceiveInfo> list = dao.getListResultByHQL(
				PreferentialReceiveInfo.class, hql, phone);
		List<PreferentialReceiverView> resultObjects = new ArrayList<PreferentialReceiverView>();
		for (PreferentialReceiveInfo receiver : list) {
			resultObjects.add(new PreferentialReceiverView(receiver,
					Constants.SHARE));
		}
		String hqls = "from PreferentialReceiveInfo t where t.ovld = true and t.toUserPhone=?";
		List<PreferentialReceiveInfo> listResult = dao.getListResultByHQL(
				PreferentialReceiveInfo.class, hqls, phone);
		// List<PreferentialReceiverView> resultObjects = new
		// ArrayList<PreferentialReceiverView>();
		for (PreferentialReceiveInfo receiver : listResult) {
			resultObjects.add(new PreferentialReceiverView(receiver,
					Constants.USERS));
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultObjects);
		return result;
	}

	@Transactional
	public void addPreferentialType() {
		PreferentialType type = PreferentialType.getInstance();
		type.setName("分销优惠券");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2016);
		Date endDate = c.getTime();
		type.setEndDate(endDate);
		dao.add(type);
	}

	@Transactional
	public void addPreferentialBehavior() {
		PreferentialBehavior behavior = PreferentialBehavior.getInstance();
		behavior.setName("扣减");
		behavior.setToUser(Constants.USERS);
		dao.add(behavior);
		PreferentialBehavior behavior1 = PreferentialBehavior.getInstance();
		behavior1.setName("返现");
		behavior1.setToUser(Constants.SHARE);
		dao.add(behavior1);
		PreferentialType type = dao.getResultById(PreferentialType.class, 1);
		PreferentialTypeBehavior tb1 = PreferentialTypeBehavior.getInstance();
		tb1.setType(type);
		tb1.setBehavior(behavior1);
		tb1.setPreferentialMoney(500);
		dao.add(tb1);
		PreferentialTypeBehavior tb2 = PreferentialTypeBehavior.getInstance();
		tb2.setType(type);
		tb2.setBehavior(behavior);
		tb2.setPreferentialMoney(500);
		dao.add(tb2);

	}

	public PageResult preferentialPromoterList(String phone, PageResult result) {
		String hql = "from PreferentialReceiveInfo t where t.ovld = true and t.toUserPhone=?";
		List<PreferentialReceiveInfo> list = dao.getListResultByHQL(
				PreferentialReceiveInfo.class, hql, phone);
		List<PreferentialReceiverView> resultObjects = new ArrayList<PreferentialReceiverView>();
		for (PreferentialReceiveInfo receiver : list) {
			resultObjects.add(new PreferentialReceiverView(receiver,
					Constants.USERS));
		}
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", resultObjects);
		return result;
	}
}
