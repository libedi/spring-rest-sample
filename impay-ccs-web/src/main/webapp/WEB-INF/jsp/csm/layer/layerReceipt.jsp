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
<link type="text/css" rel="stylesheet" href="${resources}/css/print.css" />
<script type="text/javascript">
	var rcptLayer = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				search : "/cnslMng/search/trdCmplAllList",
				print : "/cnslMng/print/receipt",
				sendReceipt : "/cnslMng/send/receipt"
		};
		var messages;
		var context;	//jquery selector context
		var data;
		
		/**
		 * public 
		 */	
		module.init = function(opts){
			options.contextPath = opts.contextPath;
			messages = opts.messages;
			context = opts.context;
			data = opts.data;
		};
		module.getOptions = function(){
			return root.options;
		};
		// 조회
		module.search = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.search,
				data: $("#layerForm", context).serialize() + data.trdNos,
				dataType: "json",
				successCallback : function(result){
					root.bindList(result);
					$("#emailReceipt", context).focus();
				}
			});
		};
		// 발송
		module.sendReceipt = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.sendReceipt,
				data: $("#layerForm", context).serialize() + data.trdNos + "&custMphnNo=" + utils.formatTel(data.custMphnNo),
				dataType: "json",
				successCallback : function(result){
					if(result.success === true){
						alert(messages.sendOk);
						layer.close();
					} else {
						alert(messages.error);
					}
				}
			});
		};
		
		module.bindList = function(result){
			if(result.length > 0){
				$("#mphnNo", context).text(utils.formatTel(data.custMphnNo));
				$("#result", context).html(tmpl("tmpl-receiptList", result));
			}
		};
		
		return module;
	}(window.rcptLayer || {}, $));
	
	$(function(){
		var data = layer.getOptions().data;
		var messages = {
				"print" : "<spring:message code='csm.cnslmng.layer.receipt.msg.download'/>",
				"sendOk" : "<spring:message code='common.email.message.success'/>",
				"error" : "<spring:message code='common.error.message'/>"
				
		};
		rcptLayer.init({
						contextPath: layer.getOptions().contextPath, 
						context: ".fixed-layer",
						messages: messages,
						data: data
		});
		
		// 거래명세서 조회
		rcptLayer.search();
		
		$("#emailReceipt", ".fixed-layer").keyup(function(e){
			var code = e.which ? e.which : e.keyCode;
			if($.trim($(this).val()) !== "" && (code === 13 || code === 10)){
				sendReceipt();
			}
		});
		
		// 인쇄
		$("#printBtn", ".fixed-layer").click(function(){
			var params = $("#layerForm", ".fixed-layer").serialize() + data.trdNos + "&custMphnNo=" + utils.formatTel(data.custMphnNo);
			var popObj = window.open('${contextPath}' + "/cnslMng/openReceiptPrint?" + params, "print");
			if(popObj === null){
				alert("<spring:message code='csm.cnslmng.layer.receipt.msg.popup'/>");
			} else {
				// 팝업창 인쇄 사파리 대응
				if(navigator.userAgent.toLowerCase().indexOf("safari") > -1){
					window.setTimeout(function(){
						popObj.print();
					}, 350);
				} else {
					popObj.print();
				}
				window.setTimeout(function(){
					popObj.close();
				}, 350);
			}
		});
		
		// 발송
		$("#sendBtn", ".fixed-layer").click(function(){
			utils.applyTrim("layerForm");
			sendReceipt();
		});
	});
	
	function sendReceipt(){
		$("#layerForm", ".fixed-layer").validate({
	    	debug: true,
			onfocusout: false,
	        rules: {
	        	email : {
	        		required : true,
	        		email : true
	        	}
			},
			messages: {
				email: {
					required : "<spring:message code='csm.cnslmng.layer.receipt.msg.check.email'/>",
					email : "<spring:message code='csm.cnslmng.layer.receipt.msg.check.emailtype'/>"
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
	        	rcptLayer.sendReceipt();
			},
	        showErrors: function(errorMap, errorList) {
	        }
		});
		$("#layerForm", ".fixed-layer").submit();
	}
</script>
<!-- grid -->
<div class="tbl-info info-type2">
	<h5 class="cont-h5" title="결제 내역 현황"><span id="mphnNo"></span> <spring:message code="csm.cnslmng.layer.receipt.header"/></h5>
	<span class="unit" title="(단위 : 원)"><spring:message code="common.label.unit"/></span>
</div>
<div class="tbl-grid-box txt-right">
	<!--grid_head-->
	<div class="grid-head">
		<table>
			<caption title="거래 명세서 리스트"><spring:message code="csm.cnslmng.layer.receipt.caption1"/></caption>
			<colgroup>
				<col style="width:40px">
				<col style="width:70px">
				<col style="width:163px">
				<col style="width:100px">
				<col style="width:163px">
				<col style="width:163px">
				<col style="width:100px">
				<col style="width:50px">
				<col style="width:18px">
			</colgroup>
			<thead>
				<tr>
					<th scope="col" title="번호"><spring:message code="csm.cnslmng.layer.receipt.th.num"/></th>
					<th scope="col" title="결제 일시"><spring:message code="csm.cnslmng.layer.receipt.th.date.pay"/></th>
					<th scope="col" title="결제 사이트"><spring:message code="csm.cnslmng.layer.receipt.th.paysite"/></th>
					<th scope="col" title="연락처"><spring:message code="csm.cnslmng.layer.receipt.th.mphnno"/></th>
					<th scope="col" title="가맹점명"><spring:message code="csm.cnslmng.layer.receipt.th.cpnm"/></th>
					<th scope="col" title="상품명"><spring:message code="csm.cnslmng.layer.receipt.th.goodsnm"/></th>
					<th scope="col" title="금액"><spring:message code="csm.cnslmng.layer.receipt.th.payamt"/></th>
					<th scope="col" title="상태"><spring:message code="csm.cnslmng.layer.receipt.th.stat"/></th>
					<th scope="col"></th>
				</tr>
			</thead>
		</table>
	</div>
	<!--//grid_head-->
	
	<!--grid_body-->
	<div class="grid-body" style="height:300px;">
		<table>
			<caption title="거래 명세서 리스트"><spring:message code="csm.cnslmng.layer.receipt.caption1"/></caption>
			<colgroup>
				<col style="width:40px">
				<col style="width:70px">
				<col style="width:163px">
				<col style="width:100px">
				<col style="width:163px">
				<col style="width:163px">
				<col style="width:100px">
				<col style="width:50px">
			</colgroup>
			<tbody id="result"></tbody>
		</table>
	</div>
	<!--//grid_body-->
</div>
<!--// grid -->
<script type="text/html" id="tmpl-receiptList">
{% for(var i=0; i<o.length; i++) { %}
<tr>
	<td class="txt-center">{%=i+1%}</td>
	<td class="txt-center">{%=o[i].trdDt%}</td>
	<td class="txt-left">{%=StringUtil.nvl(o[i].paySiteNm, "")%}</td>
	<td class="txt-center">{%=StringUtil.nvl(o[i].telNo, "")%}</td>
	<td class="txt-left">{%=o[i].paySvcNm%}</td>
	<td class="txt-left">{%=o[i].godsNm%}</td>
	<td class="txt-right">{%=Common.formatMoney(o[i].payAmt)%}</td>
	<td class="txt-center">{%#o[i].payStat%}</td>
{% } %}
</script>
<form id="layerForm" onsubmit="return false;">
	<input type="hidden" name="pageParam.pageIndex" value="1"/>
	<input type="hidden" name="pageParam.rowCount" value="5"/>
	<div class="btn-wrap txt-right borderBottom">
		<button type="button" class="l-btn dark" id="printBtn" title="인쇄하기"><spring:message code="csm.cnslmng.layer.receipt.btn.print"/></button>
	</div>
	<div class="tbl-box top-br mrg-t10">
		<table>
			<caption title="email"><spring:message code="csm.cnslmng.layer.receipt.caption2"/></caption>
			<colgroup>
				<col style="width:15%">
				<col style="width:*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="E-mail"><spring:message code="csm.cnslmng.layer.receipt.th.email"/></th>
					<td>
						<input type="text" name="email" id="emailReceipt" class="input-text" style="width:97%;" placeholder="<spring:message code='csm.cnslmng.layer.receipt.placeholder'/>">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="btn-wrap">
	<button type="button" class="l-btn cmpl" id="sendBtn" title="발송"><spring:message code="common.email.button.send"/></button>
	<button type="button" class="l-btn closePop" onclick="layer.close();" title="닫기"><spring:message code="common.button.close"/></button>
</div>