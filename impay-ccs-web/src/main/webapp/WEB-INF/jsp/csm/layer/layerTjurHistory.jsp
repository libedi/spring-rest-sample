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
	var tjurHistLayer = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				search : "/cnslMng/search/tjurProcHist",
				saveTjurHist : "/cnslMng/save/tjurProcHist"
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
				data: {rcptNo : data.rcptNo},
				dataType: "json",
				successCallback : function(result){
					if(result.length > 0){
						$("div.yscroll", context).html(tmpl("tmpl-tjurHistList", result));
					}
				}
			});
		};
		// 저장
		module.saveTjurHist = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.saveTjurHist,
				data: {
					rcptNo : data.rcptNo,
					procTjurCtnt : $("#procTjurCtnt", context).val(),
				},
				dataType: "json",
				successCallback : function(result){
					if(result.success === true){
						$("#procTjurCtnt", context).val("");
						alert(messages.saveOk);
						root.search();
					} else {
						alert(messages.error);
					}
				}
			});
		};
		return module;
	}(window.tjurHistLayer || {}, $));
	
	$(function(){
		var data = layer.getOptions().data;
		var messages = {
				"saveOk" : "<spring:message code='common.alert.save.succ'/>",
				"error" : "<spring:message code='common.error.message'/>"
		};
		tjurHistLayer.init({
						contextPath: layer.getOptions().contextPath, 
						context: ".fixed-layer",
						messages: messages,
						data: data
		});
		
		// 이관처리 내역조회
		tjurHistLayer.search();
		
		// 처리내용 글자수 제한
		$("#procTjurCtnt", ".fixed-layer").keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			if(ctntByte > 500){
				$(this).val(Common.getStringToByte($(this).val(), 500));
				alert("<spring:message code='csm.cnslmng.msg.limit.byte' arguments='500'/>");
			}
		});
		
		// 저장
		$("#saveBtn", ".fixed-layer").click(function(){
			if($.trim($("#procTjurCtnt", ".fixed-layer").val()) === ""){
				alert("<spring:message code='csm.cnslmng.msg.select.tjur.proc'/>");
				return;
			}
			tjurHistLayer.saveTjurHist();
		});
	});
</script>
<!-- grid -->
<div class="tbl-box">
	<table>
		<caption title="이관 처리 내역"><spring:message code="csm.cnslmng.layer.tjurhist.title"/></caption>
		<colgroup>
			<col style="width:20%">
			<col style="width:*">
		</colgroup>
		<tbody>
			<tr>
				<td colspan="2">
					<div class="yscroll"></div>
				</td>
			</tr>
			<tr>
				<th scope="row" title="처리 내용 입력"><spring:message code="csm.cnslmng.layer.tjurhist.th.procctnt"/></th>
				<td class="conts">
					<textarea name="procTjurCtnt" id="procTjurCtnt" class="textarea" rows="1" cols="1" style="width:96%; height:90px;overflow-y:auto" placeholder="<spring:message code='csm.cnslmng.layer.tjurhist.placeholder'/>"></textarea>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<script type="text/html" id="tmpl-tjurHistList">
{% for(var i=0; i<o.length; i++) { %}
{%=o[i].regr%} {%=o[i].regDt%}<br/>
{%#o[i].procTjurCtnt%}
<br/><br/>
{% } %}
</script>
<!-- btn -->
<div class="btn-wrap">
	<button type="button" class="l-btn cmpl" id="saveBtn" title="저장"><spring:message code="common.button.save"/></button>
	<button type="button" class="l-btn close" id="closeBtn" onclick="layer.close();" title="닫기"><spring:message code="common.button.close"/></button>
</div>
<!--// btn -->