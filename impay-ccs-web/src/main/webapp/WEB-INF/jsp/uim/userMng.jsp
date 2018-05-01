<%--
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title><spring:message code="uim.userMng.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<script type="text/javascript" src="${resources}/js/ccs/uim/userMng.js"></script>
	<script type="text/javascript">
	var contextPath = "${contextPath}";
	var resources   = "${resources}";
	$(function(){
		$(".update").hide();
		
		var messages = {
					"updateTitle" : "<spring:message code='uim.update.title'/>",
					"updateOk" : "<spring:message code='uim.userMng.alert.updateOk'/>"
				};
		var forms = { updateForm: "frmUpdate" };
		userMng.init({
						contextPath: contextPath, 
						context: ".contents",
						forms: forms,
						messages: messages
		});
		
		// 비밀번호 변경
		$("#updatePwd", ".contents").click(userMng.updatePwd);
		
		// 수정버튼
		$("#updateBtn", ".contents").click(function(){
			$(".saved", ".contents").hide();
			$(".update", ".contents").show();
		});
		
		// 취소버튼
		$("#cancelBtn", ".contents").click(function(){
			$(".update", ".contents").hide();
			$("#mphnNo", ".contents").val($("#savedMphnNo").text());
			$("#email", ".contents").val($("#savedEmail").text());
			$("#deptNm", ".contents").val($("#savedDeptNm").text());
			$(".saved", ".contents").show();
		});
		
		// 저장
		$("#saveBtn", ".contents").click(update);
		
		// 필드 엔터키 입력 이벤트
		$("input:text", ".contents").keyup(function(e){
			var code = e.which ? e.which : e.keyCode;
			if(code === 13 || code === 10){
				update();
			}
		});
	});
	
	// 저장
	function update() {
		if(confirm("<spring:message code='uim.userMng.alert.confirm'/>")){
			// 전화번호 유효성 검사
			$.validator.addMethod("telType", function(value, element) {
		    	return this.optional(element) || /\b\d{2,3}[-]\d{3,4}[-]\d{4}\b/g.test(value);
		    }, "<spring:message code='uim.userMng.alert.valid.telType'/>"); 
			
			// 폼 유효성 검사
			$("#frmUpdate").validate({
		    	debug: true,
				onfocusout: false,
		        rules: {
					email: { required : true, email: true },
					mphnNo: { required : true, telType: true },
					deptNm: { required : true}
				},
				messages: {
					email: {
		            	required: "<spring:message code='uim.userMng.alert.valid.email'/>", 
		            	email: "<spring:message code='uim.userMng.alert.valid.emailType'/>"
					},
					mphnNo: {
		            	required: "<spring:message code='uim.userMng.alert.valid.tel'/>"
					},
					deptNm: {
		            	required: "<spring:message code='uim.userMng.alert.valid.deptNm'/>"
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
		        	userMng.update();
				},
		        showErrors: function(errorMap, errorList) {
	        		//alert(errorList[0].message);
		        }
			});
			
			utils.applyTrim("frmUpdate");
			$('#frmUpdate').submit();
		}
	}
	</script>
</head>
<body>
	<!-- contents -->
	<div id="contents" class="contents">
					
		<h3 class="cont-h3" title="사용자 정보 관리"><spring:message code="uim.userMng.title"/></h3>
		
		<!-- search -->
		<div class="search-box">
			<form id="frmUpdate">
			<input type="hidden" name="userSeq" id="userSeq" value="${userMng.userSeq}" />
			<input type="hidden" name="cntcSeq" id="cntcSeq" value="${userMng.cntcInfo.cntcSeq}" />
			<fieldset>
			<legend title="사용자 정보 관리"><spring:message code="uim.userMng.title"/></legend>
			<table>
				<caption title="사용자 정보 관리"><spring:message code="uim.userMng.title"/></caption>
				<colgroup>
					<col style="width:14%">
					<col style="width:*">
					<col style="width:14%">
					<col style="width:*">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" title="사용자 이름"><spring:message code="uim.userMng.label.username"/></th>
						<td>${userMng.userNm}</td>
						<th scope="row" title="사용자 ID"><spring:message code="uim.userMng.label.userId"/></th>
						<td>${userMng.userId}</td>
					</tr>
					<tr>
						<th scope="row" title="비밀 번호"><spring:message code="uim.userMng.label.password"/></th>
						<td>
							<span class="saved">******</span>
							<input type="password" name="password" id="password" class="input-text update" value="******" readonly="readonly"/>
							<button type="button" class="inbtn update" id="updatePwd" title="변경"><spring:message code="uim.userMng.button.password.update"/></button>
						</td>
						<th scope="row" title="전화 번호"><spring:message code="uim.userMng.label.telNo"/></th>
						<td>
							<span class="saved" id="savedMphnNo">${userMng.cntcInfo.mphnNo}</span>
							<input type="text" name="mphnNo" id="mphnNo" class="input-text update" value="${userMng.cntcInfo.mphnNo}"/>
						</td>
					</tr>
					<tr>
						<th scope="row" title="E-mail"><spring:message code="uim.userMng.label.email"/></th>
						<td>
							<span class="saved" id="savedEmail">${userMng.cntcInfo.email}</span>
							<input type="text" name="email" id="email" class="input-text update" style="width:300px" value="${userMng.cntcInfo.email}"/>
						</td>
						<th scope="row" title="부서명"><spring:message code="uim.userMng.label.deptNm"/></th>
						<td>
							<span class="saved" id="savedDeptNm">${userMng.cntcInfo.deptNm}</span>
							<input type="text" name="deptNm" id="deptNm" class="input-text update" style="width:300px" value="${userMng.cntcInfo.deptNm}"/>
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
			<button type="button" class="l-btn saved" id="updateBtn" title="수정"><spring:message code="common.button.update"/></button>
			<button type="button" class="l-btn cmpl update" id="saveBtn" title="저장"><spring:message code="common.button.save"/></button>
			<button type="button" class="l-btn update" id="cancelBtn" title="취소"><spring:message code="common.button.cancel"/></button>
		</div>
		<!--// btn -->

	</div>
	<!--// contents -->	
</body>
</html>