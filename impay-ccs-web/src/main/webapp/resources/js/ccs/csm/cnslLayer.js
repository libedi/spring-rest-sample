var cnslLayer = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {
			tjurProcCtnt :		"/cnslMng/tjurProc/procCtnt"
	};
	var messages;
	var forms;
	var context;	//jquery selector context
	
	/**
	 * public 
	 */	
	module.init = function(opts){
		options.contextPath = opts.contextPath;
		forms = opts.forms;
		messages = opts.messages;
		context = opts.context;
	};
	module.getOptions = function(){
		return root.options;
	};

	// 공지사항 레이어
	module.openAnnoDesc = function() {
		layer.init({contextPath:options.contextPath, parentModule:root});
		layer.make('fixed-layer', messages.annoBordTitle, {width:850,height:585});
        layer.load('csm/layer/layerAnnoDesc');
        layer.open();
	};
	// 대상 가맹점 검색 레이어
	module.openTrgtCpSeachLayer = function() {		
		var searchClf = "";
		var searchWord = "";
		var title = messages.cpTitle;
		if($("#code", context).val() !== "") {
			searchClf = "cpCd";
			searchWord = $("#code", context).val();
		} else if($("#name", context).val() !== "") {
			searchClf = "cpNm";
			searchWord = $("#name", context).val();
		}
		layer.init( {data: {searchClf:searchClf, searchWord:searchWord},
		     		 returnCallback: function(data){
		     			 if(data === undefined) return;
		     			 $("#code", context).val(data.cpCd);
		     			 $("#name", context).val(data.paySvcNm);
		     			 $("#cpCd", context).val(data.cpCd);
				     }, contextPath:options.contextPath});
		layer.make("fixed-layer", title, {width:850, height:576});
		layer.load("/common/layer/layerFindListCp");
		layer.open();
	};
	// 거래명세서 팝업
	module.openReceiptLayer = function(trdNos) {
		var url = "csm/layer/layerReceipt";
		var title = messages.receiptTitle;
		var css = {width : 850, height : 580};
		var data = {
				trdNos : trdNos,
				custMphnNo : $("#custMphnNo", context).val()
			};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	// 미납이력 및 가산금 부과내역 레이어
    module.openNpayHistory = function(payMphnId) {
    	var data = { payMphnId : payMphnId };
        layer.init({data:data, contextPath:options.contextPath, parentModule:root});
        layer.make("fixed-layer", messages.npayTitle, {width:983, height:585});
        layer.load("csm/layer/layerNpayHistory");
        layer.open();
        // 날짜 선택 최대값을 오늘로 제한
        $(document).on("focus", ".calendar", function(){
            $(this).removeClass("hasDatepicker").datepicker();
        });
    };
    // 환불 접수 레이어
    module.openPayBackRcpt = function()	{
    	layer.init({contextPath:options.contextPath, parentModule:root});
        layer.make("fixed-layer", messages.paybackRcptTitle, {width:750, height:266});
        layer.load("rpm/layer/layerPayBackRcpt");
        layer.open();
    };
    // 결제차단 등록 팝업
	module.openPayItcptRegist = function() {
		var url = "csm/layer/layerPayItcptRegist";
		var title = messages.payItcptRegistTitle;
		var css = {width : 850, height : 401};
		var data = {
				payMphnId : $("#payMphnId", context).val(),
				payrSeq : $("#payrSeq", context).val(),
				mphnNo : $("#custMphnNo", context).val()
			};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data, returnCallback:root.reloadPayItcpt});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	// 결제차단등록 팝업 콜백
	module.reloadPayItcpt = function(retObj){
		if(retObj !== null && retObj !== undefined){
			if($("#payMphnId", context).val() !== "" && retObj.page !== null && retObj.page !== undefined && Number(retObj.page) !== "NaN"){
				cnslMng.goPagePayItcpt(retObj.page);
			} else if($("#payMphnId", context).val() === "" && retObj.payMphnId !== null && retObj.payMphnId !== undefined) {
				// 1. 결제폰정보 설정
				$("#payMphnId", context).val(retObj.payMphnId);
				// 2. 고객정보 조회
				cnslMng.custInfoSearch();
				// 3. 결제차단내용 조회
				cnslMng.goPagePayItcpt(retObj.page);
			}
		}
	};
	// 결제차단 수정 팝업
	module.openPayItcptUpdate = function(id) {
		var url = "csm/layer/layerPayItcptUpdate";
		var title = messages.payItcptUpdateTitle;
		var css = {width : 850, height : 565};
		var data = {
				itcptRegSeq : id,
				payMphnId : $("#payMphnId", context).val(),
				payrSeq : $("#payrSeq", context).val(),
				mphnNo : $("#custMphnNo", context).val()
			};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data, returnCallback:cnslMng.payItcptSearch});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	// 사업부 이관 팝업
	module.openBusinessTjur = function() {
		var url = "csm/layer/layerTjurBusiness";
		var title = messages.businessTjurTitle;
		var css = {width : 850, height : 565};
		var data = {rcptNo : $("#tempProcRcptNo", context).val()};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data, returnCallback:root.tjurCallback});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	// 사업부 이관 콜백함수
	module.tjurCallback = function(result){
		if(result !== undefined && result !== null){
			impay.sendPost({
				requestUri: options.contextPath + uris.tjurProcCtnt,
				data: result,
				dataType: "json",
				successCallback: function(result){
					if(result.success === true){
						$("#tjurCtnt", context).text(result.result);
						$("#tjurYn", context).val("Y");
						$("#tjurCd", context).val("B");
						// 전체 상담이력 리스트, 관리탭 재조회
						cnslMng.cnslDtlSearch();
						cnslMng.cnslChgSearch();
					} else {
						alert(messages.error);
						$("#tjurCtnt", context).text("");
					}
				}
			});
		}
	};
	// 이관처리내역 레이어
	module.openTjurHistory = function() {
		var url = "csm/layer/layerTjurHistory";
		var title = messages.tjurHistTitle;
		var css = {width : 600, height : 447};
		var data = {rcptNo : $("#tempProcRcptNo", context).val()};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data, returnCallback:root.tjurCallback});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	// 문서보관함 레이어
	module.openSmsDocuInven = function(){
		var url = "csm/layer/layerSmsDocuInven";
		var title = messages.smsDocuInvenTitle;
		var css = {width : 480, height : 540};
		layer.init({contextPath:options.contextPath, parentModule:root, returnCallback:root.docuCallback});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	// 문서보관함 선택 콜백함수
	module.docuCallback = function(result){
		if(result !== undefined && result !== null){
			$("textarea.sms[name='procCtnt']", context).val(result);			
		}
	};
	// 이메일 미리보기 레이어
	module.openPreviewEmail = function(){
		var url = "csm/layer/layerPreviewEmail";
		var title = messages.previewEmailTitle;
		var css = {width : 857, height : 551};
		var data = {
				title: $("input.sendEmail[name='title']", context).val(),
				email: $("#email", context).val(),
				procCtnt : $("textarea.sendEmail[name='procCtnt']", context).val()
		};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	return module;
}(window.cnslLayer || {}, $));