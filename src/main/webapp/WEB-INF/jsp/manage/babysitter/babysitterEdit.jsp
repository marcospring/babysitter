<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {    
		    chapterValid: {    
		        validator: function(value){    
		        	var re =new RegExp("^[A-Za-z0-9]+$");
		            return re.test(value);
		        },    
		        message: '必须为英文字母与数字的组合'   
		    }    
		}); 
		
		var url = '${vo.id}' ? '${pageContext.request.contextPath}/manage/user/edit.html' : '${pageContext.request.contextPath}/manage/user/add.html';
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
					<th>用户名</th>
					<td><input name="username" type="text" placeholder="请输入用户名" class="easyui-validatebox span2" data-options="required:true,validType:'chapterValid'" value="${vo.username}"></td>
                    <th>密码</th>
                    <td><input name="password" type="text" placeholder="请输入密码"  class="easyui-validatebox span2" data-options="required:true" value="${vo.password}"></td>
				</tr>
				<tr>
					<th>用户名称</th>
					<td><input name="name" type="text" placeholder="请输用户名称" class="easyui-validatebox span2" data-options="required:true" value="${vo.name}"></td>
                    <th></th>
                    <td></td>
				</tr>
				
				<tr>
					<th>用户角色</th>
					<td colspan="4">
						<c:forEach var="role" items="${roles}" varStatus="s">
							 <c:if test="${s.index%4==0}"><br/></c:if>
							 <c:choose>
								 <c:when test="${role.checked == 0}">
									<input name="roleids" value="${role.id}" type="checkbox" style="margin-top:-4px;"/>${role.name}
								</c:when>
								 <c:when test="${role.checked == 1}">
									<input name="roleids" value="${role.id}" type="checkbox" checked="checked" style="margin-top:-4px;"/>${role.name}
								</c:when>
							</c:choose>
						</c:forEach>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
