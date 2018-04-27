<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <%@ include file="/WEB-INF/jsp/common/include.jsp" %>
	<title><spring:message code="title.card.select"/></title>
</head>

<body>
<div id="selectCard" data-role="page">
    <!--header-->
    <header data-role="header" class="page-header">
        <div class="main-top-btn-section">
            <a href="#" class="btn-icon-back" data-rel="back" data-direction="reverse">back</a>
            <h1><spring:message code="title.card.select"/></h1>
        </div>
    </header>
    <!--/header-->
    <!--main-->
    <main>
        <ul class="select-box select-card">
        	<li>
			    <a href="#" data-rel="back">
			        <h1><spring:message code="common.all"/></h1>
			        <input type="hidden" name="selectCard" value="ALL">
			    </a>
			</li>
        	<c:forEach var="card" items="${cardList}" varStatus="status">
        		<li>
				    <a href="#" data-rel="back">
				        <div class="row">
				            <h2 class="col-xs-6"><span>${card.cardBrandLookupCode}</span></h2>
				            <h3 class="col-xs-6">${card.personName}</h3>
				        </div>
				        <h1>${card.cardNumber}</h1>
				        <input type="hidden" name="selectCard" value="${card.cardNumber}">
				    </a>
				</li>
        	</c:forEach>
        </ul>
    <main>
    <!--/main-->
</div>
<!--/page-->
</body>
</html>
