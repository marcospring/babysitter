<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>发送消息</title>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/manage/companyNotice/noticeList.html',
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
							
							columns : [ [ {
								field : 'babysitterName',
								title : '月嫂姓名',
								width : 100

							},{
								field : 'state',
								title : '是否已读',
								width : 100,
								formatter:function(val,rowdata,index){
									if(val == 1){
										return "<font color='green'>已读</font>";
									}else{
										return "<font color='red'>未读</font>";
									}
								}

							},  {
								field : 'title',
								title : '标题',
								width : 200

							}, {
								field : 'memo',
								title : '通知内容',
								width : 500
								
							}] ],
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
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div id="tb" data-options="region:'north',title:'查询条件',border:false" style="height:70px; overflow: hidden;">
			<form id="searchForm">
			<table style="font-size:13px;">
				<tr>
					<td style="padding:0px;">月嫂名称：</td>
					<td style="padding:5px;"><input name="name" class="easyui-validatebox" type="text" style="width:150px;height: 25px;"></td>
					<td style="padding:0px;"><a href="javascript: queryForm();" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a></td>
				</tr>
			</table>
			
			</form>
    	</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	
	
</body>
</html>