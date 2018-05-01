<%--
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources"   value="${pageContext.request.contextPath}/resources" />
<script type="text/javascript">
	var layerCpModule = (function(module, $){
		'use strict';
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath: '' };
		var uris = { 	cpForm: '/common/cp/form' ,
						search: '/common/cp/search' };
		var messages = { "noResult" : "<spring:message code='common.list.notice'/>" };
		var forms;
		var context = '.fixed-layer'; //jquery selector context
		/**
		 * public
		 */
		module.init = function(opts){
			options.contextPath = opts.contextPath;		
			forms = opts.forms;
			messages: messages;
		};
		/*가맹점 조회 폼*/
		module.getCpForm = function() {
			impay.sendGet({
				requestUri: options.contextPath + uris.cpForm,
				successCallback: root.bindCp
			});
		};
		module.bindCp = function(data) {				
			if(data != null) {
				for(var i in data) {
					$('#cpStatus').append('<option value="' + data[i].cd + '">' + data[i].cdNm + '</option>');
				}
			}
		};
		/*가맹점 조회*/
		module.getCpSearch = function() {
			impay.sendPost({
				requestUri: options.contextPath + uris.search,
				data: $('#'+ forms.searchForm, context).serialize(),
				dataType: 'json',
				successCallback: root.getCpSearchSuccess
			});
		};
		/*가맹점 조회 성공*/
		module.getCpSearchSuccess = function(data) {
			if(data.total > 0) {
				$('#impay-cp-search-list .cpResult' , context).html(tmpl('tmpl-cplist' , data.content));
				$('#impay-cp-search-list .pagination-wrap', context).css({'display': 'block'});
				
				$('#pagination', context).paging({
		        	totalCount : data.total,
		    		pageSize: $('#rowCount', context).val(),
		    		pageNo: $('#pageIndex', context).val(),
		    		onSelectPage : root.goPage
		        });
			} else {
				$('#impay-cp-search-list .cpResult', context).html('<tr><td colspan="5" align="center">' + messages.noResult + '</td><tr>');
				$('#impay-cp-search-list .pagination-wrap', context).css({'display': 'none'});
			}
		};
		/*가맹점 조회 결과 선택*/
		module.getSelectCp = function(cpCd, paySvcNm, entpId, entpNm) {
			var result = {cpCd:cpCd, paySvcNm:paySvcNm, entpId:entpId, entpNm:entpNm};
			layer.close(result);
		};
		/*법인명 정렬*/
		module.searchOrderByEntpNm = function() {
			$('#sortName', context).val('entpNm');
			if($('#sortOrder', context).val() == 'asc') {
				$('#sortOrder', context).val('desc');
			} else if($('#sortOrder', context).val() == 'desc') {
				$('#sortOrder', context).val('asc');
			}
			$('span.icon-sort', context).toggleClass('selected');
			
			root.getCpSearch();
		};
		/*가맹점명 정렬*/
		module.searchOrderByCpNm = function () {
			$('#sortName', context).val('cpNm');
			if($('#sortOrder', context).val() == 'asc') {
				$('#sortOrder', context).val('desc');
			} else if($('#sortOrder', context).val() == 'desc') {
				$('#sortOrder', context).val('asc');
			}
			
			$('span.icon-sort', context).toggleClass('selected');
			
			root.getCpSearch();
		};
		module.goPage = function(pageNo){
			$('#pageIndex', context).val(pageNo);
			root.getCpSearch();
		};
		return module;
	}(window.layerCpModule || {}, $));
	
	//document ready
	$(function() {
		var messages = {};
		var forms = { 'searchForm': 'frmCpSearch' };
		layerCpModule.init({contextPath: '${contextPath}',
							forms: forms,
							messages: messages});

		layerCpModule.getCpForm();
		
		$('#searchClf option[value="' + layer.getOptions().data.searchClf + '"]' , '.fixed-layer').prop('selected' , true);
		$('#searchWord').val(layer.getOptions().data.searchWord);
		
		layerCpModule.getCpSearch();
		
		$('#btnCpSearchSearch').on('click',function(){
			$('#pageIndex' , '.fixed-layer').val('1');
			layerCpModule.getCpSearch();
		});
		
		$("#searchWord", '.fixed-layer').keydown(function (e) {
			var code = e.which ? e.which : e.keyCode;
			if(code === 13 || code === 10){
				layerCpModule.getCpSearch();
			}
        });
	});
</script>
<!-- search -->
<div class="search-box">
	<fieldset>
		<legend title="가맹점 조회"><spring:message code="common.label.cpSearch"/></legend>
		<form id="frmCpSearch" name="frmCpSearch" onsubmit="return false;">
			<input type="hidden" id="pageIndex" name="pageParam.pageIndex" value="1"/>
			<input type="hidden" id="rowCount" name="pageParam.rowCount" value="10"/>
			<input type="hidden" id="sortName" name="pageParam.sortName" value="entpNm" />
			<input type="hidden" id="sortOrder" name="pageParam.sortOrder" value="asc" />
			<table>
				<caption title="조회 구분"><spring:message code="common.label.searchClf"/></caption>
				<colgroup>
					<col style="width:12%">
					<col style="width:48%">
					<col style="width:10%">
					<col style="width:*" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" title="조회 구분"><spring:message code="common.label.searchClf"/></th>
						<td>
							<select class="input-select" id="searchClf" name="searchClf" style="width:100px;">
								<option value="all" title="전체"><spring:message code="common.select.all"/></option>
								<option value="cpNm" title="가맹점 명"><spring:message code="common.label.cpNm"/></option>
								<option value="cpCd" title="가맹점 코드"><spring:message code="common.label.cpCd"/></option>
								<option value="groupNm" title="그룹명"><spring:message code="common.label.grpNm"/></option>
								<option value="groupCd" title="그룹 코드"><spring:message code="common.label.grpCd"/></option>
								<option value="entpNm" title="법인명"><spring:message code="common.label.entpNm"/></option>
								<option value="entpId" title="법인 코드"><spring:message code="common.label.entpId"/></option>
							</select>
							<input type="text" name="searchWord" id="searchWord" class="input-text" style="width:200px;" placeholder="<spring:message code='common.input.placeholder2'/>">
						</td>
						<th scope="row" title="상태"><spring:message code="common.label.status"/></th>
						<td>
							<select class="input-select" id="cpStatus" name="cpStatus" style="width:150px;">
								<option value='' title="전체"><spring:message code="common.select.all"/></option>
	    					</select>
							<button type="button" class="l-btn searchbtn init-search" id="btnCpSearchSearch" name="btnSearch" title="조회"><spring:message code="common.button.search"/></button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</fieldset>
</div>
<!--// search -->
<!-- grid -->
<div class="panel-box" id="impay-cp-search-list">
	<div class="tbl-info">
		<!-- comment -->
		<span class="tbl-info commt"><span class="ico-required">*<span class="txt" title="하단 항목 한 개를 클릭 시 창이 닫히면서 해당 값이 입력 됩니다."><spring:message code="common.layer.indication1"/></span></span></span>
		<!--// comment -->
	</div>
	<!-- grid -->
	<div class="tbl-grid-box">
		<table>
			<caption title="가맹점 조회"><spring:message code="common.label.cpSearch"/></caption>
			<colgroup>
				<col style="width:24%">
				<col style="width:24%">
				<col style="width:15%">
				<col style="width:*">
				<col style="width:10%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col" title="법인명"><spring:message code="common.label.entpNm"/><span class="icon-sort" id="entpSort"><a href="#" onclick="layerCpModule.searchOrderByEntpNm();"></a></span></th>
					<th scope="col" title="그룹명"><spring:message code="common.label.grpNm"/></th>
					<th scope="col" title="가맹점 코드"><spring:message code="common.label.cpCd"/></th>
					<th scope="col" title="가맹점 명"><spring:message code="common.label.cpNm"/><span class="icon-sort" id="entpSort"><a href="#" onclick="layerCpModule.searchOrderByCpNm();"></a></span></th>
					<th scope="col" title="상태"><spring:message code="common.label.status"/></th>
				</tr>
			</thead>
			<tbody class="cpResult">
			</tbody>
		</table>
		<%-- template --%>
		<script type="text/html" id="tmpl-cplist">
			{% for (var i=0; i<o.length; i++) { %}
				<tr onclick="layerCpModule.getSelectCp('{%=o[i].cpCd%}' , '{%=o[i].paySvcNm%}', '{%=o[i].entpId%}', '{%=o[i].entpNm%}')">
					<td>{%=o[i].entpNm%}</td>
					<td>{%=o[i].cpGrpNm%}</td>
					<td class="txt-center">{%=o[i].cpCd%}</td>
					<td>{%=o[i].paySvcNm%}</td>
					<td>{%=o[i].statNm%}</td>
				</tr>
			{% } %}
		</script>
		<!-- pagination -->
		<div class="pagination-wrap">
			<div id="pagination" class="pagination-inner"></div>
		</div>
		<!--// pagination -->
		<!-- btn -->
		<div class="btn-wrap">
			<button type="button" class="l-btn close" id="" onclick="layer.close();"><spring:message code="common.button.close"/></button>
		</div>
		<!--// btn -->
	</div>
</div>