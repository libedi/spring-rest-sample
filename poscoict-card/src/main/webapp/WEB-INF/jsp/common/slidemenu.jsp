<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--slide menu-->
<div id="slideMenu" data-role="panel" data-position="right" data-display="overlay">
   	<!--slide header-->
   	<header class="slidemenu-header">
       	<div class="main-top-btn-section">
           <a href="${urls.homeUrl}" class="btn-icon-home">home</a>
           <a href="#" data-rel="close" class="btn-icon-close">Close</a>
       	</div>
       	<div class="clearfix">
           <h1 class="main-top-img">
               <div class="main-img">
                   <div class="main-img-logo"></div>
                   <div class="main-img-circle"></div>
               </div>
               <div class="main-img-tit"></div>
               <div class="main-img-ict"></div>
           </h1>
           <div class="main-img-bg"></div>
       	</div>
	</header>
	<!--/slide header-->
	<ul data-role="listview" class="slidemenu-list">
		<li><h2 class="slidemenu-subtit-approval"><spring:message code="menu.cate1"/></h2></li>
        <li><a href="${urls.erpUrl}${encodeId}" class="wbtn"><spring:message code="menu.erp"/><span id="erpCnt" class="badge erpCnt hidden"></span></a></li>
        <li><a href="${urls.cardUrl}" class="wbtn"><spring:message code="menu.card"/><span id="cardCnt" class="badge cardCnt hidden"></span></a></li>
        <li><a href="#" class="wbtn mainLink" data-url="${urls.pomUrl}"><spring:message code="menu.pom"/><span id="pomCnt" class="badge pomCnt hidden"></span></a></li>
        <li><a href="#" class="wbtn mainLink" data-url="${urls.secUrl}"><spring:message code="menu.sec"/><span id="secCnt" class="badge secCnt hidden"></span></a></li>
        <li><a href="#" class="wbtn mainLink" data-url="${urls.etcUrl}"><spring:message code="menu.etc"/><span id="etcCnt" class="badge etcCnt hidden"></span></a></li>
        <li><h2 class="slidemenu-subtit-application"><spring:message code="menu.cate2"/></h2></li>
        <li data-role="collapsible" data-iconpos="no" data-inset="false">
            <h3 class="wbtn"><spring:message code="menu.work"/></h3>
            <ul class="collapsible-application" data-role="listview">
                <li><a href="#" class="wbtn subLink" data-url="${urls.workUrl}" data-target="/work"><spring:message code="menu.work.hist"/></a></li>
                <li><a href="#" class="wbtn subLink" data-url="${urls.workUrl}" data-target="/annual"><spring:message code="menu.work.year"/></a></li>
                <li><a href="#" class="wbtn subLink" data-url="${urls.workUrl}" data-target="/vacation"><spring:message code="menu.work.nowrk"/></a></li>
                <li><a href="#" class="wbtn subLink" data-url="${urls.workUrl}" data-target="/flexible"><spring:message code="menu.work.relax"/></a></li>
            </ul>
        </li>
        <li data-role="collapsible" data-iconpos="no" data-inset="false">
            <h3 class="wbtn"><spring:message code="menu.bs-trip"/></h3>
            <ul class="collapsible-application" data-role="listview">
                <li><a href="#" class="wbtn subLink" data-url="${urls.bsTripUrl}" data-target="/travel"><spring:message code="menu.bs-trip.hist"/></a></li>
                <li><a href="#" class="wbtn subLink" data-url="${urls.bsTripUrl}" data-target="/city"><spring:message code="menu.bs-trip.city"/></a></li>
                <li><a href="#" class="wbtn subLink" data-url="${urls.bsTripUrl}" data-target="/domestic"><spring:message code="menu.bs-trip.cntry"/></a></li>
            </ul>
        </li>
    </ul>
    <form id="linkForm" method="post">
    	<input type="hidden" name="encodeId" value="${encodeId}">
    	<input type="hidden" name="targetUrl" id="targetUrl">
    </form>
</div>
<!--/slide menu-->