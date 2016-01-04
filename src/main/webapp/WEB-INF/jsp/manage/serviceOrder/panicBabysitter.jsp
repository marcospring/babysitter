<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/manage/serviceOrder/panicBabysitterlist.html?serviceOrderId=${getBabysitterServiceOrderId}',
							fit : true,
							fitColumns : false,
							border : false,
							pagination : true,
							idField : 'id',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50 ],
							nowrap : false,
							checkOnSelect : false,
							selectOnCheck : false,
							singleSelect : true,
							frozenColumns : [ [ { field: 'ck', 
								 checkbox: true 
							 },{
								field : 'id',
								title : '编号',
								width : 30
							}, {
								field : 'cardNo',
								title : '编码',
								width : 70
							}, {
								field : 'guid',
								title : 'GUID',
								width : 300
							} ] ],
							columns : [ [ {
								field : 'name',
								title : '用户名称',
								width : 90

							}, {
								field : 'mobilePhone',
								title : '电话',
								width : 100

							}, {
								field : 'county',
								title : '城市',
								width : 90,
								formatter : function(val) {
									return val.name;
								}

							}, {
								field : 'level',
								title : '级别',
								width : 90

							}, {
								field : 'identificationNo',
								title : '身份证号',
								width : 130

							}, {
								field : 'lowerSalary',
								title : '最低薪水',
								width : 120

							}, {
								field : 'age',
								title : '年龄',
								width : 60

							} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								$('#searchForm table').show();
								parent.$.messager.progress('close');

								$(this).datagrid('tooltip');
							},
							onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).datagrid('unselectAll').datagrid(
										'uncheckAll');
								$(this).datagrid('selectRow', rowIndex);
								$('#menu').menu('show', {
									left : e.pageX,
									top : e.pageY
								});
							},
							onLoadError : function() {
								$.messager.alert('信息提示', '会话超时或没有权限使用该功能',
										'info');
							}
						});

	});
	
	function queryForm(){
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function markBabysitter() {
		
		var rows = dataGrid.datagrid('getChecked');
		if(rows.length >1){
			alert('只能选择一位');
			return;
		}
		 var i = 0;  
	        var id = "";  
	        for(i;i<rows.length;i++){  
	        	id = rows[i].id; 
	        } 
		
		var serviceOrderId = '${getBabysitterServiceOrderId}';
		parent.$.messager
		.confirm(
				'询问',
				'您是否要选中该月嫂？',
				function(b) {
					if (b) {
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post(
							'${pageContext.request.contextPath}/manage/serviceOrder/markPanicBabysitter.html',
							{
								serviceOrderId : serviceOrderId,
								babysitterId:id
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
</script>

	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
			<div id="toolbar" style="display: none;">
		<a onclick="markBabysitter();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">选定</a>
	</div>

	</div>
