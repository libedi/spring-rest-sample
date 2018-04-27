var messages = (function(module, $){
	"use strict";
	
	/*
	 * private
	 */
	var root = module;
	var messages = {};
	
	/*
	 * public
	 */
	// 메시지 추가
	module.add = function(obj) {
		if($.isPlainObject(obj)) {
			$.extend(messages, obj);
		}
	};
	
	// 메시지 조회
	module.get = function(id) {
		return root.contains(id) ? messages[id] : "";
	};
	
	// 메시지 코드 포함여부
	module.contains = function(id) {
		return id in messages;
	};
	
	return module;
}(window.messages || {}, $));