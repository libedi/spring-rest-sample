<%--
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"		pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"		uri="http://www.springframework.org/security/tags"%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<title><spring:message code="sysm.annoDescReg.title"/></title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources"	value="${pageContext.request.contextPath}/resources" />
<script type="text/javascript"	src="${resources}/js/ccs/sysm/annoDescReg.js"></script>
<script type="text/javascript">
	$(function() {
		var messages = {'noResult' 	: '<spring:message code="common.list.notice"/>',
						'insertOk' 	: '<spring:message code="common.alert.insert.succ"/>',
						'updateOk' 	: '<spring:message code="common.alert.update.succ"/>',
						'error' 	: '<spring:message code="common.error.message"/>',	
						'dateCheck1' : '<spring:message code="common.alert.termOver"/>',
						'dateCheck2' : '<spring:message code="common.alert.termError"/>'};
		var forms = {
			'searchForm' : 'frmSearch',
			'infoForm' : 'infoForm'
		};
	
		var today = "${currDate}";
		today = today.replace(/\./g, "");
		var tempDt;
		var resultDt;
		tempDt = new Date(today.substring(0,4), (today.substring(4,6)-7), Number(today.substring(6,8))+1);
		resultDt = tempDt.getFullYear().toString() + "." + Common.lpad((tempDt.getMonth() + 1), "0", "2") + "." + Common.lpad(tempDt.getDate(), "0", "2");
		$("#stDate").val(resultDt);
		
		annoDescReg.init({
			contextPath : '${contextPath}',
			context : '.contents',
			forms : forms,
			messages : messages
		});
		$(".calendar").datepicker("option", "maxDate", "+0d");
		$("#regBtn", ".contents").hide();
		annoDescReg.search();
		
		// 조회
		$("#searchBtn",".contents").click(function() {
			$("#pageIndex").val("1");
			annoDescReg.search();
		});
		$("input:text",".frmSearch").keydown(function(event){
			if(event.keyCode === 13){
				$("#pageIndex").val("1");
				annoDescReg.search();
				event.preventDefault();
			}
		});
		// 신규등록 
		$("#newBtn",".contents").click(function() {
			annoDescReg.formReset();
			$("#infoDiv ,#newTitle, #regBtn").show();
			$("#updateBtn").hide();
			$("#regDt").html(Common.formatDateYYYYMMDD(today, "."));
			$("#regr").html("${userNm}");
		});
		// 저장
		$("#regBtn",".contents").click(function() {
			validateForAnnoDescReg("insert");
		});
		// 수정
		$("#updateBtn",".contents").click(function() {
			validateForAnnoDescReg("update");
		});
		// 취소
		$("#cancelBtn",".contents").click(function(){
			var clctBordSeq = $("#clctBordSeq",".contents").val();
			if(confirm('<spring:message code="common.confirm.cancel1"/>')){
				if(Common.getIsNull(clctBordSeq)===false){
					annoDescReg.getAnnoDescInfo(clctBordSeq);	
				}else{
					annoDescReg.formReset();
					$("#newTitle", ".contents").show();
					return;
				}
        	}
		});
	});

	function validateForAnnoDescReg(division) {
		$("#division").val(division);
		$("#infoForm", ".contents").validate({
			rules : {
				bordClfCd 	: {required : true},
				titl 		: {required : true, maxlength : 100	},
				ctnt 		: {required : true	}
			},
			messages : {
				bordClfCd 	: {	required : '<spring:message code="sysm.valid.bordClfCd"/>'	},
				titl 		: {	required : '<spring:message code="sysm.valid.title.required"/>',
								maxlength :'<spring:message code="sysm.valid.title.maxlength"/>'},
				ctnt 		: {	required : '<spring:message code="sysm.valid.ctnt"/>'}
			},
			errorPlacement : function(error, element) {
				// do nothing
			},
			invalidHandler : function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					alert(validator.errorList[0].message);
					validator.errorList[0].element.focus();
				}
			},
			submitHandler : function(form) {
				var division = $("#division").val();
				if (division === "insert") {
					msg = '<spring:message code="common.confirm.insert"/>'
					if (confirm(msg)) {
						annoDescReg.addAnnoDescReg();
					} else {
						annoDescReg.formReset();
					}
				} else if (division === "update") {
					msg = '<spring:message code="common.confirm.update"/>'
					if (confirm(msg)) {
						annoDescReg.updateAnnoDescReg();
					} else {
						var clctBordSeq = $("#clctBordSeq").val();
						annoDescReg.getAnnoDescInfo(clctBordSeq);
					}
				}
			}
		});
		$('#infoForm').submit();
	}
</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">

		<h3 class="cont-h3" title="공지사항 등록"><spring:message code="sysm.annoDescReg.title"/></h3>

		<!-- search -->
		<div class="search-box">
			<form id="frmSearch" class="frmSearch">
				<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"> 
				<input type="hidden" id="rowCount" name="pageParam.rowCount" value="5">
				<input type="hidden" id="userNm" name="userNm" value="${user.userNm}"> 
				<fieldset>
					<legend title="공지사항 등록"><spring:message code="sysm.annoDescReg.title"/></legend>
					<table>
						<caption title="공지사항 등록"><spring:message code="sysm.annoDescReg.title"/></caption>
						<colgroup>
							<col style="width: 12%">
							<col style="width: 38%">
							<col style="width: 12%">
							<col style="width: *">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" title="조회 기간"><spring:message code="common.inquery.term"/></th>
								<td>
									<div class="datebox">
										<input type="text" name="stDate" id="stDate" class="input-text calendar" style="width: 80px;"> 
										<a href="#"><span class="icon-cal"></span></a>
									</div> <span class="hyphen">~</span>
									<div class="datebox">
										<input type="text" name="endDate" id="endDate" class="input-text calendar" style="width: 80px;" value="${currDate}"> 
										<a href="#"><span class="icon-cal"></span></a>
									</div>
								</td>
								<th scope="row" title="유형별"><spring:message code="sysm.types"/></th>
								<td>
									<select class="input-select" id="bordClfCd" name="bordClfCd" style="width: 100px;">
										<option value="" title="전체"><spring:message code="common.option.all"/></option>
										<c:forEach items="${bordClfCd}" var="item" varStatus="status">
											<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="제목+내용"><spring:message code="sysm.annoDescReg.title.ctnt"/></th>
								<td colspan="3">
									<input class="input-text" style="width: 450px;" type="text" id="srchText" name="srchText" placeholder="<spring:message code='sysm.palceholder'/>">
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
			<button type="button" class="l-btn" id="newBtn" title="신규 등록"><spring:message code="common.button.new"/></button>
		</div>
		<!--// btn -->

		<div class="panel-box" id="impay-csm-annoDesc">
			<div class="tbl-info">
				<h4 class="cont-h4" title="공지 리스트"><spring:message code="sysm.anno.list"/></h4>
			</div>
			<!-- grid -->
			<div class="tbl-grid-box">
				<table>
					<caption title="공지 리스트"><spring:message code="sysm.anno.list"/></caption>
					<colgroup>
						<col style="width: 12%">
						<col style="width: *">
						<col style="width: 15%">
						<col style="width: 15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" title="날짜"><spring:message code="sysm.date"/></th>
							<th scope="col" title="제목"><spring:message code="sysm.title"/></th>
							<th scope="col" title="유형"><spring:message code="sysm.type"/></th>
							<th scope="col" title="등록자"><spring:message code="sysm.regr"/></th>
						</tr>
					</thead>
					<tbody id="result">
					</tbody>
				</table>
			</div>
			<!--// grid -->

			<%-- template --%>
			<script type="text/html" id="tmpl-annoDescList">	
				{% var resources = '${resources}'; %}
				{% for (var i=0; i<o.length; i++) { %}
					<tr onclick="annoDescReg.getAnnoDescInfo('{%=o[i].clctBordSeq%}')">	
    					<td class="txt-center">{%=o[i].regDt%}</td>
						<td>
					{% 	if(o[i].titlBldYn=="Y"){ 	%}
						<a href="#none" class="txt-em-green">
					{%	}else{	 					%}
						<a href="#none">
					{%	}							%}	
							{%=o[i].titl%}</a>
					{% 	if(o[i].newYn=="Y"){ 		%}
							<img src="{%=resources%}/images/ico_note_new.png" alt="new">
					{%	}	 						%}
						</td>
						<td class="txt-center">{%=o[i].bordClfCd%}</td>
						<td class="txt-center">{%=o[i].regr%}</td>
					</tr>
				{% } %}	
			</script>

			<!-- pagination -->
			<div class="pagination-wrap">
				<div id="pagination" class="pagination-inner"></div>
			</div>
			<!--// pagination -->

		</div>

			
		<div class="panel-box sec-t20" id="infoDiv">
			<div class="tbl-info" id="newTitle" style="display: none;">
				<h4 class="cont-h4" title="신규등록"><spring:message code="common.button.new"/></h4>
			</div>
			<!-- table -->
			<div class="tbl-box">
				<form id="infoForm">
				<input type="hidden" id="clctBordSeq" name="clctBordSeq">
				<table>
					<colgroup>
						<col style="width: 12%">
						<col style="width: 21%">
						<col style="width: 12%">
						<col style="width: 21%">
						<col style="width: 12%">
						<col style="width: *">
					</colgroup>
					<caption title="공지 상세 정보"><spring:message code="sysm.annoDetail.info"/></caption>
					<tbody>
						<tr>
							<th scope="row" title="제목"><spring:message code="sysm.title"/></th>
							<td colspan="5"><input type="text" name="titl" id="titl" class="input-text" style="width: 800px;" placeholder="">
								<input type="checkbox" name="titlBldYn" id="titlBldYn" value="Y" class="mrg-l10"><label for="title_em" title="제목 강조"><spring:message code="sysm.title.bold"/></label>
							</td>
						</tr>
						<tr>
							<th scope="row" title="날짜"><spring:message code="sysm.date"/></th>
								<td id="regDt"></td>
							<th scope="row" title="유형"><spring:message code="sysm.type"/></th>
								<td>
								<select class="input-select" name="bordClfCd" id="clf" style="width: 100px">
									<option value="" title="선택"><spring:message code="rpm.select"/></option>
									<c:forEach items="${bordClfCd}" var="item" varStatus="status">
										<option value="${item.cd}">${item.cdNm}</option>
									</c:forEach>
								</select>
								</td>
							<th scope="row" title="등록자"><spring:message code="sysm.regr"/></th>
							<td id="regr"></td>
						</tr>
						<tr>
							<th scope="row" title="내용"><spring:message code="common.email.label.content"/></th>
							<td colspan="5"><textarea name="ctnt" id="ctnt"	class="textarea" rows="1" cols="1" style="width: 896px;" placeholder=""></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!--// table -->
			<!-- btn -->
			<div class="btn-wrap">
				<button type="button" class="l-btn cmpl" id="updateBtn" title="수정"><spring:message code="common.button.update"/></button>
				<button type="button" class="l-btn cmpl" id="regBtn" title="등록"><spring:message code="sysm.insert"/></button>
				<button type="button" class="l-btn" id="cancelBtn" title="취소"><spring:message code="common.button.cancel"/></button>
			</div>
			<!--// btn -->

		</div>
	<input type="hidden" id="division">
	</div>
	<!--// contents -->
</body>
</html>
