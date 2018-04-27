var adjust = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var urls = {
			cards 		: "/cards/",
			cardsSeq 	: "/cards/seq/",
			duplCheck	: "/cards/dupl/",
			except 		: "/cards/adjust/except/",
			cancel 		: "/cards/adjust/cancel/"
	};
	var forms;
	var context;
	var contextPath;
	
	/**
	 * public
	 */
	// 초기화
	module.init = function(options) {
		forms = options.forms || "";
		context = options.context || "";
		contextPath = options.contextPath || "";
	};
	
	// 카드내역 조회
	module.getCardHistoryList = function() {
		var cardno = $("#cardno").val();
		rest.get({
			url : contextPath + urls.cards + cardno,
			data : $("#cardFrm").serializeObject(),
			success : function(result) {
				if(result) {
					var html = tmpl("tmpl-historyList", result);
					$("#cardHistoryContent").html(html);
					toggleList();
				}
			}
		});
	};
	
	// 카드내역 상세보기 조회
	module.getCardHistoryDetail = function(seq) {
		rest.get({
			url : contextPath + urls.cardsSeq + seq,
			success : function(result) {
				$("#cardHistoryContent").find("tr:even").each(function(i){
					if($(this).data("seq") == seq) {
						root.cardDetailBind($(this).next("tr"), result);
						return false;
					}
				});
			}
		});
	};
	
	// 카드상세내역 데이터 바인딩
	module.cardDetailBind = function($obj, result) {
		$obj.find(".apprno").text(		result.apprno);
		$obj.find(".appramt").text(		common.formatCurrency(result.appramt) + messages.get("currency"));
		$obj.find(".mccname").text(		common.nullToSpace(result.mccname));
		$obj.find(".vat").text(			common.formatCurrency(result.vat) + messages.get("currency"));
		$obj.find(".merchaddr").text(	common.nullToSpace(result.merchaddr));
		$obj.find(".apprPerson").text(	common.nullToSpace(result.apprPerson));
		$obj.find(".apprDate").text(	common.nullToSpace(result.apprDate));
		$obj.find(".apprComments").text(common.nullToSpace(result.apprComments));
	};
	
	// 중복 검사
	module.duplCheck = function() {
		rest.get({
			url : contextPath + urls.duplCheck,
			data : {
				cardno : $(".selected-row").find(":hidden[name='cardno']").val(),
				apprno : $(".selected-row").find(":hidden[name='apprno']").val()
			},
			success : function(result, status, xhr) {
				if(result > 0) {
					$("#alertMsg").text(messages.get("duplAdjust"));
					$("body").on("popup", "open");
				} else {
					location.href = contextPath + "/detail?seq=" + $(".selected-row").data("seq");
				}
			}
		});
	};
	
	// 정산제외 처리
	module.except = function() {
		rest.put({
			url : contextPath + urls.except,
			data : {
				exceptCode : "C" ,
							//$(":radio[name='excptCode']:checked").val(), 
							//팝업으로 사유선택 -> 정산취소 하나만 고정, 정산 취소 코드값으로 고정.
				seq : Number($(".selected-row").data("seq"))
			},
			success : function(result, status, xhr) {
				$("#popAlert").popup({
					afterclose : root.getCardHistoryList
				});
				$("#alertMsg").text(messages.get("excptOk"));
				// 이전 팝업 종료를 위한 delay
				setTimeout(function(){
					$("#popAlert").popup("open");
				}, 250);
			},
			error : function(xhr, status, err) {
				$("#popAlert").popup({
					afterclose : undefined
				});
				$("#alertMsg").text(xhr.responseJSON.errorMessage);
				// 이전 팝업 종료를 위한 delay
				setTimeout(function(){
					$("#popAlert").popup("open");
				}, 250);
			}
		});
	};
	
	// 정산취소 처리
	module.cancel = function() {
		rest.put({
			url : contextPath + urls.cancel,
			data : {
				exceptCode : $(":radio[name='excptCode']:checked").val(),
				seq : Number($(".selected-row").data("seq"))
			},
			success : function(result, status, xhr) {
				$("#popAlert").popup({
					afterclose : root.getCardHistoryList
				});
				$("#alertMsg").text(messages.get("cancelOk"));
				// 이전 팝업 종료를 위한 delay
				setTimeout(function(){
					$("#popAlert").popup("open");
		        }, 250);
			},
			error : function(xhr, status, err) {
				$("#popAlert").popup({
					afterclose : undefined
				});
				$("#alertMsg").text(xhr.responseJSON.errorMessage);
				// 이전 팝업 종료를 위한 delay
				setTimeout(function(){
					$("#popAlert").popup("open");
		        }, 250);
			}
		});
	};
	
	return module;
}(window.adjust || {}, $));