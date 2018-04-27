<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <%@ include file="/WEB-INF/jsp/common/include.jsp" %>
	<title><spring:message code="title.org.search"/></title>
</head>

<body>
<div id="selectOrg" data-role="page">
    <!--header-->
    <header data-role="header" class="page-header">
        <div class="main-top-btn-section">
            <a href="#" class="btn-icon-back" data-rel="back" data-direction="reverse">back</a>
            <h1 title="수행조직 검색"><spring:message code="title.org.search"/></h1>
        </div>
    </header>
    <!--/header-->

    <!--main-->
    <main class="page-main">
        <!--option-section-->
        <header class="option-section">
        	<input type="hidden" name="index" id="index" value="${index}">
        	<input type="hidden" name="projectId" id="projectId" value="${projectId}">
            <dl class="input-group-search pop-search">
                <dt class="input-group-tit" style="width:40px;margin-right:10px;" title="검색"><spring:message code="common.search"/></dt>
                <dd style="width: calc(100% - 60px);">
                    <input type="text" class="input-group-box" name="srchTxt" placeholder="<spring:message code='msg.placeholder.dept'/>">
                    <a href="#" class="btn-icon-search-blue input-group-btn" id="btnSearch"></a>
                </dd>
            </dl>
        </header>
        <!--/option-section-->
        <!--검색결과table-->
        <table id="toggleTbl" class="table-style1 table-padding-select list-table">
            <thead>
                <tr>
                    <th title="부서명"><spring:message code="text.dept.name"/></th>
                </tr>
            </thead>
            <tbody id="orgContent"></tbody>
        </table>
        <span class="no-result hidden" title="검색결과가 없습니다."><spring:message code="common.msg.no-content"/></span>
        <!--/검색결과table-->
        <script type="text/x-tmpl" id="tmpl-orgList">
		{% for(var i=0; i<o.length; i++){ %}
			<tr>
			    <td>
					{%=o[i].expOrgName %}
					<input type="hidden" name="expOrgName" value="{%=o[i].expOrgName %}"/>
					<input type="hidden" name="expOrgId" value="{%=o[i].expOrgId %}"/>
					<input type="hidden" name="segment2" value="{%=o[i].segment2 %}"/>
				</td>
			</tr>
		{% } %}
		</script>
    </main>
    <!--/main-->
    <!--footer-->
    <footer class="btn-footer container page-footer" data-role="footer">
        <a href="#" id="btnOk" class="col-xs-6 button-recognize wbtn" data-rel="back" title="확인"><spring:message code="common.ok"/></a>
        <a href="#" class="col-xs-6 button-cancel wbtn" data-rel="back" title="취소"><spring:message code="common.cancel"/></a>
    </footer>
    <!-- /footer -->
	<script>
		detail.getOrgList();
	</script>
</div>
<!--/page-->
</body>
</html>