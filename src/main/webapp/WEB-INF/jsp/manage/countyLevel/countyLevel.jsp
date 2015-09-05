<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>城市级别管理</title>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/manage/countyLevel/list.html',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			nowrap : false,
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect : true,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 50
			}, {
				field : 'guid',
				title : 'guid',
				width : 300
			} ] ],
			columns : [ [ {
				field : 'countyName',
				title : '城市名称',
				width : 200

			}, {
				field : 'name',
				title : '级别名称',
				width : 300

			}, {
				field : 'score',
				title : '升级分数',
				width : 300

			}, {
				field : 'price',
				title : '默认价格',
				width : 300

			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadError : function() {
				$.messager.alert('信息提示', '会话超时或没有权限使用该功能', 'info');
			}
		});
	});

	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager
				.confirm(
						'询问',
						'您是否要删除当前用户？',
						function(b) {
							if (b) {
								var currentUserId = '${sessionUser.id}';/*当前登录用户的ID*/
									parent.$.messager.progress({
										title : '提示',
										text : '数据处理中，请稍后....'
									});
									$
											.post(
													'${pageContext.request.contextPath}/manage/countyLevel/delete.html',
													{
														id : id
													},
													function(result) {
														if (result.status == 0) {
															parent.$.messager
																	.alert(
																			'提示',
																			result.message,
																			'info');
															dataGrid
																	.datagrid('reload');
														}
														parent.$.messager
																.progress('close');
													}, 'JSON');
							
							}
						});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$
				.modalDialog({
					title : '编辑城市级别',
					width : 690,
					height : 170,
					href : '${pageContext.request.contextPath}/manage/countyLevel/goEdit.html?id='
							+ id,
					buttons : [ {
						text : '编辑',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.submit();
						}
					} ]
				});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加城市级别',
			width : 690,
			height : 170,
			href : '${pageContext.request.contextPath}/manage/countyLevel/goAdd.html',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="addFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">添加</a>
		<!-- <a onclick="batchDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'delete'">批量删除</a> -->
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
		<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
	</div>
</body>
</html>