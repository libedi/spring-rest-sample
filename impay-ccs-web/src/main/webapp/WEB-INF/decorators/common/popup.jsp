<%--
   Copyright (c) 2013 SK planet.
   All right reserved.
 
   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ page session="false"    %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"            %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"             %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"       %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"          %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form"     %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page"      uri="http://www.opensymphony.com/sitemesh/page"     %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>IMPay CCS <decorator:title /></title>
    
    <%@ include file="/WEB-INF/jsp/common/include.jsp"%>
    <link type="text/css" rel="stylesheet" href="${resources}/css/popup.css" />

    <decorator:head />
</head>
<body>
    <sec:authentication var="user" property="principal" />
    <decorator:body />

<div id="loadingbar" class="fixed-layer ly-preloader" style="display:none">
	<div class="bg-opacity"></div>
	<div id="ly-wrap" class="ly-wrap"><img alt="preloader" src="${resources}/images/preloader.gif" /></div>
</div>
</body>
</html>