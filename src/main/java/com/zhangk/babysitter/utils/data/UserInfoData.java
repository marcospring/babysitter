package com.zhangk.babysitter.utils.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.utils.data.FileDataProvider.Type;

public class UserInfoData {
	FileDataProvider dataProvider = new FileDataProvider();

	public List<UserInfo> initUserData() {
		List<UserInfo> list = new ArrayList<UserInfo>();
		for (int i = 0; i < 20; i++) {
			UserInfo u = UserInfo.getInstance();
			u.setUsername(dataProvider.getValue(Type.USERNAME));
			u.setName(dataProvider.getValue(Type.NAME));
			u.setPassword("123");
			list.add(u);
		}
		return list;
	}

	public List<Babysitter> initBabysitterData() {
		List<Babysitter> list = new ArrayList<Babysitter>();
		for (int i = 0; i < 20; i++) {
			Babysitter u = Babysitter.getInstance();
			u.setUsername(dataProvider.getValue(Type.USERNAME));
			u.setName(dataProvider.getValue(Type.NAME));
			u.setPassword("123");
			u.setMobilePhone(dataProvider.getValue(Type.PHONE));

			list.add(u);
		}
		return list;
	}

	public CountyLevel getRandomLevel() {
		CountyLevel level = new CountyLevel();
		Random r = new Random();
		int id = r.nextInt(7);
		level.setId(id);
		return level;
	}

	public List<BabysitterOrder> initBabysitterOrderData() {
		List<BabysitterOrder> list = new ArrayList<BabysitterOrder>();
		Random r = new Random();
		for (int i = 0; i < 20; i++) {
			BabysitterOrder u = BabysitterOrder.getInstance();
			u.setOrderPrice(r.nextInt(10000));
			u.setServiceBeginDate(new Date());

			Calendar.getInstance().add(Calendar.DAY_OF_MONTH, 20);
			u.setServiceEndDate(Calendar.getInstance().getTime());
			list.add(u);
		}
		return list;
	}

	public List<Employer> initEmployerData() {
		List<Employer> list = new ArrayList<Employer>();
		for (int i = 0; i < 20; i++) {
			Employer u = Employer.getInstance();
			u.setUsername(dataProvider.getValue(Type.USERNAME));
			u.setMobilePhone(dataProvider.getValue(Type.PHONE));
			u.setEmail("zhangsan@126.com");
			u.setPassword("123");
			list.add(u);
		}
		return list;
	}

	public List<County> initCountyData() {
		List<County> list = new ArrayList<County>();
		County beijing = County.getInstance();
		beijing.setName("北京");
		beijing.setShortName("BJ");
		list.add(beijing);
		County shanghai = County.getInstance();
		shanghai.setName("上海");
		shanghai.setShortName("SH");
		list.add(shanghai);
		County guangzhou = County.getInstance();
		guangzhou.setName("广州");
		guangzhou.setShortName("GZ");
		list.add(guangzhou);
		County shenzhen = County.getInstance();
		shenzhen.setName("深圳");
		shenzhen.setShortName("SZ");
		list.add(shenzhen);
		return list;
	}

	public CustomerManager initManagerData() {
		// List<CustomerManager> list = new ArrayList<CustomerManager>();
		// for (int i = 0; i < 20; i++) {
		CustomerManager u = CustomerManager.getInstance();
		u.setUsername(dataProvider.getValue(Type.USERNAME));
		u.setPassword("123");

		// }
		return u;
	}
}
