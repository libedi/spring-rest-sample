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
<script type="text/javascript">
	var cnslViewLayer = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				search : "/rpm/trxRcpt/search/uploadTjurRcpt",
				saveUpldCtnt : "/rpm/trxRcpt/save/uploadTjurRcpt"
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
					$("#dataRegNo", context).text(result.dataRegNo);
					$("#upldDt", context).text(result.upldDt);
					$("#rcptMthdCd", context).text(result.rcptMthdCdNm);
					$("#regr", context).text(result.regr);
					if(data.hash === "result-view"){
						$("input:radio[name='procStat']", context).removeAttr("checked");
						if(result.procStat === "C"){
							$("input:radio[name='procStat'][value='N']", context).prop("checked", true);
							$("input:radio[name='procStat']", context).prop("disabled", true);
						} else {
							$("input:radio[name='procStat'][value='" + result.procStat + "']", context).prop("checked", true);
						}
					}
					if(data.hash === "counsel-view"){
						$("#ctnt", context).val(result.cnslCtnt);
					} else if(data.hash === "process-view"){
						$("#ctnt", context).val(result.procCtnt);
					} else {
						$("#ctnt", context).val(result.procRslt);
					}
					var ctntByte = Common.getByteString($("#ctnt", context).val());
					$("#cnslByte", context).text(ctntByte);
				}
			});
		};
		// 저장
		module.saveUpldCtnt = function(){
			if($("#ctnt", context).val() === ""){
				alert("<spring:message code='common.email.message.content'/>");
				return;
			}
			impay.sendPost({
				requestUri: options.contextPath + uris.saveUpldCtnt,
				data: $("#layerForm", context).serialize() + "&dataRegNo=" + data.rcptNo,
				dataType: "json",
				successCallback : function(result){
					if(result.success === true){
						alert(messages.saveOk);
						layer.close(true);
					} else {
						alert(messages.error);
					}
				}
			});
		};
		
		return module;
	}(window.cnslViewLayer || {}, $));
	
	$(function(){
		var data = layer.getOptions().data;
		var messages = {
				"saveOk" : "<spring:message code='rpm.tjur.msg.saveOk'/>",
				"error" : "<spring:message code='common.error.message'/>"
				
		};
		cnslViewLayer.init({
						contextPath: layer.getOptions().contextPath, 
						context: ".fixed-layer",
						messages: messages,
						data: data
		});
		
		$("#title", ".fixed-layer").text(data.title);
		if(data.hash === "counsel-view"){
			$("#rsltTr", ".fixed-layer").remove();
			$("#ctntName", ".fixed-layer").text("<spring:message code='rpm.tjur.th.content.cnsl'/>");
			$("#ctnt", ".fixed-layer").attr("name", "cnslCtnt");
		} else if(data.hash === "process-view"){
			$("#rsltTr", ".fixed-layer").remove();
			$("#ctntName", ".fixed-layer").text("<spring:message code='rpm.tjur.th.content.proc'/>");
			$("#ctnt", ".fixed-layer").attr("name", "procCtnt");
		} else {
			$("#ctntName", ".fixed-layer").text("<spring:message code='rpm.tjur.th.procRslt'/>");
			$("#ctnt", ".fixed-layer").attr("name", "procRslt");
		}
		cnslViewLayer.search();
		
		$("#ctnt", ".fixed-layer").keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			$("#cnslByte", ".fixed-layer").text(ctntByte);
			if(ctntByte > 4000){
				$(this).val(Common.getStringToByte($(this).val(), 4000));
				alert("<spring:message code='csm.cnslmng.msg.limit.byte' arguments='4000'/>");
				$("#cnslByte", ".fixed-layer").text(Common.getByteString($(this).val()));
			}
		});
		
		$("#clearBtn", ".fixed-layer").click(function(){
			$("#ctnt", ".fixed-layer").val("");
		});
		
		$("#saveBtn", ".fixed-layer").click(cnslViewLayer.saveUpldCtnt);
	});
</script>
<!-- table -->
<div class="tbl-box">
	<form id="layerForm" onsubmit="return false;">
	<table>
		<caption><span id="title"></span></caption>
		<colgroup>
			<col style="width:12%">
			<col style="width:*">
			<col style="width:12%">
			<col style="width:*">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><spring:message code="rpm.tjur.th.rcptno"/></th>
				<td><span id="dataRegNo"></span></td>
				<th scope="row"><spring:message code="rpm.tjur.th.date.upload"/></th>
				<td><span id="upldDt"></span></td>
			</tr>
			<tr>
				<th scope="row"><spring:message code="rpm.tjur.th.rcptMthd"/></th>
				<td><span id="rcptMthdCd"></span></td>
				<th scope="row"><spring:message code="rpm.tjur.th.regr"/></th>
				<td><span id="regr"></span></td>
			</tr>
			<tr id="rsltTr">
				<th scope="row"><spring:message code="rpm.tjur.th.procRslt"/></th>
				<td colspan="3">
					<input type="radio" name="procStat" value="Y">Y
					<input type="radio" name="procStat" value="N">N
				</td>
			</tr>
			<tr>
				<th scope="row"><span id="ctntName"></span><br><span class="byteCount">(<span id="cnslByte">0</span>/4000byte)</span><br />
						<button class="inbtn" type="button" id="clearBtn"><spring:message code="rpm.tjur.th.deleteCtnt"/></button></th>
				<td colspan="3">
					<textarea name="" id="ctnt" class="textarea" rows="1" cols="1" style="width:98%" placeholder=""></textarea> 
				</td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<!--// table -->
<!-- btn -->
<div class="btn-wrap"> 
	<button type="button" class="l-btn cmpl" id="saveBtn"><spring:message code="common.button.save"/></button>
	<button type="button" class="l-btn close" onclick="layer.close();" id="closeBtn"><spring:message code="common.button.close"/></button>
</div>
<!--// btn -->