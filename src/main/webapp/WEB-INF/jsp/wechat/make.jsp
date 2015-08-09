<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<!-- saved from url=(0061)http://weixin.yunjiazheng.com/maternitymatron/index?city_id=2 -->
<html lang="zh-Hans-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>预约月嫂</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/webapp.css">
    <script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/mobiscroll_002.js" type="text/javascript"></script>
	<script src="${ctx}/js/mobiscroll.js" type="text/javascript"></script>
	<script src="${ctx}/js/mobiscroll_003.js" type="text/javascript"></script>
    <link href="${ctx}/css/mobiscroll_style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${ctx}/js/sea.js"></script>
    <script>
        seajs.config({
            base: 'http://webapp.renwutao.com/js/',
        });
    </script>
</head>
<body>
    <div class="view-service" id="view-service">
        <p>查看服务内容</p>
    </div>
    <div class="item-box">
        <div class="item hasicon dheader">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_date.png"></span>
                <span>
				<input placeholder="请选择预产期" class="appDate" readonly="readonly" name="appDate" id="appDate" type="text" value="${date}">
                </span>
            </div>
        </div>
    </div>
    <div class="item-box">
        <div class="item header hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_price.png"></span><span>价格<i class="i-size">(元/月，26工作日/月)</i></span>
            </div>
            <div class="right">
            	<span class="tip"><a href="#">评级标准</a></span>
                <!--<span class="tip"><em class="red">￥<i>8k-8.5k</i></em></span>-->
            </div>
        </div>

        <div class="range-box">
            <p class="info">
                <span><br/>8k</span>
                <span>初级<br/>8.5k</span>
                <span>高级<br/>9k</span>
                <span>皇冠<br/>1.1k</span>
                <span>特级<br/>1.238k</span>
                <span>皇冠<br/>2.18</span>
                <span>皇冠<br/>2.19k</span>
            <span></span>
            </p>
            <p class="range"><input id="yy-xlprice" type="range" value="30" data-max="10000" data-min="6000" data-step="1000" max="120" min="0" step="0.5"></p>
            <ul>
               <li id="21">345名</li>
               <li id="22">35名</li>
               <li id="23">4名</li>
               <li id="24">27名</li>
               <li id="25">32名</li>
               <li id="26">21名</li>
               <li id="26">23名</li>
           </ul>
        </div>
    </div>
    <div class="item-box item-box-d">
        <a href="javascript:void(0)" onclick="address()" class="item hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_add_l.png"></span><span class="input-box"><input value="${add }${ress }" class="yy-addr" id="yy-addr" type="text" placeholder="请输入服务地址" readonly=""></span>
            </div>
            <div class="right">
                <i class="icon-right"></i>
            </div>
        </a>
    </div>
    <div class="item-box">
        <div class="item hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_people.png"></span><span class="input-box"><input id="name" type="text" placeholder="请输入联系人姓名" maxlength="20" required=""><i class="del"></i></span>
            </div>
        </div>
        <div class="item hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_tel.png"></span><span class="input-box"><input id="tel" type="tel" placeholder="请输入您的手机号" maxlength="13" required=""><i class="del"></i></span>
            </div>
            <div class="right"><input name="" type="button"  onclick="code()" class="validate" value="点击获取验证码"></div>
        </div>
        <div class="item hasicon">
            <div class="left">
                <span class="icon"><img src="${ctx}/images/icon_tel.png"></span><span class="input-box"><input id="code" type="tel" placeholder="请输入验证码" maxlength="13" required=""><i class="del"></i></span>
            </div>
        </div>
    </div>
    <div class="long-btn fix">
        <a class="btn"  onclick="publish()">发布订单</a>
    </div>
    <div class="pop-service" id="pop-service">
        <section>
            <h4>服务内容</h4>
            <p>照顾产妇及新生儿。</p>
            <p>主要包括：产妇护理、婴儿护理、婴儿护理辅导、产期杂务等。</p>
        </section>
        <a><img src="${ctx}/images/icon_close.png" alt="关闭"></a>
    </div>
    <div class="lock-box" id="lock-box" style="display: none; -webkit-transform-origin: 0px 0px; opacity: 1; -webkit-transform: scale(1, 1);">
        <p><img src="imaes/icon_refresh.png"></p>
    </div>
    <div class="pop-box" id="pop-box">
        <section class="con">
            <div class="txt">
                <h4 id="pop-tit"></h4>
                <p id="pop-info">请输入服务地址</p>
            </div>
            <div class="btn">
                <a class="hide" id="pop-confirm" style="-webkit-transform-origin: 0px 0px; opacity: 1; -webkit-transform: scale(1, 1);">确认</a>
            </div>
        </section>
    </div>
    <script>
        seajs.use('longer');
    </script>
    <script type="text/javascript">
	$(function () {
		var currYear = (new Date()).getFullYear();	
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.default = {
			theme: 'android-ics light', //皮肤样式
			display: 'modal', //显示方式 
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy-mm-dd',
			lang: 'zh',
			showNow: true,
			nowText: "今天",
			startYear: currYear - 10, //开始年份
			endYear: currYear + 10 //结束年份
		};
		$("#appDate").mobiscroll($.extend(opt['date'], opt['default']));
		var optDateTime = $.extend(opt['datetime'], opt['default']);
		var optTime = $.extend(opt['time'], opt['default']);
		$("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
		$("#appTime").mobiscroll(optTime).time(optTime);
	});
	function address(){
		var date = $("#appDate").val();
		var price = 2000;
		window.location = "${ctx}/wechat/add.html?date="+date+"&price="+price;
	}
	function code(){
		var num = $("#yy-tel").val();
		$.ajax({
			url:"${ctx}/mobile/common/code.html",
			type:"post",
			data:{
				telephone:num,
				type:2
			},
			success:function(data){
				
			}
		});
	}
	function publish(){
		var date = $("#appDate").val();
		var adress=$("#yy-addr").val();
		var name=$("#name").val();
		var phone=$("#tel").val();
		var code=$("#code").val();
		$.ajax({
			url:"${ctx}/wechat/addOrder.html",
			type:"post",
			data:{
				date:date,
				price:9000,
				address:adress,
				name:name,
				mobile:phone,
				checkCode:code
			},
			success:function(data){
				if(data.code==0){
					window.location="${ctx}/wechat/orderList.html?phone="+phone;
				}
			}
		});
	}
</script>
</body>
</html>