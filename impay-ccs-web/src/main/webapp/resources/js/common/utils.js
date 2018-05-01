var utils = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var regTel = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/;
	var regUrl = /^(http\:\/\/)?((\w+)[.])+(asia|biz|cc|cn|com|de|eu|in|info|jobs|jp|kr|mobi|mx|name|net|nz|org|travel|tv|tw|uk|us)(\/(\w*))*$/i;
	var regEmail = /^[A-Za-z0-9.\-\_]+@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	var regIp = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	var regAcc = /^(\d{5})\d{1,5}(\d{1,})*/g;	// 계좌번호 정규식
	
	/**
	 * public 
	 */
	
	/**
	 * 전화번호 문자열을 받아 하이픈(-)을 넣은 문자열로 포맷
	 * 0230592345 -> 02-3059-2345
	 * 15775555 -> 1577-5555
	 */
	module.formatTel = function(tel) {
		var result = '';
		if(tel.length === 8){
			result = tel.substr(0,4) + '-' + tel.substr(5,4);
		} else {
			result = tel.replace(regTel,"$1-$2-$3");
		}
		return result;
	};
	
	/**
	 * 전화번호 문자열을 잘라 배열로 리턴
	 * 
	 */
	module.telToArray = function(tel) {
		if(tel === "" || tel === undefined || tel === null || tel === "null"){
			return "";
		}
		//return tel.match(regTel).splice(1,3);
		var result = tel.match(regTel);
		if(result === null || result === undefined){
			return "";
		}
		var telArr = [];
		for(var i = 1; i < result.length; i++){
			telArr.push(result[i]);
		}
		return telArr;
	};
	
	module.bizrRegNoToArray = function(bizrRegNo) {
		if(bizrRegNo === "" || bizrRegNo === undefined){
			return "";
		}
		
		var bizrRegNoArr = [];
		bizrRegNoArr.push(bizrRegNo.substr(0,3));
		bizrRegNoArr.push(bizrRegNo.substr(3,2));
		bizrRegNoArr.push(bizrRegNo.substr(5,5));
		
		return bizrRegNoArr;
	};
	
	/**
	 * 전화번호 중간번호 암호화처리
	 */
	module.hiddenToTel = function(tel) {
		var hiddenTel = "";
		var telArr = root.telToArray(tel);
		var astecriks = "";
		
		if(telArr !== ""){
			if(telArr[1].length === 3){
				astecriks = "***";
			} else {
				astecriks = "****";
			}
			hiddenTel = telArr[0] + astecriks + telArr[2];
		}
		return hiddenTel;
	};
	
	// 계좌번호 마스킹 처리 
	module.maskingAccount = function(acc){
		var masking = acc;
		if(Common.getIsNull(acc) === false){
			masking = String(acc).replace(regAcc, "$1*****$2");
		} 
		return masking;
	};
	
	module.getRequest = function(valuename){
	    var rtnval;
	    var nowAddress = unescape(location.href);
	    var parameters = [];
	    parameters = (nowAddress.slice(nowAddress.indexOf("?")+1,nowAddress.length)).split("&");
	    for(var i = 0 ; i < parameters.length ; i++){
	        if(parameters[i].split("=")[0] === valuename){
	            rtnval = parameters[i].split("=")[1];
	            if(rtnval === undefined || rtnval === null){
	                rtnval = "";
	            }
	            return rtnval;
	        }
	    }
	};
	
	module.digitCheck = function(evt){
	    var code = evt.which?evt.which:event.keyCode;
	    if(code < 48 || code > 57){
	        return false;
	    }
	};
	
	
	module.isValidUrl = function(url){
		if(regUrl.test(url)){
			return true;
		} else {
			return false;
		}
	};
	
	module.isValidEmail = function(email){
		if(regEmail.test(email)){
			return true;
		} else {
			return false;
		}
	};
	
	module.isValidIp = function(ip){
		if(regIp.test(ip)){
			return true;
		} else {
			return false;
		}
	};
	
	// 검색 폼 입력값 일괄 Trim 처리
	module.applyTrim = function(formId){
		$("#" + formId + " input:text, #" + formId + " input:password").each(function(){
			$(this).val($.trim($(this).val()));
		});
	};
	
	return module;	
}(window.utils || {}, $));
