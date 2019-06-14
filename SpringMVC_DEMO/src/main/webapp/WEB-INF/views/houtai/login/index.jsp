<%@page contentType="text/html; utf-8;" pageEncoding="utf-8" %>
<div id="pages">
    <div class="section section1">
        <div class="logo">IM</div>
        <%--云朵--%>
        <div class="cloud-group">
            <img class="cloud1" src="${pageContext.request.contextPath}/css/apps/${ccsLocation}/img/cloud1.png"/>
            <img class="cloud2" src="${contextPath}/css/apps/${ccsLocation}/img/cloud2.png"/>
            <img class="cloud3" src="${contextPath}/css/apps/${ccsLocation}/img/cloud3.png"/>
            <img class="cloud4" src="${contextPath}/css/apps/${ccsLocation}/img/cloud4.png"/>
        </div>
        <div class="mingyan-box" style="WRITING-MODE: vertical-rl; TEXT-ALIGN: left;">
            <div id="mingyan-inner" style="LINE-HEIGHT: 1.5em; LETTER-SPACING: 0.2em;">
            </div>
        </div>
        <div class="bg-top"></div>
        <%--登录表单--%>
        <div class="content login">
            <div class="header">
                <span class="active">账户登录</span><span>二维码登录</span>
            </div>
            <div class="form">
                <form id="login-form" action="javascript:void(0)" method="post">
                    <div class="w100p">
                        <div class="control">
                            <span class="fa-box">
                                <i class="fa fa-user"></i>
                            </span><input name="userName" placeholder="用户名" type="text"/>
                            <i class="fa fa-check fn-hide"></i><i class="fa fa-times fn-hide"></i>
                        </div>
                        <div class="control">
                            <span class="fa-box">
                                <i class="fa fa-lock"></i>
                            </span><input name="userPwd" placeholder="密码" type="password"/>
                            <i class="fa fa-check fn-hide"></i><i class="fa fa-times fn-hide"></i>
                        </div>
                        <div class="control clearfix">
                            <div class="imgCaptcha_text">
                                <span class="fa-box"><i class="fa fa-puzzle-piece"></i></span><input id="checkCode" name="checkCode" placeholder="验证码" type="text" style="width: 70px" data-validation-engine="validate[required]"/>
                            </div>
                            <div class="imgCaptcha_img">
                                <img id="codeImage" width="80" height="30" src="${contextPath}/login/getCheckCodeImage.do" alt="点击换一张" title="点击更换"/>
                            </div>
                            <div class="fl" style="margin-top:9px;">
                                &nbsp;<i class="fa fa-check fn-hide"></i><i class="fa fa-times fn-hide"></i>
                            </div>
                        </div>
                        <div class="control clearfix f12" style="display: block">
                            <a class="fl" href="#"  style="color: black">免费注册</a><a class="fr" href="#"  style="color: black">忘记登录密码?</a>
                        </div>
                        <div class="control">
                            <input class="submit" type="submit" value="登录"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="knife"></div>
        <div class="bg-bottom"></div>
    </div>
    <div class="section section2">
    </div>
</div>