"use strict";

//스크립트 모듈 초기화
slide.init({
	contextPath : contextPath,
});
// 패널 오픈 이벤트
$("#slideMenu").panel({
	beforeopen : slide.getBadgeCnts
});