<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <%@ include file="/WEB-INF/jsp/common/include.jsp" %>
	<title><spring:message code="title.card"/></title>
</head>

<body>
<div id="adjust" data-role="page" data-fullscreen="true">
    <%@ include file="/WEB-INF/jsp/common/slidemenu.jsp" %>
    <!--header-->
    <header data-role="header" class="page-header">
        <div class="main-top-btn-section">
            <a href="#" class="btn-icon-back" data-rel="back" data-direction="reverse">back</a>
            <h1 title="법인카드 정산"><spring:message code="title.card"/></h1>
            <a href="#slideMenu" class="btn-icon-menu">slide menu</a>
        </div>
    </header>
    <!--/header-->
    <!--main-->
    <main class="page-main">
        <!--대상카드 선택-->
        <header class="option-section">
            <dl class="input-group-search">
                <dt class="input-group-tit" title="대상카드"><spring:message code="text.card.target"/></dt>
                <dd>
                    <span class="input-group-box" title="전체"><spring:message code="common.all"/></span>
                    <a href="${contextPath}/adjust/selectCard" data-transition="slide" class="btn-icon-search-blue input-group-btn"></a>
                </dd>
            </dl>
            <form id="cardFrm">
                <fieldset data-role="controlgroup" data-type="horizontal" class="small margin-b0 margin-l80">
                    <input type="radio" name="status" id="radio-choice-1-a" value="">
                    <label for="radio-choice-1-a" title="전체"><spring:message code="common.all"/></label>
                    <input type="radio" name="status" id="radio-choice-1-b" value="N" checked="checked">
                    <label for="radio-choice-1-b" class="ui-radio-on" title="미처리"><spring:message code="text.status.no"/></label>
                    <input type="radio" name="status" id="radio-choice-1-c" value="Y">
                    <label for="radio-choice-1-c" title="정산완료"><spring:message code="text.status.complete"/></label>
                </fieldset>
                <input type="hidden" id="cardno" name="cardno" value="ALL">
                <input type="hidden" id="from" name="from" value="${fromDate}">
                <input type="hidden" id="to" name="to" value="${toDate}">
            </form>
        </header>
        <!--/대상카드 선택-->
	    <main>
		    <table id="toggleTbl" class="table-style1 table-row2">
		        <colgroup>
		            <col width="40px" />
		            <col width="70px" />
		            <col width="" />
		            <col width="80px" />
		            <col width="70px" />
		        </colgroup>
		        <thead>
		            <tr>
		                <th></th>
		                <th title="사용일시"><spring:message code="text.use.date"/></th>
		                <th title="사용처"><spring:message code="text.use.location"/></th>
		                <th title="금액"><spring:message code="text.price"/></th>
		                <th title="처리상태"><spring:message code="text.status"/></th>
		            </tr>
		        </thead>
		        <tbody id="cardHistoryContent"></tbody>
		    </table>
		
	<script type="text/x-tmpl" id="tmpl-historyList">
	{% for(var i=0; i<o.length; i++){ %}
	<tr data-seq="{%=o[i].seq%}">
        <td>
			<a href="#" class="btn-icon-expand"></a>
			<input type="hidden" name="cardno" value="{%=o[i].cardno%}">
			<input type="hidden" name="apprno" value="{%=o[i].apprno%}">
			<input type="hidden" name="transferFlag" value="{%=o[i].transferFlag%}">
		</td>
        <td><span>{%=o[i].transdate %}</span><br /><span>{%=o[i].transtime %}</span></td>
        <td><span class="table-2row-ellipsis">{%=o[i].merchname %}</span></td>
        <td>{%=common.formatCurrency(o[i].apprtot)%}<spring:message code="common.unit.currency"/></td>
        <td>
		{% if(o[i].statCd === "Y"){ %}
			<span class="green-txt">
				{%=o[i].statNm %}
				<input type="hidden" name="statCd" value="{%=o[i].statCd %}">
			</span>
		{% } else if(o[i].statCd === "N"){ %}
			<span class="red-txt">
				{%=o[i].statNm %}
				<input type="hidden" name="statCd" value="{%=o[i].statCd %}">
			</span>
		{% } %}
		</td>
    </tr>
    <tr class="collapse-tr">
        <td colspan="5">
            <dl class="collapse-tr-inner">
                <div class="col-xs-12">
                    <dt><spring:message code="text.card.number"/></dt>
                    <dd class="cardno">{%=o[i].cardno %} ({%=o[i].apprno %})</dd>
                </div>
                <div class="col-xs-6">
                    <dt><spring:message code="text.price.supply"/></dt>
                    <dd class="appramt">{%=common.formatCurrency(o[i].appramt)%}<spring:message code="common.unit.currency"/></dd>
                </div>
                <div class="col-xs-6">
                    <dt><spring:message code="text.type.tax"/></dt>
                    <dd class="taxtype">{%=o[i].taxtype %}</dd>
                </div>
                <div class="col-xs-6">
                    <dt><spring:message code="text.price.vat"/></dt>
                    <dd class="vat">{%=common.formatCurrency(o[i].vat)%}<spring:message code="common.unit.currency"/></dd>
                </div>
                <div class="col-xs-6">
                    <dt><spring:message code="text.type.bussiness"/></dt>
                    <dd class="mccname">{%=o[i].mccname %}</dd>
                </div>
                <div class="col-xs-12">
                    <dt><spring:message code="common.address"/></dt>
                    <dd class="merchaddr">{%=o[i].merchaddr %}</dd>
                </div>
            </dl>
            <dl class="collapse-tr-inner">
                <div class="col-xs-6">
                    <dt><spring:message code="text.approve.user1"/></dt>
                    <dd class="apprPerson">{%=o[i].apprPerson %}</dd>
                </div>
                <div class="col-xs-6">
                    <dt><spring:message code="text.approve.date"/></dt>
                    <dd class="apprDate">{%=o[i].apprDate %}</dd>
                </div>
                <div class="col-xs-12">
                    <dt><spring:message code="text.approve.msg"/></dt>
                    <dd class="apprComments">{%=o[i].apprComments %}</dd>
                </div>
            </dl>
        </td>
    </tr>
	{% } %}
	</script>
		    <!--footer-->
		    <footer class="btn-footer container page-footer" data-role="footer" data-tab-toggle="false">
		        <a href="#popMenu1" id="btnAdjExcpt" class="col-xs-4 button-etc wbtn" data-position-to="window" data-rel="popup" data-transition="slideup" title="정산제외">
					<spring:message code="text.adjust.except"/>
		        </a>
		        <a href="#" id="btnAdj" data-ajax="false" class="col-xs-4 button-recognize wbtn" title="정산">
					<spring:message code="text.adjust"/>
		        </a>
		        <a href="#popMenu2" id="btnAdjCncl" class="col-xs-4 button-cancel wbtn" data-position-to="window" data-rel="popup" data-transition="slideup" title="정산취소">
					<spring:message code="text.adjust.cancel"/>
		        </a>
		        <input type="hidden" id="isValidTerm" value="${isValidTerm }">
		        <c:if test="${isValidTerm eq true}">
		        	<input type="hidden" id="validTermMessage" value="${validTermMessage }">
		        </c:if>
		    </footer>
		    <!-- /footer -->
		    
		    <!--사유선택 팝업-->
		    <div data-role="popup" id="popMenu1" class="ui-content" data-theme="a" data-dismissible="false">
		        <main>
		            <p title="정산제외 하시겠습니까?"><spring:message code="msg.adjust.except.confm"/></p>
		        </main>
		        <footer>
		            <a href="#" class="btn-basic button-recognize btn-colgroup-2" data-rel="back" id="btnExcpt"><spring:message code="common.ok"/></a>
		            <a href="#" class="btn-basic button-cancel btn-colgroup-2" data-rel="back"><spring:message code="common.cancel"/></a>
		        </footer>
		    </div>
		    <!--사유선택 팝업-->
		    
		    <!--정산취소 팝업-->
		    <div data-role="popup" id="popMenu2" class="ui-content" data-theme="a" data-dismissible="false">
		        <main>
		            <p title="정산을 취소하시겠습니까?"><spring:message code="msg.adjust.cancel.confm"/></p>
		        </main>
		        <footer>
		            <a href="#" class="btn-basic button-recognize btn-colgroup-2" data-rel="back" id="btnCncl"><spring:message code="common.ok"/></a>
		            <a href="#" class="btn-basic button-cancel btn-colgroup-2" data-rel="back"><spring:message code="common.cancel"/></a>
		        </footer>
		    </div>
		    <!--/정산취소 팝업-->
		    
		    <!-- alert 팝업 -->
		    <div data-role="popup" id="popAlert" class="ui-content" data-theme="a" data-dismissible="false">
		        <main>
		            <p id="alertMsg"></p>
		        </main>
		        <footer>
		            <a href="#" class="btn-basic button-recognize btn-colgroup-2 width100" data-rel="back"><spring:message code="common.ok"/></a>
		        </footer>
		    </div>
	    </main>
    </main>
    <!--/alert 팝업-->
</div>
<!--/page-->

<!--table row toggle-->
<script src="${resources}/js/card/adjust/adjust.js"></script>
<script src="${resources}/js/card/adjust/adjustValid.js"></script>
<script src="${resources}/js/card/adjust/adjustEvent.js"></script>
<script src="${resources}/js/common/slideEvent.js"></script>
<script>
	"use strict";
	
	// 메시지 초기화
	messages.add({
		currency 		: '<spring:message code="common.unit.currency"/>',
		cancelConfm 	: '<spring:message code="msg.adjust.cancel.confm"/>',
		cancelNo 		: '<spring:message code="msg.adjust.cancel.no"/>',
		cancelOk		: '<spring:message code="msg.adjust.cancel.ok"/>',
		excptNo 		: '<spring:message code="msg.adjust.except.no"/>',
		excptOk 		: '<spring:message code="msg.adjust.except.ok"/>',
		detailNo 		: '<spring:message code="msg.adjust.detail.no"/>',
		targetSel 		: '<spring:message code="msg.adjust.target.sel"/>',
		duplAdjust 		: '<spring:message code="msg.adjust.duplicate"/>'
	});
	// 정산로직 초기화
	adjust.init({
		contextPath : "${contextPath}"
	});
	adjust.getCardHistoryList();
</script>
</body>
</html>
