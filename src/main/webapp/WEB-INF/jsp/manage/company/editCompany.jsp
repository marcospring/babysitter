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


		var url = '${pageContext.request.contextPath}/manage/company/edit.html' ;
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
		$('#countyId').combobox({    
		    url:'${pageContext.request.contextPath}/manage/county/comboList.html',    
		    valueField:'id',
		    editable:false, 
		    textField:'name'
		});  
		$('#countyId').combobox('setValue', '${vo.county.id}');
		$('#type').combobox('setValue', '${vo.type}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${vo.id}"/>
			<table class="table table-hover table-condensed"
				style="font-size: 14px;">
				<tr>
					<td style="text-align: right;">公司名称</td>
					<td style="text-align: left;"><input name="name" type="text"
						placeholder="请输入公司名称" class="easyui-validatebox span2"
						data-options="required:true" value="${vo.name }"></td>
				</tr>
				<tr>
					<td style="text-align: right;">公司电话</td>
					<td style="text-align: left;"><input name="telephone"
						type="text" placeholder="请输入公司电话" class="easyui-validatebox span2"
						data-options="required:true" value="${vo.telephone }"></td>
				</tr>
				<tr>
					<td style="text-align: right;">公司地址</td>
					<td style="text-align: left;"><input name="address"
						type="text" placeholder="公司地址" class="easyui-validatebox span2"
						data-options="required:true" value="${vo.address }"></td>
				</tr>
				<tr>
					<td style="text-align: right;">城市级别</td>
					<td style="text-align: left;"><input name="countyLevel"
						type="text" placeholder="请输电话号码" class="easyui-numberbox"
						data-options="required:true" value="${vo.countyLevel }"></td>
				</tr>
				<tr>
					<td style="text-align: right;">城市</td>
					<td style="text-align: left;"><input id="countyId"
						name="countyid"></td>
				</tr>
				<tr>
					<td style="text-align: right;">公司类型</td>
					<td style="text-align: left;">
					<select id="type" name="type"
						class="easyui-combobox" style="width: 200px;">
							<option value="1" selected="selected">加盟店</option>
							<option value="2">直营店</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
</div>
