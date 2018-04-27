<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
	<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
	<title><spring:message code="title.card.detail"/></title>
</head>

<body>
<div id="detail" data-role="page">
	<%@ include file="/WEB-INF/jsp/common/slidemenu.jsp" %>
    <!--header-->
    <header data-role="header" class="tit-header page-header">
        <div class="main-top-btn-section">
            <a href="#" class="btn-icon-back" data-rel="back" data-direction="reverse">back</a>
            <h1><spring:message code="title.card.detail"/></h1>
            <a href="#slideMenu" class="btn-icon-menu">slide menu</a>
        </div>
    </header>
    <!--/header-->
    <!--main-->
    <main class="page-main">
        <!--option section-->
        <header class="option-section">
        	<form id="headerFrm">
            <dl class="input-group-search">
                <dt class="input-group-tit" title="부가세"><spring:message code="text.price.vat"/></dt>
                <dd>
					<fieldset data-role="controlgroup" data-type="horizontal" class="small margin-b0 radio-colgroup-2">
					    <input type="radio" name="hasVat" id="radio-choice-3-a" value="1"
					    		<c:if test="${card.hasVatYn eq 'Y'}">checked="checked"</c:if>
					    		<c:if test="${card.hasVatYn eq 'N'}">disabled="disabled"</c:if> />
					    <label for="radio-choice-3-a" title="포함"><spring:message code="text.supply.contain"/></label>
					    <input type="radio" name="hasVat" id="radio-choice-3-b" value="0" 
					    		<c:if test="${card.hasVatYn eq 'N'}">checked="checked"</c:if> />
					    <label for="radio-choice-3-b" class="ui-radio-on" title="제외(불공제 등)"><spring:message code="text.supply.contain.no"/></label>
					</fieldset>
                </dd>
            </dl>
            <dl class="input-group-search">
                <dt class="input-group-tit" title="유형"><spring:message code="text.type"/></dt>
                <dd>
					<fieldset data-role="controlgroup" data-type="horizontal" class="small margin-b0 radio-colgroup-2">
					    <input type="radio" name="adjustType" id="radio-choice-4-a" value="D" checked="checked">
					    <label for="radio-choice-4-a" title="부서경비"><spring:message code="text.type.dept"/></label>
					    <input type="radio" name="adjustType" id="radio-choice-4-b" value="P">
					    <label for="radio-choice-4-b" class="ui-radio-on" title="프로젝트 비용"><spring:message code="text.type.project"/></label>
					</fieldset>
                </dd>
            </dl>
            <input type="hidden" name="seq" value="${card.seq}">
            <input type="hidden" id="apprtot" value="${card.apprtot}">
            <input type="hidden" id="initAmt" value="${card.appramt}">
            <input type="hidden" id="initVat" value="${card.vat}">
            <input type="hidden" id="selfApproveYn" value="${autoApproveInfo.selfApproveYn}">
            <input type="hidden" id="autoApproveAmt" value="${autoApproveInfo.autoApproveAmt}">
            </form>
        </header>
        <!--/option section-->
    <main>
    <!--/main-->

    <!--고정테이블-->
    <table class="table-style1">
        <colgroup>
            <col width="70px" />
            <col width="" />
            <col width="80px" />
            <col width="70px" />
        </colgroup>
        <thead>
            <tr>
                <th title="사용일시"><spring:message code="text.use.date"/></th>
                <th title="사용처"><spring:message code="text.use.location"/></th>
                <th title="공급가"><spring:message code="text.price.supply"/></th>
                <th title="부가세"><spring:message code="text.price.vat"/></th>
            </tr>
        </thead>
        <tbody id="historyContent">
            <tr>
                <td><span>${card.transdate}</span><br /><span>${card.transtime}</span></td>
                <td><span class="table-2row-ellipsis">${card.merchname}</span></td>
                <td id="tdAmt"><fmt:formatNumber value="${card.appramt}" pattern="#,###"/><spring:message code="common.unit.currency"/></td>
                <td id="tdVat"><fmt:formatNumber value="${card.vat}" pattern="#,###"/><spring:message code="common.unit.currency"/></td>
            </tr>
        </tbody>
    </table>
    <!--/고정테이블-->
    
    <!--유형:부서경비 선택시 활성테이블(비활성시 table에 class="hidden" 처리)-->
    <form id="deptFrm">
    	<input type="hidden" name="cardno" value="${card.cardno}"/>
	    <table id="deptTbl" class="table-style2 padding-t0">
	        <colgroup>
	            <col width="80px" />
	            <col width="" />
	        </colgroup>
	        <tbody>
	            <tr>
	                <th title="정산일자"><spring:message code="text.adjust.date"/></th>
	                <td>
	                    <div class="input-box-wrap datepicker-search">
		                    <input id="input_01" class="datepicker" name="glDate" type="text" data-value="${card.initDate}">
	                    </div>
	                </td>
	            </tr>
	            <tr>
	                <th title="승인권자"><spring:message code="text.approve.user2"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="${contextPath}/detail/selectAppr" data-transition="slide">
	                        <c:if test="${autoApproveInfo.selfApproveYn eq 'Y'}">
	                            <span class="hname">${user.hname}</span>
	                            <span class="btn-icon-search-small"></span>
	                            <input type="hidden" name="empcd" value="${user.empcd}">
	                            <input type="hidden" name="empId" value="${user.empId}">
	                            <input type="hidden" name="userId" value="${user.userId}">
	                        </c:if>
	                        <c:if test="${autoApproveInfo.selfApproveYn eq 'N'}">
	                            <span class="hname">${superUser.hname}</span>
	                            <span class="btn-icon-search-small"></span>
	                            <input type="hidden" name="empcd" value="${superUser.empcd}">
	                            <input type="hidden" name="empId" value="${superUser.empId}">
	                            <input type="hidden" name="userId" value="${superUser.userId}">
	                        </c:if>
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr>
	                <th title="첨부파일"><spring:message code="text.attach-file"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <input type="file" name="file" id="file" value="">
	                    </div>
	                    <ul class="att fileList"></ul>
	                </td>
	            </tr>
	            <tr>
	                <th title="참석자"><spring:message code="text.attendance"/></th>
	                <td>
	                    <input type="text" class="input-box-wrap user-input" name="attend">
	                </td>
	            </tr>
	            <tr class="tr-divide add-th-bg row-index-0">
	                <th title="부서"><spring:message code="text.dept"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="${contextPath}/detail/selectDept?index=0" data-transition="slide">
	                            <span class="deptNm">${user.costCenterName}</span>
	                            <span class="btn-icon-right"></span>
	                            <input type="hidden" name="deptCode" value="${user.costCenter}">
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="계정"><spring:message code="text.account"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="${contextPath}/detail/selectAccnt?index=0" data-transition="slide">
	                            <span class="accName"></span>
	                            <span class="btn-icon-right"></span>
	                            <input type="hidden" name="mainAccCode">
	                            <input type="hidden" name="subAccCode">
	                            <input type="hidden" name="attributeCategory">
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="적요"><spring:message code="text.description"/></th>
	                <td>
	                	<input type="text" class="input-box-wrap user-input" name="description">
<!-- 	                    <textarea name="description" class="input-box-wrap user-input" rows="3"></textarea> -->
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="공급가"><spring:message code="text.price.supply"/></th>
	                <td>
	                    <div class="input-box-wrap user-input">
	                        <span class="input-box-txt">
	                        	<input type="text" class="currency" name="appramt" value="<fmt:formatNumber value='${card.appramt}' pattern='#,###'/>"><spring:message code="common.unit.currency"/>
	                        </span>
	                    </div>
	                </td>
	            </tr>
			</tbody>
	    </table>
    </form>
    <!--/부서경비 선택시 활성테이블-->
    
    <script type="text/x-tmpl" id="tmpl-fileList">
		<li data-id="{%=o.fileId %}" data-nm="{%=o.originalFileNm %}">
			<a href="#" data-url="${contextPath}/api/files/{%=o.fileId %}" class="file-view" data-transition="fade">{%=o.originalFileNm %}</a>
			<a href="#popDelete" data-position-to="window" data-rel="popup" data-transition="slideup" class="add-del" data-id="{%=o.fileId %}"></a>
		</li>
	</script>
	
    <script type="text/x-tmpl" id="tmpl-deptTbl">
		<tr class="tr-divide add-th-bg row-index-{%=o.index %}">
            <th title="부서"><spring:message code="text.dept"/></th>
            <td>
                <div class="input-box-wrap">
                    <a href="${contextPath}/detail/selectDept?index={%=o.index%}" data-transition="slide">
                        <span class="deptNm"></span>
                        <span class="btn-icon-right"></span>
                        <input type="hidden" name="deptCode">
                    </a>
                </div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="계정"><spring:message code="text.account"/></th>
            <td>
                <div class="input-box-wrap">
                    <a href="${contextPath}/detail/selectAccnt?index={%=o.index%}" data-transition="slide">
                        <span class="accName"></span>
                        <span class="btn-icon-right"></span>
                        <input type="hidden" name="mainAccCode">
                        <input type="hidden" name="subAccCode">
						<input type="hidden" name="attributeCategory">
                    </a>
                </div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="적요"><spring:message code="text.description"/></th>
            <td>
				<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
					<input type="text" class="input-box-wrap user-input" name="description">
				</div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="공급가"><spring:message code="text.price.supply"/></th>
            <td>
				<div class="input-box-wrap user-input">
					<span class="input-box-txt">
						<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
	                       	<input type="text" class="currency" name="appramt" value="0">
						</div>
						<spring:message code="common.unit.currency"/>
                    </span>
                </div>
            </td>
        </tr>
	</script>
	
    <!--유형:프로젝트비용 선택시 활성테이블(비활성시 table에 class="hidden" 처리)-->
    <form id="prjFrm">
    	<input type="hidden" name="cardno" value="${card.cardno}"/>
	    <table id="prjTbl" class="table-style2 padding-t0 hidden">
	        <colgroup>
	            <col width="80px" />
	            <col width="" />
	        </colgroup>
	        <tbody>
	            <tr>
	                <th title="정산일자"><spring:message code="text.adjust.date"/></th>
	                <td>
	                    <div class="input-box-wrap datepicker-search">
		                    <input id="input_01" class="datepicker" name="glDate" type="text" data-value="${card.initDate}">
	                    </div>
	                </td>
	            </tr>
	            <tr>
	                <th title="승인권자"><spring:message code="text.approve.user2"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="${contextPath}/detail/selectAppr" data-transition="slide">
	                        <c:if test="${autoApproveInfo.selfApproveYn eq 'Y'}">
	                            <span class="hname">${user.hname}</span>
	                            <span class="btn-icon-search-small"></span>
	                            <input type="hidden" name="empcd" value="${user.empcd}">
	                            <input type="hidden" name="empId" value="${user.empId}">
	                            <input type="hidden" name="userId" value="${user.userId}">
	                        </c:if>
	                        <c:if test="${autoApproveInfo.selfApproveYn eq 'N'}">
	                            <span class="hname">${superUser.hname}</span>
	                            <span class="btn-icon-search-small"></span>
	                            <input type="hidden" name="empcd" value="${superUser.empcd}">
	                            <input type="hidden" name="empId" value="${superUser.empId}">
	                            <input type="hidden" name="userId" value="${superUser.userId}">
	                        </c:if>
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr>
	                <th title="첨부파일"><spring:message code="text.attach-file"/></th>
	                <td>
	                    <div class="input-box-wrap file">
	                        <input type="file" name="file" id="file" value="">
	                    </div>
	                    <ul class="att fileList"></ul>
	                </td>
	            </tr>
	            <tr>
	                <th title="참석자"><spring:message code="text.attendance"/></th>
	                <td>
	                    <input type="text" class="input-box-wrap user-input" name="attend">
	                </td>
	            </tr>
	            <tr class="tr-divide add-th-bg row-index-0">
	                <th title="프로젝트"><spring:message code="text.project"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="${contextPath}/detail/selectPrj?index=0" data-transition="slide">
	                            <span class="projectName"></span>
	                            <span class="btn-icon-right"></span>
	                            <input type="hidden" name="projectName">
	                            <input type="hidden" name="projectNumber">
	                            <input type="hidden" name="projectType">
	                            <input type="hidden" name="projectStatusCode">
	                            <input type="hidden" name="projectStatusCodeNm">
	                            <input type="hidden" name="projectId">
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th>Task</th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="#" class="goTask" data-index="0" data-transition="slide">
	                            <span class="taskName task"></span>
	                            <span class="btn-icon-right"></span>
	                            <input type="hidden" name="taskId" class="task">
	                            <input type="hidden" name="taskName" class="task">
	                            <input type="hidden" name="taskNumber" class="task">
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="수행조직"><spring:message code="text.org"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="#" class="goOrg" data-index="0" data-transition="slide">
	                            <span class="expOrgName org"></span>
	                            <span class="btn-icon-right"></span>
	                            <input type="hidden" name="expOrgId" class="org">
	                            <input type="hidden" name="expOrgName" class="org">
	                            <input type="hidden" name="segment2" class="org">
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="원가유형"><spring:message code="text.type.amt"/></th>
	                <td>
	                    <div class="input-box-wrap">
	                        <a href="#" class="goType" data-index="0" data-transition="slide">
	                            <span class="expType type"></span>
	                            <span class="btn-icon-right"></span>
	                            <input type="hidden" name="expType" class="type">
	                            <input type="hidden" name="mainAccCode" class="type">
	                            <input type="hidden" name="subAccCode" class="type">
	                            <input type="hidden" name="attributeCategory" class="type">
	                        </a>
	                    </div>
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="적요"><spring:message code="text.description"/></th>
	                <td>
	                	<input type="text" class="input-box-wrap user-input" name="description">
	                </td>
	            </tr>
	            <tr class="add-th-bg row-index-0">
	                <th title="공급가"><spring:message code="text.price.supply"/></th>
	                <td>
	                    <div class="input-box-wrap user-input">
	                        <span class="input-box-txt">
	                        	<input type="text" class="currency" name="appramt" value="<fmt:formatNumber value='${card.appramt}' pattern='#,###'/>"><spring:message code="common.unit.currency"/>
	                        </span>
	                    </div>
	                </td>
	            </tr>
			</tbody>
	    </table>
    </form>
    <!--/프로젝트비용 선택시 활성테이블-->
    <script type="text/x-tmpl" id="tmpl-prjTbl">
		<tr class="tr-divide add-th-bg row-index-{%=o.index%}">
            <th title="프로젝트"><spring:message code="text.project"/></th>
            <td>
                <div class="input-box-wrap">
                    <a href="${contextPath}/detail/selectPrj?index={%=o.index%}" data-transition="slide">
                        <span class="projectName"></span>
                        <span class="btn-icon-right"></span>
						<input type="hidden" name="projectName">
                        <input type="hidden" name="projectNumber">
                        <input type="hidden" name="projectType">
                        <input type="hidden" name="projectStatusCode">
                        <input type="hidden" name="projectStatusCodeNm">
                        <input type="hidden" name="projectId">
                    </a>
                </div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th>Task</th>
            <td>
                <div class="input-box-wrap">
                    <a href="#" class="goTask" data-index="{%=o.index%}" data-transition="slide">
                        <span class="taskName task"></span>
                        <span class="btn-icon-right"></span>
						<input type="hidden" name="taskId" class="task">
						<input type="hidden" name="taskName" class="task">
                        <input type="hidden" name="taskNumber" class="task">
                    </a>
                </div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="수행조직"><spring:message code="text.org"/></th>
            <td>
                <div class="input-box-wrap">
					<a href="#" class="goOrg" data-index="{%=o.index%}" data-transition="slide">
                        <span class="expOrgName org"></span>
                        <span class="btn-icon-right"></span>
                        <input type="hidden" name="expOrgId" class="org">
						<input type="hidden" name="expOrgName" class="org">
                        <input type="hidden" name="segment2" class="org">
                    </a>
                </div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="원가유형"><spring:message code="text.type.amt"/></th>
            <td>
                <div class="input-box-wrap">
					<a href="#" class="goType" data-index="{%=o.index%}" data-transition="slide">
                        <span class="expType type"></span>
                        <span class="btn-icon-right"></span>
						<input type="hidden" name="expType" class="type">
                        <input type="hidden" name="mainAccCode" class="type">
                        <input type="hidden" name="subAccCode" class="type">
						<input type="hidden" name="attributeCategory" class="type">
                    </a>
                </div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="적요"><spring:message code="text.description"/></th>
            <td>
				<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
					<input type="text" class="input-box-wrap user-input" name="description">
				</div>
            </td>
        </tr>
        <tr class="add-th-bg row-index-{%=o.index%}">
            <th title="공급가"><spring:message code="text.price.supply"/></th>
            <td>
                <div class="input-box-wrap user-input">
                    <span class="input-box-txt">
					<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
						<input type="text" class="currency" name="appramt" value="0">
					</div>
					<spring:message code="common.unit.currency"/></span>
                </div>
            </td>
        </tr>
	</script>

    <!--footer-->
    <footer class="btn-footer container page-footer" data-role="footer">
        <a href="#" id="btnAdd" data-transition="slide" class="col-xs-2 button-etc wbtn btn-icon-plus" title="추가"><spring:message code="common.add"/></a>
        <a href="#" id="btnDel" data-transition="slide" class="col-xs-2 button-etc wbtn btn-icon-plus" title="삭제"><spring:message code="common.delete"/></a>
        <a href="#popConfirm" id="btnDtlAppr" data-position-to="window" data-rel="popup" data-transition="slideup" class="col-xs-4 button-recognize wbtn float-right" title="승인요청"><spring:message code="text.req.apprv"/></a>
    </footer>
    <!-- /footer -->
    <!-- datepicker -->
    <div id="datepicker1"></div>
    <!-- /datepicker -->
	<!-- 승인요청 팝업-->
    <div data-role="popup" id="popConfirm" class="ui-content" data-theme="a" data-dismissible="false">
        <main>
            <p title="승인요청 하시겠습니까?"><spring:message code="msg.apprv.confirm"/></p>
        </main>
        <footer>
            <a href="#" id="btnApprv" class="btn-basic button-recognize btn-colgroup-2" data-rel="back"><spring:message code="common.ok"/></a>
            <a href="#" class="btn-basic button-cancel btn-colgroup-2" data-rel="back"><spring:message code="common.cancel"/></a>
        </footer>
    </div>
    <!--/승인요청 팝업-->
    <!-- 파일삭제 팝업-->
    <div data-role="popup" id="popDelete" class="ui-content" data-theme="a" data-dismissible="false">
        <main>
            <p title="해당 파일을 삭제하시겠습니까?"><spring:message code="msg.delete.confirm"/></p>
            <input type="hidden" id="delFileId" />
        </main>
        <footer>
            <a href="#" id="btnDelete" class="btn-basic button-recognize btn-colgroup-2" data-rel="back"><spring:message code="common.ok"/></a>
            <a href="#" class="btn-basic button-cancel btn-colgroup-2" data-rel="back"><spring:message code="common.cancel"/></a>
        </footer>
    </div>
    <!--/파일삭제 팝업-->
    <!-- 이미지뷰 팝업-->
    <div data-role="popup" id="popupPhotoPortrait" class="photopopup" data-overlay-theme="a" data-corners="false" data-tolerance="30,15">
	    <a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a><img src="/" alt="">
	</div>
	<!--/이미지뷰 팝업-->
</div>
<!--/page-->
<!-- alert 팝업 부서,계정등 다른 페이지에서 보일수 있게 page랑 분리-->
<div data-role="popup" id="popAlert" class="ui-content" data-theme="a" data-dismissible="false">
    <main>
        <p id="alertMsg"></p>
    </main>
    <footer>
        <a href="#" class="btn-basic button-recognize btn-colgroup-2 width100" data-rel="back"><spring:message code="common.ok"/></a>
    </footer>
</div>
<!--/alert 팝업-->
<!-- Script logic -->
<script src="${resources}/js/card/detail/detail.js"></script>
<script src="${resources}/js/card/detail/detailInit.js"></script>
<script src="${resources}/js/card/detail/detailValid.js"></script>
<script src="${resources}/js/card/detail/detailEvent.js"></script>
<script src="${resources}/js/common/slideEvent.js"></script>
<script type="text/javascript">
	messages.add({
		add 			: '<spring:message code="common.add"/>',
		currency 		: '<spring:message code="common.unit.currency"/>',
		selectProject 	: '<spring:message code="msg.alert.select.project"/>',
		selectApprv 	: '<spring:message code="msg.alert.select.apprv"/>',
		selectAccnt 	: '<spring:message code="msg.alert.select.accnt"/>',
		selectDept 		: '<spring:message code="msg.alert.select.dept"/>',
		selectTask 		: '<spring:message code="msg.alert.select.task"/>',
		selectType 		: '<spring:message code="msg.alert.select.type"/>',
		manEtc 			: '<spring:message code="common.man.etc"/>',
		manCnt 			: '<spring:message code="common.man.cnt"/>',
		validSupply 	: '<spring:message code="msg.valid.supply"/>',
		inputCate 		: '<spring:message code="msg.alert.cate.input"/>',
		validapprtot 	: '<spring:message code="msg.valid.apprtot"/>',
		apprvOk 		: '<spring:message code="msg.apprv.ok"/>',
		validApproveAmt	: '<spring:message code="msg.valid.approve.amt"/>',
		validImage		: '<spring:message code="msg.valid.image"/>',
		errorUpload		: '<spring:message code="msg.error.file-upload"/>'
	});
	detail.init({
		contextPath : "${contextPath}"
	});
</script>
</body>
</html>
