"use strict";

// 정산상세 페이지 초기화
$(document).on("pagecreate", "#detail", function(e) {
	// 오른쪽 스와이프 이전 탐색
	$(document).on("swiperight", ".ui-page", function(e){
		$.mobile.back();
	});
	// date picker
    var $input = $( '.datepicker' ).pickadate({
    	format: 'yyyy.mm.dd',
    	formatSubmit: 'yyyy-mm-dd',
    	hiddenSuffix : "Submit",
        container: '#datepicker1',
        closeOnSelect: true,
        closeOnClear: false,
    });
    var picker = $input.pickadate('picker');
    
    // 첨부파일
    $(":file").change(function(){
    	detail.upload($(":file").index(this));
    });
    
    // 파일보기
    $(".photopopup").popup({
        beforeposition: function() {
            var maxHeight = $(window).height() - 60 + "px";
            $(".photopopup img").css("max-height", maxHeight);
        }
    });
    
    // 참석자
    $(":text[name='attend']").blur(function(){
    	$(":text[name='attend']").val($(this).val());
    });
    
    // Task
    $("#prjTbl").on("tap", ".goTask", function(){
    	var index = $(this).data("index");
    	var projectId = $("#prjTbl tbody tr.row-index-" + index + " input[name='projectId']").val();
    	if(projectId === ""){
    		// 팝업 이벤트 초기화
    		$("#popAlert").popup({
    			afterclose : undefined
    		});
    		$("#alertMsg").text(messages.get("selectProject"));
    		$("#popAlert").popup("open");
    		return false;
    	} else {
    		$(":mobile-pagecontainer").pagecontainer("change", common.getContextPath() + "/detail/selectTask", {
        		data : {
        			index : index,
        			projectId : projectId
        		},
        		transition : "slide"
        	});
    	}
    });
    
    // 수행조직
    $("#prjTbl").on("tap", ".goOrg", function(){
    	var index = $(this).data("index");
    	var projectId = $("#prjTbl tbody tr.row-index-" + index + " input[name='projectId']").val();
    	if(projectId === ""){
    		// 팝업 이벤트 초기화
    		$("#popAlert").popup({
    			afterclose : undefined
    		});
    		$("#alertMsg").text(messages.get("selectProject"));
    		$("#popAlert").popup("open");
    		return false;
    	} else {
    		$(":mobile-pagecontainer").pagecontainer("change", common.getContextPath() + "/detail/selectOrg", {
        		data : {
        			index : index,
        			projectId : projectId
        		},
        		transition : "slide"
        	});
    	}
    });
    
    // 원가유형
    $("#prjTbl").on("tap", ".goType", function(){
    	var index = $(this).data("index");
    	var projectId = $("#prjTbl tbody tr.row-index-" + index + " input[name='projectId']").val();
    	if(projectId === ""){
    		// 팝업 이벤트 초기화
    		$("#popAlert").popup({
    			afterclose : undefined
    		});
    		$("#alertMsg").text(messages.get("selectProject"));
    		$("#popAlert").popup("open");
    		return false;
    	} else {
    		$(":mobile-pagecontainer").pagecontainer("change", common.getContextPath() + "/detail/selectType", {
        		data : {
        			index : index,
        			projectId : projectId,
        			projectNumber : $("#prjTbl tbody tr.row-index-" + index + " input[name='projectNumber']").val()
        		},
        		transition : "slide"
        	});
    	}
    });
    // 과세유형 변경
    $(":radio[name='hasVat']").change(function(){
    	//과세유형 변경시 화면에 표시되는 공급가, 부가세 값 자동 변경 및 1라인일경우 금액도 자동변경.
    	if($(":radio[name='hasVat']:checked").val() === "1"){
    		$("#tdAmt").text(common.formatCurrency($("#initAmt").val()));
    		$("#tdVat").text(common.formatCurrency($("#initVat").val()));
    		if($("#deptTbl :text[name='appramt']").length === 1){
    			$("#deptTbl :text[name='appramt']").val(common.formatCurrency($("#initAmt").val()))
    		}
    		if($("#prjTbl :text[name='appramt']").length === 1){
    			$("#prjTbl :text[name='appramt']").val(common.formatCurrency($("#initAmt").val()))
    		}
    	} else {
    		$("#tdAmt").text(common.formatCurrency($("#apprtot").val()));
    		$("#tdVat").text("0");
    		if($("#deptTbl :text[name='appramt']").length === 1){
    			$("#deptTbl :text[name='appramt']").val(common.formatCurrency($("#apprtot").val()))
    		}
    		if($("#prjTbl :text[name='appramt']").length === 1){
    			$("#prjTbl :text[name='appramt']").val(common.formatCurrency($("#apprtot").val()))
    		}
    	}
    	
    });
});

//승인권자 검색 페이지 초기화
$(document).on("pagecreate", "#selectAppr", function() {
	// 오른쪽 스와이프 이전 탐색
	$(document).on("swiperight", ".ui-page", function(e){
		$.mobile.back();
	});
    // 검색 이벤트
	$("#btnSearch").on("tap", detail.getApprList);
	// 추가 이벤트
	$("#apprContent").on("tap", "a.list-add-btn", function(){
		if($(this).attr("class").indexOf("checked") > 0) {
			// 선택해제 & 삭제
			$(this).removeClass("checked");
			$(this).text("추가");
			var id = $(this).data("id");
			$("#apprSelContent tr").each(function(){
				if(id === $(this).data("id")) {
					$(this).remove();
					return false;
				}
			});
		} else {
			// 선택추가
			$(this).addClass("checked");
			$(this).text("");
			var dataObj = $(this).parent().siblings().first().find("form").serializeObject();
			var html = tmpl("tmpl-apprSelList", dataObj);
			$("#apprSelContent").append(html);
		}
	});
	// 삭제 이벤트
	$("#apprSelContent").on("tap", "a.list-del-btn", function(e){
		var id = $(this).data("id");
		$("#apprContent tr").each(function(){
			if(id == $(this).data("id")) {
				$(this).find("a.list-add-btn").removeClass("checked");
				$(this).find("a.list-add-btn").text(messages.get("add"));
				return false;
			}
		});
		$(this).parent().parent().remove();
		e.preventDefault();
	});
	// 선택 이벤트
	$("#btnOk").on("tap", function(){
		var $sel = $("#apprSelContent tr");
		if($sel.length === 0) {
			// 팝업 이벤트 초기화
			$("#popAlert").popup({
				afterclose : undefined
			});
			$("#alertMsgAppr").text(messages.get("selectApprv"));
    		$("#popAlertAppr").popup("open");
    		return false;
		}
		var hname = $sel.length === 1 ? 
				$sel.find(":hidden[name='hname']").val() : 
				$sel.last().find(":hidden[name='hname']").val() + " " + messages.get("manEtc") + " " + ($sel.length - 1) + messages.get("manCnt");
		var empcdArray = [];
		var empIdArray = [];
		var userIdArray = [];
		
		$sel.each(function(){
			empcdArray.push($(this).find(":hidden[name='empcd']").val());
			empIdArray.push($(this).find(":hidden[name='empId']").val());
			userIdArray.push($(this).find(":hidden[name='userId']").val());
		});
		
		$("#deptTbl tbody tr span.hname," +
		  "#prjTbl tbody tr span.hname").text(hname);
		$("#deptTbl tbody tr :hidden[name='empcd']," +
		  "#prjTbl tbody tr :hidden[name='empcd']").val(empcdArray.join(","));
		$("#deptTbl tbody tr :hidden[name='empId']," +
		  "#prjTbl tbody tr :hidden[name='empId']").val(empIdArray.join(","));
		$("#deptTbl tbody tr :hidden[name='userId']," +
		  "#prjTbl tbody tr :hidden[name='userId']").val(userIdArray.join(","));
		
		//선택한결재권자 중 마지막선택한 사람의 전결금액을 가져옴.
		var autoApproveAmt = $sel.length === 1 ? 
				$sel.find(":hidden[name='autoApproveAmt']").val() : 
				$sel.last().find(":hidden[name='autoApproveAmt']").val();
		$("#autoApproveAmt").val(autoApproveAmt);
	});
});

// 검색 페이지 초기화 공통함수
function initSearchLayer(layerId, searchFunction, selectFunction) {
	$(document).on("pagecreate", layerId, function() {
		// 오른쪽 스와이프 탐색
		$(document).on("swiperight", ".ui-page", function(e){
			$.mobile.back();
		});
	    // 검색 이벤트
		$("#btnSearch").on("tap", searchFunction);
		// 선택 이벤트
		$("#btnOk").on("tap", selectFunction);
	});
}

// 부서 검색 페이지 초기화
initSearchLayer("#selectDept", detail.getDeptList, function(){
	var $sel = $("#deptContent tr.selected-row");
	var rowIndex = $("#index").val();
	if($sel.length == 0){
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get("selectDept"));
		$("#popAlert").popup("open");
		return false;
	}
	$("#deptTbl tbody tr.row-index-" + rowIndex).find("span.deptNm").text($sel.data("nm"));
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='deptCode']").val($sel.data("cd"));
	//부서선택시 계정관련 항목 초기화
	$("#deptTbl tbody tr.row-index-" + rowIndex).find("span.accName").empty();
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='mainAccCode']").val("");
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='subAccCode']").val("");
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='attributeCategory']").val("");
});

// 계정 검색 페이지 초기화
initSearchLayer("#selectAccnt", detail.getAccntList, function(){
	var $sel = $("#accntContent tr.selected-row");
	var rowIndex = $("#index").val();
	if($sel.length == 0){
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get("selectAccnt"));
		$("#popAlert").popup("open");
		return false;
	}
	$("#deptTbl tbody tr.row-index-" + rowIndex).find("span.accName").text($sel.find(":hidden[name='accName']").val());
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='mainAccCode']").val($sel.find(":hidden[name='mainAccCode']").val());
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='subAccCode']").val($sel.find(":hidden[name='subAccCode']").val());
	$("#deptTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='attributeCategory']").val($sel.find(":hidden[name='attributeCategory']").val());
});

// 프로젝트 검색 페이지 초기화
initSearchLayer("#selectPrj", detail.getPrjList, function(){
	var $sel = $("#prjContent tr.selected-row");
	var rowIndex = $("#index").val();
	if($sel.length == 0){
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get("selectProject"));
		$("#popAlert").popup("open");
		return false;
	}
	$("#prjTbl tbody tr.row-index-" + rowIndex).find("span.projectName").text($sel.find(":hidden[name='projectName']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='projectName']").val($sel.find(":hidden[name='projectName']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='projectNumber']").val($sel.find(":hidden[name='projectNumber']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='projectType']").val($sel.find(":hidden[name='projectType']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='projectStatusCode']").val($sel.find(":hidden[name='projectStatusCode']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='projectStatusCodeNm']").val($sel.find(":hidden[name='projectStatusCodeNm']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='projectId']").val($sel.find(":hidden[name='projectId']").val());
	// 프로젝트에 영향을 받는 항목들 초기화
	$("#prjTbl tbody tr.row-index-" + rowIndex  + " span.task, " +
	  "#prjTbl tbody tr.row-index-" + rowIndex  + " span.org, " +
	  "#prjTbl tbody tr.row-index-" + rowIndex  + " span.type").empty();
	$("#prjTbl tbody tr.row-index-" + rowIndex  + " :hidden.task, " +
	  "#prjTbl tbody tr.row-index-" + rowIndex  + " :hidden.org, " +
	  "#prjTbl tbody tr.row-index-" + rowIndex  + " :hidden.type").val("");
});

// Task 검색 페이지 초기화
initSearchLayer("#selectTask", detail.getTaskList, function(){
	var $sel = $("#taskContent tr.selected-row");
	var rowIndex = $("#index").val();
	if($sel.length == 0){
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get("selectTask"));
		$("#popAlert").popup("open");
		return false;
	}
	$("#prjTbl tbody tr.row-index-" + rowIndex).find("span.taskName").text($sel.find(":hidden[name='taskName']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='taskId']").val($sel.find(":hidden[name='taskId']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='taskName']").val($sel.find(":hidden[name='taskName']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='taskNumber']").val($sel.find(":hidden[name='taskNumber']").val());
});

// 수행조직 검색 페이지 초기화
initSearchLayer("#selectOrg", detail.getOrgList, function(){
	var $sel = $("#orgContent tr.selected-row");
	var rowIndex = $("#index").val();
	if($sel.length == 0){
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get("selectDept"));
		$("#popAlert").popup("open");
		return false;
	}
	$("#prjTbl tbody tr.row-index-" + rowIndex).find("span.expOrgName").text($sel.find(":hidden[name='expOrgName']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='expOrgName']").val($sel.find(":hidden[name='expOrgName']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='expOrgId']").val($sel.find(":hidden[name='expOrgId']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='segment2']").val($sel.find(":hidden[name='segment2']").val());
});

// 원가유형 검색 페이지 초기화
initSearchLayer("#selectType", detail.getTypeList, function(){
	var $sel = $("#typeContent tr.selected-row");
	var rowIndex = $("#index").val();
	if($sel.length == 0){
		// 팝업 이벤트 초기화
		$("#popAlert").popup({
			afterclose : undefined
		});
		$("#alertMsg").text(messages.get("selectType"));
		$("#popAlert").popup("open");
		return false;
	}
	$("#prjTbl tbody tr.row-index-" + rowIndex).find("span.expType").text($sel.find(":hidden[name='expType']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='expType']").val($sel.find(":hidden[name='expType']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='mainAccCode']").val($sel.find(":hidden[name='mainAccCode']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='subAccCode']").val($sel.find(":hidden[name='subAccCode']").val());
	$("#prjTbl tbody tr.row-index-" + rowIndex).find(":hidden[name='attributeCategory']").val($sel.find(":hidden[name='attributeCategory']").val());
});
