var isDebug = (window.location.href.indexOf("localhost") >= 0) || (window.location.href.indexOf("192.168") >= 0) || (window.location.href.indexOf("115.27") >= 0);
//isDebug = 1;

// 获得雇主订单信息
var fetchEmployerOrderInfoUrl = isDebug ? "data/order_list_2.json" : "/babysitter/wechat/orderList.html";
// 获得推荐月嫂列表
var fetchRecommendedYsListUrl = isDebug ? "data/ys_list.json" : "/babysitter/wechat/babysitterList.html";
// 根据雇主订单guid获得月嫂订单信息
var fetchYsOrderUrl = isDebug ? "data/ys_order.json" : "/babysitter/wechat/getBabysitterOrderInfo.html";
// 获得月嫂信息
var fetchYsInfoUrl = isDebug ? "data/ys_info_data.json" : "/babysitter/mobile/babysitter/babysitter.html";
// 同意月嫂下户
var postAgreeServiceEndUrl = isDebug ? "data/ys_order.json" : "/babysitter/wechat/serviceEnd.html";
// 51. 添加订单评论
var postAddCommentUrl = isDebug ? "data/ys_order.json" : "/babysitter/wechat/addEvaluate.html";
// 3. 级别信息列表
var fetchLevelListUrl = isDebug ? "data/level_data.json" : "/babysitter/mobile/common/levelList.html";

function redirectTo(url) {
    window.location.href = url;
}

function initLockBox() {
    if (!$('#lock-box').length) {
        $('body').append($('<div class="lock-box" id="lock-box"><p><img src="images/loading.gif"></p></div>'));
        isDebug && console.log("Lock box created.");
    }
}

function lockPage() {
    $('.lock-box').show();
}

function unlockPage() {
    $('.lock-box').hide();
}

function getQueryParam(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=');
        if (decodeURIComponent(pair[0]) == variable) {
            return decodeURIComponent(pair[1]);
        }
    }
    console.log('Query variable %s not found', variable);
}

/**
 *
 * @param method Get or Post
 * @param url 请求URL
 * @param data 参数
 * @param success 成功请求调用，function(data)
 * @param error 异常请求调用，function(errorCode)
 * @param options 其他参数
 */
function makeRequest(method, url, data, success, error, options) {

    errHandler = error ? error : function (errorCode) {
        alert("通信错误: ERR_CODE_" + errorCode);
    };

    options = options ? options : {};
    options.type = method;
    options.url = url;
    options.data = data;
    $.ajax(options).success(function (data) {
        var code;
        if (isDebug) {
            try {
                data = JSON.parse(data);
            } catch (e) {}
        }
        code = data.code;
        if (code != 0) {
            errHandler(code, data.result);
        } else {
            success(data.result);
        }
    }).error(function (xhr) {
        alert("通信异常: url=" + url + "&state=" + xhr.state());
    });

}

function jumpToDetail(jqYsInfo) {
    var guid = jqYsInfo.data('guid');
    window.location.href = "detail.html?guid=" + guid;
}

function refreshYsList(jqUl, ysList) {

    jqUl.html("");

    var i, len = ysList.length, data;
    for (i = 0; i < len; ++i) {
        var ysInfoTemplate = $("<li class=ys-info></li>").append($(".ys-info-template").html());

        data = ysList[i];

        // 更新左侧数据
        ysInfoTemplate.find('.data-pic').attr('src', data.headUrl);
        ysInfoTemplate.find('.data-price').html("￥" + data.price + "元/月");

        // 更新右侧数据
        var levelImgName;
        ysInfoTemplate.find('.data-id').html(data.name + " " + data.cardNo);
        switch (data.level) {
            case "初级月嫂": levelImgName = "chuji"; break;
            case "高级月嫂": levelImgName = "gaoji"; break;
            case "金牌月嫂": levelImgName = "jinpai"; break;
            case "皇冠月嫂": levelImgName = "huangguan"; break;
            case "中级月嫂": levelImgName = "zhongji"; break;
            case "特级月嫂": levelImgName = "teji"; break;
            case "钻石月嫂": levelImgName = "zuanshi"; break;
        }
        if (levelImgName) {
            ysInfoTemplate.find('.data-id').append($('<span class="white-space"></span>'));
            ysInfoTemplate.find('.data-id').append($('<img class="level" src="images/level-icon/' + levelImgName + '.png"/>'));
        }
        ysInfoTemplate.find('.data-score').html("客户满意度：" + data.score + "分");
        ysInfoTemplate.find('.data-order-count').html("接单数量：" + data.orderCount);

        ysInfoTemplate.find('.data-more').html("");
        if (data.isV) {
            ysInfoTemplate.find('.data-more').append($('<span><img src="images/v.png">资料认证</span><span class="white-space"></span>'));
        }
        if (data.promotions.length > 0) {
            var pro = $('<span class="data-promotion"><img src="images/free.png">2天免费试用</span>');
            pro.html(data.promotions[0].title);
            pro.attr('src', data.promotions[0].iconUrl);
            ysInfoTemplate.find('.data-more').append(pro);
        }

        ysInfoTemplate.data('guid', data.guid);
        ysInfoTemplate.click(function (e) {
            jumpToDetail($(e.target).closest('.ys-info'));
        });
        jqUl.append(ysInfoTemplate);
    }
}
