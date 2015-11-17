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


		var url = '${vo.id}' ? '${pageContext.request.contextPath}/manage/babysitter/edit.html' : '${pageContext.request.contextPath}/manage/babysitter/add.html';
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
		    textField:'name',
		    onSelect: function(rec){    
	            var url = '${pageContext.request.contextPath}/manage/countyLevel/comboList.html?countyid='+rec.id;  
	            $('#levelId').combobox('reload', url);   
	            $('#levelId').combobox('select', '');
	        }
		});  
		$('#levelId').combobox({   
			url: '${pageContext.request.contextPath}/manage/countyLevel/comboList.html?countyid='+'${vo.county.id}',
		    valueField:'id', 
		    editable:false,    
		    textField:'name'
		});  
		 $('#countyId').combobox('setValue', '${vo.county.id}');
		 $('#levelId').combobox('setValue', '${vo.level.id}');
		$('#birthday').datebox({    
		    required:true,
		    editable:false
		}); 
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${vo.id}"/>
			<table class="table table-hover table-condensed" border="1" >
				<tr>
					<td class="titleTd">用户名称</td>
					<td style="text-align:left;"><input name="name" type="text" placeholder="请输入姓名" class="easyui-validatebox span2" data-options="required:true" value="${vo.name}"></td>
				<td  class="titleTd">密码</td>
                    <td style="text-align:left;"><input name="password" type="text" placeholder="请输入密码"  class="easyui-validatebox span2" data-options="required:true" value="${vo.password}"></td>
                </tr>
				<tr>
					<td  class="titleTd">最低薪水</td>
					<td style="text-align:left;"><input name="lowerSalary" type="text" placeholder="请输用户名称" class="easyui-validatebox span2" data-options="required:true" value="${vo.lowerSalary}"></td>
                     <td class="titleTd">移动电话</td>
                    <td style="text-align:left;"><input name="mobilePhone" type="text" placeholder="请输电话号码" class="easyui-numberbox" data-options="required:true" value="${vo.mobilePhone}"></td>
                  </tr>
				<tr>
					<td  class="titleTd">城市</td>
					<td><input id="countyId" name="countyId" ></td>
					<td class="titleTd">级别</td>
					<td style="text-align:left;"><input id="levelId" name="levelId"></td>
				</tr>
				<tr>
					<td  class="titleTd">身份证号</td>
					<td style="text-align:left;"><input name="identificationNo" type="text" placeholder="请输身份证号" class="easyui-validatebox span2" data-options="required:true" value="${vo.identificationNo}"></td>
					<td  class="titleTd">生日</td>
					<td style="text-align:left;"><input name="birthday" id="birthday" type="text"  value="${vo.birthday}"></td>
				</tr>
				<tr>
					<td  class="titleTd">籍贯</td>
					<td style="text-align:left;"><input name="nativePlace" type="text" placeholder="请输籍贯" class="easyui-validatebox span2" value="${vo.nativePlace}"></td>
					<td  class="titleTd">身高</td>
					<td style="text-align:left;"><input name="height" type="text" placeholder="身高" class="easyui-validatebox span2" value="${vo.height}"></td>
				</tr>
				<tr>
					<td  class="titleTd">体重</td>
					<td style="text-align:left;"><input name="weight" type="text" placeholder="体重" class="easyui-validatebox span2" value="${vo.weight}"></td>
					<td  class="titleTd">爱好</td>
					<td style="text-align:left;"><input name="hobbies" type="text" placeholder="爱好" class="easyui-validatebox span2" value="${vo.hobbies}"></td>
				</tr>
				
				<tr>
					<td  class="titleTd">普通话</td>
					<td style="text-align:left;"><input name="mandarin" type="text" placeholder="普通话" class="easyui-validatebox span2" value="${vo.mandarin}"></td>
					<td  class="titleTd">是否为V</td>
					<td style="text-align:left;">
						<c:if test="${vo.isV ==1 }">
							<input type="radio" name="isV" value="1" checked="checked"/>是
							<input type="radio" name="isV" value="0"/>否
						</c:if>
						<c:if test="${vo.isV ==0 || empty vo.isV }">
							<input type="radio" name="isV" value="1" />是
							<input type="radio" name="isV" value="0" checked="checked"/>否
						</c:if>
					</td>
				</tr>
				<tr>
					<td  class="titleTd">开户行名称</td>
					<td style="text-align:left;"><input name="bankName" type="text" placeholder="请输入开户行" class="easyui-validatebox span2" data-options="required:true" value="${vo.bankName}"></td>
					<td  class="titleTd">银行卡号</td>
					<td style="text-align:left;"><input name="bankCardNo" type="text" placeholder="请输入银行卡号"  class="easyui-validatebox span2" data-options="required:true" value="${vo.bankCardNo}"></td>
				</tr>
				<tr>
					<td  class="titleTd">开户名</td>
					<td style="text-align:left;"><input name="bankUserName" type="text" placeholder="请输开户名" class="easyui-validatebox span2" data-options="required:true" value="${vo.bankUserName}"></td>
					<td  class="titleTd"></td>
					<td style="text-align:left;"></td>
				</tr>
				<tr>
					<td  class="titleTd" valign="top">简介</td>
					<td style="text-align:left;" colspan="3"><textarea cols="70" rows="3" name="introduce" class="easyui-validatebox" >${vo.introduce}</textarea></td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
