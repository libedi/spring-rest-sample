<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <%@ include file="/WEB-INF/jsp/common/include.jsp" %>
	<title><spring:message code="title.approve.search"/></title>
</head>

<body>
<div id="selectAppr" data-role="page" class="height100">
    <!--header-->
    <header data-role="header" class="page-header">
        <div class="main-top-btn-section">
            <a href="#" class="btn-icon-back" data-rel="back" data-direction="reverse">back</a>
            <h1 title="승인권자 검색"><spring:message code="title.approve.search"/></h1>
        </div>
    </header>
    <!--/header-->
    <!--main-->
    <main class="full-main">
        <!--option-section-->
        <header class="option-section">
            <dl class="input-group-search pop-search">
                <dt class="input-group-tit" style="width:40px;margin-right:10px;" title="검색"><spring:message code="common.search"/></dt>
                <dd style="width: calc(100% - 60px);">
                    <input type="text" class="input-group-box" name="srchTxt" placeholder="<spring:message code='msg.placeholder.apprv'/>">
                    <a href="#" class="btn-icon-search-blue input-group-btn" id="btnSearch"></a>
                </dd>
            </dl>
        </header>
        <!--/option-section-->
    
        <!--검색결과table-->
        <div class="search-result">
        	<h1 class="list-tit" title="검색결과"><spring:message code="common.search.result"/></h1>
	        <span class="no-result hidden" title="검색결과가 없습니다."><spring:message code="common.msg.no-content"/></span>
	        <table id="toggleTbl" class="table-style1 table-padding-basic department-list">
	            <colgroup>
	                <col width="" />
	            	<col width="70px" />
	            </colgroup>
	            <tbody id="apprContent"></tbody>
	        </table>
	        <!--/검색결과table-->
        </div>
        
        <script type="text/x-tmpl" id="tmpl-apprList">
		{% for(var i=0; i<o.length; i++){ %}
			<tr data-id="{%=o[i].empcd %}">
			    <td style="text-align:left">
					<form>
						<span class="list-department">{%=o[i].deptcdDisp %}</span><br/>
						<span class="list-name">{%=o[i].hname %} ({%=o[i].empcd %})</span>
                    	<span class="list-position">{%=o[i].jikwicdDisp %}</span>
						<input type="hidden" name="empcd" value="{%=o[i].empcd %}"/>
						<input type="hidden" name="empId" value="{%=o[i].empId %}"/>
						<input type="hidden" name="jikwicdDisp" value="{%=o[i].jikwicdDisp %}"/>
						<input type="hidden" name="deptcdDisp" value="{%=o[i].deptcdDisp %}"/>
						<input type="hidden" name="hname" value="{%=o[i].hname %}"/>
						<input type="hidden" name="userId" value="{%=o[i].userId %}"/>
						<input type="hidden" name="autoApproveAmt" value="{%=o[i].autoApproveAmt %}"/>
					</form>
				</td>
				<td><a href="#" class="list-add-btn" data-id="{%=o[i].empcd %}" title="추가"><spring:message code="common.add"/></a></td>
			</tr>
		{% } %}
		</script>
		
		<!--선택 table-->
        <div class="selected-list">
            <h1 class="list-tit" title="승인권자"><spring:message code="text.approve.user2"/></h1>
            <table id="toggleTbl" class="table-style1 table-padding-basic department-list">
                <colgroup>
	                <col width="" />
	                <col width="70px" />
                </colgroup>
                <tbody id="apprSelContent"></tbody>
            </table>
        </div>
        <!--/선택 table-->
        <script type="text/x-tmpl" id="tmpl-apprSelList">
		<tr data-id="{%=o.empcd %}">
			<td style="text-align:left">
				<form>
					<span class="list-department">{%=o.deptcdDisp %}</span><br/>
					<span class="list-name">{%=o.hname %} ({%=o.empcd %})</span>
					<span class="list-position">{%=o.jikwicdDisp %}</span>
					<input type="hidden" name="empcd" value="{%=o.empcd %}"/>
					<input type="hidden" name="empId" value="{%=o.empId %}"/>
					<input type="hidden" name="jikwicdDisp" value="{%=o.jikwicdDisp %}"/>
					<input type="hidden" name="deptcdDisp" value="{%=o.deptcdDisp %}"/>
					<input type="hidden" name="hname" value="{%=o.hname %}"/>
					<input type="hidden" name="userId" value="{%=o.userId %}"/>
					<input type="hidden" name="autoApproveAmt" value="{%=o.autoApproveAmt %}"/>
				</form>
			</td>
			<td><a href="#" class="list-del-btn" data-id="{%=o.empcd %}" title="삭제"><spring:message code="common.delete"/></a></td>
		</tr>
		</script>
    </main>
    <!--/main-->
    <!--footer-->
    <footer class="btn-footer container page-footer" data-role="footer">
        <a href="#" id="btnOk" class="col-xs-6 button-recognize wbtn" data-rel="back" title="확인"><spring:message code="common.ok"/></a>
        <a href="#" class="col-xs-6 button-cancel wbtn" data-rel="back" title="취소"><spring:message code="common.cancel"/></a>
    </footer>
    <!-- /footer -->
    <!-- alert 팝업 -->
    <div data-role="popup" id="popAlertAppr" class="ui-content" data-theme="a" data-dismissible="false">
        <main>
            <p id="alertMsgAppr"></p>
        </main>
        <footer>
            <a href="#" class="btn-basic button-recognize btn-colgroup-2 width100" data-rel="back"><spring:message code="common.ok"/></a>
        </footer>
    </div>
    <!--/alert 팝업-->
</div>
<!--/page-->
</body>
</html>