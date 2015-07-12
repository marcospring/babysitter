<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html class="bootstrap-admin-vertical-centered">
<head>
        <title>Login page | Bootstrap 3.x Admin Theme</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap -->
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-theme.min.css">

        <!-- Bootstrap Admin Theme -->
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme.css">

        <!-- Custom styles -->
        <style type="text/css">
            .alert{
                margin: 0 auto 20px;
            }
        </style>

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
</head>
<body class="bootstrap-admin-without-padding">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				 
				<form class="bootstrap-admin-login-form">
					<h1>登录</h1>
					<div class="form-group">
						<input class="form-control" type="text" id="username"
							placeholder="Username">
					</div>
					
					<div class="form-group">
						<input class="form-control" type="password" id="password"
							placeholder="Password">
					</div>
					
					<button id="loginBtn" class="btn btn-lg btn-primary" type="button">登陆</button>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			// Setting focus
			$('input[name="username"]').focus();
			$("#loginBtn").bind('click',function() {
				var params = {};
				params.username = $.trim($("#username").val());
				params.password = $.trim($("#password").val());
				$.post('${ctx}/user/login.html',
						params,
						function(data) {
							if (data.code == 0) {
								window.parent.location = "${ctx}/main/m.html";
							} else {
								alert(data.msg);
							}
						});
			});
		});
	
	</script>
</body>
</html>