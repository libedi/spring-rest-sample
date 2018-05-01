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
	<title title="클레임 분석 관리"><spring:message code="cam.clmAnlsMng.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<sec:authentication var="user" property="principal" />
	<script type="text/javascript" src="${resources}/js/ccs/cam/claimAnalysisMng.js"></script>
	<script type="text/javascript" src="${resources}/js/libs/chart/highcharts.js"></script>
	<script type="text/javascript" src="${resources}/js/libs/chart/modules/exporting.js"></script>
	<script type="text/javascript">
	$(function(){
		var messages = {'noResult' 		: '<spring:message code="common.list.notice"/>',
						'cpTitle' 		: '<spring:message code="common.label.cpSearch"/>',
						'excelDown' 	: '<spring:message code="common.button.excel.notice"/>',
						//char Y 축		
						'chart_y'		: '<spring:message code="cam.chart.y"/>',
						'count'			: '<spring:message code="common.count"/>',
						//chart 첫번째
						'chart1_header'	: '<spring:message code="cam.chart1.header"/>',
						'chart1_label1'	: '<spring:message code="cam.chart1.x.label1"/>',
						'chart1_label2'	: '<spring:message code="cam.chart1.x.label2"/>',
						'chart1_label3'	: '<spring:message code="cam.chart1.x.label3"/>',
						'chart1_label4'	: '<spring:message code="cam.chart1.x.label4"/>',
						//chart 두번째
						'chart2_header'	: '<spring:message code="cam.chart2.header"/>',
						'chart2_label1'	: '<spring:message code="cam.chart2.x.label1"/>',
						'chart2_label2'	: '<spring:message code="cam.chart2.x.label2"/>',
						'chart2_label3'	: '<spring:message code="cam.chart2.x.label3"/>',
						//chart 세번째
						'chart3_header'	: '<spring:message code="cam.chart3.header"/>',
						'chart3_label1'	: '<spring:message code="cam.chart3.x.label1"/>',
						'chart3_label2'	: '<spring:message code="cam.chart3.x.label2"/>',
						'chart3_label3'	: '<spring:message code="cam.chart3.x.label3"/>',
						'chart3_label4'	: '<spring:message code="cam.chart3.x.label4"/>',
						'dateCheck1'	: '<spring:message code="common.alert.termOver"/>',
						'dateCheck2' 	: '<spring:message code="common.alert.termError"/>'
						};
		var forms = {	
					'searchFormTab1': 'tab1-frmSearch',
					'searchFormTab2' : 'tab2-frmSearch',
					'searchFormTab3' : 'tab3-frmSearch'};
		clmAnslMng.init({contextPath: '${contextPath}',
							context	: '.contents',
							forms	: forms,
							messages: messages});

		var admTypCd = "${user.admTypCd eq admTypCd}";
		
		if(admTypCd !== "true"){
			$(".excelDown").hide();
		}
		
		Common.tabs();
		
		// 최대 선택 날짜 현재까지 설정
		$(".calendar",".contents").datepicker("option", "maxDate", "+0d");
		$(".calendar",".contents").datepicker("option", "minDate", new Date(2010, 1 - 1, 1));
		
		// 조회 기간 BUTTON EVENT
		$(".inbtn2", ".contents").click(function(){
			var id = $(this).attr("id");
			// 오늘날짜
			var today = "${currDate}";
			$("#endDateT1").val(today);
			today = today.replace(/\./g, "");
			var tempDt;
			var resultDt;
			if(id == "mon"){			// 이번달 처음날짜
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-1));
			} else if(id == "1mon") {	// 1달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-2),(today.substring(6,8)));
			} else if(id == "2mon") {	// 2달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-3),(today.substring(6,8)));
			} else if(id == "3mon") {	// 3달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-4),(today.substring(6,8)));
			} else if(id == "6mon") {	// 6달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-7),(today.substring(6,8)));
			} else if(id == "1year") {	// 1년전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-12),(today.substring(6,8)));
			}
			resultDt = tempDt.getFullYear().toString() + "." + Common.lpad((tempDt.getMonth() + 1),"0","2")+"."+Common.lpad(tempDt.getDate(),"0","2");
			$("#stDateT1").val(resultDt);
		});
		// TAB1(건별 조회), TAB2(유형별 조회) ENTER KEY EVENT
		$("input:text", '.contents').keydown(function (event) {
			if (event.keyCode == 13) {
				var form = $(this).parents("form").attr("id").split("-");
				utils.applyTrim(form[0]+"-frmSearch");
				$("#"+form[0]+"-pageIndex", '.contents').val("1");
				
				if(form[0] == "tab1"){
					clmAnslMng.searchTab1();	
				} else if(form[0] == "tab2"){
					clmAnslMng.searchTab2();
				} else {
					return;
				}
				event.preventDefault();
			}
		});
		
		/* TAB-1 */
		// 건별 조회 
		$("#searchBtn-Tab1").click(function(){
			utils.applyTrim("tab1-frmSearch");
			$("#tab1-pageIndex", '.contents').val("1");
			clmAnslMng.searchTab1();
		});
		// 건별 엑셀다운
		$("#caseExcel").click(function(){
			clmAnslMng.caseExcelDown();
		});
		
		/* TAB-2 */
		// 유형별 조회 
		$("#searchBtn-Tab2").click(function(){
			utils.applyTrim("tab2-frmSearch");
			$("#tab2-pageIndex", '.contents').val("1");
			clmAnslMng.searchTab2();
		});
		// 건별 엑셀다운
		$("#typeExcel").click(function(){
			clmAnslMng.typeExcelDown();
		});
		
		/* TAB-3 */
		// 이관접수별 조회
		$("#searchBtn-Tab3").click(function(){
			$("#tab3-pageIndex", '.contents').val("1");
			clmAnslMng.searchTab3();
		});
		$("#tjurExcel").click(function(){
			clmAnslMng.tjurExcelDown();
		});
	});
	// 상담내용 아이콘 이벤트 화면전환 -> 상담관리
	function cnsl(rcptNo, hash){
		$("#hiddenForm #rcptNo").val(rcptNo);
		$("#hiddenForm #hash").val(hash);
		$("#hiddenForm").submit();
	};
	// 처리내용 아이콘 이벤트 화면전환 -> 상담관리(처리)
	function proc(rcptNo, hash){
		$("#hiddenForm #rcptNo").val(rcptNo);
		$("#hiddenForm #hash").val(hash);
		$("#hiddenForm").submit();
	};
	</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
					
		<h3 class="cont-h3" title="클레임 분석 관리"><spring:message code="cam.clmAnlsMng.title"/></h3>
		
		<div class="panel-box">
			<!-- tabs -->
			<ul id="tabs" class="tab-head basic">
				<li><a href="#tab1" class="tab-selected" title="건별 조회"><spring:message code="cam.clmAnlsMng.case"/></a></li>
				<li><a href="#tab2" class="" title="유형별 조회"><spring:message code="cam.clmAnlsMng.type"/></a></li>
				<li><a href="#tab3" class="" title="이관 접수건 조회"><spring:message code="cam.clmAnlsMng.tjur"/></a></li>
			</ul>
			<!-- tabs -->

			<!-- tab 1 -->
			<div id="tab1" class="tab-conts">
				
				<!-- search-->
				<div class="search-box">
					<form id="tab1-frmSearch">
					<input type="hidden" id="tab1-pageIndex" name="pageParam.pageIndex" value="1"  >
					<input type="hidden" id="tab1-rowCount"  name="pageParam.rowCount"  value="5" >
					<fieldset>
					<legend title="건별 조회"><spring:message code="cam.clmAnlsMng.case"/></legend>
					<table>
						<colgroup>
							<col style="width:12%">
							<col style="width:38%">
							<col style="width:12%">
							<col style="width:*">
						</colgroup>
						<caption title="건별 조회"><spring:message code="cam.clmAnlsMng.case"/></caption>
						<tbody>
							<tr>
								<th scope="row" title="접수 일자"><spring:message code="cam.rcptDd"/></th>
								<td colspan="3">
									<div class="datebox">
										<input type="text" name="stDate" id="stDateT1" class="input-text calendar" value="${currDate}" style="width:80px;">
										<a href="#none"><span class="icon-cal"></span></a>
									</div>
									<span class="hyphen">~</span>
									<div class="datebox">
										<input type="text" name="endDate" id="endDateT1" class="input-text calendar" value="${currDate}" style="width:80px;">
										<a href="#none"><span class="icon-cal"></span></a>
									</div>
									<button type="button" class="inbtn2" id="mon" title="당월"><spring:message code="common.button.month0"/></button>
									<button type="button" class="inbtn2" id="1mon" title="1개월"><spring:message code="common.button.month1"/></button>
									<button type="button" class="inbtn2" id="2mon" title="2개월"><spring:message code="common.button.month2"/></button>
									<button type="button" class="inbtn2" id="3mon" title="3개월"><spring:message code="common.button.month3"/></button>
									<button type="button" class="inbtn2" id="6mon" title="6개월"><spring:message code="common.button.month4"/></button>
									<button type="button" class="inbtn2" id="1year" title="1년"><spring:message code="common.button.month5"/></button>
								</td>
							</tr>
							<tr>
								<th scope="row" title="접수 유형"><spring:message code="cam.rcptType"/></th>
								<td>
									<select class="input-select" name="rcptMthdCd" id="rcptMthdCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${rcptMthdCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row" title="결제 조건"><spring:message code="cam.payClf"/></th>
								<td>
									<select class="input-select" name="payCndiCd" id="payCndiCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${cnslPayClf}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="상담 유형"><spring:message code="cam.cnslType"/></th>
								<td>
									<select class="input-select" name="cnslTypCd" id="cnslTypCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${cnslTypCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row" title="상담 구분"><spring:message code="cam.cnslClf"/></th>
								<td>
									<select class="input-select" name="cnslClfUprCd" id="cnslClfUprCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${cnslClfCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="처리 여부"><spring:message code="cam.procYn"/></th>
								<td>
									<select class="input-select" name="procYn" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<option value="Y" title="처리"><spring:message code="cam.procY"/></option>
										<option value="N" title="미처리"><spring:message code="cam.procN"/></option>
									</select>
								</td>
								<th scope="row" title="통신사"><spring:message code="cam.commc1"/></th>
								<td>
									<select class="input-select" name="commcClf" id="commcClf" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${commcClf}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="휴대폰 번호"><spring:message code="cam.mphnNo"/></th>
								<td>
									<input type="text" name="mphnNo" id="mphnNo" class="input-text digit" style="width:275px;"placeholder="'-' 없이 입력">
								</td>
								<th scope="row" title="접수자"><spring:message code="cam.rcpt.regr"/></th>
								<td>
									<select class="input-select" name="regr" style="width:280px;">
										<option value="" title="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${ccsUser}" var="item" varStatus="status" >
	  										<option value="${item.userSeq}">${item.userId}(${item.userNm})</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="가맹점 조회"><spring:message code="cam.cpSearch"/></th>
								<td>
									<input type="text" name="cpCd" id="cpCd" class="input-text searchCode" style="width:110px;" placeholder="코드">
									<input type="text" name="cpNm" id="cpNm" class="input-text" style="width:158px;" placeholder="이름">
									<button type="button" class="inbtn" id="findCp" title="검색" onclick="clmAnslMng.openTrgtCpSeachLayer()"><spring:message code="common.button.find"/></button>
								</td>
								<th scope="row" title="이벤트"><spring:message code="cam.event"/></th>
								<td>
									<select class="input-select" name="cnslEvntCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${eventTyp}" var="item" varStatus="status" >
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
				<!-- btn-->
				<div class="btn-wrap">
					<button type="button" class="l-btn" id="searchBtn-Tab1" title="조회"><spring:message code="common.button.search"/></button>
				</div>
				<!--// btn -->
				
				<!-- 조회 전 안내 문구-->
				<div class="search-info" id="tab1-info" title="원하시는 항목을 선택 하신 후 조회를 클릭 하시기 바랍니다.">
					<spring:message code="common.list.indication1"/>
				</div>
				<!--// 조회 전 안내 문구 -->
					
				<div class="panel-box" style="width:1300px; display:none; padding-right:30px;" id="impay-cam-caseList">
					<!-- grid -->
					<div class="tbl-grid-box txt-center">
						<table>
							<caption title="건별 조회 리스트"><spring:message code="cam.clmAnlsMng.case.list"/></caption>
							<colgroup>
								<col style="width:5.5%">
								<col style="width:6.5%">
								<col style="width:4%">
								<col style="width:7%">
								<col style="width:6%">
								<col style="width:5.5%">
								<col style="width:7.5%">
								<col style="width:*">
								<col style="width:*">
								<col style="width:6%">
								<col style="width:5%">
								<col style="width:4%">
								<col style="width:5%">
								<col style="width:4.5%">
								<col style="width:4.5%">
								<col style="width:3.3%">
								<col style="width:3.3%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="접수 번호"><spring:message code="cam.rcptNo"/></th>
									<th scope="col" title="접수 일시"><spring:message code="cam.rcptDt"/></th>
									<th scope="col" title="접수 유형"><spring:message code="cam.br.rcptType"/></th>
									<th scope="col" title="접수자"><spring:message code="cam.rcpt.regr"/></th>
									<th scope="col" title="고객 유형"><spring:message code="cam.custType"/></th>
									<th scope="col" title="결제 조건"><spring:message code="cam.payClf"/></th>
									<th scope="col" title="휴대폰 번호"><spring:message code="cam.mphnNo"/></th>
									<th scope="col" title="가맹점 명"><spring:message code="cam.cpNm"/></th>
									<th scope="col" title="상품 명"><spring:message code="cam.gods"/></th>
									<th scope="col" title="이벤트"><spring:message code="cam.event"/></th>
									<th scope="col" title="통신사"><spring:message code="cam.commc1"/></th>
									<th scope="col" title="상담 유형"><spring:message code="cam.br.cnslType"/></th>
									<th scope="col" title="상담 구분"><spring:message code="cam.br.cnslClf"/></th>
									<th scope="col" title="처리 상태"><spring:message code="cam.br.procState"/></th>
									<th scope="col" title="이관 구분"><spring:message code="cam.br.tjurClf"/></th>
									<th scope="col" title="상담 내용"><spring:message code="cam.br.cnslCtnt"/></th>
									<th scope="col" title="처리 내용"><spring:message code="cam.br.procCtnt"/></th>
								</tr>
							</thead>
							<tbody id="caseResult">
							</tbody>
						</table>
					</div>
					<!--// grid -->
					
					<%-- template --%>
					<script type="text/html" id="tmpl-caseList">	
						{% for (var i=0; i<o.length; i++) { %}
							<tr>	
    							<td class="txt-center">{%=o[i].rcptNo%}</td>
								<td class="txt-center">{%=o[i].regDt%}</td> 
								<td class="txt-center">{%=o[i].rcptMthdNm%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].regr,'-')%}</td>
								<td class="txt-center">{%=o[i].custTypFlgNm%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].payCndiNm,'-')%}</td>
								<td class="txt-center" title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
								<td class="txt-left">{%=StringUtil.nvl(o[i].cpNm,'-')%}</td>
								<td class="txt-left">{%=StringUtil.nvl(o[i].godsNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].cnslEvntNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].commcClfNm,'-')%}</td>
								<td class="txt-center">{%=o[i].cnslTypNm%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].cnslClfUprCdNm,'-')%}</td>
								<td class="txt-center">{%=o[i].procNm%}</td>
								<td class="txt-center">{%=o[i].tjurClfFlgNm%}</td>
								<td>
								{% 	if(o[i].cnslCtnt !== null){ 	%}
										<a href="javascript:void(0);" onclick="cnsl({%=o[i].rcptNo%},'counsel-view');">
										<img src="{%='${resources}'%}/images/counselIcon.gif" title="{%=o[i].cnslCtnt%}"></a>
								{%	}	 					%}
								</td>
								<td>
								{% 	if(o[i].procCtnt !== null){ 	%}
										<a href="javascript:void(0);" onclick="proc({%=o[i].rcptNo%},'process-view');">
										<img src="{%='${resources}'%}/images/counselIcon.gif" title="{%=o[i].procCtnt%}"></a>
								{%	}	 					%}
								</td>
							</tr>
						{% } %}	
					</script>
					
					<!-- pagination -->
					<div class="pagination-wrap">
						<div id="pagination-tab1" class="pagination-inner"></div>
					</div>
					<!--// pagination -->
					
					<!-- btn -->
					<div class="btn-wrap">
						<button type="button" class="l-btn excelDown" id="caseExcel"><span class="icon-excel" title="엑셀 다운로드"><spring:message code="common.button.execel"/></span></button>
					</div>
					<!--// btn -->
					
					<!-- chart -->
					<div class="panel-box"  >
						<div class="tbl-info">
							<h4 class="cont-h4" title="상담 유형 건별 통계"><spring:message code="cam.chart1.header"/></h4>
						</div>
						<div id="impay-case-chart" class="chart-wrap"></div>
					</div>
					<!--// chart -->
				</div>
				
			</div>
			<!-- // tab 1 -->

			<!-- tab 2 -->
			<div id="tab2" class="tab-conts" style="display:none;">
				<!-- search -->
				<div class="search-box">
					<form id="tab2-frmSearch">
					<input type="hidden" id="tab2-pageIndex" name="pageParam.pageIndex" value="1"  >
					<input type="hidden" id="tab2-rowCount"  name="pageParam.rowCount"  value="5" >
					<fieldset>
					<legend title="유형별 조회"><spring:message code="cam.clmAnlsMng.type"/></legend>
					<table>
						<caption title="유형별 조회"><spring:message code="cam.clmAnlsMng.type"/></caption>
						<colgroup>
							<col style="width:12%">
							<col style="width:38%">
							<col style="width:12%">
							<col style="width:*">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" title="접수 일자"><spring:message code="cam.rcptDd"/></th>
								<td>
									<div class="datebox">
										<input type="text" name="stDate" id="stDateT2" class="input-text calendar" style="width:80px;" value="${currDate}">
										<a href="#none"><span class="icon-cal"></span></a>
									</div>
									<span class="hyphen">~</span>
									<div class="datebox">
										<input type="text" name="endDate" id="endDateT2" class="input-text calendar" style="width:80px;" value="${currDate}">
										<a href="#none"><span class="icon-cal"></span></a>
									</div>
								</td>
								<th scope="row" title="접수 유형"><spring:message code="cam.rcptType"/></th>
								<td>
									<select class="input-select" name="rcptMthdCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${rcptMthdCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="상담 유형"><spring:message code="cam.cnslType"/></th>
								<td>
									<select class="input-select" name="cnslTypCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${cnslTypCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row" title="상담 구분"><spring:message code="cam.cnslClf"/></th>
								<td>
									<select class="input-select" name="cnslClfUprCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${cnslClfCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="결제 조건"><spring:message code="cam.payClf"/></th>
								<td>
									<select class="input-select" name="payCndiCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${cnslPayClf}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row" title="통신사"><spring:message code="cam.commc1"/></th>
								<td>
									<select class="input-select" name="commcClf" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${commcClf}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="휴대폰 번호"><spring:message code="cam.mphnNo"/></th>
								<td>
									<input type="text" name="mphnNo" id="mphnNo" class="input-text digit" style="width:200px;"placeholder="'-' 없이 입력">
								</td>
								<th scope="row" title="이벤트"><spring:message code="cam.event"/></th>
								<td>
									<select class="input-select" name="cnslEvntCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${eventTyp}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="가맹점 조회"><spring:message code="cam.cpSearch"/></th>
								<td colspan="3">
									<input type="text" name="cpCd" id="cpCd" class="input-text searchCode" style="width:110px;" placeholder="코드">
									<input type="text" name="cpNm" id="cpNm" class="input-text" style="width:250px;" placeholder="이름">
									<button type="button" class="inbtn" id="findCp" title="검색" onclick="clmAnslMng.openTrgtCpSeachLayer()"><spring:message code="common.button.find"/></button>
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
					<button type="button" class="l-btn" id="searchBtn-Tab2" title="조회" ><spring:message code="common.button.search"/></button>
				</div>
				<!--// btn -->
				
				<!-- 조회 전 안내 문구-->
				<div class="search-info" id="tab2-info" title="원하시는 항목을 선택 하신 후 조회를 클릭 하시기 바랍니다.">
					<spring:message code="common.list.indication1"/>
				</div>
				<!--// 조회 전 안내 문구 -->
				
				<div class="panel-box"  id="impay-cam-typeList" style="display:none;">
					<div class="tbl-info info-type2">
						<!-- unit -->
					    <span class="unit" title="(단위 : 건)"><spring:message code="common.label.unit.count"/></span></span>
					    <!--// unit -->
					</div>
					<!-- grid -->
					<div class="tbl-grid-box txt-center">
						<table>
							<caption title="유형별 조회 리스트"><spirng:message code="cam.clmAnlsMng.type.list"/></caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:*">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="접수 일자"><spring:message code="cam.rcptDd"/></th>
									<th scope="col" title="상담 유형"><spring:message code="cam.cnslType"/></th>
									<th scope="col" title="이벤트"><spring:message code="cam.event"/></th>
									<th scope="col" title="상담 구분"><spring:message code="cam.cnslClf"/></th>
									<th scope="col" title="접수 유형"><spring:message code="cam.rcptType"/></th>
									<th scope="col" title="결제 조건"><spring:message code="cam.payClf"/></th>
									<th scope="col" title="통신사"><spring:message code="cam.commc1"/></th>
									<th scope="col" title="접수 건수"><spring:message code="cam.rcptCnt"/></th>
									<th scope="col" title="처리 건수"><spring:message code="cam.procY.count"/></th>
									<th scope="col" title="미처리 건수"><spring:message code="cam.procN.count"/></th>
								</tr>
							</thead>
							<tbody id="typeResult">
							</tbody>
						</table>
					</div>
					<!--// grid -->
					
					<%-- template --%>
					<script type="text/html" id="tmpl-typeList">	
						{% for (var i=0; i<o.length; i++) { %}
							<tr>	
								<td class="txt-center">{%=o[i].regDt%}</td>
								<td class="txt-center">{%=o[i].cnslTypNm%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].cnslEvntNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].cnslClfUprCdNm,'-')%}</td>
								<td class="txt-center">{%=o[i].rcptMthdNm%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].payCndiNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].commcClfNm,'-')%}</td>
								<td class="txt-right">{%=o[i].rcptCnt%}</td>
								<td class="txt-right">{%=o[i].procYCnt%}</td>
								<td class="txt-right">{%=o[i].procNCnt%}</td>
							</tr>
						{% } %}	
					</script>
					
					<!-- pagination -->
					<div class="pagination-wrap">
						<div id="pagination-tab2" class="pagination-inner"></div>
					</div>
					<!--// pagination -->
					
					<!-- btn -->
					<div class="btn-wrap">
						<button type="button" class="l-btn excelDown" id="typeExcel"><span class="icon-excel" title="엑셀 다운로드"><spring:message code="common.button.execel"/></span></button>
					</div>
					<!--// btn -->
					
					<!-- chart -->
					<div class="panel-box">
						<div class="tbl-info">
							<h4 class="cont-h4" title="유형 건별 통계"><spring:message code="cam.chart2.header"/></h4>
						</div>
						<div id="impay-type-chart" class="chart-wrap">
						</div>
					</div>
					<!--// chart -->								
				</div>		
				
			</div>
			<!-- // tab 2 -->

			<!-- tab 3 -->
			<div id="tab3" class="tab-conts" style="display:none;">
				<!-- search -->
				<div class="search-box">
					<form id="tab3-frmSearch">
					<input type="hidden" id="tab3-pageIndex" name="pageParam.pageIndex" value="1"  >
					<input type="hidden" id="tab3-rowCount"  name="pageParam.rowCount"  value="5" >
					<fieldset>
					<legend title="이관 접수건 조회"><spring:message code="cam.clmAnlsMng.tjur"/></legend>
					<table>
						<colgroup>
							<col style="width:12%">
							<col style="width:38%">
							<col style="width:12%">
							<col style="width:*">
						</colgroup>
						<caption title="이관 접수건 조회"><spring:message code="cam.clmAnlsMng.tjur"/></caption>
						<tbody>
							<tr>
								<th scope="row" title="접수 일자"><spring:message code="cam.rcptDd"/></th>
								<td colspan="3">
									<div class="datebox">
										<input type="text" name="stDate" id="stDateT3" class="input-text calendar" style="width:80px;" value="${currDate}">
										<a href="#none"><span class="icon-cal"></span></a>
									</div>
									<span class="hyphen">~</span>
									<div class="datebox">
										<input type="text" name="endDate" id="endDateT3" class="input-text calendar" style="width:80px;" value="${currDate}">
										<a href="#none"><span class="icon-cal"></span></a>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" title="이관 접수 구분"><spring:message code="cam.tjurRcptClf"/></th>
								<td>
									<select class="input-select" name="deptCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<option value="C" title="가맹점"><spring:message code="cam.cp"/></option>
										<c:forEach items="${tjur}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row" title="가맹점"><spring:message code="cam.cp"/></th>
								<td>
									<select class="input-select" id="adjEntp" name="cpCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${pgCdList}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" title="통신사"><spring:message code="cam.commc1"/></th>
								<td>
									<select class="input-select" name="commcClf" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${commcClf}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
										<c:forEach items="${commcClf1}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
										<c:forEach items="${commcClf2}" var="item" varStatus="status" >
	  										<option value="${item.cd}">${item.cdNm}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row" title="이벤트"><spring:message code="cam.event"/></th>
								<td>
									<select class="input-select" name="cnslEvntCd" style="width:280px;">
										<option value="" title="전체"><spring:message code="common.all"/></option>
										<c:forEach items="${eventTyp}" var="item" varStatus="status" >
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
					<button type="button" class="l-btn" id="searchBtn-Tab3" title="조회"><spring:message code="common.button.search"/></button>
				</div>
				<!--// btn -->
				
				<!-- 조회 전 안내 문구-->
				<div class="search-info" id="tab3-info" title="원하시는 항목을 선택 하신 후 조회를 클릭 하시기 바랍니다.">
					<spring:message code="common.list.indication1"/>
				</div>
				<!--// 조회 전 안내 문구 -->
				
				<div class="panel-box" id="impay-cam-tjurList" style="display:none;">
					<div class="tbl-info info-type2">
						<!-- unit -->
					    <span class="unit" title="(단위 : 건)"><spring:message code="common.label.unit.count"/></span>
					    <!--// unit -->
					</div>
					<!-- grid -->
					<div class="tbl-grid-box txt-center">
						<table>
							<caption title="이관 접수건 조회 리스트"><spring:message code="cam.clmAnlsMng.tjur.list"/></caption>
							<colgroup>
								<col style="width:5%">
								<col style="width:9%">
								<col style="width:10%">
								<col style="width:*">
								<col style="width:9%">
								<col style="width:9%">
								<col style="width:10%">
								<col style="width:9%">
								<col style="width:9%">
								<col style="width:9%">
								<col style="width:9%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="No.">No.</th>
									<th scope="col" title="접수 일자"><spring:message code="cam.rcptDd"/></th>
									<th scope="col" title="이관 구분"><spring:message code="cam.tjurClf"/></th>
									<th scope="col" title="가맹점 명"><spring:message code="cam.cpNm"/></th>
									<th scope="col" title="상담 유형"><spring:message code="cam.cnslType"/></th>
									<th scope="col" title="상담 구분"><spring:message code="cam.cnslClf"/></th>
									<th scope="col" title="이벤트"><spring:message code="cam.event"/></th>
									<th scope="col" title="통신사"><spring:message code="cam.commc1"/></th>
									<th scope="col" title="접수 건수"><spring:message code="cam.rcptCnt"/></th>
									<th scope="col" title="처리 건수"><spring:message code="cam.procY.count"/></th>
									<th scope="col" title="미처리 건수"><spring:message code="cam.procN.count"/></th>
								</tr>
							</thead>
							<tbody id="tjurResult">
							</tbody>
						</table>
					</div>
					<!--// grid -->
					
					<%-- template --%>
					<script type="text/html" id="tmpl-tjurList">	
						{% for (var i=0; i<o.length; i++) { %}
							<tr>	
								<td class="txt-center">{%=o[i].idx%}</td>
								<td class="txt-center">{%=o[i].regDt%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].deptNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].paySvcNm,'-')%}</td>
								<td class="txt-center">{%=o[i].cnslTypNm%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].cnslClfUprCdNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].cnslEvntNm,'-')%}</td>
								<td class="txt-center">{%=StringUtil.nvl(o[i].commcClfNm,'-')%}</td>
								<td class="txt-right">{%=o[i].rcptCnt%}</td>
								<td class="txt-right">{%=o[i].procYCnt%}</td>
								<td class="txt-right">{%=o[i].procNCnt%}</td>
							</tr>
						{% } %}	
					</script>
					
					
					<!-- pagination -->
					<div class="pagination-wrap">
						<div id="pagination-tab3" class="pagination-inner"></div>
					</div>
					<!--// pagination -->
					
					<!-- btn -->
					<div class="btn-wrap">
						<button type="button" class="l-btn excelDown" id="tjurExcel"><span class="icon-excel" title="엑셀 다운로드"><spring:message code="common.button.execel"/></span></button>
					</div>
					<!--// btn -->
					<!-- chart -->
					<div class="panel-box">
						<div class="tbl-info">
							<h4 class="cont-h4" title="이관 접수 건별 통계"><spring:message code="cam.chart3.header"/></h4>
						</div>
						<div id="impay-tjur-chart" class="chart-wrap">
						</div>
					</div>
					<!--// chart -->								
				</div>
				
			</div>
			<!-- // tab 3 -->
		</div>

	</div>
	<!--// contents -->	
	
	<form id="hiddenForm"  method="POST" action="${contextPath}/cnslMng/view">
		<input type="hidden" id="rcptNo" name="rcptNo" title="접수번호">
		<input type="hidden" id="hash" name="hash" title="상담/처리 구분">
		<input type="hidden" name="mnuId" value="CCM000">
		<input type="hidden" name="uprMnuId" value="CCM001">	
	</form>
</body>
</html>
