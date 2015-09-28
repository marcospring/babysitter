<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/manage/babysitter/list.html',
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
								width : 80

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
	function addPanic(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		var babysitterId = '${serviceOrderId}';
		parent.$.messager
		.confirm(
				'询问',
				'您是否要推送？',
				function(b) {
					if (b) {
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post(
							'${pageContext.request.contextPath}/manage/serviceOrder/addPanicBabysitter.html',
							{
								serviceOrderId : id,
								babysitterId:babysitterId
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
		<div id="tb" data-options="region:'north',title:'查询条件',border:false" style="height:70px; overflow: hidden;">
			<form id="searchForm">
			<table style="font-size:12px;">
				<tr>
					<td style="padding:0px;">月嫂名称：</td>
					<td style="padding:5px;"><input name="name" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">身份证号：</td>
					<td style="padding:5px;"><input name="identificationNo" class="easyui-numberbox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">城市：</td>
					<td style="padding:5px;"><input id="countyId" name="countyId" style="width:100px;"></td>
				
					<td style="padding:0px;">级别：</td>
					<td style="padding:5px;"><input id="levelid" name="levelid" style="width:100px;"></td>
					<td style="padding:0px;">电话：</td>
					<td style="padding:5px;"><input name="telephone" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">月嫂编码：</td>
					<td style="padding:5px;"><input name="cardNo" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;"><a href="javascript: queryForm();" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a></td>
				</tr>
			</table>
			
			</form>
    	</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
		<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<div onclick="addPanic();" data-options="iconCls:'pencil'">推送</div>
	</div>
	</div>
