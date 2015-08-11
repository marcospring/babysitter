<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
	$('#form').form({
		url : '${pageContext.request.contextPath}/manage/user/grantMenu.html',
		onSubmit : function() {
			parent.$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			var isValid = $(this).form('validate');
			if (!isValid) {
				parent.$.messager.progress('close');
			}
            var resourceTreeObj = $.fn.zTree.getZTreeObj("resourceTree");
            if (resourceTreeObj != null) {
                var resourceTreeNodes = resourceTreeObj.getCheckedNodes(true);
                if (resourceTreeNodes.length > 0) {
					var resourceTreeIds = "";
                    for (var i = 0; i < resourceTreeNodes.length; i++) {
                    	resourceTreeIds += resourceTreeNodes[i].id + ",";
                    }
                    resourceTreeIds = resourceTreeIds.substring(0, resourceTreeIds.length - 1);
                    $("#resourceTreeIds").val(resourceTreeIds);
                }
            }
			return isValid;
		},
		success : function(result) {
			parent.$.messager.progress('close');
			result = $.parseJSON(result);
			if (result.status == 0) {
				parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
				parent.$.modalDialog.handler.dialog('close');
			} else {
				parent.$.messager.alert('信息提示', result.message, 'info');
			}
		}
	});
});
var setting = {
    check:{
        enable:true
    },
    data:{
        simpleData:{
            enable:true
        }
    }
};
setting.check.chkboxType = { "Y":"ps", "N":"ps" };

$(document).ready(function () {
    $.fn.zTree.init($("#resourceTree"), setting, ${resourceJson});
});
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west'" title="系统资源" style="width: 300px; padding: 1px;">
		<div class="well well-small">
			<form id="form" method="post">
				<input type="hidden" name="resourceTreeIds" id="resourceTreeIds"/>
				<input type="hidden" name="id" id="id" value="${role.id}"/>
				<span id="resourceTree" class="ztree"/>
			</form>
		</div>
	</div>
	<div data-options="region:'center'" style="overflow: hidden; padding: 1px;">
		<div class="well well-small">
			当前角色：<span class="label label-success">${role.name}</span>
			<br/>
			角色描述：<span class="label label-success">${role.description}</span>
		</div>
	</div>
</div>