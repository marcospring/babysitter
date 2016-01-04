<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		var url = '${pageContext.request.contextPath}/manage/serviceOrder/addEmployer.html';
		$('#form').form({
			url : url,
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.status == 0) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.modalDialog.handler.dialog('close');
				}else{
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed" border="1">
				<tr>
					<td class="titleTd">雇主名称</td>
					<td style="text-align:left;"><input name="employerName" type="text" placeholder="请输入雇主名称" class="easyui-validatebox span2" data-options="required:true" ></td>
                    </tr>
				<tr>
				<td class="titleTd">雇主电话</td>
                    <td style="text-align:left;"><input name="telephone" type="text" placeholder="请输入雇主电话"  class="easyui-numberbox span2" data-options="required:true" ></td>
				</tr>
				<tr>
					<td class="titleTd">雇主地区</td>
                    <td style="text-align:left;">
                    	<input name="countyId"type="hidden"value="${county.id}">
                    	<input name="countyName" readonly="readonly" type="text" class="span2" value="${county.name }">
                    </td>
				</tr>
				<tr>
					<td class="titleTd">雇主地址</td>
					<td style="text-align:left;"><input name="address" type="text" placeholder="请输入雇主地址" class="easyui-validatebox span2" data-options="required:true" ></td>
                  </tr>
			</table>
		</form>
	</div>
</div>
