var payBackRcptMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {search 		: '/rpm/payBackRcpt/search',	
				checkedDel 	: '/rpm/payBackRcpt/delete',
				excelDown	: '/rpm/payBackRcpt/excelDown',
				payBackRcptInfo : '/rpm/payBackRcpt/getPayBackRcptInfo'};
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
	// 초기 검색 날짜 셋팅
	module.dateSetting = function(){
		var today = DateUtil.getToday();
		today = Common.formatDateYYYYMMDD(today, ".");
		$("#rcptEndDt").val(today);
		today = today.replace(/\./g, "");

		var tempDt = new Date(today.substring(0,4), (today.substring(4,6)-1));
		var resultDt = tempDt.getFullYear().toString() + "." + Common.lpad((tempDt.getMonth() + 1),"0","2")+"."+Common.lpad(tempDt.getDate(),"0","2");
		$("#rcptStDt").val(resultDt);
		$("#reqStDt").val(resultDt);
		
		var aftMn = new Date(today.substring(0,4), today.substring(4,6) );
		var aftDd = (new Date( aftMn.getFullYear().toString(), aftMn.getMonth()+1, "0")).getDate();
		var affResult = aftMn.getFullYear().toString() + "." + Common.lpad((aftMn.getMonth()+1),"0","2")+"."+aftDd;
		$("#reqEndDt").val(affResult);
	};
	// 환불 접수 검색
	module.search = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.search,
			data: $('#'+ forms.searchForm, context).serialize(),
			dataType: 'json',
			successCallback: root.bindList
		});
	};
	module.bindList = function(data){
		$("#impay-search-list .pagination-wrap", context).css({'display': 'block'});
    	if(data.total > 0){
    		$("#result", context).html(tmpl("tmpl-payBackRcptList", data.content));
    		$("#pagination", context).paging({
                totalCount : data.total,
                pageSize: $('#rowCount', context).val(),
                pageNo: $("#pageIndex", context).val(),
                onSelectPage : root.goPage
            });
    		root.getPayBackRcptInfo(data.content[0].pybkRcptNo);
    	}else{
    		$("#impay-search-list #result", context).html("<tr><td colspan='12' align='center'>" + messages.noResult + "</td></tr>");
			$("#impay-search-list .pagination-wrap", context).css({'display': 'none'});
			$("#payBackRcptInfo").hide();
    	}
	};
	module.goPage = function(pageNo){
		$('#pageIndex', context).val(pageNo);
		root.search();
	};
	// CHECK 된 데이터 삭제여부 Y/N 수정
	module.checkedDel = function(){
		var trdNos = "";
		$("input:checkbox[name='trdNos']:checked", context).each(function(){
			trdNos += "&trdNos=" + $(this).val();
		});
		
		if(trdNos.length != 0 ){
			impay.sendPost({
				requestUri: options.contextPath + uris.checkedDel,
				data: trdNos,
				dataType: 'json',
				successCallback: root.successDelete
			});
		}else{
			alert(messages.checkAlert);
		}
	};	
	module.successDelete = function(data){
		if(data.message == "success"){
			alert(messages.deleteOk);
		}else if(data.message == "fail"){
			alert(messages.errorMsg);
		}
		root.search();
	};
	// 환불 접수 상세 내역 조회
	module.getPayBackRcptInfo = function(pybkRcptNo){
    	var target = $(event.target);
		$(target).parent().parent().find('tr').removeClass('row-selected');
		$(target).parent().addClass('row-selected');
		
		impay.sendGet({
			requestUri: options.contextPath + uris.payBackRcptInfo + "/" + pybkRcptNo,
			successCallback: root.bindPayBackRcptInfo
		});
	};
	module.bindPayBackRcptInfo = function(data){
		$("#payBackRcptInfo").show();
		$("#payBackRcptInfo #regDt").html(data.regDt);
		$("#payBackRcptInfo #commcClf").html(data.commcClf);
		$("#payBackRcptInfo #pybkReqDd").html(Common.formatDateYYYYMMDD(data.pybkReqDd, "."));
		$("#payBackRcptInfo #mphnNo").html(utils.hiddenToTel(data.mphnNo));
		$("#payBackRcptInfo #mphnNo").attr('title',data.mphnNo);
		$("#payBackRcptInfo #pybkTypCd").html(data.pybkTypCd);
		$("#payBackRcptInfo #payrNm").html(data.payrNm);
		$("#payBackRcptInfo #payAmt").html(Common.formatMoney(data.payAmt));
		$("#payBackRcptInfo #bankNm").html(data.bankNm);
		$("#payBackRcptInfo #acctNo").html(utils.maskingAccount(data.acctNo));
		$("#payBackRcptInfo #acctNo").attr('title',data.acctNo);
		
		$("#payBackRcptInfo #dpstrNm").html(data.dpstrNm);
	};
	return module;
}(window.payBackRcptMng || {}, $));