<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html lang="zh-CN">
<head>
  <tles:insertAttribute name="flatyHead" ignore="true"></tles:insertAttribute>
  <tles:insertAttribute name="flatyHeadJS" ignore="true"></tles:insertAttribute>
</head>
<body class="h100p">
  <%@ include file="flatyBody.jsp"%>
  <%@ include file="flatyBody_js.jsp"%>
</body>
</html>