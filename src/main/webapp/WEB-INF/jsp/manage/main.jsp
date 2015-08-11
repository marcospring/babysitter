<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/inc.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台主页</title>
<script type="text/javascript">
var index_tabs;
var index_tabsMenu;
var index_layout;
$(function() {
	index_layout = $('#index_layout').layout({
		fit : true
	});
	/*index_layout.layout('collapse', 'east');*/

	index_tabs = $('#index_tabs').tabs({
		fit : true,
		border : false,
		onContextMenu : function(e, title) {
			e.preventDefault();
			index_tabsMenu.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		}
	});

	index_tabsMenu = $('#index_tabsMenu').menu({
		onClick : function(item) {
			var curTabTitle = $(this).data('tabTitle');
			var type = $(item.target).attr('title');

			if (type === 'refresh') {
				index_tabs.tabs('getTab', curTabTitle).panel('refresh');
				return;
			}

			if (type === 'close') {
				var t = index_tabs.tabs('getTab', curTabTitle);
				if (t.panel('options').closable) {
					index_tabs.tabs('close', curTabTitle);
				}
				return;
			}

			var allTabs = index_tabs.tabs('tabs');
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel('options');
				if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === 'closeAll') {
					closeTabsTitle.push(opt.title);
				}
			});

			for ( var i = 0; i < closeTabsTitle.length; i++) {
				index_tabs.tabs('close', closeTabsTitle[i]);
			}
		}
	});
});

function database_refresh() {
	var href = index_tabs.tabs('getSelected').panel('options').href;
	if (href) {/*说明tab是以href方式引入的目标页面*/
		var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
		index_tabs.tabs('getTab', index).panel('refresh');
	} else {/*说明tab是以content方式引入的目标页面*/
		var panel = index_tabs.tabs('getSelected').panel('panel');
		var frame = panel.find('iframe');
		try {
			if (frame.length > 0) {
				for ( var i = 0; i < frame.length; i++) {
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
					frame[i].src = frame[i].src;
				}
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
}

function delete_fun() {
	var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
	var tab = index_tabs.tabs('getTab', index);
	if (tab.panel('options').closable) {
		index_tabs.tabs('close', index);
	} else {
		$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
	}
}

// 更改主题
function changeThemeFun(themeName) {
	if ($.cookie('easyuiThemeName')) {
		$('#layout_north_pfMenu').menu('setIcon', {
			target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
			iconCls : 'emptyIcon'
		});
	}
	$('#layout_north_pfMenu').menu('setIcon', {
		target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
		iconCls : 'tick'
	});

	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});

};

function logout() {
	if(confirm("确定退出吗?")){
		window.location.href="${pageContext.request.contextPath}/logout";
	}
}

function editCurrentUserPwd() {
	parent.$.modalDialog({
		title : '修改密码',
		width : 300,
		height : 250,
		href : '${pageContext.request.contextPath}/sys/user/userEditPwd.jsp',
		buttons : [ {
			text : '修改',
			handler : function() {
				var f = parent.$.modalDialog.handler.find('#editCurrentUserPwdForm');
				f.submit();
			}
		} ]
	});
}

function currentUserRole() {
	parent.$.modalDialog({
		title : '我的角色',
		width : 300,
		height : 250,
		href : '${pageContext.request.contextPath}/userController/currentUserRolePage'
	});
}
function currentUserResource() {
	parent.$.modalDialog({
		title : '我可以访问的资源',
		width : 300,
		height : 250,
		href : '${pageContext.request.contextPath}/userController/currentUserResourcePage'
	});
}
</script>
</head>
<body>

	<div id="index_layout">
		<div data-options="region:'west',href:'${pageContext.request.contextPath}/manage/layout/west.html',split:true" title="导航" style="width: 200px; overflow: hidden;"></div>
		<div data-options="region:'center'" title="" style="overflow: hidden;">
			<div id="index_tabs" style="overflow: hidden;" data-options="tools:'#tab-tools'">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<div align="center"><h1>欢迎使用后台系统</h1></div>	
				</div>
			</div>
		</div>
	</div>

	<div id="index_tabsMenu" style="width: 120px; display: none;">
		<div title="refresh" data-options="iconCls:'transmit'">刷新</div>
		<div class="menu-sep"></div>
		<div title="close" data-options="iconCls:'delete'">关闭</div>
		<div title="closeOther" data-options="iconCls:'delete'">关闭其他</div>
		<div title="closeAll" data-options="iconCls:'delete'">关闭所有</div>
	</div>
	
	<div id="tab-tools">
        <span>
        	<c:if test="${empty currentUser}">[<strong>${sessionUser.username}</strong>]</c:if>
			你使用IP[<strong>[${pageContext.request.remoteAddr }]</strong>登录！
        </span>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'database_refresh'" onclick="database_refresh()"></a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'delete'" onclick="delete_fun()"></a>
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a>
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a>
	    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
	</div>
	<div id="layout_north_pfMenu" style="width: 120px; display: none;">
		<div onclick="changeThemeFun('default');" title="default">default</div>
		<div onclick="changeThemeFun('gray');" title="gray">gray</div>
		<div onclick="changeThemeFun('metro');" title="metro">metro</div>
		<div onclick="changeThemeFun('bootstrap');" title="bootstrap">bootstrap</div>
		<div onclick="changeThemeFun('black');" title="black">black</div>
		<div class="menu-sep"></div>
		<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
		<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
		<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
		<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
		<div class="menu-sep"></div>
		<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
		<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
		<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
		<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
		<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
	</div>
	<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
		<div onclick="editCurrentUserPwd();">修改密码</div>
	<!-- 	<div class="menu-sep"></div>
		<div onclick="currentUserRole();">我的角色</div>
		<div class="menu-sep"></div>
		<div onclick="currentUserResource();">我的权限</div> -->
	</div>
	<div id="layout_north_zxMenu" style="width: 100px; display: none;">
		<div onclick="logout();">退出系统</div>
	</div>
</body>
</html>