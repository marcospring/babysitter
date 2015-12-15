<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>月嫂之星 | 分享</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/all.css">
    <link rel="stylesheet" href="${ctx}/static/css/share.css">

    <script src="${ctx}/static/js/jquery-1.8.3.js"></script>
    <script src="${ctx}/static/js/app.js"></script>
  <%--   <script src="${ctx}/static/js/jquery.cookie.js"></script> --%>
    <script type="text/javascript">
        var phoneWidth =  parseInt(window.screen.width);
        var phoneScale = phoneWidth / 640;
        document.write('<meta name="viewport" content="width=640, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
    </script>

</head>
<body id="page-share-4">

<img class="background" src="${ctx}/images/share/bg_2.jpg"/>

<div class="text-box">
    <h3>Hi，我是月嫂之星</h3>
    <p class="big-text align-center block-1">${preferential.telephone}用户给你${preferential.message}</p>
    <p class="rmb align-center block-2">${preferential.money}元优惠券</p>
    <div class="wrapper">
        <input class="btn-box ipt-tel" placeholder="请输入手机号">
        <div class="one-line">
            <input class="btn-box ipt-sms-code" placeholder="请输入验证码">
            <a class="btn-box btn-get-sms-code align-center">获取验证码</a>
        </div>
        <a class="btn-box btn-get-ticket align-center">领取优惠券</a>
    </div>
</div>

<!--<div class="lock-box" id="lock-box">-->
    <!--<p><img src="../images/loading.gif"></p>-->
<!--</div>-->

</body>
</html>
