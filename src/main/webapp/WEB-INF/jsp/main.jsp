<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>About | Bootstrap 3.x Admin Theme</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap Docs -->
<link href="http://getbootstrap.com/docs-assets/css/docs.css"
	rel="stylesheet" media="screen">

<!-- Bootstrap -->
<link rel="stylesheet" media="screen"
	href="${ctx }/css/bootstrap.min.css">
<link rel="stylesheet" media="screen"
	href="${ctx }/css/bootstrap-theme.min.css">

<!-- Bootstrap Admin Theme -->
<link rel="stylesheet" media="screen"
	href="${ctx }/css/bootstrap-admin-theme.css">
<link rel="stylesheet" media="screen"
	href="${ctx }/css/bootstrap-admin-theme-change-size.css">

<!-- Custom styles -->
<style type="text/css">
@font-face {
	font-family: Ubuntu;
	src: url('${ctx}/fonts/Ubuntu-Regular.ttf');
}

.bs-docs-masthead {
	background-color: #6f5499;
	background-image: linear-gradient(to bottom, #563d7c 0px, #6f5499 100%);
	background-repeat: repeat-x;
}

.bs-docs-masthead {
	padding: 0;
}

.bs-docs-masthead h1 {
	color: #fff;
	font-size: 40px;
	margin: 0;
	padding: 34px 0;
	text-align: center;
}

.bs-docs-masthead a:hover {
	text-decoration: none;
}

.meritoo-logo a {
	background-color: #fff;
	border: 1px solid rgba(66, 139, 202, 0.4);
	display: block;
	font-family: Ubuntu;
	padding: 22px 0;
	text-align: center;
}

.meritoo-logo a, .meritoo-logo a:hover, .meritoo-logo a:focus {
	text-decoration: none;
}

.meritoo-logo a img {
	display: block;
	margin: 0 auto;
}

.meritoo-logo a span {
	color: #4e4b4b;
	font-size: 18px;
}

.row-urls {
	margin-top: 4px;
}

.row-urls .col-md-6 {
	text-align: center;
}

.row-urls .col-md-6 a {
	font-size: 14px;
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
	<nav
		class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar-sm"
		role="navigation">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav navbar-right">

							<li class="dropdown"><a href="#" role="button"
								class="dropdown-toggle" data-hover="dropdown"> <i
									class="glyphicon glyphicon-user"></i> ${sessionUser.username}<i
									class="caret"></i></a>
								<ul class="dropdown-menu">
									<li><a href="index.html">Logout</a></li>
								</ul></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</nav>

	<!-- main / large navbar -->
	<nav
		class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small"
		role="navigation">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target=".main-navbar-collapse">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="about.html">Hello，${sessionUser.name}</a>
					</div>
				</div>
			</div>
		</div>
		<!-- /.container -->
	</nav>
	<script type="text/javascript">
		function delay() {
			t = setTimeout("ajustSize()", 500);
		}
		function ajustSize() {
			var frame = document.getElementById("mainContent");
			var win = frame.contentWindow;
			doc = win.document;
			html = doc.documentElement;
			body = doc.body;
			var height = Math.max(body.scrollHeight, body.offsetHeight,
					html.clientHeight, html.scrollHeight, html.offsetHeight, $(
							window).height() - 85);
			frame.setAttribute("height", height);
		}
	</script>
	<div class="container">
		<!-- left, vertical navbar & content -->
		<div class="row">
			<!-- left, vertical navbar -->
			<div class="col-md-2 bootstrap-admin-col-left">
				<ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
					<c:forEach var="menu" items="${menus}">
						<li><a href="${ctx }${menu.url}" target="mainContent"><i
								class="glyphicon glyphicon-chevron-right"></i> ${menu.title }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-md-10 bootstrap-admin-col-center">
				<iframe id="mainContent" name="mainContent" width="100%"
					height="200" scrolling="no" frameborder="0"
					allowtransparency="true" src="" onload="delay()"></iframe>
			</div>
		</div>

	</div>
	<!-- footer -->
	<div class="navbar navbar-footer">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<footer role="contentinfo">
						<p class="left">月嫂之星</p>
						<p class="right">
							&copy; 2013 <a href="http://www.meritoo.pl" target="_blank">Meritoo.pl</a>
						</p>
					</footer>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${ctx }/js/twitter-bootstrap-hover-dropdown.min.js"></script>
	<script type="text/javascript"
		src="${ctx }/js/bootstrap-admin-theme-change-size.js"></script>
</body>
</html>
