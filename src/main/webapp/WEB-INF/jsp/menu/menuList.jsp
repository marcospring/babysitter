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
						菜单管理&nbsp;
						<button class="btn btn-xs btn-info" id="btnAddMenu"><i class="glyphicon glyphicon-plus-sign"></i>&nbsp;添加菜单</button>
						</div>
					</div>
					<div class="bootstrap-admin-panel-content">
						<table class="table  table-bordered">
							<thead>
								<tr>
									<th>编号</th>
									<th>名称</th>
									<th>uri</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="menu" items="${menus}">
									<tr class="odd gradeX">
										<td>${menu.id}</td>
										<td>${menu.title}</td>
										<td>${menu.url}</td>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="addMenu" tabindex="-1" role="dialog" >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="addRoleTitle">添加菜单</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="focusedInput">菜单名称</label>
						<div class="col-lg-10">
							<input id="title" class="form-control" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="focusedInput">菜单路径</label>
						<div class="col-lg-10">
							<input id="url" class="form-control" type="text" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="activeAddMenu" class="btn btn-primary" >添加</button>
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
			$("#btnAddMenu").bind("click",function(){
				$("#addMenu").modal("show");
			})
			$("#activeAddMenu").bind("click",function(){
				var title = $("#title").val();
				var url = $("#url").val();
				var param = {};
				param.title = title;
				param.url = url;
				$.post('${ctx}/menu/addMenu.html',param,function(data){
					if(data.code == 0){
						window.location = "${ctx}/menu/menuList.html"
					}else{
						alert(data.msg);
					}
				});
			})
		});
	</script>
</body>
</html>