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
	<title title="상담 시나리오"><spring:message code="csm.cnsl.scenario.title"/></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
	<script type="text/javascript">
	var contextPath = "${contextPath}";

    $().ready(function(){
    	Common.tabs();
    	
        var lastEvent = null;
        var slide  = "ul.scrio-list > li > div";
        var alink  = "ul.scrio-list > li > a";
   
    	function accordion(){
    	    if (this == lastEvent) return false;
    		lastEvent = this;
    		setTimeout(function() {lastEvent = null}, 200);
    		
    		if ($(this).attr('class') !== 'on-selected') {
    			$(slide).slideUp();
    			$(this).next(slide).slideDown();
    			$(alink).removeClass('on-selected');
                $(this).addClass('on-selected');
    		} else if ($(this).next(slide).is(':hidden')) {
    			$(slide).slideUp();
    			$(this).next(slide).slideDown();
    		} else {
    			$(this).next(slide).slideUp();
    		}
    	}
    	
    	$("ul.scrio-list").on("click", "li > a", accordion).on("focus", "li > a", accordion);
    	$('ul.scrio-list > li:last > a').addClass('stay');
    });
    
    var popup = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				search : "/cnslMng/search/cnslScrptList"
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
		};
		module.getOptions = function(){
			return root.options;
		};
		// 조회
		module.search = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.search,
				data: {
					srchText : $("#srchText", context).val()
				},
				dataType: "json",
				successCallback : function(result){
					if(result !== null){
						var html = tmpl("tmpl-list", result);
						$("#result", context).html(html);
					}
				}
			});
		};
		
		return module;
	}(window.popup || {}, $));
    
    $(function(){
    	var messages = {
				"error" : "<spring:message code='common.error.message'/>"
		};
    	popup.init({
				contextPath: contextPath, 
				context: ".pop-wrap",
				messages: messages
		});
    	// 엔터키 조회 이벤트
    	$("#srchText", ".pop-wrap").keyup(function(e){
    		if(e.which == 13){
    			search();
    		}
    		e.preventDefault();
    	});
    	// 조회버튼 클릭 이벤트
    	$("#searchBtn", ".pop-wrap").click(search);
    });
    
    function search(){
    	$("#srchText", ".pop-wrap").val($.trim($("#srchText", ".pop-wrap").val()));
    	if($("#srchText", ".pop-wrap").val() === ""){
    		alert("<spring:message code='csm.cnsl.scenario.msg.search'/>");
    	} else {
    		popup.search();
    	}
    }
	</script>
</head>
<body>
	<div class="pop-wrap">
		<div class="pop-header">
			<h1 title="상담 시나리오"><spring:message code="csm.cnsl.scenario.title"/></h1>
		</div>
		
		<div class="pop-conts">
			
			<!-- search -->
			<div class="search-box">
				<fieldset>
				<legend title="상담 시나리오 조회"><spring:message code="csm.cnsl.scenario.caption"/></legend>
				<table>
					<caption title="상담 시나리오 조회"><spring:message code="csm.cnsl.scenario.caption"/></caption>
					<colgroup>
						<col style="width:12%">
						<col style="width:*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" title="상담 검색"><spring:message code="csm.cnsl.scenario.th.search"/></th>
							<td>
								<input type="text" class="input-text" name="srchText" id="srchText" style="width:490px;">
								<button type="button" class="l-btn searchbtn" id="searchBtn" title="조회"><spring:message code="common.button.search"/></button>
							</td>
						</tr>
					</tbody>
				</table>
				</fieldset>
			</div>
			<!--// search -->
			
			<div class="panel-box scrio">
				<!-- tabs -->
				<ul class="tab-head basic">
					<li><a href="#tab1" title="조회"><spring:message code="common.button.search"/></a></li>
					<c:forEach items="${cnslClfCdList}" var="list" varStatus="status">
						<li><a href="#tab${status.count + 1}">${list.cdNm}</a></li>
					</c:forEach>
				</ul>
				<!-- tabs -->
				
				<h2 title="상담 시나리오"><spring:message code="csm.cnsl.scenario.title"/></h2>
				
				<!-- tab 1 -->
				<div id="tab1" class="tab-conts">
					<ul class="scrio-list" id="result"></ul>
				</div>
				<!--// tab 1 -->
				
				<%-- template --%>
				<script type="text/x-tmpl" id="tmpl-list">
				{% for(var i=0; i<o.length; i++) { %}
					{% for(var j=0; j<o[i].cnslScrptList.length; j++) { %}
					<li>
						<a href="#none"><span>[{%=o[i].cnslScrptList[j].cnslClfLwrCdNm%}] {%#o[i].cnslScrptList[j].cnslCrspScrptQstn%}</span></a>
						<div>
							{%#o[i].cnslScrptList[j].cnslCrspScrptCtnt%}
						</div>
					</li>
					{% } %}
				{% } %}
				</script>
					
				<c:forEach items="${cnslScnrList}" var="list1" varStatus="status">
					<div id="tab${status.count + 1}" class="tab-conts">
						<ul class="scrio-list">
							<c:forEach items="${list1.cnslScrptList}" var="list2" varStatus="status2">
								<li>
									<a href="#none"><span>[${list2.cnslClfLwrCdNm}] <c:out value="${list2.cnslCrspScrptQstn}" escapeXml="false"/></span></a>
									<div>
										<c:out value="${list2.cnslCrspScrptCtnt}" escapeXml="false"/>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
				
				<!-- btn -->
				<div class="btn-wrap"> 
					<button type="button" class="l-btn close" onclick="window.close()" title="닫기"><spring:message code="common.button.close"/></button>
				</div>
				<!--// btn -->
			</div>
		</div>		
	</div>
</body>
</html>