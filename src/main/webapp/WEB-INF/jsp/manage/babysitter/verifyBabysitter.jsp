<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>月嫂管理</title>
<jsp:include page="/inc.jsp"></jsp:include>
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
							frozenColumns : [ [  { field: 'ck', 
								 checkbox: true 
							 },{
								field : 'id',
								title : '编号',
								width : 50
							}, {
								field : 'cardNo',
								title : '编码',
								width : 70
							}, {
								field : 'guid',
								title : 'GUID',
								width : 70
							}, {
								field : 'state',
								title : '是否验证',
								width : 70,
								formatter : function(val) {
									return val==1?"<font color='green'>通过</font>":"<font color='red'>未通过</font>";
								}
							}  ] ],
							columns : [ [ {
								field : 'name',
								title : '用户名称',
								width : 200

							}, {
								field : 'mobilePhone',
								title : '电话',
								width : 300

							}, {
								field : 'county',
								title : '城市',
								width : 300,
								formatter : function(val) {
									return val.name;
								}

							}, {
								field : 'level',
								title : '级别',
								width : 300

							}, {
								field : 'identificationNo',
								title : '身份证号',
								width : 350

							}, {
								field : 'lowerSalary',
								title : '最低薪水',
								width : 150

							}, {
								field : 'age',
								title : '年龄',
								width : 100

							}, {
								field : 'nativePlace',
								title : '籍贯',
								width : 150

							}, {
								field : 'bankName',
								title : '开户行名称',
								width : 300

							}, {
								field : 'bankCardNo',
								title : '银行卡号',
								width : 300

							}, {
								field : 'bankUserName',
								title : '开户名',
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
	function verifyFun(state) {
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
						state ==1?'您是否要审核通过？':'您是否要取消审核？',
						function(b) {
							if (b) {
								parent.$.messager.progress({
									title : '提示',
									text : '数据处理中，请稍后....'
								});
								$.post(
									'${pageContext.request.contextPath}/manage/babysitter/verify.html',
									{
										ids : ids,
										state:state
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
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="verifyFun(1);" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">验证通过</a>
		<a onclick="verifyFun(0);" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">取消验证</a>
	</div>

</body>
</html>