<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!doctype html>
<html lang="zh-Hans-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>地址</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/webapp.css">
    <script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/sea.js"></script>
    <script>
        seajs.config({
            base: 'http://webapp.renwutao.com/js/',
        });
    </script>
</head>
<body>
    <div class="item-box">
        <div class="item hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_add.png"></span><span class="input-box"><input id="scanf-addr" type="text"  placeholder="请输入所在小区、街道或大厦名称" maxlength="40" required><i class="del"></i></span>
            </div>
            <div class="right"></div>
        </div>

        <div class="item hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_number.png"></span><span class="input-box"><input id="scanf-houseno" type="text"  placeholder="请输入门牌号等详细信息" maxlength="20" required><i class="del"></i></span>
            </div>
            <div class="right"></div>
        </div>
    </div>

    <div class="long-btn">
        <a class="btn" id="confirm-addr" onclick="address()">完成</a>
    </div>
    <div class="pop-box" id="pop-box">
        <section class="con">
            <div class="txt">
                <h4 id="pop-tit"></h4>
                <p id="pop-info"></p>
            </div>
            <div class="btn">
                <a class="hide" id="pop-confirm">好</a>
            </div>
        </section>
    </div>
    <script type="text/javascript">
        seajs.use('common');
        function address(){
        	var add = $("#scanf-addr").val();
        	var ress = $("#scanf-houseno").val();
        	var date = '${date}';
        	window.location="${ctx}/wechat/makeOrder.html?add="+add+"&ress="+ress+"&date="+date+"&price="+9000;
        }
    </script>
</body>
</html>
