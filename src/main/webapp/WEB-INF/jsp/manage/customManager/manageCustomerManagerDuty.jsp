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
		        message: '必须为英文字母与数字的组合'   
		    }    
		}); 
		$.fn.datebox.defaults.formatter = function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m < 10 ? ("0" + m) : m)+'-'+d;
		}


		var url = '${pageContext.request.contextPath}/manage/customerManager/duty.html' ;
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
					parent.$.messager
					.alert(
							'提示',
							result.message,
							'info');
				}else{
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		$('#countyId').combobox({    
		    url:'${pageContext.request.contextPath}/manage/county/comboList.html',    
		    valueField:'id',
		    editable:false, 
		    textField:'name'
		});  
		//$('#countyId').combobox('setValue', '${vo.county.id}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${customerManager.id}"/>
			<table class="table table-hover table-condensed"
				style="font-size: 14px;">
				<tr>
					<td style="text-align: right;">客户经理</td>
					<td style="text-align: left;">${customerManager.name}</td>
				</tr>
				<tr>
					<td style="text-align: right;">星期</td>
					<td style="text-align: left;">
					<select name="week">
						<option value="1">一</option>
						<option value="2">二</option>
						<option value="3">三</option>
						<option value="4">四</option>
						<option value="5">五</option>
						<option value="6">六</option>
						<option value="7">日</option>
					</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">城市</td>
					<td style="text-align: left;"><input id="countyId"
						name="countyid"></td>
				</tr>
			</table>
		</form>
	</div>
</div>
