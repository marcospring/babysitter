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
		var url = '${vo.id}' ? '${pageContext.request.contextPath}/manage/countyLevel/edit.html' : '${pageContext.request.contextPath}/manage/countyLevel/add.html';
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
		$('#countyid').combobox({    
		    url:'${pageContext.request.contextPath}/manage/county/comboList.html',    
		    valueField:'id',
		    editable:false,
		    textField:'name'   
		});  
		$('#levelid').combobox({    
		    url:'${pageContext.request.contextPath}/manage/level/list.html',    
		    valueField:'id',
		    editable:false,    
		    textField:'name'   
		});  
		$('#countyid').combobox('setValue', '${vo.county.id}');
		$('#levelid').combobox('setValue', '${vo.level.id}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${vo.id}"/>
			<table class="table table-hover table-condensed" style="font-size:13px;">
				<tr>
					<th>城市：</th>
					<td><input id="countyid" name="countyid" style="width:100px;"></td>
                    <th>级别：</th>
                    <td><input id="levelid" name="levelid" style="width:100px;"></td>
				</tr>
				<tr>
					<th>级别分数</th>
					<td><input name="score" type="text" placeholder="请输用户名称" class="easyui-validatebox span2" data-options="required:true,validType:'numberValid'" value="${vo.score}"></td>
                    <th>级别价格</th>
                    <td><input name="money" type="text" placeholder="请输用户名称" class="easyui-validatebox span2" data-options="required:true,validType:'numberValid'" value="${vo.money}"></td>
				</tr>
				
				
				</tr>
				
			</table>
		</form>
	</div>
</div>
