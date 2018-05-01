var impay = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var defaults = {	contextPath: '', 
						method: 'post', 
						timeout: 1000*60,
						requestUri: '', 
						dataType: 'json',
						contentType: 'application/x-www-form-urlencoded; charset=utf-8', 
						data: '', 
						successCallback: '',
						errorCallback: ''};
	var options = defaults || {};

	/**
	 * public 
	 */
	
	module.sendPost = function(o) {
		//console.log(o);
		var showLoadingBar = (o.showLoadingBar === undefined || o.showLoadingBar === null) ? true : o.showLoadingBar;
		
		$.ajax({
	        type: 'post',
	        url: o.requestUri,
	        timeout: (o.timeout === undefined || o.timeout === null) ? defaults.timeout : o.timeout,
	        data: (o.data === undefined || o.data === null) ? defaults.data : o.data,
	        async: (o.async === undefined || o.async === null) ? defaults.async : o.async,
	        dataType: (o.dataType === undefined || o.dataType === null) ? defaults.dataType : o.dataType,
	        contentType: options.contentType,
	        success: function(result) {
	        	if(o.successCallback !== 'undefined' && typeof o.successCallback === 'function') {
	        		o.successCallback(result);
	    		}
	        },
	        error: function(xhr, status, err) {
	        	$("button").prop("disabled", false);
	        	if(o.errorCallback !== undefined && typeof o.errorCallback === 'function') {
	        		o.errorCallback(err);
	    		}
	        	if (showLoadingBar) {
		        	$("#loadingbar").hide();
			        $('body').removeClass('stop-scrolling');
		        }
	        },
	        beforeSend: function() {
	        	$("button").prop("disabled", true);
	        	if (showLoadingBar) {
		        	$("#loadingbar").show();
			        $('body').addClass('stop-scrolling');
	        	}
			},
			complete:function(){
				$("button").prop("disabled", false);
				if (showLoadingBar) {
					$("#loadingbar").hide();
			        $('body').removeClass('stop-scrolling');
				}
			}
		});
	};
	module.sendGet = function(o) {
		//console.log(o);
		var showLoadingBar = (o.showLoadingBar === undefined || o.showLoadingBar === null) ? true : o.showLoadingBar;
		
		$.ajax({
	        type: 'get',
	        url: o.requestUri,
	        timeout: (o.timeout === undefined || o.timeout === null) ? defaults.timeout : o.timeout,
	        data: (o.data === undefined || o.data === null) ? defaults.data : o.data,
	        async: (o.async === undefined || o.async === null) ? defaults.async : o.async,
	        success: function(result) {
	        	if(o.successCallback !== undefined && typeof o.successCallback === 'function') {
	        		o.successCallback(result);
	    		}
	        },
	        error: function(xhr, status, err) {
	        	$("button").prop("disabled", false);
	        	if(o.errorCallback !== undefined && typeof o.errorCallback === 'function') {
	        		o.errorCallback(err);
	    		}
	        	if (showLoadingBar) {
		        	$("#loadingbar").hide();
			        $('body').removeClass('stop-scrolling');
		        }
	        },
	        beforeSend: function() {
	        	$("button").prop("disabled", true);
	        	if (showLoadingBar) {
		        	$("#loadingbar").show();
			        $('body').addClass('stop-scrolling');
	        	}
			},
			complete:function(){
				$("button").prop("disabled", false);
				if (showLoadingBar) {
					$("#loadingbar").hide();
			        $('body').removeClass('stop-scrolling');
				}
			}
		});
	};
	module.sendPostJson = function(o) {
		$.ajax({
	        type: 'post',
	        url: o.requestUri,
	        timeout: (o.timeout === undefined || o.timeout === null) ? defaults.timeout : o.timeout,
	        data: o.data,
	        dataType: o.dataType,
	        contentType: 'application/json; charset=utf-8',
	        success: function(result) {
	        	if(o.successCallback !== undefined && typeof o.successCallback === 'function') {
	        		o.successCallback(result);
	    		}
	        },
	        error: function(data, status, err) {
	        	if(o.errorCallback !== undefined && typeof o.errorCallback === 'function') {
	        		o.errorCallback(err);
	    		}
	        }
		});
	};
	module.getOptions = function(){
		return root.options;
	};
	
	return module;	
}(window.impay || {}, $));


$(document).ajaxError(function(event, xhr, settings, thrownError) {
	if (xhr.status === 401) {
		alert("인증에 실패 했습니다. 로그인 페이지로 이동합니다.");
		location.href = contextPath + "/login/form";
	} else if (xhr.status === 403) {
		alert("세션이 만료되었습니다. 로그인 페이지로 이동합니다.");
		location.href = contextPath + "/login/form";
	} else if (xhr.status === 404) {
		location.href = contextPath + "/handle/error";
	} else if (xhr.status === 500) {
		alert("에러가 발생했습니다. 관리자에게 문의하세요.");
	} else if (thrownError === "timeout"){
		$("#loadingbar").hide();
		$("#timeoutlayer").show();
	} else {
		alert("[" + xhr.status + "] 에러가 발생하였습니다");
	}
	console.log(thrownError);
});

$(document).ready(function(){
	// IE9 이하에서 console 객체를 인식 못하는 오류때문에 선언
	if(!window.console) window.console = {};
	if(!window.console.log) window.console.log = function (){};
	
	// 화폐
	$("body").on("keyup", "input.money", function() {
		var val = $(this).val().replace(/,/g, "");
		var ptArr = val.match(new RegExp(/\./gi));
		if(ptArr !== null && ptArr.length > 1){
			val = val.substring(0, val.lastIndexOf("."));
		}
	    var ptIdx = val.indexOf(".");
	    var intVal = val;
	    var ptVal = "";
	    if(ptIdx !== -1){
	    	intVal = val.substring(0, ptIdx);
	    	ptVal = val.substring(ptIdx + 1);
	    }

		if(val !== null && val !== undefined){
			if(val.length > 1 && new RegExp(/^0/gi).test(val) && !new RegExp(/^(0\.)/gi).test(val)){
				$(this).val("0");
			} else {
		        intVal = Common.formatMoney(intVal.replace(/[^(0-9|\.)]/gi, ""));
		      	if(ptIdx !== -1){
		          ptVal = "." + ptVal.replace(/[^(0-9|\.)]/gi, "");
		        }
				$(this).val(intVal + ptVal);
			}
		}
	});
	
	// 백분율 : 최대 5자리, 소숫점 2자리까지
	$("body").on("keyup", "input.percent", function() {
		var val = $(this).val();
		var ptArr = val.match(new RegExp(/\./gi));
		if(ptArr !== null && ptArr.length > 1){
			val = val.substring(0, val.lastIndexOf("."));
		}
	    var ptIdx = val.indexOf(".");
	    var intVal = val;
	    var ptVal = "";
	    if(ptIdx !== -1){
	    	intVal = val.substring(0, ptIdx);
	    	ptVal = val.substring(ptIdx + 1);
	    }
	    if(intVal.length > 3){
	    	intVal = intVal.substring(0, 3);
	    }
	    if(ptVal !== "" && ptVal.length > 2){
	    	ptVal = ptVal.substring(0, 2);
	    }
	    
		if(val !== null && val !== undefined){
			if(val.length > 1 && new RegExp(/^0/gi).test(val) && !new RegExp(/^(0\.)/gi).test(val)){
				$(this).val("0");
			} else {
		        intVal = intVal.replace(/[^0-9]/gi, "");
		      	if(ptIdx !== -1){
		          ptVal = "." + ptVal.replace(/[^0-9]/gi, "");
		        }
				$(this).val(intVal + ptVal);
			}
		}
	});
	
	// 숫자
	$("body").on("keyup", "input.digit", function() {
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});
	
	// auto tab
	$("body").on("keyup", "input.autotab", function() {
		if($(this).val().length === parseInt($(this).attr('maxlength'))){
			$(this).nextAll('input').first().focus();
		}
	});
	
	// email
	$("body").on("focusout", "input.email", function() {
		if($(this).val() === '') return;
		if(!utils.isValidEmail($(this).val())){
			alert("이메일 정보가 유효하지 않습니다.");
			$(this).val('').focus();
		}
	});
	
	// url
	$("body").on("focusout", "input.url", function() {
		if($(this).val() === '') return;
		if(!utils.isValidUrl($(this).val())){
			alert("URL 정보가 유효하지 않습니다.");
			$(this).val('').focus();
		}
	});
	
	// ip
	$("body").on("focusout", "input.ip", function() {
		if($(this).val() === '') return;
		if(!utils.isValidIp($(this).val())){
			alert("IP 정보가 유효하지 않습니다.");
			$(this).val('').focus();
		}
	});
	
	// 검색시 코드
	$("body").on("keydown keyup focusout keypress", "#searchCode", function() {
		var str = $(this).val();
		if ( str.match(/[^a-zA-Z0-9]/) !== null ) {
			$(this).val($(this).val().replace(/[^a-zA-Z0-9]/gi, ""));
			return;
		}
	});
	$("body").on("keydown keyup focusout keypress", ".searchCode", function() {
		var str = $(this).val();
		if ( str.match(/[^a-zA-Z0-9]/) !== null ) {
			$(this).val($(this).val().replace(/[^a-zA-Z0-9]/gi, ""));
			return;
		}
	});
	
	//검색조건 초기화
	$(".init-search").on("click", function() {
		$(".search-box").find("input:text:not(.calendar, .timepicker, .ympicker), textarea").val("");
		$(".search-box").find("select").each(function(){
			$(this).find("option:eq(0)").prop("selected", true);
		});
		$(".search-box").find("input:radio").each(function(){
			var $name = $(this).attr("name");
			$("input:radio[name=" + $name + "]").removeAttr("checked");
			$("input:radio[name=" + $name + "]:eq(0)").prop("checked", true);
		});
	});
	
	// IE 9 이하 버전 placeholder 대응
//	$("input, textarea").placeholder();
	
});