/**
 * jQuery EasyUI 1.4.4
 *
 * Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the freeware license: http://www.jeasyui.com/licenselinkreeware.php
 * To use it on other terms please contact us: info@jeasyui.com
 *
 * 一些注意事项总结：
 *      1.loadJs和runJs的不同在于：loadJs是将js加载进来，长久的存在。而runJs是将脚本加载进来后执行完，然后就将该js移除。
 *      2.如果引入了easyloader.js，就不用引入jquery.easyui.min.js了。
 *      3.window.using = easyloader.load的加载方式有:(1).加载外部的css或js（2）.加载内部定义的modules中的内容
 */
(function () {
    //定义的模块

    //css模块
    var cssModules = {
        "fontAwesome":{path:"cssPlugin",css:"font-awesome.min.css",description:"Icons plugin:the complete set of 361 icons in Font Awesome 3.2.1"},
        "animate":{path:"cssPlugin",css:"animate.min.css",description:"css3 animate,effect：http://www.jq22.com/jquery-info819"}
    };

    var coreModules = {
        jqueryUI:{path:"core",js:"jquery-ui.js",css:"jquery-ui.css",description:"jqueryUI"},
        bootstrap:{path:"core",js:"js/bootstrap.min.js",css:["bootstrap.min.css"],version:"v3.0.2"},
        flaty:{path:"core",js:"flaty.js",css:["flaty.css","flaty-responsive.css"]}
    };

    //自己创建的一些Modules
    var selfMakeModules = {
        objectUtil:{path:"otherProvidePackage",js:"objectUtils.js",description:"对象创建工具"}
    };

    //其它网站提供的程序包
    var otherWebSiteProvideModules = {
        mustache:{path:"otherProvidePackage",js:"jquery.mustache.js",description:"模板填充插件"},
        fullpage:{path:"otherProvidePackage",js:"jquery.fullpage.js",css:"jquery.fullpage.css",description:"整页滚动插件"},
        moment:{path:"otherProvidePackage",js:"moment.js",description:"JavaScript 日期处理类库",website:"http://momentjs.cn/"},
        fullCalendar:{path:"otherProvidePackage",js:["fullcalendar.js","lang/zh-cn.js"],css:["fullcalendar.css"],dependencies:["moment"],description:"fullcalendar日历插件",website:"http://fullcalendar.io/"},
        jqueryValidation:{path:"otherProvidePackage",js:["jquery.validationEngine-zh_CN.js","jquery.validationEngine.js"],css:"validationEngine.jquery.css",description:"jquery验证插件"},
        bootstrapValidator:{path:"otherProvidePackage",js:"bootstrapValidator.js",css:["bootstrapValidator.css"],dependencies:["bootstrap"],description:"bootstrapValidator验证插件"},
        jquerySlimScroll:{path:"otherProvidePackage",js:"jquery.slimscroll.min.js",description:"一个漂亮的滚动条插件"},
        jquerySparkline:{path:"otherProvidePackage",js:"jquery.sparkline.min.js",description:"jQuery线状图插件Sparkline"},
        jqueryFlot:{path:"otherProvidePackage",js:["jquery.flot.js","jquery.flot.resize.js","jquery.flot.stack.js","jquery.flot.crosshair.js","jquery.flot.pie.js","jquery.flot.tooltip.min.js"],description:"一个图表插件"},
        jqueryCookie:{path:"otherProvidePackage",js:"jquery.cookie.js",description:"一个Cookie操作的插件"},
        qunit:{path:"otherProvidePackage",js:"qunit-1.23.1.js",css:"qunit-1.23.1.css", description:"一个测试框架"}
    };

    //easyui自带的Modules
    var easyUIModulues = {
        draggable: {path:"easyUI",js: "jquery.draggable.js",description:"让元素具有可拖动性"},
        droppable: {path:"easyUI",js: "jquery.droppable.js"},
        resizable: {path:"easyUI",js: "jquery.resizable.js"},
        linkbutton: {path:"easyUI",js: "jquery.linkbutton.js", css: "linkbutton.css"},
        progressbar: {path:"easyUI",js: "jquery.progressbar.js", css: "progressbar.css"},
        tooltip: {path:"easyUI",js: "jquery.tooltip.js", css: "tooltip.css"},
        //js：配置js文件，css：配置css文件，dependencies配置依赖关系  说明：前两项可以配置相对路径
        pagination: {path:"easyUI",js: "jquery.pagination.js", css: "pagination.css", dependencies: ["linkbutton"]},
        datagrid: {path:"easyUI",js: "jquery.datagrid.js",css: "datagrid.css", dependencies: ["panel", "resizable", "linkbutton", "pagination"]},
        treegrid: {path:"easyUI",js: "jquery.treegrid.js", css: "tree.css", dependencies: ["datagrid"]},
        propertygrid: {path:"easyUI",js: "jquery.propertygrid.js", css: "propertygrid.css", dependencies: ["datagrid"]},
        datalist: {path:"easyUI",js: "jquery.datalist.js", css: "datalist.css", dependencies: ["datagrid"]},
        panel: {path:"easyUI",js: "jquery.panel.js", css: "panel.css"},
        window: {path:"easyUI",js: "jquery.window.js", css: "window.css", dependencies: ["resizable", "draggable", "panel"]},
        dialog: {path:"easyUI",js: "jquery.dialog.js", css: "dialog.css", dependencies: ["linkbutton", "window"]},
        messager: {path:"easyUI",js: "jquery.messager.js",css: "messager.css",dependencies: ["linkbutton", "dialog", "progressbar"]},
        layout: {path:"easyUI",js: "jquery.layout.js", css: "layout.css", dependencies: ["resizable", "panel"]},
        form: {path:"easyUI",js: "jquery.form.js"},
        menu: {path:"easyUI",js: "jquery.menu.js", css: "menu.css"},
        tabs: {path:"easyUI",js: "jquery.tabs.js", css: "tabs.css", dependencies: ["panel", "linkbutton"]},
        menubutton: {path:"easyUI",js: "jquery.menubutton.js", css: "menubutton.css", dependencies: ["linkbutton", "menu"]},
        splitbutton: {path:"easyUI",js: "jquery.splitbutton.js", css: "splitbutton.css", dependencies: ["menubutton"]},
        switchbutton: {path:"easyUI",js: "jquery.switchbutton.js", css: "switchbutton.css"},
        accordion: {path:"easyUI",js: "jquery.accordion.js", css: "accordion.css", dependencies: ["panel"]},
        calendar: {path:"easyUI",js: "jquery.calendar.js", css: "calendar.css"},
        textbox: {path:"easyUI",js: "jquery.textbox.js", css: "textbox.css", dependencies: ["validateboxExt", "linkbutton"]},
        filebox: {path:"easyUI",js: "jquery.filebox.js", css: "filebox.css", dependencies: ["textbox"]},
        combo: {path:"easyUI",js: "jquery.combo.js", css: "combo.css", dependencies: ["panel", "textbox"]},
        combobox: {path:"easyUI",js: "jquery.combobox.js", css: "combobox.css", dependencies: ["combo"]},
        combotree: {path:"easyUI",js: "jquery.combotree.js", dependencies: ["combo", "tree"]},
        combogrid: {path:"easyUI",js: "jquery.combogrid.js", dependencies: ["combo", "datagrid"]},
        //easyUI验证插件 参考文档：http://www.tuicool.com/articles/a22Iney
        validatebox: {path:"easyUI",js: "jquery.validatebox.js", css: "validatebox.css", dependencies: ["tooltip"]},
        //validatebox扩展
        validateboxExt: {path:"easyUI",js: "jquery.validateboxExt.js", dependencies: ["validatebox"]},
        numberbox: {path:"easyUI",js: "jquery.numberbox.js", dependencies: ["textbox"]},
        searchbox: {path:"easyUI",js: "jquery.searchbox.js", css: "searchbox.css", dependencies: ["menubutton", "textbox"]},
        spinner: {path:"easyUI",js: "jquery.spinner.js", css: "spinner.css", dependencies: ["textbox"]},
        numberspinner: {path:"easyUI",js: "jquery.numberspinner.js", dependencies: ["spinner", "numberbox"]},
        timespinner: {path:"easyUI",js: "jquery.timespinner.js", dependencies: ["spinner"]},
        tree: {path:"easyUI",js: "jquery.tree.js", css: "tree.css", dependencies: ["draggable", "droppable"]},
        datebox: {path:"easyUI",js: "jquery.datebox.js", css: "datebox.css", dependencies: ["calendar", "combo"]},
        datetimebox: {path:"easyUI",js: "jquery.datetimebox.js", dependencies: ["datebox", "timespinner"]},
        slider: {path:"easyUI",js: "jquery.slider.js", dependencies: ["draggable"]},
        parser: {path:"easyUI",js: "jquery.parser.js"},
        mobile: {path:"easyUI",js: "jquery.mobile.js"},
        "easyUI-fullCalendar": {path:"easyUI",js: "jquery.fullcalendar.js",css:"easyuiFullCalendar.css",dependencies:["calendar"],description:"网络上寻找的一个别人融合了农历和特殊节日的插件，之后自己也会改动内容"}
    };

    var modules = $.extend({},cssModules, coreModules, easyUIModulues, selfMakeModules, otherWebSiteProvideModules);
    //本地化语言包(这里一般用不了这么多，删除一些，然后将locale文件夹下面的对应文件夹删掉。)
    var locales = {
        "en": "easyui-lang-en.js", //英文
        "zh_CN": "easyui-lang-zh_CN.js"
    };
    //加载的module。该变量用来存放加载了的队列，每个module的name作为key，加载状态作为value。
    var queues = {};

    //加载js
    function loadJs(url, callback) {//url加载js的路径     callback加载完成后回调
        var done = false;//是否加载完成
        //该种方式还可以用于JSONP(主流浏览器的跨域数据访问的问题)
        var script = document.createElement("script");//创建一个script标签
        script.type = "text/javascript";//指定scirpt标签的类型
        script.language = "javascript";//指定script标签的语言
        script.src = url;//指定script标签要加载的脚本文件路径
        script.onload = script.onreadystatechange = function () {//加载监听
            //如果  加载没有完成  有readyState  加载状态为loaded   或者  加载状态为complete
            if (!done && (!script.readyState || script.readyState == "loaded" || script.readyState == "complete")) {
                done = true;//加载完成
                script.onload = script.onreadystatechange = null;//加载完成后将script标签的监听处理函数去掉
                if (callback) {//判断是否有回调函数
                    callback.call(script);//如果有回调函数，就执行该回调函数，并且将创建的这个script标签掉用者
                }
            }
        };
        //将创建的这个表情添加进入head中script阵列。
        document.getElementsByTagName("head")[0].appendChild(script);
    };
    //运行js
    function runJs(url, callback) {
        loadJs(url, function () {
            //这里执行完后，就将script这个标签移除掉
            document.getElementsByTagName("head")[0].removeChild(this);//这里的this指的是callback.call(script)中的script
            if (callback) {//判断是否有回调函数
                callback();//如果有回调函数就执行该回调函数
            }
        });
    };
    //加载css
    function loadCss(url, callback) {
        var done = false;//是否加载完成
        var link = document.createElement("link");//创建一个
        link.rel = "stylesheet";//指定link为样式表
        link.type = "text/css";//指定link的类型
        //media 属性规定被链接文档将显示在什么设备上。media 属性用于为不同的媒介类型规定不同的样式。
        //所有浏览器都支持值为 "screen"、"print" 以及 "all" 的 media 属性。
        //提示：在全屏模式中，Opera 也支持 "projection" 属性值。
        //支持的具体情况，详见http://www.w3school.com.cn/tags/att_link_media.asp
        link.media = "screen";//支持为屏幕类型
        link.href = url;//支持层叠样式表的路径
        //监听加载完成的事件
        link.onload = link.onreadystatechange = function (){
            //如果  加载没有完成  有readyState  加载状态为loaded   或者  加载状态为complete
            if (!done && (!link.readyState || link.readyState == "loaded" || link.readyState == "complete")) {
                done = true;//加载完成
                link.onload = link.onreadystatechange = null;//加载完成后将script标签的监听处理函数去掉
                if (callback) {//如果有回调函数
                    callback.call(link);//执行该回调函数
                }
            }
        }
        document.getElementsByTagName("head")[0].appendChild(link);//将该dom添加到head标签当中去
    };
    //加载单个文件
    function loadSingle(name, callback) {
        queues[name] = "loading";//将队列中的这个文件设置为加载状态
        var module = modules[name];//获取到要加载的module
        var jsStatus = module["js"] ? "loading" : "loaded";//js的加载状态(改)
        //如果easyloader配置了加载css，并且模块的css也配置了，那么加载状态为loading,或者不用加载css，状态为加载成功。
        var cssStatus = (easyloader.css && module["css"]) ? "loading" : "loaded";//css的加载状态
        if (easyloader.css && module["css"]) {//如果要加载css，并且配置了css
            if(!(module["css"] instanceof Array)) module["css"] = [module["css"]];
            for(var i in module["css"]){
                //如果css的配置是以http开头的绝对应用路径。
                //！！！！这里可以做一些调整，加一项指定站内其它位置。
                if (/^http/i.test(module["css"][i])) {
                    var url = module["css"][i];
                } else {
                    if(/^easyUI/i.test(module["path"])){//如果是easyui组件有一点不一样，就是easyui定义了不同的主题
                        //easyloader的base目录加上themes目录下对应的模块下面的css。
                        var url = easyloader.base + module["path"]  + "/themes/" + easyloader.theme + "/" + module["css"][i];
                    } else {
                        //否者这里不属于easyUI中的内容
                        var url = easyloader.base + module["path"] +"/" + name + "/themes/" + module["css"][i];
                    }
                }
                //载入css
                loadCss(url, function () {
                    cssStatus = "loaded";//加载完后回调函数，将css加载状态置为加载完成
                    if (jsStatus == "loaded" && cssStatus == "loaded") {//如果js和css都加载完成了的话，就执行finish函数
                        finish();
                    }
                });
            }
        }
        if(module["js"]){//如果检测到有js就加载js
            if(!(module["js"] instanceof Array)) module["js"] = [module["js"]];
            for(var i in module["js"]){
                //检测该js配置是内部配置的还是外部网站的js文件
                if (/^http/i.test(module["js"][i])) {
                    //如果是外部的js，那么js的路径就是配置的路径
                    var url = module["js"][i];
                } else {
                    //如果是内部的js，那么js的路径要进行组装
                    if(/^easyUI/i.test(module["path"])) {//easyui的插件全部在plugins下面
                        var url = easyloader.base + module["path"] + "/plugins/" + module["js"][i];
                    } else {
                        var url = easyloader.base + module["path"] +"/" + name +"/" + module["js"][i];
                    }
                }
                //载入js文件
                loadJs(url, function () {
                    jsStatus = "loaded";//加载成功回调函数，将js的加载状态置为加载完成状态
                    if (jsStatus == "loaded" && cssStatus == "loaded") {//如果js和css都加载完成了的话，就执行finish函数
                        finish();
                    }
                });
            }
        }
        //加载完成单个module的js和css要执行的函数
        function finish() {
            queues[name] = "loaded";//将加载的这个模块置为加载完成，表示该模块的css和js都加载完成了
            easyloader.onProgress(name);//加载后在easyloader中属性定义的onProgress函数
            if (callback) {//如果定义了加载单个文件的回调函数
                callback();//执行回调函数
            }
        };
    };
    //加载模块
    function loadModule(name, callback) {
        var mm = [];//加载队列
        var doLoad = false;
        //如果参数name是一个string类型的，说明该参数加载一个module
        if (typeof name == "string") {
            add(name);
        } else { //这里看出，第一个参数name可以定义成一个数组加载进来多个module
            for (var i = 0; i < name.length; i++) {//遍历该module数组
                add(name[i]);
            }
        }
        function add(name) {
            //检测一下在目前定义的modules中是否定义了name这个module。
            if (!modules[name]) {
                console.warn("在easyloader中没有找到" + name + "这个module.");
                return;
            }
            //获取该模块定义的所有的依赖
            var dependencyModule = modules[name]["dependencies"];
            if (dependencyModule) {     //如果定义依赖的module
                for (var i = 0; i < dependencyModule.length; i++) {
                    //将依赖也加入待加载的队列(这里用得很巧妙),有可能依赖还有依赖，所以这个递归很棒！！
                    add(dependencyModule[i]);
                }
            }
            //将所有的依赖加载完毕之后，最后将自己加入待加载的队列中。
            mm.push(name);
        };
        //加载完成后要调用的执行函数。
        function finish() {
            if (callback) {//如果定义了回调函数
                callback();//执行回调函数
            }
            easyloader.onLoad(name);//调用加载完成后要执行的回调函数
        };

        //该变量用来统计模块加载超时的时间的。
        var time = 0;

        //加载module加载队列中的module
        function loadMm() {
            if (mm.length) {//如果加载队列中还有值
                var m = mm[0];//获取第一个要待加载的module
                if (!queues[m])//如果已加载序列中没有该模块
                {
                    doLoad = true;//加载状态置为true
                    //加载单个module
                    loadSingle(m, function () {//加载完成单个module后的回调函数
                        mm.shift();//移除第一个i
                        loadMm();//加载模块,这里又巧妙的用上了递归(如果没有加载完模块就继续加载)
                    });
                } else {//否则已加载序列中获取到该模块
                    //如果发现该module已经加载，并且已经加载完成了，就不用加载了，直接移除掉（防止重复加载）
                    if (queues[m] == "loaded") {
                        mm.shift();//将该模块从待加载队列中移除掉
                        loadMm();//这里又巧妙的用上了递归
                    } else {
                        if (time < easyloader.timeout) {//如果没有超时，就继续加载
                            time += 10;//已经加载的时间
                            //arguments.callee 调用自身
                            setTimeout(arguments.callee, 10);//过10毫秒再次执行该函数
                        }
                        //否者如果超时了，那么就不加载该module了。
                    }
                }
            } else {//如果加载队列中的值已经加载完了
                //下面是最后加载的内容，用来加载最后的本地化语言文件。
                //如果设置了本地化语言 并且已经加载完成  并且获取到easyloader参数中定义的local。
                if (easyloader.locale && doLoad == true && locales[easyloader.locale]) {
                    //获取本地化语言文件的路径
                    var url = easyloader.base + "easyUI/locale/" + locales[easyloader.locale];
                    //执行该js的。从这里看出，本地化语言文件在加载完成后就会将该文件删除掉。
                    runJs(url, function () {
                        finish();//加载完成
                    });
                } else {
                    finish();//加载完成
                }
            }
        };
        //主动调用加载队列中模块
        loadMm();
    };
    easyloader = {
        modules: modules,  //传入所有定义的modules模块
        locales: locales,  //传入说有定义的locales本地化语言包
        base: ".",         //开始位置,
        theme: "metro", //主题的名称，这里可以换主题
        css: true,         //是否加载css文件
        locale: "zh_CN",      //
        timeout: 2000,     // 加载超时设置
        load: function (name, callback) {
            //判断如果加载的是css
            if (/\.css$/i.test(name)) {
                if (/^http/i.test(name)) {//如果是外部的css
                    loadCss(name, callback);//加载css
                } else {//否则是内部的css
                    //!!!!!载入内部的css，注意这里url的构造，所以使用的时候注意写什么。
                    loadCss(easyloader.base + name, callback);
                }
            } else if (/\.js$/i.test(name)) {//如果是加载的js
                if (/^http/i.test(name)) {//如果是外部的js
                    loadJs(name, callback);//加载js
                } else {//否则是内部的js
                    //!!!!!载入内部的js，注意这里url的构造，所以使用的时候注意写什么。
                    loadJs(easyloader.base + name, callback);//加
                }
            } else {//否者是加载的模块的名称
                loadModule(name, callback);
            }
        },
        //当加载完成一个module就会执行一次该函数。
        onProgress: function (moduleName) {//参数为每一次加载完成的模块名称
        },
        //
        onLoad: function (arg2) {
        }
    };

    //获取到所有script标签
    var scripts = document.getElementsByTagName("script");
    for (var i = 0; i < scripts.length; i++) {//遍历所有的script标签。
        var src = scripts[i].src;//获取到script中src指向的路径
        if (!src) {//如果script标签不是引用的外部的js文件，那么就是定义的那种内部的script，排除，继续循环
            continue;
        }
        var matchResult = src.match(/easyloader\.js(\W|$)/i);//如果它的这个路径匹配到easyloader.js这个文件
        if (matchResult) {//如果匹配到   matchResult.index匹配到的位置
            easyloader.base = src.substring(0, matchResult.index);  //默认就将easyloader.js文件的所在位置作为base的路径
        }
    }

    //为方便使用，将该方法绑定到window对象上，下次就可以执行用using去执行该方法
    window.using = easyloader.load;
    //判断是否已经加载jQuery
    if (window.jQuery) {
        jQuery(function () {//当加载完成就开始执行该匿名回调函数
            //加载解析器
            easyloader.load("parser", function () {//using("parser", function () {
                //全局解析器，加载的时候把所有都解析一遍(这考虑一下是不是需要改动一下)
                jQuery.parser.parse();//开始执行解析
            });
        });
    }
})();

