
$.provide('detail.Page');

detail.Page = function() {
};


$.extend(detail.Page.prototype, {
    init: function() {
        // var guid = '25087D5D6EDD45A282A7D9B663DD0900';
		var guid = $.getUrlParam('guid');
        // 浣跨敤鏈嶅姟鍣ㄦ帴鍙ｆ椂鎶婁笅闈袱琛屾敞閲婂璋冿紝鍙﹀闇�瑕佹纭殑璁剧疆涓婇潰鐨刧uid
        // data.json鏄竴涓湰鍦扮殑娴嬭瘯鏁版嵁锛屽彲鍒�
        this.url_ = '/babysitter/mobile/babysitter/babysitter.html?guid=' + guid;
//        this.url_ = 'data.json';
        this.index = 4;
        this.hiddend = true;
        this.initCalender();
        this.initLoader();
        this.bindEvents();
    },

    initLoader:function() {
        this.loader = new oss.Loader();
        this.loader.render($('#main-page'));
        this.loader.send(this.url_);
    },

    initCalender: function () {
        this.calender = new detail.Calender;
        this.calender.render($('.calender'));
    },

    bindEvents: function() {
        $(this.loader).on('renderpage', $.proxy(this.handleRenderPage, this));
        $('.arrow').bind('click', $.proxy(this.showHiddenend, this));
    },
    showHiddenend: function(e) {
        var container = $(e.target).parent().children('.hidden-container');
        if (this.hiddend) {
            $(e.target).parent().children('.arrow').addClass('up').removeClass('down');
            container.show(300);
            this.hiddend = false;
        } else {
            $(e.target).parent().children('.arrow').addClass('down').removeClass('up');
            container.hide(300);
            this.hiddend = true;
        }
    },

    ClickTitle: function(e) {
        var elem = $(e.target);
        var index = elem.data('month');
        this.calender.adjustContent(index);

    },

    handleRenderPage: function(e, response) {
        var data = response.result;
        var i;
		if (!data) {
			alert('月嫂信息不存在');
			return;
		}
        data.professcial = [];
        data.credential = [];
        data.photos = [];
        for (i = 0; i < data.credentials.length; i++) {
            var cre = data.credentials[i];
            if (cre.check != 1)
                continue;
            if (cre.credentialType == 2 ) {
                data.professcial.push(cre);
            } else {
                data.credential.push(cre);
            }
        }
        for (i = 0; i < data.images.length; i++) {
            data.photos.push({img: data.images[i]});
        }
        if (data.professcial.length > 4) {
            data.moreProfesscial = true;
        }
        if (data.professcial.length > 8) {
            data.moreCredential = true;
        }
        data.amount = data.orders.length;

        this.insertDate(data);
        //this.pageUI.contentElem.append(this.app.rebuildWebAppUrl(QTMPL.index.render(data)));
        this.renderCompelete();
    },

    insertDate: function (data) {
        var exactDateFunc = function (x) {
            return { beginDate: x.beginDate, endDate: x.endDate };
        };
        var unavailableDates = data.orders.map(exactDateFunc).concat(data.restInfos.map(exactDateFunc));
        this.calender.setUnavailable(unavailableDates);
		var levelImgName;
		
		switch (data.level) {
			case "初级月嫂": levelImgName = "chuji"; break;
			case "高级月嫂": levelImgName = "gaoji"; break;
			case "金牌月嫂": levelImgName = "jinpai"; break;
			case "皇冠月嫂": levelImgName = "huangguan"; break;
			case "中级月嫂": levelImgName = "zhongji"; break;
			case "特级月嫂": levelImgName = "teji"; break;
			case "钻石月嫂": levelImgName = "zuanshi"; break;
		}
		
        $('.head-img').attr('src', data.headUrl);
        $('.data-salary').html(data.price);
        $('.data-name').html(data.name);
		if (data.isV)
			$('.name').append($('<img src="ysimg/v.png"/>'));
		if (levelImgName)
			$('.name').append($('<img src="ysimg/level-icon/' + levelImgName + '.png"/>'));
        $('.data-age').html(data.age);
        $('.data-native-place').html(data.nativePlace);
        $('.data-score').html(data.score);
        $('.data-order-count').html(data.orderCount);
        if (data.promotions.length > 0) {
            $('.data-promotion').html(data.promotions[0].title);
            $('.promotion>img').attr('src', data.promotions[0].iconUrl);
        }
        $('.data-nation').html(data.nation);
        $('.data-height').html(data.height);
        $('.data-weight').html(data.weight);
        $('.data-hobbies').html(data.Hobbies);
        $('.data-mandarin').html(data.Mandarin);

        function translateTimeString(dateStr) {
            var d = new Date(dateStr.split(' ')[0]);
            return d.getFullYear() + '年' + (d.getMonth() + 1) + '月' + d.getDate() + '日';
        }

        var commentTemplate = $('.comment-template').html(), order;
        var tel;
        for (i = 0; i < data.amount; ++i) {
            order = data.orders[i];
            tel = order.telephone;
            tel = tel.substr(0, 3) + 'xxxx' + tel.substr(7);
            $('.data-comment-list').append(
                commentTemplate.
                    replace('%%SCORE%%', order.score ? order.score + "分" : "未评价").
                    replace('%%EMPLORER%%', order.employerName).
                    replace('%%TEL%%', tel).
                    replace('%%CONTENT%%', order.evaluation ? order.evaluation : "该客户暂时未进行评价").
                    replace('%%ORDERID%%', order.orderId).
                    replace('%%TIME%%', translateTimeString(order.beginDate) + '至' + translateTimeString(order.endDate)).
                    replace('%%ADDRESS%%', order.address.substr(0, 5) + "****")
            );
        }

        var imageTemplate = '<div class="swiper-slide"><a href="%%IMGURL%%" class="swipebox"><img data-src="%%IMGURL%%" /></a></div>', image;
        for (i = 0; i < data.images.length; ++i) {
            image = data.images[i];
            $('.data-image-list').append(
                imageTemplate.replace(/%%IMGURL%%/g, image.url)
                    .replace('data-src', 'src')
            );
        }
		$('.data-image-list').parent().find('a.swipebox').attr('rel', 'gallery-1');

        function createFlexboxContainerDom() {
            return ret = $("<div></div>");
        }

        var checkedItemTemplate = $('.checked-item-template').html(), item;
        var cont;
        for (i = 0; i < data.credential.length; ++i) {
            if (i % 3 == 0) {
                cont = createFlexboxContainerDom();
                $('.data-credential').append(cont);
            }
            item = data.credential[i];
            cont.append(
                checkedItemTemplate.replace('%%NAME%%', item.name)
            );
            $('.data-credential-image').append(
                imageTemplate.replace(/%%IMGURL%%/g, item.url)
                    .replace('data-src', 'src')
            );
        }
		$('.data-credential-image').parent().find('a.swipebox').attr('rel', 'gallery-2');
		

        for (i = 0; i < data.professcial.length; ++i) {
            if (i % 3 == 0) {
                cont = createFlexboxContainerDom();
                $('.data-professcial').append(cont);
            }
            item = data.professcial[i];
            cont.append(
                checkedItemTemplate.replace('%%NAME%%', item.name)
            );
            $('.data-professial-image').append(
                imageTemplate.replace(/%%IMGURL%%/g, item.url)
                    .replace('data-src', 'src')
            );
        }
		$('.data-professial-image').parent().find('a.swipebox').attr('rel', 'gallery-3');

    },

    renderCompelete: function() {
        //initialize swiper when document ready
        var mySwiper = new Swiper ('.swiper-container', {
            autoplay: 8000, // ms
            paginationClickable: true,
            centerSlides: true,
            width: 320,
            slidesPerView: 2.4,
            freeMode: true,
            //centeredSlides: true,
            spaceBetween: 0
        });

        // initialize swipebox
        $('.swipebox').swipebox();
    }

});

(function ($) {
                $.getUrlParam = function (name) {
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null) return unescape(r[2]); return null;
                }
            })(jQuery);
