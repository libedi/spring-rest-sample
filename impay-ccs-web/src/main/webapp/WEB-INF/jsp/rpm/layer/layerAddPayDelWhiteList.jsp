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
	
	var layerDelAddPayWhiteList = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath: '' };
		var uris = { 
					selectBox : '/api/code',
					addPayWhiteListInfo : '/rpm/addPayWhiteListMng/info',
					saveDelRsn : '/rpm/addPayWhiteListMng/saveDelRsn'
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
		module.getInfo = function(){
			var wlRegNo = $("#wlRegNo", ".contents").val();
			impay.sendGet({
				requestUri: options.contextPath + uris.addPayWhiteListInfo + "/" + wlRegNo,
				successCallback: root.addPayWhiteListInfo
			});
		};
		// layer init 이후 선택한 행의 데이터 조회
		module.addPayWhiteListInfo = function(data){
			$("input:hidden[name='wlRegNo']").val(data.wlRegNo);
			var today = DateUtil.getToday();
			today = Common.formatDateYYYYMMDD(today, ".");
			$("#wlRegNo", context).html(data.wlRegNo);
			$("#payrNm", context).html(data.payrNm);
			$("#mphnNo", context).html(utils.hiddenToTel(data.mphnNo));
			$("#regDt", context).html(data.regDt);
			$("#delDt", context).html(today);
			$("#regClfCd", context).html(data.regClfCd);
			$("#regRsn", context).html(data.regRsn);
			$("#delRsn", context).html(data.delRsn);
			$("#regr", context).html(data.regr);
			$("#regCtnt",context).html(Common.getByteString(data.regRsn));
		};
		
		module.saveDelRsn = function(){
			if(Common.isEmpty($("#delRsn",context).val())){
				alert(messages.validMsg1);
				return;
			};
			impay.sendPost({
				requestUri: options.contextPath + uris.saveDelRsn,
				data: $('#'+ forms.delRsnForm, context).serialize(),
				dataType: 'json',
				successCallback: root.successDelRsn
			});
		};
		module.successDelRsn = function(data){
			if(data.message == "success"){
				alert(messages.succMsg);
			}
			var result = {message : data.message};
			layer.close(result);
		};
		return module;
	}(window.layerDelAddPayWhiteList || {}, $));
	
	$(function() {
		var messages = {'noResult' : '<spring:message code="common.list.notice"/>',
						'succMsg' : '<spring:message code="common.alert.insert.succ"/>',
						'validMsg1' : '<spring:message code="rpm.addPayMng.valid4"/>'};
		var forms = { 'delRsnForm': 'delRsnForm'};
		layerDelAddPayWhiteList.init({contextPath: '${contextPath}',
							context:".fixed-layer",
							forms: forms,
							messages: messages
							});
		layerDelAddPayWhiteList.getInfo();
		// byte 계산
		$("#delRsn",".fixed-layer").keyup(function(){ 
			var ctntByte = Common.getByteString($(this).val());
			$("#ctntCount",".fixed-layer").text(ctntByte);
			if(ctntByte > 200){
				$(this).val(Common.getStringToByte($(this).val(), 200));
				alert('<spring:message code="rpm.addPayMng.alert"/>');
				$("#ctntCount",".fixed-layer").text(Common.getByteString($(this).val()));
			}
		});
		$("#saveBtn").on('click', function(){
			layerDelAddPayWhiteList.saveDelRsn();
		});
	});
	
</script>
<!-- search -->
			<div class="search-box" >
				<form id="delRsnForm" name="delRsnForm">
				<input type="hidden" id="wlRegNo" name="wlRegNo"/>
				<fieldset>
				<legend title="가산금 White List 삭제 사유 등록"><spring:message code="rpm.addPayMng.delReason.insert"/></legend>
				<table>
					<caption title="가산금 White List 삭제 사유 등록"><spring:message code="rpm.addPayMng.delReason.insert"/></caption>
					<colgroup>
						<col style="width:24.5%">
						<col style="width:34%">
						<col style="width:17%">
						<col style="width:*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="등록 번호"><spring:message code="rpm.addPayMng.regNo"/></th>
							<td id="wlRegNo"></td>
							<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
							<td id="name"></td>
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
							<th scope="row" title="등록 사유"><spring:message code="rpm.addPayMng.regReason"/><br>(<span class="em" id="regCtnt">0</span>/200byte)</th>
							<td colspan="3">
								<textarea id="regRsn" class="textarea" rows="1" cols="1" style="width:96%;height:100px" readonly="readonly"></textarea>
							</td>
						</tr>
						<tr>
							<th scope="row" title="삭제 사유"><spring:message code="rpm.addPayMng.delReason"/><br>(<span class="em" id="ctntCount">0</span>/200byte)</th>
							<td colspan="3">
								<textarea name="delRsn" id="delRsn" class="textarea" rows="1" cols="1" style="width:96%;height:100px" placeholder=<spring:message code="rpm.addPayMng.placeholder"/>></textarea>
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
		