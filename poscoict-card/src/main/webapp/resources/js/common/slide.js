var slide = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var urls = {
			getBadgeCnts  : "/badges"
	};
	var messages = {};		// Message
	var forms = {};			// form ID
	var context = "";		// jQuery selector context
	var contextPath = "";	// contextPath
	
	/**
	 * public
	 */
	// 초기
	module.init = function(options) {
		messages = options.messages || {};
		forms = options.forms || {};
		context = options.context || "";
		contextPath = options.contextPath || "";
	};
	
	// 사이드메뉴 전체 뱃지건수 조회
	module.getBadgeCnts = function(id) {
		rest.get({
			url : contextPath + urls.getBadgeCnts,
			success : function(result) {
				if(result) {
					$(".badge").addClass("hidden");
					if(result.erpCnt > 0){
						$("#erpCnt").removeClass("hidden");
						$("#erpCnt").html(result.erpCnt);
					}
					if(result.cardCnt > 0){
						$("#cardCnt").removeClass("hidden");
						$("#cardCnt").html(result.cardCnt);
					}
					if(result.pomCnt > 0){
						$("#pomCnt").removeClass("hidden");
						$("#pomCnt").html(result.pomCnt);
					}
					if(result.secCnt > 0){
						$("#secCnt").removeClass("hidden");
						$("#secCnt").html(result.secCnt);
					}
					if(result.etcCnt > 0){
						$("#etcCnt").removeClass("hidden");
						$("#etcCnt").html(result.etcCnt);
					}
				}
			}
		});
	};
	
	return module;
}(window.slide || {}, $));