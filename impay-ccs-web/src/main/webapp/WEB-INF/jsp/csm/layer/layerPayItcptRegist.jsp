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
<link type="text/css" rel="stylesheet" href="${resources}/css/popup.css" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
<sec:authentication var="user" property="principal" />
<script type="text/javascript">
	var payItcptlayer = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				view : "/cnslMng/payItcpt/view",
				checkUser : "/user/search/payMphnInfo",
				save : "/cnslMng/save/payItcpt"
		};
		var messages;
		var context;	//jquery selector context
		var data;
		var forms;
		var parentModule;
		
		/**
		 * public 
		 */	
		module.init = function(opts){
			options.contextPath = opts.contextPath;
			forms = opts.forms;
			messages = opts.messages;
			context = opts.context;
			data = opts.data;
			parentModule = opts.parentModule;
		};
		module.getOptions = function(){
			return root.options;
		};
		
		// 폼 데이터 셋팅
		module.view = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.view,
				dataType: "json",
				successCallback : function(result){
					if(result !== null){
						var html = "";
						for(var i=0; i<result.length; i++){
							html += "<option value='" + result[i].cd + "'>" + result[i].cdNm + "</option>";
						}
						$("#commcClf", context).append(html);
					} else {
						alert(messages.error);
					}
				}
			});
		};
		
		// 사용자 검사
		module.checkUser = function(){
			// 거래 고객 또는 미거래 고객이지만 기존 상담내역이 있는 고객
			if($("#payMphnId", context).val() !== ""){
				impay.sendPost({
					requestUri: options.contextPath + uris.checkUser,
					data: {payMphnId : $("#payMphnId", context).val()},
					dataType: "json",
					successCallback : function(result){
						if(result.success === true){
							if(result.result !== null){
								if(result.result.brthYmdFull !== null && $("#brthYmd", context).val() !== result.result.brthYmdFull){
									alert(messages.checkBrthday);
									return;
								}
							} else if($("#commcClf > option:selected").val() === ""){	// 미거래 고객 등록시
								alert(messages.noReqr);
								return;
							} 
							root.save();
						} else {	
							alert(messages.error);
						}
					}
				});
			} else {
				// 상담 내역도 없는 미거래 고객
				root.save();
			}
		};
		// 등록
		module.save = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.save,
				data: $("#" + forms.saveForm, context).serialize(),
				dataType: "json",
				successCallback : function(result){
					alert(result.message);
					if(result.success === true){
						var retObj = {
							page : 1,
							payMphnId : result.result.payMphnId
						};
						layer.close(retObj);
					}
				}
			});
		};
		
		return module;
	}(window.payItcptlayer || {}, $));
	
	$(function(){
		var data = layer.getOptions().data;
		var messages = {
				"error" : "<spring:message code='common.error.message'/>",
				"noReqr" : "<spring:message code='csm.cnslmng.layer.itcpt.noreqr'/>",
				"checkBrthday" : "<spring:message code='csm.cnslmng.layer.itcpt.check.brthday'/>"
		};
		var forms = {saveForm: "saveForm"};
		payItcptlayer.init({
						contextPath: layer.getOptions().contextPath, 
						context: ".fixed-layer",
						forms: forms,
						messages: messages,
						data: data,
						parentModule: layer.getOptions().parentModule
		});
		
		$(".calendar", ".fixed-layer").datepicker("option", "minDate", null);
		
		// 고객정보 셋팅
		$("#payMphnId", ".fixed-layer").val(data.payMphnId);
		$("#payrSeq", ".fixed-layer").val(data.payrSeq);
		$("#mphnNo", ".fixed-layer").val(data.mphnNo);
		
		// 미거래 고객이 아닌 경우, 통신사 선택 비활성화
		if($("#payMphnId", ".fixed-layer").val() !== "" && $("#payrSeq", ".fixed-layer").val() !== "9999999999"){
			$(".commcTr", ".fixed-layer").empty();
		} else {
			// 폼 데이터 설정
			payItcptlayer.view();
		}
		
		// 차단기준 선택 이벤트
		$("#itcptClfFlg", ".fixed-layer").change(function(){
			var custInfo = "";
			if($(this).val() == "U"){
				// 미거래 고객은 휴대폰번호로만 등록가능
				if($("#payMphnId", ".fixed-layer").val() === "" || $("#payrSeq", ".fixed-layer").val() === "9999999999"){
					alert("<spring:message code='csm.cnslmng.layer.itcpt.msg.noPayrseq'/>");
					$(this).find("option").each(function(){
						if($(this).val() === ""){
							$(this).prop("selected", true);
							return false;
						}
					});
					$("#itcptClfFlgVal", ".fixed-layer").val("");
					return false;
				}
				custInfo = data.payrSeq;
			} else if($(this).val() == "P"){
				custInfo = data.mphnNo;
			}
			$("#itcptClfFlgVal", ".fixed-layer").val(custInfo);
		});
		
		// 차단사유 바이트 제한
		$("#procRsn", ".fixed-layer").keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			if(ctntByte > 100){
				alert("<spring:message code='csm.cnslmng.msg.limit.byte' arguments='100'/>");
				$(this).val(Common.getStringToByte($(this).val(), 100));
			}
		});
		
		// 등록버튼 이벤트
		$("#saveBtn", ".fixed-layer").click(function(){
			utils.applyTrim("saveForm");
			save();
		});
		
		$(".closePop", "fixed-layer").click(layer.close);
	});
	
	function save(){
		$("#saveForm", ".fixed-layer").validate({
	    	debug: true,
			onfocusout: false,
	        rules: {
	        	itcptReqrClfFlg : { required : true },
	        	itcptReqrNm : { required : true },
	        	itcptClfFlg : { required : true },
	        	procClfFlg : { required : true },
	        	procRsn : { required : true },
	        	brthYmd : { required : true },
	        	commcClf : {
	        		required : {
	        			depends : function(element){
	        				return ($("#payMphnId", ".fixed-layer").val() === "" || $("#payrSeq", ".fixed-layer").val() === "9999999999");
		        		}
	        		}
	        	}
			},
			messages: {
				itcptReqrClfFlg: {
	            	required: "<spring:message code='csm.cnslmng.layer.itcpt.msg.select.blockreqr'/>"
				},
				itcptReqrNm: {
	            	required: "<spring:message code='csm.cnslmng.layer.itcpt.msg.select.blockreqrnm'/>"
				},
				itcptClfFlg: {
	            	required: "<spring:message code='csm.cnslmng.layer.itcpt.msg.select.blockstand'/>"
				},
				procClfFlg: {
	            	required: "<spring:message code='csm.cnslmng.layer.itcpt.msg.select.blockyn'/>"
				},
				procRsn: {
	            	required: "<spring:message code='csm.cnslmng.layer.itcpt.msg.select.blockreason'/>"
				},
				brthYmd: {
					required: "<spring:message code='csm.cnslmng.layer.itcpt.msg.select.brthday'/>",
				},
				commcClf: {
					required: "<spring:message code='csm.cnslmng.layer.itcpt.noreqr'/>"
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
	        	payItcptlayer.checkUser();
			},
	        showErrors: function(errorMap, errorList) {
	        }
		});
		
		if(confirm("<spring:message code='csm.cnslmng.layer.itcpt.msg.confirm.itcptreg'/>")){
			$("#saveForm", ".fixed-layer").submit();
		}
	}
</script>
<div class="tbl-info">
	<!-- comment -->
	<span class="commt"><span class="ico-required">*<span class="txt" title="표시는 필수 항목"><spring:message code="common.indication"/></span></span></span>
	<!--// comment -->
</div>
<div class="tbl-box">
	<form id="saveForm">
		<input type="hidden" name="payMphnId" id="payMphnId" value=""/>
		<input type="hidden" name="payrSeq" id="payrSeq" value=""/>
		<input type="hidden" name="mphnNo" id="mphnNo" value=""/>
		<table>
			<caption title="결제 차단 등록"><spring:message code="csm.cnslmng.layer.itcpt.caption1"/></caption>
			<colgroup>
				<col style="width:15%">
				<col style="width:40%">
				<col style="width:14%">
				<col style="width:*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="차단요청자"><span class="ico-required">*<span class="hide" title=""><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.layer.itcpt.th.itcptReqr"/></th>
					<td><select class="input-select" name="itcptReqrClfFlg" id="itcptReqrClfFlg" style="width:100px">
							<option value="" title="선택"><spring:message code="common.select.select"/></option>
							<option value="I" title="본인"><spring:message code="csm.cnslmng.layer.itcpt.option.self"/></option>
							<option value="F" title="가족"><spring:message code="csm.cnslmng.layer.itcpt.option.family"/></option>
							<option value="E" title="기타"><spring:message code="csm.cnslmng.layer.itcpt.option.etc"/></option>
						</select> <input type="text" class="input-text" name="itcptReqrNm" id="itcptReqrNm" style="width:140px">
					</td>
					<th scope="row" title="등록 일자"><spring:message code="csm.cnslmng.layer.itcpt.th.date.reg"/></th>
					<td></td>
				</tr>
				<tr>
					<th scope="row" title="차단 기준"><span class="ico-required">*<span class="hide" title="필수"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.th.block.stand"/></th>
					<td><select class="input-select" name="itcptClfFlg" id="itcptClfFlg" style="width:100px">
							<option value="" title="선택"><spring:message code="common.select.select"/></option>
							<option value="U" title="고객 번호"><spring:message code="csm.cnslmng.th.custno"/></option>
							<option value="P" title="휴대폰 번호"><spring:message code="csm.cnslmng.th.telno"/></option>
						</select> <input type="text" class="input-text" name="itcptClfFlgVal" id="itcptClfFlgVal" style="width:140px"  readonly="readonly"></td>
					<th scope="row" title="입력자"><spring:message code="csm.cnslmng.th.regr"/></th>
					<td><c:out value="${user.userNm}"/></td>
				</tr>
				<tr>
					<th scope="row" title="결제 차단 여부"><span class="ico-required">*<span class="hide" title="필수"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.th.block.yn"/></th>
					<td><select class="input-select" name="procClfFlg" id="procClfFlg" style="width:100px">
							<option value="R" title="차단"><spring:message code="csm.cnslmng.data.itcpt.procflg.reg"/></option>
						</select> <input type="text" class="input-text" name="procClfFlgVal" id="procClfFlgVal" readonly="readonly" style="width:140px" value="<spring:message code='csm.cnslmng.data.itcpt.procflg.reg'/>"></td>
					<th scope="row" title="생년 월일"><span class="ico-required">*<span class="hide" title=""><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.th.birthday"/></th>
					<td><div class="datebox">
						<input type="text" name="brthYmd" id="brthYmd" class="input-text calendar calendar-layer" style="width:80px;" readonly="readonly">
						<a href="#none"><span class="icon-cal"></span></a>
					</div></td>
				</tr>
				<tr>
					<th class="commcTr" scope="row" title="통신사"><spring:message code="csm.cnslmng.th.commc"/></th>
					<td class="commcTr">
						<select class="input-select" name="commcClf" id="commcClf" style="width:100px">
							<option value="" title="선택"><spring:message code="common.select.select"/></option>
						</select>
					</td>
					<th scope="row" title="이름"><spring:message code="csm.cnslmng.layer.itcpt.th.name"/></th>
					<td><input type="text" class="input-text" name="payrNm" id="payrNm" placeholder="<spring:message code='csm.cnslmng.layer.itcpt.placeholder2'/>" style="width:140px"></td>
				</tr>
				<tr>
					<th scope="row" title="차단 사유"><span class="ico-required">*<span class="hide" title="필수"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.layer.itcpt.th.blockreason"/></th>
					<td colspan="3"><textarea style="width:97%; height:80px" name="procRsn" id="procRsn" class="textarea" placeholder="<spring:message code='csm.cnslmng.layer.itcpt.placeholder1'/>"></textarea></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div class="btn-wrap">
	<button type="button" class="l-btn closePop cmpl" id="saveBtn" title="등록"><spring:message code="csm.cnslmng.layer.itcpt.btn.reg"/></button>
</div>