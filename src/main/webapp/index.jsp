<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width,initial-scale=1" name="viewport">
<title>Insert title here</title>
<!-- Bootstrap -->
<link rel="stylesheet" media="screen" href="css/bootstrap.min.css">
<link rel="stylesheet" media="screen" href="css/bootstrap-theme.min.css">

<!-- Bootstrap Admin Theme -->
<link rel="stylesheet" media="screen"
	href="css/bootstrap-admin-theme.css">
<!-- Custom styles -->
<style type="text/css">
.alert {
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
                   
                    <form method="post" action="login.html" class="bootstrap-admin-login-form">
                        <h1>登陆</h1>
                        <div class="form-group">
                            <input class="form-control" type="text" id="username" placeholder="Username">
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="password" id="password" placeholder="Password">
                        </div>
                        <button id="loginBtn" class="btn btn-lg btn-primary" type="submit">登陆</button>
                    </form>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript">
            $(function() {
                // Setting focus
                $('input[name="username"]').focus();
                $("#loginBtn").bind('click',function(){
                	var params = {};
                	params.username=$.trim($("#username").val());
                	params.password=$.trim($("#password").val());
                	$.post('/user/login.html',params,function(data){
                		if(data.code == 0){
                			window.parent.location="user/main.html";
                		}else{
                			alert(data.msg);
                		}
                	});
                });
            });
        </script>
</body>
</html>