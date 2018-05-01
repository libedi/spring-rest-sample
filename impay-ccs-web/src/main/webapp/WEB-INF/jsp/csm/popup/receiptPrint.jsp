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
<html lang="ko">
<head>
	<title title="IMPay 휴대폰 소액결제 내역"><spring:message code="csm.cnslmng.receipt.header"/></title>
	<style type="text/css">
		body {margin:0; padding:0; font-size:9pt; font-family:Tahoma, Arial, sans-serif,"돋움","굴림"; color:#333;}
		.wrap {width:820px; height:100%; margin:0 auto; font-size:11pt;}
		.wrap .contents {padding:0 50px;}
		.wrap table {margin:0 auto;}
		.contents {}
		.header {position:relative; height:60px; padding:0 0 10px 1px; border-bottom:4px solid #e51937; }
		.header img {position:absolute; left:2px; bottom:12px; height:45px;}
		.conts {padding:20px 0; line-height:170%;}
		.conts table {width:100%;}
		.conts h2 {height:37px; margin:0; padding:0; font-size:14pt; font-weight:bold; text-align:left;}
		.conts h2 img {vertical-align:middle;}
		.conts h2 b {color:#e51937;}
		.conts p {padding-bottom:30px;}
		.tbl-box {border-top:2px solid #545454; padding-bottom:30px;}
		table {border-collapse:collapse; border-spacing:0;}
		th,td {margin:0; padding:0; background:#fff; border:1px solid #c4c4c4; line-height:140%;}
		th {background-color:#e8e9eb; padding:7px 3px; border-top:none !important;}
		td {padding:5px;}
		.text-left {text-align:left;}
		.text-right {text-align:right;}
		.text-center {text-align:center;}
		@media print {
			body {color:#000; background:transprent;}
			.tbl-box {border-top:2px solid #000;}
			th,td {border:1px solid #000;}
			th {background-color:#e8e9eb;}
			p {}
		}
	</style>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
</head>
<body>
	<div class="wrap">
		<div class="contents">
			<div class="header">
				<h1><img src="${resources}/images/bi_impay.png" alt="IMPay"></h1>
			</div>
			<div class="conts">
				<p><spring:message code="csm.cnslmng.receipt.text1"/></p>
				<h2><img src="${resources}/images/bul_statement.png" alt=""><b>${mphnNo}</b> <spring:message code="csm.cnslmng.receipt.title"/></h2>
				<div class="tbl-box text-center">
					<table>
						<colgroup>
							<col style="width:5%">
							<col style="width:11.5%">
							<col style="width:11.5%">
							<col style="width:*">
							<col style="width:13%">
							<col style="width:19%">
							<col style="width:10.5%">
							<col style="width:7.5%">
						</colgroup>
						<thead>
							<tr>
								<th><spring:message code="csm.cnslmng.receipt.col1"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col2"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col3"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col4"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col5"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col6"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col7"/></th>
								<th><spring:message code="csm.cnslmng.receipt.col8"/></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${trdList}" var="list" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${list.trdDt}</td>
								<td>${list.cnclDt}</td>
								<td class="text-left">${list.paySvcNm}</td>
								<td>${list.telNo}</td>
								<td class="text-left">${list.godsNm}</td>
								<td class="text-right"><fmt:formatNumber value="${list.payAmt}" type="number"/></td>
								<td>${list.payStat}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				<p><spring:message code="csm.cnslmng.receipt.text2"/></p>
			</div>
		</div>
	</div>
</body>
</html>