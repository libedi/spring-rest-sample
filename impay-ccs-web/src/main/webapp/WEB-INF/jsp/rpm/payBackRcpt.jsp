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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />
	<title><spring:message code="rpm.payback.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<script type="text/javascript" src="${resources}/js/ccs/csm/cnslLayer.js"></script>
	<script type="text/javascript" src="${resources}/js/ccs/rpm/payBackRcpt.js"></script>
	<script type="text/javascript">
	$(function() {
		var messages = {"paybackRcptTitle" : "<spring:message code='csm.cnslmng.layer.title.paybackRcpt'/>",
						'noResult' 	: '<spring:message code="common.list.notice"/>',
						'checkAlert': '<spring:message code="rpm.payback.alert1"/>',
						'deleteOk'	: '<spring:message code="common.alert.delete.succ"/>',
						'errorMsg'	: '<spring:message code="common.error.message"/>'};
		var forms = { 'searchForm': 'frmSearch'};
		payBackRcptMng.init({contextPath: '${contextPath}',
								context: '.contents',
								forms: forms,
								messages: messages});
		cnslLayer.init({
			contextPath: contextPath, 
			context: ".contents",
			forms: forms,
			messages: messages
		});
		// 초기 조회 기간 셋팅
		payBackRcptMng.dateSetting();
		// 초기 조회
		payBackRcptMng.search();
		
		// datepicker 제어 최소 선택 기간 및 미래 날짜 선택 제어 
		$(".rcpt").datepicker("option", "maxDate", "+0d");
		$(".rcpt",".contents").datepicker("option", "minDate", new Date(2010, 1 - 1, 1));
		
		// 신규등록
		$("#regBtn",".contents").click(function(){
			cnslLayer.openPayBackRcpt();
		});
		// 조회
		$("#searchBtn",".contents").click(function(){
			$("#pageIndex", '.contents').val("1");
			payBackRcptMng.search();
		});
		// INPUT ENTER KEY EVENT
		$("input:text",".contents").keydown(function(event){
			if(event.keyCode === 13){
				$("#pageIndex", '.contents').val("1");
				payBackRcptMng.search();
				event.preventDefault();				
			}
		});
		// 삭제 여부 수정 
		$("#deleteBtn",".contents").click(function(){
			var msg = '<spring:message code="common.confirm.delete"/>';
        	if(confirm(msg)){
        		payBackRcptMng.checkedDel();
        	}else{
        		return;
        	}
		});
		// CHECK 전체선택 해제
		$("#allDelete",".contents").on("click", function(){
			$("input:checkbox[name='trdNos']").prop("checked", $(this).prop("checked"));
		});
		
	});
	function reSearch(){ // 리스트 갱신
		payBackRcptMng.search();
	}
	</script>
	
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
					
		<h3 class="cont-h3" title="환불 접수"><spring:message code="rpm.payback.title"/></h3>
		
		<!-- search -->
		<div class="search-box">
			<form id="frmSearch" name="frmSearch">
			<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"/>
			<input type="hidden" id="rowCount" name="pageParam.rowCount" value="5"/>
			<fieldset>
			<legend title="환불 접수 조회"><spring:message code="rpm.payback.search"/></legend>
			<table>
				<colgroup>
					<col style="width:12%">
					<col style="width:38%">
					<col style="width:12%">
					<col style="width:*">
				</colgroup>
				<caption title="환불 접수 조회"><spring:message code="rpm.payback.search"/></caption>
				<tbody>
					<tr>
						<th scope="row" title="접수일"><spring:message code="rpm.rcptDt"/></th>
						<td>
							<div class="datebox">
								<input type="text" name="rcptStDt" id="rcptStDt" class="input-text calendar rcpt" style="width:120px;">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
							<span class="hyphen">~</span>
							<div class="datebox">
								<input type="text" name="rcptEndDt" id="rcptEndDt" class="input-text calendar rcpt" style="width:120px;">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
						</td>
						<th scope="row" title="통신사"><spring:message code="rpm.commc1"/></th>
						<td>
							<select class="input-select" name="commcClf" style="width:205px">
								<option value="" title="전체"><spring:message code="common.all"/></option>
								
							<c:forEach items="${commcClf}" var="item" varStatus="status" >
                  	     		<option value="${item.cd}">${item.cdNm}</option>
                       		</c:forEach> 
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row" title="환불 요청일"><spring:message code="rpm.payback.rcptDt"/></th>
						<td>
							<div class="datebox">
								<input type="text" name="reqStDt" id="reqStDt" class="input-text calendar" style="width:120px;">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
							<span class="hyphen">~</span>
							<div class="datebox">
								<input type="text" name="reqEndDt" id="reqEndDt" class="input-text calendar" style="width:120px;">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
						</td>
						<th scope="row" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
						<td>
							<input type="text" name="phoneNo" id="phoneNo" class="input-text digit" style="width:201px;"placeholder="'-' 없이 입력" maxlength="11">
						</td>
					</tr>
					<tr>
						<th scope="row" title="환불 구분"><spring:message code="rpm.paybackClf"/></th>
						<td>
							<select class="input-select" name="pybkTypCd" style="width:213px;">
								<option value="" title="선택"><spring:message code="rpm.select"/></option>
							<c:forEach items="${pay}" var="item" varStatus="status" >
                  	     		<option value="${item.cd}">${item.cdNm}</option>
                       		</c:forEach> 
							</select>
						</td>
						<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
						<td>
							<input type="text" name="name" id="name" class="input-text" style="width:201px;" placeholder="이름 입력">
						</td>
						
					</tr>
				</tbody>
			</table>
			</fieldset>
			</form>
		</div>
		<!--// search -->
		<!-- btn -->
		<div class="btn-wrap">
			<button type="button" class="l-btn" id="searchBtn" title="조회"><spring:message code="common.button.search"/></button>
			<button type="button" class="l-btn cmpl" id="regBtn" title="등록"><spring:message code="rpm.insert"/></button>
		</div>
		<!--// btn -->

		
		<div class="panel-box" id="impay-search-list">
			<div class="tbl-info info-type2">
				<!-- unit -->
				<span class="unit" title="(단위 : 원)"><spring:message code="common.label.unit"/></span>
				<!--// unit -->
			</div>
			<!-- grid -->
			<div class="tbl-grid-box txt-center">
				<table>
					<caption title="환불 접수 리스트"><spring:message code="rpm.payback.rcpt.list"/></caption>
					<colgroup>
						<col style="width:5%">
						<col style="width:8.5%">
						<col style="width:8.5%">
						<col style="width:*">
						<col style="width:7%">
						<col style="width:10%">
						<col style="width:8%">
						<col style="width:10%">
						<col style="width:8%">
						<col style="width:12%">
						<col style="width:8%">
						<col style="width:6.5%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" title="No."			>No.</th>
							<th scope="col" title="접수일" 		><spring:message code="rpm.rcptDt"/></th>
							<th scope="col" title="환불 요청일" ><spring:message code="rpm.payback.rcptDt"/></th>
							<th scope="col" title="환불 구분" 	><spring:message code="rpm.paybackClf"/></th>
							<th scope="col" title="통신사" 		><spring:message code="rpm.commc1"/></th>
							<th scope="col" title="휴대폰 번호"	><spring:message code="rpm.phoneNo"/></th>
							<th scope="col" title="이름" 		><spring:message code="rpm.name"/></th>
							<th scope="col" title="환불 금액"	><spring:message code="rpm.payback.amt"/></th>
							<th scope="col" title="은행명" 		><spring:message code="rpm.payback.bank"/></th>
							<th scope="col" title="계좌 번호" 	><spring:message code="rpm.payback.acctNo"/></th>
							<th scope="col" title="예금주" 		><spring:message code="rpm.payback.dpstr"/></th>
							<th scope="col"><label for="del" title="삭제"><spring:message code="rpm.delete"/></label> <input type="checkbox" value="" id="allDelete" name=""></th>
						</tr>
					</thead>
					<tbody id="result">
					</tbody>
				</table>
			</div>
			<!--// grid -->
			
			<%-- template --%>
			<script type="text/html" id="tmpl-payBackRcptList">	
				{% for (var i=0; i<o.length; i++) { %}
				<tr onclick="payBackRcptMng.getPayBackRcptInfo('{%=o[i].pybkRcptNo%}')">	
    				<td class="txt-center">{%=o[i].rnum%}</td>
					<td class="txt-center">{%=o[i].regDt%}</td>
					<td class="txt-center">{%=Common.formatDateYYYYMMDD(o[i].pybkReqDd,".")%}</td>
					<td class="txt-center">{%=o[i].pybkTypCd%}</td>
					<td class="txt-center">{%=o[i].commcClf%}</td>
					<td class="txt-center" title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td class="txt-center">{%=Common.nvl(o[i].payrNm,"")%}</td>
					<td class="txt-right">{%=Common.formatMoney(o[i].payAmt)%}</td>
					<td class="txt-center">{%=o[i].bankNm%}</td>
					<td class="txt-center" title={%=o[i].acctNo%}>{%=utils.maskingAccount(o[i].acctNo)%}</td>
					<td class="txt-center">{%=o[i].dpstrNm%}</td>
					<td><input type="checkbox" value="{%=o[i].pybkRcptNo%}" name="trdNos"></td>
				</tr>
				{% } %}	
			</script>
			<!-- pagination -->
			<div class="pagination-wrap">
				<div id="pagination" class="pagination-inner"></div>
			</div>
			<!--// pagination -->
			
			<!-- btn -->
			<div class="btn-wrap">
				<button type="button" class="l-btn" id="deleteBtn" title="삭제"><spring:message code="rpm.delete"/></button>
			</div>
			<!--// btn -->
					
		</div>
		
		<div class="panel-box" id="payBackRcptInfo" style="display:none;">
			<!-- 상세 정보 -->
			<div class="tbl-box">
				<table>
					<colgroup>
						<col style="width:12%">
						<col style="width:38%">
						<col style="width:12%">
						<col style="width:*">
					</colgroup>
					<caption title="환불 접수 상세"><spring:message code="rpm.payback.rcptInfo"/></caption>
					<tbody>
						<tr>
							<th scope="row" title="접수일"><spring:message code="rpm.rcptDt"/></th>
							<td id="regDt"></td>
							<th scope="row" title="통신사"><spring:message code="rpm.commc1"/></th>
							<td id="commcClf"></td>
						</tr>
						<tr>
							<th scope="row" title="환불 요청일"><spring:message code="rpm.payback.rcptDt"/></th>
							<td id="pybkReqDd"></td>
							<th scope="row" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
							<td id="mphnNo"></td>
						</tr>
						<tr>
							<th scope="row" title="환불 구분"><spring:message code="rpm.paybackClf"/></th>
							<td id="pybkTypCd"></td>							
							<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
							<td id="payrNm"></td>
						</tr>
						<tr>
							<th scope="row" title="환불 금액"><spring:message code="rpm.payback.amt"/></th>
							<td id="payAmt"></td>
							<th scope="row" title="은행명"><spring:message code="rpm.payback.bank"/></th>
							<td id="bankNm"></td>
						</tr>
						<tr>
							<th scope="row" title="계좌 번호"><spring:message code="rpm.payback.acctNo"/></th>
							<td id="acctNo"></td>
							<th scope="row" title="예금주"><spring:message code="rpm.payback.dpstr"/></th>
							<td id="dpstrNm"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--// 상세 정보 -->
		</div>

				
	</div>
	<!--// contents -->	
</body>
</html>
