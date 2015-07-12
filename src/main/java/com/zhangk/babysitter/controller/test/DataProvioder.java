package com.zhangk.babysitter.controller.test;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.Level;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.level.LevelService;
import com.zhangk.babysitter.service.menu.MenuService;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.service.user.UserService;
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
		for (Babysitter babysitter : list) {
			babysitter.setLevel(userData.getRandomLevel());
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
}
