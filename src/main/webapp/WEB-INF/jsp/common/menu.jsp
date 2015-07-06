<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title>menu</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap -->
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-theme.min.css">

        <!-- Bootstrap Admin Theme -->
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme.css">
        <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme-change-size.css">

        <!-- Vendors -->
        <!-- (...) -->

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="bootstrap-admin-with-small-navbar">

        <div class="container">
            <!-- left, vertical navbar & content -->
            <div class="row">
                <!-- left, vertical navbar -->
                <div class="col-lg-12">
                    <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                    	<c:forEach var="menu" items="${menus}">
	                        <li>
	                            <a href="${menu.url }"><i class="glyphicon glyphicon-chevron-right"></i> ${menu.title }</a>
	                        </li>
                    	</c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/twitter-bootstrap-hover-dropdown.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/bootstrap-admin-theme-change-size.js"></script>
    </body>
</html>