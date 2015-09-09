<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		
		$('#pid').combotree({
			url : '${pageContext.request.contextPath}/manage/county/manageComboList.html',
			parentField : 'parent.id',
			value : '${vo.parent.id}',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});

		var url = '${vo.id}' ? '${pageContext.request.contextPath}/manage/county/edit.html' : '${pageContext.request.contextPath}/manage/county/add.html';
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');
					parent.$.modalDialog.handler.dialog('close');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${vo.id}"/>
			<table class="table table-hover table-condensed" border="1">
				<tr>
					<td class="titleTd">城市名称</td>
					<td style="text-align: left;"><input name="name" type="text" placeholder="请输入资源名称" class="easyui-validatebox span2" data-options="required:true" value="${vo.name}"></td>
				</tr>
				<tr>
                    <td class="titleTd">城市简称</td>
                    <td style="text-align: left;"><input name="shortName" type="text" placeholder="请输入资源路径"  class="easyui-validatebox span2" size="35" value="${vo.shortName}"></td>
				</tr>
				
				<tr>
					<td class="titleTd">上级城市</td>
					<td style="text-align: left;"><select id="pid" name="pid" style="width: 140px; height: 29px;"></select><img src="${pageContext.request.contextPath}/static/component/au/images/extjs_icons/cut_red.png" onclick="$('#pid').combotree('clear');" /></td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
