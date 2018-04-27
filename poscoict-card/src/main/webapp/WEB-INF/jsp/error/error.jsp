<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
	<title>error</title>
	<c:set var="contextPath" 	value="${pageContext.request.contextPath}"/>
	<c:set var="resources"		value="${contextPath}/resources"/>
	<link rel="stylesheet" href="${resources}/css/workplace.min.css">
</head>

<body>
<div data-role="page" class="error-page">
    <header>
        <div class="ci"></div>
    </header>
    <main>
        <h1>${title}</h1>
        <h2>${description}<br/><br/>${footer}</h2>
        <a href="${homeUrl}" class="btn-home"><spring:message code="error.text.go-home"/></a>
    </main>
</div>
</body>
</html>
