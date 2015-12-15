<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>babysitter</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
</head>
<body style="padding-top:0px">
	${preferential.telephone}用户给你${preferential.message}的${preferential.money}元优惠卷
	<form action="${ctx}/preferential/addPreferentialReceiver.html">
		<input type="hidden" name="preferentialValue" value="${preferential.guid}"/>
		<input type="text" name="toUserTelephone" value="请输入手机号"/><br/>
		<input type="text" name="verifyCode" value="请输入验证码"/>
		<input type="button"  value="获取验证码"/><br/>
		<input type="submit" value="领取">
	</form>
</body>
</html>