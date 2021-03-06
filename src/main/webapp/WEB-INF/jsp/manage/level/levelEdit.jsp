<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {    
		    numberValid: {    
		        validator: function(value){    
		        	var re =new RegExp("^[0-9]+$");
		            return re.test(value);
		        },    
		        message: '必须为数字'   
		    }    
		}); 
		var url = '${vo.id}' ? '${pageContext.request.contextPath}/manage/level/edit.html' : '${pageContext.request.contextPath}/manage/level/add.html';
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
			<table class="table table-hover table-condensed"  border="1">
				<tr>
                    <td class="titleTd">级别名称</td>
                    <td style="text-align: left;"><input id="level" name="name" style="width:100px;" value="${vo.name}">></td>
					<td class="titleTd">级别分数</td>
					<td style="text-align: left;"><input name="score" type="text" placeholder="请输用户名称" class="easyui-validatebox span2" data-options="required:true,validType:'numberValid'" value="${vo.score}"></td>
				</tr>
				<tr>
                    <td class="titleTd">级别价格</td>
                    <td style="text-align: left;"><input name="money" type="text" placeholder="请输用户名称" class="easyui-validatebox span2" data-options="required:true,validType:'numberValid'" value="${vo.money}"></td>
					<td class="titleTd"></td>
					<td style="text-align: left;"></td>
				</tr>
				
				
				
			</table>
		</form>
	</div>
</div>
