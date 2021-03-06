<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {
			numberValid : {
				validator : function(value) {
					var re = new RegExp("^[0-9]+$");
					return re.test(value);
				},
				message : '必须为英文字母与数字的组合'
			}
		});
		$.fn.datebox.defaults.formatter = function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + '-' + (m < 10 ? ("0" + m) : m) + '-' + d;
		}

		var url = '${pageContext.request.contextPath}/manage/company/add.html';
		$('#form').form({
			url : url,
			type:'json',
			onSubmit : function() {
				
				var isValid = $(this).form('validate');
				//if (!isValid) {
				//	parent.$.messager.progress('close');
				//}
				return isValid;
			},
			success : function(result) {
				 var data = eval('(' + result + ')');  // change the JSON string to javascript object    
			        if (data.status !=0){
			        	$.messager.alert('警告',data.message);  
			        }else{
			        	$.messager.alert('警告','添加成功！'); 
			        }
				$('#form').reset();
			}
		});
		$('#countyId')
			.combobox(
					{
						url : '${pageContext.request.contextPath}/manage/county/comboList.html',
						valueField : 'id',
						editable : false,
						textField : 'name'

					});

	});
	function sub(){
		$('#form').submit();
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">

			<table class="table table-hover table-condensed"
				style="font-size: 14px;">
				<tr>
					<td style="text-align: right;">公司名称</td>
					<td style="text-align: left;"><input name="name" type="text"
						placeholder="请输入公司名称" class="easyui-validatebox span2"
						data-options="required:true" ></td>
				</tr>
				<tr>
					<td style="text-align: right;">公司电话</td>
					<td style="text-align: left;"><input name="telephone"
						type="text" placeholder="请输入公司电话" class="easyui-validatebox span2"
						data-options="required:true" ></td>
				</tr>
				<tr>
					<td style="text-align: right;">公司地址</td>
					<td style="text-align: left;"><input name="address"
						type="text" placeholder="公司地址" class="easyui-validatebox span2"
						data-options="required:true" ></td>
				</tr>
				<tr>
					<td style="text-align: right;">城市级别</td>
					<td style="text-align: left;"><input name="countyLevel"
						type="text" placeholder="请输电话号码" class="easyui-numberbox"
						data-options="required:true" ></td>
				</tr>
				<tr>
					<td style="text-align: right;">城市</td>
					<td style="text-align: left;"><input id="countyId"
						name="countyid"></td>
				</tr>
				<tr>
					<td style="text-align: right;">公司类型</td>
					<td style="text-align: left;">
					<select name="type"
						class="easyui-combobox" style="width: 200px;">
							<option value="1" selected="selected">加盟店</option>
							<option value="2">直营店</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="2">
				<input type="button" value="添加" onclick="sub()" />
					
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
