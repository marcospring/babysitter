<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>月嫂证件管理</title>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid')
				.datagrid(
						{                                          
							url : '${pageContext.request.contextPath}/manage/credential/goCredentialList.html',
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
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 30
							}, {
								field : 'yuesaoid',
								title : '月嫂id',
								width : 50
							}, {
								field : 'babysitterNo',
								title : '月嫂编号',
								width : 80
							} ] ],
							columns : [ [  {
								field : 'babysitterName',
								title : '月嫂姓名',
								width : 100

							}, {
								field : 'babysitterCounty',
								title : '月嫂城市',
								width : 100

							},  {
								field : 'babysitterTelephone',
								title : '月嫂电话',
								width : 100

							},   {
								field : 'babysitterLevel',
								title : '月嫂级别',
								width : 100

							}, {
								field : 'name',
								title : '证件名称',
								width : 90

							},{
								field : 'check',
								title : '是否审核',
								width : 100,
								formatter : function(val) {
									return val==0?"<font color='red'>未审核</font>":"<font color='green'>已审核</font>";
								}


							}, {
								field : 'url',
								title : '图片',
								width : 90,
								formatter : function(val) {
									return "<a target='blank' href='"+val+"'><img width='50' height='50' src='"+val+"'/></a>";
								}

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
		$('#countyId').combobox({    
		    url:'${pageContext.request.contextPath}/manage/county/comboList.html',    
		    valueField:'id',
		    editable:false, 
		    textField:'name',
		    onSelect: function(rec){    
	            var url = '${pageContext.request.contextPath}/manage/countyLevel/comboList.html?countyid='+rec.id;  
	            $('#levelid').combobox('reload', url);   
	            $('#levelid').combobox('select', '');
	        }
		});
		
		$('#levelid').combobox({    
		    valueField:'id', 
		    editable:false,    
		    textField:'name'
		});  
	});
	
	function queryForm(){
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function valid(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager
		.confirm(
				'询问',
				'您是否要审核通过？',
				function(b) {
					if (b) {
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post(
							'${pageContext.request.contextPath}/manage/credential/check.html',
							{
								credentialId : id,
								flag:1
							},
							function(result) {
								if (result.status == 0) {
									parent.$.messager
											.alert(
													'提示',
													result.message,
													'info');
									dataGrid.datagrid('reload');
								}
								parent.$.messager
										.progress('close');
							}, 'JSON');
					}
				});
	}
	function unvalid(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager
		.confirm(
				'询问',
				'您是否要审核不通过？',
				function(b) {
					if (b) {
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post(
							'${pageContext.request.contextPath}/manage/credential/check.html',
							{
								credentialId : id,
								flag:0
							},
							function(result) {
								if (result.status == 0) {
									parent.$.messager
											.alert(
													'提示',
													result.message,
													'info');
									dataGrid.datagrid('reload');
								}
								parent.$.messager
										.progress('close');
							}, 'JSON');
					}
				});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div id="tb" data-options="region:'north',title:'查询条件',border:false" style="height:70px; overflow: hidden;">
			<form id="searchForm">
			<table style="font-size:12px;">
				<tr>
					<td style="padding:0px;">证件名称：</td>
					<td style="padding:5px;"><input name="credentialName" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">月嫂编号：</td>
					<td style="padding:5px;"><input name="babysitterId"  class = "easyui-numberbox" type="text"  style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">月嫂姓名：</td>
					<td style="padding:5px;"><input name="babysitterName" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;"><a href="javascript: queryForm();" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a></td>
				</tr>
			</table>
			</form>
    	</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
		
	</div>
<div id="toolbar" style="display: none;">
	<a onclick="" href="javascript:valid()"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">审核通过</a>
	<a onclick="" href="javascript:unvalid()"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">审核不通过</a>
</div>
</body>
</html>