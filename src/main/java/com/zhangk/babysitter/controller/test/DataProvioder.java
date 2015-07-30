package com.zhangk.babysitter.controller.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.entity.Credential;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.Image;
import com.zhangk.babysitter.entity.Level;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.service.common.CredentialService;
import com.zhangk.babysitter.service.common.ImageService;
import com.zhangk.babysitter.service.common.PromotionService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.service.level.LevelService;
import com.zhangk.babysitter.service.manager.CustomerManagerService;
import com.zhangk.babysitter.service.menu.MenuService;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.service.user.UserService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.data.UserInfoData;

@Controller
@RequestMapping("/dataProvider")
public class DataProvioder extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private BabysitterService babyService;
	@Autowired
	private LevelService levelService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private CountyService countyService;
	@Autowired
	private CountyLevelService countyLevelService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private CredentialService credentialService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CustomerManagerService managerService;

	@ResponseBody
	@RequestMapping("/userData")
	public PageResult userData() {
		UserInfoData userData = new UserInfoData();
		List<UserInfo> list = userData.initUserData();
		for (UserInfo userInfo : list) {
			userService.userRegister(userInfo);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/roleData")
	public PageResult roleData() {
		Role superAdmin = Role.getInstance();
		superAdmin.setName("超级管理员");
		roleService.addRole(superAdmin);

		Role xingzheng = Role.getInstance();
		xingzheng.setName("总部行政");
		roleService.addRole(xingzheng);

		Role zhiyingAreaManager = Role.getInstance();
		zhiyingAreaManager.setName("区域经理");
		roleService.addRole(zhiyingAreaManager);

		Role jiamengAreaManager = Role.getInstance();
		jiamengAreaManager.setName("加盟区域经理");
		roleService.addRole(jiamengAreaManager);

		Role zhiyingGuestManager = Role.getInstance();
		zhiyingGuestManager.setName("直营客户经理");
		roleService.addRole(zhiyingGuestManager);

		Role jiamengGuestManager = Role.getInstance();
		jiamengGuestManager.setName("加盟客户经理");
		roleService.addRole(jiamengGuestManager);

		Role zhiyingShenji = Role.getInstance();
		zhiyingShenji.setName("直营审计");
		roleService.addRole(zhiyingShenji);

		Role jiamengShenji = Role.getInstance();
		jiamengShenji.setName("加盟审计");
		roleService.addRole(jiamengShenji);

		Role chuna = Role.getInstance();
		chuna.setName("总部出纳");
		roleService.addRole(chuna);

		Role jiamengChuna = Role.getInstance();
		jiamengChuna.setName("加盟出纳");
		roleService.addRole(jiamengChuna);
		return res;
	}

	@ResponseBody
	@RequestMapping("/menuData")
	public PageResult menuData() {
		Menu role = Menu.getInstance();
		role.setUrl("/role/roleList.html");
		role.setTitle("角色管理");
		menuService.addMenu(role);
		Menu menu = Menu.getInstance();
		menu.setUrl("/menu/menuList.html");
		menu.setTitle("菜单管理");
		menuService.addMenu(menu);
		return res;
	}

	@ResponseBody
	@RequestMapping("/userRoleMenuData")
	public PageResult userRoleMenuData() {
		List<Menu> menus = menuService.menuList();
		Role role = roleService.getRole(1);
		role.setMenus(menus);
		roleService.updateRole(role);
		UserInfo u = userService.login("zhangsan", "123");
		List<Role> roles = u.getRoles();
		roles.add(role);
		u.setRoles(roles);
		userService.updateUser(u);
		return res;
	}

	@ResponseBody
	@RequestMapping("/levelData")
	public PageResult levelData() {
		Level chuji = Level.getInstance();
		chuji.setName("初级月嫂");
		chuji.setScore(15);
		chuji.setMoney(0);
		levelService.addLevel(chuji);

		Level zhongji = Level.getInstance();
		zhongji.setName("中级月嫂");
		zhongji.setScore(30);
		zhongji.setMoney(1000);
		levelService.addLevel(zhongji);

		Level gaoji = Level.getInstance();
		gaoji.setName("高级月嫂");
		gaoji.setScore(50);
		gaoji.setMoney(2000);
		levelService.addLevel(gaoji);

		Level teji = Level.getInstance();
		teji.setName("特级月嫂");
		teji.setScore(60);
		teji.setMoney(3000);
		levelService.addLevel(teji);

		Level jinpai = Level.getInstance();
		jinpai.setName("金牌月嫂");
		jinpai.setScore(70);
		jinpai.setMoney(5000);
		levelService.addLevel(jinpai);

		Level zuanshi = Level.getInstance();
		zuanshi.setName("钻石月嫂");
		zuanshi.setScore(80);
		zuanshi.setMoney(6000);
		levelService.addLevel(zuanshi);

		Level huangguan = Level.getInstance();
		huangguan.setName("皇冠月嫂");
		huangguan.setScore(90);
		huangguan.setMoney(7000);
		levelService.addLevel(huangguan);
		return res;
	}

	@ResponseBody
	@RequestMapping("/babysitterData")
	public PageResult babysitterData() {
		UserInfoData userData = new UserInfoData();
		List<Babysitter> list = userData.initBabysitterData();
		List<Credential> cres = credentialService.getCredentials();
		List<PromotionInfo> infos = promotionService.getPagePromotionInfo(
				new Pagination<PromotionInfo>()).getResult();
		for (Babysitter babysitter : list) {
			babysitter.setLevel(userData.getRandomLevel());
			babysitter.setHeadUrl("/head/babysitter/head.jpg");
			babysitter.setCounty(countyService
					.getCounty("13189F35478B4E01BD6F1F057C20CA4B"));
			// babysitter.setCredentials(cres);
			babysitter.setPromotions(infos);
			babyService.addBabysitter(babysitter);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/babysitterOrderData")
	public PageResult babysitterOrderData() {
		UserInfoData userData = new UserInfoData();
		List<BabysitterOrder> list = userData.initBabysitterOrderData();
		Babysitter b = new Babysitter();
		Random r = new Random();
		for (BabysitterOrder babysitter : list) {
			b.setId(r.nextInt(26) == 2 || r.nextInt() == 0 ? 3 : r.nextInt(26));
			babysitter.setBabysitter(b);
			babyService.addOrder(babysitter);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/employerData")
	public PageResult employerData() {
		UserInfoData userData = new UserInfoData();
		List<Employer> list = userData.initEmployerData();
		for (Employer userInfo : list) {
			employerService.addEmployer(userInfo);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/countyData")
	public PageResult countyData() {
		UserInfoData userData = new UserInfoData();
		List<County> list = userData.initCountyData();
		for (County county : list) {
			countyService.addCounty(county, 0);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/promotionData")
	public PageResult promotionData() {
		PromotionInfo san = PromotionInfo.getInstance();
		san.setTitle("三天免费试用");
		san.setMemo("可以免费试用三天");
		promotionService.addPromotion(san);
		PromotionInfo wu = PromotionInfo.getInstance();
		wu.setTitle("五天免费试用");
		wu.setMemo("可以免费试用五天");
		promotionService.addPromotion(wu);
		PromotionInfo all = PromotionInfo.getInstance();
		all.setTitle("终生免费试用");
		all.setMemo("可以终生试用，管一日三餐和住宿");
		promotionService.addPromotion(all);

		return res;
	}

	@ResponseBody
	@RequestMapping("/credentialData")
	public PageResult CredentialData() {
		Credential shenfenzheng = Credential.getInstance();
		shenfenzheng.setName("身份证");
		credentialService.addCredential(shenfenzheng);
		Credential yuesaozheng = Credential.getInstance();
		yuesaozheng.setName("月嫂证");
		credentialService.addCredential(yuesaozheng);
		Credential jiankangzheng = Credential.getInstance();
		jiankangzheng.setName("健康证");
		credentialService.addCredential(jiankangzheng);

		return res;
	}

	@ResponseBody
	@RequestMapping("/imageData")
	public PageResult imageData() {
		Image image1 = Image.getInstance();
		image1.setName("宣传图片1");
		image1.setTopIndex(1);
		image1.setUrl("/top/image1.jpg");
		imageService.addImage(image1);
		Image image2 = Image.getInstance();
		image2.setName("宣传图片2");
		image2.setTopIndex(1);
		image2.setUrl("/top/image2.jpg");
		imageService.addImage(image2);
		Image image3 = Image.getInstance();
		image3.setName("宣传图片3");
		image3.setTopIndex(1);
		image3.setUrl("/top/image3.jpg");
		imageService.addImage(image3);

		return res;
	}

	@ResponseBody
	@RequestMapping("/countyLevelData")
	public PageResult countyLevelData() {
		CountyLevel beijingchuji = CountyLevel.getInstance();
		beijingchuji.setCounty(countyService.getCounty(1));
		beijingchuji.setLevel(levelService.getLevel(1));
		beijingchuji.setMoney(0);
		beijingchuji.setScore(15);
		countyLevelService.addCountyLevel(beijingchuji);
		CountyLevel beijingzhongji = CountyLevel.getInstance();
		beijingzhongji.setCounty(countyService.getCounty(1));
		beijingzhongji.setLevel(levelService.getLevel(2));
		beijingzhongji.setMoney(1000);
		beijingzhongji.setScore(30);
		countyLevelService.addCountyLevel(beijingzhongji);
		CountyLevel beijinggaoji = CountyLevel.getInstance();
		beijinggaoji.setCounty(countyService.getCounty(1));
		beijinggaoji.setLevel(levelService.getLevel(3));
		beijinggaoji.setMoney(2000);
		beijinggaoji.setScore(50);
		countyLevelService.addCountyLevel(beijinggaoji);
		CountyLevel beijingteji = CountyLevel.getInstance();
		beijingteji.setCounty(countyService.getCounty(1));
		beijingteji.setLevel(levelService.getLevel(4));
		beijingteji.setMoney(3000);
		beijingteji.setScore(60);
		countyLevelService.addCountyLevel(beijingteji);
		CountyLevel beijingjinpai = CountyLevel.getInstance();
		beijingjinpai.setCounty(countyService.getCounty(1));
		beijingjinpai.setLevel(levelService.getLevel(5));
		beijingjinpai.setMoney(5000);
		beijingjinpai.setScore(70);
		countyLevelService.addCountyLevel(beijingjinpai);
		CountyLevel beijingzuanshi = CountyLevel.getInstance();
		beijingzuanshi.setCounty(countyService.getCounty(1));
		beijingzuanshi.setLevel(levelService.getLevel(6));
		beijingzuanshi.setMoney(6000);
		beijingzuanshi.setScore(80);
		countyLevelService.addCountyLevel(beijingzuanshi);
		CountyLevel beijinghuangguan = CountyLevel.getInstance();
		beijinghuangguan.setCounty(countyService.getCounty(1));
		beijinghuangguan.setLevel(levelService.getLevel(6));
		beijinghuangguan.setMoney(7000);
		beijinghuangguan.setScore(90);
		countyLevelService.addCountyLevel(beijinghuangguan);
		return res;
	}

	@ResponseBody
	@RequestMapping("/managerData")
	public PageResult managerData() {
		UserInfoData userData = new UserInfoData();
		CustomerManager manager = userData.initManagerData();
		manager.setCounty(countyService
				.getCounty("13189F35478B4E01BD6F1F057C20CA4B"));
		List<Babysitter> babysitters = babyService.BabysitterList();
		manager.setBabysitters(babysitters);
		manager.setTelephone("15111311131");
		managerService.addCustomerManager(manager);
		return res;
	}

	@ResponseBody
	@RequestMapping("/recommendData")
	public PageResult recommendData() {
		County county = countyService.getCounty(1);
		RecommendInfo info = RecommendInfo.getInstance();
		info.setCounty(county);
		Babysitter a = babyService.getBabysitter(14);
		Babysitter b = babyService.getBabysitter(15);
		Babysitter c = babyService.getBabysitter(16);
		List<Babysitter> list = new ArrayList<Babysitter>();
		list.add(a);
		list.add(b);
		list.add(c);
		info.setBabysitters(list);
		babyService.addRecommendInfo(info);
		return res;
	}

}
