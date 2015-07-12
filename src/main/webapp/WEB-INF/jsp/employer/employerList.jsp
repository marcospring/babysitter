<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>employer</title>
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
						雇主管理&nbsp;
						</div>
					</div>
					<div class="bootstrap-admin-panel-content">
						<table class="table  table-bordered">
							<thead>
								<tr>
									<th>编号</th>
									<th>姓名</th>
									<th>电话</th>
									<th>Email</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="employer" items="${page.result}">
									<tr class="odd gradeX">
										<td>${employer.id}</td>
										<td>${employer.username}</td>
										<td>${employer.mobilePhone}</td>
										<td>${employer.email}</td>
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