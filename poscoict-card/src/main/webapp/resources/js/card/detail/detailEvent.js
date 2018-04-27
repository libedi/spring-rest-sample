"use strict";

// 로우 선택 활성화 처리
$("body").on("tap", "table:not(#deptTbl, #prjTbl) tbody tr", function(){
	$(this).siblings().removeClass("selected-row");
	$(this).addClass("selected-row");
});

// 화폐단위 변환
$("body").on("keyup", ":text.currency", function(e){
	e.stopPropagation();
	var currency = common.formatCurrency($(this).val());
	$(this).val(currency);
});

// 정산일자 변경시 모든 유형에 적용
$(".datepicker").change(function(){
	$(".datepicker").val($(this).val());
});

// 검색시 엔터키 이벤트
$("body").on("keyup", ":text[name='srchTxt']", function(e){
	var code = e.which ? e.which : e.keyCode;
	if(code === 13 || code === 10) {
		$(this).parent().parent().find("a#btnSearch").trigger("tap");
	}
});
//
// 유형 선택 이벤트
$(":radio[name='adjustType']").on("change", function(){
	var val = $(":radio[name='adjustType']:checked").val();
	if(val === "D") {
		$("#deptTbl").removeClass("hidden");
		$("#prjTbl").addClass("hidden");
	} else if(val === "P") {
		$("#deptTbl").addClass("hidden");
		$("#prjTbl").removeClass("hidden");
	}
});

// 추가
$("#btnAdd").on("tap", function(){
	var val = $(":radio[name='adjustType']:checked").val();
	var tblId = "";
	var rowNum = 1;
	if(val === "D") {
		tblId = "deptTbl";
		rowNum = 4;
	} else if(val === "P") {
		tblId = "prjTbl";
		rowNum = 6;
	}
	var addIndex = ($("#"+ tblId + " > tbody > tr").length - 4) / rowNum;
	var html = tmpl("tmpl-" + tblId, {index : addIndex});
	$("#" + tblId + " tbody").append(html);
});

// 삭제
$("#btnDel").on("tap", function(){
	var val = $(":radio[name='adjustType']:checked").val();
	var tblId = "";
	var rowNum = 1;
	if(val === "D") {
		tblId = "deptTbl";
		rowNum = 4;
	} else if(val === "P") {
		tblId = "prjTbl";
		rowNum = 6;
	}
	var removeIndex = ($("#"+ tblId + " > tbody > tr").length - 4) / rowNum - 1;
	if(removeIndex > 0) {
		$("#" + tblId + " tbody").find("tr.row-index-" + removeIndex).remove();
	}
	//마지막 1라인만 남을경우 부가세 제외여부에 따른 금액조정
	if(removeIndex === 1) {
		if($(":radio[name='hasVat']:checked").val() === "1") {
			$("#"+ tblId +" :text[name='appramt']").val(common.formatCurrency($("#initAmt").val()));
		} else {
			$("#"+ tblId +" :text[name='appramt']").val(common.formatCurrency($("#apprtot").val()));
		}
	}
});

// 승인요청
$("#btnDtlAppr").on("tap", function(){
	return isValidApproveAmt() && isValidInputValue() && isValidAmt();
});

// 승인요청 처리
$("#btnApprv").on("tap", detail.reqAdj);

//파일 다운로드
$("ul.fileList").on("tap", "a.file-view", function(e){
	e.stopPropagation();
	detail.downloadView($(this).data("url"));
});

// 파일 삭제 팝업 호출
$("ul.fileList").on("tap", "a.add-del", function(e){
	e.stopPropagation();
	$("#delFileId").val($(this).data("id"));
});

// 파일 삭제
$("#btnDelete").on("tap", function(){
	detail.deleteFile($("#delFileId").val());
});