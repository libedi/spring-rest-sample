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
<script type="text/javascript">
	var docuInven = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				search : "/cnslMng/search/smsDocuList"
		};
		var messages;
		var context;	//jquery selector context
		
		/**
		 * public 
		 */	
		module.init = function(opts){
			options.contextPath = opts.contextPath;
			messages = opts.messages;
			context = opts.context;
		};
		module.getOptions = function(){
			return root.options;
		};
		// 조회
		module.search = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.search,
				dataType: "json",
				successCallback: root.resultBind
			});
		};
		// 출력
		module.resultBind = function(result){
			if(result !== undefined && result !== null){
				$("#total", ".fixed-layer").text(Common.formatMoney(result.length));
				$(".documentInven", ".fixed-layer").html(tmpl("tmpl-list", result));
			}
		};
		
		return module;
	}(window.docuInven || {}, $));
	
	$(function(){
		var messages = {
				"error" : "<spring:message code='common.error.message'/>"
		};
		docuInven.init({
						contextPath: layer.getOptions().contextPath, 
						context: ".fixed-layer",
						messages: messages
		});
		
		// 등록건 조회
		docuInven.search();
		
		// 선택 이벤트
		$(".documentInven").on("click", "span", function(){
			$('.documentInven > span').removeClass('on');
			$(this).addClass('on');
		});
		
		// 선택건 더블클릭 이벤트
		$(".documentInven").on("dblclick", "span", function(){
			$('.documentInven > span').removeClass('on');
			$(this).addClass('on');
			layer.close($(this).text());
		});
		
		// 확인
		$(".closePop", ".fixed-layer").click(function(){
			layer.close($(".documentInven > span.on").text());
		});
	});
	
</script>
<div class="tbl-info">
	<!-- total -->
	<span class="total"><spring:message code="common.total"/><strong><span id="total">0</span></strong><spring:message code="common.count"/></span>
	<!--// total -->
</div>
<div class="documentInven">
</div>
<%-- template --%>
<script type="text/html" id="tmpl-list">
{% for(var i=0; i<o.length; i++) { %}
<span><span>{%=o[i]%}</span></span>
{% } %}
</script>
<div class="btn-wrap">
	<button type="button" class="l-btn closePop"><spring:message code="common.button.ok"/></button>
</div>