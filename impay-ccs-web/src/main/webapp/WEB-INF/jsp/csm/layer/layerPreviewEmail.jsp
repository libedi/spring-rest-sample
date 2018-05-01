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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
<script type="text/javascript">
	$(function(){
		var data = layer.getOptions().data;
		$("#title", ".fixed-layer").val(data.title);
		$("#email", ".fixed-layer").val(data.email);
		$("div.email-txt").html(data.procCtnt.replace(/[\r|\n|\r\n|\n\r]/g, "<br/>"));
		// 닫기
		$(".closePop", ".fixed-layer").click(layer.close);
	});
</script>
<div class="tbl-box">
	<dl class="ul-tbl">
		<dt title="제목"><spring:message code="common.email.label.title"/></dt>
		<dd><input type="text" name="title" id="title" class="input-text" style="width:650px;" value=""></dd>
	</dl>
	<dl class="ul-tbl">	
		<dt title="수신자"><spring:message code="common.email.label.receipient"/></dt>
		<dd><input type="text" name="email" id="email" class="input-text" style="width:650px;" value=""></dd>
	</dl>
	<dl class="ul-tbl email-conts">		
		<dt class="hide" title="내용"><spring:message code="common.email.label.content"/> :</dt>
		<dd>
			<div class="email-bg"></div>
			<div class="email-txt view"></div>
		</dd>
	</dl>
</div>
<div class="email-footer mrg-tm1">
	<span class="footer-ci"><img src="${resources}/images/skp.png" alt="SK planet"></span>
	<dl class="footer-info">
		<dt title="고객센터"><strong><spring:message code="common.footer.info.cs"/> :</strong></dt>
		<dd><strong>1599-2583</strong></dd>
		<dt title="운영시간"><spring:message code="common.footer.info.cs.run"/> :</dt>
		<dd title="평일(월~금) 09:00 ~ 18:00"><spring:message code="common.footer.info.cs.runtime"/></dd>
	</dl>
</div>
<!-- btn -->
<div class="btn-wrap">
	<button type="button" class="l-btn closePop" onclick="layer.close();" id="" title="확인"><spring:message code="common.button.ok"/></button>
</div>
<!--// btn -->