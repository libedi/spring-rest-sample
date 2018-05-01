var rmsCnslMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {
		searchChrgRcptAccList : "/rmsCnslMng/search/chrgRcptAccList",
		searchChrgRcptMonList : "/rmsCnslMng/search/chrgRcptMonList",
		searchNpayCntList : "/rmsCnslMng/search/npayCntList",
		searchNpayAmtDetail : "/rmsCnslMng/search/npayAmtDetail",
		searchRmBlkReliefList : "/rmsCnslMng/search/rmBlkReliefList",
		saveRmRelief : "/rmsCnslMng/save/rmRelief",
		saveRmBlk : "/rmsCnslMng/save/rmBlk",
		searchFraudBlkList : "/rmsCnslMng/search/fraudBlkList",
		saveFraudRelief : "/rmsCnslMng/save/fraudRelief",
		searchCpBLInqList : "/rmsCnslMng/search/cpBLInqList",
		searchRmChangeHisList : "/rmsCnslMng/search/rmChangeHisList"
	};
	var messages;
	var forms;
	var context;	//jquery selector context
	
	/**
	 * 테이블 헤더 고정
	 * ex) fixTableHeader(바디스크롤러);
	 * ex) fixTableHeader(바디스크롤러, {bodyHeight: "바디높이"});
	 * @author Sunghee Park
	 */
	var fixTableHeader = function(elementSet, options) {
		$(elementSet).each(function() {
			var $bodyScroll = $(this);
			options = options || {};
			
			var $bodyTable = $bodyScroll.find("table");
			
			if (!$bodyTable[0].style.width) {
				$bodyTable[0].style.width = "100%";
			}
			
			var $headTable = $bodyTable.clone();
			
			$headTable.find("tbody").remove();
			$bodyTable.find("thead").remove();
			
			var $headScroll = $("<div>").addClass("grid-head").css({overflow: "hidden"}).append(
				$("<div>").css({cssFloat: "left"}).append($headTable)
			);
			
			$bodyScroll.css({overflow: "auto"}).before($headScroll).on("scroll", function() {
				$headScroll.scrollLeft($(this).scrollLeft());
			});
			
			$bodyTable.on("resize", function() {
				if ($bodyScroll.height() === $bodyScroll.prop("scrollHeight")) {
					$headScroll.children("div").css({paddingRight: "0px"});
				} else {
					$headScroll.children("div").css({paddingRight: "16px"});
				}
			}).trigger("resize");
			
			if (options.bodyHeight) {
				$bodyScroll.css({height: options.bodyHeight});
			}
			
			if ($bodyScroll.hasClass("tbl-grid-box")) {
				$bodyScroll.removeClass("tbl-grid-box");
				$("<div>").addClass("tbl-grid-box").insertAfter($bodyScroll).append($headScroll, $bodyScroll);
			}
		});
	};
	
	/**
	 * public 
	 */	
	module.init = function(opts) {
		options.contextPath = opts.contextPath;
		forms = opts.forms;
		messages = opts.messages;
		context = opts.context;
		
		// 그리드 헤더 고정
		fixTableHeader("#tab3-1 .tbl-grid-box", context);
		fixTableHeader("#tab3-4 .tbl-grid-box", context);
	};
	module.getOptions = function() {
		return root.options;
	};
	
	module.parseYmd = function(str) {
		var arr = str.replace(/[\.-]/g, "").match(/^([0-9]{4})([0-9]{2})([0-9]{2})/);
		if (arr) {
			arr = $.map(arr, Number);
			return new Date(arr[1], arr[2]-1, arr[3]);
		}
		return null;
	};
	
	module.extractForm = function(form) {
		var data = {};
		$(form).find(":input:not(:button)").each(function() {
			var $element = $(this);
			if (!($element.is(":radio") || $element.is(":checkbox")) || this.checked) {
				data[this.name] = $element.val();
			}
		});
		return data;
	};
	
	// 거래완료 탭 조회 결과에 대한 RM쪽 처리
	module.handleMainSearchResult = function(result) {
		// RM탭의 활성화된 하위탭 조회
		var tab3Hash = $("#tab3 > ul.tab-head a.tab-selected", context).attr("href");
		tab3Hash = tab3Hash.substring(tab3Hash.indexOf("-") + 1);
		searchTab3(tab3Hash);
	};
	
	// 청구/수납(누적) 탭 리스트 조회
	module.searchChrgRcptAccList = function() {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchChrgRcptAccList,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: function(result) {
				if (result && result.length > 0) {
					$("#chrgRcptAccResult", context).html(tmpl("tmpl-chrgRcptAccList", result));
				} else {
					$("#chrgRcptAccResult", context).html("<tr><td colspan='7' align='center'>" + messages.noResult + "</td></tr>");
				}
			}
		});
	};
	
	// 청구/수납(월별) 탭 리스트 조회
	module.searchChrgRcptMonList = function() {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchChrgRcptMonList,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: function(result) {
				if (result && result.length > 0) {
					$("#chrgRcptMonResult", context).html(tmpl("tmpl-chrgRcptMonList", result));
					$("#chrgRcptMonTotal", context).html(tmpl("tmpl-chrgRcptMonTotal", result));
				} else {
					$("#chrgRcptMonResult", context).html("<tr><td colspan='8' align='center'>" + messages.noResult + "</td></tr>");
					$("#chrgRcptMonTotal", context).empty();
				}
			}
		});
	};
	
	// 미납횟수(회차별) 탭 리스트 조회
	module.searchNpayCntList = function() {
		var params = module.extractForm($("#"+ forms.searchForm, context));
		
		impay.sendGet({
			requestUri: options.contextPath + uris.searchNpayCntList,
			data: params,
			dataType: "json",
			successCallback: function(result) {
				var $tbody = $("#npayCntListResult", context);
				
				$("#npayAmtDetailContainer", context).hide();
				
				if (result && result.length > 0) {
					$tbody.html(tmpl("tmpl-npayCntList", result));
					$tbody.find("td[data-npayCnt-mnts]").on("click", function() {
						module.searchNpayAmtDetail({
							payrSeq: params.payrSeq,
							mphnNo: params.custMphnNo,
							mnts: $(this).attr("data-npayCnt-mnts")
						});
					});
				} else {
					$tbody.html("<tr><td colspan='7' align='center'>" + messages.noResult + "</td></tr>");
				}
			}
		});
	};
	
	// 미납횟수(회차별) 탭 상세 조회
	module.searchNpayAmtDetail = function(params) {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchNpayAmtDetail,
			data: params,
			dataType: "json",
			successCallback: function(result) {
				if (result) {
					var $container = $("#npayAmtDetailContainer", context).show();
					$container.find("#npayAmtDetail_billAmt").html(Common.formatMoney(result.billAmt));
					$container.find("#npayAmtDetail_rcptAmt").html(Common.formatMoney(result.rcptAmt));
					$container.find("#npayAmtDetail_npayAmt").html(Common.formatMoney(result.npayAmt));
				}
			}
		});
	};
	
	// RM 차단/해제 등록 탭 결과 초기화
	module.clearRmBlkReliefResult = function(message) {
		var $tbody = $("#rmBlkReliefResult", context).empty();
		if (message) {
			$tbody.html("<tr><td colspan='14' align='center'>" + message + "</td></tr>");
		}
		$("#tab3-1 .tbl-grid-box table", context).trigger("resize");
	};
	
	// RM 차단/해제 등록 탭 리스트 조회
	module.searchRmBlkReliefList = function() {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchRmBlkReliefList,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: function(result) {
				if (result && result.length > 0) {
					$("#rmBlkReliefResult", context).html(tmpl("tmpl-rmBlkReliefList", result));
					$("#tab3-1 .tbl-grid-box table", context).trigger("resize");
				} else {
					module.clearRmBlkReliefResult(messages.noResult);
				}
			}
		});
	};
	
	// RM 차단/해제 등록 탭 저장
	module.saveRmBlkRelief = function(saveOptions){
		saveOptions = saveOptions || {};
		
		var searchParams = module.extractForm($("#"+ forms.searchForm, context));
		var saveParams = module.extractForm($("#"+ forms.rmBlkReliefRegForm, context));
		
		saveParams.payrSeq = searchParams.payrSeq;
		saveParams.mphnNo  = searchParams.custMphnNo;
		
		var uri;
		if (saveParams.applyStatus === "R") {
			uri = uris.saveRmRelief;
		} else if (saveParams.applyStatus === "B") {
			uri = uris.saveRmBlk;
		} else {
			alert('applyStatus is invalid!');
			return false;
		}
		
		impay.sendPost({
			requestUri: options.contextPath + uri,
			data: saveParams,
			dataType: "json",
			successCallback: function(result){
				if (result.success === true) {
					alert(messages.saveOk);
					module.searchRmBlkReliefList();
				} else {
					if (result.fieldError && result.fieldError.length && saveOptions.fieldErrorHandler) {
						if (saveOptions.fieldErrorHandler(result)) return;
					}
					
					alert(result.message || messages.error);
				}
			}
		});
	};
	
	// 불량고객 해제 등록 탭 결과 초기화
	module.clearFraudBlkResult = function(message) {
		var $tbody = $("#fraudBlkResult", context).empty();
		if (message) {
			$tbody.html("<tr><td colspan='10' align='center'>" + message + "</td></tr>");
		}
	};
	
	// 불량고객 해제 등록 탭 리스트 조회
	module.searchFraudBlkList = function() {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchFraudBlkList,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: function(result) {
				if (result && result.length > 0) {
					module.fraudBlkCount = result.length;
					$("#fraudBlkResult", context).html(tmpl("tmpl-fraudBlkList", result));
				} else {
					module.fraudBlkCount = 0;
					module.clearFraudBlkResult(messages.noResult);
				}
			}
		});
	};
	
	// 불량고객 해제 등록 탭 저장
	module.saveFraudRelief = function(saveOptions){
		saveOptions = saveOptions || {};
		
		var searchParams = module.extractForm($("#"+ forms.searchForm, context));
		var saveParams = module.extractForm($("#"+ forms.fraudReliefRegForm, context));
		
		saveParams.payrSeq = searchParams.payrSeq;
		
		impay.sendPost({
			requestUri: options.contextPath + uris.saveFraudRelief,
			data: saveParams,
			dataType: "json",
			successCallback: function(result){
				if (result.success === true) {
					alert(messages.saveOk);
					module.searchFraudBlkList();
				} else {
					if (result.fieldError && result.fieldError.length && saveOptions.fieldErrorHandler) {
						if (saveOptions.fieldErrorHandler(result)) return;
					}
					
					alert(result.message || messages.error);
				}
			}
		});
	};
	
	// 가맹점B/L 조회 탭 결과 초기화
	module.clearCpBLInqResult = function(message) {
		var $tbody = $("#cpBLInqResult", context).empty();
		if (message) {
			$tbody.html("<tr><td colspan='8' align='center'>" + message + "</td></tr>");
		}
	};
	
	// 가맹점B/L 조회 탭 리스트 조회
	module.searchCpBLInqList = function() {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchCpBLInqList,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: function(result) {
				if (result && result.length > 0) {
					$("#cpBLInqResult", context).html(tmpl("tmpl-cpBLInqList", result));
				} else {
					module.clearCpBLInqResult(messages.noResult);
				}
			}
		});
	};
	
	// RM 변경이력 탭 결과 초기화
	module.clearRmChangeHisResult = function(message) {
		var $tbody = $("#rmChangeHisResult", context).empty();
		if (message) {
			$tbody.html("<tr><td colspan='9' align='center'>" + message + "</td></tr>");
		}
		$("#tab3-4 .tbl-grid-box table", context).trigger("resize");
	};
	
	// RM 변경이력 탭 리스트 조회
	module.searchRmChangeHisList = function() {
		impay.sendGet({
			requestUri: options.contextPath + uris.searchRmChangeHisList,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: function(result) {
				if (result && result.length > 0) {
					$("#rmChangeHisResult", context).html(tmpl("tmpl-rmChangeHisList", result));
					$("#tab3-4 .tbl-grid-box table", context).trigger("resize");
				} else {
					module.clearRmChangeHisResult(messages.noResult);
				}
			}
		});
	};
	
	return module;
}(window.rmsCnslMng || {}, $));