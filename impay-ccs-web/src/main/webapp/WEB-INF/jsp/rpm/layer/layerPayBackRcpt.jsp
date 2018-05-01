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
	
	var layerPayBackRcpt = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath: '' };
		var uris = { 
					addPayBackRcpt : '/rpm/payBackRcpt/add',
					selectBox : '/api/code' 
					 };
		var messages;
		var forms;
		var context = '.fixed-layer'; //jquery selector context
		
		var commcClf;	// 이통사 구분
		var commcClf1;
		var pybkTypCd;	// 환불 유형 코드
		var bankCd;		// 은행코드
		
		/**
		 * public
		 */
		module.init = function(opts){
			options.contextPath = opts.contextPath;		
			forms = opts.forms;
			messages = opts.messages;
		};
		// SELECTBOX VALUE/NM SETTING
		module.selectBox = function(){
			/* 이통사 */
			impay.sendGet({
				requestUri: options.contextPath + uris.selectBox+"/ml/CKCC00",
				successCallback: function(data){
					commcClf = data;
					for(var i=0; i<commcClf.length; i++){
						$("#lyCommcClf").append("<option value="+commcClf[i].cd+">"+commcClf[i].cdNm+"</option>");
					}
				}
			});
			impay.sendGet({
				requestUri: options.contextPath + uris.selectBox+"/ml/CKMV00", 
				successCallback: function(data){
					commcClf1 = data;
						$("#lyCommcClf").append("<option value="+commcClf1[0].cd+">"+commcClf1[0].cdNm+"</option>");
				}
			});
			/* 환불 유형 */
			impay.sendGet({
				requestUri: options.contextPath + uris.selectBox+"/sl/RT0000",
				successCallback: function(data){
					pybkTypCd=data;
					for(var i=0; i<pybkTypCd.length; i++){
						$("#lyPybkTypCd").append("<option value="+pybkTypCd[i].cd+">"+pybkTypCd[i].cdNm+"</option>");
					}
				}
			});
			/* 은행명 */
			impay.sendGet({
				requestUri: options.contextPath + uris.selectBox+"/ml/BCBC00",
				successCallback: function(data){
					bankCd=data;
					for(var i=0; i<bankCd.length; i++){
						$("#bankCd").append("<option value="+bankCd[i].cd+">"+bankCd[i].cdNm+"</option>");
					}
				}
			});
		};
		// 환불 접수 등록
		module.addPayBackRcpt = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.addPayBackRcpt,
				data: $('#'+ forms.payBackForm, context).serialize(),
				dataType: 'json',
				successCallback: root.successInsert
			}); 
		};
		module.successInsert = function(data){
			if(data.message == "success"){
				alert('<spring:message code="common.alert.insert.succ"/>');
				layer.close();
			}else if(data.message == "commcClfError"){
				alert('<spring:message code="common.alert.noPhone"/>');
			}else if(data.message == "fail"){
				alert('<spring:message code="common.error.message"/>');
			}
			if(typeof reSearch == 'function'){
				reSearch();
				layer.close();
			}
		};
		return module;
	}(window.layerPayBackRcpt || {}, $));
	
	$(function() {
		var messages = {'noResult' 	: '<spring:message code="common.list.notice"/>'
						};
		var forms = { 'payBackForm': 'payBackForm'};
		layerPayBackRcpt.init({contextPath: '${contextPath}',
							forms: forms,
							messages: messages
							});
		var context = ".fixed-layer";
		
		layerPayBackRcpt.selectBox();
		$(".rcpt").datepicker("option", "maxDate", "+0d");
		$(".rcpt",".contents").datepicker("option", "minDate", new Date(2010, 1 - 1, 1));
		var today = DateUtil.getToday();
		today = Common.formatDateYYYYMMDD(today, ".");
		$("#lyRegDt", context).val(today);
		$("#lyPybkReqDd", context).val(today);
		
		$("#regBtn", context).on('click',function(){
			validateForPayBackRcpt();
		});
		
		// 휴대번호 입력 이벤트
		$("#mphnNo", context).keyup(function(){
			var pattern = /\d/g;
			if($(this).val() !== "" && pattern.test($(this).val()) === false){
				alert("<spring:message code='csm.cnslmng.msg.onlynumber'/>");
				$(this).val("");
				$(this).focus();
			}
		});
		// 계좌번호 입력 이벤트
		$("#acctNo", context).keyup(function(){
			var pattern = /\d/g;
			if($(this).val() !== "" && pattern.test($(this).val()) === false){
				alert("<spring:message code='rpm.payback.valid12'/>");
				$(this).val("");
				$(this).focus();
			}
		});
		// 환불금액 입력 이벤트
		$("#payAmt", context).keyup(function(e) {
			   var val = $(this).val().replace(/,/g, "");
			   var code = e.which ? e.which : e.keyCode;
			   if( !((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 8 || code == 46 || code == 37 || code == 39) ) {
			      alert("<spring:message code='rpm.payback.valid13'/>");
			      val = val.substring(0, val.length-1);
			   }
			   $(this).val(Common.formatMoney(val));
		});
	});
	
	function validateForPayBackRcpt() {
		$("#payBackForm", ".fixed-layer").validate({
			rules : { 
						regDt		: { required : true },
						commcClf 	: { required : true },
						pybkTypCd	: { required : true },
						pybkReqDd	: { required : true },
						mphnNo 		: { required : true, digits : true, rangelength: [10,11]},
						bankCd 		: { required : true },
						dpstrNm 	: { required : true },
						payAmt 		: { required : true	},
						acctNo 		: { required : true, digits : true, maxlength : 20 },
					},
			messages : {
						regDt		: { required : '<spring:message code="rpm.payback.valid1"/>' },
						commcClf 	: { required : '<spring:message code="rpm.payback.valid2"/>' },
						pybkTypCd	: { required : '<spring:message code="rpm.payback.valid3"/>' },
						pybkReqDd	: { required : '<spring:message code="rpm.payback.valid4"/>' },
						mphnNo 		: { required : '<spring:message code="rpm.payback.valid5"/>',
										digits	 : '<spring:message code="rpm.payback.valid6"/>',
										rangelength: "<spring:message code='csm.cnslmng.msg.check.length'/>"},
						bankCd 		: { required : '<spring:message code="rpm.payback.valid7"/>'},
						dpstrNm 	: { required : '<spring:message code="rpm.payback.valid8"/>'},
						payAmt 		: { required : '<spring:message code="rpm.payback.valid9"/>'},
						acctNo 		: { required : '<spring:message code="rpm.payback.valid10"/>',
										maxlength: '<spring:message code="rpm.payback.valid11"/>',
										digits	 : '<spring:message code="rpm.payback.valid12"/>'},
						},
			errorPlacement : function(error, element) {	},
			invalidHandler : function(form, validator) {
								var errors = validator.numberOfInvalids();
								if (errors) {
									alert(validator.errorList[0].message);
									validator.errorList[0].element.focus();
								}
							},
			submitHandler : function(form) {
						layerPayBackRcpt.addPayBackRcpt();
						}
			});
		$('#payBackForm').submit();
	}
</script>
<!-- search -->
			<div class="search-box">
				<form id="payBackForm" name="payBackForm">
				<fieldset>
				<legend title="환불 접수"><spring:message code="rpm.payback.title"/></legend>
				<table>
					<caption title="환불 접수"><spring:message code="rpm.payback.title"/></caption>
					<colgroup>
						<col style="width:12%">
						<col style="width:40%">
						<col style="width:12%">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="접수일"><spring:message code="rpm.rcptDt"/></th>
							<td>
								<div class="datebox">
									<input type="text" name="regDt" id="lyRegDt" class="input-text calendar rcpt" style="width:80px;">
									<a href="#"><span class="icon-cal"></span></a>
								</div>
							</td>
							<th scope="row" title="통신사"><spring:message code="rpm.commc1"/></th>
							<td>
								<select class="input-select" id="lyCommcClf" name="commcClf" style="width:205px">
									<option id="" value="" title="전체"><spring:message code="common.all"/></option>
								</select>
							</td>

						</tr>
						<tr>
							<th scope="row" title="환불 요청일"><spring:message code="rpm.payback.rcptDt"/></th>
							<td>
								<div class="datebox">
									<input type="text" name="pybkReqDd" id="lyPybkReqDd" class="input-text calendar" style="width:80px;">
									<a href="#"><span class="icon-cal"></span></a>
								</div>
							</td>
							<th scope="row" title="휴대폰 번호"><spring:message code="rpm.phoneNo"/></th>
							<td>
								<input type="text" name="mphnNo" id="mphnNo" class="input-text" style="width:200px;" placeholder="휴대폰 입력, '-' 없이 입력">
							</td>

						</tr>
						<tr>
							<th scope="row" title="환불 구분"><spring:message code="rpm.paybackClf"/></th>
							<td>
								<select class="input-select" id="lyPybkTypCd" name="pybkTypCd" style="width:205px;">
									<option value="" title="선택"><spring:message code="rpm.select"/></option>
								</select>
							</td>
							<th scope="row" title="이름"><spring:message code="rpm.name"/></th>
							<td><input type="text" name="payrNm" id="payrNm" class="input-text" style="width:210px" placeholder="이름 입력"></td>
						</tr>
						<tr>
							<th scope="row" title="환불 금액"><spring:message code="rpm.payback.amt"/></th>
							<td><input type="text" name="payAmt" id="payAmt" class="input-text" style="width:205px" placeholder="원 단위 입력"><label title="원"><spring:message code="common.won"/></label></td>
							<th scope="row" title="은행명"><spring:message code="rpm.payback.bank"/></th>
							<td>
								<select class="input-select" id="bankCd" name="bankCd" style="width:210px;">
									<option value="" title="선택"><spring:message code="rpm.select"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" title="계좌 번호"><spring:message code="rpm.payback.acctNo"/></th>
							<td><input type="text" name="acctNo" id="acctNo" class="input-text" style="width:210px" placeholder="'-'없이 입력"></td>
							<th scope="row" title="예금주"><spring:message code="rpm.payback.dpstr"/></th>
							<td><input type="text" name="dpstrNm" id="dpstrNm" class="input-text" style="width:210px" placeholder="이름 입력"></td>
						</tr>
					</tbody>
				</table>
				</fieldset>
				</form>
			</div>
			<!--// search -->
			
			<!-- btn -->
			<div class="btn-wrap">
				<button type="button" class="l-btn cmpl" id="regBtn" title="등록"><spring:message code="rpm.insert"/></button>
				<button type="button" class="l-btn closePop" onclick="layer.close()" title="취소"><spring:message code="rpm.cancel"/></button>
			</div>
			<!--// btn -->		