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
	
	var layerAddPayWhiteList = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath: '' };
		var uris = { 
					saveRegRsn : '/rpm/addPayWhiteListMng/saveRegRsn',
					selectBox : '/api/code' 
					 };
		var messages;
		var forms;
		var context; //jquery selector context
		
		var regClfCd;
		/**
		 * public
		 */
		module.init = function(opts){
			options.contextPath = opts.contextPath;		
			forms = opts.forms;
			messages = opts.messages;
			context = opts.context;
		};
		// selectBox
		module.selectBox = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.selectBox+"/ml/NWNW00",
				successCallback: function(data){
					regClfCd=data;
					for(var i=0; i<regClfCd.length; i++){
						$("#lyRegClfCd option:eq(0)").after("<option value="+regClfCd[i].cd+">"+regClfCd[i].cdNm+"</option>");
					}
				}
			});
		};
		// 등록 사유 등록
		module.saveRegRsn = function(){
			// 휴대폰 번호 check
			if(StringUtil.isEmpty($("#mphnNo", context).val())){
				alert("<spring:message code='rpm.addPayMng.valid1'/>");
				return $("#mphnNo",context).focus();
			}
			if(StringUtil.isEmpty($("#lyRegClfCd", context).val())){
				alert("<spring:message code='rpm.addPayMng.valid2'/>");
				return $("#lyRegClfCd",context).focus();
			}
			if(StringUtil.isEmpty($("#regRsn", context).val())){
				alert("<spring:message code='rpm.addPayMng.valid3'/>");
				return $("#regRsn",context).focus();
			}
			impay.sendPost({
				requestUri: options.contextPath + uris.saveRegRsn,
				data: $('#'+ forms.regRsnForm, context).serialize(),
				dataType: 'json',
				successCallback: root.successRegRsn
			});
		};
		module.successRegRsn = function(data){
			if(data.message == "success"){			// 성공
				alert(messages.succMsg);
			}else if(data.message == "mphNoError"){ // 없는 핸드폰 번호
				alert(messages.noPhone);
			}else if(data.message == "dataCheck"){	// 이미 등록된 데이터
				alert(messages.overLapData);
			}else if(data.message == "fail"){		// 실패
				alert(messages.errorMsg);
			}
			var result = {message : data.message};
			layer.close(result);
		};
		
		return module;
	}(window.layerAddPayWhiteList || {}, $));
	
	$(function() {
		var messages = {'noResult' : '<spring:message code="common.list.notice"/>',
						'succMsg' : '<spring:message code="common.alert.insert.succ"/>',
						'errorMsg' : '<spring:message code="common.error.message"/>',
						'noPhone' : '<spring:message code="common.alert.noPhone"/>',
						'overLapData' : '<spring:message code="common.alert.overlapData"/>'
						};
		var forms = { 'regRsnForm': 'regRsnForm'};
		layerAddPayWhiteList.init({contextPath: '${contextPath}',
							context:".fixed-layer",
							forms: forms,
							messages: messages
							});
		var context = ".fixed-layer";
		
		layerAddPayWhiteList.selectBox();
		
		var today = DateUtil.getToday();
		today = Common.formatDateYYYYMMDD(today, ".");
		$("#regDt",context).html(today);	// 오늘 날짜
		$("#regr", context).html($("#userNm",".contents").val()); // 접속한 상담원 이름
		
		// 등록사유 byte 체크
		$("#regRsn", context).keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			$("#ctntCount", context).text(ctntByte);
			if(ctntByte > 200){
				$(this).val(Common.getStringToByte($(this).val(), 200));
				alert('<spring:message code="rpm.addPayMng.alert"/>');
				$("#ctntCount", context).text(Common.getByteString($(this).val()));
			}
		});
		// 휴대폰 번호 입력 이벤트
		$("#mphnNo", context).keyup(function(e) {
			var pattern = /\d/g;
			if($(this).val() !== "" && pattern.test($(this).val()) === false){
			    alert("<spring:message code='csm.cnslmng.msg.onlynumber'/>");
			    $(this).val("");
				$(this).focus();
			}
		});
		$("#saveBtn").click(function(){
			layerAddPayWhiteList.saveRegRsn();
		});
		
	});
	
</script>
			<!-- search -->
			<div class="search-box">
				<form id="regRsnForm" name="regRsnForm">
				<fieldset>
				<legend title="가산금 White List 등록"><spring:message code="rpm.addPayMng.insert"/></legend>
				<table>
					<caption title="가산금 White List 등록"><spring:message code="rpm.addPayMng.insert"/></caption>
					<colgroup>
						<col style="width:17%">
						<col style="width:30%">
						<col style="width:17%">
						<col style="width:*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="등록 번호"><spring:message code="rpm.addPayMng.regNo"/></th>
							<td>-</td>
							<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
							<td>
								<input type="text" name="payrNm" id="payrNm" class="input-text" style="width:180px;" placeholder="이름을 입력해 주세요">
							</td>
						</tr>
						<tr>
							<th scope="row" title="등록 일자"><spring:message code="rpm.addPayMng.regDd"/></th>
							<td id="regDt"></td>
							<th scope="row" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
							<td>
								<input type="text" name="mphnNo" id="mphnNo" class="input-text" maxlength="11" style="width:180px;" placeholder="<spring:message code="rpm.addPayMng.placeholder1"/>">
							</td>
						</tr>
						<tr>
							<th scope="row" title="등록 구분"><spring:message code="rpm.addPayMng.regClf"/></th>
							<td>
								<select class="input-select" id="lyRegClfCd" name="regClfCd" style="width:120px;">
									<option value="" title="선택"><spring:message code="rpm.select"/></option>
								</select>
							</td>
							<th scope="row" title="상담원"><spring:message code="rpm.addPayMng.adviser"/></th>
							<td id="regr"></td>
						</tr>
						<tr>
							<th scope="row" title="등록 사유"><spring:message code="rpm.addPayMng.regReason"/><br>(<span class="em" id="ctntCount">0</span>/200byte)</th>
							<td colspan="3">
								<textarea name="regRsn" id="regRsn" class="textarea" rows="1" cols="1" style="width:96%;height:100px" placeholder=<spring:message code="rpm.addPayMng.placeholder"/>></textarea>
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
				<button type="button" class="l-btn close cmpl" id="saveBtn" title="저장"><spring:message code="common.button.save"/></button>
			</div>
			<!--// btn -->			
		