<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>babysitter</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/static/css/all.css" rel="stylesheet">
<link href="${ctx}/static/css/share.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx}/static/js/app.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.cookie.js"></script>
 <script type="text/javascript">
        var phoneWidth =  parseInt(window.screen.width);
        var phoneScale = phoneWidth / 640;
        document.write('<meta name="viewport" content="width=640, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
        function sub(){
        	$("#getForm").submit();
        }
        function getCodee(){
        	var ctx ="${ctx}";
        	var param = {};
        	param.telephone=$("#phoneNo").val();
        	param.type=5;
        	$.ajax({
        		url:ctx+"/mobile/common/code.html",
        		data:param,
        		type:"post",
        		dataType:"json",
        		success:function(data){
        			if(data.code ==0)
        				alert("发送成功！");
        		}
        	});
        }
    </script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
</head>
<body id="page-share-4">
<img class="background" src="${ctx}/images/share/bg_2.jpg"/>
<div class="text-box">
    <h3>Hi，我是月嫂之星</h3>
    <p class="big-text align-center block-1">${preferential.telephone}用户分享给你的</p>
    <p class="rmb align-center block-2">${preferential.money}元优惠券</p>
	<form id="getForm" action="${ctx}/preferential/addPreferentialReceiver.html">
    <div class="wrapper">
		<input type="hidden" name="preferentialValue" value="${preferential.guid}"/>
        <input id="phoneNo" name="toUserTelephone" class="btn-box ipt-tel" placeholder="请输入手机号">
        <div class="one-line">
            <input name="verifyCode" class="btn-box ipt-sms-code" placeholder="请输入验证码"><a class="btn-box btn-get-sms-code align-center" onclick="getCodee()">获取验证码</a>
        </div>
        <a class="btn-box btn-get-ticket align-center" onclick="sub()">领取优惠券</a>
    </div>
	</form>
</div>

</body>
</html>