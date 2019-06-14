<%@page contentType="text/html; utf-8;" pageEncoding="utf-8" %>
<style>
    body{
        background-color: #9adca0;
        padding: 0px;
    }
</style>
<script>
    (function($){
        //名人名言
        var MingRenMingYan = {
            data:[],
            displayTime:15000,
            lastCachNum:10,
            isFirstRun:true,
            keywordsManagent:{
                data:["抱负","父爱","爱情","诗经","笑话","节约","奋斗"],
                readerIndex:0,
                init:function(){
                    this.readerIndex = parseInt(Math.random()*this.data.length);
                },
                getKeyword:function(){
                    var that = this;
                    return this.data[(function(){
                        var t  = that.readerIndex;
                        if(that.readerIndex+1 == that.data.length){
                            that.readerIndex = 0;
                        } else {
                            that.readerIndex ++;
                        }
                        return t;
                    })()];
                }
            },
            argumentManagent:{
                dtypeEnum:{
                    XML:"xml",
                    JSON:"json"
                },
                dtype:null,keyword:null,page:1,rows:20,total:null,
                init:function(dtype,keyword,page,rows){
                    this.dtype = dtype,
                    this.keyword = keyword,
                    this.page = page;
                    this.rows = rows;
                },
                getArgs:function(){
                    return "dtype="+this.dtype+"&keyword="+encodeURI(this.keyword)+"&page="+this.page+"&rows="+ this.rows;
                }
            },
            init:function(){
                var that = this,go = function(){
                    that.run.apply(that);
                };
                this.keywordsManagent.init();
                this.argumentManagent.init(
                        this.argumentManagent.dtypeEnum.JSON,//格式
                        this.keywordsManagent.getKeyword(),//关键字
                        1,//页数
                        20//行数
                );
                setInterval(go,this.displayTime);//定时执行
                go();//立即执行第一次
            },
            run:function(){
                var that = this,
                    renderFun = function(){
                        if(that.data.length > 0){ //如果堆栈中有元素那么就弹出渲染
                            that.renderContent(that.data.pop());
                            console.info("队列中还剩："+that.data.length);
                        }
                    };
                if(that.data.length <= that.lastCachNum){
                    this.getDataList(function(data){
                        if(data && data.reason == "Succes"){
                            //记录下总数
                            that.argumentManagent.total = data.total;
                            for(var i=0;i<data.result.length;i++){
                                that.data.unshift(data.result[i]);//加入队列
                            }
                            //改变下次去获取数据的参数
                            if(that.argumentManagent.page * that.argumentManagent.rows < that.argumentManagent.total){
                                that.argumentManagent.page ++;
                            } else {
                                that.argumentManagent.keyword = that.keywordsManagent.getKeyword();
                                that.argumentManagent.page = 1;
                            }
                            if(that.isFirstRun){//如果是第一次运行，加载完数据后要立刻渲染一次
                                that.isFirstRun = false;
                                renderFun();
                            }
                        }
                    });
                }
                renderFun();
            },
            //渲染内容
            renderContent:function(data){
                var mingyan = data.famous_saying + "——" + data.famous_name;
                $("#mingyan-inner").html(mingyan);
            },
            getDataList:function(callback){
                var that = this;
                $.getJSON("${contextPath}/badu/api/getService.do",
                    {
                        apiName: "API_MINGRENMINGYAN",
                        args: that.argumentManagent.getArgs()
                    }, function(json){
                        callback && callback(json && $.parseJSON(json));//遇到的问题，这里json返回了一个带有“的json转，这里必须借助jquery转一下。
                    }
                );
            }
        };

        //登录模块
        var loginModule = {
            init:function(){
                this.pluginInit();
                this.bindEvent();
            },
            pluginInit:function(){
            },
            bindEvent:function(){
                var scope = this;
                //验证码图片点击重新加载验证码
                $("#codeImage").click(function(){
                    $(this).attr("src","${contextPath}/login/getCheckCodeImage.do?date="+new Date().getTime());
                });
                //登录
                $("#login-form").submit(function(event){
                    //验证是否符合基本标准数据
                    if(scope.formValidate()){
                        //验证表单数据之后，提交登录请求到后台
                        $.ajax({
                            type: "POST",
                            url: "${contextPath}/login/login.do",
                            data: $(event.target).serialize(),
                            success: function(result){
                                console.dir(result);
                            }
                        });
                    }
                    event.preventDefault();
                    return false;
                });
            },
            //验证表单参数
            formValidate:function(){
                var resultArr = [];
                var $form = $("#login-form"),
                    userName = $form.find("input[name=userName]").val().trim(),
                    pwd = $form.find("input[name=password]").val().trim();
                if(!userName || !pwd){
                    resultArr.push("用户名或密码不能为空！");
                } else{
                    //登录不用验证长度，在新建或注册账户的时候要注意这个问题
//                    if(userName.length >=3 && userName.length < 15){
//                        resultArr.push("用户名长度必须在3到15之间");
//                    }
//                    if(userName.length >=6 && userName.length < 20){
//                        resultArr.push("密码长度必须在6到20之间");
//                    }
                }
                if(!this.validCheckCode($("#checkCode").val())){
                    resultArr.push("验证码错误！");
                }
                if(resultArr.length){
                    this.popInfo(resultArr);
                    return false;//验证失败
                }
                return true;//验证通过
            },
            validCheckCode:function(checkCode){
                var bool = false,msg="";
                $.ajax({
                    type: "POST",
                    url: "${contextPath}/login/isValidcheckCode.do",
                    async:false, //同步执行
                    data: {
                        checkCode : checkCode
                    },
                    success: function(result){
                        if(result["code"]){
                            switch (result["code"]){
                                case 'failure':
                                    bool = false;
                                    msg = result["msg"];
                                    break;
                            }
                        } else {
                            bool = true;
                        }
                    }
                });
                return bool;
            },
            //从上方向下划出验证的小心
            popInfo:function(infoArr){
                alert(JSON.stringify(infoArr));
            }
        }

        //滚动页面控制
        var PageControl = {
            opts:{
                verticalCentered:true,    //默认内容是垂直居中的
                resize:false, //字体是否随着窗口缩放而缩放
                sectionsColor: ['#FFF', '#FFF', '#339'],
                anchors: ['page1', 'page2'],
                scrollingSpeed:2500, //滚动的速度
                easing:"easeInQuart", //滚动动画方式
                navigation:false
                //menu:'#menu'
            },
            init:function(){
                this.pluginBuild();
            },
            pluginBuild:function(){
                $("#pages").fullpage(this.opts);//参数详情参考http://www.dowebok.com/77.html
            }
        };

        //整个页面一个大的对象
        var work = {
            init:function(){
                //滚动页面控制
                PageControl.init();
                //名人名言
                MingRenMingYan.init();
                //登录控制
                loginModule.init();
                this.bindEvent();
            },
            bindEvent:function(){

                //由于fullpage对于动画的云识别问题，导致下面有一个白条，需刷新触发一次窗口大小改变事件。
//                $(window).trigger("resize");//在之前下面出现白条的一种不合适的解决办法。

                //取消掉这个页面的右键菜单
//                $(document).on("contextmenu",function(data){
//                    //data.target可以获取到当前点击的是哪一个元素上
//                    console.info(data.target);
//                    return false;
//                });
                //禁用掉网页上所有的图片拖动操作
                for(var i in document.images) document.images[i].ondragstart = function(){return false};
            }
        };

        $(function(){
            //todo：使用bootstrapValidator出现了无限递归问题。
            using(["bootstrap","fontAwesome","fullpage"], function(){
                work.init();
            });
        });
    })(window.jQuery);
</script>