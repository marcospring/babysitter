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
<body class="bootstrap-admin-with-small-navbar">
	<div class="col-md-12">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="text-muted bootstrap-admin-box-title">角色管理</div>
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
								<tr class="odd gradeX">
									<td>Trident</td>
									<td>Internet Explorer 4.0</td>
									<td>Win 95+</td>
									<td class="center">4</td>
								</tr>

							</tbody>
						</table>
						<nav>
							<ul class="pagination">
								<li><a href="#" aria-label="Previous"> <span
										aria-hidden="true">&laquo;</span>
								</a></li>
								<li><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#" aria-label="Next"> <span
										aria-hidden="true">&raquo;</span>
								</a></li>
							</ul>
						</nav>
					</div>
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
</body>
</html>