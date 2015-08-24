
$.provide('detail.Calender');

detail.Calender = function() {
};


$.extend(detail.Calender.prototype, {

    init: function () {
        this.nowMonth = -1;
    },

    render: function (container) {
        var today = new Date();
        this.startYear = today.getFullYear();
        this.startMonth = today.getMonth();
        this.endYear = today.getFullYear() + 1;
        this.endMonth = today.getMonth();
        this.nowIndex = -1;
        var content = this._generateBody();
        container.html(content);
        this.adjustHeader(this.getIndexFromMonth(today.getMonth() + 1));
        this.adjustContent(today.getMonth() + 1);
        this.bindEvents();
    },

    bindEvents: function () {
        $('.c-title [data-month]').on('click', $.proxy(this.changeContent, this));
        $('.c-prev').on('click', $.proxy(this.movePrev, this));
        $('.c-next').on('click', $.proxy(this.moveNext, this));
    },

    getMonthFromIndex: function (index) {
        return (this.startMonth + index) % 12 + 1;
    },

    getIndexFromMonth: function (month) {
        return (month + 12 - this.startMonth - 1) % 12;
    },

    _generateBody: function () {
        var a = new Date(this.startYear, this.startMonth),
            b = new Date(this.endYear, this.endMonth);
        var ret, ele;
        var headers = '', bodys = '';
        var days, i;
        var cnt = 1;

        ret = '<ul class="flexbox c-title">';
        ret += '<li class="flexbox-item"><span class="c-prev"></span></li>';
        for (; a < b; a.setMonth(a.getMonth() + 1), cnt++) {
            ele =
                '<li class="flexbox-item" data-year="%%year%%" data-month="%%month%%" data-index="%%index%%">%%month%%月</li>'
                    .replace(/%%month%%/g, a.getMonth() + 1)
                    .replace("%%index%%", cnt)
                    .replace("%%year%%", a.getFullYear());
            headers += ele;

            // generate month
            ele = '<div class="c-body" data-year="' + a.getFullYear() + '" data-month="' + (a.getMonth() + 1) + '" style="display:none">';
            days = daysInMonth(a.getMonth() + 1, a.getYear());
            for (i = 1; i <= days; ++i) {
                ele += '<span>' + i + '</span>';
            }
            ele += '</div>';
            bodys += ele;
        }
        ret += headers;
        ret += '<li class="flexbox-item"><span class="c-next"></span></li>';
        ret += '</ul>';
        ret += bodys;
        return ret;
    },

    movePrev: function () {
        this.adjustHeader(this.nowIndex - 3);
    },

    moveNext: function () {
        this.adjustHeader(this.nowIndex + 3);
    },

    changeContent: function (e) {
        this.adjustContent($(e.target).data('month'));
    },

    adjustHeader: function (index) {
        var start, end, i;
        if (index < 3) {
            start = 1;
            end = 7;
        } else if (index > 9) {
            start = 7;
            end = 13;
        } else {
            start = index - 2;
            end = index + 4;
        }

        $('.flexbox-item[data-index]').hide();
        for (i = start; i < end; ++i) {
            $('.flexbox-item[data-index=' + i + ']').show();
        }
        $('.flexbox-item[data-month=' + this.nowMonth + ']').addClass('orange');
        this.nowIndex = Math.floor((start + end) / 2);
    },

    adjustContent: function (month) {
        if (month != this.nowMonth) {
            $('.flexbox-item[data-month=' + this.nowMonth + ']').removeClass('orange');
            $('.flexbox-item[data-month=' + month + ']').addClass('orange');
            $('.c-body[data-month="' + this.nowMonth + '"]').hide();
            $('.c-body[data-month="' + month + '"]').show();
            this.nowMonth = month;
        }
    },

    setUnavailable: function (dates) {
        $('.c-body').children().removeClass("unactive").addClass("active");
        dates.every(function (x) {
            var a = new Date(x.beginDate.replace(' ', 'T'));
            var b = new Date(x.endDate.replace(' ', 'T'));
            var year, month, day;
            for (; a < b; a.setDate(a.getDate() + 1)) {
                year = a.getFullYear();
                month = a.getMonth() + 1;
                day = a.getDate();
                $('.c-body[data-month=' + month + '][data-year=' + year + ']').children(':nth-child(' + day + ')').removeClass('active').addClass('unactive');
            }
            return true;
        });
    }

});

function daysInMonth(month,year) {
    return new Date(year, month, 0).getDate();
}