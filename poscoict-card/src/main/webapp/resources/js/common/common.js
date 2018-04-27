// form 객체를 JSON Object로 변환 : $("form").serializeObject();
$.fn.serializeObject = function(){
	"use strict";
	
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

// jQuery 공통 초기화
$(function(){
	"use strict";
	
	// REST API 를 사용하기 위한 ajax 필터
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		if(options.type === "put" || options.type === "patch" || options.type === "delete") {
			var headerValue = options.type;
			jqXHR.setRequestHeader("X-HTTP-Method-Override", headerValue);
			options.type = "post";
		}
	});
});

// 공통 함수
var common = (function(module, $){
	"use strict";
	
	var root = module;
	var urls = {
			healthCheck : "/healthCheck"
	};
	var contextPath = "";
	var resources = "";
	
	module.init = function(options) {
		contextPath = options.contextPath;
		resources = options.resources;
	};
	
	module.getContextPath = function() {
		return contextPath;
	};
	
	module.getResources = function() {
		return resources;
	};
	
	module.formatCurrency = function(num) {
		num = num.replace(/\,/g, "");
		if(!$.isNumeric(num)) {
			return "0";
		}
		var regexp = /(^0+)(\d+)/g;
		num = regexp.test(num) ? RegExp.$2 : num;
		return String(num).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	};
	
	module.isNull = function(s) {
		return !Boolean(s) || s === "null" || s === "undefined";
	};
	
	module.nullToSpace = function(s) {
		if(root.isNull(s)) {
			return "";
		}
		return s;
	};
	
	// 업무서버 헬스체크 여부
	module.healthCheck = function(rootUrl, subUrl) {
		if(rootUrl !== "#") {
			rest.get({
				url : contextPath + urls.healthCheck,
				data : { url : rootUrl },
				success	: function(result) {
					var $frm = $("#linkForm");
					if(!root.isNull(subUrl)) {
						$("#targetUrl").val(subUrl);
					}
					$frm.attr("action", rootUrl + "/login");
					$frm.submit();
				},
				error : function(xhr, status, err) {
					location.href = contextPath + "/error/500";
				}
			});
		} else {
			location.href = subUrl + "";
		}
	};
	
	return module;
}(window.common || {}, $));