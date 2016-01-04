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

<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.js"></script>
    <script src="${ctx}/static/js/app.js"></script>
    <script src="${ctx}/static/js/jquery.cookie.js"></script>
    <script type="text/javascript">
        var phoneWidth =  parseInt(window.screen.width);
        var phoneScale = phoneWidth / 640;
        document.write('<meta name="viewport" content="width=640, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
    </script>

</head>
<body id="page-share-5">
<c:choose>
	<c:when test="${preferential.code!=0}">
	 <script type="text/javascript">
		alert("${ preferential.msg }");
		window.history.go(-1);
	</script>
	</c:when>
	<c:when test="${preferential.code==0}">
	<img class="background" src="${ctx}/images/share/bg_2.jpg"/>
	<div class="text-box">
	    <h3>亲爱的${preferential.result.toPhone}：</h3>
	    <p class="big-text">您的<span class="rmb">${preferential.result.money}元</span>优惠卷领取成功。下单成功讲直接扣减<span class="rmb">${preferential.result.money}元</span>，关注我们并下单吧！</p>
	    <img class="qr-code" src="${ctx}/images/share/gongzhonghao.jpg">
	    <a class="btn-box btn-share" href="${ctx}/ysapp/share/1.html">生成我的推广页</a>
	    <p class="normal-text text-explain"><img src="${ctx}/images/share/q-mark.png">活动说明：<br/>☆1. 活动时间：从即日--2016年12月10日；<br/>
☆2. 此促销活动仅针对在北京地区使用月嫂订单的客户使用；<br/>
☆3. 我爱我妻公司保留在法律规定的范围内对上述规则进行解释的权利；</p>
	</div>
	</c:when>
</c:choose>


<!--<div class="lock-box" id="lock-box">-->
    <!--<p><img src="../images/loading.gif"></p>-->
<!--</div>-->

</body>
</html>
