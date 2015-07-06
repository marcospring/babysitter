<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title>top</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">


        <!-- Bootstrap -->
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-theme.min.css">

        <!-- Bootstrap Admin Theme -->
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme.css">
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme-change-size.css">

        <!-- Custom styles -->
        <style type="text/css">
            @font-face {
                font-family: Ubuntu;
                src: url('${ctx}/fonts/Ubuntu-Regular.ttf');
            }
        </style>

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="bootstrap-admin-with-small-navbar">
        <!-- small navbar -->
        <nav class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar-sm" role="navigation">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="collapse navbar-collapse">
                            
                            <ul class="nav navbar-nav navbar-right">
                               
                                <li class="dropdown">
                                    <a href="#" role="button" class="dropdown-toggle" data-hover="dropdown"> <i class="glyphicon glyphicon-user"></i> ${sessionUser.username } <i class="caret"></i></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="${ctx}/index.jsp">Logout</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <!-- main / large navbar -->
        <nav class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small" role="navigation">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".main-navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand">你好，${sessionUser.name}</a>
                        </div>
                       
                    </div>
                </div>
            </div><!-- /.container -->
        </nav>
		<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/twitter-bootstrap-hover-dropdown.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/bootstrap-admin-theme-change-size.js"></script>
    </body>
</html>
