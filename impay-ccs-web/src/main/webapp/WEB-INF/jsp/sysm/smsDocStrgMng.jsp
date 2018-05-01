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
	<title title="SMS 문서 보관함"><spring:message code="sysm.smsDocStrgMng.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
<script type="text/javascript" src="${resources}/js/ccs/sysm/smsDocStrgMng.js"></script>
<script type="text/javascript">
	$(function() {
		var messages = {
						'insertOk'	: '<spring:message code="common.alert.insert.succ"/>',
						'updateOk'	: '<spring:message code="common.alert.update.succ"/>',
						'deleteOk'	: '<spring:message code="common.alert.delete.succ"/>',
						'error' 	: '<spring:message code="common.error.message"/>'
						};
		var forms = {'smsWordForm':'smsWordForm'};
		smsDocStrgMng.init({contextPath: '${contextPath}',
									context: '.contents',
									forms: forms,
									messages: messages});
		smsDocStrgMng.search();
		// 선택한 영역 활성화
		$("#result").on('click', '.msg_box', function() {
			smsDocStrgMng.getSmsWord($(this).data("id"));
			$('.msg_box').removeClass('on');
			$(this).addClass('on');
		});
		// 삭제 버튼 CLICKEVENT
		$("#result").on('click','.msg_box .delBtn',function() {
			var charStrgNo = $(this).val();
			var msg = '<spring:message code="sysm.smsDoc.alert.delete"/>';
        	if(confirm(msg)){
        		smsDocStrgMng.deleteSmsWord(charStrgNo);
        	}else{
        		return;
        	}
		});
		// byte 계산
		$("#charCtnt").keyup(function(){ 
			var ctntByte = Common.getByteString($(this).val());
			$("#ctntCount").text(ctntByte);
			if(ctntByte > 200){
				$(this).val(Common.getStringToByte($(this).val(), 200));
				alert('<spring:message code="sysm.valid.byte"/>');
				$("#ctntCount").text(Common.getByteString($(this).val()));
			}
		});
		// 취소 버튼 CLICKEVENT
		$("#cancelBtn").on("click",function(){
			var msg = '<spring:message code="common.confirm.cancel1"/>';
        	if(confirm(msg)){
        		smsDocStrgMng.resetForm();
        		$("#updateBtn").hide();
        		$("#addBtn").show();
        		$('.msg_box').removeClass('on');
        	}else{
        		return;
        	}		
		});
		// 추가 버튼 CLICKEVENT
		$("#addBtn").on('click',function(){
			validateForSmsDocStrgMng("insert");
		});		
		// 수정 버튼 CLICKEVENT
		$("#updateBtn").on('click',function(){
			validateForSmsDocStrgMng("update");
		});
	});
	function validateForSmsDocStrgMng(division) {
		$("#division", ".contents").val(division);
		$("#smsWordForm").validate({
			rules 		: { charCtnt : { required : true } },
			messages 	: { charCtnt : { required : '<spring:message code="sysm.valid.ctnt"/>'} },
			errorPlacement : function(error, element) {	},
			invalidHandler : function(form, validator) {
									var errors = validator.numberOfInvalids();
									if (errors) {
										alert(validator.errorList[0].message);
										validator.errorList[0].element.focus();
									}
							},
			submitHandler : function(form) {
				var division = $("#division").val();
						if(division == "insert"){
							var msg = '<spring:message code="sysm.smsDoc.alert.insert"/>';
				        	if(confirm(msg)){
				        		smsDocStrgMng.addSmsWord();
				        	}else{
				        		return;
				        	}
						}else if(division == "update"){
							var msg = '<spring:message code="sysm.smsDoc.alert.update"/>';
				        	if(confirm(msg)){
				        		smsDocStrgMng.updateSmsWord();
				        	}else{
				        		return;
				        	}
						}
					}
			});
		$('#smsWordForm').submit();
	}
</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
		<h3 class="cont-h3" title="SMS 문서 보관함 관리"><spring:message code="sysm.smsDocStrgMng.title"/></h3>
		<!-- SMS 문서 보관함 관리 Wrap-->
		<div class="sms_box_manage">
			<!-- SMS 보관함 -->
			<div class="prev_msg_box">
				<h4 class="cont-h4" title="SMS 보관함"><spring:message code="sysm.smsDocStrg"/></h4>
				<span title="총"><spring:message code="common.total"/> <em id="listTotal">0</em><spring:message code="common.count"/></span>
				<div class="inner">
					<div class="msg_list">
						<ul id="result">
						</ul>
					</div>
				</div>
			</div>
			<!-- // SMS 보관함 -->
			<script type="text/html" id="tmpl-smsDocList">	
				{% for(var i=0; i<o.length; i++) { %}
					<li >
						<div class="msg_box" data-id="{%=o[i].charStrgNo%}"><span>{%=o[i].charCtnt%}</span>
							<button class="delBtn" type="button" value="{%=o[i].charStrgNo%}" title="삭제"></button>
						</div>
					</li>
				{% } %}
			</script>
			<!-- SMS 문구 -->
			<div class="send_msg_box">
			<form id="smsWordForm">
			<input type="hidden" id="division">
			<input type="hidden" id="charStrgNo" name="charStrgNo" >
				<h4 class="cont-h4" title="SMS 문구"><spring:message code="sysm.smsDoc"/></h4>
				<div class="inner">
					<span>(<em id="ctntCount">0</em>/200 Byte)</span>
					<textarea name="charCtnt" id="charCtnt" cols="30" rows="10"></textarea>
					<!-- btn -->
					<div class="btn-wrap">
						<button type="button" class="l-btn cmpl" id="updateBtn" style="display:none;" title="수정"><spring:message code="common.button.update"/></button>
						<button type="button" class="l-btn cmpl" id="addBtn" title="추가"><spring:message code="sysm.add"/></button>
						<button type="button" class="l-btn" id="cancelBtn" title="취소"><spring:message code="common.button.cancel"/></button>
					</div>
					<!--// btn -->

				</div>
			</form>
			</div>
			<!-- // SMS 문구 -->
		</div>
		<!-- // SMS 문서 보관함 관리 Wrap-->
	</div>
	<!--// contents -->	
</body>
</html>
