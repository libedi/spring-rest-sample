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
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />	
	<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
	<script type="text/javascript">
	$(function(){
		$("#frm").submit();
	});
	</script>
</head>
<body>
	<form id="frm" method="post" action="${contextPath}/userMng/view">
		<input type="hidden" name="mnuId" value="CUM000">
		<input type="hidden" name="uprMnuId" value="CUM001">
	</form>
</body>
</html>