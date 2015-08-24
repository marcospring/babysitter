$.provide("ptouch.calendar");

ptouch.calendar = function(select) {
    ptouch.calendar.select = select;
    this._holidays = ptouch.calendar.holidays;
};

ptouch.calendar.holidays = {
    "2013-1-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "元旦",
        "holidayClass": "yuandan"
    },
    "2013-2-9": {
        "afterTime": 0,
        "beforeTime": 0,
        "dayindex": 0,
        "holidayName": "除夕",
        "holidayClass": "chuxi"
    },
    "2013-2-10": {
        "afterTime": 0,
        "beforeTime": 0,
        "dayindex": 0,
        "holidayName": "春节",
        "holidayClass": "chunjie"
    },
    "2013-2-11": {
        "afterTime": 0,
        "beforeTime": 0,
        "dayindex": 0,
        "holidayName": "初二"
    },
    "2013-2-12": {
        "afterTime": 0,
        "beforeTime": 0,
        "dayindex": 0,
        "holidayName": "初三"
    },
    "2013-2-24": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "元宵",
        "holidayClass": "yuanxiao"
    },
    "2013-4-4": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "清明",
        "holidayClass": "qingming"
    },
    "2013-5-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "劳动",
        "holidayClass": "laodong"
    },
    "2013-6-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "儿童"
    },
    "2013-6-12": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "端午",
        "holidayClass": "duanwu"
    },
    "2013-9-10": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "教师"
    },
    "2013-9-19": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "中秋",
        "holidayClass": "zhongqiu"
    },
    "2013-10-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "国庆",
        "holidayClass": "guoqing"
    },
    "2013-12-25": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "圣诞",
        "holidayClass": "shengdan"
    },
    "2014-1-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "元旦",
        "holidayClass": "yuandan"
    },
    "2014-1-30": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "除夕",
        "holidayClass": "chuxi"
    },
    "2014-1-31": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "春节",
        "holidayClass": "chunjie"
    },
    "2014-2-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "正月初二"
    },
    "2014-2-2": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "正月初三"
    },
    "2014-2-14": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "元宵",
        "holidayClass": "yuanxiao"
    },
    "2014-4-5": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "清明",
        "holidayClass": "qingming"
    },
    "2014-5-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "劳动",
        "holidayClass": "laodong"
    },
    "2014-6-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "儿童"
    },
    "2014-6-2": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "端午",
        "holidayClass": "duanwu"
    },
    "2014-9-8": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "中秋",
        "holidayClass": "zhongqiu"
    },
    "2014-10-1": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "国庆",
        "holidayClass": "guoqing"
    },
    "2014-12-25": {
        "afterTime": 3,
        "beforeTime": 3,
        "dayindex": 0,
        "holidayName": "圣诞",
        "holidayClass": "shengdan"
    }
};

ptouch.calendar.prototype = {

    _init: function() {
        this._attachEvents();
    },

    _attachEvents: function() {},

    render: function(data) {
        var self = this;
        var calendar = ['<div class="calendar-body">'];
        for (var key in data) {
            calendar.push('<div class="calendar-one">');
            var date = self._parseDate(key);
            calendar.push(self._renderHeader(date) + self._renderBody(date, data[key]));
            calendar.push('</div>');
        }
        calendar.push('</div>');
        return calendar.join('');
    },

    _renderHeader: function(date) {

        var header = '<div class="calendar-one-header"><span class="year"><em>' + date.getFullYear() + '</em>年</span><span class="month"><em>' + (date.getMonth() + 1) + '</em>月</span></div>';

        return header;
    },

    _renderBody: function(date, data) {
        var body = '<div class="calendar-one-body">\
                        <table class="calendar-week">\
                            <tbody>\
                                <tr>\
                                    <th>周一</th>\
                                    <th>周二</th>\
                                    <th>周三</th>\
                                    <th>周四</th>\
                                    <th>周五</th>\
                                    <th class="weekend">周六</th>\
                                    <th class="weekend">周日</th>\
                                </tr>\
                            </tbody>\
                        </table>\
                        <table class="calendar-no">\
                            <tbody>';

        var everyDay = [];

        var year = date.getFullYear();

        var month = date.getMonth();

        var days = this._getDaysInMonth(year, month + 1);

        var weekday = this._getDayOfWeek(year, month);

        if(weekday == 0)
            weekday = 7;
        var apart = weekday - 1;

        var zeroDay = 2 - weekday;

        var total = apart + days;

        for (var i = zeroDay, j = 0; i <= total; i++, j++) {
            var time = new Date(year, month, i);
            if (j % 7 == 0) {
                everyDay.push('</tr>', '<tr>');
            }

            if (i < 1 || i > days) {
                everyDay.push('<td></td>');
                continue;
            }

            var isWeekend = false;
            if (this._isWeekend(time.getDay())) {
                isWeekend = true;
                everyDay.push('<td class="weekend');
            } else {
                everyDay.push('<td class="');
            }

            var value = time.getDate();

            if (this._getTodayTime() == time.getTime()) {
                value = '今天';
            }

            var formatedTime = year + '-' + (month + 1) + '-' + i;

            if (this._holidays[formatedTime]) {
                value = this._holidays[formatedTime].holidayName;
                if (!isWeekend) {
                    everyDay.push(' ' + 'weekend');
                }
            }

            var disabled = false;

            if (!data[i - 1]) {
                everyDay.push(' ' + 'expired');
                disabled = true;
            }

            everyDay.push('"');
            if (!disabled) {
                everyDay.push(' onclick="ptouch.calendar.select(event' +','+ year + ',' + (month + 1) + ',' + i + ');return false;"');
            }

            everyDay.push('>');
            everyDay.push('<span>');
            everyDay.push(value);
            everyDay.push('</span>');
            if (!disabled && data[i - 1].price) {
                everyDay.push('<br><span class="price">&yen;' + data[i - 1].price + '</span>');
            }
            everyDay.push('</td>');
        }

        everyDay = everyDay.slice(1, -1);
        body = body + everyDay.join('') + '</tbody></table></div>';
        return body;
    },

    _isWeekend: function(week) {
        return week == 6 || week == 0;
    },

    _getTodayTime: function() {

        var today = new Date();

        return new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime();
    },

    _getDaysInMonth: function(year, month) { //返回指定月份的天数，默认是当月

        var date = null;
        if (arguments.length == 2) {
            date = new Date(year, month, 0);
        } else {

            var temp = new Date();

            date = new Date(temp.getFullYear(), temp.getMonth() + 1, 0);
        }
        return date.getDate();
    },

    /**
     * 如果传2个参数则返回某个月1号是星期几，如果什么都不传则默认返回当月1号是周几
     * @param  {[type]} year  [description]
     * @param  {[type]} month [description]
     * @param  {[type]} day   [description]
     * @return {[type]}       [description]
     */
    _getDayOfWeek: function(year, month, day) {

        if (arguments.length == 3) {

            return new Date(year, month, day).getDay();

        } else if (arguments.length == 2) {

            return new Date(year, month, 1).getDay();

        } else {
            var temp = new Date();

            return new Date(temp.getFullYear(), temp.getMonth(), 1).getDay();

        }
    },

    _parseDate: function(str) {

        var str = str.split('-');
        return new Date(str[0], parseInt(str[1], 10) - 1, 1);
    }
};