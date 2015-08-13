<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		
		var url = '${pageContext.request.contextPath}/manage/companyNotice/send.html';
		$('#form').form({
			url : url,
			
			onSubmit : function(params) {
				var rows = parent.$.modalDialog.rows;
				 var i = 0;  
		        var ids = "";  
		        for(i;i<rows.length;i++){  
		        	ids += rows[i].id;  
		            if(i < rows.length-1){  
		            	ids += ',';  
		            }else{  
		                break;  
		            }  
		        } 
		        params.ids = ids;
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
			<table class="table table-hover table-condensed" style="font-size:14px;">
				<tr>
					<td style="text-align:right;">消息标题</td>
					<td style="text-align:left;"><input name="title" type="text" placeholder="请输入姓名" class="easyui-validatebox span2" data-options="required:true"></td>
                    </tr>
				<tr>
				<td style="text-align:right;">消息内容</td>
                    <td style="text-align:left;"><textarea name="memo"  placeholder="请输入消息内容"  class="easyui-validatebox span2" data-options="required:true"></textarea></td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
