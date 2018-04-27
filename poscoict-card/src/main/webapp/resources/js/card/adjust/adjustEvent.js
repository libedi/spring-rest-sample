"use strict";

// 정산화면 페이지 초기화
$(document).on("pagecreate", "#adjust", function(){
	// 리스트 새로고침
	$("#toggleTbl").pullToRefresh({
		refresh: 120,
		resetSpeed: '300ms'
	}).on("refresh.pulltorefresh", adjust.getCardHistoryList);
});

// 카드 검색 페이지 초기화
$(document).on("pagecreate", "#selectCard", function(){
	// 오른쪽 스와이프 이전 탐색
	$(document).on("swiperight", ".ui-page", function(e){
		$.mobile.back();
	});
	// 대상카드 선택 이벤트
    $(".select-card a").on("tap", function(){
    	$(".input-group-box").html($(this).children("h1").html());
    	$("#cardno").val($(this).children("input[name='selectCard']").val());
    	adjust.getCardHistoryList();
    });
});

// 목록 선택 토글 이벤트
function toggleList() {
	$("#toggleTbl tr:odd").addClass("show-row");
	$("#toggleTbl tr:not(.show-row)").hide();
	$("#toggleTbl tr:first-child").show();
	$("#toggleTbl tr.show-row td a.btn-icon-expand").on("tap", function(){
	    $(this).parent('td').parent('tr').next("tr").toggle();
	    $(this).toggleClass("up");
	    // 상세보기 펼치기 할때 두번눌리는 현상 수정
	    if($(this).attr("class") === "btn-icon-expand up") {
	    	// open 시에만 결재권자 정보 조회
	    	adjust.getCardHistoryDetail($(this).parent("td").parent("tr").data("seq"));
	    }
	    return false;
	});
	$("#toggleTbl tr").on("tap", function(event){
		//더보기 / 상세내용은 선택 제외.
		if(event.target.tagName == "A" || event.target.tagName == "a" || $(this).hasClass("collapse-tr")){
			return false;
		}else{
			$(".selected-row").removeClass("selected-row");
			$(this).addClass("selected-row");
		}
	});
}
toggleList();

// 카드 처리 상태 라디오 버튼 클릭 이벤트
$(":radio[name='status']").on("change", adjust.getCardHistoryList);

// 사유선택 팝업 내 제외
$("#btnExcpt").on("tap", adjust.except);

// 정산제외
$("#btnAdjExcpt").on("tap", function() {
	return adjustValidate("E");
});

// 정산취소 컨펌창 -> 확인
$("#btnCncl").on("tap",adjust.cancel);

// 정산취소
$("#btnAdjCncl").on("tap", function() {
	return adjustValidate("C");
});

// 정산
$("#btnAdj").on("tap", function() {
	if(adjustValidate("A")) {
		adjust.duplCheck();
	}
});
