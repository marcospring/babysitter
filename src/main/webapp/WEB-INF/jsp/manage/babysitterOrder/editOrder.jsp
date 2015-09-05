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


		var url = '${pageContext.request.contextPath}/manage/babysitterOrder/edit.html' ;
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
		
		$('#beginDate').datebox({    
		    required:true,
		    editable:false
		}); 
		$('#endDate').datebox({    
		    required:true,
		    editable:false
		});
		var comboData =[{    
		    "id":1,    
		    "text":"新发布"   
		},{    
		    "id":2,    
		    "text":"等待上户"   
		},{    
		    "id":3,    
		    "text":"尾款结算，上户中"
		},{    
		    "id":4,    
		    "text":"上户中"   
		},{    
		    "id":5,    
		    "text":"订单完成，待评价"   
		},{    
		    "id":6,    
		    "text":"订单完成"   
		}]  ;
		$('#state').combobox({   
			data: comboData,
		    valueField:'id', 
		    editable:false,    
		    textField:'text'
		});  
		 $('#state').combobox('setValue', '${vo.state}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${vo.id}"/>
			<table class="table table-hover table-condensed" style="font-size:14px;">
				<tr>
					<td style="text-align:right;">雇主名称</td>
					<td style="text-align:left;"><input name="employerName" type="text" placeholder="请输入雇主名称" class="easyui-validatebox span2" data-options="required:true" value="${vo.employerName }"></td>
                    </tr>
				<tr>
				<td style="text-align:right;">雇主电话</td>
                    <td style="text-align:left;"><input name="employerTelephone" type="text" placeholder="请输入雇主电话"  class="easyui-numberbox span2" data-options="required:true" value="${vo.employerTelephone }"></td>
				</tr>
				<tr>
					<td style="text-align:right;">雇主地址</td>
					<td style="text-align:left;"><input name="employerAddress" type="text" placeholder="请输入雇主地址" class="easyui-validatebox span2" data-options="required:true" value="${vo.employerAddress }"></td>
                  </tr>
				<tr>
					<td style="text-align:right;">订单状态</td>
					<td style="text-align:left;"><input name="state" id="state"></td>
               </tr>
                   <tr>
                     <td style="text-align:right;">订单开始时间</td>
                    <td style="text-align:left;"><input id="beginDate" name="beginDate" type="text" data-options="required:true" value="${vo.serviceBeginDate }"></td>
                    </tr>
				<tr>
					<td style="text-align:right;">订单结束时间</td>
					<td style="text-align:left;"><input id="endDate" name="endDate"  type="text" value="${vo.serviceEndDate }"></td>
				</tr>
				<tr>
					<td style="text-align:right;">订单价格</td>
					<td style="text-align:left;"><input id="price" name="price" class = "easyui-numberbox" value="${vo.orderPrice }"> </td>
				</tr>
			
			</table>
		</form>
	</div>
</div>
