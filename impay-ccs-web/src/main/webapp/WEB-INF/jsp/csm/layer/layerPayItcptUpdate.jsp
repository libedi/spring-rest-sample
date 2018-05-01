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
				search : "/cnslMng/search/payItcpt",
				update : "/cnslMng/update/payItcpt"
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
						root.search();
					} else {
						alert(messages.error);
					}
				}
			});
		};
		// 조회
		module.search = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.search,
				data: {itcptRegSeq : data.itcptRegSeq},
				dataType: "json",
				successCallback: root.resultBind
			});
		};
		// 출력
		module.resultBind = function(result){
			$("#itcptRegSeq", context).val(result.itcptRegSeq);
			$("#payMphnId", context).val(result.payMphnId);
			$("#payrSeq", context).val(result.payrSeq);
			$("#mphnNo", context).val(result.mphnNo);
			$("#brthYmd", context).val(result.brthYmd);
			$("#payrNm", context).val(result.payrNm);
			$("#regDt", context).text(result.regDt);
			$("#regr", context).text(result.regrNm);
			if(result.updateYn === "Y"){
				$("#lastChgDt", context).text(result.lastChgDt);
				$("#lastChgr", context).text(result.lastChgrNm);
			} else {
				$("#lastChgDt", context).text("");
				$("#lastChgr", context).text("");
			}
			$("#itcptReqrClfFlg > option", context).each(function(){
				if($(this).val() === result.itcptReqrClfFlg){
					$(this).prop("selected", true);
					return false;
				}
			});
			$("#itcptReqrNm", context).val(result.itcptReqrNm);
			$("#itcptClfFlg > option", context).each(function(){
				if($(this).val() === result.itcptClfFlg){
					$(this).prop("selected", true);
					if(result.itcptClfFlg == "U"){
						$("#itcptClfFlgVal").val(result.payrSeq);
					} else if(result.itcptClfFlg === "P"){
						$("#itcptClfFlgVal").val(result.mphnNo);
					}
					return false;
				}
			});
			$("#procClfFlg > option", context).each(function(){
				if($(this).val() === result.procClfFlg){
					$(this).prop("selected", true);
					$("#procClfFlgVal", context).val($(this).text());
					return false;
				}
			});
			if($("#payMphnId", context).val() === "" || $("#payrSeq", context).val() === "9999999999"){
				$("#commcClf > option", context).each(function(){
					if($(this).val() === result.commcClf){
						$(this).prop("selected", true);
						return false;
					}
				});
			}
			$("#blockRsn", context).val(result.procRsn);
			$("#unblockRsn", context).val(StringUtil.nvl(result.itcptRelsRsn, ""));
			if(result.procClfFlg !== "R"){
				$("#updateBtn", context).hide();
				$("#confirmBtn", context).show();
				root.allDisabled();
			}
		};
		// 수정
		module.update = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.update,
				data: $("#" + forms.updateForm, context).serialize(),
				dataType: "json",
				successCallback : function(result){
					if(result.success === true){
						alert(messages.updateOk);
						layer.close(1);
					} else {
						alert(messages.error);
					}
				}
			});
		};
		// disabled
		module.allDisabled = function(){
			$("#itcptRegSeq", context).prop("disabled", true);
			$("#itcptClfFlgVal", context).prop("disabled", true);
			$("#procClfFlgVal", context).prop("disabled", true);
			$("#mphnNo", context).prop("disabled", true);
			$("#brthYmd", context).prop("disabled", true);
			$("#payrNm", context).prop("disabled", true);
			$("#itcptReqrClfFlg", context).prop("disabled", true);
			$("#itcptReqrNm", context).prop("disabled", true);
			$("#itcptClfFlg", context).prop("disabled", true);
			$("#procClfFlg", context).prop("disabled", true);
			if($("#payMphnId", context).val() === "" || $("#payrSeq", context).val() === "9999999999"){
				$("#commcClf", context).prop("disabled", true);
			}
			$("#blockRsn", context).prop("disabled", true);
			$("#unblockRsn", context).prop("disabled", true);
		};
		
		return module;
	}(window.payItcptlayer || {}, $));
	
	$(function(){
		var data = layer.getOptions().data;
		var messages = {
				"updateOk" : "<spring:message code='csm.cnslmng.layer.itcpt.msg.updateOk'/>",
				"error" : "<spring:message code='common.error.message'/>"
		};
		var forms = {updateForm: "updateForm"};
		payItcptlayer.init({
						contextPath: layer.getOptions().contextPath, 
						context: ".fixed-layer",
						forms: forms,
						messages: messages,
						data: data
		});
		
		// 고객정보 셋팅
		$("#payMphnId", ".fixed-layer").val(data.payMphnId);
		$("#payrSeq", ".fixed-layer").val(data.payrSeq);
		$("#mphnNo", ".fixed-layer").val(data.mphnNo);
		
		if($("#payMphnId", ".fixed-layer").val() !== "" && $("#payrSeq", ".fixed-layer").val() !== "9999999999"){
			$(".commcTr", ".fixed-layer").empty();
		}
		// 등록건 조회
		payItcptlayer.view();
		
		$(".calendar", ".fixed-layer").datepicker("option", "minDate", null);
		
		$("#procClfFlg", ".fixed-layer").change(function(){
			$("#procClfFlgVal", ".fixed-layer").val($(this).find("option:selected").text());
			if($(this).find("option:selected").val() === "U"){
				$("#unRsnSpan", ".fixed-layer").html('<span class="ico-required">*<span class="hide" title="필수"><spring:message code="common.essential"/></span></span>');
			} else {
				$("#unRsnSpan", ".fixed-layer").empty();
			}
		});
		
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
		
		// 차단/해제사유 바이트 제한
		$("#blockRsn, #unblockRsn", ".fixed-layer").keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			if(ctntByte > 100){
				alert("<spring:message code='csm.cnslmng.msg.limit.byte' arguments='100'/>");
				$(this).val(Common.getStringToByte($(this).val(), 100));
			}
		});
		
		// 수정 버튼 이벤트
		$("#updateBtn", ".fixed-layer").click(function(){
			utils.applyTrim("updateForm");
			update();
		});
		
		// 닫기
		$("#confirmBtn", ".fixed-layer").click(layer.close);
	});
	
	function update(){
		$("#updateForm", ".fixed-layer").validate({
	    	debug: true,
			onfocusout: false,
	        rules: {
	        	itcptReqrClfFlg : { required : true },
	        	itcptReqrNm : { required : true },
	        	itcptClfFlg : { required : true },
	        	procClfFlg : { required : true },
	        	procRsn : { required : true },
	        	itcptRelsRsn : {
	        		required : function(){
	        			return $("#procClfFlg > option:selected", ".fixed-layer").val() === "U";
	        		}
	        	},
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
				itcptRelsRsn: {
	            	required: "<spring:message code='csm.cnslmng.layer.itcpt.placeholder3'/>"
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
	        	payItcptlayer.update();
			},
	        showErrors: function(errorMap, errorList) {
	        }
		});
		
		if(confirm("<spring:message code='common.button.update.notice'/>")){
			$("#updateForm", ".fixed-layer").submit();
		}
	}
</script>
<div class="tbl-info">
	<!-- comment -->
	<span class="commt"><span class="ico-required">*<span class="txt" title="표시는 필수 항목"><spring:message code="common.indication"/></span></span></span>
	<!--// comment -->
</div>
<div class="tbl-box">
	<form id="updateForm">
		<input type="hidden" name="itcptRegSeq" id="itcptRegSeq" value=""/>
		<input type="hidden" name="payMphnId" id="payMphnId" value=""/>
		<input type="hidden" name="payrSeq" id="payrSeq" value=""/>
		<input type="hidden" name="mphnNo" id="mphnNo" value=""/>
		<table>
			<caption title="결제 차단 해제"><spring:message code="csm.cnslmng.layer.itcpt.caption2"/></caption>
			<colgroup>
				<col style="width:15%">
				<col style="width:40%">
				<col style="width:14%">
				<col style="width:*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="차단 요청자"><span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.layer.itcpt.th.itcptReqr"/></th>
					<td><select class="input-select" name="itcptReqrClfFlg" id="itcptReqrClfFlg" style="width:100px">
							<option value="" title="선택"><spring:message code="common.select.select"/></option>
							<option value="I" title="본인"><spring:message code="csm.cnslmng.layer.itcpt.option.self"/></option>
							<option value="F" title="가족"><spring:message code="csm.cnslmng.layer.itcpt.option.family"/></option>
							<option value="E" title="기타"><spring:message code="csm.cnslmng.layer.itcpt.option.etc"/></option>
						</select> <input type="text" class="input-text" name="itcptReqrNm" id="itcptReqrNm" style="width:140px">
					</td>
					<th scope="row" title="등록 일자"><spring:message code="csm.cnslmng.layer.itcpt.th.date.reg"/></th>
					<td><span id="regDt"></span></td>
				</tr>
				<tr>
					<th scope="row" title="차단 기준"><span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.th.block.stand"/></th>
					<td><select class="input-select" name="itcptClfFlg" id="itcptClfFlg" style="width:100px">
							<option value="" title="선택"><spring:message code="common.select.select"/></option>
							<option value="U" title="고객 번호"><spring:message code="csm.cnslmng.th.custno"/></option>
							<option value="P" title="휴대폰 번호"><spring:message code="csm.cnslmng.th.telno"/></option>
						</select> <input type="text" class="input-text" name="itcptClfFlgVal" id="itcptClfFlgVal" style="width:140px"  style="width:140px"></td>
					<th scope="row" title="입력자"><spring:message code="csm.cnslmng.th.regr"/></th>
					<td><span id="regr"></span></td>
				</tr>
				<tr>
					<th scope="row" title="결제 차단 여부"><span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.th.block.yn"/></th>
					<td><select class="input-select" name="procClfFlg" id="procClfFlg" style="width:100px">
							<option value="R" title="차단"><spring:message code="csm.cnslmng.data.itcpt.procflg.reg"/></option>
							<option value="U" title="해제"><spring:message code="csm.cnslmng.data.itcpt.procflg.unreg"/></option>
						</select> <input type="text" class="input-text" name="procClfFlgVal" id="procClfFlgVal" style="width:140px" value="<spring:message code='csm.cnslmng.data.itcpt.procflg.unreg'/>"></td>
					<th scope="row" title="생년 월일"><spring:message code="csm.cnslmng.th.birthday"/></th>
					<td><div class="datebox">
						<input type="text" name="brthYmd" id="brthYmd" class="input-text calendar" style="width:80px;" disabled="disabled">
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
					<td><input type="text" class="input-text" name="payrNm" id="payrNm" style="width:140px" placeholder="<spring:message code='csm.cnslmng.layer.itcpt.placeholder2'/>"></td>
				</tr>
				<tr>
					<th scope="row" title="수정 일자"><spring:message code="csm.cnslmng.layer.itcpt.th.date.update"/></th>
					<td><span id="lastChgDt"></span></td>
					<th scope="row" title="수정자"><spring:message code="csm.cnslmng.layer.itcpt.th.updater"/></th>
					<td><span id="lastChgr"></span></td>
				</tr>
				<tr id="blk_tr">
					<th scope="row" title="차단 사유"><span class="ico-required">*<span class="hide"><spring:message code="common.essential"/></span></span><spring:message code="csm.cnslmng.layer.itcpt.th.blockreason"/></th>
					<td colspan="3"><textarea style="width:97%; height:80px;" name="procRsn" id="blockRsn" class="textarea" placeholder="<spring:message code='csm.cnslmng.layer.itcpt.placeholder1'/>"></textarea></td>
				</tr>
				<tr>
					<th scope="row" title="차단 해제 사유"><span id="unRsnSpan"></span><spring:message code="csm.cnslmng.layer.itcpt.th.unblockreason"/></th>
					<td colspan="3"><textarea style="width:97%; height:80px;" name="itcptRelsRsn" id="unblockRsn" class="textarea" placeholder="<spring:message code='csm.cnslmng.layer.itcpt.placeholder3'/>"></textarea></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div class="btn-wrap">
	<button type="button" class="l-btn closePop cmpl" id="updateBtn" title="수정"><spring:message code="common.button.update"/></button>
	<button type="button" class="l-btn closePop cmpl" id="confirmBtn" title="확인" onclick="layer.close();" style="display: none;"><spring:message code="common.button.ok"/></button>
</div>