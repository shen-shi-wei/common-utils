// 为每个ajax添加请求头 token
$(function () {
    function getToken() {
        var strcookie = document.cookie;//获取cookie字符串
        var arrcookie = strcookie.split("; ");//分割
        //遍历匹配
        for (var i = 0; i < arrcookie.length; i++) {
            var arr = arrcookie[i].split("=");
            if (arr[0] == "authentication") {
                return arr[1];
            }
        }
        return "";
    }

    //首先备份下jquery的ajax方法  
    if (!$.ajax) {
        return;
    }
    var _ajax = $.ajax;

    //重写jquery的ajax方法
    $.ajax = function (opt) {
        //备份opt中error和success方法 
        var fn = {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            success: function (data, textStatus) {
            }
        }
        if (opt.error) {
            fn.error = opt.error;
        }
        if (opt.success) {
            fn.success = opt.success;
        }

        //扩展增强处理 
        var _opt = $.extend(opt, {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //错误方法增强处理 
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success: function (data, textStatus) {
                //成功回调方法增强处理  
                fn.success(data, textStatus);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('authentication', getToken());
            }
        });
        return _ajax(_opt);
    };
})