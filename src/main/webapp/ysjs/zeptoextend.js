
$.inherits = function(childCtor, parentCtor) {
    function tempCtor() {};
    tempCtor.prototype = parentCtor.prototype;
    childCtor.superClass_ = parentCtor.prototype;
    childCtor.prototype = new tempCtor();
    childCtor.prototype.constructor = childCtor;
};

$.provide = function(name) {
    var parts = name.split('.');
    var cur = window;
    for (var part; parts.length && (part = parts.shift());) {
        if (cur[part]) {
            cur = cur[part];
        } else {
            cur = cur[part] = {};
        }
    }
};

$.inArray = function(elem, array, i) {
    var len;

    if (array) {
        if (Array.prototype.indexOf) {
            return Array.prototype.indexOf.call(array, elem, i);
        }

        len = array.length;
        i = i ? i < 0 ? Math.max(0, len + i) : i : 0;

        for (; i < len; i++) {
            // Skip accessing in sparse arrays
            if (i in array && array[i] === elem) {
                return i;
            }
        }
    }

    return -1;
};

$.listen = function(src, type, fn, opt_handler) {
    if(opt_handler) {
        fn = $.proxy(fn, opt_handler);
    }
    $(src).on(type, fn);
};
$.fn.listen = function(type, fn, opt_handler, opt_data) {
    if(opt_handler) {
        fn = $.proxy(fn, opt_handler);
    }
    this.on(type, fn, opt_handler);
    return this;
};

$.unlisten = function(src, type, fn) {
    $(src).off(type, fn);
};

$.fn.unlisten = function(type, fn, opt_handler) {
    if(opt_handler) {
        fn = $.proxy(fn, opt_handler);
    }
    this.off(type, fn, opt_handler);
};

$.isNumber = function(obj) {
    return !$.isArray( obj ) && (obj - parseFloat( obj ) + 1) >= 0;
};
$.isObject = function(val) {
    var type = typeof val;
    return type == 'object' && val != null || type == 'function';
};
$.isString = function(val) {
    return $.type(val) == "string";
};

