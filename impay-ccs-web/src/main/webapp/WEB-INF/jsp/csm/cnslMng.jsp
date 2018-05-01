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
	<title title="상담관리"><spring:message code="csm.cnslmng.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<style type="text/css">
		#tab3 .caption-li strong {color: red}
	</style>
	<script type="text/javascript" src="${resources}/js/ccs/csm/cnslMng.js"></script>
	<script type="text/javascript" src="${resources}/js/ccs/csm/cnslLayer.js"></script>
	<script type="text/javascript" src="${resources}/js/ccs/csm/rmsCnslMng.js"></script>
	<script type="text/javascript">
	var contextPath = "${contextPath}";
	var NO_REG_CUST = "9999999999";		// 미거래 고객 번호 : 다른 값을 대입하지 않도록 주의. IE 10 이하에선 const문은 에러 발생.
	// 가맹점 이관 포함 옵션
	var tjurCpOptions = '<option value="" title="선택"><spring:message code="common.select.select"/></option><option value="C" title="가맹점 이관"><spring:message code="csm.cnslmng.option.tjur.cp"/></option><option value="B" title="사업부 이관"><spring:message code="csm.cnslmng.option.tjur.dp"/></option>';
	// 가맹점 이관 제외 옵션
	var tjurBsOptions = '<option value="" title="선택"><spring:message code="common.select.select"/></option><option value="B" title="사업부 이관"><spring:message code="csm.cnslmng.option.tjur.dp"/></option>';
	// 거래명세서 체크리스트
	var rcptCheckList = [];
	
	$(function(){
		var messages = {
				"annoBordTitle" : "<spring:message code='csm.cnslmng.layer.title.annobord'/>",
				"receiptTitle" : "<spring:message code='csm.cnslmng.layer.title.receipt'/>",
				"payItcptRegistTitle" : "<spring:message code='csm.cnslmng.layer.title.payItcptRegist'/>",
				"payItcptUpdateTitle" : "<spring:message code='csm.cnslmng.layer.title.payItcptUpdate'/>",
				"paybackRcptTitle" : "<spring:message code='csm.cnslmng.layer.title.paybackRcpt'/>",
				"npayTitle" : "<spring:message code='csm.cnslmng.layer.title.npay'/>",
				"businessTjurTitle" : "<spring:message code='csm.cnslmng.layer.title.businessTjur'/>",
				"smsDocuInvenTitle" : "<spring:message code='csm.cnslmng.button.document'/>",
				"previewEmailTitle" : "<spring:message code='csm.cnslmng.layer.title.previewEmail'/>",
				"cpTitle" : "<spring:message code='common.label.cpSearch'/>",
				"noResult" : "<spring:message code='common.list.notice'/>",
				"noSelect" : "<spring:message code='csm.cnslmng.msg.noselect'/>",
				"clickReception" : "<spring:message code='csm.cnslmng.msg.clickRcpt'/>",
				"error" : "<spring:message code='common.error.message'/>",
				"excelDown" : "<spring:message code='common.button.excel.notice'/>",
				"excelDownLimit" : "<spring:message code='common.button.excel.limitNotice'/>",
				"tempSaveOk" : "<spring:message code='csm.cnslmng.msg.tempsaveOk'/>",
				"deleteOk" : "<spring:message code='csm.cnslmng.msg.deleteOk'/>",
				"saveOk" : "<spring:message code='csm.cnslmng.msg.saveOk'/>",
				"sendOk" : "<spring:message code='csm.cnslmng.msg.sendOk'/>",
				"tjurOk" : "<spring:message code='csm.cnslmng.comment4'/>",
				"reSearch" : "<spring:message code='csm.cnslmng.layer.itcpt.msg.re-search'/>",
				"noProcCtnt" : "<spring:message code='csm.cnslmng.msg.select.tjur.proc'/>",
				"tjurCpOptions" : tjurCpOptions,
				"tjurBsOptions" : tjurBsOptions,
				"validCnslTypCd" : "<spring:message code='csm.cnslmng.msg.select.cnslTypCd'/>",
				"validRcptMthdCd" : "<spring:message code='csm.cnslmng.msg.select.rcptMthdCd'/>",
				"validCnslCtnt" : "<spring:message code='csm.cnslmng.msg.input.cnslCtnt'/>",
				"tjurHistTitle" : "<spring:message code='csm.cnslmng.layer.tjurhist.title'/>"
		};
		var forms = {
					searchForm: "searchForm",
					cnslForm: "cnslForm",
					procForm: "procForm",
					sndRsltForm: "sndRsltForm",
					rmBlkReliefRegForm: "rmBlkReliefRegForm",
					fraudReliefRegForm: "fraudReliefRegForm"
		};
		cnslMng.init({
			contextPath: contextPath, 
			context: ".contents",
			forms: forms,
			messages: messages
		});
		cnslLayer.init({
			contextPath: contextPath, 
			context: ".contents",
			forms: forms,
			messages: messages
		});
		rmsCnslMng.init({
			contextPath: contextPath, 
			context: ".contents",
			forms: forms,
			messages: messages
		});
		
		/**************** 공통 ****************/
		// 공지사항 팝업
		$(".noticeBar").on({
			click : function(){
				cnslLayer.openAnnoDesc();
			},
			mouseenter : function(){
				$(this).find(".slider").stop();
			},
			mouseleave : function(){
				animateNotice($(this).find(".slider"));
			}
		});
		
		// 탭 헤더 선택 이벤트
		Common.tabs();
		
		//퀵메뉴 세번째 상담사 시나리오 팝업
		$("#quickMenu div.firstMenu").click(function(){
			var popObj = window.open('${contextPath}' + "/cnslMng/openCnslScenarioPopup", "small", "width=900,height=608,scrollbars=yes,menubars=yes");
			popArrObj.push(popObj);
		});

		//퀵메뉴 두번째 미납이력 및 가산금 부과내역 팝업
		$("#quickMenu div.secondMenu").click(function(e){
			//상담자 휴대폰번호
			var custMphnNo = $("#custMphnNo", ".contents").val();
			if(Common.isEmpty(custMphnNo)){
				alert("<spring:message code='csm.cnslmng.msg.input.mphnno'/>");
				return false;
			}
			var payrSeq = $("#payrSeq", ".contents").val();
			if (payrSeq === "") {
				alert("<spring:message code='csm.cnslmng.msg.select.custno'/>");
				return false;
			} else if (payrSeq === NO_REG_CUST) {
				alert("<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
				return false;
			} else {
		        e.preventDefault();
				var data = $("#payMphnId", ".contents").val();
		        var popObj = window.open('${contextPath}' + "/cnslMng/openNpayHistoryPopup/"+data, "small", "width=983,height=585");
				popArrObj.push(popObj);
			}
		});

		//퀵메뉴 세번째 환불 접수
		$("#quickMenu div.thirdMenu").click(function(e){
			e.preventDefault();
			cnslLayer.openPayBackRcpt();
		});
		
		//탭 이동
		$(".tabBar button").click(function(){
			var idx = $(this).closest("span").index();
			
			var $target = $(".tabBar").eq(idx + 1);
			var scrollTop = $target.offset().top;
			
			$("html, body").animate({
				scrollTop : scrollTop
			});
		});

		//하단 여백 더하기
		var $div = $("<div />");
		var $last = $(".tab-conts").last();
		$div.css({
			height : $(window).height() - $last.outerHeight()
		});
		$last.after($div);
		
		// 임시저장된 내용이 있으면 뿌려준다. (우선순위 : 타메뉴접근 > 임시저장)
		var tempCnsl = localStorage.getItem("tempCnsl");
		if(tempCnsl !== null && ("${hash}" === null || "${hash}" === "")){
			tempCnsl = JSON.parse(tempCnsl);
			// 상담내용
			$("#custMphnNo", ".contents").val(tempCnsl.tempMphnNo);
			$("#tempMphnNo", ".contents").val(tempCnsl.tempMphnNo);
			$("#payMphnId", ".contents").val(tempCnsl.tempPayMphnId);
			$("#payrSeq", ".contents").val(tempCnsl.tempPayrSeq);
			cnslMng.custInfoSearch();
			cnslMng.trdCmplSearch();
			cnslMng.cnslDtlSearch();
			$("#tempPayMphnId", ".contents").val(tempCnsl.tempPayMphnId);
			$("#tempPayrSeq", ".contents").val(tempCnsl.tempPayrSeq);
			$("#tempRcptNo", ".contents").val(tempCnsl.tempRcptNo);
			$("#tempTrdNo", ".contents").val(tempCnsl.tempTrdNo);
			$("#infoTrdTypNm", ".contents").text(tempCnsl.infoTrdTypNm);
			$("#infoTrdDt", ".contents").text(tempCnsl.infoTrdDt);
			$("#infoCommcClfNm", ".contents").text(tempCnsl.infoCommcClfNm);
			$("#infoCnclDt", ".contents").text(tempCnsl.infoCnclDt);
			$("#infoPayAmt", ".contents").text(tempCnsl.infoPayAmt);
			$("#infoAvlCncl", ".contents").text(tempCnsl.infoAvlCncl);
			$("#infoPaySvcNm", ".contents").text(tempCnsl.infoPaySvcNm);
			$("#infoTrdNo", ".contents").text(tempCnsl.infoTrdNo);
			$("#infoGodsNm", ".contents").text(tempCnsl.infoGodsNm);
			$("#infoTelNo", ".contents").text(tempCnsl.infoTelNo);
			$("#cnslClfUprCd", ".contents").val(tempCnsl.cnslClfUprCd);
			$("#cnslEvntCd", ".contents").val(tempCnsl.cnslEvntCd);
			$("#cnslTypCd", ".contents").val(tempCnsl.cnslTypCd);
			$("#rcptMthdCd", ".contents").val(tempCnsl.rcptMthdCd);
			$("#custTypFlg", ".contents").val(tempCnsl.custTypFlg);
			$("#cnslCtnt", ".contents").val(tempCnsl.cnslCtnt);
			if(tempCnsl.infoTrdNo !== null && tempCnsl.infoTrdNo !== ""){
				$("#tjurClfFlg", ".contents").html(tjurCpOptions);
			}
			// 처리내용
			$("#tempProcRcptNo", ".contents").val(tempCnsl.tempProcRcptNo);
			$("#tempProcTrdNo", ".contents").val(tempCnsl.tempProcTrdNo);
			$("#tempProcEntpId", ".contents").val(tempCnsl.tempProcEntpId);
			$("#tempProcCpCd", ".contents").val(tempCnsl.tempProcCpCd);
			$("input:radio[name='procYn']", ".contents").removeAttr("checked");
			$("input:radio[name='procYn'][value='" + tempCnsl.procYn + "']", ".contents").prop("checked", true);
			cnslMng.disabledTjurBtn(tempCnsl.procYn);
			$("#infoProcDt", ".contents").text(tempCnsl.procDt);
			$("#procDt", ".contents").val(tempCnsl.procDt);
			$("#infoLastChgDt", ".contents").text(tempCnsl.lastChgDt);
			$("#lastChgDt", ".contents").val(tempCnsl.lastChgDt);
			$("#infoLastChgr", ".contents").text(tempCnsl.lastChgr);
			$("#lastChgr", ".contents").val(tempCnsl.lastChgr);
			$("#tjurClfFlg", ".contents").val(tempCnsl.tjurClfFlg);
			$("#procCtnt", ".contents").val(tempCnsl.procCtnt);
			$("#tjurCtnt", ".contents").text(tempCnsl.tjurCtnt);
			$("#tjurYn", ".contents").val(tempCnsl.tjurYn);
			$("#tjurCd", ".contents").val(tempCnsl.tjurCd);
			// 화면 노출
			if(tempCnsl.infoTrdNo !== ""){
				$("div.trade-info", ".contents").show();
			}
			$(".tab-conts", ".contents").show();
			$("#noTab", ".contents").hide();
			window.setTimeout(function(){
				cnslMng.moveDisplay(tempCnsl.hash);
			}, 300);
		}
		
		// 임시저장 된 상담내용 값이 있으면 byte 수 표시
		if($("#cnslCtnt", ".contents").val() !== ""){
			$("#textCountCnslCtnt", ".contents").text(Common.getByteString($("#cnslCtnt", ".contents").val()));
		}
		
		<c:if test="${hash ne null}">
			/* 타메뉴에서 접근시 조회값 셋팅.
			 * - 스크립트 오류 방지를 위해 textarea의 개행문자는 반드시 <br/>로 되어있어야 함.
			 */
			cnslMng.totTrdCmplBindList(JSON.parse('${totTrdCmpl}'));
			cnslMng.cnslCtntBind(JSON.parse('${cnslCtnt}'));
			<c:if test='${cnslChg ne null}'>
				cnslMng.cnslChgBindList(JSON.parse('${cnslChg}'));
			</c:if>
			<c:if test='${notiResult ne null}'>
				var notiJson = JSON.parse('${notiResult}');
				cnslMng.notiRsltBind(notiJson);
				if(notiJson.rsltNotiMthd !== null && notiJson.rsltNotiMthd === "S"){
					$("#tab6 a[href='#tab6-2']", ".contents").removeClass("tab-selected");
					$("#tab6-2", ".contents").hide();
					$("#tab6 a[href='#tab6-1']", ".contents").addClass("tab-selected");
					$("#tab6-1", ".contents").show();
				} else if(notiJson.rsltNotiMthd !== null && notiJson.rsltNotiMthd === "M"){
					$("#tab6 a[href='#tab6-1']", ".contents").removeClass("tab-selected");
					$("#tab6-1", ".contents").hide();
					$("#tab6 a[href='#tab6-2']", ".contents").addClass("tab-selected");
					$("#tab6-2", ".contents").show();
				}
			</c:if>
			window.setTimeout(function(){
				cnslMng.moveDisplay("${hash}");
			}, 300);
		</c:if>
		
		/**************** 검색 ****************/
		// 최대 5년까지 검색
		$(".dateTerm", ".contents").datepicker("option", "minDate", "-5y");
		
		// 달력 선택시에도 라디오버튼 체크
		$("div.search-box div.datebox input, div.search-box div.datebox span", ".contents").click(function(){
			$("input:radio[name='selectDate']", ".contents").removeProp("checked");
			$(this).closest("div").prevAll("input:radio").first().prop("checked", true);
		});
		$("div.search-box button.dateTerm", ".contents").click(function(){
			$("input:radio[name='selectDate']", ".contents").removeProp("checked");
			$("input:radio[name='selectDate'][value='T']", ".contents").prop("checked", true);
		});
		
		// 기간별 버튼 이벤트
		$(".inbtn2", ".contents").click(function(){
			var id = $(this).attr("id");
			// 오늘날짜
			var today = "${today}";
			$("#endDt").val(today);
			
			today = today.replace(/\./g, "");
			var tempDt;
			var resultDt;
			if(id === "mon"){			// 이번달 처음날짜
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-1));
			} else if(id === "1mon") {	// 1달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-2), Number(today.substring(6,8))+1);
			} else if(id === "2mon") {	// 2달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-3), Number(today.substring(6,8))+1);
			} else if(id === "3mon") {	// 3달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-4), Number(today.substring(6,8))+1);
			} else if(id === "6mon") {	// 6달전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-7), Number(today.substring(6,8))+1);
			} else if(id === "1year") {	// 1년전
				tempDt = new Date(today.substring(0,4), (today.substring(4,6)-13), Number(today.substring(6,8))+1);
			}
			resultDt = tempDt.getFullYear().toString() + "." + Common.lpad((tempDt.getMonth() + 1), "0", "2") + "." + Common.lpad(tempDt.getDate(), "0", "2");
			$("#strtDt").val(resultDt);
		});
		
		// 가맹점 조회 버튼 이벤트
		$("#cpSearchBtn", ".contents").click(cnslLayer.openTrgtCpSeachLayer);
		
		// 가맹점 조회 초기화
		$("#code, #name", ".contents").keyup(function(e){
			var code = e.which ? e.which : e.keyCode;
			if($.trim($("#code").val()) === "" || $.trim($("#name").val()) === ""){
				$("#cpCd").val("");
			}
			if(code === 13 || code === 10){
				cnslLayer.openTrgtCpSeachLayer();
			}
		});
		
		// 휴대폰번호 입력 이벤트
		$("#custMphnNo", ".contents").keyup(function(e){
			var pattern = /[^\d]/g;
			var code = e.which ? e.which : e.keyCode;
			
			if($(this).val() !== "" && pattern.test($(this).val()) === true){
				alert("<spring:message code='csm.cnslmng.msg.onlynumber'/>");
				$(this).val("");
				$(this).focus();
			}
			// 휴대폰번호 입력시 기존 고객정보 초기화
			$("#payMphnId", ".contents").val("");
			$("#payrSeq", ".contents").val("");
			
			// 휴대폰번호 필드 엔터키 입력 이벤트
			if(code === 13 || code === 10){
				searchEvent();
			}
		});
		
		// 조회 버튼 이벤트
		$("#searchBtn", ".contents").click(searchEvent);
		
		// 접수 버튼 이벤트
		$("#rcptBtn", ".contents").click(function(){
			// 선택된 로우 활성화 취소
			if($("#trdCmplResult > tr", ".contents").size() > 1){
				$("#trdCmplResult > tr", ".contents").removeClass("row-selected");
			}
			if($("#cnslDtlResult > tr", ".contents").size() > 1){
				$("#cnslDtlResult > tr", ".contents").removeClass("row-selected");
			}
			if($("#cnslChgResult > tr", ".contents").size() > 1){
				$("#cnslChgResult > tr", ".contents").removeClass("row-selected");
			}
			cnslMng.viewCnslCtnt();
			// 미거래고객 접수 알림
			if($("#selPayrSeq option:selected", ".contents").val() === ""){
				alert("<spring:message code='csm.cnslmng.msg.nocust.search'/>");
			}
		});
		
		/**************** 조회탭 ****************/
		// 조회탭 하위 탭 클릭시 조회
		$("#tab1 > ul.tab-head a", ".contents").click(function(e){
			if($(this).attr("class") != "tab-selected"){
				var hash = $(this).attr("href");
				hash = hash.substring(hash.indexOf("-") + 1);
				
				if(hash === "1"){		// 거래 완료
					$("#pageIndex", ".contents").val("1");
					search();
				} else {
					if(hash === "2"){	// 거래 시도
						$("#pageIndex", ".contents").val("1");
						return searchOtherTabs(cnslMng.trdTrySearch, "tab1-2");
					} else if(hash === "3"){	// 청구/수납(누적)
						$("#pageIndex", ".contents").val("1");
						return searchOtherTabs(rmsCnslMng.searchChrgRcptAccList, "tab1-3");
					} else if(hash === "4"){	// 청구/수납(월별)
						return searchOtherTabs(rmsCnslMng.searchChrgRcptMonList, "tab1-4");
					} else if(hash === "5"){	// 결제 차단 등록/해제
						$("#pageIndex", ".contents").val("1");
						cnslMng.tabSelect("tab1", "tab1-5");
						cnslMng.payItcptSearch();
					} else if(hash === "6"){	// 미납횟수(회차별)
						return searchOtherTabs(rmsCnslMng.searchNpayCntList, "tab1-6");
					}
				}
			}
			e.preventDefault();
		});
		
		// 고객번호 선택 이벤트
		$("#selPayrSeq", ".contents").change(function(){
			var value = $(this).find("option:selected").val();
			if(value !== ""){
				var text = $(this).find("option:selected").text();
				$("#payrSeq", ".contents").val(text);
				$("#payMphnId", ".contents").val(value);
				cnslMng.tabSelect("tab1", "tab1-1");
				search();
			} else {
				cnslMng.clearCustInfo();
			}
		});
		
		// 거래완료건 로우 클릭시 이벤트
		$("#trdCmplResult", ".contents").on("click", "tr", function(){
			if($(this).find("td").size() > 1 && checkPayrSeq()){
				var id = $(this).data("id");
				// 상담이력, 상담관리이력 로우 활성화 취소
				$("#cnslDtlResult > tr, #cnslChgResult > tr", ".contents").removeClass("row-selected");
				// 선택 로우 활성화
				$(this).parent().children().removeClass("row-selected").each(function(){
					if($(this).data("id") === id){
						$(this).addClass("row-selected");
					}
				});
				if(id !== null && id !== undefined && id !== ""){
					$("#pageIndex", ".contents").val("1");
					cnslMng.trdSearch(id);
				}
				window.setTimeout(function(){
					cnslMng.moveDisplay("counsel-view");
				}, 300);
			}
		});
		
		// 고객번호, 거래명세서 컬럼에 거래완료건 클릭이벤트 버블방지
		$("#trdCmplResult", ".contents").on("click", "tr:even > td:nth-of-type(4), tr:even > td:last-of-type", function(e){
			e.stopPropagation();
		});
		
		// 고객번호 클릭 이벤트
		$("#trdCmplResult", ".contents").on("click", "tr:even > td:nth-of-type(4) > a", function(){
			var id = $(this).data("id");
			var text = $(this).text();
			$("#payrSeq", ".contents").val(text);
			$("#payMphnId", ".contents").val(id);
			cnslMng.tabSelect("tab1", "tab1-1");
			search();
		});
		
		// 거래완료 엑셀 다운로드
		$("#excelDown", ".contents").click(cnslMng.excelDown);
		
		// 거래명세서 체크박스 체크 이벤트
		$("#trdCmplResult", ".contents").on("click", "input:checkbox[name='trdNos']", function(){
			rcptCheckEvent($(this));
		});
		
		// 거래명세서 팝업 버튼 이벤트
		$("#popReceipt", ".contents").on("click", function(){
			if(rcptCheckList.length > 0){
				cnslLayer.openReceiptLayer("&trdNos=" + rcptCheckList.join("&trdNos="));
			} else {
				alert("<spring:message code='csm.cnslmng.msg.select.receipt'/>");
			}
		});
		
		// 체크박스 전체선택/해제
		$("#allChk", ".contents").on("click", function(){
			$("input:checkbox[name='trdNos']", ".contents").prop("checked", $(this).prop("checked"));
			$("input:checkbox[name='trdNos']", ".contents").each(function(){
				rcptCheckEvent($(this));
			});
		});
		
		// 거래시도 로우 클릭 이벤트
		$("#trdTryResult", ".contents").on("click", "tr", function(){
			if($(this).find("td").size() > 1 && checkPayrSeq()){
				var id = $(this).data("id");
				// 선택 로우 활성화
				$(this).parent().children().removeClass("row-selected").each(function(){
					if($(this).data("id") === id){
						$(this).addClass("row-selected");
					}
				});
				if(id !== null && id !== undefined && id !== ""){
					$("#pageIndex", ".contents").val("1");
					cnslMng.trdTrySelectSearch(id);
				}
				window.setTimeout(function(){
					cnslMng.moveDisplay("counsel-view");
				}, 300);
			}
		});
		
		// 거래시도 탭 엑셀 다운로드
		$("#trdTryExcelDown", ".contents").click(cnslMng.trdTryExcelDown);
		
		// 결제차단리스트 로우 클릭 이벤트
		$("#payItcptResult", ".contents").on("click", "tr", function(){
			var id = $(this).data("id");
			if(id !== null && id !== undefined && id !== ""){
				cnslLayer.openPayItcptUpdate(id);
			}
		});
		
		// 결제차단리스트 정렬 버튼 이벤트
		$(".icon-sort", ".contents").click(function(){
			if($("#payMphnId", ".contents").val() === ""){
				alert("<spring:message code='csm.cnslmng.msg.select.search'/>");
				return false;
			}
			// 조회된 값이 있으면 정렬조회
			if($("#payItcptResult", ".contents").children().length > 0){
				$(this).toggleClass("selected");
				if($("#sortOrder", ".contents").val() == "DESC"){
					$("#sortOrder", ".contents").val("ASC");
				} else {
					$("#sortOrder", ".contents").val("DESC");
				}
				cnslMng.payItcptSearch();
			}
		});
		
		// 결제차단신규 등록 이벤트
		$("#newPayItcptBtn", ".contents").click(cnslLayer.openPayItcptRegist);
		
		// 상담내용, 처리내용 이미지 클릭 이벤트
		$("#cnslDtlResult", ".contents").on("click", ".cnsl-ctnt,.proc-ctnt", function(){
			// 선택 로우 활성화
			var tr = $(this).closest("tr");
			tr.siblings().removeClass("row-selected");
			tr.addClass("row-selected");
			// 거래완료 리스트 로우 활성화 취소
			if($("#trdCmplResult > tr", ".contents").size() > 1){
				$("#trdCmplResult > tr", ".contents").removeClass("row-selected");
			}
			var id = $(this).data("id");
			var hash = $(this).data("hash");
			$("#chgListYn", ".contents").val("Y");
			cnslMng.cnslCtntSearch(id, hash);
		});
		
		/**************** 접수탭 ****************/
		// 상담내용 바이트 제한
		$("#cnslCtnt", ".contents").keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			$("#textCountCnslCtnt", ".contents").text(ctntByte);
			if(ctntByte > 400){
				$(this).val(Common.getStringToByte($(this).val(), 400));
				alert("<spring:message code='csm.cnslmng.msg.limit.byte' arguments='400'/>");
				$("#textCountCnslCtnt", ".contents").text(Common.getByteString($(this).val()));
			}
		});
		
		// 상담내용 임시저장
		$("#tempSaveCnslBtn", ".contents").click(cnslMng.tempSaveCnsl);
		
		// 상담내용 삭제
		$("#deleteCnslBtn", ".contents").click(function(){
			cnslMng.tempDeleteCnsl(tempCnsl);
		});
		
		// 상담내용 저장
		$("#saveCnslBtn", ".contents").click(cnslMng.saveCnslCtnt);
		
		// 상담내용 수정
		$("#updateCnslBtn", ".contents").click(cnslMng.updateCnslCtnt);
		
		/**************** RM탭 ****************/
		var $rmBlkReliefRegForm = $("#rmBlkReliefRegForm");
		var $fraudReliefRegForm = $("#fraudReliefRegForm");
		
		// RM탭 초기화
		(function() {
			var dateObj = rmsCnslMng.parseYmd("${today}");
			dateObj.setMonth(dateObj.getMonth() + 1);
			$rmBlkReliefRegForm.find("input[name=edDt]").datepicker("setDate", dateObj);
		})();
		
		// RM탭 하위 탭 클릭시 조회
		$("#tab3 > ul.tab-head a", ".contents").click(function(){
			if ($(this).attr("class") != "tab-selected") {
				var payrSeq = $("#payrSeq", ".contents").val();
				if (payrSeq === "") {
					alert("<spring:message code='csm.cnslmng.msg.select.custno'/>");
					return false;
				} else if (payrSeq === NO_REG_CUST) {
					alert("<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
					return false;
				}
				
				var dateObj = rmsCnslMng.parseYmd("${today}");
				dateObj.setMonth(dateObj.getMonth() + 1);
				$rmBlkReliefRegForm.find("input[name=edDt]").datepicker("setDate", dateObj);
				
				var hash = $(this).attr("href");
				hash = hash.substring(hash.indexOf("-") + 1);
				return searchTab3(hash);
			}
		});
		
		// RM 차단/해제 등록 > 적용상태가 해제일때만 해제사유, 종료일자 활성화
		$rmBlkReliefRegForm.find("select[name=applyStatus]").change(function(){
			var $relsRsnCd = $rmBlkReliefRegForm.find("select[name=relsRsnCd]").val("");
			var $edDt = $rmBlkReliefRegForm.find("input[name=edDt]");
			
			var applyStatus = $(this).val();
			if (applyStatus === "R") {
				$relsRsnCd.removeAttr("disabled");
				$edDt.removeAttr("disabled");
			} else {
				$relsRsnCd.attr("disabled", "disabled");
				$edDt.attr("disabled", "disabled");
			}
		}).trigger("change");
		
		//날짜 정규식
		$.validator.addMethod("validDate", function(value, element) {
			return this.optional(element) || (/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/.test(value) && !isNaN(Date.parse(value.replace(/\./g,'-'))));
		});
		
		// RM 차단/해제 등록 > 저장 폼 유효성 검사
		$rmBlkReliefRegForm.validate({
			rules: {
				reliefClfCd: { required: true },
				applyStatus: { required: true },
				blkReliefCd: { required: true },
				edDt: { validDate: true }
			},
			messages: {
				reliefClfCd: {
					required: "<spring:message code='rms.csm.rmsCnslMng.rmBlkReliefReg.form.valid.reliefClfCd.required'/>"
				},
				applyStatus: {
					required: "<spring:message code='rms.csm.rmsCnslMng.rmBlkReliefReg.form.valid.applyStatus.required'/>"
				},
				blkReliefCd: {
					required: "<spring:message code='rms.csm.rmsCnslMng.rmBlkReliefReg.form.valid.blkReliefCd.required'/>"
				},
				edDt: {
					validDate: "<spring:message code='csm.cnslmng.msg.check.date'/>"
				}
			},
			errorPlacement: function(error, element) {
				// do nothing
			},
			invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					alert(validator.errorList[0].message);
					validator.errorList[0].element.focus();
				}
			}
		});
		
		// RM 차단/해제 등록 > 저장 버튼 클릭시
		$rmBlkReliefRegForm.find("button[name=btnSave]").click(function() {
			var payrSeq = $("#payrSeq", ".contents").val();
			if (payrSeq === "") {
				alert("<spring:message code='csm.cnslmng.msg.select.custno'/>");
				return false;
			} else if (payrSeq === NO_REG_CUST) {
				alert("<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
				return false;
			}
			
			if (!$rmBlkReliefRegForm.valid()) {
				return false;
			}
			
			var applyStatus = $rmBlkReliefRegForm.find("select[name=applyStatus]").val();
			if (applyStatus === "R") {
				var $relsRsnCd = $rmBlkReliefRegForm.find("select[name=relsRsnCd]");
				if (!$relsRsnCd.val()) {
					alert("<spring:message code='rms.csm.rmsCnslMng.rmBlkReliefReg.form.valid.relsRsnCd.required'/>");
					$relsRsnCd.focus();
					return false;
				}
				
				var $edDt = $rmBlkReliefRegForm.find("input[name=edDt]");
				if (!$edDt.val()) {
					alert("<spring:message code='rms.csm.rmsCnslMng.rmBlkReliefReg.form.valid.edDt.required'/>");
					$edDt.focus();
					return false;
				}
				if ($edDt.val() < "${today}") {
					alert("<spring:message code='rms.csm.rmsCnslMng.rmBlkReliefReg.form.valid.edDt.isPast'/>");
					$edDt.focus();
					return false;
				}
			}
			
			rmsCnslMng.saveRmBlkRelief();
			
			return false;
		});
		
		// 불량고객 해제 등록 > 저장 폼 유효성 검사
		$fraudReliefRegForm.validate({
			rules: {
				relsRsnCd: { required: true }
			},
			messages: {
				relsRsnCd: {
					required: "<spring:message code='rms.csm.rmsCnslMng.fraudReliefReg.form.valid.relsRsnCd.required'/>"
				}
			},
			errorPlacement: function(error, element) {
				// do nothing
			},
			invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					alert(validator.errorList[0].message);
					validator.errorList[0].element.focus();
				}
			}
		});
		
		// 불량고객 해제 등록 > 저장 버튼 클릭시
		$fraudReliefRegForm.find("button[name=btnSave]").click(function() {
			var payrSeq = $("#payrSeq", ".contents").val();
			if (payrSeq === "") {
				alert("<spring:message code='csm.cnslmng.msg.select.custno'/>");
				return false;
			} else if (payrSeq === NO_REG_CUST) {
				alert("<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
				return false;
			}
			
			if (!rmsCnslMng.fraudBlkCount) {
				alert("<spring:message code='rms.csm.rmsCnslMng.fraudReliefReg.save.fraudRelief.noBlk'/>");
				return false;
			}
			
			if (!$fraudReliefRegForm.valid()) {
				return false;
			}
			
			rmsCnslMng.saveFraudRelief();
			
			return false;
		});
		
		/**************** 처리탭 ****************/
		// 처리내용 임시저장
		$("#tempSaveProcBtn", ".contents").click(cnslMng.tempSaveProc);
		
		// 처리내용 임시저장 삭제
		$("#deleteProcBtn", ".contents").click(function(){
			cnslMng.tempDeleteProc(tempCnsl);
		});
		
		// 처리완료
		$("#saveProcBtn", ".contents").click(cnslMng.updateProcCtnt);
		
		// 이관하기 버튼 이벤트
		$("#sendTjur", ".contents").click(function(){
			if($("#tempProcRcptNo", ".contents").val() === "") {
				alert("<spring:message code='csm.cnslmng.msg.tjur'/>");
				cnslMng.viewCnslCtnt();
			} else {
				var sel = $("#tjurClfFlg > option:selected").val();
				if(sel === ""){
					alert("<spring:message code='csm.cnslmng.msg.select.tjur'/>");
					return false;
				} else if(sel == "C"){
					// 가맹점 이관
					cnslMng.tjurProcCtnt();
				} else if(sel == "B"){
					// 사업부 이관 레이어
					cnslLayer.openBusinessTjur();
				}
			}
		});
		
		// 처리 상태 변경 이벤트
		$(":radio[name='procYn']", ".contents").change(function(){
			cnslMng.disabledTjurBtn($(this).val());
		});
		
		// 이관처리내역 버튼 클릭 이벤트
		$("#popTjurHist", ".contents").click(function(){
			var tjurCd = $("#tjurCd", ".contents").val();
			if(tjurCd !== ""){		// 이관건이 있는 경우
				if(tjurCd === "C"){	// 가맹점 이관
					cnslLayer.openTjurHistory();
				} else {			// 사업부 이관
					alert("<spring:message code='csm.cnslmng.msg.noCpTjur'/>");
				}
			} else {
				alert("<spring:message code='csm.cnslmng.msg.noTjur'/>");
			}
		});
		
		/**************** 관리탭 ****************/
		// 관리탭에서 상담내용, 처리내용 이미지 클릭 이벤트
		$("#cnslChgResult", ".contents").on("click", ".cnsl-ctnt,.proc-ctnt", function(){
			// 선택 로우 활성화
			var tr = $(this).closest("tr");
			tr.siblings().removeClass("row-selected");
			tr.addClass("row-selected");
			// 거래완료 리스트 로우선택 초기화
			if($("#trdCmplResult > tr", ".contents").size() > 1){
				$("#trdCmplResult > tr", ".contents").removeClass("row-selected");
			}
			var id = $(this).data("id");
			var hash = $(this).data("hash");
			$("#chgListYn", ".contents").val("N");
			cnslMng.cnslCtntSearch(id, hash);
		});
		
		// 관리탭에서 결과통보 클릭 이벤트
		$("#cnslChgResult", ".contents").on("click", "a.send-rslt", function(){
			// 결과방법구분값으로 sms/email 탭을 활성화 시킨다.
			var flg = $(this).data("flg");
			
			if(flg == "S"){
				// SMS 발송탭 활성화
				$(".sms", ".contents").prop("disabled", false);
				$(".sendEmail", ".contents").prop("disabled", true);
				$("#rsltNotiMthd", ".contents").val("S");
				if($("#tab6 a.tab-selected", ".contents").attr("href") !== "#tab6-1"){
					$("#tab6 a[href='#tab6-2']", ".contents").removeClass("tab-selected");
					$("#tab6-2", ".contents").hide();
					$("#tab6 a[href='#tab6-1']", ".contents").addClass("tab-selected");
					$("#tab6-1", ".contents").show();
				}
			} else if(flg == "M"){
				// E-mail 발송 활성화
				$(".sms", ".contents").prop("disabled", true);
				$(".sendEmail", ".contents").prop("disabled", false);
				$("#rsltNotiMthd", ".contents").val("M");
				if($("#tab6 a.tab-selected", ".contents").attr("href") !== "#tab6-2"){
					$("#tab6 a[href='#tab6-1']", ".contents").removeClass("tab-selected");
					$("#tab6-1", ".contents").hide();
					$("#tab6 a[href='#tab6-2']", ".contents").addClass("tab-selected");
					$("#tab6-2", ".contents").show();
				}
			}
			cnslMng.moveDisplay("result-view");
			
			// 결과통보내용을 조회한다.
			var seq = $(this).data("seq");
			cnslMng.notiRsltSearch(seq);
		});
		
		/**************** 결과통보탭 ****************/
		// 결과통보 방법탭 클릭 이벤트
		$("#tab6 > ul.tab-head a", ".contents").click(function(){
			if($(this).attr("class") != "tab-selected"){
				var hash = $(this).attr("href");
				hash = hash.substring(hash.indexOf("-") + 1);
				
				if(hash == "1"){		// SMS
					$(".sms", ".contents").prop("disabled", false);
					$(".sendEmail", ".contents").prop("disabled", true);
					$("#rsltNotiMthd", ".contents").val("S");
					// layer 버그로 인한 강제 탭 비활성화
					$("#tab6 a[href='#tab6-2']", ".contents").removeClass("tab-selected");
					$("#tab6-2", ".contents").hide();
				} else if(hash == "2"){	// 이메일
					$(".sms", ".contents").prop("disabled", true);
					$(".sendEmail", ".contents").prop("disabled", false);
					$("#rsltNotiMthd", ".contents").val("M");
					// layer 버그로 인한 강제 탭 비활성화
					$("#tab6 a[href='#tab6-1']", ".contents").removeClass("tab-selected");
					$("#tab6-1", ".contents").hide();
				}
			}
		});
		
		// 발송 버튼 클릭 이벤트
		$(".sendRsltBtn", ".contents").click(function(){
			if(confirm("<spring:message code='csm.cnslmng.msg.isSend'/>")){
				var testYn = $(this).data("test");
				// 고객정보 조회 검사
				if($("#tempRcptNo", ".contents").val() === ""){
					alert("<spring:message code='csm.cnslmng.msg.select.content.cnsl'/>");
					return false;
				}
				// 이메일 발송시 이메일 주소 검사
				if($(this).attr("class").indexOf("email") > -1 && testYn === "N"){
					if($.trim($("#email", ".contents").val()) === ""){
						alert("<spring:message code='csm.cnslmng.layer.receipt.msg.check.email'/>");
						return false;
					} else if(!( /([\w\.\-_]+)?\w+@[\w-_]+(\.\w+){1,}/igm.test($("#email", ".contents").val()) )){
						alert("<spring:message code='csm.cnslmng.layer.receipt.msg.check.emailtype'/>");
						return false;
					}
				}
				// 테스트 발송여부
				$("#testYn", ".contents").val(testYn);
				cnslMng.sendNotiRslt();
			}
		});
		
		// 문서보관함 레이어 팝업
		$("#documentInven", ".contents").click(cnslLayer.openSmsDocuInven);
		
		// 이메일 미리보기
		$(".previewEmail", ".contents").click(cnslLayer.openPreviewEmail);
	});
	
	$(window).load(function(){
		//공지 슬라이더
		var $holder = $(".noticeBar .holder");
		var $slider = $(".noticeBar .slider");

		var w = 0;
		$slider.children().each(function(){
			w += $(this).outerWidth(true);
		});
		w += parseInt($holder.css("marginLeft"));
		$slider.outerWidth(w);
		animateNotice($slider);
	});

	//공지 애니 재귀 함수
	function animateNotice($slider){
		var left = $slider.children().first().outerWidth(true);
		var duration = (left - (parseInt($slider.css("left")) * -1)) * 15;
		$slider.animate({
			left : left * -1
		},
		duration,
		"linear",
		function(){
			$(this).css("left", 0);
			$(this).children().first().appendTo($(this));
			animateNotice($(this));
		});
	}

	// 조회
	function search() {
		// 날짜 범위 검사
		$.validator.addMethod("dateCheck", function(value, element){
			return this.optional(element) || (Number($.trim(value).replace(/\./g,'')) - Number($.trim($("#strtDt", ".contents").val()).replace(/\./g,'')) >= 0)
		}, "<spring:message code='common.alert.termError'/>");
		
		// 날짜값 검사 - 월별
		$.validator.addMethod("todayMonthCheck", function(value, element){
			return this.optional(element) || (Number(value.replace(/\./g,'')) - Number("${todayMonth}".replace(/\./g,'')) <= 0)  
		}, "<spring:message code='common.alert.todayMonthError'/>");
		
		// 날짜값 검사 - 기간별
		$.validator.addMethod("todayCheck", function(value, element){
			return this.optional(element) || (Number(value.replace(/\./g,'')) - Number("${today}".replace(/\./g,'')) <= 0)
		}, "<spring:message code='common.alert.todayError'/>");
		
		// 날짜 유효성 검사
		$.validator.addMethod("date", function(value, element) {
			var agent = navigator.userAgent.toLowerCase();
		    if(agent.indexOf("msie") !== -1 || agent.search("trident") > -1 || agent.search("edge/") > -1 || agent.indexOf("safari") !== -1){
		    	return this.optional(element) || /^(19|20)\d{2}\.(0[1-9]|1[0-2])(\.(0[1-9]|[12][0-9]|3[0-1]))?/g.test(value);
		    }
		    return this.optional(element) || !/Invalid|NaN/.test(new Date(value));
		});
		
		// 연도 범위 검사
		$.validator.addMethod("limitYearCheck", function(value, element){
			return this.optional(element) || (Number("${todayMonth}".replace(/\./g,'')) - Number(value.replace(/\./g,'')) <= 500)
		}, "<spring:message code='csm.cnslmng.msg.limitYearCheck'/>");
		
		$("#searchForm", ".contents").validate({
	    	debug: true,
			onfocusout: false,
	        rules: {
	        	selectDate: { required : true },
	        	strtMon: {
	        		date: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "M";
	        			}
	        		},
	        		todayMonthCheck: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "M";
	        			}
	        		},
	        		limitYearCheck: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "M";
	        			}
	        		}
	        	},
	        	strtDt: {
	        		date: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "T";
	        			}
	        		},
	        		todayCheck: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "T";
	        			}
	        		}
	        	},
				endDt: {
	        		date: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "T";
	        			}
	        		},
	        		todayCheck: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "T";
	        			}
	        		},
	        		dateCheck: {
	        			depends : function(element){
	        				return $("input:radio[name='selectDate']:checked").val() === "T";
	        			}
	        		}
	        	},
				custMphnNo: { required : true, digits: true, rangelength: [9,11] }
			},
			messages: {
				selectDate: {
					required : "<spring:message code='csm.cnslmng.msg.select.date'/>"
				},
	        	strtMon: {
	        		date: "<spring:message code='csm.cnslmng.msg.check.date'/>"
				},
	        	strtDt: {
	        		date: "<spring:message code='csm.cnslmng.msg.check.date'/>"
				},
				endDt: {
					date: "<spring:message code='csm.cnslmng.msg.check.date'/>"
				},
				custMphnNo: {
					required : "<spring:message code='csm.cnslmng.msg.input.mphnno'/>",
					digits: "<spring:message code='csm.cnslmng.msg.onlynumber'/>",
					rangelength: "<spring:message code='csm.cnslmng.msg.check.length'/>"
				}
			},
	        invalidHandler: function(frm, validator) {
	        	var errors = validator.numberOfInvalids();
	        	if (errors) {
	                alert(validator.errorList[0].message);
	                validator.errorList[0].element.focus();
	            }
			},
	        submitHandler: function(frm) {
	        	rcptCheckList = [];
	        	cnslMng.totTrdCmplSearch();
			},
	        showErrors: function(errorMap, errorList) {
	        }
		});
		$("#searchForm", ".contents").submit();
	}

	// 조회탭 항목 내 다른탭 조회
	function searchOtherTabs(_submitfunction, tabIdx) {
		if($("#payMphnId", ".contents").val() === ""){
			alert("<spring:message code='csm.cnslmng.msg.select.custno'/>");
			return false;
		} else if($("#tempPayrSeq", ".contents").val() === NO_REG_CUST){
			alert("<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
			return false;
		}
		cnslMng.tabSelect("tab1", tabIdx);
		_submitfunction();
	}
	
	// 고객번호 선택여부검사
	function checkPayrSeq(){
		if($("#selPayrSeq option:selected", ".contents").val() === ""){
			alert("<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
			return false;
		} else {
			return true;
		}
	}
	
	// 최초 거래완료 조회 이벤트 함수
	function searchEvent(){
		$("#pageIndex", ".contents").val("1");
		$("#payMphnId", ".contents").val("");
		$("#payrSeq", ".contents").val("");
		utils.applyTrim("searchForm");
		cnslMng.tabSelect("tab1", "tab1-1");
		search();
	}
	
	// 거래명세서 체크리스트 추가 및 삭제 함수
	function rcptCheckEvent($obj){
		var len = rcptCheckList.length;
		if($obj.prop("checked") === true){
			var isExist = false;
			for(var i=0; i<len; i++){
				if(rcptCheckList[i] === $obj.val()){
					isExist = true;
					break;
				}
			}
			if(isExist === false){
				rcptCheckList[len] = $obj.val();
			}
		} else {
			for(var i=0; i<len; i++){
				if(rcptCheckList[i] === $obj.val()){
					rcptCheckList.splice(i, 1);
					break;
				}
			}
		}
	}
	</script>
	
	<script type="text/javascript">
	// RM탭(tab3) 결과 초기화
	function clearTab3Result(hash, message) {
		if (hash == "1") {			// RM 차단/해제 등록
			rmsCnslMng.clearRmBlkReliefResult(message);
		} else if (hash == "2") {	// 불량고객 해제 등록
			rmsCnslMng.clearFraudBlkResult(message);
		} else if (hash == "3") {	// 가맹점B/L 조회
			rmsCnslMng.clearCpBLInqResult(message);
		} else if (hash == "4") {	// RM 변경이력
			rmsCnslMng.clearRmChangeHisResult(message);
		}
	}
	
	// RM탭(tab3) 조회
	function searchTab3(hash) {
		var payrSeq = $("#payrSeq", ".contents").val();
		if (payrSeq === "") {
			clearTab3Result(hash, "<spring:message code='csm.cnslmng.msg.select.custno'/>");
			return false;
		} else if (payrSeq === NO_REG_CUST) {
			clearTab3Result(hash, "<spring:message code='csm.cnslmng.msg.nosearch.cust'/>");
			return false;
		}
		
		if (hash == "1") {			// RM 차단/해제 등록
			rmsCnslMng.searchRmBlkReliefList();
		} else if (hash == "2") {	// 불량고객 해제 등록
			rmsCnslMng.searchFraudBlkList();
		} else if (hash == "3") {	// 가맹점B/L 조회
			rmsCnslMng.searchCpBLInqList();
		} else if (hash == "4") {	// RM 변경이력
			rmsCnslMng.searchRmChangeHisList();
		}
	}
	</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
		
		<h3 class="cont-h3" title="상담관리"><spring:message code="csm.cnslmng.title"/></h3>
		<div class="noticeBar">
			<div class="floatLeft">
				<span class="noticeIcon"></span>
				<span class="noticeTitle" title="고객 센터 알림 사항"><spring:message code="csm.cnslmng.center.notice"/></span>
			</div>
			<%-- 부드러운 애니메이션을 위에서 div.slider 와 각 a 태그 사이에 공백이 없게 해 주시기 바랍니다. a 태그내의 공백은 상관 없습니다. --%>
			<div class="holder"><div class="slider"><c:if test="${notiList.total > 0}"><c:forEach items="${notiList.content}" var="list" varStatus="status" end="5"><span><span class="noticeContent<c:if test="${list.titlBldYn eq 'Y'}"> txt-em-green</c:if>"><c:out value="${status.count}"/>. <c:out value="${list.titl}"/></span><c:if test="${list.newYn eq 'Y'}"><span class="newIcon"></span></c:if><span class="noticeTime"><c:out value="${list.regDt}"/></span></span></c:forEach></c:if><c:if test="${notiList.total eq 0}"><div class="slider"><span><span class="noticeContent"></span></span></div></c:if></div></div>
		</div>
		<!-- search -->
		<div class="search-box">
			<form id="searchForm">
			<fieldset>
			<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"/>
			<input type="hidden" id="rowCount" name="pageParam.rowCount" value="10"/>
			<input type="hidden" id="sortOrder" name="pageParam.sortOrder" value="DESC"/>
			<input type="hidden" id="chgListYn" name="chgListYn" value=""/>
			<legend title="상담 관리 조회"><spring:message code="csm.cnslmng.search"/></legend>
			<table>
				<colgroup>
					<col style="width:12%">
					<col style="width:50%">
					<col style="width:12%">
					<col>
				</colgroup>
				<caption title="상담 관리 조회"><spring:message code="csm.cnslmng.search"/></caption>
				<tbody>
					<tr>
						<th scope="row" title="조회 기간"><spring:message code="common.inquery.term"/></th>
						<td colspan="3">
							<input type="radio" name="selectDate" id="date01" value="M" id="selectDateMonth"><label for="date01" title="월별"><spring:message code="common.label.month"/></label>
							<div class="datebox">
								<input type="text" name="strtMon" id="strtMon" class="input-text dateMonth ympicker date-group" style="width:80px;" value="${todayMonth}" readonly="readonly">
								<a href="#none"><span class="icon-cal"></span></a>
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="selectDate" id="date02" value="T" id="selectDateTerm" checked="checked"><label for="date02" title="기간별"><spring:message code="common.label.term"/></label>
							<div class="datebox">
								<input type="text" name="strtDt" id="strtDt" class="input-text dateTerm calendar date-group" style="width:80px;" value="${prevYear}" readonly="readonly">
								<a href="#none"><span class="icon-cal"></span></a>
							</div>
							<span class="hyphen">~</span>
							<div class="datebox">
								<input type="text" name="endDt" id="endDt" class="input-text dateTerm calendar date-group" style="width:80px;" value="${today}" readonly="readonly">
								<a href="#none"><span class="icon-cal"></span></a>
							</div>
							<button type="button" class="inbtn2 dateTerm" id="mon" title="당월"><spring:message code="common.button.month0"/></button>
							<button type="button" class="inbtn2 dateTerm" id="1mon" title="1개월"><spring:message code="common.button.month1"/></button>
							<button type="button" class="inbtn2 dateTerm" id="2mon" title="2개월"><spring:message code="common.button.month2"/></button>
							<button type="button" class="inbtn2 dateTerm" id="3mon" title="3개월"><spring:message code="common.button.month3"/></button>
							<button type="button" class="inbtn2 dateTerm" id="6mon" title="6개월"><spring:message code="common.button.month4"/></button>
							<button type="button" class="inbtn2 dateTerm" id="1year" title="1년"><spring:message code="common.button.month5"/></button>
						</td>
					</tr>
					<tr>
						<th scope="row" title="가맹점 조회"><spring:message code="common.label.cpSearch"/></th>
						<td>
							<input type="text" name="code" id="code" class="input-text searchCode" style="width:110px;" placeholder="<spring:message code='common.label.code'/>"/>
							<input type="text" name="name" id="name" class="input-text" style="width:250px;" placeholder="<spring:message code='common.label.codeNm'/>"/>
							<input type="hidden" name="cpCd" id="cpCd" value=""/>
							<button type="button" class="inbtn" id="cpSearchBtn" title="검색"><spring:message code="common.button.find"/></button>
						</td>
						<th title="상담자 휴대폰번호"><spring:message code="csm.cnslmng.label.tel"/></th>
						<td>
							<input type="text" name="custMphnNo" id="custMphnNo" class="input-text" maxlength="11" value="${mphnNo}" style="width:200px;" placeholder="<spring:message code='common.input.placeholder1'/>">
							<input type="hidden" name="payMphnId" id="payMphnId" value=""/>
							<input type="hidden" name="payrSeq" id="payrSeq" value=""/>
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
			<button type="button" class="l-btn" id="rcptBtn" style="display: none;" title="접수"><spring:message code="csm.cnslmng.buttom.rcpt"/></button>
			<button type="button" class="l-btn" id="searchBtn" title="조회"><spring:message code="common.button.search"/></button>
		</div>
		<!--// btn -->
		
		<%-- 타메뉴 조회 상담내용 --%>
		<c:set var="tempCnsl" value="${null}"/>
		<c:if test="${TEMP_CNSL ne null}">
			<c:set var="tempCnsl" value="${TEMP_CNSL}"/>
		</c:if>
		<%-- 타메뉴 조회 처리내용 --%>
		<c:set var="tempProc" value="${null}"/>
		<c:if test="${TEMP_PROC ne null}">
			<c:set var="tempProc" value="${TEMP_PROC}"/>
		</c:if>
		
		<div id="noTab" class="tab-conts">
			<div class="tabBar">
				<span><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<!-- 조회 전 안내 문구 -->
			<div class="search-info">
				<spring:message code="common.list.indication1"/>
			</div>
			<!--// 조회 전 안내 문구 -->
		</div>
		
		<div id="tab1" class="tab-conts" style="display: none;">
			<div class="tabBar">
				<span class="active"><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<dl class="custom-no">
				<dt title="고객번호"><spring:message code="csm.cnslmng.label.custno"/></dt>
				<dd>
					<select class="input-select" name="selPayrSeq" id="selPayrSeq" style="width:100px;">
					</select>
				</dd>
			</dl>
			<ul class="tab-head basic">
				<li><a href="#tab1-1" class="" title="거래 완료"><spring:message code="csm.cnslmng.tab.sub1"/></a></li>
				<li><a href="#tab1-2" class="" title="거래 시도"><spring:message code="csm.cnslmng.tab.sub2"/></a></li>
				<li><a href="#tab1-3" class="" title="청구/수납(누적)"><spring:message code="csm.cnslmng.tab.sub3"/></a></li>
				<li><a href="#tab1-4" class="" title="청구/수납(월별)"><spring:message code="csm.cnslmng.tab.sub4"/></a></li>
				<li><a href="#tab1-6" class="" title="미납횟수(회차별)"><spring:message code="csm.cnslmng.tab.sub8"/></a></li>
				<li><a href="#tab1-5" class="" title="결제 차단 등록/해제"><spring:message code="csm.cnslmng.tab.sub5"/></a></li>
			</ul>
			<div id="tab1-1">
				<div class="panel-box">
					<ul class="caption-li mrg-b5">
						<li title="상품과 상관없이 휴대폰 번호로 접수하시려면 상단 접수를 클릭하십시오."><spring:message code="csm.cnslmng.comment1"/></li>
					</ul>
					<div class="tbl-grid-box txt-center">
						<table>
							<caption title="거래 완료"><spring:message code="csm.cnslmng.header.title.tradecomplete"/></caption>
							<colgroup>
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:4%">
								<col style="width:7%">
								<col style="width:7%">
								<col style="width:8%">
								<col style="width:8%">
								<col style="width:8%">
								<col style="width:*">
								<col style="width:9%">
								<col style="width:7%">
								<col style="width:4.8%">
								<col style="width:4.8%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="결제 조건"><spring:message code="csm.cnslmng.th.conditaion.pay"/></th>
									<th scope="col" title="거래 일시"><spring:message code="csm.cnslmng.th.date.trade.br"/></th>
									<th scope="col" class="lineHeight12" title="취소 가능 여부" rowspan="2"><spring:message code="csm.cnslmng.th.cancel.yn.br"/></th>
									<th scope="col" title="고객 번호"><spring:message code="csm.cnslmng.th.custno"/></th>
									<th scope="col" title="통신사"><spring:message code="csm.cnslmng.th.commc"/></th>
									<th scope="col" title="거래 금액" rowspan="2"><spring:message code="csm.cnslmng.th.tradeamount"/></th>
									<th scope="col" title="할인 금액" rowspan="2"><spring:message code="csm.cnslmng.th.saleamount"/></th>
									<th scope="col" title="청구 금액" rowspan="2"><spring:message code="csm.cnslmng.th.reqtradeamount"/></th>
									<th scope="col" title="가맹점명"><spring:message code="csm.cnslmng.th.cpname"/></th>
									<th scope="col" title="고객 상태"><spring:message code="csm.cnslmng.th.status.cust"/></th>
									<th scope="col" title="결제 승인(구매)번호" rowspan="2"><spring:message code="csm.cnslmng.th.tradeno.br"/></th>
									<th scope="col" title="인증 방법" rowspan="2"><spring:message code="csm.cnslmng.th.authflg.br"/></th>
									<th scope="col" class="lineHeight12" title="거래 명세서" rowspan="2"><spring:message code="csm.cnslmng.th.receipt.br"/><br><input type="checkbox" value="" id="allChk" name=""></th>
								</tr>
								<tr>
									<th scope="col" title="결제 상태"><spring:message code="csm.cnslmng.th.status.pay"/></th>
									<th scope="col" title="취소 일시"><spring:message code="csm.cnslmng.th.date.cancel.br"/></th>
									<th scope="col" title="생년 월일"><spring:message code="csm.cnslmng.th.birthday"/></th>
									<th scope="col" title="휴대폰 번호"><spring:message code="csm.cnslmng.th.telno.br"/></th>
									<th scope="col" title="상품명"><spring:message code="csm.cnslmng.th.goodsname"/></th>
									<th scope="col" title="고객 센터"><spring:message code="csm.cnslmng.th.center"/></th>
								</tr>
							</thead>
							<tbody id="trdCmplResult">
							</tbody>
						</table>
						<div class="pagination-wrap" id="trdCmplPaginationWrap">
							<div class="pagination-inner" id="trdCmplPaginationInner">
							</div>
						</div>
					</div>
					<!--// grid -->
					
					<%-- template --%>
					<script type="text/html" id="tmpl-trdCpmlList">
					{% var chkLen = rcptCheckList.length; %}
					{% for(var i=0; i<o.length; i++) {
							var isCheck = false;
							for(var j=0; j<chkLen; j++){
								if(rcptCheckList[j] === o[i].trdNo){
									isCheck = true;
									break;
								}
							}
					%}
					<tr data-id="{%=o[i].trdNo%}">
						<td>{%=o[i].trdTypNm%}</td>
						<td>{%=o[i].trdDt%}</td>
						<td rowspan="2">{%=o[i].avlCncl%}</td>
						<td>
							<a href="javascript:void(0);" data-id="{%=o[i].payMphnId%}">{%=o[i].payrSeq%}</a>
						</td>
						<td>{%=o[i].commcClfNm%}</td>
						<td class="txt-right" rowspan="2">{%=Common.formatMoney(o[i].payAmt)%}</td>
						<td class="txt-right" rowspan="2">{%=Common.formatMoney(o[i].saleAmt)%}</td>
						<td class="txt-right" rowspan="2">{%=Common.formatMoney(o[i].acPayAmt)%}</td>
						<td class="txt-left">{%=o[i].paySvcNm%}</td>
						<td>{%#o[i].smplPayNm%}</td>
						<td rowspan="2">{%=o[i].trdNo%}</td>
						<td rowspan="2">{%=o[i].authtiClfFlgNm%}</td>
						<td rowspan="2">
						{% if(isCheck === true) { %}
							<input type="checkbox" value="{%=o[i].trdNo%}" name="trdNos" checked="checked">
						{% } else { %}
							<input type="checkbox" value="{%=o[i].trdNo%}" name="trdNos">
						{% } %}
						</td>
					</tr>
					<tr data-id="{%=o[i].trdNo%}">
						<td>{%#o[i].payStat%}</td>
						<td>{%=StringUtil.nvl(o[i].cnclDt, '')%}</td>
						<td>{%=o[i].brthYmd%}</td>
						<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
						<td class="txt-left">{%=o[i].godsNm%}</td>
						<td>{%=StringUtil.nvl(o[i].telNo, '')%}</td>
					</tr>
					{% } %}
					</script>

					<!-- btn -->
					<div class="btn-wrap">
						<button type="button" class="l-btn" id="excelDown"><span class="icon-excel" title="엑셀 다운로드"><spring:message code="common.button.execel"/></span></button> <button type="button" class="l-btn" id="popReceipt" title="거래 명세서 발송">거래 명세서 발송</button>
					</div>
					<!--// btn -->
				</div>
				
				<div class="tbl-info cnsl-info">
					<h4 class="cont-h4" title="전체 상담 이력"><spring:message code="csm.cnslmng.header.title.totalcounsellist"/></h4>
				</div>
				<div class="tbl-grid-box txt-center cnsl-info">
					<table>
						<caption title="전체 상담 이력"><spring:message code="csm.cnslmng.header.title.totalcounsellist"/></caption>
						<colgroup>
							<col style="width:4%">
							<col style="width:7.5%">
							<col style="width:8%">
							<col style="width:7.5%">
							<col>
							<col style="width:5%">
							<col style="width:4%">
							<col style="width:8%">
							<col style="width:4%">
							<col style="width:5%">
							<col style="width:4%">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="csm.cnslmng.th.no"/></th>
								<th scope="col" title="접수 일시"><spring:message code="csm.cnslmng.th.date.rcpt.dtl"/></th>
								<th scope="col" title="상담자"><spring:message code="csm.cnslmng.th.cnslmanager"/></th>
								<th scope="col" title="접수 번호"><spring:message code="csm.cnslmng.th.rcptno"/></th>
								<th scope="col" title="상담 구분"><spring:message code="csm.cnslmng.th.cnslgubun"/></th>
								<th scope="col" title="상담 상태"><spring:message code="csm.cnslmng.th.status.cnsl.br"/></th>
								<th scope="col" title="상담 내용"><spring:message code="csm.cnslmng.th.content.cnsl.br"/></th>
								<th scope="col" title="처리 일자"><spring:message code="csm.cnslmng.th.date.proc"/></th>
								<th scope="col" title="처리 내용"><spring:message code="csm.cnslmng.th.content.proc.br"/></th>
								<th scope="col" title="변경자"><spring:message code="csm.cnslmng.th.changer"/></th>
								<th scope="col" title="이관"><spring:message code="csm.cnslmng.th.tjur"/></th>
								<th scope="col" title="가맹점"><spring:message code="csm.cnslmng.th.cp"/></th>
							</tr>
						</thead>
						<tbody id="cnslDtlResult">
						</tbody>
					</table>
					
					<div class="pagination-wrap">
						<div class="pagination-inner" id="cnslDtlPaginationInner">
						</div>
					</div>
				</div>
			</div>
			<%-- template --%>
			<script type="text/html" id="tmpl-cnslDtlList">
			{% var resources = '${resources}'; %}
			{% for(var i=0; i<o.length; i++) { %}
			<tr {% if(o[i].rcptNo === cnslMng.getRowParam().rcptNo){ %}class="row-selected"{% } %} data-id="{%=cnslMng.getRowParam().rcptNo%}">
				<td>{%=o[i].rnum%}</td>
				<td>{%=o[i].regDt%}</td>
				<td>{%=o[i].regr%}</td>
				<td><a href="javascript:void(0);" class="cnsl-ctnt" title="<spring:message code='csm.cnslmng.data.title.cnsl'/>" data-hash="counsel-view" data-id="{%=o[i].rcptNo%}">{%=o[i].rcptNo%}</a></td>
				<td class="txt-left">{%=StringUtil.nvl(o[i].cnslClfUprNm, "")%}</td>
				<td>{%=o[i].procYnNm%}</td>
				<td><a href="javascript:void(0);" class="cnsl-ctnt" title="<spring:message code='csm.cnslmng.data.title.cnsl'/>" data-hash="counsel-view" data-id="{%=o[i].rcptNo%}"><img src="{%=resources%}/images/counselIcon.gif" alt="<spring:message code='csm.cnslmng.th.content.cnsl'/>"></a></td>
				{%	if(Common.getIsNull(o[i].procDd) === false){ %}
				<td>{%=o[i].procDd.substring(0,4) + "." + o[i].procDd.substring(4,6) + "." + o[i].procDd.substring(6,8)%}</td>
				{% 	} else { %}
				<td></td>
				{%	} %}
				{%	if(Common.getIsNull(o[i].procDd) === false){ %}
				<td><a href="javascript:void(0);" class="proc-ctnt" title="<spring:message code='csm.cnslmng.data.title.proc'/>" data-hash="process-view" data-id="{%=o[i].rcptNo%}"><img src="{%=resources%}/images/counselIcon2.gif" alt="<spring:message code='csm.cnslmng.th.content.proc'/>"></a></td>
				{% 	} else { %}
				<td></td>
				{%	} %}
				<td>{%=o[i].lastChgr%}</td>
				<td>{%=StringUtil.nvl(o[i].cnslTjurNm, '')%}</td>
				<td class="txt-left">{%=StringUtil.nvl(o[i].paySvcNm, '')%}</td>
			</tr>
			<tr class="counselTr">
				<td colspan="12"><span class="tag"><spring:message code="csm.cnslmng.th.content.cnsl"/></span>{%=Common.nvl(o[i].cnslCtnt,"").replace(/<br\/>/g, "\r\n")%}</td>
			</tr>
				{% if(Common.getIsNull(o[i].procCtnt) === false){ %}
				<tr class="counselTr">
					<td colspan="12"><span class="tag"><spring:message code="csm.cnslmng.th.content.proc"/></span>{%=o[i].procCtnt.replace(/<br\/>/g, "\r\n")%}</td>
				</tr>
				{% } %}
			{% } %}
			</script>
			
			<div id="tab1-2">
				<div class="panel-box">
					<div class="tbl-info info-type2">
						<h4 class="cont-h4" title="조회 결과"><spring:message code="csm.cnslmng.header.result"/></h4>
						<!-- unit -->
						<span class="unit" title="(단위 : 원)"><spring:message code="common.label.unit"/></span>
						<!--// unit -->
					</div>
					<div class="tbl-grid-box txt-center">
						<table>
							<caption title="거래 시도 리스트"><spring:message code="csm.cnslmng.header.paytry"/></caption>
							<colgroup>
								<col width="4%">
								<col width="6.5%">
								<col width="7%">
								<col width="5.5%">
								<col width="8%">
								<col width="9%">
								<col width="8%">
								<col>
								<col width="4%">
								<col width="4%">
								<col>
								<col width="4%">
								<col width="8%">
								<col width="4.8%">
								<col width="7%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="결제 조건"><spring:message code="csm.cnslmng.th.conditaion.pay.br"/></th>
									<th scope="col" title="거래 일시"><spring:message code="csm.cnslmng.th.date.trade"/></th>
									<th scope="col" title="생년 월일"><spring:message code="csm.cnslmng.th.birthday"/></th>
									<th scope="col" title="통신사"><spring:message code="csm.cnslmng.th.commc"/></th>
									<th scope="col" title="고객 번호"><spring:message code="csm.cnslmng.th.custno"/></th>
									<th scope="col" title="휴대폰 번호"><spring:message code="csm.cnslmng.th.telno"/></th>
									<th scope="col" title="거래 금액"><spring:message code="csm.cnslmng.th.tradeamount"/></th>
									<th scope="col" title="가맹점명"><spring:message code="csm.cnslmng.th.cpname"/></th>
									<th scope="col" title="내부 오류"><spring:message code="csm.cnslmng.th.error.inner"/></th>
									<th scope="col" title="외부 오류"><spring:message code="csm.cnslmng.th.error.outer"/></th>
									<th scope="col" title="오류 메세지"><spring:message code="csm.cnslmng.th.error.msg"/></th>
									<th scope="col" title="전문 코드"><spring:message code="csm.cnslmng.th.sendcode.br"/></th>
									<th scope="col" title="결제 승인(구매)번호"><spring:message code="csm.cnslmng.th.tradeno.br"/></th>
									<th scope="col" title="인증 방법"><spring:message code="csm.cnslmng.th.authflg.br"/></th>
									<th scope="col" title="고객 센터"><spring:message code="csm.cnslmng.th.center"/></th>
								</tr>
							</thead>
							<tbody id="trdTryResult">
							</tbody>
						</table>
						<div class="pagination-wrap" id="trdTryPaginationWrap" style="display: none;">
							<div class="pagination-inner" id="trdTryPaginationInner">
							</div>
						</div>
					</div>
					<!--// grid -->
				</div>
				<%-- template --%>
				<script type="text/html" id="tmpl-trdTryList">
				{% for(var i=0; i<o.length; i++) { %}
				<tr data-id="{%=o[i].trdNo%}">
					<td>{%=o[i].trdTypNm%}</td>
					<td>{%=o[i].trdDt%}</td>
					<td>{%=o[i].brthYmd%}</td>
					<td>{%=o[i].commcClfNm%}</td>
					<td>{%=o[i].payrSeq%}</td>
					<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td class="txt-right">{%=Common.formatMoney(o[i].payAmt)%}</td>
					<td class="txt-left">{%=o[i].paySvcNm%}</td>
					<td>{%=o[i].inRsltCd%}</td>
					<td>{%=o[i].extrRsltCd%}</td>
					<td class="txt-left">{%=StringUtil.nvl(o[i].inRsltMsg, "")%}</td>
					<td>{%=o[i].transCdNm%}</td>
					<td>{%=o[i].trdNo%}</td>
					<td>{%=o[i].authtiClfFlgNm%}</td>
					<td>{%=StringUtil.nvl(o[i].telNo, '')%}</td>
				</tr>
				{% } %}
				</script>

				<!-- btn -->
				<div class="btn-wrap">
					<button type="button" class="l-btn" id="trdTryExcelDown"><span class="icon-excel" title="엑셀 다운로드"><spring:message code="common.button.execel"/></span></button>
				</div>
				<!--// btn -->
			</div>
			<div id="tab1-3">
				<ul class="caption-li mrg-b5">
					<li title='<spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.comment"/>'><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.comment"/></li>
				</ul>
				<div class="tbl-grid-box txt-center">
					<table>
						<caption title="청구/수납(누적)"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.caption"/></caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:14%">
							<col style="width:14%">
							<col style="width:*">
							<col style="width:16%">
							<col style="width:16%">
							<col style="width:16%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.idx"/></th>
								<th scope="col" title="고객번호"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.payrSeq"/></th>
								<th scope="col" title="통신사"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.commcClfNm"/></th>
								<th scope="col" title="휴대폰번호"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.mphnNo"/></th>
								<th scope="col" title="청구금액"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.billAmt"/></th>
								<th scope="col" title="수납금액"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.rcptAmt"/></th>
								<th scope="col" title="미납금액"><spring:message code="rms.csm.rmsCnslMng.chrgRcptAcc.list.th.npayAmt"/></th>
							</tr>
						</thead>
						<tbody id="chrgRcptAccResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-chrgRcptAccList">	
				{% for (var i = 0; i < o.length; i++) { %}
				<tr>
					<td>{%=o[i].idx%}</td>
					<td>{%=o[i].payrSeq%}</td>
					<td>{%=o[i].commcClfNm%}</td>
					<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td class="txt-right">{%=Common.formatMoney(o[i].billAmt)%}</td>
					<td class="txt-right">{%=Common.formatMoney(o[i].rcptAmt)%}</td>
					<td class="txt-right">{%=Common.formatMoney(o[i].npayAmt)%}</td>
				</tr>
				{% } %}
				</script>
			</div>
			<div id="tab1-4">
				<ul class="caption-li mrg-b5">
					<li title="<spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.comment"/>"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.comment"/></li>
				</ul>
				<div class="tbl-grid-box txt-center">
					<table>
						<caption title="청구/수납(월별)"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.caption"/></caption>
						<colgroup>
							<col style="width:4%">
							<col style="width:13%">
							<col style="width:13%">
							<col style="width:13%">
							<col style="width:*">
							<col style="width:15%">
							<col style="width:15%">
							<col style="width:15%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.idx"/></th>
								<th scope="col" title="고객번호"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.payrSeq"/></th>
								<th scope="col" title="미납년월"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.payYm"/></th>
								<th scope="col" title="통신사"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.commcClfNm"/></th>
								<th scope="col" title="휴대폰번호"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.mphnNo"/></th>
								<th scope="col" title="청구금액"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.billAmt"/></th>
								<th scope="col" title="수납금액"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.rcptAmt"/></th>
								<th scope="col" title="미납금액"><spring:message code="rms.csm.rmsCnslMng.chrgRcptMon.list.th.npayAmt"/></th>
							</tr>
						</thead>
						<tfoot id="chrgRcptMonTotal">
						</tfoot>
						<tbody id="chrgRcptMonResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-chrgRcptMonList">	
				{% for (var i = 0; i < o.length; i++) { %}
					{% if (o[i].payrSeq) { %}
					<tr>
						<td>{%=o[i].idx%}</td>
						<td>{%=o[i].payrSeq%}</td>
						<td>{%=o[i].payYm%}</td>
						<td>{%=o[i].commcClfNm%}</td>
						<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
						<td class="txt-right">{%=Common.formatMoney(o[i].billAmt)%}</td>
						<td class="txt-right">{%=Common.formatMoney(o[i].rcptAmt)%}</td>
						<td class="txt-right">{%=Common.formatMoney(o[i].npayAmt)%}</td>
					</tr>
					{% } %}
				{% } %}
				</script>
				
				<script type="text/html" id="tmpl-chrgRcptMonTotal">	
				{% for (var i = 0; i < o.length; i++) { %}
					{% if (!o[i].payrSeq) { %}
					<tr>
						<td colspan="5" title="합계"><spring:message code="common.label.totalSum2"/></td>
						<td class="txt-right">{%=Common.formatMoney(o[i].billAmt)%}</td>
						<td class="txt-right">{%=Common.formatMoney(o[i].rcptAmt)%}</td>
						<td class="txt-right">{%=Common.formatMoney(o[i].npayAmt)%}</td>
					</tr>
					{% } %}
				{% } %}
				</script>
			</div>
			<div id="tab1-6">
				<div class="tbl-info cnsl-info">
					<h5 class="cont-h5" title="1. 수납 회차별 미납횟수"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.title"/></h5>
				</div>
				<ul class="caption-li mrg-b5">
					<li title="<spring:message code="rms.csm.rmsCnslMng.dftNum.list.comment"/>"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.comment"/></li>
				</ul>
				<div class="tbl-grid-box txt-center">
					<table>
						<caption title="수납 회차별 미납횟수"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.caption"/></caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:14%">
							<col style="width:14%">
							<col style="width:*">
							<col style="width:16%">
							<col style="width:16%">
							<col style="width:16%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" rowspan="2" title="No."><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.idx"/></th>
								<th scope="col" rowspan="2" title="고객번호"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.payrSeq"/></th>
								<th scope="col" rowspan="2" title="통신사"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.commcClfNm"/></th>
								<th scope="col" rowspan="2" title="휴대폰번호"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.mphnNo"/></th>
								<th scope="col" colspan="3" title="수납 회차별 미납횟수"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.npayCnt"/></th>
							</tr>
							<tr>
								<th scope="col" title="3회차"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.mnts3NpayCnt"/></th>
								<th scope="col" title="6회차"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.mnts6NpayCnt"/></th>
								<th scope="col" title="12회차"><spring:message code="rms.csm.rmsCnslMng.dftNum.list.th.mnts12NpayCnt"/></th>
							</tr>
						</thead>
						<tbody id="npayCntListResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-npayCntList">	
				{% for (var i = 0; i < o.length; i++) { %}
				<tr>
					<td>{%=o[i].idx%}</td>
					<td>{%=o[i].payrSeq%}</td>
					<td>{%=o[i].commcClfNm%}</td>
					<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td class="txt-right" data-npayCnt-mnts="MNTS3">{%=Common.formatMoney(o[i].mnts3NpayCnt)%}</td>
					<td class="txt-right" data-npayCnt-mnts="MNTS6">{%=Common.formatMoney(o[i].mnts6NpayCnt)%}</td>
					<td class="txt-right" data-npayCnt-mnts="MNTS12">{%=Common.formatMoney(o[i].mnts12NpayCnt)%}</td>
				</tr>
				{% } %}
				</script>
				
				<div style="margin-top:20px;">
					<spring:message code="rms.csm.rmsCnslMng.dftNum.definition.title"/>
				</div>
				<div class="tbl-grid-box" style="width:750px; margin-top:10px; border:solid 1px #bbbbbb;">
					<table>
						<caption title=""></caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:40%">
							<col style="*">
						</colgroup>
						<tbody>
							<tr>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.curMon.display"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.curMon.mean"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.curMon.exam"/></td>
							</tr>
							<tr>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts3.display"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts3.mean"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts3.exam"/></td>
							</tr>
							<tr>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts6.display"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts6.mean"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts6.exam"/></td>
							</tr>
							<tr>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts12.display"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts12.mean"/></td>
								<td><spring:message code="rms.csm.rmsCnslMng.dftNum.definition.mnts12.exam"/></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div id="npayAmtDetailContainer" style="margin-top:20px; display:none;">
					<div class="tbl-info cnsl-info">
						<h5 class="cont-h5" title="2. 미납금액 상세"><spring:message code="rms.csm.rmsCnslMng.dftNum.detail.title"/></h5>
					</div>
					<div class="tbl-grid-box txt-center" style="width:500px;">
						<table>
							<caption title="미납금액 상세"><spring:message code="rms.csm.rmsCnslMng.dftNum.detail.caption"/></caption>
							<colgroup>
								<col style="width:33%">
								<col style="width:33%">
								<col style="width:33%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="청구금액"><spring:message code="rms.csm.rmsCnslMng.dftNum.detail.th.billAmt"/></th>
									<th scope="col" title="수납금액"><spring:message code="rms.csm.rmsCnslMng.dftNum.detail.th.rcptAmt"/></th>
									<th scope="col" title="미납금액"><spring:message code="rms.csm.rmsCnslMng.dftNum.detail.th.npayAmt"/></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td id="npayAmtDetail_billAmt" class="txt-right"></td>
									<td id="npayAmtDetail_rcptAmt" class="txt-right"></td>
									<td id="npayAmtDetail_npayAmt" class="txt-right"></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div id="tab1-5">
				<ul class="caption-li mrg-b5">
					<li title="수정을 하시려면 해당 내용을 클릭, 신규등록은 하단 신규 버튼을 클릭."><spring:message code="csm.cnslmng.comment2"/></li>
				</ul>
				<div class="tbl-grid-box txt-center">
					<table id="blockListTable">
						<caption title="결제 차단 등록/해제"><spring:message code="csm.cnslmng.header.title.block.pay"/></caption>
						<colgroup>
							<col style="width:4%">
							<col style="width:10%">
							<col style="width:12%">
							<col style="width:14%">
							<col>
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:12%">
							<col style="width:10%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="csm.cnslmng.th.no"/></th>
								<th scope="col" title="차단 요청자"><spring:message code="csm.cnslmng.th.block.req"/></th>
								<th scope="col" title="등록 일시"><spring:message code="csm.cnslmng.th.date.reg"/><span class="icon-sort"><a href="javascript:void(0);"></a></span></th>
								<th scope="col" colspan="2" title="차단 기준"><spring:message code="csm.cnslmng.th.block.stand"/></th>
								<th scope="col" title="입력자"><spring:message code="csm.cnslmng.th.regr"/></th>
								<th scope="col" title="결제 차단 여부"><spring:message code="csm.cnslmng.th.block.yn"/></th>
								<th scope="col" title="수정 일시"><spring:message code="csm.cnslmng.th.date.update"/></th>
								<th scope="col" title="수정자"><spring:message code="csm.cnslmng.th.updater"/></th>
							</tr>
						</thead>
						<tbody id="payItcptResult">
						</tbody>
					</table>
					<div class="pagination-wrap" id="payItcptPaginationWrap">
						<div class="pagination-inner" id="payItcptPaginationInner">
						</div>
					</div>
				</div>
				<!--// grid -->
				
				<!-- btn -->
				<div class="btn-wrap">
					<button type="button" class="l-btn" id="newPayItcptBtn" title="신규"><spring:message code="csm.cnslmng.button.new"/></button>
				</div>
				<!--// btn -->
			</div>
		</div>
		
		<%-- template --%>
		<script type="text/html" id="tmpl-payItcptList">	
		{% for(var i=0; i<o.length; i++) { %}
		<tr data-id="{%=o[i].itcptRegSeq%}">
			<td>{%=o[i].rnum%}</td>
			{% if(o[i].itcptReqrClfFlg === "I"){ %}
			<td><spring:message code="csm.cnslmng.layer.itcpt.option.self"/></td>
			{% } else if(o[i].itcptReqrClfFlg === "F"){ %}
			<td><spring:message code="csm.cnslmng.layer.itcpt.option.family"/></td>
			{% } else{ %}
			<td><spring:message code="csm.cnslmng.layer.itcpt.option.etc"/></td>
			{% } %}
			<td>{%=o[i].regDt%}</td>
			<td>{%=o[i].itcptClfFlgNm%}</td>
			{% if(o[i].itcptClfFlg === "U"){ %}
			<td>{%=o[i].payrSeq%}</td>
			{% } else if(o[i].itcptClfFlg === "P"){ %}
			<td>{%=o[i].mphnNo%}</td>			
			{% } %}
			<td>{%=o[i].regrNm%}</td>
			<td>{%=o[i].procClfFlgNm%}</td>
			{% if(o[i].updateYn === "Y"){ %}
			<td>{%=o[i].lastChgDt%}</td>
			<td>{%=o[i].lastChgrNm%}</td>
			{% } else { %}
			<td></td>
			<td></td>
			{% } %}
		</tr>
		{% } %}
		</script>
		
		<div id="tab2" class="tab-conts" style="display: none;">
			<a href="#none" name="counsel-view"></a>
			<div class="tabBar">
				<span><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span class="active"><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<div class="tbl-info info-type2">
				<h4 class="cont-h4" title="이용자 정보"><spring:message code="csm.cnslmng.header.title.info.user"/></h4>
				<!-- unit -->
				<span class="unit" title="(단위 : 원)"><spring:message code="common.label.unit"/></span>
				<!--// unit -->
			</div>
			<!-- grid -->
			<div class="tbl-box rcpt-info">
				<table>
					<colgroup>
						<col style="width:12%">
						<col>
						<col style="width:12%">
						<col>
						<col style="width:12%">
						<col>
						<col style="width:12%">
						<col>
					</colgroup>
					<caption title="이용자 정보1"><spring:message code="csm.cnslmng.caption.info.user1"/></caption>
					<tbody>
						<tr>
							<th scope="row" title="접수 일자"><spring:message code="csm.cnslmng.th.date.rcpt"/></th>
							<td>
								<span id="infoRcptDt"></span>
							</td>
							<th scope="row" title="접수자"><spring:message code="csm.cnslmng.th.rcptregr"/></th>
							<td>
								<span id="infoRegr"></span>
							</td>
							<th scope="row" title="접수시간"><spring:message code="csm.cnslmng.th.time.rcpt"/></th>
							<td>
								<span id="infoRegTime"></span>
							</td>
							<th scope="row" title="접수 번호"><spring:message code="csm.cnslmng.th.rcptno"/></th>
							<td>
								<span id="infoRcptNo"></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tbl-box mrg-t10 trade-info" style="display: none;">
				<table>
					<colgroup>
						<col style="width:12%">
						<col style="width:38%">
						<col style="width:12%">
						<col style="width:*">
					</colgroup>
					<caption title="이용자 정보2"><spring:message code="csm.cnslmng.caption.info.user2"/></caption>
					<tbody>
						<tr>
							<th scope="row" title="결제 조건"><spring:message code="csm.cnslmng.th.conditaion.pay"/></th>
							<td>
								<span id="infoTrdTypNm"></span>
							</td>
							<th scope="row" title="거래 일시"><spring:message code="csm.cnslmng.th.date.trade"/></th>
							<td>
								<span id="infoTrdDt"></span>
							</td>
						</tr>
						<tr>
							<th scope="row" title="통신사"><spring:message code="csm.cnslmng.th.commc"/></th>
							<td>
								<span id="infoCommcClfNm"></span>
							</td>
							<th scope="row" title="취소 일시"><spring:message code="csm.cnslmng.th.date.cancel"/></th>
							<td>
								<span id="infoCnclDt"></span>
							</td>
						</tr>
						<tr>
							<th scope="row" title="거래금액"><spring:message code="csm.cnslmng.th.tradeamount"/></th>
							<td>
								<span id="infoPayAmt"></span>
							</td>
							<th scope="row" title="취소 가능 여부"><spring:message code="csm.cnslmng.th.cancel.yn"/></th>
							<td>
								<span id="infoAvlCncl"></span>
							</td>
						</tr>
						<tr>
							<th scope="row" title="가맹점명"><spring:message code="csm.cnslmng.th.cpname"/></th>
							<td>
								<span id="infoPaySvcNm"></span>
							</td>
							<th scope="row" title="결제 승인 번호"><spring:message code="csm.cnslmng.th.tradeno"/></th>
							<td>
								<span id="infoTrdNo"></span>
							</td>
						</tr>
						<tr>
							<th scope="row" title="상품명"><spring:message code="csm.cnslmng.th.goodsname"/></th>
							<td>
								<span id="infoGodsNm"></span>
							</td>
							<th scope="row" title="고객센터"><spring:message code="csm.cnslmng.th.center"/></th>
							<td>
								<span id="infoTelNo"></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<h4 class="cont-h4 mrg-t10" title="상담 내역"><spring:message code="csm.cnslmng.header.title.content.cnsl"/></h4>
			<form id="cnslForm">
			<input type="hidden" name="mphnNo" id="tempMphnNo" value=""/>
			<input type="hidden" name="payMphnId" id="tempPayMphnId" value=""/>
			<input type="hidden" name="payrSeq" id="tempPayrSeq" value=""/>
			<input type="hidden" name="rcptNo" id="tempRcptNo" value=""/>
			<input type="hidden" name="tradeModel.trdNo" id="tempTrdNo" value=""/>
			<div class="tbl-box">
				<table>
					<caption title="상담내역1"><spring:message code="csm.cnslmng.caption.content.cnsl1"/></caption>
					<colgroup>
						<col style="width:12%">
						<col style="width:38%">
						<col style="width:12%">
						<col style="width:*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="상담 구분"><spring:message code="csm.cnslmng.th.cnslgubun"/></th>
							<td>
								<select class="input-select" name="cnslClfUprCd" id="cnslClfUprCd" style="width:200px;">
									<option value="" title="선택"><spring:message code="common.select.select"/></option>
									<c:forEach items="${cnslClfCdList}" var="list" varStatus="status">
										<option value="${list.cd}">${list.cdNm}</option>
									</c:forEach>
								</select>
							</td>
							<th title="이벤트"><spring:message code="csm.cnslmng.th.event"/></th>
							<td>
								<select class="input-select" name="cnslEvntCd" id="cnslEvntCd" style="width:200px;">
									<option value="" title="선택"><spring:message code="common.select.select"/></option>
									<c:forEach items="${evntTypCdList}" var="list" varStatus="status">
										<option value="${list.cd}">${list.cdNm}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tbl-box mrg-t10">
				<table>
					<caption title="상담내역2"><spring:message code="csm.cnslmng.caption.content.cnsl2"/></caption>
					<colgroup>
						<col style="width:12%">
						<col style="width:38%">
						<col style="width:12%">
						<col style="width:*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="상담 유형"><spring:message code="csm.cnslmng.th.cnsltype"/></th>
							<td>
								<select class="input-select" name="cnslTypCd" id="cnslTypCd" style="width:200px;">
									<option value="" title="선택"><spring:message code="common.select.select"/></option>
									<c:forEach items="${cnslTypCdList}" var="list" varStatus="status">
										<option value="${list.cd}" <c:if test="${list.cd eq 'TKTK01'}">selected="selected"</c:if>>${list.cdNm}</option>
									</c:forEach>
								</select>
							</td>
							<th title="접수 유형"><spring:message code="csm.cnslmng.th.rcpttype"/></th>
							<td>
								<select class="input-select" name="rcptMthdCd" id="rcptMthdCd" style="width:200px;">
									<option value="" title="선택"><spring:message code="common.select.select"/></option>
									<c:forEach items="${rcptMthdCdList}" var="list" varStatus="status">
										<option value="${list.cd}" <c:if test="${list.cd eq 'RKRK01'}">selected="selected"</c:if>>${list.cdNm}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" title="고객 유형"><spring:message code="csm.cnslmng.th.custtype"/></th>
							<td colspan="3">
								<select class="input-select" name="custTypFlg" id="custTypFlg" style="width:200px;">
									<option value="" title="선택"><spring:message code="common.select.select"/></option>
									<option value="B" title="구매자" selected="selected"><spring:message code="csm.cnslmng.option.custtype1"/></option>
									<option value="S" title="판매자"><spring:message code="csm.cnslmng.option.custtype2"/></option>
									<option value="C" title="통신사 상담원"><spring:message code="csm.cnslmng.option.custtype4"/></option>
									<option value="E" title="기타"><spring:message code="csm.cnslmng.option.custtype3"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" title="상담 내용"><spring:message code="csm.cnslmng.th.content.cnsl"/><br>(<span id="textCountCnslCtnt">0</span>/400byte)</th>
							<td colspan="3">
								<textarea class="textarea" name="cnslCtnt" id="cnslCtnt" style="width:99%" rows="1" cols="1"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="tableBottom">
				<button class="l-btn" type="button" id="tempSaveCnslBtn" title="임시 저장"><spring:message code="csm.cnslmng.button.save.temp"/></button>
				<button class="l-btn" type="button" id="deleteCnslBtn" title="내용 삭제"><spring:message code="csm.cnslmng.button.delete.temp"/></button>
			</div>
			<!-- btn -->
			<div class="btn-wrap">
				<button type="button" class="l-btn" id="saveCnslBtn" title="접수 완료"><spring:message code="csm.cnslmng.button.save.rcpt"/></button>
				<button type="button" class="l-btn" id="updateCnslBtn" style="display: none;" title="수정 완료"><spring:message code="csm.cnslmng.button.update.rcpt"/></button>
			</div>
			<!--// btn -->
		</div>
		<div id="tab3" class="tab-conts" style="display: none;">
			<a href="#none" name="rm-view"></a>
			<div class="tabBar">
				<span><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span class="active"><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<ul class="tab-head basic">
				<li><a href="#tab3-1" class="tab-selected"><spring:message code="rms.csm.rmsCnslMng.tab3.subtab1.label"/></a></li>
				<li><a href="#tab3-2" class=""><spring:message code="rms.csm.rmsCnslMng.tab3.subtab2.label"/></a></li>
				<li><a href="#tab3-3" class=""><spring:message code="rms.csm.rmsCnslMng.tab3.subtab3.label"/></a></li>
				<li><a href="#tab3-4" class=""><spring:message code="rms.csm.rmsCnslMng.tab3.subtab4.label"/></a></li>
			</ul>
			<div id="tab3-1">
				<form id="rmBlkReliefRegForm">
					<table>
						<colgroup>
							<col style="width:*">
							<col style="width:65px">
						</colgroup>
						<tbody>
							<tr>
								<td>
									<div class="tbl-box mrg-t10">
										<table class="rmTable">
											<caption></caption>
											<colgroup>
												<col style="width:10%">
												<col style="width:17%">
												<col style="width:10%">
												<col style="width:22%">
												<col style="width:10%">
												<col style="width:*">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row">
														<span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.reliefClfCd"/>
													</th>
													<td>
														<select class="input-select" name="reliefClfCd">
															<option value="D" title="고객번호" selected="selected"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.reliefClfCd.D"/></option>
															<option value="P" title="휴대폰번호"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.reliefClfCd.P"/></option>
														</select>
													</td>
													<th scope="row">
														<span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.applyStatus"/>
													</th>
													<td>
														<select class="input-select" name="applyStatus">
															<option value="" title="선택"><spring:message code="common.select.select"/></option>
															<option value="R" title="해제"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.applyStatus.R"/></option>
															<option value="B" title="차단(해제취소)"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.applyStatus.B"/></option>
														</select>
													</td>
													<th scope="row">
														<span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.blkReliefCd"/>
													</th>
													<td>
														<select class="input-select" name="blkReliefCd">
															<option value="" title="선택"><spring:message code="common.select.select"/></option>
															<c:forEach items="${rmsBlkReliefCdList}" var="list" varStatus="status">
																<option value="${list.cd}" title="${list.cdNm}">${list.cdNm}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th scope="row">
														<span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.edDt"/>
													</th>
													<td>
														<div class="datebox">
															<input type="text" name="edDt" class="input-text calendar" style="width:80px;">
															<a href="#none"><span class="icon-cal"></span></a>
														</div>
													</td>
													<th scope="row">
														<span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.relsRsnCd"/>
													</th>
													<td>
														<select class="input-select" name="relsRsnCd">
															<option value="" title="선택"><spring:message code="common.select.select"/></option>
															<c:forEach items="${rmsRmRelsRsnCdList}" var="list" varStatus="status">
																<option value="${list.cd}" title="${list.cdNm}">${list.cdNm}</option>
															</c:forEach>
														</select>
													</td>
													<th scope="row"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.rmk"/></th>
													<td>
														<input type="text" name="rmk" class="input-text" style="width:100%" maxlength="255">
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
								<td>
									<div class="btn-wrap">
										<button type="button" class="l-btn" name="btnSave">저장</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<ul class="caption-li mrg-b5" style="margin-bottom:10px;">
					<li><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.form.comment"/></li>
				</ul>
				<div class="tbl-grid-box txt-center" style="overflow-x:auto; overflow-y:auto; max-height:483px;">
					<table style="width:1300px;">
						<caption title="RM 차단/해제 등록"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.caption"/></caption>
						<colgroup>
							<col style="width:50px">
							<col style="width:90px">
							<col style="width:100px">
							<col style="width:80px">
							<col style="width:120px">
							<col style="width:100px">
							<col style="width:80px">
							<col style="width:80px">
							<col style="width:150px">
							<col style="width:*">
							<col style="width:80px">
							<col style="width:60px">
							<col style="width:80px">
							<col style="width:60px">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.idx"/></th>
								<th scope="col" title="고객번호"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.payrSeq"/></th>
								<th scope="col" title="휴대폰번호"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.mphnNo"/></th>
								<th scope="col" title="적용대상"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.reliefClfNm"/></th>
								<th scope="col" title="적용범위"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.blkReliefNm"/></th>
								<th scope="col" title="적용상태"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.applyStatusNm"/></th>
								<th scope="col" title="시작일시"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.stDt"/></th>
								<th scope="col" title="종료일시"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.edDt"/></th>
								<th scope="col" title="해제사유"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.relsRsnNm"/></th>
								<th scope="col" title="비고"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.rmk"/></th>
								<th scope="col" title="입력일시"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.inptDt"/></th>
								<th scope="col" title="입력자"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.inptNm"/></th>
								<th scope="col" title="수정일시"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.updtDt"/></th>
								<th scope="col" title="수정자"><spring:message code="rms.csm.rmsCnslMng.rmBlkReliefReg.list.th.updtNm"/></th>
							</tr>
						</thead>
						<tbody id="rmBlkReliefResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-rmBlkReliefList">	
				{% for (var i = 0; i < o.length; i++) { %}
				<tr>
					<td>{%=o[i].idx%}</td>
					<td>{%=o[i].payrSeq%}</td>
					<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td>{%=o[i].reliefClfNm%}</td>
					<td>{%=o[i].blkReliefNm%}</td>
					<td>{%=o[i].applyStatusNm%}</td>
					<td>{%=o[i].stDt%}</td>
					<td>{%=o[i].edDt%}</td>
					<td>{%=o[i].relsRsnNm%}</td>
					<td class="txt-left">{%=o[i].rmk%}</td>
					<td>{%=o[i].inptDt%}</td>
					<td>{%=o[i].inptNm%}</td>
					<td>{%=o[i].updtDt%}</td>
					<td>{%=o[i].updtNm%}</td>
				</tr>
				{% } %}
				</script>
			</div>
			<div id="tab3-2">
				<form id="fraudReliefRegForm">
					<table>
						<colgroup>
							<col style="width:*">
							<col style="width:65px">
						</colgroup>
						<tbody>
							<tr>
								<td>
									<div class="tbl-box mrg-t10">
										<table class="rmTable">
											<caption></caption>
											<colgroup>
												<col style="width:10%">
												<col style="width:25%">
												<col style="width:10%">
												<col>
											</colgroup>
											<tbody>
												<tr>
													<th scope="row">
														<span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.form.relsRsnCd"/>
													</th>
													<td>
														<select class="input-select" name="relsRsnCd">
															<option value="" title="선택"><spring:message code="common.select.select"/></option>
															<c:forEach items="${rmsFraudRelsRsnCdList}" var="list" varStatus="status">
																<option value="${list.cd}" title="${list.cdNm}">${list.cdNm}</option>
															</c:forEach>
														</select>
													</td>
													<th scope="row"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.form.rmk"/></th>
													<td colspan="3">
														<input type="text" name="rmk" class="input-text" style="width:100%" maxlength="255">
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
								<td>
									<div class="btn-wrap">
										<button type="button" class="l-btn" name="btnSave">저장</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<ul class="caption-li mrg-b5">
					<li><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.form.comment1"/></li>
					<li><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.form.comment2"/></li>
				</ul>
				<div class="tbl-grid-box txt-center" style="margin-top:10px;">
					<table>
						<caption title="불량고객 해제 등록"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.caption"/></caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:8%">
							<col style="width:15%">
							<col style="width:80px">
							<col style="width:80px">
							<col style="width:*">
							<col style="width:80px">
							<col style="width:6%">
							<col style="width:80px">
							<col style="width:6%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.idx"/></th>
								<th scope="col" title="고객번호"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.payrSeq"/></th>
								<th scope="col" title="적용범위"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.fraudClfNm"/></th>
								<th scope="col" title="시작일시"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.stDt"/></th>
								<th scope="col" title="종료일시"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.edDt"/></th>
								<th scope="col" title="비고"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.rmk"/></th>
								<th scope="col" title="입력일시"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.inptDt"/></th>
								<th scope="col" title="입력자"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.inptNm"/></th>
								<th scope="col" title="수정일시"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.updtDt"/></th>
								<th scope="col" title="수정자"><spring:message code="rms.csm.rmsCnslMng.fraudReliefReg.list.th.updtNm"/></th>
							</tr>
						</thead>
						<tbody id="fraudBlkResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-fraudBlkList">	
				{% for (var i = 0; i < o.length; i++) { %}
				<tr>
					<td>{%=o[i].idx%}</td>
					<td>{%=o[i].payrSeq%}</td>
					<td>{%=o[i].fraudClfNm%}</td>
					<td>{%=o[i].stDt%}</td>
					<td>{%=o[i].edDt%}</td>
					<td>{%=o[i].rmk%}</td>
					<td>{%=o[i].inptDt%}</td>
					<td>{%=o[i].inptNm%}</td>
					<td>{%=o[i].updtDt%}</td>
					<td>{%=o[i].updtNm%}</td>
				</tr>
				{% } %}
				</script>
			</div>
			<div id="tab3-3">
				<ul class="caption-li mrg-b5">
					<li title="<spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.comment"/>"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.comment"/></li>
				</ul>
				<div class="tbl-grid-box txt-center">
					<table>
						<caption title="가맹점B/L 조회"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.caption"/></caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:8%">
							<col style="width:10%">
							<col style="width:30%">
							<col style="width:*">
							<col style="width:80px">
							<col style="width:7%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.idx"/></th>
								<th scope="col" title="차단구분"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.valClfNm"/></th>
								<th scope="col" title="적용대상"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.val"/></th>
								<th scope="col" title="차단범위"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.cpClfNm"/></th>
								<th scope="col" title="차단사유"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.rmk"/></th>
								<th scope="col" title="차단일시"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.inptDt"/></th>
								<th scope="col" title="차단자"><spring:message code="rms.csm.rmsCnslMng.cpBLInq.list.th.inptNm"/></th>
							</tr>
						</thead>
						<tbody id="cpBLInqResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-cpBLInqList">	
				{% for (var i = 0; i < o.length; i++) { %}
				<tr>
					<td>{%=o[i].idx%}</td>
					<td>{%=o[i].valClfNm%}</td>
					<td title="{%=o[i].val%}">{%=o[i].valClf == 'P' ? utils.hiddenToTel(o[i].val) : o[i].val%}</td>
					<td class="txt-left">{%=o[i].cpClfNm%}</td>
					<td class="txt-left">{%=o[i].rmk%}</td>
					<td>{%=o[i].inptDt%}</td>
					<td>{%=o[i].inptNm%}</td>
				</tr>
				{% } %}
				</script>
			</div>
			<div id="tab3-4">
				<div class="tbl-grid-box txt-center" style="overflow-y:auto; max-height:470px;">
					<table>
						<caption title="RM 변경이력"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.caption"/></caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:8%">
							<col style="width:9%">
							<col style="width:15%">
							<col style="width:8%">
							<col style="width:17%">
							<col style="width:*">
							<col style="width:80px">
							<col style="width:6%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" title="No."><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.idx"/></th>
								<th scope="col" title="고객번호"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.payrSeq"/></th>
								<th scope="col" title="휴대폰번호"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.mphnNo"/></th>
								<th scope="col" title="처리업무"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.custInfoChgNm"/></th>
								<th scope="col" title="처리상태"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.blkRelsClfNm"/></th>
								<th scope="col" title="사유"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.chgRsnNm"/></th>
								<th scope="col" title="비고"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.rmk"/></th>
								<th scope="col" title="입력일시"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.inptDt"/></th>
								<th scope="col" title="입력자"><spring:message code="rms.csm.rmsCnslMng.rmChangeHis.list.th.inptNm"/></th>
							</tr>
						</thead>
						<tbody id="rmChangeHisResult">
						</tbody>
					</table>
				</div>
				
				<script type="text/html" id="tmpl-rmChangeHisList">	
				{% for (var i = 0; i < o.length; i++) { %}
				<tr>
					<td>{%=o[i].idx%}</td>
					<td>{%=o[i].payrSeq%}</td>
					<td title="{%=o[i].mphnNo%}">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
					<td>{%=o[i].custInfoChgNm%}</td>
					<td>{%=o[i].blkRelsClfNm%}</td>
					<td>{%=o[i].chgRsnNm%}</td>
					<td class="txt-left">{%=o[i].rmk%}</td>
					<td>{%=o[i].inptDt%}</td>
					<td>{%=o[i].inptNm%}</td>
				</tr>
				{% } %}
				</script>
			</div>
			
		</div>
		
		<div id="tab4" class="tab-conts" style="display: none;">
			<div class="tabBar">
				<span><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span class="active"><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<a name="process-view"></a>
			<form id="procForm">
			<input type="hidden" name="rcptNo" id="tempProcRcptNo" value=""/>
			<input type="hidden" name="tradeModel.trdNo" id="tempProcTrdNo" value=""/>
			<input type="hidden" name="entpId" id="tempProcEntpId" value=""/>
			<input type="hidden" name="cpCd" id="tempProcCpCd" value=""/>
			<input type="hidden" name="tjurYn" id="tjurYn" value="N"/>
			<input type="hidden" name="tjurCd" id="tjurCd" value=""/>
			<div class="tbl-box">
				<table>
					<caption title="처리 상태"><spring:message code="csm.cnslmng.th.status.proc"/></caption>
					<colgroup>
						<col style="width:10%">
						<col>
						<col style="width:10%">
						<col>
						<col style="width:10%">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="처리 상태"><spring:message code="csm.cnslmng.th.status.proc"/></th>
							<td>
								<label><input type="radio" name="procYn" value="Y">Y</label>
								<label><input type="radio" name="procYn" value="N">N</label>
							</td>
							<th scope="row" title="처리 일자"><spring:message code="csm.cnslmng.th.date.proc"/></th>
							<td>
								<span id="infoProcDt"><c:out value=""/></span>
								<input type="hidden" name="procDt" id="procDt" value=""/>
							</td>
							<th scope="row" title="최종 변경 일자"><spring:message code="csm.cnslmng.th.date.lastchange"/></th>
							<td>
								<span id="infoLastChgDt"><c:out value=""/></span>
								<input type="hidden" name="lastChgDt" id="lastChgDt" value=""/>
							</td>
							<th scope="row" title="변경자"><spring:message code="csm.cnslmng.th.changer"/></th>
							<td>
								<span id="infoLastChgr"><c:out value=""/></span>
								<input type="hidden" name="lastChgr" id="lastChgr" value=""/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tbl-box mrg-t10">
				<table>
					<caption title="처리 내용"><spring:message code="csm.cnslmng.th.content.proc"/></caption>
					<colgroup>
						<col style="width:10%">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="처리 내용"><spring:message code="csm.cnslmng.th.content.proc"/></th>
							<td>
								<textarea style="width:99%" name="procCtnt" id="procCtnt" class="textarea" rows="1" cols="1"></textarea>
								<p>
								<select class="input-select" name="tjurClfFlg" id="tjurClfFlg" style="width:200px">
									<option value="" title="선택"><spring:message code="common.select.select"/></option>
									<option value="B" title="사업부 이관"><spring:message code="csm.cnslmng.option.tjur.dp"/></option>
								</select>
								<button class="inbtn" type="button" id="sendTjur" title="이관하기" data-disabled="false"><spring:message code="csm.cnslmng.button.tjur"/></button>
								<span class="mrg-l10" id="tjurCtnt"></span>
								</p>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="tableBottom">
				<button class="l-btn" type="button" id="tempSaveProcBtn" title="임시 저장"><spring:message code="csm.cnslmng.button.save.temp"/></button>
				<button class="l-btn" type="button" id="deleteProcBtn" title="내용 삭제"><spring:message code="csm.cnslmng.button.delete.temp"/></button>
			</div>
			<!-- btn -->
			<div class="btn-wrap">
				<button type="button" class="l-btn" id="popTjurHist" title="이관 처리 내역"><spring:message code="csm.cnslmng.layer.tjurhist.title"/></button>
				<button type="button" class="l-btn" id="saveProcBtn" title="처리 완료"><spring:message code="csm.cnslmng.button.update.proc"/></button>
			</div>
			<!--// btn -->
		</div>
		<div id="tab5" class="tab-conts" style="display: none;">
			<div class="tabBar">
				<span><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span class="active"><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<a href="#none" name="manage-view"></a>
			<div class="tbl-grid-box txt-center">
				<table>
					<caption title="전체 상담 이력"><spring:message code="csm.cnslmng.header.title.cnsl.history"/></caption>
					<colgroup>
						<col style="width:4%">
						<col style="width:8%">
						<col style="width:10%">
						<col style="width:9%">
						<col style="width:8%">
						<col style="width:8%">
						<col style="width:5%">
						<col style="width:8%">
						<col style="width:5%">
						<col>
						<col style="width:8%">
						<col style="width:10%">
						<col style="width:7%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" title="No."><spring:message code="csm.cnslmng.th.no"/></th>
							<th scope="col" title="접수 일자"><spring:message code="csm.cnslmng.th.date.rcpt"/></th>
							<th scope="col" title="접수자"><spring:message code="csm.cnslmng.th.rcptregr"/></th>
							<th scope="col" title="접수 번호"><spring:message code="csm.cnslmng.th.rcptno"/></th>
							<th scope="col" title="결제 조건"><spring:message code="csm.cnslmng.th.conditaion.pay"/></th>
							<th scope="col" title="상담 구분"><spring:message code="csm.cnslmng.th.cnslgubun"/></th>
							<th scope="col" title="상담 내용"><spring:message code="csm.cnslmng.th.content.cnsl.br"/></th>
							<th scope="col" title="처리 일자"><spring:message code="csm.cnslmng.th.date.proc"/></th>
							<th scope="col" title="처리 내용"><spring:message code="csm.cnslmng.th.content.proc.br"/></th>
							<th scope="col" title="변경자"><spring:message code="csm.cnslmng.th.changer"/></th>
							<th scope="col" title="이관"><spring:message code="csm.cnslmng.th.tjur"/></th>
							<th scope="col" title="결과 통보 일시"><spring:message code="csm.cnslmng.th.date.result"/></th>
							<th scope="col" title="결과 통보"><spring:message code="csm.cnslmng.th.result.br"/></th>
						</tr>
					</thead>
					<tbody id="cnslChgResult">
						<tr>
							<td colspan="13" align="center"><spring:message code="csm.cnslmng.comment5"/></td>
						</tr>
					</tbody>
				</table>
				<div class="pagination-wrap" id="cnslChgPaginationWrap" style="display: none;">
					<div class="pagination-inner" id="cnslChgPaginationInner">
					</div>
				</div>
			</div>
			<%-- template --%>
			<script type="text/html" id="tmpl-cnslChgList">
			{% var resources = '${resources}'; %}
			{% for(var i=0; i<o.length; i++) { %}
			<tr>
				<td>{%=o[i].rnum%}</td>
				<td>{%=o[i].regDt%}</td>
				<td>{%=o[i].regr%}</td>
				<td><a href="javascript:void(0);" class="cnsl-ctnt" title="<spring:message code='csm.cnslmng.data.title.cnsl'/>" data-hash="counsel-view" data-id="{%=o[i].rcptNo%}">{%=o[i].rcptNo%}</a></td>
				<td>{%=StringUtil.nvl(o[i].payCndiNm, "")%}</td>
				<td>{%=StringUtil.nvl(o[i].cnslClfUprNm, "")%}</td>
				<td><a href="javascript:void(0);" class="cnsl-ctnt" title="<spring:message code='csm.cnslmng.data.title.cnsl'/>" data-hash="counsel-view" data-id="{%=o[i].rcptNo%}"><img src="{%=resources%}/images/counselIcon.gif" alt="<spring:message code='csm.cnslmng.th.content.cnsl'/>"></a></td>
				{% if(Common.getIsNull(o[i].procDd) === false){ %}
				<td>{%=o[i].procDd.substring(0,4) + "." + o[i].procDd.substring(4,6) + "." + o[i].procDd.substring(6,8)%}</td>
				{% } else { %}
				<td></td>
				{% } %}
				{% if(Common.getIsNull(o[i].procCtnt) === false){ %}
				<td><a href="javascript:void(0);" class="proc-ctnt" title="<spring:message code='csm.cnslmng.data.title.proc'/>" data-hash="process-view" data-id="{%=o[i].rcptNo%}"><img src="{%=resources%}/images/counselIcon2.gif" alt="<spring:message code='csm.cnslmng.th.content.proc'/>"></a></td>
				{% } else { %}
				<td></td>
				{% } %}
				<td>{%=o[i].lastChgr%}</td>
				<td>{%=StringUtil.nvl(o[i].cnslTjurNm, "")%}</td>
				<td>{%=StringUtil.nvl(o[i].rsltNotiDt, "")%}</td>
				{% if(Common.getIsNull(o[i].rsltNotiMthd) === true){ %}
				<td></td>
				{% } else if(o[i].rsltNotiMthd === "S"){ %}
				<td><a href="javascript:void(0);" class="send-rslt" data-flg="S" data-seq="{%=o[i].sndReqSeq%}">SMS</a></td>
				{% } else if(o[i].rsltNotiMthd === "M"){ %}
				<td><a href="javascript:void(0);" class="send-rslt" data-flg="M" data-seq="{%=o[i].sndReqSeq%}">E-mail</a></td>
				{% } %}
			</tr>
			<tr class="counselTr">
				<td colspan="13"><span class="tag"><spring:message code="csm.cnslmng.th.content.cnsl"/></span>{%=o[i].cnslCtnt.replace(/<br\/>/g, "\r\n")%}</td>
			</tr>
			{% if(Common.getIsNull(o[i].procCtnt) === false){ %}
			<tr class="counselTr">
				<td colspan="13"><span class="tag"><spring:message code="csm.cnslmng.th.content.proc"/></span>{%=o[i].procCtnt.replace(/<br\/>/g, "\r\n")%}</td>
			</tr>
			{% } %}
			{% } %}
			</script>
		</div>
		<div id="tab6" class="tab-conts" style="display: none;">
			<div class="tabBar">
				<span><button type="button" title="조회"><spring:message code="csm.cnslmng.tab.main1"/></button></span><span><button type="button" title="접수"><spring:message code="csm.cnslmng.tab.main2"/></button></span><span><button type="button" title="RM"><spring:message code="csm.cnslmng.tab.main3"/></button></span><span><button type="button" title="처리"><spring:message code="csm.cnslmng.tab.main4"/></button></span><span><button type="button" title="관리"><spring:message code="csm.cnslmng.tab.main5"/></button></span><span class="active"><button type="button" title="결과 통보"><spring:message code="csm.cnslmng.tab.main6"/></button></span>
			</div>
			<a href="#none" name="result-view"></a>
			<ul class="tab-head basic">
				<li><a href="#tab6-1" id="tab6_1" class="tab-selected" title="SMS 발송"><spring:message code="csm.cnslmng.tab.sub6"/></a></li>
				<li><a href="#tab6-2" id="tab6_2" class="" title="E-mail 발송"><spring:message code="csm.cnslmng.tab.sub7"/></a></li>
			</ul>
			<form id="sndRsltForm">
			<input type="hidden" name="rsltNotiMthd" id="rsltNotiMthd" value="S" />
			<input type="hidden" name="testYn" id="testYn" value="" />
			<input type="hidden" name="mphnNo" id="sndRsltMphnNo" value="" />
			<div id="tab6-1">
				<div class="tbl-box mrg-t10">
					<table>
						<caption title="SMS 발송"><spring:message code="csm.cnslmng.tab.sub6"/></caption>
						<colgroup>
							<col style="width:12%">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" title="발송 대상"><spring:message code="csm.cnslmng.th.target"/></th>
								<td><span class="custNo"></span> / <span class="mphnNoCust"></span></td>
							</tr>
							<tr>
								<th scope="row" title="처리 내용"><spring:message code="csm.cnslmng.th.content.proc"/></th>
								<td>
									<button class="inbtn" type="button" id="documentInven" title="문서 보관함"><spring:message code="csm.cnslmng.button.document"/></button><br>
									<textarea name="procCtnt" style="width:100%; box-sizing:border-box" class="textarea mrg-t5 sms" rows="1" cols="1" placeholder="<spring:message code='csm.cnslmng.placeholder.guide'/>"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="tableBottom">
					<button class="l-btn sendRsltBtn sms" type="button" title="테스트 발송" data-test="Y"><spring:message code="common.email.label.testSend"/></button>
					<button class="l-btn sendRsltBtn sms" type="button" title="발송" data-test="N"><spring:message code="common.email.button.send"/></button>
				</div>
			</div>
			<div id="tab6-2">
				<div class="tbl-box mrg-t10">
					<table>
						<caption title="E-mail 발송"><spring:message code="csm.cnslmng.tab.sub7"/></caption>
						<colgroup>
							<col style="width:115px">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" title="받는 대상"><spring:message code="csm.cnslmng.th.target.rcv"/></th>
								<td><input type="text" name="email" id="email" class="input-text sendEmail email" style="width:98.6%" placeholder="E-mail 입력"></td>
							</tr>
							<tr>
								<th scope="row" title="제목"><spring:message code="common.email.label.title"/></th>
								<td><input type="text" name="title" style="width:98.6%" class="input-text sendEmail" placeholder="제목 입력"></td>
							</tr>
							<tr>
								<th scope="row" title="처리 내용"><spring:message code="csm.cnslmng.th.content.proc"/></th>
								<td><textarea name="procCtnt" style="width:99%; box-sizing:border-box" class="textarea sendEmail" rows="1" cols="1" placeholder="<spring:message code='csm.cnslmng.placeholder.guide'/>"></textarea></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="tableBottom">
					<button class="l-btn previewEmail sendEmail" type="button" title="미리 보기"><spring:message code="common.email.button.preview"/></button>
					<button class="l-btn sendRsltBtn sendEmail" type="button" title="테스트 발송" data-test="Y"><spring:message code="common.email.label.testSend"/></button>
					<button class="l-btn sendRsltBtn sendEmail" type="button" title="발송" data-test="N"><spring:message code="common.email.button.send"/></button>
				</div>
			</div>
			</form>
		</div>
	</div>
	<div id="quickMenu">
		<a href="#"><div class="firstMenu"></div></a>
		<a href="#"><div class="secondMenu"></div></a>
		<a href="#"><div class="thirdMenu"></div></a>
	</div>
	<!--// contents -->	
</body>
</html>