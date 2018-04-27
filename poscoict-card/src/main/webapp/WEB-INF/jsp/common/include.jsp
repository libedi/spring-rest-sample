<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" 	value="${pageContext.request.contextPath}" />
<c:set var="resources"		value="${contextPath}/resources" />
 <!-- css -->
<link rel="stylesheet" href="${resources}/css/jquery.mobile.icons.min.css">
<link rel="stylesheet" href="${resources}/css/workplace.min.css">
<!-- js -->
<script src="${resources}/js/libs/jquery-1.11.1.min.js"></script>
<script src="${resources}/js/libs/tmpl.min.js"></script>
<script src="${resources}/js/libs/jquery.mobile-1.4.5.min.js"></script>
<script src="${resources}/js/libs/jquery.p2r.min.js"></script>
<script src="${resources}/js/libs/picker.js"></script>
<script src="${resources}/js/libs/picker.date.js"></script>
<script src="${resources}/js/libs/legacy.js"></script>
<script src="${resources}/js/common/messages.js"></script>
<script src="${resources}/js/common/common.js"></script>
<script src="${resources}/js/common/rest.js"></script>
<script src="${resources}/js/common/slide.js"></script>

<script type="text/javascript">
common.init({
	contextPath : "${contextPath}",
	resources : "${resources}"
});
var contextPath = common.getContextPath();
var resources = common.getResources();

// popup scroll control
$(document).on('popupafteropen', '[data-role="popup"]', function(event, ui) {
    $('body').css('overflow', 'hidden').on('touchmove', function(e) {
        e.preventDefault();
    });
}).on('popupafterclose', '[data-role="popup"]', function(event, ui) {
    $('body').css('overflow', 'auto').off('touchmove');
});
//슬라이드 메뉴 링크 : 메인
$(document).on("tap", "#slideMenu a.mainLink", function(){
	var url = $(this).data("url");
	common.healthCheck(url);
});
// 슬라이드 메뉴 링크 : 서브
$(document).on("tap", "#slideMenu a.subLink", function(){
	var url = $(this).data("url");
	var targetUrl = $(this).data("target");
	common.healthCheck(url, targetUrl);
});
<c:if test="${not empty jwt}">
// jwt 저장
sessionStorage.setItem("jwt", "${jwt}");
</c:if>
</script>