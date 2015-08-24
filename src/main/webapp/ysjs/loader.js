
$.provide('oss.Loader');

oss.Loader = function() {
    this.param = {};
    this.url = '';
};
oss.Loader.ERRORTEXT = {
    "4xx": "请求错误，点击重新加载",
    "5xx": "服务器错误，点击重新加载",
    "xx": "发生错误，点击重新加载"
};

$.extend(oss.Loader.prototype, {

    render: function(container) {
        this.container_ = container;
    },

    send: function(url, param) {
        this.url = url || this.url;
        this.param = param || this.param;
        this.showLoading();
        $.ajax({
            type: 'get',
            url: this.url,
            data: this.param,
            dataType: "json",
            success: this.handleSuccess,
            error: this.handleError,
            complete: this.handleComplete,
            context: this,
            timeout: 30000
        });
    },

    setUrl: function(url) {
        this.url = url;
    },

    handleSuccess: function(response) {
        if (this.errorElem_) {
            this.errorElem_.hide();
        }
        this.loadingElem_.hide();
        if (response.code == 0) {
            $(this).trigger('renderpage', [response]);
        } else {
            oss.Dialog.alert(response.errmsg);
        }
    },

    handleError: function(xhr) {
        var key = "xx";
        var status = new String(xhr.status);
        if (status && status.length > 0 ) {
            var firstOfStatus = status.substring(0,1);
            if (firstOfStatus == 4 || firstOfStatus == 5) {
                key = firstOfStatus;
            }
        }
        if (!this.errorElem_) {
            this.errorElem_ = $('<div style="width: 100%;text-align: center;color: #25a4bb;height: 50px;line-height: 50px;font-size: 14px"></div>').insertAfter(this.container_);
            this.errorElem_.listen('click', this.handleRefesh, this);
        }
        this.errorElem_.html(oss.Loader.ERRORTEXT[key]);
        this.errorElem_.show();
        this.loadingElem_.hide();
    },

    handleRefesh: function() {
        this.errorElem_.hide();
        this.send();
    },

    handleComplete: function(data) {
    },

    showLoading: function() {
        if (this.loadingElem_) {
            this.loadingElem_.show()
        } else {
            this.loadingElem_ = $('<div class="mpl-loading">\
            <img width="26" style="margin-bottom:-4px;" src="http://source.qunar.com/piao/images/loading_camel.gif"> 加载中...</div>')
            .insertAfter(this.container_);
        }
    }
});