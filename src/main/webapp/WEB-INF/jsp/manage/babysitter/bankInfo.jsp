<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		
		var url = '${pageContext.request.contextPath}/manage/babysitter/addBankInfo.html';
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
			<input type="hidden" name="id" value="${vo.id}"/>
			<table class="table table-hover table-condensed" style="font-size:14px;">
				<tr>
					<td style="text-align:right;">开户行名称</td>
					<td style="text-align:left;"><input name="bankName" type="text" placeholder="请输入开户行" class="easyui-validatebox span2" data-options="required:true" value="${vo.bankName}"></td>
                    </tr>
				<tr>
				<td style="text-align:right;">银行卡号</td>
                    <td style="text-align:left;"><input name="bankCardNo" type="text" placeholder="请输入银行卡号"  class="easyui-numberbox" data-options="required:true" value="${vo.bankCardNo}"></td>
				</tr>
				<tr>
					<td style="text-align:right;">开户名</td>
					<td style="text-align:left;"><input name="bankUserName" type="text" placeholder="请输开户名" class="easyui-validatebox span2" data-options="required:true" value="${vo.bankUserName}"></td>
                  </tr>
                
			</table>
		</form>
	</div>
</div>
