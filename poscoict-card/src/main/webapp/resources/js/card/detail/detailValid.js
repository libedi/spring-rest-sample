"use strict";

// 공급가 유효성 검증
function isValidAmt() {
	var amt = 0;
	var val = $(":radio[name='adjustType']:checked").val();
	var tblId = "";
	if(val === "D") {
		tblId = "#deptTbl";
	} else if(val === "P") {
		tblId = "#prjTbl";
	}
	$(tblId + " :text[name='appramt']").each(function(){
		amt += Number($(this).val().replace(/\,/g, ""));
	});
	
	var hasVat = $(":radio[name='hasVat']:checked").val();
	var validAmt, validMsg;
	if(hasVat === "1"){
		validAmt = Number($("#initAmt").val());
		validMsg = "validSupply";
	}else{
		validAmt = Number($("#apprtot").val());
		validMsg = "validapprtot";
	}
	
	var isValid = amt === validAmt;
	if(!isValid) {
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get(validMsg));
		$("#popAlert").popup("open");
	}
	return isValid;
}

// 입력값 유효성 검증
function isValidInputValue() {
	var val = $(":radio[name='adjustType']:checked").val();
	var tblId = "";
	if(val === "D") {
		tblId = "#deptTbl";
	} else if(val === "P") {
		tblId = "#prjTbl";
	}
	var isValid = true;
	$(tblId + " tbody > tr").each(function(i){
		$(this).find("input:not(:file)").each(function(i){
			if($(this).val() === ""){
				// 팝업 이벤트 초기화
				$("#popAlert").popup({
					afterclose : undefined
				});
				var cate = $(this).parents("tr").find("th").text();
				$("#alertMsg").text(cate + " " + messages.get("inputCate"));
				$("#popAlert").popup("open");
				isValid = false;
				return false;
			}
		});
		return isValid;
	});
	return isValid;
}

//승인권자 전결금액 검증
function isValidApproveAmt(){
	//총 사용금액이 전결가능 금액보다 작으면 OK, 크거나 같으면 승인요청 불가
	if( Number($("#apprtot").val()) < Number($("#autoApproveAmt").val()) ){
		return true;
	}else{
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").html(messages.get("validApproveAmt"));
		$("#popAlert").popup("open");
		return false;
	}
}