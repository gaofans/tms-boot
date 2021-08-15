/** EasyWeb iframe v3.1.7 date:2020-03-11 License By http://easyweb.vip */

// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
    version: '317',
    base: getProjectUrl() + 'assets/common/module/'
}).extend({
    jf: 'jquery-form/jquery.form',
    sock: 'sockjs/sockjs',
    stomp: 'stompjs/stomp.umd',
    steps: 'steps/steps',
    notice: 'notice/notice',
    cascader: 'cascader/cascader',
    dropdown: 'dropdown/dropdown',
    fileChoose: 'fileChoose/fileChoose',
    treeTable: 'treeTable/treeTable',
    Split: 'Split/Split',
    Cropper: 'Cropper/Cropper',
    tagsInput: 'tagsInput/tagsInput',
    citypicker: 'city-picker/city-picker',
    introJs: 'introJs/introJs',
    zTree: 'zTree/zTree',
    selectPlus: '../../expand/module/selectPlus/selectPlus',
    ax: '../../expand/module/ax/ax',
    ztree: '../../expand/module/ztree/ztree-object',
    func: '../../expand/module/func/func',
    iconPicker: '../../expand/module/iconPicker/iconPicker'
}).use(['layer', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var admin = layui.admin;

    // 移除loading动画
    setTimeout(function () {
        admin.removeLoading();
    }, window === top ? 300 : 0);
    //注册session超时的操作
    $.ajaxSetup({
        method:'post',
        dataType:"json",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        beforeSend: function(){

            //是否需要显示遮罩层
            var load = admin.getTempData(CommonTool.LOAD);
            if(load){
                //开启遮罩层并移除这个标记
                admin.putTempData(CommonTool.LOAD);
                CommonTool.loading();
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
            //尝试关闭遮罩层
            CommonTool.closeLoading();
            //通过XMLHttpRequest取得响应头，sessionstatus，
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            if (sessionstatus === "timeout") {
                //如果超时就处理 ，指定要跳转的页面
                window.top.location.reload();
            }
        }
    });
});

// 获取当前项目的根路径，通过获取layui.js全路径截取assets之前的地址
function getProjectUrl() {
    var layuiDir = layui.cache.dir;
    if (!layuiDir) {
        var js = document.scripts, last = js.length - 1, src;
        for (var i = last; i > 0; i--) {
            if (js[i].readyState === 'interactive') {
                src = js[i].src;
                break;
            }
        }
        var jsPath = src || js[last].src;
        layuiDir = jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
    }
    return layuiDir.substring(0, layuiDir.indexOf('assets'));
}
var CommonTool = new Object();
CommonTool.FORM_OK = "formOk";
CommonTool.LOAD = "load";
CommonTool.info = function (info) {
    top.layer.msg(info, {icon: 6});
};
CommonTool.success = function (info) {
    top.layer.msg(info, {icon: 1});
};
CommonTool.error = function (info) {
    top.layer.msg(info, {icon: 2});
};
CommonTool.ajaxError = function(result,msg){
    if(result){
        if(result.responseJSON && result.responseJSON.message){
            CommonTool.error(msg + result.responseJSON.message);
            return;
        }else if(result.responseText && result.responseText.message){
            CommonTool.error(msg + result.responseText.message);
            return;
        }
    }
    CommonTool.error(msg);
}
CommonTool.confirm = function (tip, ensure) {
    top.layer.confirm(tip, {
        skin: 'layui-layer-admin'
    }, function () {
        ensure();
    });
};
CommonTool.tips = function(info,target,color,postion){
    layer.tips(info, "#"+target, {
        tips: [postion, color],
        time: 4000,
        tipsMore: true
    });
},
CommonTool.infoTip = function(info,target){
    CommonTool.tips(info,target,"white",3);
},
CommonTool.successTip = function(info,target){
    CommonTool.tips(info,target,"green",3);
},
CommonTool.errorTip = function(info,target){
    CommonTool.tips(info,target,"red",3);
},
CommonTool.warningTip = function(info,target){
    CommonTool.tips(info,target,"yellow",3);
},
CommonTool.currentDate = function () {
    // 获取当前日期
    var date = new Date();
    // 获取当前月份
    var nowMonth = date.getMonth() + 1;
    // 获取当前是几号
    var strDate = date.getDate();
    // 添加分隔符“-”
    var seperator = "-";
    // 对月份进行处理，1-9月在前面添加一个“0”
    if (nowMonth >= 1 && nowMonth <= 9) {
        nowMonth = "0" + nowMonth;
    }
    // 对月份进行处理，1-9号在前面添加一个“0”
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    // 最后拼接字符串，得到一个格式为(yyyy-MM-dd)的日期
    return date.getFullYear() + seperator + nowMonth + seperator + strDate;
};
CommonTool.getUrlParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    } else {
        return null;
    }
};
CommonTool.infoDetail = function (title, info) {
    var display = "";
    if (typeof info === "string") {
        display = info;
    } else {
        if (info instanceof Array) {
            for (var x in info) {
                display = display + info[x] + "<br/>";
            }
        } else {
            display = info;
        }
    }
    top.layer.open({
        title: title,
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['950px', '600px'], //宽高
        content: '<div style="padding: 20px;">' + display + '</div>'
    });
};
CommonTool.zTreeCheckedNodes = function (zTreeId) {
    var zTree = $.fn.zTree.getZTreeObj(zTreeId);
    var nodes = zTree.getCheckedNodes();
    var ids = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        ids += "," + nodes[i].id;
    }
    return ids.substring(1);
};
/**
 * 全局的加载层
 */
var _common_load_index;
CommonTool.loading = function(){
    _common_load_index = top.layer.msg('请稍候', {
        icon: 16
        ,shade: 0.01
        ,time:0
    });
}
CommonTool.closeLoading = function () {
    try {
        top.layer.close(_common_load_index);
    }catch (e) {}
};