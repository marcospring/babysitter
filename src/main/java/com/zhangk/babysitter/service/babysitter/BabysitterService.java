package com.zhangk.babysitter.service.babysitter;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;

public interface BabysitterService {
	String level = "level";
	String orderCount = "orderCount";
	String score = "score";

	List<Babysitter> BabysitterList();

	Pagination<Babysitter> getPageBabysitterList(Pagination<Babysitter> page);

	void addBabysitter(Babysitter babysitter);

	void deleteBabysitter(long id);

	void updateBabysitter(Babysitter babysitter);

	Babysitter getBabysitter(long id);

	Babysitter getBabysitter(String guid);

	Pagination<BabysitterOrder> getPageOrderList(
			Pagination<BabysitterOrder> page, String name);

	void addOrder(BabysitterOrder order);

	Pagination<BabysitterView> getManageBabysitters(
			Pagination<Babysitter> page, String countyId, String name,
			String levelid, String telephone, String cardNo);

	BabysitterView getBabysitterView(String guid);

	void deleteBabysitterImage(String imageGuid);

	void addBabysitterImage(BabysitterImage image);

	RecommendInfo getNewBabysitterRecommend(String countyGuid);

	void addRecommendInfo(RecommendInfo info);

	List<BabysitterView> getExpectedBabysitter(String countyGuid,
			String expectedDate);

	long getBabysitterCountByCounty(String countyGuid);

	PageResult register(String telephone, String password, String name,
			String cardNo, String countyGuid, String verifyCode, PageResult res);

	PageResult login(String telephone, String password, PageResult res);

	PageResult changePass(String telephone, String password, String code,
			PageResult res);

	RestInfo addRestInfo(String guid, Date beginDate, Date endDate, String memo);

	PageResult addLowerSalary(String guid, String money, PageResult res);

	PageResult joinPromotion(String guid, String promotionGuid, PageResult res);

	PageResult updateHeadImage(String guid, HttpServletRequest request,
			PageResult res);

	PageResult addLifeImage(String guid, HttpServletRequest request,
			String cardGuid, PageResult res);

	PageResult updateBankCard(String guid, String bankName, String bankCardNo,
			String bankUserName, String cardGuid, PageResult result);

	PageResult addOrder(String guid, String beginDate, String endDate,
			String price, String address, String employerName,
			String telephone, PageResult res);

	ResultInfo manageAddBabysitter(String name, String password,
			String identificationNo, long lowerSalary, String mobilePhone,
			long countyId, long levelId, String birthday, String nativePlace,
			String introduce);

	ResultInfo manageUpdateBabysitter(String id, String name, String password,
			String identificationNo, long lowerSalary, String mobilePhone,
			long countyId, long levelId, String birthday, String nativePlace,
			String introduce, String height, String weight, String hobbies,
			String mandarin, String isV);

	PageResult orderScore(PageResult result, String countyGuid);

	PageResult scoreSort(PageResult result, String countyGuid);

	ResultInfo addBankInfo(long id, String bankName, String bankCardNo,
			String bankUserName);

	PageResult addCredential(HttpServletRequest request, String babysitterGuid,
			String credentialGuid, PageResult result);

	PageResult search(String countyGuid, String expectedDate, String level,
			PageResult result);

	PageResult nameSearch(String name, PageResult result);
}
