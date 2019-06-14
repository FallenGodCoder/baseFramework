<%@page contentType="text/html; utf-8;" pageEncoding="utf-8" %>
<%@ taglib prefix="tle" uri="http://www.IM.com/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IM系统，为个人信息管理提供了完美的解决方案" name="Description">
<meta content="IM系统，为个人信息管理提供了完美的解决方案" name="Keywords">
<meta content="App" name="msApplication-ID">
<title>IM系统</title>
<tle:js url="/js/plugins/core/jQuery/jquery-3.2.1.js"/>
<tle:js url="/js/plugins/easyloader.js"/>
<tle:css url="/trunk.css"></tle:css>
<c:if test="${not empty ccsLocation}">
    <tle:css url="/apps/${ccsLocation}/project.css"/>
</c:if>
