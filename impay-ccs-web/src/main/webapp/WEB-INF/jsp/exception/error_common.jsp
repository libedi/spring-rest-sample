<%--
   Copyright (c) 2013 SK planet.
   All right reserved.
 
   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>IMPay CCS</title>	
	<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
	<link type="text/css" rel="stylesheet" href="${resources}/css/etc.css" />
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<div class="container" style="width:auto;">
<!-- contents -->
		<div id="contents" class="contents">
			
			<div class="error-wrap">
				<strong>
					<em><spring:message code="errorpage.indication.msg1"/></em><br>
					<spring:message code="errorpage.indication.msg2"/>
				</strong>
				<br><br>
				<spring:message code="errorpage.indication.msg3"/><br>
				<spring:message code="errorpage.indication.msg4"/>
				<br><br>
				<spring:message code="errorpage.indication.msg5"/><br>
				<em><spring:message code="errorpage.indication.msg6"/></em><spring:message code="errorpage.indication.msg7"/>
				<br><br>
				<spring:message code="errorpage.indication.msg8"/> <em><spring:message code="errorpage.indication.msg9"/></em><spring:message code="errorpage.indication.msg10"/>
				<br><br>

			</div>
			
		</div>
		<!--// contents -->
	</div>
</body>
</html>
