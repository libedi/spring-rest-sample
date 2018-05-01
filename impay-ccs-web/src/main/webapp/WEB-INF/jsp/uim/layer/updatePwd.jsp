<%--
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"            %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"             %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"       %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"          %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form"     %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
<link type="text/css" rel="stylesheet" href="${resources}/css/popup.css" />
<script type="text/javascript">
$(function() {
	$("#updatePwdBtn").on("click", function(){
		utils.applyTrim("passwordform");
		$('#passwordform').submit();
	});
	
    $('#passwordform').validate({
        onfocusout: false,
        rules: {
            pwd: {
                required: true
            }, newPwd: {
                required: true,
                minlength: 7
            }, newPwd2: {
                required: true,
                minlength: 7,
                equalTo: '#newPwd'
            }
        }, messages: {
            pwd: {
                required: '<spring:message code="uim.update.valid.password.current" />'
            }, newPwd: {
                required: '<spring:message code="uim.update.valid.password.new" />',
                minlength: '<spring:message code="uim.update.valid.password.minlength" arguments="7" />'
            }, newPwd2: {
                required: '<spring:message code="uim.update.valid.password.confirm" />',
                minlength: '<spring:message code="uim.update.valid.password.minlength" arguments="7" />',
                equalTo: '<spring:message code="uim.update.alert.password.confirm" />'
            }
        }, errorPlacement: function (error, element) {
        }, invalidHandler: function (form, validator) {
            var errors = validator.numberOfInvalids();
            if (errors) {
                alert(validator.errorList[0].message);
                validator.errorList[0].element.focus();
            }
        }, submitHandler: function (form) {
            $('#passwordform > input[name="userSeq"]').val(layer.getOptions().data.userSeq);
            impay.sendPost({
                requestUri : '<c:url value="/userMng/updatePwd" />',
                data : $('#passwordform').serialize(),
                successCallback : function (result) {
                    if (result.success && result.result == 1) {
                    	alert('<spring:message code="uim.update.alert.updateOk" />');
                    	layer.close();
                    } else {
                    	alert(result.message);
                    }
                },
                errorCallback : function () {
                    alert('<spring:message code="uim.update.alert.error" />');
                }
            });
        }
    });
});
</script>
<form id="passwordform" method="post">
    <input type="hidden" name="siteNm" />
    <input type="hidden" name="userSeq" />
	<div class="tbl-info mrg-tm7">
		<!-- comment -->
		<span class="commt"><span class="ico-required">*<span class="txt"><spring:message code="uim.update.comment.required"/></span></span></span>
		<!--// comment -->
	</div>
	<div class="tbl-box">
		<table>
			<caption title="비밀번호 변경"><spring:message code="uim.update.title" /></caption>
			<colgroup>
				<col style="width: 40%">
				<col style="width: *">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="현재 비밀번호"><span class="ico-required">*<span class="hide" title="필수"><spring:message code="uim.update.label.required" /></span></span><spring:message code="uim.update.label.password.current" /></th>
					<td><input type="password" name="pwd" id="pwd" class="input-text" style="width: 150px;" value=""></td>
				</tr>
				<tr>
					<th scope="row" title="새 비밀번호"><span class="ico-required">*<span class="hide" title="필수"><spring:message code="uim.update.label.required" /></span></span><spring:message code="uim.update.label.password.new" /></th>
					<td><input type="password" name="newPwd" id="newPwd" class="input-text" style="width: 150px;" value=""></td>
				</tr>
				<tr>
					<th scope="row" title="새 비밀번호 확인"><span class="ico-required">*<span class="hide" title="필수"><spring:message code="uim.update.label.required" /></span></span><spring:message code="uim.update.label.password.confirm" /></th>
					<td><input type="password" name="newPwd2" id="newPwd2" class="input-text" style="width: 150px;" value=""></td>
				</tr>
			</tbody>
		</table>
	</div>
	<ul class="caption-li">
		<li title="중복 문자, 연속 문자 3자 이상은 사용하실 수 없습니다.">
			<spring:message code="uim.update.comment.duplication" />
		</li>
		<li title="비밀번호는 7자리 이상,영문 대 문자, 영문 소 문자, 숫자, 특수 문자 중 2가지 이상을 조합 하여야 합니다.">
			<em class="point-red"><spring:message code="uim.update.comment.pattern" /></em>
		</li>
	</ul>
	<!-- btn -->
	<div class="btn-wrap">
		<button type="button" class="l-btn close" id="updatePwdBtn" title="확인"><spring:message code="common.button.ok" /></button>
	</div>
	<!--// btn -->
</form>