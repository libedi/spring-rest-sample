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
<html lang="ko">
<head>
	<title title="미납이력 및 가산금 부과내역 조회"><spring:message code="csm.cnslmng.layer.title.npay"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<link type="text/css" rel="stylesheet" href="${resources}/css/popup.css" />
	<script type="text/javascript">
		var parentModule;
		
		var layerNpayHistory = (function(module, $){
			"use strict";
			
			/**
			 * private
			 */
			var root = module;
			var options = { contextPath: '' };
			var uris = { search: '/cnslMng/layer/npayHistory', 
						 };
			var messages;
			var forms;
			var context = '.pop-wrap'; //jquery selector context
			
			/**
			 * public
			 */
			module.init = function(opts){
				options.contextPath = opts.contextPath;		
				forms = opts.forms;
				messages = opts.messages;
			};
			// 미납이력 및 가산금 부과내역 조회
			module.search = function(){
				if($(":radio[name='selectDate']:checked").val() === "T"){
			        var sttDd = $(".pop-wrap input[name='strtDt']").val().replace(/\./g, "");
			        var endDd = $(".pop-wrap input[name='endDt']").val().replace(/\./g, "");
					if( (sttDd !== null && sttDd !== '') && (endDd !== null && endDd !== '') ) {
						var result = DateUtil.getDiffMonth(sttDd, endDd);
						if(result < 0 ){
							alert('<spring:message code="common.alert.termError"/>');
							return false;   
		   				}else if(result > 2){
		   					alert('<spring:message code="common.alert.termOver1"/>');
		   					$(".pop-wrap input[name='strtDt']").focus();
		   					return false;
		   				} 
					}
				}	
				impay.sendPost({
					requestUri: options.contextPath + uris.search,
					data: $('#'+ forms.searchForm, context).serialize(),
					dataType: 'json',
					successCallback: root.bindList
				});
			};		
			module.bindList = function(data){
				if(data.total > 0){
					$('#result', context).html(tmpl("tmpl-npayHisList" , data.content));
					var acPayAmtTotal = 0;
					var rcptAmtTotal = 0;
					var npayAmtTotal = 0;
					var billAmtTotal = 0;
					for(var i=0; i<data.content.length; i++){
						acPayAmtTotal = acPayAmtTotal + parseInt(data.content[i].acPayAmt);
						rcptAmtTotal = rcptAmtTotal + parseInt(data.content[i].rcptAmt);
						npayAmtTotal = npayAmtTotal + parseInt(data.content[i].npayAmt);
						billAmtTotal = billAmtTotal + parseInt(data.content[i].billAmt);
		       		}
					var i = 0;
					$("#npayHisListTotal>tr>td", context).eq(i++).html("<spring:message code='rpm.sum'/>"); 
		    		$("#npayHisListTotal>tr>td", context).eq(i++).html(Common.formatMoney(acPayAmtTotal));
		    		$("#npayHisListTotal>tr>td", context).eq(i++).html(Common.formatMoney(rcptAmtTotal));
		    		$("#npayHisListTotal>tr>td", context).eq (i++).html(Common.formatMoney(npayAmtTotal));
		    		$("#npayHisListTotal>tr>td", context).eq(i++).html(Common.formatMoney(billAmtTotal));
		    		$("#npayHisListTotal", context).show();
		    	}else{
		    		$("#npayHisListTotal", context).hide();
		    		$("#result", context).html("<tr><td colspan='10' class='txt-center' style='width:924px'>" + messages.noResult + "</td></tr>");
		    		
		    	}
			};
			return module;
		}(window.layerNpayHistory || {}, $));
		
		
		$(function() {
			var messages = {'noResult' : '<spring:message code="common.list.notice"/>'};
			var forms = { 'searchForm': 'frmSearch'};
			layerNpayHistory.init({contextPath: '${contextPath}',
								forms: forms,
								messages: messages});
			var context = ".pop-wrap";
			var today = DateUtil.getToday();
			today = Common.formatDateYYYYMM(today, ".") 
			$("#strtMon1").val(today);
			$("#stDate").val(today);
			$("#endDate").val(today);
			
			var payMphnId = "${payMphnId}";
			$("#payMphnId", ".pop-wrap").val(payMphnId);
			layerNpayHistory.search();
			
			// 조회기간 라디오 이벤트
			$("div.search-box div.datebox input, div.search-box div.datebox span").click(function(){
				var cls = $(this).closest("div").find("input").attr("class");
				var val = "";
				if(cls.indexOf("dateMonth") > -1){
					val = "M"
				} else if(cls.indexOf("dateTerm") > -1){
					val = "T"
				}
				$("input:radio[name='selectDate']").removeProp("checked");
				$("input:radio[name='selectDate'][value='" + val + "']").prop("checked", true);
			});
			$("#div.search-box button.dateTerm").click(function(){
				$("input:radio[name='selectDate']").removeProp("checked");
				$("input:radio[name='selectDate'][value='T']").prop("checked", true);
			});
			$("#searchBtn", context).on('click',function(){
				$("#pageIndex", context).val("1");
				layerNpayHistory.search();		
			});	
		});
	</script>
</head>
<body>
	<div class="pop-wrap">
		<div class="pop-header">
			<h1 title="미납이력 및 가산금 부과내역 조회"><spring:message code="csm.cnslmng.layer.title.npay"/></h1>
		</div>
		
		<div class="pop-conts">
<!-- search -->
			<div class="search-box">
				<fieldset>
				<legend title="미납 이력 및 가산금 부과 내역 조회"><spring:message code="csm.cnslmng.layer.title.npay"/></legend>
				<form id="frmSearch">
				<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"  >
				<input type="hidden" id="rowCount"  name="pageParam.rowCount"  value="5" >
				<input type="hidden" id="payMphnId" name="payMphnId" >
				<table>
					<caption title="미납 이력 및 가산금 부과 내역 조회"><spring:message code="csm.cnslmng.layer.title.npay"/></caption>
					<colgroup>
						<col style="width:12%">
						<col>
						<col style="width:12%">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="조회 기간"><spring:message code="common.inquery.term"/></th>
							<td colspan="3">
								<input type="radio" name="selectDate" value="M" id="selectDateMonth"><label for="selectDateMonth"  title="월별"><spring:message code="common.label.month"/></label>
								<div class="datebox">
									<input type="text" name="strtMon" id="strtMon1" class="input-text dateMonth ympicker" style="width:80px;" readonly="readonly" >
									<a href="#"><span class="icon-cal"></span></a>
								</div>
	
								<input type="radio" name="selectDate" value="T" id="selectDateTerm" checked="checked" class="mrg-l20"><label for="selectDateTerm"  title="기간별"><spring:message code="common.label.term"/></label>
								<div class="datebox">
									<input type="text" name="strtDt" id="stDate" class="input-text dateTerm ympicker" style="width:80px;" readonly="readonly" >
									<a href="#none"><span class="icon-cal"></span></a>
								</div>
								<span class="hyphen">~</span>
								<div class="datebox">
									<input type="text" name="endDt" id="endDate" class="input-text dateTerm ympicker" style="width:80px;" readonly="readonly" >
									<a href="#none"><span class="icon-cal"></span></a>
								</div>
								<button type="button" class="l-btn searchbtn" id="searchBtn"  title="조회"><spring:message code="common.button.search"/></button>						
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				</fieldset>
			</div>
			<!--// search -->
			<div class="tbl-info info-type2">
				<span class="unit" title="(단위 : 원)"><spring:message code="common.label.unit"/></span>
			</div>
				<!-- grid -->
				<div class="tbl-grid-box txt-right" id="impay-csm-layer-NpayHis">
					<!--grid_head-->
					<div class="grid-head">
						<table>
							<caption title="미납 이력 및 가산금 부과 내역 조회"><spring:message code="csm.cnslmng.layer.title.npay"/></caption>
							<colgroup>
								<col style="width:58px">
								<col style="width:70px">
								<col style="width:159px">
								<col style="width:159px">
								<col style="width:88px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:18px">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" title="가산금"><spring:message code="rpm.incentive"/><br title="청구월"><spring:message code="rpm.billMonth"/></th>
									<th scope="col" title="거래 일시"><spring:message code="rpm.trdDt"/></th>
									<th scope="col" title="가맹점명"><spring:message code="rpm.cpNm"/></th>
									<th scope="col" title="상품명"><spring:message code="csm.cnslmng.th.goodsname"/></th>
									<th scope="col" title="휴대폰 번호"><spring:message code="rpm.mphnNo"/></th>
									<th scope="col" title="거래 금액"><spring:message code="rpm.trd.price"/></th>
									<th scope="col" title="수납 금액"><spring:message code="rpm.acceptance.price"/></th>
									<th scope="col" title="미납 금액"><spring:message code="rpm.outstanding.price"/></th>
									<th scope="col" title="가산 금액"><spring:message code="rpm.added.price"/></th>
									<th scope="col" title="상태"><spring:message code="rpm.state"/></th>
									<th scope="col"></th>
								</tr>
							</thead>
						</table>
					</div>
					<!-- // grid_head-->
					
					<!-- grid_body-->
					<div class="grid-body" style="height: 330px;">
						<table>
							<caption><spring:message code="csm.cnslmng.layer.title.npay"/></caption>
							<colgroup>
								<col style="width:58px">
								<col style="width:70px">
								<col style="width:159px">
								<col style="width:159px">
								<col style="width:88px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:78px">
								<col style="width:78px">
							</colgroup>
							<tbody id="result">
							</tbody>
							<tfoot id="npayHisListTotal" style="display:none;">
								<tr>
									<td colspan="5" class="txt-center"></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<!-- // grid_body-->
				</div>
				<!--// grid -->	

				<%-- template --%>
				<script type="text/html" id="tmpl-npayHisList">	
					{% for (var i=0; i<o.length; i++) { %}
						<tr>	
		    				<td class="txt-center">{%=Common.formatDateYYMM(o[i].billYm,".")%}</td>
							<td class="txt-center">{%=o[i].trdDd%}</td>
							<td class="txt-left">{%=Common.nvl(o[i].cpNm,"-")%}</td>
							<td class="txt-left">{%=o[i].godsNm%}</td>
							<td class="txt-center">{%=utils.hiddenToTel(o[i].mphnNo)%}</td>
							<td class="txt-right">{%=Common.formatMoney(o[i].acPayAmt)%}</td>
							<td class="txt-right">{%=Common.formatMoney(o[i].rcptAmt)%}</td>
							<td class="txt-right">{%=Common.formatMoney(o[i].npayAmt)%}</td>
							<td class="txt-right">{%=Common.formatMoney(o[i].billAmt)%}</td>
							<td class="txt-center">{%=o[i].trdStat%}</td>
						</tr>
					{% } %}	
				</script>

				<div class="btn-wrap">
					<button type="button" class="l-btn closePop" onclick="window.close()"  title="닫기"><spring:message code="common.button.close"/></button>
				</div>		
		</div>
	</div>
</body>
</html>
