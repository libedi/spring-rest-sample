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

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />
	<title><spring:message code="common.login.title"/></title>
	<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${resources}/css/login.css">
	<script type="text/javascript">
		var contextPath = "${contextPath}";
		
		$(document).ready(function() {
			var errorMsg = utils.getRequest("errorMsg");			
			if(errorMsg !== undefined && errorMsg !== null && errorMsg !== ""){
				alert('<spring:message code="common.error.badCredentials"/>');
				window.location.search = '';
			}
			$("#id").focus();
		  	$("#pw").on('keydown', function(key) {
                if (key.keyCode === 13) {
                	$('#frmLogin').submit();
                }
            });
			$("#button-login").on('click', function(){
				$('#frmLogin').submit();
			});
			
			// validation check
			$('#frmLogin').validate({
				debug:false,
				onkeyup:false,     /** keyup 시 유효성 체크 */
				onfocusout:false,  /** focus out 시 유효성 체크 */ 
				onclick:false,     /** radio, checkbox등이 클릭 될 때 유효성 체크 */
				focusInvalid:true, /** 유효성 체크 후 무효필드에 포커스를 둘지 여부 */
	            rules: {
	                username: { required: true },
	                password: { required: true },
	            },
	            messages: {
	                username: {
	                    required: '<spring:message code="common.login.placeholder.username"/>',
	                },
	                password: {
	                    required: '<spring:message code="common.login.placeholder.password"/>',
	                },
	            },
	            success : function(e) { 
	            //
	            },
	            showErrors : function(errorMap, errorList) {
	            	if(this.numberOfInvalids()) {
	            		alert(errorList[0].message);
	            	}
	            }
	        });
			// submit
			$("#submitBtn").on("click", function(){
				$('#frmLogin').submit();
			});
		});
	</script>
</head>
<body>
	<!--Login-->
	<div class="login_wrap">
		<div class="bg"></div>
		<div class="inner">
			<div class="top">
				<h1>
					<img src="${resources}/images/login_bi_ccs.png" alt="IMPay CCS">
				</h1>
				<div>
					<em>I</em>ntergrated <em>M</em>arketing &amp; <em>Pay</em>ment
				</div>
			</div>
			<div class="left">
				<img src="${resources}/images/login_img01_ccs.png" alt="CCS" class="ccs" >
			</div>
			<form id="frmLogin" method="post">
				<fieldset>
					<legend><spring:message code="common.login.button.login"/></legend>
					<dl>
						<dt><label for="id">ID</label></dt>
						<dd><input type="text" value="" id="id" name="username"></dd>
						<dt class="last"><label for="pw">PW</label></dt>
						<dd><input type="password" value="" id="pw" name="password"></dd>
					</dl>
					<button type="button" id="submitBtn"><spring:message code="common.login.button.login"/></button>
<%-- 					<a href="#none"><spring:message code="common.login.button.find"/></a> --%>
				</fieldset>
			</form>
			<footer>
				<em><spring:message code="common.footer.info.cs"/></em> : <spring:message code="common.footer.info.cs.tel"/>
				<em><spring:message code="common.footer.info.cs.run"/></em> : <spring:message code="common.footer.info.cs.runtime"/>
				<em><spring:message code="common.footer.info.cs.inquire"/></em> <a href="mailto:imPay@sk.com"><spring:message code="common.footer.info.cs.inquire.email"/></a>
			</footer>
		</div>
	</div>
	<!--//Login-->
</body>
</html>