"use strict";

/*
 * 법인카드 정산 검증 함수
 * @param action : "E"-정산제외, "A"-정산, "C"-취소 
 */ 
function adjustValidate(action) {
	// 팝업 이벤트 초기화
	$("#popAlert").popup({
		afterclose : undefined
	});
	// 공통 검증
	if($(".selected-row").length === 0) {
		$("#alertMsg").text(messages.get("targetSel"));
		$("#popAlert").popup("open");
		return false;
	} else if(JSON.parse($("#isValidTerm").val())) {	// 검증기간이면,
		$("#alertMsg").text($("#validTermMessage").val());
		$("#popAlert").popup("open");
		return false;
	}
	var isValid = true;
	// 이벤트별 검증
	switch(action) {
	case "E":		// 정산제외
		if($(".selected-row").find(":hidden[name='transferFlag']").val() !== "N") {
			$("#alertMsg").text(messages.get("excptNo"));
			$("#popAlert").popup("open");
			isValid = false;
		}
		break;
	case "A":		// 정산
		if($(".selected-row").find(":hidden[name='transferFlag']").val() !== "N") {
			$("#alertMsg").text(messages.get("detailNo"));
			$("#popAlert").popup("open");
			isValid = false;
		}
		break;
	case "C":		// 취소
		var selRow = $(".selected-row");
		if(selRow.find(":hidden[name='transferFlag']").val() !== "Y" || selRow.find(":hidden[name='statCd']").val() !== "N" ) {
			$("#alertMsg").text(messages.get("cancelNo"));
			$("#popAlert").popup("open");
			isValid = false;
		}
		break;
	default :
		isValid = false;
		break;
	}
	return isValid;
}