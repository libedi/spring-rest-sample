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
	var tjurLayer = (function(module, $){
		"use strict";
		
		/**
		 * private
		 */
		var root = module;
		var options = { contextPath : "" };
		var uris = {
				view : "/cnslMng/tjurProc/view",
				search : "/cnslMng/search/cnslCtnt",
				send : "/cnslMng/tjurProc/sendEmail"
		};
		var messages;
		var context;	//jquery selector context
		var data;
		var forms;
		
		/**
		 * public 
		 */	
		module.init = function(opts){
			options.contextPath = opts.contextPath;
			forms = opts.forms;
			messages = opts.messages;
			context = opts.context;
			data = opts.data;
		};
		module.getOptions = function(){
			return root.options;
		};
		// 폼 셋팅
		module.view = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.view,
				dataType: "json",
				successCallback: function(result){
					var html = "";
					for(var i=0; i<result.length; i++){
						html += "<option value='" + result[i].cd + "' data-addr='" + result[i].prepWord1 + "'>" + result[i].cdNm + "</option>";
					}
					$("#deptCd", context).append(html);
					root.search();
				}
			});
		};
		// 조회
		module.search = function(){
			impay.sendGet({
				requestUri: options.contextPath + uris.search,
				data: {rcptNo : data.rcptNo},
				dataType: "json",
				successCallback: root.resultBind
			});
		};
		// 출력
		module.resultBind = function(result){
			if(result !== null){
				// 접수정보
				var regDts = result.regDt.split(" ");
				$("#rcptDt", context).text(regDts[0]);
				$("input[name='rcptDt']", context).val(regDts[0]);
				$("#regr", context).text(result.regr);
				$("input[name='regr']", context).val(result.regr);
				$("#rcptTm", context).text(regDts[1]);
				$("input[name='rcptTm']", context).val(regDts[1]);
				$("#rcptNo", context).text(result.rcptNo);
				$("input[name='rcptNo']", context).val(result.rcptNo);
				// 거래정보
				if(result.tradeModel !== null && result.tradeModel.trdNo !== null){
					var trd = result.tradeModel;
					$("#payCndiNm", context).text(result.payCndiNm);
					$("input[name='payCndiNm']", context).val(result.payCndiNm);
					$("#commcClfNm", context).text(trd.commcClfNm);
					$("input[name='commcClfNm']", context).val(result.commcClfNm);
					$("#payAmt", context).text(Common.formatMoney(trd.payAmt));
					$("input[name='payAmt']", context).val(Common.formatMoney(trd.payAmt));
					$("#paySvcNm", context).text(trd.paySvcNm);
					$("input[name='paySvcNm']", context).val(trd.paySvcNm);
					$("#godsNm", context).text(trd.godsNm);
					$("input[name='godsNm']", context).val(trd.godsNm);
					$("#trdDt", context).text(trd.trdDt);
					$("input[name='trdDt']", context).val(trd.trdDt);
					$("#cnclDt", context).text(StringUtil.nvl(trd.cnclDt, ""));
					$("input[name='cnclDt']", context).val(StringUtil.nvl(trd.cnclDt, ""));
					$("#avlCncl", context).text(trd.avlCncl);
					$("input[name='avlCncl']", context).val(trd.avlCncl);
					$("#trdNo", context).text(trd.trdNo);
					$("input[name='trdNo']", context).val(trd.trdNo);
					$("#telNo", context).text(StringUtil.nvl(trd.telNo, ""));
					$("input[name='telNo']", context).val(StringUtil.nvl(trd.telNo, ""));
				}
				// 상담정보
				$("#cnslClfNm", context).text(StringUtil.nvl(result.cnslClfUprNm, ""));
				$("input[name='cnslClfNm']", context).val(StringUtil.nvl(result.cnslClfUprNm, ""));
				$("#cnslEvntNm", context).text(StringUtil.nvl(result.cnslEvntNm, ""));
				$("input[name='cnslEvntNm']", context).val(StringUtil.nvl(result.cnslEvntNm, ""));
				$("#cnslTypNm", context).text(result.cnslTypNm);
				$("input[name='cnslTypNm']", context).val(result.cnslTypNm);
				$("#rcptMthdNm", context).text(result.rcptMthdNm);
				$("input[name='rcptMthdNm']", context).val(result.rcptMthdNm);
				$("textarea[name='cnslCtnt']", context).val(StringUtil.nvl(result.cnslCtnt, "").replace(/(<br\/>|<br>)/g, "\r\n"));
				$("#byteCount", context).text(Common.getByteString(result.cnslCtnt));
			}
		};
		// 전송
		module.send = function(){
			impay.sendPost({
				requestUri: options.contextPath + uris.send,
				data: $("#" + forms.sendForm, context).serialize(),
				dataType: "json",
				successCallback : function(result){
					if(result.success === true){
						alert(messages.sendOk);
						layer.close({
							rcptNo : $("input[name='rcptNo']", context).val(),
							cnslTjurCtnt : $("textarea[name='cnslCtnt']", context).val(),
							procTjurCtnt : $("#reqCtnt", context).val(),
							deptCd : $("#deptCd", context).val(),
							tjurClfFlg : "B",
							tjurProcYn : "N"
						});
					} else {
						alert(messages.error);
					}
				}
			});
		};
		
		return module;
	}(window.tjurLayer || {}, $));
	
	$(function(){
		var data = layer.getOptions().data;
		var messages = {
				"sendOk" : "<spring:message code='common.email.message.success'/>",
				"error" : "<spring:message code='common.error.message'/>"
		};
		var forms = {sendForm: "sendForm"};
		tjurLayer.init({
				contextPath: layer.getOptions().contextPath, 
				context: ".fixed-layer",
				forms: forms,
				messages: messages,
				data: data
		});
		
		// 등록건 조회
		tjurLayer.view();
		
		// 이관내용 바이트 제한
		$("#reqCtnt", ".fixed-layer").keyup(function(){
			var ctntByte = Common.getByteString($(this).val());
			$("#byteCount", ".fixed-layer").text(ctntByte);
			if(ctntByte > 4000){
				$(this).val(Common.getStringToByte($(this).val(), 4000));
				alert("<spring:message code='csm.cnslmng.msg.limit.byte' arguments='4000'/>");
				$("#byteCount", ".fixed-layer").text(Common.getByteString($(this).val()));
			}
		});
		
		// 부서선택시 해당부서 담당 이메일주소 설정
		$("#deptCd", ".fixed-layer").on("change", function(){
			$("#emailAddr", ".fixed-layer").val($(this).find("option:selected").data("addr"));
		});
		
		$("#sendBtn", ".fixed-layer").click(function(){
			utils.applyTrim("sendForm");
			sendEmail();
		});
		
		// 닫기
		$(".closePop", ".fixed-layer").click(function(){
			if(confirm("<spring:message code='csm.cnslmng.layer.tjur.confirm'/>")){
				layer.close();
			}
		});
	});
	
	function sendEmail(){
		$("#sendForm", ".fixed-layer").validate({
	    	debug: true,
			onfocusout: false,
	        rules: {
	        	title : { required : true },
	        	deptCd : { required : true },
				emailAddr : { required : true },
				cnslCtnt : { required : true },
	        	reqCtnt : { required : true }
			},
			messages: {
				title: {
					required: "<spring:message code='csm.cnslmng.layer.tjur.msg.title'/>"
				},
				deptCd: {
					required: "<spring:message code='csm.cnslmng.layer.tjur.msg.dept'/>"
				},
				emailAddr: {
					required: "<spring:message code='csm.cnslmng.layer.tjur.msg.email'/>"
				},
				cnslCtnt: {
					required: "<spring:message code='csm.cnslmng.msg.input.cnslCtnt'/>"
				},
				reqCtnt: {
					required: "<spring:message code='csm.cnslmng.layer.tjur.msg.content'/>"
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
	        	tjurLayer.send();
			},
	        showErrors: function(errorMap, errorList) {
	        }
		});
		
		if(confirm("<spring:message code='common.email.message.sendEmail'/>")){
			$("#sendForm", ".fixed-layer").submit();
		}
	}
</script>
<form id="sendForm">
	<div class="search-box">
		<table>
			<caption title="이메일 정보"><spring:message code="csm.cnslmng.layer.tjur.caption1"/></caption>
			<colgroup>
				<col style="width:13%">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><spring:message code="common.email.label.title"/></th>
					<td><input type="text" class="input-text" name="title" style="width:98%" placeholder="<spring:message code='common.email.message.title'/>"></td>
				</tr>
				<tr>
					<th scope="row" title="이관 부서 선택"><spring:message code="csm.cnslmng.layer.tjur.th.dept"/></th>
					<td>
						<select class="input-select" name="deptCd" id="deptCd">
							<option value="" data-addr="" title="선택"><spring:message code="common.select.select"/></option>
						</select>
						<input type="text" class="input-text" name="emailAddr" id="emailAddr" style="width:90%" placeholder="<spring:message code='csm.cnslmng.layer.tjur.placeholder1'/>">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="sendEmailTitle">
		<h3 class="cont-h3"><spring:message code="common.footer.info.cs"/></h3>
	</div>
	<div class="sendEmailBar"></div>
	<div class="tbl-box">
		<table>
			<caption title="이메일 접수 내용1"><spring:message code="csm.cnslmng.layer.tjur.caption2"/></caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:13%">
				<col style="width:11%">
				<col style="width:14%">
				<col style="width:13%">
				<col>
				<col style="width:12%">
				<col style="width:12%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="접수일자"><spring:message code="csm.cnslmng.layer.tjur.date.reg"/></th>
					<td>
						<span id="rcptDt"></span>
						<input type="hidden" name="rcptDt" value=""/>
					</td>
					<th scope="row" title="접수자"><spring:message code="csm.cnslmng.th.rcptregr"/></th>
					<td>
						<span id="regr"></span>
						<input type="hidden" name="regr" value=""/>
					</td>
					<th scope="row" title="접수 시간"><spring:message code="csm.cnslmng.th.time.rcpt"/></th>
					<td>
						<span id="rcptTm"></span>
						<input type="hidden" name="rcptTm" value=""/>
					</td>
					<th scope="row" title="접수 번호"><spring:message code="csm.cnslmng.th.rcptno"/></th>
					<td>
						<span id="rcptNo"></span>
						<input type="hidden" name="rcptNo" value=""/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="tbl-box mrg-t10">
		<table>
			<caption title="이메일 접수 내용2"><spring:message code="csm.cnslmng.layer.tjur.caption3"/></caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:38%">
				<col style="width:13%">
				<col style="width:*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="결제 조건"><spring:message code="csm.cnslmng.th.conditaion.pay"/></th>
					<td>
						<span id="payCndiNm"></span>
						<input type="hidden" name="payCndiNm" value=""/>
					</td>
					<th scope="row" title="거래 일시"><spring:message code="csm.cnslmng.th.date.trade"/></th>
					<td>
						<span id="trdDt"></span>
						<input type="hidden" name="trdDt" value=""/>
					</td>
				</tr>
				<tr>
					<th scope="row" title="통신사"><spring:message code="csm.cnslmng.th.commc"/></th>
					<td>
						<span id="commcClfNm"></span>
						<input type="hidden" name="commcClfNm" value=""/>
					</td>
					<th scope="row" title="취소 일시"><spring:message code="csm.cnslmng.th.date.cancel"/></th>
					<td>
						<span id="cnclDt"></span>
						<input type="hidden" name="cnclDt" value=""/>
					</td>
				</tr>
				<tr>
					<th scope="row" title="거래 금액"><spring:message code="csm.cnslmng.th.tradeamount"/></th>
					<td>
						<span id="payAmt"></span>
						<input type="hidden" name="payAmt" value=""/>
					</td>
					<th scope="row" title="취소 가능 여부"><spring:message code="csm.cnslmng.th.cancel.yn"/></th>
					<td>
						<span id="avlCncl"></span>
						<input type="hidden" name="avlCncl" value=""/>
					</td>
				</tr>
				<tr>
					<th scope="row" title="가맹점명"><spring:message code="csm.cnslmng.th.cpname"/></th>
					<td>
						<span id="paySvcNm"></span>
						<input type="hidden" name="paySvcNm" value=""/>
					</td>
					<th scope="row" title="결제 승인 번호"><spring:message code="csm.cnslmng.th.tradeno"/></th>
					<td>
						<span id="trdNo"></span>
						<input type="hidden" name="trdNo" value=""/>
					</td>
				</tr>
				<tr>
					<th scope="row" title="상품명"><spring:message code="csm.cnslmng.th.goodsname"/></th>
					<td>
						<span id="godsNm"></span>
						<input type="hidden" name="godsNm" value=""/>
					</td>
					<th scope="row" title="고객센터"><spring:message code="csm.cnslmng.th.center"/></th>
					<td>
						<span id="telNo"></span>
						<input type="hidden" name="telNo" value=""/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="tbl-info mrg-t20">
		<h4 class="cont-h4" title="상담 내역"><spring:message code="csm.cnslmng.header.title.content.cnsl"/></h4>
	</div>
	<div class="tbl-box">
		<table>
			<caption title="상담 내역1"><spring:message code="csm.cnslmng.caption.content.cnsl1"/></caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:38%">
				<col style="width:13%">
				<col style="width:*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="상담 구분"><spring:message code="csm.cnslmng.th.cnslgubun"/></th>
					<td>
						<span id="cnslClfNm"></span>
						<input type="hidden" name="cnslClfNm" value=""/>
					</td>
					<th scope="row" title="이벤트"><spring:message code="csm.cnslmng.th.event"/></th>
					<td>
						<span id="cnslEvntNm"></span>
						<input type="hidden" name="cnslEvntNm" value=""/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="tbl-box mrg-t10">
		<table>
			<caption title="상담 내역2"><spring:message code="csm.cnslmng.caption.content.cnsl2"/></caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:38%">
				<col style="width:13%">
				<col style="width:*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" title="상담 유형"><spring:message code="csm.cnslmng.th.cnsltype"/></th>
					<td>
						<span id="cnslTypNm"></span>
						<input type="hidden" name="cnslTypNm" value=""/>
					</td>
					<th scope="row" title="접수유형"><spring:message code="csm.cnslmng.th.rcpttype"/></th>
					<td>
						<span id="rcptMthdNm"></span>
						<input type="hidden" name="rcptMthdNm" value=""/>
					</td>
				</tr>
				<tr>
					<th scope="row" title="상담내용"><spring:message code="csm.cnslmng.th.content.cnsl"/></th>
					<td><textarea class="textarea" name="cnslCtnt" id="cnslCtnt" style="width:96%"></textarea></td>
					<th scope="row" title="이관 요청내용"><spring:message code="csm.cnslmng.layer.tjur.content"/><br/>(<span id="byteCount">0</span>/4000byte)</th>
					<td><textarea class="textarea" name="reqCtnt" id="reqCtnt" style="width:96%" placeholder="<spring:message code='csm.cnslmng.layer.tjur.placeholder2'/>"></textarea></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="email-footer mrg-tm1">
	<span class="footer-ci"><img src="${resources}/images/skp.png" alt="SK planet"></span>
	<dl class="footer-info">
		<dt title="고객센터"><strong><spring:message code="common.footer.info.cs"/> :</strong></dt>
		<dd><strong>1599-2583</strong></dd>
		<dt title="운영시간"><spring:message code="common.footer.info.cs.run"/> :</dt>
		<dd title="평일(월~금) 09:00 ~ 18:00"><spring:message code="common.footer.info.cs.runtime"/></dd>
	</dl>
</div>				
<!-- btn -->
<div class="btn-wrap">
	<button type="button" class="l-btn cmpl " id="sendBtn" title="전송"><spring:message code="csm.cnslmng.layer.tjur.btn.send"/></button>
	<button type="button" class="l-btn closePop" id="cancelBtn" title="취소"><spring:message code="common.button.cancel"/></button>
</div>
<!--// btn -->