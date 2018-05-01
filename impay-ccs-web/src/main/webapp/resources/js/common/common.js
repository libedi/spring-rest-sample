var popArrObj = [];

var Common = (function(module, $){
	"use strict";
	var htmlEscapes = {
		  '&': '&amp;',
		  '<': '&lt;',
		  '>': '&gt;',
		  '"': '&quot;',
		  "'": '&#x27;',
		  '/': '&#x2F;'
	};
	var htmlEscaper = /[&<>"'\/]/g;
	/**
	 * Login/Logout 기능 구현 (이하)
	 * */
	module.login = function(el) {
		var isValid = el.form('validate');
		if(isValid) {
			el.submit();	
		}
	};

	module.logout = function(contextPath) {
		if(confirm("로그아웃하시겠습니까?")) {
			for(var i=0; i<popArrObj.length; i++){
				popArrObj[i].close();
			}
			top.location.href = contextPath+'/login/logout';
		}
	};

	module.tabs = function() {
		$(".tab-head").not(".sharpLink").each(function(){
			var $active, $content, $links = $(this).find('a');
			
			var locationHash = location.hash || "#tab1";
			
			$active = $($links.filter('[href="' + locationHash + '"]')[0] || $links[0]);
			$active.addClass('tab-selected');

			$content = $($active[0].hash);

			$links.not($active).each(function () {
				$(this.hash).hide();
			});

			$(this).on('click', 'a', function(e){
				$active.removeClass('tab-selected');
				$content.hide();

				$active = $(this);
				$content = $(this.hash);
				
				$active.addClass('tab-selected');
				$content.show();

				e.preventDefault();
			});
		});
	};

	/**
	 * checkbox 전체선택/해제 기능
	 * ckall  (className) 
	 * cklist (className)
	 */
	module.toggle = function(ckall, cklist) {
		$(cklist).prop("checked", $(ckall).prop("checked"));
	};
	/**
	 * 그룹 checkbox 전체선택 시 전체선택 checkbox선택  
	 * ckall  (className) 
	 * cklist (className)
	 */
	module.iscked = function(ckall, cklist) {
		if($(cklist).length === $(cklist+":checked").length){
			$(ckall).prop("checked", true);
		} else {
			$(ckall).prop("checked", false);
		}
	};
	/**
	 * 그룹 checkbox 선택 된 갯수  
	 * cklist (className)
	 */
	module.getCkedCnt = function(cklist) {
		return $(cklist+":checked").length;
	};
	/**
	 * 선택 된 그룹checkbox의 value를 하나의 String으로 리턴  
	 * cklist (className)
	 * delimiter (value 연결 시 구분자 ex), : ...)
	 */
	module.getCkedVal = function(cklist, delimiter) {
		if(delimiter === undefined) { delimiter = ','; }  //default..
		var result = '';
		$(cklist+":checked").each(function(idx){
			if(idx < $(cklist+":checked").length-1) {
				result = result.trim() + $(this).val() + delimiter;
			} else {
				result = result.trim() + $(this).val();
			}
		});
		return result;
	};
	/**
	 * 선택 된 그룹checkbox의 value를 Array로 리턴  
	 * cklist (className)
	 */
	module.getCkedToArray = function(cklist) {
		var result = [];
		$(cklist+":checked").each(function(){
			result.push($(this).val());
		});
		return result;
	};



	/**
	 * inputNm에 해당하는 input box의 값을 스트링으로 리턴
	 * 구분자는 ','(콤마)
	 */
	module.getInputText = function(inputNm) {	
		var result = "";
		
		$('input[name=' + inputNm +']').each(function(){
			result += this.value +",";
		});
		
		result = result.substring(0, result.length-1);		
		return result;	
	};
	/* 전화 번호 / 팩스 번호 / 사업자 번호  */
	module.inPutSplit = function (){
	    var repTelNo = $("#repTelNo1").val()+$("#repTelNo2").val()+$("#repTelNo3").val();
	    $("#repTelNo").val(repTelNo);
	   
	    var faxNo = $("#faxNo1").val()+$("#faxNo2").val()+$("#faxNo3").val();
	    $("#faxNo").val(faxNo);
	    
	    var bizrRegNo = $("#bizrRegNo1").val()+$("#bizrRegNo2").val()+$("#bizrRegNo3").val();
	    $("#bizrRegNo").val(bizrRegNo);
	};
	/*
	 * 2015.11.16
	 * 숫자 콤마처리
	 * ex) 1234567 => 1,234,567 
	 * */
	module.formatMoney = function(value){
		return String(value).replace(/\B(?=(\d{3})+(?!\d))/g, ",");		
	};


	module.formatDate = function(value, separator) {
		if(value.length === 4) {
			return value;
		} else if(value.length === 6) {
			return module.formatDateYYYYMM(value,separator);
		} else if(value.length === 8) {
			return module.formatDateYYYYMMDD(value,separator);
		} else {
			return '';
		}
	};

	module.formatDateYYYYMM = function(value, separator) {
		if(module.isEmpty(separator)){ 
			separator = "."; 
		}
		return !module.isEmpty(value) ? value.substring(0,4)+separator+value.substring(4,6) : '';
	};

	module.formatDateYYMM = function(value, separator) {
		if(module.isEmpty(separator)){ 
			separator = "."; 
		}
		return !module.isEmpty(value) ? value.substring(2,4)+separator+value.substring(4,6) : '';
	};

	module.formatDateYYYYMMDD = function(value, separator) {
		if(module.isEmpty(separator)){ 
			separator = "."; 
		}
		return !module.isEmpty(value) ? value.substring(0,4)+separator+value.substring(4,6)+separator+value.substring(6,8) : '';
	};

	module.formatDateYYMMDD = function(value, separator) {
		if(module.isEmpty(separator)){ 
			separator = "."; 
		}
		return !module.isEmpty(value) ? value.substring(2,4)+separator+value.substring(4,6)+separator+value.substring(6,8) : '';
	};

	module.nvl = function(value, replace) {
		return value !== null ? value : replace; 
	};

	module.isEmpty = function(value) {
		return value === null ? true : value === undefined ? true : value === 'undefined' ? true : value === '' ? true : false;
	};

	/**
	 * DateFormat, moneyFormat을 제가할 때 사용
	 * [srcStr] 원본 스트링
	 * [tarStr] 변경할 문자
	 * [rpsStr] 변경될 문자
	 */
	module.removeFormat = function(srcStr, tarStr, rpsStr) {
		if(module.isEmpty(srcStr)) {
			return "";
		} else {
			return srcStr.replace(new RegExp(tarStr, 'gi'), rpsStr);
		}
	};

	/*
	 * 2015.11.18
	 * 왼쪽에서부터 채운다는 의미 LPAD
	 * s : 대상 스트링
	 * c : 채울 스트링
	 * n : 전체 스트링 길이
	 * ex ) module.lpad("1","0","3") = > "001"
	 * */
	module.lpad = function (s, c, n) {   		
		s = String(s);
		c = String(c); 
		
	    if (! s || ! c || s.length >= n) {
	        return s;
	    }	 
	    var max = (n - s.length)/c.length;
	    
	    for (var i = 0; i < max; i++) {
	        s = c + s;
	    }
	    return s;
	};

	/*
	 * 2015.11.18
	 * 오른쪽에서부터 채운다는 의미
	 */
	 
	module.rpad = function (s, c, n) {		
		s = String(s);
		c = String(c);
		
		if (! s || ! c || s.length >= n) {
			return s;
		}
		
		var max = (n - s.length)/c.length;
		for (var i = 0; i < max; i++) {
			s += c;
		}
		
		return s;		
	};

	module.getIsNull = function (Val)
	{
		if (String(Val).valueOf() === "undefined") 
		{
			return true;
		}
		if (Val === null) 
		{
			return true;
		}
		if (("x" + Val === "xNaN") && (String(Val.length).valueOf() === "undefined")) 
		{
			return true;
		}
		if ( Val.length === 0 ) 
		{
			return true;
		}

		return false;
	};

	/**
	 * 문자열 바이트 구하기
	 */
	module.getByteString = function(str){
		var byte = 0;
		if(Common.isEmpty(str)){
			return;
		} else {
			var tempStr = str.replace(htmlEscaper, function(match){
				return htmlEscapes[match];
			});
		}
		for(var i=0; i<tempStr.length; i++){
			if(escape(tempStr.charAt(i)).length >= 4 || escape(tempStr.charAt(i)) === "%A7"){
				byte += 3;
			} else if(escape(str.charAt(i)) === "%0A"){
				byte += 2;
			} else if(escape(str.charAt(i)) !== "%0D"){
				byte++;
			}
		}
		return byte;
	};

	/**
	 * 바이트만큼 문자열 반환
	 */
	module.getStringToByte = function(str, limit){
		var byte = 0;
		var tempStr = str.replace(htmlEscaper, function(match){
			return htmlEscapes[match];
		});
		var retStr = tempStr;
		
		for(var i=0; i<tempStr.length; i++){
			if(escape(tempStr.charAt(i)).length >= 4 || escape(tempStr.charAt(i)) === "%A7"){
				byte += 3;
			} else if(escape(str.charAt(i)) === "%0A"){
				byte += 2;
			} else if(escape(str.charAt(i)) !== "%0D"){
				byte++;
			}
			if(byte > limit){
				retStr = str.substring(0, i);
				break;
			}
		}
		return retStr;
	};

	/**
	 * 입력값이 숫자인지 여부 확인
	 */
	module.checkNumber = function(checkData) {
		var numPattern = /([^0-9])/;
	    numPattern = checkData.value.match(numPattern);
	    if(numPattern !== null){
	        alert("숫자만 입력해 주세요!");
	        checkData.value = "";
	        checkData.focus();
	        return false;
	    }
	};

	return module;	
}(window.Common || {}, $));



/* ##########################
 * ###### String 관련 공통 JS ######
 * ##########################
 * */
var StringUtil = (function(module, $){
	"use strict";

	module.nvl = function(value, replace) {
		if(value === null || value === "null") {
			return replace;
		} else {
			return value;
		}
	};

	module.isEmpty = function(value) {
		value = String(value);
		if(value === null || value === undefined || value === 'undefined' || value === '' || value === null || value === "null") {
			return true;
		} else {
			return false;
		}
	};
	return module;	
}(window.StringUtil || {}, $));




/* ########################
 * ###### 날짜 관련 공통 JS ######
 * ########################
 * */
var DateUtil = (function(module, $){
	"use strict";

	/*
	 * 2015.11.18
	 * 오늘 날짜를 리턴  YYYYMMDD형
	 */
	module.getToday = function(){
		
		var result = module.formatDate(new Date());
		
		return result;
	};

	module.formatDate = function(date){
		var yyyy = date.getFullYear().toString();
	    var mm = (date.getMonth() + 1).toString();
	    var dd = date.getDate().toString();
		return yyyy + Common.lpad(mm, 0, 2) + Common.lpad(dd, 0, 2);
	};

	/*
	 * 입력된 날자에  OffSet만큼의 일을 더한다.
	 * date yyyymmdd형 스트링
	 * nOffSet 증감값
	 */  
	module.addDay = function(tarDate, nOffSet) {
		var date = new Date(tarDate.substr(0, 4), Common.lpad(tarDate.substr(4, 2) - 1,0,2), Common.lpad(tarDate.substr(6, 2), 0, 2));
		date.setDate(date.getDate() + nOffSet);	
		return module.formatDate(date);	
	};

	/*
	 * 입력된 날자에  OffSet만큼의 달을 더한다.
	 * date yyyymmdd형 스트링
	 * nOffSet 증감값
	 */  
	module.addMonth = function(tarDate, nOffSet) {
		var date = new Date(tarDate.substr(0, 4), Common.lpad(tarDate.substr(4, 2) - 1,0,2), Common.lpad(tarDate.substr(6, 2), 0, 2));
		date.setMonth(date.getMonth() + nOffSet);
		return module.formatDate(date);	
	};

	/*
	 * 두 객체(input)에 과거(nOffSet만큼의 과거)와 현재를 세팅
	 * fmObjId
	 * toObjId
	 * nOffSet 증감값
	 */
	module.setMonthPeriod = function(fmObjId, toObjId, nOffSet){
	
		var today = module.getToday();
		
		var dateFm = module.addMonth(today, nOffSet); 
		var dateTo = today;
		
		dateFm = Common.formatDateYYYYMM(dateFm, ".");
		dateTo = Common.formatDateYYYYMM(dateTo, ".");
		
		fmObjId.val(dateFm);
		toObjId.val(dateTo);
	};

	/*
	 * 두 객체(input)에 과거(nOffSet만큼의 과거)와 현재를 세팅
	 * fmObjId
	 * toObjId
	 * nOffSet 증감값
	 */
	module.setDayPeriod = function(fmObjId, toObjId, nOffSet){
		
		var result = "";
		var result2 = "";
		
		var today = module.getToday();
		
		var dateFm = module.addMonth(today, nOffSet); 
		var dateTo = today;
		
		result = Common.formatDateYYYYMMDD(dateFm, ".");
		result2 = Common.formatDateYYYYMMDD(dateTo, ".");
		
		fmObjId.val(result);
		toObjId.val(result2);
		
	};

	module.getDiffMonth = function (sStartDate, sEndDate){
		if (Common.getIsNull(sStartDate) || Common.getIsNull(sEndDate)) {
			return NaN;
		}
		var nSttMon = parseInt(sStartDate.substr(0, 4), 10) * 12 + parseInt(sStartDate.substr(4, 2), 10);
		var nEndMon = parseInt(sEndDate.substr(0, 4), 10) * 12 + parseInt(sEndDate.substr(4, 2), 10);
		return (nEndMon - nSttMon);
	};
	module.getDiffDate = function(sttDd, endDd){
		if(StringUtil.isEmpty(sttDd) || StringUtil.isEmpty(sttDd)){
			alert("날짜형식을 확인해주세요.");
			return false;
		}
		var arrSttDd = sttDd.split(".");
		var arrEndDd = endDd.split(".");
		return (new Date(arrEndDd[0],arrEndDd[1]-1,arrEndDd[2]).getTime()-new Date(arrSttDd[0],arrSttDd[1]-1,arrSttDd[2]).getTime())/1000/60/60/24; 
	};
	return module;	
}(window.DateUtil || {}, $));