var cnslMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {
			custInfoSearch : 	"/cnslMng/search/custInfo",
			totTrdCmplSearch : 	"/cnslMng/search/totalTrdCmplList",
			trdCmplSearch : 	"/cnslMng/search/trdCmplList",
			cnslDtlSearch : 	"/cnslMng/search/cnslDtlList",
			trdCmplCount :		"/cnslMng/search/trdCmplListCount",
			excelDown :			"/cnslMng/search/trdCmplList/excelDown",
			trdSearch : 		"/cnslMng/search/trdCmpl",
			cnslCtntSearch : 	"/cnslMng/search/cnslCtnt",
			saveCnslCtnt : 		"/cnslMng/save/cnslCtnt",
			updateCnslCtnt : 	"/cnslMng/update/cnslCtnt",
			tjurProcCtnt :		"/cnslMng/tjurProc/procCtnt",
			cnslChgSearch :		"/cnslMng/search/cnslChgList",
			trdTrySearch : 		"/cnslMng/search/trdTryList",
			trdTryCount : 		"/cnslMng/search/trdTryListCount",
			trdTryExcelDown : 	"/cnslMng/search/trdTryList/excelDown",
			trdTrySelectSearch :"/cnslMng/search/trdTry",
			payItcptSearch : 	"/cnslMng/search/payItcptList",
			sendNotiRslt :		"/cnslMng/send/notiRslt",
			notiRsltSearch :	"/cnslMng/search/notiRslt"
	};
	var messages;
	var forms;
	var context;	//jquery selector context
	
	var payCustInfo;		// 결제고객정보
	var chgSearchParams;	// 관리탭 파라미터
	
	var rcptNo = "";		// 수정시 로우선택을 위한 파라미터
	
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
	
	// tab 선택 이벤트
	module.tabSelect = function(mainTabIdx, subTabIdx){
		$("#" + mainTabIdx + " > ul.tab-head li > a", context).removeClass("tab-selected");
		$("#" + mainTabIdx + " > ul.tab-head li > a", context).each(function(){
			if($(this).attr("href") === ("#" + subTabIdx)){
				$(this).addClass("tab-selected");
				return false;
			}
		});
		$("#" + mainTabIdx + " > div:not(.tabBar)", context).hide();
		$("#" + subTabIdx, context).show();
	};
	
	// 화면을 부드럽게 이동
	module.moveDisplay = function(target){
		var $target = $("a[name='" + target + "']", context);
		$("html, body").animate({
			scrollTop : $target.offset().top
		});
	};
	// 로우 파라미터 초기화
	module.setClearRowParam = function(){
		rcptNo = "";
	};
	// 로우 파라미터 반환
	module.getRowParam = function(){
		return {
			rcptNo : rcptNo
		}
	};
	// 페이지 입력 필드 초기화
	module.setFieldInit = function(){
		// 상담내용 필드 초기화
		$(".rcpt-info span", context).empty();
		$("#tempRcptNo", context).val("");
		$(".trade-info span", context).empty();
		$(".trade-info", context).hide();
		$("#tempTrdNo", context).val("");
		$("#cnslClfUprCd > option", context).each(function(){
			if($(this).val() === ""){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#cnslEvntCd > option", context).each(function(){
			if($(this).val() === ""){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#cnslTypCd > option", context).each(function(){
			if($(this).val() === "TKTK01"){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#rcptMthdCd > option", context).each(function(){
			if($(this).val() === "RKRK01"){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#custTypFlg > option", context).each(function(){
			if($(this).val() === "B"){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#cnslCtnt", context).val("");
		$("#textCountCnslCtnt", context).text(0);
		$("#saveCnslBtn", context).show();
		$("#updateCnslBtn", context).hide();
		// 처리내용 필드 초기화
		$("#tempProcTrdNo", context).val("");
		$("#tempProcRcptNo", context).val("");
		$("#tempProcEntpId", context).val("");
		$("input:radio[name='procYn']", context).removeAttr("checked");
		$("input:radio[name='procYn'][value='N']", context).prop("checked", true);
		root.disabledTjurBtn("N");
		$("#infoProcDt", context).text("");
		$("#infoLastChgDt", context).text("");
		$("#infoLastChgr", context).text("");
		$("#procDt", context).val("");
		$("#lastChgDt", context).val("");
		$("#lastChgr", context).val("");
		$("#procCtnt", context).val("");
		$("#tjurClfFlg", context).html(messages.tjurBsOptions);
		$("#tjurClfFlg > option", context).each(function(){
			if($(this).val() === ""){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#tjurCtnt", context).text("");
		$("#tjurYn", context).val("N");
		$("#tjurCd", context).val("");
		// 결과통보 필드 초기화
		$("textarea.sms[name='procCtnt']", context).val("");
		$("textarea.sendEmail[name='procCtnt']", context).val("");
		$("#email", context).val("");
		$("input.sendEmail[name='title']", context).val("");
	};
	
	// 고객정보 초기화
	module.clearCustInfo = function(){
		$("#payMphnId", context).val("");
		$("#tempPayMphnId", context).val("");
		$("#payrSeq", context).val("");
		$("#tempPayrSeq", context).val("");
		$(".custNo", context).empty();
		$(".mphnNoCust", context).empty();
		$("#sndRsltMphnNo", context).val("");
	};
	
	// 고객정보 조회
	module.custInfoSearch = function(){
		// 조회구분값 trim
		impay.sendGet({
			requestUri: options.contextPath + uris.custInfoSearch,
			async : false,
			data: {
				custMphnNo : $("#custMphnNo", context).val(),
				payMphnId : $("#payMphnId", context).val()
			},
			dataType: "json",
			successCallback: root.setCustInfo
		});
	};
	
	// 고객정보 설정
	module.setCustInfo = function(result){
		// 로우 선택값 초기화
		root.setClearRowParam();
		// 고객번호 셀렉트 셋팅
		var html = "<option value=''>" + messages.noSelect + "</option>";
		if(Common.getIsNull(result.mphnList) === false){
			for(var i=0; i<result.mphnList.length; i++){
				html += "<option value='" + result.mphnList[i].payMphnId + "'>" + result.mphnList[i].payrSeq + " (" + result.mphnList[i].commcClfNm + ")</option>";
			}
		}
		$("#selPayrSeq", context).empty().append(html);
		$("#tempMphnNo", context).val($("#custMphnNo", context).val());
		// 결제고객정보
		if(Common.getIsNull(result.payMphnInfo) === false){
			payCustInfo = result.payMphnInfo;
			$("#selPayrSeq > option", context).each(function(){
				if($(this).val() === payCustInfo.payMphnId){
					$(this).prop("selected", true);
					return false;
				}
			});
			$("#payMphnId", context).val(payCustInfo.payMphnId);		// 조회용 결제폰정보
			$("#tempPayMphnId", context).val(payCustInfo.payMphnId);	// 상담내역 저장용 결제폰정보
			$("#payrSeq", context).val(payCustInfo.payrSeq);			// 
			$("#tempPayrSeq", context).val(payCustInfo.payrSeq);
			$(".custNo", context).text(payCustInfo.payrSeq);
			$(".mphnNoCust", context).text(utils.hiddenToTel(payCustInfo.mphnNo));
			$("#sndRsltMphnNo", context).val(payCustInfo.mphnNo);
		} else {
			payCustInfo = null;
			$("#selPayrSeq > option", context).each(function(){
				if($(this).val() === ""){
					$(this).prop("selected", true);
					return false;
				}
			});
			root.clearCustInfo();
		}
	};
	
	// 거래완료 탭 조회
	module.totTrdCmplSearch = function(){
		utils.applyTrim(forms.searchForm);
		// 조회구분값 trim
		impay.sendGet({
			requestUri: options.contextPath + uris.totTrdCmplSearch,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: root.totTrdCmplBindList
		});
	};
	
	// 거래완료 탭 결과 출력
	module.totTrdCmplBindList = function(result){
		// 고객정보 설정
		root.setCustInfo(result);
		// 입력필드 초기화
		root.setFieldInit();
		// 거래완료 탭 활성화
		$("#tab1 > ul.tab-head a", context).each(function(index){
			var tabId = $(this).attr("href");
			tabId = tabId.substring(1);
			
			if(index === 0){
				$(this).addClass("tab-selected");
				$(tabId).show();
			} else {
				$(this).removeClass("tab-selected");
				$(tabId).hide();
			}
		});
		// 거래완료건 리스트
		root.trdCmplBindList(result.tradeList);
		// 전체 상담이력 리스트
		root.cnslDtlBindList(result.cnslDetailList);
		// 전체 탭 노출
		$(".tab-conts", context).show();
		$("#noTab", context).hide();
		// 조회버튼 노출
		$("#rcptBtn", context).show();
		
		// 거래완료 탭 조회 결과에 대한 RM쪽 처리
		rmsCnslMng.handleMainSearchResult(result);
	};
	
	// 거래완료 리스트 조회
	module.trdCmplSearch = function(){
		impay.sendGet({
			requestUri: options.contextPath + uris.trdCmplSearch,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: root.trdCmplBindList
		});
	};
	
	// 거래완료 리스트 결과 출력
	module.trdCmplBindList = function(result){
		var htmlResult;
		var pageDisplay = "none";
		
		if(Common.getIsNull(result) === false && result.total > 0){
			htmlResult = tmpl("tmpl-trdCpmlList", result.content);
			$("#trdCmplPaginationInner", context).paging({
	        	totalCount : result.total,
	    		pageSize: $("#rowCount", context).val(),
	    		pageNo: $("#pageIndex", context).val(),
	    		onSelectPage : root.goPageTrdCmpl
	        });
			pageDisplay = "block";
			$("#excelDown", context).css("display", "inline-block");
			$("#popReceipt", context).css("display", "inline-block");
		} else {
			htmlResult = "<tr><td colspan='13' class='het50 txt-center'>" + 
					"<a href='javascript:cnslMng.viewCnslCtnt();'>" + messages.noResult + " <strong>" + utils.hiddenToTel($("#custMphnNo", context).val()) + messages.clickReception +
					"</strong></a></td></tr>";
			$("#trdCmplPaginationInner", context).empty();
			$("#excelDown", context).hide();
			$("#popReceipt", context).hide();
		}
		$("#allChk", context).removeAttr("checked");
		$("#trdCmplResult", context).html(htmlResult);
		$("#trdCmplPaginationWrap", context).css({"display": pageDisplay});
	};
	
	// 거래완료 리스트 페이징
	module.goPageTrdCmpl = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.trdCmplSearch();
	};
	
	// 상담내역 입력으로 이동
	module.viewCnslCtnt = function(){
		root.setFieldInit();
		root.moveDisplay("counsel-view");
	};
	
	// 거래완료 단건 조회
	module.trdSearch = function(trdNo){
		chgSearchParams = "&trdNos=" + trdNo;
		impay.sendGet({
			requestUri: options.contextPath + uris.trdSearch,
			data: $("#"+ forms.searchForm, context).serialize() + chgSearchParams,
			dataType: "json",
			successCallback: root.trdSearchCallback
		});
	};
	
	// 거래완료 단건 조회 콜백함수
	module.trdSearchCallback = function(result){
		if(result.success === true){
			// 입력필드 초기화
			root.setFieldInit();
			// 거래정보 값 셋팅
			var trd = result.result.tradeList.content[0];
			$("#infoTrdTypNm", context).text(trd.trdTypNm);
			$("#infoTrdDt", context).text(trd.trdDd.substring(0,4) + "." + trd.trdDd.substring(4,6) + "." + trd.trdDd.substring(6));
			$("#infoCommcClfNm", context).text(trd.commcClfNm);
			var cnclDd = "";
			if(Common.getIsNull(trd.cnclDd) === false){
				cnclDd = trd.cnclDd.substring(0,4) + "." + trd.cnclDd.substring(4,6) + "." + trd.cnclDd.substring(6)
			}
			$("#infoCnclDt", context).text(cnclDd);
			$("#infoPayAmt", context).text(Common.formatMoney(trd.payAmt));
			$("#infoAvlCncl", context).text(trd.avlCncl);
			$("#infoPaySvcNm", context).text(trd.paySvcNm);
			$("#infoTrdNo", context).text(trd.trdNo);
			$("#infoGodsNm", context).text(trd.godsNm);
			$("#infoTelNo", context).text(StringUtil.nvl(trd.telNo, ""));
			$("#tempTrdNo", context).val(trd.trdNo);
			$(".trade-info", context).show();
			// 관리탭 상담이력 리스트 출력
			root.cnslChgBindList(result.result.cnslDetailList);
		} else {
			$(".trade-info", context).hide();
			alert(messages.error);
		}
	};
	
	// 전체상담이력 리스트 조회
	module.cnslDtlSearch = function(){
		// 조회구분값 trim
		impay.sendGet({
			requestUri: options.contextPath + uris.cnslDtlSearch,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: root.cnslDtlBindList
		});
	};
	
	// 전체상담이력 리스트 결과 출력
	module.cnslDtlBindList = function(result){
		var htmlResult = '';
		if(Common.getIsNull(result) === true || result.total === 0){
			$(".cnsl-info", context).hide();
		} else {
			htmlResult = tmpl("tmpl-cnslDtlList", result.content);
			$("#cnslDtlPaginationInner", context).paging({
	        	totalCount : result.total,
	    		pageSize: $("#rowCount", context).val(),
	    		pageNo: $("#pageIndex", context).val(),
	    		onSelectPage : root.goPageCnslDtl
	        });
			
			$(".cnsl-info", context).show();
		}
		$("#cnslDtlResult", context).html(htmlResult);
	};
	
	// 전체상담이력 리스트 페이징
	module.goPageCnslDtl = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.cnslDtlSearch();
	};
	
	// 거래완료 리스트 엑셀 다운로드
	module.excelDown = function(){
		if(!confirm(messages.excelDown)) return;
		var params = $("#"+ forms.searchForm, context).serialize();
		
		impay.sendGet({
			requestUri: options.contextPath + uris.trdCmplCount,
			data: params,
			successCallback: function(result){
				if(result > 10000){
					alert(messages.excelDownLimit);
					return;
				}
				$.fileDownload(options.contextPath + uris.excelDown + "?" + params)
			   	 	.done(function () { alert("File download a success!"); })
			   	 	.fail(function () { alert("File download failed!"); });
			},
			errorCallback: function(err){
				alert(err);
			}
		});	
		return false;
	};
	
	// 상담내용 조회
	module.cnslCtntSearch = function(id, hash){
		$("#pageIndex", context).val("1");
		chgSearchParams = "&rcptNo=" + id;
		impay.sendGet({
			requestUri: options.contextPath + uris.cnslCtntSearch,
			data: $("#"+ forms.searchForm, context).serialize() + chgSearchParams,
			dataType: "json",
			successCallback: root.cnslCtntBind
		});
		window.setTimeout(function(){
			root.moveDisplay(hash);
		}, 500);
	};
	
	// 상담내용, 처리내용 조회 콜백함수
	module.cnslCtntBind = function(result){
		root.setFieldInit();
		// 1. 상담내용 셋팅
		var isUpdate = false;
		// 접수정보 노출
		if(Common.getIsNull(result) === false){
			var regDts = result.regDt.split(" ");
			$("#infoRcptDt", context).text(regDts[0]);
			$("#infoRegr", context).text(result.regr);
			$("#infoRegTime", context).text(regDts[1]);
			$("#infoRcptNo", context).text(result.rcptNo);
			$("#tempRcptNo", context).val(result.rcptNo);
			isUpdate = true;
		}
		// 결제정보 노출
		if(Common.getIsNull(result.tradeModel) === false && Common.getIsNull(result.tradeModel.trdNo) === false){
			var trd = result.tradeModel;
			$("#infoTrdTypNm", context).text(trd.trdTypNm);
			$("#infoTrdDt", context).text(trd.trdDt);
			$("#infoCommcClfNm", context).text(trd.commcClfNm);
			$("#infoCnclDt", context).text(StringUtil.nvl(trd.cnclDt, ""));
			$("#infoPayAmt", context).text(Common.formatMoney(trd.payAmt));
			$("#infoAvlCncl", context).text(trd.avlCncl);
			$("#infoPaySvcNm", context).text(trd.paySvcNm);
			$("#infoTrdNo", context).text(trd.trdNo);
			$("#infoGodsNm", context).text(trd.godsNm);
			$("#infoTelNo", context).text(StringUtil.nvl(trd.telNo, ""));
			$("#tempTrdNo", context).val(trd.trdNo);
			$(".trade-info", context).show();
		}
		// 상담내용 출력
		$("#cnslClfUprCd > option", context).each(function(){
			if($(this).val() == result.cnslClfUprCd){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#cnslEvntCd > option", context).each(function(){
			if($(this).val() == result.cnslEvntCd){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#cnslTypCd > option", context).each(function(){
			if($(this).val() == result.cnslTypCd){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#rcptMthdCd > option", context).each(function(){
			if($(this).val() == result.rcptMthdCd){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#custTypFlg > option", context).each(function(){
			if($(this).val() == result.custTypFlg){
				$(this).prop("selected", true);
				return false;
			}
		});
		$("#cnslCtnt", context).val(Common.nvl(result.cnslCtnt,"").replace(/(<br\/>|<br>)/g, "\r\n"));
		$("#textCountCnslCtnt", context).text(Common.getByteString(Common.nvl(result.cnslCtnt,"").replace(/(<br\/>|<br>)/g, "\r\n")));
		
		if(isUpdate){
			$("#saveCnslBtn", context).hide();
			$("#updateCnslBtn", context).show();
		} else{
			$("#saveCnslBtn", context).show();
			$("#updateCnslBtn", context).hide();
		}
		
		// 2. 처리내용 셋팅
		$("#tempProcRcptNo", context).val(result.rcptNo);
		$("#tempProcEntpId", context).val(result.entpId);
		$("#tempProcCpCd", context).val(result.cpCd);
		if(Common.getIsNull(result.procDt) === false){
			$("input:radio[name='procYn']", context).removeAttr("checked");
			$("input:radio[name='procYn'][value='" + result.procYn + "']", context).prop("checked", true);
			root.disabledTjurBtn(result.procYn);
			
			$("#infoProcDt", context).text(result.procDt);
			$("#infoLastChgDt", context).text(result.lastChgDt.substring(0, result.lastChgDt.indexOf(" ")));
			$("#infoLastChgr", context).text(result.lastChgr);
			$("#procDt", context).val(result.procDt);
			$("#lastChgDt", context).val(result.lastChgDt.substring(0, result.lastChgDt.indexOf(" ")));
			$("#lastChgr", context).val(result.lastChgr);
			$("#procCtnt", context).val(StringUtil.nvl(result.procCtnt, "").replace(/(<br\/>|<br>)/g, "\r\n"));
		}
		if(Common.getIsNull(result.cnslTjurCd) === false){
			$("#tjurCtnt", context).text(result.tjurProcDt + " " + result.cnslTjurNm + messages.tjurOk);
			$("#tjurYn", context).val("Y");
			$("#tjurCd", context).val(result.cnslTjurCd);
		} else {
			$("#tjurCtnt", context).text("");
			$("#tjurYn", context).val("N");
			$("#tjurCd", context).val("");
		}
		// 상품상담건이 아닌경우 가맹점 이관 미노출
		if(Common.getIsNull(result.tradeModel) === false && Common.getIsNull(result.tradeModel.trdNo) === false){
			$("#tempProcTrdNo", context).val(result.tradeModel.trdNo);
			$("#tjurClfFlg", context).html(messages.tjurCpOptions);
		} else {
			$("#tjurClfFlg", context).html(messages.tjurBsOptions);
		}
		$("#tjurClfFlg > option", context).each(function(){
			var tjurClfFlg = Common.getIsNull(result.cnslTjurCd) === true ? "" : (result.cnslTjurCd === "C" ? "C" : "B");
			if($(this).val() === tjurClfFlg){
				$(this).prop("selected", true);
				return false;
			}
		});
		
		// 3. 상담내용 변경이력 리스트 출력
		if($("#chgListYn", context).val() == "Y"){
			root.cnslChgBindList(result.cnslChgList);
		}
	};
	
	// 상담내용 임시저장
	module.tempSaveCnsl = function(){
		var tempCnsl = root.makeTempData("counsel-view");
		localStorage.setItem("tempCnsl", JSON.stringify(tempCnsl));
		alert(messages.tempSaveOk);
	};
	
	// 상담내용 내용삭제
	module.tempDeleteCnsl = function(tempCnsl){
		root.setFieldInit();
		if(tempCnsl !== null){
			localStorage.removeItem("tempCnsl");
		}
		alert(messages.deleteOk);
	};
	
	// 처리내용 내용삭제
	module.tempDeleteProc = function(tempCnsl){
		// 처리내용 필드 초기화
		$("input:radio[name='procYn']", context).removeAttr("checked");
		$("input:radio[name='procYn'][value='N']", context).prop("checked", true);
		root.disabledTjurBtn("N");
		$("#procCtnt", context).val("");
		$("#tjurClfFlg > option", context).each(function(){
			if($(this).val() === ""){
				$(this).prop("selected", true);
				return false;
			}
		});
		if(tempCnsl !== null){
			localStorage.removeItem("tempCnsl");
		}
		$("#tjurCd", context).val("");
		alert(messages.deleteOk);
	};
	
	// 상담내용 저장
	module.saveCnslCtnt = function(){
		if(root.checkValidationCnsl() === true){
			impay.sendPost({
				requestUri: options.contextPath + uris.saveCnslCtnt,
				data: $("#"+ forms.cnslForm, context).serialize(),
				dataType: "json",
				successCallback: root.saveCnslCtntCallback
			});
		}
	};
	
	// 상담내용 수정
	module.updateCnslCtnt = function(){
		if(root.checkValidationCnsl() === true){
			impay.sendPost({
				requestUri: options.contextPath + uris.updateCnslCtnt,
				data: $("#"+ forms.cnslForm, context).serialize(),
				dataType: "json",
				successCallback: root.saveCnslCtntCallback
			});
		}
	};
	
	// 상담내용 저장, 수정 필드 유효성 검사
	module.checkValidationCnsl = function(){
		var cnslTypCd = $("#cnslTypCd > option:selected", context).val();
		var rcptMthdCd = $("#rcptMthdCd > option:selected", context).val();
		var cnslCtnt = $.trim($("#cnslCtnt", context).val());
		
		if(cnslTypCd === ""){
			alert(messages.validCnslTypCd);
			$("#cnslTypCd", context).focus();
			return false;
		}
		if(rcptMthdCd === ""){
			alert(messages.validRcptMthdCd);
			$("#rcptMthdCd", context).focus();
			return false;
		}
		if(cnslCtnt === ""){
			alert(messages.validCnslCtnt);
			$("#cnslCtnt", context).focus();
			return false;
		}
		return true;
	};
	
	// 상담내용 저장, 수정 콜백함수
	module.saveCnslCtntCallback = function(result){
		if(result.success === true){
			root.setClearRowParam();
			rcptNo = result.result;		// 저장,수정된 로우 선택 활성화
			localStorage.removeItem("tempCnsl");
			alert(messages.saveOk);
			$("#saveCnslBtn", context).hide();
			$("#updateCnslBtn", context).show();
			$("#pageIndex", context).val("1");
			$("#chgListYn", context).val("Y");
			root.cnslDtlSearch();
			root.cnslCtntSearch(result.result, "rm-view");	// 저장완료 후 다음 탭이동
		} else {
			alert(messages.error);
		}
	};
	
	// 처리내용 임시저장
	module.tempSaveProc = function(){
		var tempCnsl = root.makeTempData("process-view");
		localStorage.setItem("tempCnsl", JSON.stringify(tempCnsl));
		alert(messages.tempSaveOk);
	};
	
	// 임시저장 데이터 생성
	module.makeTempData = function(hash){
		var tempCnsl = {
				tempMphnNo : $("#tempMphnNo", context).val(),
				tempPayMphnId : $("#tempPayMphnId", context).val(),
				tempPayrSeq : $("#tempPayrSeq", context).val(),
				tempRcptNo : $("#tempRcptNo", context).val(),
				tempTrdNo : $("#tempTrdNo", context).val(),
				infoTrdTypNm : $("#infoTrdTypNm", context).text(),
				infoTrdDt : $("#infoTrdDt", context).text(),
				infoCommcClfNm : $("#infoCommcClfNm", context).text(),
				infoCnclDt : $("#infoCnclDt", context).text(),
				infoPayAmt : $("#infoPayAmt", context).text(),
				infoAvlCncl : $("#infoAvlCncl", context).text(),
				infoPaySvcNm : $("#infoPaySvcNm", context).text(),
				infoTrdNo : $("#infoTrdNo", context).text(),
				infoGodsNm : $("#infoGodsNm", context).text(),
				infoTelNo : $("#infoTelNo", context).text(),
				cnslClfUprCd : $("#cnslClfUprCd > option:selected", context).val(),
				cnslEvntCd : $("#cnslEvntCd > option:selected", context).val(),
				cnslTypCd : $("#cnslTypCd > option:selected", context).val(),
				rcptMthdCd : $("#rcptMthdCd > option:selected", context).val(),
				custTypFlg : $("#custTypFlg > option:selected", context).val(),
				cnslCtnt : $("#cnslCtnt", context).val(),
				tempProcRcptNo : $("#tempProcRcptNo", context).val(),
				tempProcTrdNo : $("#tempProcTrdNo", context).val(),
				tempProcEntpId : $("#tempProcEntpId", context).val(),
				tempProcCpCd : $("#tempProcCpCd", context).val(),
				procYn : $("input:radio[name='procYn']:checked", context).val(),
				procDt : $("#procDt", context).val(),
				lastChgDt : $("#lastChgDt", context).val(),
				lastChgr : $("#lastChgr", context).val(),
				tjurClfFlg : $("#tjurClfFlg > option:selected", context).val(),
				procCtnt : $("#procCtnt", context).val(),
				tjurCtnt : $("#tjurCtnt", context).text(),
				tjurYn : $("#tjurYn", context).val(),
				tjurCd : $("#tjurCd", context).val(),
				hash : hash
		};
		return tempCnsl;
	};
	
	// 처리내용 수정
	module.updateProcCtnt = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.updateCnslCtnt,
			data: $("#"+ forms.procForm, context).serialize(),
			dataType: "json",
			successCallback: function(result){
				if(result.success === true){
					root.setClearRowParam();
					rcptNo = result.result;		// 저장,수정된 로우 선택 활성화
					localStorage.removeItem("tempCnsl");
					alert(messages.saveOk);
					$("#saveCnslBtn", context).hide();
					$("#updateCnslBtn", context).show();
					$("#pageIndex", context).val("1");
					$("#chgListYn", context).val("Y");
					root.cnslDtlSearch();
					root.cnslCtntSearch(result.result, "manage-view");
				} else {
					alert(messages.error);
				}
			}
		});
	};
	
	// 이관버튼 비활성화
	module.disabledTjurBtn = function(val){
		if(val === "Y"){
			$("#sendTjur", context).prop("disabled", true);
			$("#tjurClfFlg", context).prop("disabled", true);
		} else {
			$("#sendTjur", context).prop("disabled", false);
			$("#tjurClfFlg", context).prop("disabled", false);
		}
	};
	
	// 관리탭 상담이력 리스트 결과 출력
	module.cnslChgBindList = function(result){
		if(result.total > 0){
			var htmlResult = tmpl("tmpl-cnslChgList", result.content);
			$("#cnslChgPaginationInner", context).paging({
	        	totalCount : result.total,
	    		pageSize: $("#rowCount", context).val(),
	    		pageNo: $("#pageIndex", context).val(),
	    		onSelectPage : root.goPageCnslChg
	        });
			$("#cnslChgResult", context).html(htmlResult);
			$("#cnslChgPaginationWrap", context).show();
		} else {
			$("#cnslChgResult", context).html("<tr><td colspan='13' align='center'>" + messages.noResult + "</td></tr>");
			$("#cnslChgPaginationInner", context).empty();
			$("#cnslChgPaginationWrap", context).hide();
		}
	};
	
	// 관리탭 상담이력 리스트 페이징
	module.goPageCnslChg = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.cnslChgSearch();
	};
	
	// 관리탭 상담이력 리스트 조회
	module.cnslChgSearch = function(id){
		if(Common.getIsNull(id) === false){
			chgSearchParams = "&rcptNo=" + id;
		}
		impay.sendGet({
			requestUri: options.contextPath + uris.cnslChgSearch,
			data: $("#"+ forms.searchForm, context).serialize() + chgSearchParams,
			dataType: "json",
			successCallback: root.cnslChgBindList
		});
	};
	
	// 거래시도 탭 리스트 조회
	module.trdTrySearch = function(){
		$("#tab1-2", context).show();
		impay.sendGet({
			requestUri: options.contextPath + uris.trdTrySearch,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: root.trdTryBindList
		});
	};
	
	// 거래시도 탭 리스트 출력
	module.trdTryBindList = function(result){
		if(result.total > 0){
			var htmlResult = tmpl("tmpl-trdTryList", result.content);
			$("#trdTryPaginationInner", context).paging({
	        	totalCount : result.total,
	    		pageSize: $("#rowCount", context).val(),
	    		pageNo: $("#pageIndex", context).val(),
	    		onSelectPage : root.goPageTrdTry
	        });
			$("#trdTryResult", context).html(htmlResult);
			$("#trdTryPaginationWrap", context).show();
			$("#trdTryExcelDown", context).css("display", "inline-block");
		} else {
			$("#trdTryResult", context).html("<tr><td colspan='15' align='center'>" + messages.noResult + "</td></tr>");
			$("#trdTryPaginationInner", context).empty();
			$("#trdTryPaginationWrap", context).hide();
			$("#trdTryExcelDown", context).hide();
		}
	};
	
	// 거래시도 리스트 페이징
	module.goPageTrdTry = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.trdTrySearch();
	};
	
	// 거래시도 리스트 엑셀 다운로드
	module.trdTryExcelDown = function(){
		if(!confirm(messages.excelDown)) return;
		var params = $("#"+ forms.searchForm, context).serialize();
		
		impay.sendGet({
			requestUri: options.contextPath + uris.trdTryCount,
			data: params,
			successCallback: function(result){
				if(result > 10000){
					alert(messages.excelDownLimit);
					return;
				}
				$.fileDownload(options.contextPath + uris.trdTryExcelDown + "?" + params)
				   	 .done(function () { alert("File download a success!"); })
				   	 .fail(function () { alert("File download failed!"); });
			},
			errorCallback: function(err){
				alert(err);
			}
		});	
		return false;
	};
	
	// 거래시도 단건 조회
	module.trdTrySelectSearch = function(trdNo){
		impay.sendGet({
			requestUri: options.contextPath + uris.trdTrySelectSearch,
			data: {trdNos : trdNo},
			dataType: "json",
			successCallback: root.trdTrySuccessCallback
		});
	};
	
	// 거래시도 단건 조회 콜백함수
	module.trdTrySuccessCallback = function(result){
		// 입력필드 초기화
		root.setFieldInit();
		// 거래정보 값 셋팅
		$("#infoTrdTypNm", context).text(result.trdTypNm);
		$("#infoTrdDt", context).text(result.reqDd.substring(0,4) + "." + result.reqDd.substring(4,6) + "." + result.reqDd.substring(6));
		$("#infoCommcClfNm", context).text(result.commcClfNm);
		var cnclDd = "";
		if(Common.getIsNull(result.cnclDd) === false){
			cnclDd = result.cnclDd.substring(0,4) + "." + result.cnclDd.substring(4,6) + "." + result.cnclDd.substring(6)
		}
		$("#infoCnclDt", context).text(cnclDd);
		$("#infoPayAmt", context).text(Common.formatMoney(result.payAmt));
		$("#infoAvlCncl", context).text(result.avlCncl);
		$("#infoPaySvcNm", context).text(result.paySvcNm);
		$("#infoTrdNo", context).text(result.trdNo);
		$("#infoGodsNm", context).text(result.godsNm);
		$("#infoTelNo", context).text(StringUtil.nvl(result.telNo, ""));
		$("#tempTrdNo", context).val(result.trdNo);
		$(".trade-info", context).show();
	};
	
	// 결제차단내역 리스트 조회
	module.payItcptSearch = function(){
		impay.sendGet({
			requestUri: options.contextPath + uris.payItcptSearch,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: root.payItcptBindList
		});
	};
	
	// 결제차단내역 리스트 출력
	module.payItcptBindList = function(result){
		if(result.total > 0){
			var htmlResult = tmpl("tmpl-payItcptList", result.content);
			$("#payItcptPaginationInner", context).paging({
	        	totalCount : result.total,
	    		pageSize: $("#rowCount", context).val(),
	    		pageNo: $("#pageIndex", context).val(),
	    		onSelectPage : root.goPagePayItcpt
	        });
			$("#payItcptResult", context).html(htmlResult);
			$("#payItcptPaginationWrap", context).show();
		} else {
			$("#payItcptResult", context).html("<tr><td colspan='9' align='center'>" + messages.noResult + "</td></tr>");
			$("#payItcptPaginationInner", context).empty();
			$("#payItcptPaginationWrap", context).hide();
		}
	};
	
	// 결제차단내역 리스트 페이징
	module.goPagePayItcpt = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.payItcptSearch();
	};
	
	// 가맹점 이관
	module.tjurProcCtnt = function(){
		var cnslCtnt = $("#cnslCtnt", context).val();
		var procCtnt = $("#procCtnt", context).val();
		
		if($.trim(procCtnt) === ""){
			alert(messages.noProcCtnt);
			$("#procCtnt", context).focus();
			return;
		}
		var procYn = $("input:radio[name='procYn']:checked", context).val();
		var params = "&cnslTjurCtnt=" + cnslCtnt + "&procTjurCtnt=" + procCtnt + "&tjurProcYn=" + procYn;
		
		impay.sendPost({
			requestUri: options.contextPath + uris.tjurProcCtnt,
			data: $("#"+ forms.procForm, context).serialize() + params,
			dataType: "json",
			successCallback: function(result){
				if(result.success === true){
					$("#tjurCtnt", context).text(result.result);
					$("#tjurYn", context).val("Y");
					$("#tjurCd", context).val("C");
					// 전체 상담이력 리스트, 관리탭 재조회
					root.cnslDtlSearch();
					root.cnslChgSearch();
				} else {
					alert(messages.error);
					$("#tjurCtnt", context).text("");
				}
			}
		});
	};
	
	// 결과 통보
	module.sendNotiRslt = function(){
		var rcptNo = $("#tempRcptNo", context).val();
		impay.sendPost({
			requestUri: options.contextPath + uris.sendNotiRslt,
			data: $("#"+ forms.sndRsltForm, context).serialize() + "&rcptNo=" + rcptNo,
			dataType: "json",
			successCallback: function(result){
				if(result.success === true){
					alert(messages.sendOk);
					root.cnslChgSearch(rcptNo);
				} else {
					alert(messages.error);
				}
			}
		});
	};
	
	// 결과통보내용 조회
	module.notiRsltSearch = function(seq){
		impay.sendGet({
			requestUri: options.contextPath + uris.notiRsltSearch,
			data: { sndReqSeq : seq },
			dataType: "json",
			successCallback: root.notiRsltBind
		});
	};
	
	// 결과통보 조회값 셋팅
	module.notiRsltBind = function(result){
		if(result !== null){
			// 이전내용 초기화
			$("textarea.sms[name='procCtnt']", context).val("");
			$("textarea.sendEmail[name='procCtnt']", context).val("");
			$("#email", context).val("");
			$("input.sendEmail[name='title']", context).val("");
			
			if(result.rsltNotiMthd === "M"){	// email
				$("textarea.sendEmail[name='procCtnt']", context).val(result.procCtnt.replace(/(<br\/>|<br>)/g, "\r\n"));
				$("#email", context).val(result.email);
				$("input.sendEmail[name='title']", context).val(result.title);
			} else {						// sms
				$("textarea.sms[name='procCtnt']", context).val(result.procCtnt.replace(/(<br\/>|<br>)/g, "\r\n"));
			}
		} else {
			alert(messages.error);
		}
	};
	
	return module;
}(window.cnslMng || {}, $));