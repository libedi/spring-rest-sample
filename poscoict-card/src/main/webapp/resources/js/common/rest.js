var rest = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var defaults = {
			async 			: true,
			timeout 		: 1000 * 60,
			contentType 	: "application/json;charset=UTF-8",
			data 			: {}
	};
	
	/**
	 * public
	 */
	// GET 메서드 호출
	module.get = function(o) {
		$.ajax({
			type 			: "get",
			url 			: o.url,
			timeout 		: o.timeout || defaults.timeout,
			async 			: o.async || defaults.async,
			contentType 	: o.contentType || defaults.contentType,
			data			: o.data || "",
			success	: function(result, status, xhr) {
				if(o.success !== undefined && typeof o.success === "function") {
					o.success(result, status, xhr);
				}
			},
			error : function(xhr, status, err) {
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.error !== undefined && typeof o.error === "function") {
					o.error(xhr, status, err);
				}
			},
			beforeSend : function(xhr){
				$.mobile.loading("show");
				$(".ui-btn").addClass("ui-disabled");
				if(o.beforeSend !== undefined && typeof o.beforeSend === "function") {
					o.beforeSend(xhr);
				}
			},
			complete   : function(xhr, status){
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.complete !== undefined && typeof o.complete === "function") {
					o.complete(xhr,status);
				}
			}
		});
	};
	
	// POST 메서드 호출
	module.post = function(o) {
		$.ajax({
			type 			: "post",
			url 			: o.url,
			timeout 		: o.timeout || defaults.timeout,
			async 			: o.async || defaults.async,
			contentType 	: o.contentType || defaults.contentType,
			data			: JSON.stringify(o.data) || JSON.stringify(defaults.data),
			success	: function(result, status, xhr) {
				if(o.success !== undefined && typeof o.success === "function") {
					o.success(result, status, xhr);
				}
			},
			error : function(xhr, status, err) {
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.error !== undefined && typeof o.error === "function") {
					o.error(xhr, status, err);
				}
			},
			beforeSend : function(xhr){
				$.mobile.loading("show");
				$(".ui-btn").addClass("ui-disabled");
				if(o.beforeSend !== undefined && typeof o.beforeSend === "function") {
					o.beforeSend(xhr);
				}
			},
			complete   : function(xhr, status){
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.complete !== undefined && typeof o.complete === "function") {
					o.complete(xhr,status);
				}
			}
		});
	};
	
	// PUT 메서드 호출
	module.put = function(o) {
		$.ajax({
			type 			: "put",
			url 			: o.url,
			timeout 		: o.timeout || defaults.timeout,
			async 			: o.async || defaults.async,
			contentType 	: o.contentType || defaults.contentType,
			data			: JSON.stringify(o.data) || JSON.stringify(defaults.data),
			success	: function(result, status, xhr) {
				if(o.success !== undefined && typeof o.success === "function") {
					o.success(result, status, xhr);
				}
			},
			error : function(xhr, status, err) {
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.error !== undefined && typeof o.error === "function") {
					o.error(xhr, status, err);
				}
			},
			beforeSend : function(xhr){
				$.mobile.loading("show");
				$(".ui-btn").addClass("ui-disabled");
				if(o.beforeSend !== undefined && typeof o.beforeSend === "function") {
					o.beforeSend(xhr);
				}
			},
			complete   : function(xhr, status){
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.complete !== undefined && typeof o.complete === "function") {
					o.complete(xhr,status);
				}
			}
		});
	};
	
	// DELETE 메서드 호출
	module.del = function(o) {
		$.ajax({
			type 			: "delete",
			url 			: o.url,
			timeout 		: o.timeout || defaults.timeout,
			async 			: o.async || defaults.async,
			contentType 	: o.contentType || defaults.contentType,
			data			: JSON.stringify(o.data) || "",
			success	: function(result, status, xhr) {
				if(o.success !== undefined && typeof o.success === "function") {
					o.success(result, status, xhr);
				}
			},
			error : function(xhr, status, err) {
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.error !== undefined && typeof o.error === "function") {
					o.error(xhr, status, err);
				}
			},
			beforeSend : function(xhr){
				$.mobile.loading("show");
				$(".ui-btn").addClass("ui-disabled");
				if(o.beforeSend !== undefined && typeof o.beforeSend === "function") {
					o.beforeSend(xhr);
				}
			},
			complete   : function(xhr, status){
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.complete !== undefined && typeof o.complete === "function") {
					o.complete(xhr,status);
				}
			}
		});
	};
	
	// 파일 업로드 호출
	module.upload = function(o) {
		$.ajax({
			type 			: "post",
			enctype			: "multipart/form-data",
			processData 	: false,
			url 			: o.url || "/files",
			timeout 		: o.timeout || 600000,
			async 			: o.async || defaults.async,
			contentType 	: false,
			cache			: false,
			data			: o.formData,
			success	: function(result, status, xhr) {
				if(o.success !== undefined && typeof o.success === "function") {
					o.success(result, status, xhr);
				}
			},
			error : function(xhr, status, err) {
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.error !== undefined && typeof o.error === "function") {
					o.error(xhr, status, err);
				}
			},
			beforeSend : function(xhr){
				$.mobile.loading("show");
				$(".ui-btn").addClass("ui-disabled");
				if(o.beforeSend !== undefined && typeof o.beforeSend === "function") {
					o.beforeSend(xhr);
				}
			},
			complete   : function(xhr, status){
				$.mobile.loading("hide");
				$(".ui-btn").removeClass("ui-disabled");
				if(o.complete !== undefined && typeof o.complete === "function") {
					o.complete(xhr,status);
				}
			}
		});
	};
	
	return module;
}(window.rest || {}, $));