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
<link type="text/css" rel="stylesheet" href="${resources}/css/popup.css" />
<script type="text/javascript">
	var parentModule;

	var layerAnnoDesc = (function(module, $){
		"use strict";

		/**
		 * private
		 */
		var root = module;
		var options = { contextPath: '' };
		var uris = { selectBox : '/api/code',
					 search:'/sysm/annoDescRegMng/search',
					 annoDescInfo: '/sysm/annoDescRegMng/annoDescInfo'};
		var messages;
		var forms;
		var context = '.fixed-layer'; //jquery selector context

		var bordClfCd;
		/**
		 * public
		 */
		module.init = function(opts){
			options.contextPath = opts.contextPath;
			forms = opts.forms;
			messages = opts.messages;
		};
		module.selectBox = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.selectBox+"/ml/BDCS00",
				successCallback: function(data){
					bordClfCd = data;
					root.bindSelectBox(bordClfCd);		
				}
			});
			root.search();
		};
		module.bindSelectBox = function(bordClfCd){
			for(var i=0; i<bordClfCd.length; i++){
				$("#bordClfCd option:eq(0)").after("<option value="+bordClfCd[i].cd+">"+bordClfCd[i].cdNm+"</option>");
			}
		};
		// 공지사항 조회
		module.search = function(){
			// 조회 기간 조건 체크(최대 12개월)
	        var sttDd = $("#stDate",context).val();
	        var endDd = $("#endDate",context).val();
	        if( (sttDd !== null && sttDd !== '') && (endDd !== null && endDd !== '') ) {
	           var result = DateUtil.getDiffDate(sttDd, endDd);
	           if(result > 365) {
	              alert(messages.dateCheck1);
	              return;
	           }else if(result < 0 ){
	        	   alert(messages.dateCheck2);
	           		return;   
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
			$("#impay-csm-layer-annoDesc .pagination-wrap", context).css({'display': 'block'});
			if(data.total > 0){
				$("#info", context).show();
				$('#result', context).html(tmpl("tmpl-annoDescList" , data.content));
				$("#pagination", context).paging({
		        	totalCount: data.total,
		        	pageSize: $('#rowCount', context).val(),
		        	pageNo: $("#pageIndex", context).val(),
		        	onSelectPage: root.goPage
		        });
				root.getAnnoDescInfo(data.content[0].clctBordSeq);
	    	}else{
	    		$("#info", context).hide();
	    		$("#impay-csm-layer-annoDesc #result", ".fixed-layer").html("<tr><td colspan='4' align='center'>"+messages.noResult+"</td><tr>");
				$("#impay-csm-layer-annoDesc .pagination-wrap", context).css({'display': 'none'});
	    	}
		};
		module.goPage = function(pageNo){
			$('#pageIndex','.fixed-layer').val(pageNo);
			root.search();
		};
		// 공지 상세 내역
		module.getAnnoDescInfo = function(clctBordSeq){
			impay.sendGet({
				requestUri: options.contextPath + uris.annoDescInfo + "/" + clctBordSeq,
				successCallback: root.bindAnnoDescInfo
			});
		};
		module.bindAnnoDescInfo = function(data){
			$("#titl",context).html(data.titl);
			$("#regDt",context).html(data.regDt);
			$("#info #bordClfCd").html(data.bordClfCdNm);
			$("#regr",context).html(data.regr);
			$("#ctnt",context).val(data.ctnt);
		};
		return module;
	}(window.layerAnnoDesc || {}, $));

	$(function() {
		var messages = {'noResult' : '<spring:message code="common.list.notice"/>',
						'dateCheck1' : '<spring:message code="common.alert.termOver"/>',
						'dateCheck2' : '<spring:message code="common.alert.termError"/>'};
		var forms = { 'searchForm': 'frmSearch'};
		layerAnnoDesc.init({contextPath: '${contextPath}',
							forms: forms,
							messages: messages});
		// 조회 기간 셋팅
		var today = DateUtil.getToday();
		today = today.replace(/\./g, "");
		$("#endDate",'.fixed-layer').val(Common.formatDateYYYYMMDD(today, "."));
		var tempDt;
		var resultDt;
		tempDt = new Date(today.substring(0,4), (today.substring(4,6)-7), Number(today.substring(6,8))+1);
		resultDt = tempDt.getFullYear().toString() + "." + Common.lpad((tempDt.getMonth() + 1), "0", "2") + "." + Common.lpad(tempDt.getDate(), "0", "2");
		$("#stDate",'.fixed-layer').val(resultDt);

		layerAnnoDesc.selectBox();
		
		$(".calendar").datepicker("option", "maxDate", "+0d");
		$("#btnSearch").on('click', function(){
			$("#pageIndex", '.fixed-layer').val("1");
			layerAnnoDesc.search();
		});

	});

</script>
<!-- search -->
		<div class="search-box">
			<fieldset>
			<legend title="공지사항 조회"><spring:message code="sysm.annoDescReg.search"/></legend>
			<form id="frmSearch">
			<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"  >
			<input type="hidden" id="rowCount"  name="pageParam.rowCount"  value="5" >
			<input type="hidden" id="clctBordSeq"  name="clctBordSeq" >
			<table>
				<caption title="공지사항 조회"><spring:message code="sysm.annoDescReg.search"/></caption>
				<colgroup>
					<col style="width:12%">
					<col style="width:38%">
					<col style="width:12%">
					<col style="width:*">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" title="조회 기간"><spring:message code="common.inquery.term"/></th>
						<td>
							<div class="datebox">
								<input type="text" name="stDate" id="stDate" class="input-text calendar" style="width:80px;">
								<a href="#none"><span class="icon-cal"></span></a>
							</div>
							<span class="hyphen">~</span>
							<div class="datebox">
								<input type="text" name="endDate" id="endDate" class="input-text calendar" style="width:80px;">
								<a href="#none"><span class="icon-cal"></span></a>
							</div>
						</td>
						<th scope="row" title="유형별"><spring:message code="sysm.types"/></th>
						<td>
							<select class="input-select" name="bordClfCd" id="bordClfCd" style="width:100px;">
								<option value="" title="전체"><spring:message code="common.option.all"/></option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row" title="제목+내용"><spring:message code="sysm.annoDescReg.title.ctnt"/></th>
						<td colspan="3">
							<input class="input-text" style="width:98%;" type="text" id="srchText" name="srchText" placeholder="<spring:message code='sysm.palceholder'/>">
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			</fieldset>
		</div>
		<!--// search -->
		<!-- btn -->
		<div class="btn-wrap">
			<button type="button" class="l-btn type02" id="btnSearch" title="조회"><spring:message code="common.button.search"/></button>
		</div>
		<!--// btn -->

		<div class="panel-box" id="impay-csm-layer-annoDesc">
			<div class="tbl-info">
				<h4 class="cont-h4" title="공지 리스트"><spring:message code="sysm.anno.list"/></h4>
			</div>
			<!-- grid -->
			<div class="tbl-grid-box">
				<table>
					<caption title="공지 리스트"><spring:message code="sysm.anno.list"/></caption>
					<colgroup>
						<col style="width:15%">
						<col style="width:*">
						<col style="width:8%">
						<col style="width:8%">
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
					<tr onclick="layerAnnoDesc.getAnnoDescInfo('{%=o[i].clctBordSeq%}')">	
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
		<div class="panel-box sec-t20" id="info" >
			<!-- table -->
			<div class="tbl-box">
				<table>
					<caption title="신규등록"><spring:message code="common.button.new"/></caption>
					<colgroup>
						<col style="width:12%">
						<col style="width:21%">
						<col style="width:12%">
						<col style="width:21%">
						<col style="width:12%">
						<col style="width:*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="제목"><spring:message code="sysm.title"/></th>
							<td colspan="5" id="titl"></td>
						</tr>
						<tr>
							<th scope="row" title="날짜"><spring:message code="sysm.date"/></th>
							<td id="regDt"></td>
							<th scope="row" title="유형"><spring:message code="sysm.type"/></th>
							<td id="bordClfCd"></td>
							<th scope="row" title="등록자"><spring:message code="sysm.regr"/></th>
							<td id="regr"></td>
						</tr>
						<tr>
							<th scope="row" title="내용"><spring:message code="common.email.label.content"/></th>
							<td colspan="5">
								<textarea name="ctnt" id="ctnt" class="textarea" rows="1" cols="1" style="width:98%"  placeholder="" readonly="readonly"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--// table -->

		</div>
		<div class="btn-wrap">
			<button type="button" class="l-btn closePop" onclick="layer.close()" title="닫기"><spring:message code="common.button.close"/></button>
		</div>
