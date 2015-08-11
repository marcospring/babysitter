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