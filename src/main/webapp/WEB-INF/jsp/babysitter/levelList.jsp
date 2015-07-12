<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>babysitter</title>
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
						月嫂级别管理&nbsp;
						<button class="btn btn-xs btn-info" id="btnAddLevel"><i class="glyphicon glyphicon-plus-sign"></i>&nbsp;添加月嫂级别</button>
						</div>
					</div>
					<div class="bootstrap-admin-panel-content">
						<table class="table  table-bordered">
							<thead>
								<tr>
									<th>编号</th>
									<th>名称</th>
									<th>积分</th>
									<th>升级费</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="level" items="${levels}">
									<tr class="odd gradeX">
										<td>${level.id}</td>
										<td>${level.name}</td>
										<td>${level.score}</td>
										<td>${level.money}</td>
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
	<div class="modal fade" id="addLevel" tabindex="-1" role="dialog" >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="addRoleTitle">添加级别</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="focusedInput">名称</label>
						<div class="col-lg-10">
							<input id="name" class="form-control" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="focusedInput">积分</label>
						<div class="col-lg-10">
							<input id="score" class="form-control" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="focusedInput">升级费</label>
						<div class="col-lg-10">
							<input id="money" class="form-control" type="text" />
						</div>
					</div>
					
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="activeAddLevel" class="btn btn-primary" >添加</button>
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
			$("#btnAddLevel").bind("click",function(){
				$("#addLevel").modal("show");
			})
			$("#activeAddLevel").bind("click",function(){
				var name = $("#name").val();
				var score = $("#score").val();
				var money = $("#money").val();
				var param = {};
				param.name = name;
				param.score = score;
				param.money = money;
				$.post('${ctx}/babysitter/addLevel.html',param,function(data){
					if(data.code == 0){
						window.location = "${ctx}/babysitter/levelList.html"
					}else{
						alert(data.msg);
					}
				});
			})
		});
	</script>
</body>
</html>