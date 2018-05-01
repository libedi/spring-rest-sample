<%--
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"            %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"             %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"       %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"          %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form"     %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
<script type="text/javascript">
var contextPath = "${contextPath}";
var resources = "${resources}";
</script>

<link type="text/css" rel="stylesheet" href="${resources}/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="${resources}/css/jquery-ui-timepicker-addon.min.css" />
<link type="text/css" rel="stylesheet" href="${resources}/css/common.css" />
<link type="text/css" rel="stylesheet" href="${resources}/css/layout.css" />
<link type="text/css" rel="stylesheet" href="${resources}/css/contents.css" />
<script type="text/javascript" src="${resources}/js/libs/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resources}/js/libs/tmpl.min.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery-ui.min.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery-migrate-1.1.1.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery.ui.ympicker.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery-datepicker.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery.impay-1.0.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery.form.min.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery.placeholder.ls.js"></script>
<script type="text/javascript" src="${resources}/js/libs/jquery.fileDownload.js"></script>
<script type="text/javascript" src="${resources}/js/common/impay.js"></script>
<script type="text/javascript" src="${resources}/js/common/common.js"></script>
<script type="text/javascript" src="${resources}/js/common/layer.js"></script>
<script type="text/javascript" src="${resources}/js/common/utils.js"></script>