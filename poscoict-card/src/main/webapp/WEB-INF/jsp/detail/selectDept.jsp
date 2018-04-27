<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <%@ include file="/WEB-INF/jsp/common/include.jsp" %>
	<title><spring:message code="title.dept.search"/></title>
</head>

<body>
<div id="selectDept" data-role="page">
    <!--header-->
    <header data-role="header" class="page-header">
        <div class="main-top-btn-section">
            <a href="#" class="btn-icon-back" data-rel="back" data-direction="reverse">back</a>
            <h1 title="부서 검색"><spring:message code="title.dept.search"/></h1>
        </div>
    </header>
    <!--/header-->
    <!--main-->
    <main class="page-main">
        <!--option-section-->
        <header class="option-section">
        	<input type="hidden" name="index" id="index" value="${index}">
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
        <table class="table-style1 table-padding-select list-table">
<!--             <colgroup> -->
<!--                 <col width="40%" /> -->
<!--                 <col width="50%" /> -->
<!--             </colgroup> -->
            <thead>
                <tr>
<!--                     <th>부서코드</th> -->
                    <th title="부서명"><spring:message code="text.dept.name"/></th>
                </tr>
            </thead>
            <tbody id="deptContent"></tbody>
        </table>
        <span class="no-result hidden" title="검색결과가 없습니다."><spring:message code="common.msg.no-content"/></span>
        <!--/검색결과table-->
        <script type="text/x-tmpl" id="tmpl-deptList">
		{% for(var i=0; i<o.length; i++){ %}
			<tr data-cd="{%=o[i].deptCode %}" data-nm="{%=o[i].deptNm %}">
			    <td>{%=o[i].deptNm %}</td>
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
		detail.getDeptList();
	</script>
</div>
<!--/page-->
</body>
</html>