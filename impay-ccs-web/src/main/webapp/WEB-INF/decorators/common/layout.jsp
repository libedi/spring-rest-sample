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
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"            %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"             %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"       %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"          %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form"     %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page"      uri="http://www.opensymphony.com/sitemesh/page"     %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />
    <title><spring:message code="common.title"/> &gt; <decorator:title /></title>    
    <%@ include file="/WEB-INF/jsp/common/include.jsp"%>
    <script type="text/javascript">
        var contextPath = "${contextPath}";
        // Gnb
        $(function() {
            // Lnb
            var lastEvent = null;
            var slide  = "#lnb-menu > li > ul";
            var alink  = "#lnb-menu > li > a";
            
            // 현재 선택된 메뉴에 활성화 표시
            var mnuId = "${mnuId}";
            var uprMnuId = "${uprMnuId}"; 
            var selectUprMnuId = '';
            var selectMnuId = '';

            $('.submenu-wrap').each(function(){
    			if($(this).css('display') === 'block'){
    				selectMnuId = $(this).attr('id');
    				selectUprMnuId = $(this).siblings('a').attr('id');
    			}
    		})
            $(".gmenu").mouseenter(function() {
    			$('.submenu-wrap').hide();
    			$(this).next().show();
    		});
            
    		$(".header").mouseleave(function() {
    			$('.submenu-wrap').hide();
    			$('#'+selectMnuId).show();
    			$('.gnb-wrap .gmenu').removeClass('on');
    			$('#'+selectUprMnuId).addClass('on');
    		});
    		
            function accordion(){
                if (this == lastEvent) return false;
                lastEvent = this;
                setTimeout(function() {
                    lastEvent = null
                }, 200);
                
                
                if ($(this).attr('class') != 'on') {
                    $(slide).slideUp();
                    $(this).next(slide).slideDown();
                    $(alink).removeClass('on');
                    $(this).addClass('on');
                } else if ($(this).next(slide).is(':hidden')) {
                    $(slide).slideUp();
                    $(this).next(slide).slideDown();
                } else {
                    $(this).next(slide).slideUp();
                }
            }
            
            $(alink).click(accordion).focus(accordion);
            $('#lnb-menu > li:last > a').addClass('stay');
            
            $('[id^="menu"]').mouseover(function() {
                //$('[id^="sub-menu"]').slideUp(100);
                //$('#sub-'+$(this).attr('id')).slideDown(100);
                $('.gnb-wrap .gmenu').removeClass('on');
                $(this).addClass('on');
            });
            
            
            $('a[rel*="menu"]').each(function (index, element) {
                $(element).click(function (e) {
                    e.preventDefault();
                 	// 1depth 메뉴 여부 에따라 url 설정
                    var action = $(element).data('url');
                    if($(element).hasClass("gmenu")) { // 1depth 메뉴
                    	action = $(element).next().find('a').data('url');
                    } 
                    
                    var leaf = $(element).data('leaf');
                    leaf = (typeof leaf == 'undefined' || leaf == '') ? 0 : leaf;
                    if (leaf > 0) return;
                    if (typeof action != 'undefined' && action != '') {
                        $('<form/>', {method:'post', action: action})
                        .append($('<input/>', {type: 'hidden', name: 'mnuId', value: $(element).data('mnuid')}))
                        .append($('<input/>', {type: 'hidden', name: 'uprMnuId', value: $(element).data('uprmnuid')}))
                        .appendTo(document.body)
                        .submit();
                    }
                });
            });
            
            $("#button-timeout-ok").click(function(){
            	$("#timeoutlayer").hide();
            });
        });
        function logout() {
            Common.logout(contextPath); 
        }
    </script>
    <decorator:head />
</head>
<body>
<sec:authentication var="user" property="principal" />
<div class="wrap">
    <ul class="skip-navi">
        <li><a href="#contents"><spring:message code="common.navi.contents"/></a></li>
        <li><a href="#gnb"><spring:message code="common.navi.gnb"/></a></li>
        <li><a href="#lnb"><spring:message code="common.navi.lnb"/></a></li>  
    </ul>

    <!-- header -->
    <div class="header">
        <h1>
            <c:choose>
        		<c:when test='${contextPath eq ""}'>
        			<a href="/"><img src="<c:url value="/resources/images/bi.png" />" alt="IMPay BOS"></a>
        		</c:when>
        		<c:otherwise>
        			<a href="${contextPath}"><img src="<c:url value="/resources/images/bi.png" />" alt="IMPay BOS"></a>
        		</c:otherwise>
        	</c:choose>
        </h1>
        <div class="header-wrap">
            <div class="top-info">
                <span><spring:message code="common.top.account"/> : ${user.hdnUsername}</span>
                <span>(IP : ${user.hdnIp}) <strong id="timer" class="poin_org"></strong></span> <button class="l-btn" onclick="logout()"><spring:message code="common.top.logout"/></button>
            </div>
            <div id="gnb" class="gnb">
                <c:if test="${not empty menu1st}">
                    <ul class="gnb-wrap">
                        <c:forEach var="menu1st" items="${menu1st}" varStatus="status">
                            <c:set var="index" value="${index + 1}" />
                            <li>
                                <a href="#" rel="menu" data-url="<c:url value="${menu1st.mnuUrl}"/>" id="menu0<c:out value="${index}" />" 
                                	class="gmenu <c:if test="${menu1st.mnuId eq mnuId }">on</c:if>">
                                    <c:out value="${menu1st.mnuNm}" />
                                </a>
                                <div id="sub-menu0<c:out value="${index}" />" class="submenu-wrap" <c:if test="${menu1st.mnuId eq mnuId }">style="display:block;"</c:if>>
                                    <ul>
                                        <c:forEach var="menu2nd" items="${menu2nd}">
                                            <c:if test="${menu1st.mnuId eq menu2nd.uprMnuId}">
                                                <li><a href="#" rel="menu" class="<c:if test="${menu2nd.mnuId eq uprMnuId }">on</c:if>" data-mnuid="<c:out value="${menu1st.mnuId}" />" data-uprmnuid="<c:out value="${menu2nd.mnuId}" />" data-url="<c:url value="${menu2nd.mnuUrl}" />"><c:out value="${menu2nd.mnuNm}" /></a></li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </div>
    <!--// header -->
    <div class="container">
    	<c:choose>
	        <c:when test="${not empty mnuId and not empty uprMnuId}">
		        <div class="location"><c:out value="${menuPath}" /></div>
		        <!-- lnb -->
		        <div id="lnb" class="lnb">
		            <div class="lnb-wrap">
		                <h2>
		                   <c:forEach var="menu1st" items="${menu1st}">
		                       <c:if test="${mnuId eq menu1st.mnuId}">
		                           <c:out value="${menu1st.mnuNm}" />
		                       </c:if>
		                   </c:forEach>
		                </h2>
		                <ul id="lnb-menu" class="lnb-menu">
	                    <c:forEach var="menu2nd" items="${menu2nd}">
	                        <c:if test="${mnuId eq menu2nd.uprMnuId}">
                                <li <c:if test="${menu2nd.leaf gt 0}">class="depth"</c:if>>
                                
                                	<c:set var="hasChild2ndMenu" value="false"/>
                                	<c:forEach var="menu3rd" items="${menu3rd}" varStatus="status">
                                		<c:if test="${menu2nd.mnuId eq menu3rd.uprMnuId}">
                                			<c:if test="${menu3rd.uprMnuId eq uprMnuId or menu3rd.mnuId eq uprMnuId }">
	                                    		<c:set var="hasChild2ndMenu" value="true"/>
	                                    		<c:set var="uprMnuId" value="${menu3rd.mnuId }"/>
	                                    		<c:set var="menu3rdNm" value="${menu3rd.mnuNm }"/>
	                                    	</c:if>
                                    	</c:if>
                                    </c:forEach>

	                                <a href="#" rel="menu" data-mnuid="<c:out value="${mnuId}" />" 
	                                	data-uprmnuid="<c:out value="${menu2nd.mnuId}" />" 
	                                	data-leaf="${menu2nd.leaf}" 
	                                	data-url="<c:url value="${menu2nd.mnuUrl}" />"
	                                	<c:if test="${menu2nd.mnuId eq uprMnuId or hasChild2ndMenu}">class="on"</c:if>>
	                                	<c:out value="${menu2nd.mnuNm}" /></a>
	                                	
	                                <c:if test="${menu2nd.leaf gt 0}">
	                                	<c:choose>
	                                		<c:when test="${hasChild2ndMenu eq true}">
	                                			<ul style="display:block">
	                                		</c:when>
                                			<c:otherwise>
                                				<ul style="display:none">
                                			</c:otherwise>
	                                	</c:choose>
	                                </c:if>
	                                <c:set var="hasChild2ndMenu" value="false"/>
	                                
    	                                <c:forEach var="menu3rd" items="${menu3rd}" varStatus="status">
	                                       <c:if test="${menu2nd.mnuId eq menu3rd.uprMnuId}">
	                                            <li><a href="#" rel="menu" 
	                                            		data-mnuid="<c:out value="${mnuId}" />" 
	                                            		data-uprmnuid="<c:out value="${menu3rd.mnuId}" />" 
	                                            		data-url="<c:url value="${menu3rd.mnuUrl}" />"
	                                            		<c:if test="${menu3rd.mnuId eq uprMnuId }">class="on"</c:if>>
	                                            	<c:out value="${menu3rd.mnuNm}" /></a>
	                                            </li>
	                                        </c:if>
	                                    </c:forEach>
	                                <c:if test="${menu2nd.leaf gt 0}"><c:out value="</ul>" escapeXml="false" /></c:if>
	                                
	                            </li>
	                        </c:if>
	                    </c:forEach>
	                </ul>
		            </div>
		        </div>
	        </c:when>
	        <c:otherwise>
		       
	        </c:otherwise>
        </c:choose>
        <!--// lnb -->        
        <decorator:body />
    </div>    
</div>
<div id="layerholder"></div>
<div id="layertempl">
	<div class='fixed-layer'>
		<div class='bg-opacity'></div>
		<div class='ly-wrap'>
			<div class='ly-header'>
				<div class='ly-header-left'></div>
				<h4 class="title"></h4>
				<a href='#' class='close'><span class='hide'>레이어 닫기</span></a>
				<div class='ly-header-right'></div>
			</div>
			<div class='ly-content'></div>
			<div class='ly-footer'>
				<div class='ly-footer-left'></div>
				<div class='ly-footer-center'></div>
				<div class='ly-footer-right'></div>
			</div>
		</div>
	</div>
</div>

<!-- layerwrap -->
<div id="loadingbar" class="fixed-layer ly-preloader" style="display:none">
	<div class="bg-opacity"></div>
	<div id="ly-wrap" class="ly-wrap" style="margin:-200px 0 0 -325px;">
		<div class="loading-wrap">
			<img alt="preloader" src="${resources}/images/preloader.gif" />
		</div>
	</div>	
</div>
<div id="timeoutlayer" class="ly-preloader fixed-layer" style="display:none;">
	<div class="bg-opacity"></div>
	<div id="ly-wrap" class="ly-wrap" style="margin:-200px 0 0 -325px;">
		<div class="loading-wrap">
			<img src="${resources}/images/preloader.gif" alt="preloader">
			<div>
				<strong>
					<em><spring:message code="errorpage.indication.timeout1"/></em><br>
					<spring:message code="errorpage.indication.timeout2"/>
				</strong>
				<br><br>
				<spring:message code="errorpage.indication.timeout3"/>
				
				<ul>
					<li><spring:message code="errorpage.indication.timeout4"/> <em><spring:message code="errorpage.indication.timeout5"/></em></li>
					<li><spring:message code="errorpage.indication.timeout6"/> <em>1234-1234</em></li>
				</ul>
				<button id="button-timeout-ok" type="button" class="l-btn close"><spring:message code="common.button.ok"/></button>
			</div>
		</div>		
	</div>	
</div>	
<!--// layerwrap -->
<script type="text/javascript">
//2depth메뉴를 클릭할 경우 3depth location을 만들어준다
if($(".location") !== null && $(".location").html() !== "" && $(".location").html() !== undefined){        
	var level = $(".location").html().match(/&gt;/g).length;
	if("${menu3rdNm}" !== "" && level === 2){
		$(".location").html($(".location").html() + " > ${menu3rdNm}");
	}
}
</script>
</body>
</html>