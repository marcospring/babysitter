<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>订单删除</title>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/manage/babysitterOrder/list.html',
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
							frozenColumns : [ [{ field: 'ck', 
								 checkbox: true 
							 },  {
								field : 'id',
								title : '编号',
								width : 50
							}, {
								field : 'guid',
								title : 'GUID',
								width : 70
							} , {
								field : 'orderId',
								title : '订单号',
								width : 70
							}, {
								field : 'state',
								title : '订单状态',
								width : 70,
								formatter : function(val) {
									var stateStr = "";
									if(val ==1){
										stateStr = "新发布";
									}else if(val ==2){
										stateStr = "等待上户";
									}else if(val ==3||val ==4){
										stateStr = "上户中";
									}else if(val ==5 || val ==6){
										stateStr = "订单完成";
									}
									return stateStr;
								}
							}] ],
							columns : [ [ {
								field : 'babysitterName',
								title : '月嫂姓名',
								width : 200

							},{
								field : 'employerName',
								title : '雇主姓名',
								width : 200

							}, {
								field : 'telephone',
								title : '雇主电话',
								width : 300

							}, {
								field : 'address',
								title : '雇主地址',
								width : 300,
								

							}, {
								field : 'beginDate',
								title : '开始时间',
								width : 350

							}, {
								field : 'endDate',
								title : '结束时间',
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
	function deleteFun(id) {
		var rows = dataGrid.datagrid('getChecked');
		 var i = 0;  
	        var ids = "";  
	        for(i;i<rows.length;i++){  
	        	ids += rows[i].id;  
	            if(i < rows.length-1){  
	            	ids += ',';  
	            }else{  
	                break;  
	            }  
	        } 
	    
		parent.$.messager
				.confirm(
						'询问',
						'您是否要删除当前订单？',
						function(b) {
							if (b) {
								parent.$.messager.progress({
									title : '提示',
									text : '数据处理中，请稍后....'
								});
								$.post(
									'${pageContext.request.contextPath}/manage/babysitterOrder/delete.html',
									{
										ids : ids
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
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div id="tb" data-options="region:'north',title:'查询条件',border:false" style="height:70px; overflow: hidden;">
			<form id="searchForm">
			<table style="font-size:13px;">
				<tr>
					<td style="padding:0px;">月嫂GUID：</td>
					<td style="padding:5px;"><input name="babysitterGuid" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">月嫂姓名：</td>
					<td style="padding:5px;"><input name="babysitterName" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">雇主姓名：</td>
					<td style="padding:5px;"><input name="employerName" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">雇主电话：</td>
					<td style="padding:5px;"><input name="employerTelephone" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;">状态：</td>
					<td style="padding:5px;">
						<select name="state" class="easyui-combobox"style="width:200px;">   
						    <option value="" selected="selected">所有</option>   
						    <option value="1">新发布</option>   
						    <option value="2">等待上户</option> 
						    <option value="3">尾款结算，上户中</option>    
						    <option value="4">上户中</option>    
						    <option value="5">订单完成，待评价</option>    
						    <option value="6">订单完成</option>      
						</select> 
					</td>
					
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
		<a onclick="deleteFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">删除</a>
		<!-- <a onclick="batchDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'delete'">批量删除</a> -->
	</div>
	
	
</body>
</html>