<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Tables | Bootstrap 3.x Admin Theme</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap -->
<link rel="stylesheet" media="screen"
	href="${ctx}/css/bootstrap.min.css">
<link rel="stylesheet" media="screen"
	href="${ctx}/css/bootstrap-theme.min.css">

<!-- Bootstrap Admin Theme -->
<link rel="stylesheet" media="screen"
	href="${ctx}/css/bootstrap-admin-theme.css">
<link rel="stylesheet" media="screen"
	href="${ctx}/css/bootstrap-admin-theme-change-size.css">

<!-- Datatables -->
<link rel="stylesheet" media="screen" href="${ctx}/css/DT_bootstrap.css">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
</head>
<body style="padding-top:0px">
	<div class="col-md-12">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="text-muted bootstrap-admin-box-title">
						角色管理&nbsp;
						<button class="btn btn-xs btn-info" id="btnAddRole"><i class="glyphicon glyphicon-plus-sign"></i>&nbsp;添加角色</button>
						<button class="btn btn-xs btn-info"><i class="glyphicon glyphicon-filter"></i>&nbsp;功能分配</button>
						</div>
					</div>
					<div class="bootstrap-admin-panel-content">
						<table class="table  table-bordered">
							<thead>
								<tr>
									<th>编号</th>
									<th>GUID</th>
									<th>角色名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="role" items="${page.result}">
									<tr class="odd gradeX">
										<td>${role.id}</td>
										<td>${role.guid}</td>
										<td>${role.name}</td>
										<td>
											 <button class="btn btn-sm btn-primary"><i class="glyphicon glyphicon-pencil"></i> Edit</button>
                                              <button class="btn btn-sm btn-success"><i class="glyphicon glyphicon-ok-sign"></i>Confirm</button>
                                              <button class="btn btn-sm btn-warning"><i class="glyphicon glyphicon-bell"></i> Notify</button> 
                                              <button class="btn btn-sm btn-danger"> <i class="glyphicon glyphicon-trash"></i> Delete </button>
										</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						${page.pageStr }
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="addRole" tabindex="-1" role="dialog" >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="addRoleTitle">添加角色</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="focusedInput">角色名称</label>
						<div class="col-lg-10">
							<input id="roleName" class="form-control" type="text" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="activeAddRole" class="btn btn-primary" >添加</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/twitter-bootstrap-hover-dropdown.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/bootstrap-admin-theme-change-size.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/DT_bootstrap.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#btnAddRole").bind("click",function(){
				$("#addRole").modal("show");
			})
			$("#activeAddRole").bind("click",function(){
				var roleName = $("#roleName").val();
				var param = {};
				param.roleName = roleName;
				$.post('${ctx}/role/addRole.html',param,function(data){
					if(data.code == 0){
						window.location = "${ctx}/role/roleList.html"
					}else{
						alert(data.msg);
					}
				});
			})
		});
	</script>
</body>
</html>