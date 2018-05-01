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
	<title title="이관 접수"><spring:message code="rpm.tjur.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<script type="text/javascript" src="${resources}/js/ccs/rpm/transferRcpt.js"></script>
	<script type="text/javascript">
	var contextPath = "${contextPath}";
	var resources = "${resources}";
	$(function(){
		var messages = {
				"noResult" : "<spring:message code='common.list.notice'/>",
				"error" : "<spring:message code='common.error.message'/>",
				"excelDown" : "<spring:message code='common.button.excel.notice'/>",
				"excelDownLimit" : "<spring:message code='common.button.excel.limitNotice'/>",
				"layerCnslTitle" : "상담 내용 보기",
				"layerProcTitle" : "처리 내용 보기",
				"layerRsltTitle" : "처리 결과 보기"
		};
		var forms = {
					searchForm: "searchForm",
					moveForm: "moveForm"
		};
		transferRcpt.init({
						contextPath: contextPath, 
						context: ".contents",
						forms: forms,
						messages: messages
		});
		
		// 최소선택 기간을 2010년으로 설정
		$(".calendar", ".contents").datepicker("option", "minDate", new Date(2010, 1 - 1, 1));
		
		// 조회 버튼 클릭 이벤트
		$("#searchBtn", ".contents").click(search);
		
		// 엑셀 다운로드 클릭 이벤트
		$("#excelBtn", ".contents").click(transferRcpt.excelDown);
		
		// 상담내용 이미지 클릭 이벤트
		$("#result", ".contents").on("click", "a", function(){
			var id = $(this).data("id");
			var hash = $(this).data("hash");
			
			if($(this).data("flg") === "R"){
				$("#rcptNo", ".contents").val(id);
				$("#hash", ".contents").val(hash);
				
				$("#moveForm", ".contents").submit();	// 상담관리 화면으로 submit
			} else {
				transferRcpt.openLayerCnslUpldFile(id, hash);
			}
		});
		
		// 필드 엔터키 입력 이벤트
		$("input:text", ".contents").keyup(function(e){
			var code = e.which ? e.which : e.keyCode;
			if(code === 13 || code === 10){
				search();
			}
		});
		
		// 검색 초기화 버튼 이벤트
		$(".init-search", ".contents").click(function(){
			$(".calendar", ".contents").val("${today}");
		});
	});

	// 조회
	function search() {
		// 날짜 범위 검사
		$.validator.addMethod("dateCheck", function(value, element){
			return this.optional(element) || Number($.trim(value).replace(/\./g,'')) - Number($.trim($("#stDate", ".contents").val()).replace(/\./g,'')) >= 0
		}, "<spring:message code='common.alert.termError'/>");
		
		// 날짜 유효성 검사
		$.validator.addMethod("date", function(value, element) {
			var agent = navigator.userAgent.toLowerCase();
		    if(agent.indexOf("msie") !== -1 || agent.search("trident") > -1 || agent.search("edge/") > -1 || agent.indexOf("safari") !== -1){
		    	return this.optional(element) || /^(19|20)\d{2}\.(0[1-9]|1[0-2])(\.(0[1-9]|[12][0-9]|3[0-1]))?/g.test(value);
		    }
		    return this.optional(element) || !/Invalid|NaN/.test(new Date(value));
		});
		
		// 폼 유효성 검사
		$("#searchForm", ".contents").validate({
	    	debug: true,
			onfocusout: false,
	        rules: {
	        	stDate: { required: true, date: true },
	        	endDate: { required: true, date: true, dateCheck: true },
				searchPhone: { digits: true, rangelength: [10,11] }
			},
			messages: {
				stDate: {
					required : "<spring:message code='rpm.tjur.msg.select.date'/>",
					date : "<spring:message code='rpm.tjur.msg.check.date'/>"
				},
				endDate: {
					required: "<spring:message code='rpm.tjur.msg.select.date'/>",
	        		date: "<spring:message code='rpm.tjur.msg.check.date'/>"
				},
				searchPhone: {
					digits: "<spring:message code='rpm.tjur.msg.onlynumber'/>",
					rangelength: "<spring:message code='rpm.tjur.msg.check.length'/>"
				}
			},
	        invalidHandler: function(frm, validator) {
	        	var errors = validator.numberOfInvalids();
	        	if (errors) {
	                alert(validator.errorList[0].message);
	                validator.errorList[0].element.focus();
	            }
			},
	        submitHandler: function() {
	        	transferRcpt.search();
			},
	        showErrors: function(errorMap, errorList) {
	        }
		});
		
		$("#pageIndex", ".contents").val("1");
		utils.applyTrim("searchForm");
		$("#searchForm", ".contents").submit();
	}
	</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
					
		<h3 class="cont-h3" title="이관 접수"><spring:message code="rpm.tjur.title"/></h3>
		
		<!-- search -->
		<div class="search-box">
			<form id="searchForm">
			<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"/>
			<input type="hidden" id="rowCount" name="pageParam.rowCount" value="5"/>
			<fieldset>
			<legend title="이관 접수 조회"><spring:message code="rpm.tjur.caption1.title"/></legend>
			<table>
				<colgroup>
					<col style="width:12%">
					<col style="width:38%">
					<col style="width:12%">
					<col style="width:*">
				</colgroup>
				<caption title="이관 접수 조회"><spring:message code="rpm.tjur.caption1.title"/></caption>
				<tbody>
					<tr>
						<th scope="row" title="업로드 일자"><spring:message code="rpm.tjur.th.date.upload"/></th>
						<td colspan="3">
							<div class="datebox">
								<input type="text" name="stDate" id="stDate" class="input-text calendar" value="${today}" style="width:80px;" readonly="readonly">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
							<span class="hyphen">~</span>
							<div class="datebox">
								<input type="text" name="endDate" id="endDate" class="input-text calendar" value="${today}" style="width:80px;" readonly="readonly">
								<a href="#"><span class="icon-cal"></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row" title="이관 구분"><spring:message code="rpm.tjur.th.gubun.tjur"/></th>
						<td>
							<select class="input-select" name="tjurClfFlg" id="tjurClfFlg" style="width:150px;">
								<option value="" title="전체"><spring:message code="common.all"/></option>
								<option value="C"><spring:message code="rpm.tjur.th.cp"/></option>
								<c:forEach items="${tjur}" var="list" varStatus="status">
									<option value="${list.cd}">${list.cdNm}</option>
								</c:forEach>
							</select>
						</td>
						<th scope="row" title="휴대폰 번호"><spring:message code="rpm.tjur.th.mphnno"/></th>
						<td>
							<input type="text" name="searchPhone" id="searchPhone" class="input-text digit" style="width:210px;" placeholder="<spring:message code='common.input.placeholder1'/>">
						</td>
					</tr>
					<tr>
						<th scope="row" title="처리 상태"><spring:message code="rpm.tjur.th.stat.proc"/></th>
						<td>
							<select class="input-select" name="procStat" id="procStat" style="width:150px;">
								<option value="" title="전체"><spring:message code="common.all"/></option>
								<option value="Y"><spring:message code="rmp.tjur.option.processY"/></option>
								<option value="N"><spring:message code="rmp.tjur.option.processN"/></option>
								<option value="C"><spring:message code="rmp.tjur.option.processC"/></option>
							</select>
						</td>
						<th scope="row" title="가맹점"><spring:message code="rpm.tjur.th.cp"/></th>
						<td>
							<select class="input-select" name="pgId" id="pgId" style="width:215px;">
								<option value="" title="전체"><spring:message code="common.all"/></option>
								<c:forEach items="${pgCdList}" var="list" varStatus="status">
									<option value="${list.cd}">${list.cdNm}</option>
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
			<button type="button" class="l-btn init-search" title="초기화"><spring:message code="common.button.init"/></button>
			<button type="button" class="l-btn" id="searchBtn" title="조회"><spring:message code="common.button.search"/></button>
		</div>
		<!--// btn -->

		<!-- 조회 전 안내 문구-->
		<div class="search-info" title="원하시는 항목을 선택 하신 후 조회를 클릭 하시기 바랍니다.">
			<spring:message code="common.list.indication1"/>
		</div>
		<!--// 조회 전 안내 문구 -->
		
		<div class="panel-box" style="width:1300px; padding-right:30px; display: none;">
			<div class="tbl-info info-type2">
				<!-- unit -->
				<span class="unit" title="(단위 : 원)"><spring:message code="common.label.unit"/></span>
				<!--// unit -->
			</div>
			<form id="moveForm" action="${contextPath}/cnslMng/view" method="post">
			<input type="hidden" name="mnuId" value="CCM000"/>
			<input type="hidden" name="uprMnuId" value="CCM001"/>
			<input type="hidden" name="rcptNo" id="rcptNo" value=""/>
			<input type="hidden" name="hash" id="hash" value=""/>
			<div class="tbl-grid-box txt-center">
				<table>
					<caption title="이관 접수 리스트"><spring:message code="rpm.tjur.caption2.title"/></caption>
					<colgroup>
						<col style="width:3.5%">
						<col style="width:6.5%">
						<col style="width:10%">
						<col style="width:4%">
						<col style="width:8%">
						<col style="width:4.5%">
						<col style="width:6%">
						<col style="width:*">
						<col style="width:*">
						<col style="width:4.1%">
						<col style="width:4%">
						<col style="width:4%">
						<col style="width:4%">
						<col style="width:6.5%">
						<col style="width:3.3%">
						<col style="width:3.3%">
						<col style="width:4%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" title="No."><spring:message code="rpm.tjur.th.no"/></th>
							<th scope="col" title="이관 일자"><spring:message code="rpm.tjur.th.date.tjur"/></th>
							<th scope="col" title="가맹점"><spring:message code="rpm.tjur.th.cp"/></th>
							<th scope="col" title="통신사"><spring:message code="rpm.commc1"/></th>
							<th scope="col" title="휴대폰 번호"><spring:message code="rpm.tjur.th.mphnno"/></th>
							<th scope="col" title="결제 일자"><spring:message code="rpm.tjur.th.date.pay.br"/></th>
							<th scope="col" title="결제 금액"><spring:message code="rpm.tjur.th.payamt"/></th>
							<th scope="col" title="가맹점 명"><spring:message code="rpm.tjur.th.cpnm"/></th>
							<th scope="col" title="상품명"><spring:message code="rpm.tjur.th.goodsnm"/></th>
							<th scope="col" title="상담 구분"><spring:message code="rpm.tjur.th.gubun.cnsl.br"/></th>
							<th scope="col" title="상담 유형"><spring:message code="rpm.tjur.th.type.cnsl.br"/></th>
							<th scope="col" title="접수 유형"><spring:message code="rpm.tjur.th.type.rcpt.br"/></th>
							<th scope="col" title="처리 상태"><spring:message code="rpm.tjur.th.stat.proc.br"/></th>
							<th scope="col" title="처리일시"><spring:message code="rpm.tjur.th.date.proc"/></th>
							<th scope="col" title="상담 내용"><spring:message code="rpm.tjur.th.content.cnsl.br"/></th>
							<th scope="col" title="처리 내용"><spring:message code="rpm.tjur.th.content.proc.br"/></th>
							<th scope="col" title="결과 통보"><spring:message code="rpm.tjur.th.noti.result.br"/></th>
						</tr>
					</thead>
					<tbody id="result">
					</tbody>
				</table>
				<div class="pagination-wrap" id="paginationWrap">
					<div class="pagination-inner" id="paginationInner">
					</div>
				</div>
			</div>
			</form>
			
			<%-- Template --%>
			<script type="text/html" id="tmpl-list">
			{% for(var i=0; i<o.length; i++) { %}
			<tr>
				<td>{%=o[i].rnum%}</td>
				<td>{%=o[i].regDt%}</td>
				<td class="txt-left">{%=StringUtil.nvl(o[i].entpNm, "")%}</td>
				<td>{%=StringUtil.nvl(o[i].commcClfNm, "")%}</td>
				<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
				<td>{%=StringUtil.nvl(o[i].trdDt, "")%}</td>
				<td class="txt-right">{%=StringUtil.nvl(Common.formatMoney(o[i].payAmt), "")%}</td>
				<td class="txt-left">{%=StringUtil.nvl(o[i].paySvcNm, "")%}</td>
				<td class="txt-left">{%=StringUtil.nvl(o[i].godsNm, "")%}</td>
				<td>{%=StringUtil.nvl(o[i].cnslClfUprCdNm, "")%}</td>
				<td>{%=o[i].cnslTypCdNm%}</td>
				<td>{%=o[i].rcptMthdCdNm%}</td>
				<td>{%=o[i].tjurProcYn%}</td>
				<td>{%=StringUtil.nvl(o[i].procDt, "-")%}</td>
				<td><a href="javascript:void(0);" data-id="{%=o[i].rcptNo%}" data-hash="counsel-view" data-flg="{%=o[i].flg%}" title="{%=o[i].cnslCtnt%}"><img src="{%=resources%}/images/counselIcon.gif" alt="<spring:message code='rpm.tjur.th.content.cnsl'/>"></a></td>
				{% if(o[i].flg === 'D' || Common.getIsNull(o[i].procCtnt) === false){ %}
				<td><a href="javascript:void(0);" data-id="{%=o[i].rcptNo%}" data-hash="process-view" data-flg="{%=o[i].flg%}" title="{%=o[i].procCtnt%}"><img src="{%=resources%}/images/counselIcon2.gif" alt="<spring:message code='rpm.tjur.th.content.proc'/>"></a></td>
				{% } else { %}
				<td></td>
				{% } %}
				{% if(o[i].flg === 'D'){ %}
				<td><a href="javascript:void(0);" data-id="{%=o[i].rcptNo%}" data-hash="result-view" data-flg="{%=o[i].flg%}" title="{%=o[i].rsltNotiMthdNm%}"><img src="{%=resources%}/images/counselIcon2.gif" alt="<spring:message code='rpm.tjur.th.noti.result'/>"></a></td>
				{% } else if(Common.getIsNull(o[i].rsltNotiMthd) === false){ %}
				<td><a href="javascript:void(0);" data-id="{%=o[i].rcptNo%}" data-hash="result-view" data-flg="{%=o[i].flg%}">{%=o[i].rsltNotiMthdNm%}</a></td>
				{% } else { %}
				<td></td>
				{% } %}
			</tr>
			{% } %}
			</script>
		
			<!-- btn -->
			<div class="btn-wrap">
				<button type="button" class="l-btn" id="excelBtn"><span class="icon-excel" title="엑셀 다운로드"><spring:message code="common.button.execel"/></span></button>
			</div>
			<!--// btn -->
		</div>
	</div>
	<!--// contents -->	
</body>
</html>