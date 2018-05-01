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
	<title title="가산금 White List 관리"><spring:message code="rpm.addPayMng.mngTitle"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<script type="text/javascript" src="${resources}/js/ccs/rpm/addPayWhiteListMng.js"></script>
	<script type="text/javascript">
	$(function() {
		var messages = {'noResult' : '<spring:message code="common.list.notice"/>',
						'regReason' : '<spring:message code="rpm.addPayMng.insert"/>',
						'delReason' : '<spring:message code="rpm.addPayMng.delReason.insert"/>'};
		var forms = { 'searchForm': 'frmSearch'};
		addPayWhiteListMng.init({contextPath: '${contextPath}',
									context: '.contents',
									forms: forms,
									messages: messages});
		var today = DateUtil.getToday();
		today = Common.formatDateYYYYMMDD(today, ".");
		$("#stDate").val(today);
		$("#endDate").val(today);
		
		// 처음 현재 날짜에 대한 조회							
		addPayWhiteListMng.search();
		
		// 조회 BTN
		$("#searchBtn").click(function(){
			$("#pageIndex",".contents").val("1");
			addPayWhiteListMng.search();
		});
		// 조회 INPUT ENTER KEY EVENT
		$("input:text",".contents").keydown(function(event){
			if(event.keyCode === 13){
				$("#pageIndex",".contents").val("1");
				addPayWhiteListMng.search();
				event.preventDefault();
			}
		});
		// 등록 BTN
		$("#regBtn").click(function(){
			addPayWhiteListMng.addPayAddWhiteList();
		});
		
		
	});
	</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
					
		<h3 class="cont-h3" title="가산금 White List 관리"><spring:message code="rpm.addPayMng.mngTitle"/></h3>
		
		<!-- search -->
		<div class="search-box">
			<form id="frmSearch" name="frmSearch">
			<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"/>
			<input type="hidden" id="rowCount" name="pageParam.rowCount" value="10"/>
			<input type="hidden" id="wlRegNo" name="wlRegNo" />
			<input type="hidden" id="userNm" name="userNm" value="${userNm}"  >
			<fieldset>
			<legend title="가산금 White List 관리 조회"><spring:message code="rpm.addPayMng.search"/></legend>
			<table>
				<colgroup>
					<col style="width:12%">
					<col style="width:45%">
					<col style="width:12%">
					<col style="width:*">
				</colgroup>
				<caption title="가산금 White List 관리 조회"><spring:message code="rpm.addPayMng.search"/></caption>
				<tbody>
					<tr>
						<th scope="row" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
						<td>
							<input type="text" name="searchPhone" id="searchPhone" class="input-text digit" style="width:200px;"placeholder="'-' 없이 입력" maxlength="11">
						</td>
						<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
						<td>
							<input type="text" name="searchName" id="searchName" class="input-text" style="width:200px;" placeholder="이름 입력">
						</td>
					</tr>
					<tr>
						<th scope="row" title="등록 일자"><spring:message code="rpm.addPayMng.regDd"/></th>
						<td>
							<div class="datebox">
								<input type="text" name="stDate" id="stDate" class="input-text calendar" style="width:80px;">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
							<span class="hyphen">~</span>
							<div class="datebox">
								<input type="text" name="endDate" id="endDate" class="input-text calendar" style="width:80px;">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
						</td>
						<th scope="row" title="등록 구분"><spring:message code="rpm.addPayMng.regClf"/></th>
						<td>
							<select class="input-select" name="searchClf" style="width:205px;">
								<option value="" title="전체"><spring:message code="common.all"/></option>
							<c:forEach items="${regClfCd}" var="item" varStatus="status" >
                  	     		<option value="${item.cd}">${item.cdNm}</option>
                       		</c:forEach> 
							</select>
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
		
		<div class="panel-box sec-t20" id="impay-search-list">
			<!-- grid -->
			<div class="tbl-grid-box txt-center">
				<table>
					<caption title="가산금 White List 관리 리스트"><spring:message code="rpm.addPayMng.mngTitle"/></caption>
					<colgroup>
						<col style="width:4.5%">
						<col style="width:7.5%">
						<col style="width:7.5%">
						<col style="width:9%">
						<col style="width:7%">
						<col style="width:9%">
						<col style="width:*%">
						<col style="width:6%">
						<col style="width:*%">
						<col style="width:6%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" title="등록 번호"><spring:message code="rpm.addPayMng.regNo"/></th>
							<th scope="col" title="등록 일자"><spring:message code="rpm.addPayMng.regDd"/></th>
							<th scope="col" title="삭제 일자"><spring:message code="rpm.addPayMng.delDd"/></th>
							<th scope="col" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
							<th scope="col" title="이름"><spring:message code="rpm.name"/></th>
							<th scope="col" title="등록 구분"><spring:message code="rpm.addPayMng.regClf"/></th>
							<th scope="col" title="등록 사유"><spring:message code="rpm.addPayMng.regReason"/></th>
							<th scope="col" title="상담원"><spring:message code="rpm.addPayMng.adviser"/></th>
							<th scope="col" title="삭제 사유"><spring:message code="rpm.addPayMng.delReason"/></th>
							<th scope="col" title="삭제"><spring:message code="rpm.delete"/></th>
						</tr>
					</thead>
					<tbody id="result">
					</tbody>
				</table>
			</div>
			<!--// grid -->
			
			<%-- template --%>
			<script type="text/html" id="tmpl-addPayWLlist">	
				{% for (var i=0; i<o.length; i++) { %}
				<tr onclick="addPayWhiteListMng.getInfo('{%=o[i].wlRegNo%}')">		
    				<td class="txt-center">{%=o[i].wlRegNo%}</td>
					<td class="txt-center">{%=o[i].regDt%}</td>
					<td class="txt-center">{%=o[i].delDt%}</td>
					<td class="txt-center" title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td class="txt-center">{%=Common.nvl(o[i].payrNm,"")%}</td>
					<td class="txt-center">{%=Common.nvl(o[i].regClfCd,"-")%}</td>
					<td class="txt-left ellipsis" title="{%=o[i].regRsn%}">{%=Common.nvl(o[i].regRsn,'-')%}</td>
					<td class="txt-center">{%=o[i].regr%}</td>
					<td class="txt-left ellipsis" title="{%=o[i].delRsn%}">{%=o[i].delRsn%}</td>
					<td class="txt-center">
					{% 	if(o[i].delYn=="N"){ 		 %}
							<button type="button" class="inbtn2 point-red" onclick="addPayWhiteListMng.openDelAddPayWhiteList('{%=o[i].wlRegNo%}')"><spring:message code="rpm.delete"/></button>
					{%	}else if(o[i].delYn=="Y"){	 %}
							<button type="button" class="inbtn2 dis" disabled="disabled"><spring:message code="rpm.delete"/></button>							
					{%  }							 %}
					</td>
				</tr>
				{% } %}	
			</script>
			
			<!-- pagination -->
			<div class="pagination-wrap">
				<div id="pagination" class="pagination-inner"></div>
			</div>
			<!--// pagination -->
			
			<div class="panel-box sec-t30" id="info" style="display:none;">
				<div class="tbl-info info-type2">
					<h4 class="cont-h4" title="가산금 White List"><spring:message code="rpm.addPayMng.title"/></h4>
				</div>
				
				<!-- table -->
				<div class="tbl-box">
					<table>
						<caption title="가산금 White Listw 정보"><spring:message code="rpm.addPayMng.info"/></caption>
						<colgroup>
							<col style="width:14%">
							<col style="width:36%">
							<col style="width:12%">
							<col style="width:*">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" title="등록 번호"><spring:message code="rpm.addPayMng.regNo"/></th>
								<td id="wlRegNo"></td>
								<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
								<td id="payrNm"></td>
							</tr>
							<tr>
								<th scope="row" title="등록 일자"><spring:message code="rpm.addPayMng.regDd"/>&nbsp;/&nbsp;<spring:message code="rpm.addPayMng.delDd"/></th>
								<td id="regDt"></td>
								<th scope="row" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
								<td id="mphnNo"></td>
							</tr>
							<tr>
								<th scope="row" title="등록 구분"><spring:message code="rpm.addPayMng.regClf"/></th>
								<td id="regClfCd"></td>
								<th scope="row" title="상담원"><spring:message code="rpm.addPayMng.adviser"/></th>
								<td id="regr"></td>
							</tr>
							<tr>
								<th scope="row" title="등록 사유"><spring:message code="rpm.addPayMng.regReason"/></th>
								<td colspan="3">
									<textarea name="regRsn" id="regRsn" class="textarea" rows="1" cols="1" style="width:96%;height:100px" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row" title="삭제 사유"><spring:message code="rpm.addPayMng.delReason"/></th>
								<td colspan="3">
									<textarea name="delRsn" id="delRsn" class="textarea" rows="1" cols="1" style="width:96%;height:100px" readonly="readonly"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>		
				<!--// table -->
			</div>
			
		</div>

	</div>
	<!--// contents -->	
</body>
</html>
