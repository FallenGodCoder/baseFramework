<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<!--漂浮于右边的主题设置按钮-->
<div id="theme-setting">
    <a href="#"><i class="fa fa-gears fa fa-2x"></i></a>
    <ul>
        <li>
            <span>Skin</span>
            <ul class="colors" data-target="body" data-prefix="skin-">
                <li class="active">
                    <a class="blue" href="#"></a>
                </li>
                <li>
                    <a class="red" href="#"></a>
                </li>
                <li>
                    <a class="green" href="#"></a>
                </li>
                <li>
                    <a class="orange" href="#"></a>
                </li>
                <li>
                    <a class="yellow" href="#"></a>
                </li>
                <li>
                    <a class="pink" href="#"></a>
                </li>
                <li>
                    <a class="magenta" href="#"></a>
                </li>
                <li>
                    <a class="gray" href="#"></a>
                </li>
                <li>
                    <a class="black" href="#"></a>
                </li>
            </ul>
        </li>
        <li>
            <span>Navbar</span>
            <ul class="colors" data-target="#navbar" data-prefix="navbar-">
                <li class="active">
                    <a class="blue" href="#"></a>
                </li>
                <li>
                    <a class="red" href="#"></a>
                </li>
                <li>
                    <a class="green" href="#"></a>
                </li>
                <li>
                    <a class="orange" href="#"></a>
                </li>
                <li>
                    <a class="yellow" href="#"></a>
                </li>
                <li>
                    <a class="pink" href="#"></a>
                </li>
                <li>
                    <a class="magenta" href="#"></a>
                </li>
                <li>
                    <a class="gray" href="#"></a>
                </li>
                <li>
                    <a class="black" href="#"></a>
                </li>
            </ul>
        </li>
        <li>
            <span>Sidebar</span>
            <ul class="colors" data-target="#main-container" data-prefix="sidebar-">
                <li class="active">
                    <a class="blue" href="#"></a>
                </li>
                <li>
                    <a class="red" href="#"></a>
                </li>
                <li>
                    <a class="green" href="#"></a>
                </li>
                <li>
                    <a class="orange" href="#"></a>
                </li>
                <li>
                    <a class="yellow" href="#"></a>
                </li>
                <li>
                    <a class="pink" href="#"></a>
                </li>
                <li>
                    <a class="magenta" href="#"></a>
                </li>
                <li>
                    <a class="gray" href="#"></a>
                </li>
                <li>
                    <a class="black" href="#"></a>
                </li>
            </ul>
        </li>
        <li>
            <span></span>
            <a data-target="navbar" href="#"><i class="fa fa-square-o"></i> Fixed Navbar</a>
            <a class="hidden-inline-xs" data-target="sidebar" href="#"><i class="fa fa-square-o"></i> Fixed Sidebar</a>
        </li>
    </ul>
</div>
<!--顶部的一栏-->
<div id="navbar" class="navbar">
    <button type="button" class="navbar-toggle navbar-btn collapsed" data-toggle="collapse" data-target="#sidebar">
        <span class="fa fa-bars"></span>
    </button>
    <!--顶部左侧的产品名称-->
    <a class="navbar-brand" href="#">
        <small><i class="fa fa-desktop"></i>&nbsp;IM Admin</small>
    </a>
    <!--顶部右侧的通知信息和用户头像相关信息-->
    <ul class="nav flaty-nav pull-right">
        <li class="hidden-xs">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <i class="fa fa-tasks"></i>
                <span class="badge badge-warning">4</span>
            </a>
            <ul class="dropdown-navbar dropdown-menu">
                <li class="nav-header">
                    <i class="fa fa-check"></i> 4 Tasks to complete
                </li>
                <li>
                    <a href="#">
                        <div class="clearfix">
                            <span class="pull-left">Software Update</span>
                            <span class="pull-right">75%</span>
                        </div>
                        <div class="progress progress-mini">
                            <div style="width:75%" class="progress-bar progress-bar-warning"></div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <div class="clearfix">
                            <span class="pull-left">Transfer To New Server</span>
                            <span class="pull-right">45%</span>
                        </div>
                        <div class="progress progress-mini">
                            <div style="width:45%" class="progress-bar progress-bar-danger"></div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <div class="clearfix">
                            <span class="pull-left">Bug Fixes</span>
                            <span class="pull-right">20%</span>
                        </div>
                        <div class="progress progress-mini">
                            <div style="width:20%" class="progress-bar"></div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <div class="clearfix">
                            <span class="pull-left">Writing Documentation</span>
                            <span class="pull-right">85%</span>
                        </div>
                        <div class="progress progress-mini progress-striped active">
                            <div style="width:85%" class="progress-bar progress-bar-success"></div>
                        </div>
                    </a>
                </li>
                <li class="more">
                    <a href="#">See tasks with details</a>
                </li>
            </ul>
        </li>
        <li class="hidden-xs">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <i class="fa fa-bell"></i>
                <span class="badge badge-important">5</span>
            </a>
            <ul class="dropdown-navbar dropdown-menu">
                <li class="nav-header">
                    <i class="fa fa-warning"></i> 5 Notifications
                </li>
                <li class="notify">
                    <a href="#">
                        <i class="fa fa-comment orange"></i>

                        <p>New Comments</p>
                        <span class="badge badge-warning">4</span>
                    </a>
                </li>
                <li class="notify">
                    <a href="#">
                        <i class="fa fa-twitter blue"></i>

                        <p>New Twitter followers</p>
                        <span class="badge badge-info">7</span>
                    </a>
                </li>
                <li class="notify">
                    <a href="#">
                        <img src="/img/demo/avatar/avatar2.jpg" alt="Alex"/>

                        <p>David would like to become moderator.</p>
                    </a>
                </li>
                <li class="notify">
                    <a href="#">
                        <i class="fa fa-bug pink"></i>

                        <p>New bug in program!</p>
                    </a>
                </li>
                <li class="notify">
                    <a href="#">
                        <i class="fa fa-shopping-cart green"></i>

                        <p>You have some new orders</p>
                        <span class="badge badge-success">+10</span>
                    </a>
                </li>
                <li class="more">
                    <a href="#">See all notifications</a>
                </li>
            </ul>
        </li>
        <li class="hidden-xs">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <i class="fa fa-envelope"></i>
                <span class="badge badge-success">3</span>
            </a>
            <ul class="dropdown-navbar dropdown-menu">
                <li class="nav-header">
                    <i class="fa fa-comments"></i> 3 Messages
                </li>
                <li class="msg">
                    <a href="#">
                        <img src="/img/demo/avatar/avatar3.jpg" alt="Sarah's Avatar"/>

                        <div>
                            <span class="msg-title">Sarah</span>
									<span class="msg-time">
<i class="fa fa-clock-o"></i>
<span>a moment ago</span>
									</span>
                        </div>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                            labore et dolore magna aliqua.</p>
                    </a>
                </li>
                <li class="msg">
                    <a href="#">
                        <img src="/img/demo/avatar/avatar4.jpg" alt="Emma's Avatar"/>

                        <div>
                            <span class="msg-title">Emma</span>
									<span class="msg-time">
<i class="fa fa-clock-o"></i>
<span>2 Days ago</span>
									</span>
                        </div>
                        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ...</p>
                    </a>
                </li>
                <li class="msg">
                    <a href="#">
                        <img src="/img/demo/avatar/avatar5.jpg" alt="John's Avatar"/>

                        <div>
                            <span class="msg-title">John</span>
									<span class="msg-time">
<i class="fa fa-clock-o"></i>
<span>8:24 PM</span>
									</span>
                        </div>
                        <p>Duis aute irure dolor in reprehenderit in ...</p>
                    </a>
                </li>
                <li class="more">
                    <a href="#">See all messages</a>
                </li>
            </ul>
        </li>
        <li class="user-profile">
            <a data-toggle="dropdown" href="#" class="user-menu dropdown-toggle">
                <img class="nav-user-photo" src="/img/demo/avatar/avatar1.jpg" alt="Penny's Photo"/>
						<span class="hhh" id="user_info">
Penny
</span>
                <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-navbar" id="user_menu">
                <li class="nav-header">
                    <i class="fa fa-clock-o"></i> Logined From 20:45
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-cog"></i> Account Settings
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-user"></i> Edit Profile
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-question"></i> Help
                    </a>
                </li>
                <li class="divider visible-xs"></li>
                <li class="visible-xs">
                    <a href="#">
                        <i class="fa fa-tasks"></i> Tasks
                        <span class="badge badge-warning">4</span>
                    </a>
                </li>
                <li class="visible-xs">
                    <a href="#">
                        <i class="fa fa-bell"></i> Notifications
                        <span class="badge badge-important">8</span>
                    </a>
                </li>
                <li class="visible-xs">
                    <a href="#">
                        <i class="fa fa-envelope"></i> Messages
                        <span class="badge badge-success">5</span>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <i class="fa fa-off"></i> Logout
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</div>
<!--顶部标题栏以下的正文内容-->
<div class="container h100p" id="main-container">
    <!--左侧的面板-->
    <div id="sidebar" class="navbar-collapse collapse">
        <ul class="nav nav-list">
            <li>
                <form target="#" method="GET" class="search-form">
							<span class="search-pan">
							<button type="submit">
                                <i class="fa fa-search"></i>
                            </button>
								<input type="text" name="search" placeholder="Search ..." autocomplete="off"/>
							</span>
                </form>
            </li>
            <li class="active">
                <a href="${contextPath}/im/flatyMain.do">
                    <i class="fa fa-dashboard"></i>
                    <span>首页</span>
                </a>
            </li>
            <li>
                <a href="#" class="dropdown-toggle">
                    <i class="fa fa-desktop"></i>
                    <span>基本工具</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <ul class="submenu">
                    <li><a href="ui_general.html">网上冲浪(搜索引擎和分类书签)</a></li>
                    <li><a href="ui_general.html">账号管理</a></li>
                    <li><a href="ui_general.html" title="（物品介绍，物品价格，有什么趋势等）">市场产品价格采集</a></li>
                    <li><a href="ui_general.html">生日管理</a></li>
                    <li><a href="ui_general.html">人际关系链图</a></li>
                    <li><a href="ui_button.html" title="(常见格式导入导出)">通讯录</a></li>
                    <li><a href="ui_tabs.html">便签</a></li>
                    <li><a href="ui_tabs.html">时光树（记录人生大事件）</a></li>
                </ul>
            </li>
            <li>
                <a href="#" class="dropdown-toggle">
                    <i class="fa fa-edit"></i>
                    <span>财务管理</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <ul class="submenu">
                    <li><a href="ui_tabs.html">账单管理</a></li>
                    <li><a href="ui_tabs.html">收入记录</a></li>
                    <li><a href="ui_tabs.html">支出记录</a></li>
                </ul>
            </li>
            <li>
                <a href="#" class="dropdown-toggle">
                    <i class="fa fa-edit"></i>
                    <span>学习管理</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <ul class="submenu">
                    <li><a href="ui_tabs.html">知识标签管理</a></li>
                    <li><a href="ui_tabs.html">书籍管理工具</a></li>
                    <li><a href="ui_tabs.html" title="(基础实现原理：发散标签网络+有到云笔记树两种知识管理模式。)">学习笔记</a></li>
                    <li><a href="ui_tabs.html" title="(知识总结：使用百度脑图那种来构建不同分类的脑图（百度脑图好像是使用的一个开源脑图上改的）)">知识链图</a></li>
                </ul>
            </li>
            <li>
                <a href="#" class="dropdown-toggle">
                    <i class="fa fa-edit"></i>
                    <span>工作管理</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <ul class="submenu">
                    <li><a href="ui_tabs.html">项目管理</a></li>
                    <li><a href="ui_tabs.html">日志管理</a></li>
                    <li><a href="ui_tabs.html">代办项</a></li>
                    <li><a href="ui_tabs.html">出差记录</a></li>
                    <li><a href="ui_tabs.html">简历管理</a></li>
                </ul>
            </li>
            <li>
                <a href="#" class="dropdown-toggle">
                    <i class="fa fa-edit"></i>
                    <span>系统管理</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <ul class="submenu">
                    <li><a href="ui_tabs.html">用户管理</a></li>
                    <li><a href="ui_tabs.html">角色管理(模块通过角色划分,如果要运营，按角色划分的功能收费)</a></li>
                    <li><a href="ui_tabs.html">百度统计</a></li>
                    <li><a href="ui_tabs.html">服务器日志</a></li>
                    <li><a href="ui_tabs.html">系统重大事件日志追踪</a></li>
                    <li><a href="ui_tabs.html">通知管理</a></li>
                </ul>
            </li>
        </ul>
        <div id="sidebar-collapse" class="visible-lg">
            <i class="fa fa-angle-double-left"></i>
        </div>
    </div>
    <!--右侧的正文内容-->
    <div id="main-content" class="h100p">
        <tles:insertAttribute name="body" ignore="true"></tles:insertAttribute>
        <footer>
            <p> IM 信息管理工具&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <p> ----------create by tangcheng</p>
        </footer>
        <a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i class="fa fa-chevron-up"></i></a>
    </div>
</div>
<tles:insertAttribute name="js" ignore="true"></tles:insertAttribute>