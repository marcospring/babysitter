Ext.define("babysitter.loginWin", {
			extend : "Ext.window.Window",
			singleton : true,
			hideMode : 'offsets',
			closeAction : 'hide',
			closable : false,
			resizable : false,
			title : '月嫂管理系统',
			width : 300,
			height : 250,
			modal : true,
			currentTabIndex : 0,
			initComponent : function() {
				var me = this;
				me.fields = [];
				me.fields.push(Ext.widget("textfield", {
							fieldLabel : "用户名",
							name : "username",
							allowBlank : false,
							tabIndex : 1,
							listeners : {
								scope : me,
								focus : me.setTabIndex
							}
						}));
				me.fields.push(Ext.widget("textfield", {
							fieldLabel : "用户名",
							name : "password",
							inputType : "password",
							allowBlank : false,
							tabIndex : 2,
							listeners : {
								scope : me,
								focus : me.setTabIndex
							}
						}));
				me.form = Ext.create(Ext.form.Panel, {
							border : false,
							bodyPadding : 5,
							api : {
								submit : Ext.app.Login.Check
							},
							bodyStyle : "background:#DFE9F6",
							fieldDefaults : {
								labelWidth : 80,
								labelSeparator : "：",
								anchor : "0"
							},
							items : [me.fields[0], me.fields[1]],
							docketItems : [{
										xtype : 'toolbar',
										dock : 'bottom',
										ui : 'footer',
										layout : {
											pack : "center"
										},
										items : [{
													text : "登陆",
													disabled : true,
													formBind : true,
													handler : me.onLogin,
													scope : me
												}, {
													text : "重置",
													handler : me.onReset,
													scope : me
												}]
									}]

						});
				me.items = [me.form];
				me.on("show", function() {
							me.onReset();
						}, me);
				me.callParent(arguments);
			},
			initEvents : function() {
				var me = this;
				me.KeyNav = Ext.create("Ext.util.KeyNav", me.form.getEl(), {
							enter : me.onFocus,
							scope : me
						})
			},
			onLogin : function() {
				var me = this, f = me.form.getForm();
				if (f.isValid()) {
					f.submit
				}
			}
			
		})