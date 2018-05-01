var addPayWhiteListMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {search 		: '/rpm/addPayWhiteListMng/search',
				addPayWhiteListInfo : '/rpm/addPayWhiteListMng/info'
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
    		$("#result", context).html(tmpl("tmpl-addPayWLlist", data.content));
    		$("#pagination", context).paging({
                totalCount : data.total,
                pageSize: $('#rowCount', context).val(),
                pageNo: $("#pageIndex", context).val(),
                onSelectPage : root.goPage
            });
    	}else{
    		$("#info", context).hide();
    		$("#impay-search-list #result", context).html("<tr><td colspan='10' align='center'>" + messages.noResult + "</td></tr>");
			$("#impay-search-list .pagination-wrap", context).css({'display': 'none'});
    	}
	};
	module.goPage = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.search();
	};
	// 가산금 WhiteList 등록 layer
	module.addPayAddWhiteList = function()	{
    	layer.init({contextPath:options.contextPath, parentModule:root, returnCallback:module.callBack});
        layer.make('fixed-layer', messages.regReason, {width:620, height:326});
        layer.load('rpm/layer/layerAddPayWhiteList');
        layer.open();
    };
    module.callBack = function(data){
    	if(!Common.isEmpty(data)){
    		if(data.message === "success"){
    			root.search();
    		}
    	}
    };
    // 가산금 WhiteList 삭제 layer
    module.openDelAddPayWhiteList = function(wlRegNo){
    	$("#wlRegNo", context).val(wlRegNo);
    	layer.init({contextPath:options.contextPath, parentModule:root, returnCallback:module.callBack});
        layer.make('fixed-layer', messages.delReason, {width:620, height:445});
        layer.load('rpm/layer/layerAddPayDelWhiteList');
        layer.open();
    };
    module.setLayerInit = function(){
    	$("#lyWlRegNo", ".fixed-layer").html($("#wlRegNo", context).val());
    };
    // 상세 정보 조회
    module.getInfo = function(wlRegNo){
    	var target = $(event.target);
		$(target).parent().parent().find('tr').removeClass('row-selected');
		$(target).parent().addClass('row-selected');
		impay.sendGet({
			requestUri: options.contextPath + uris.addPayWhiteListInfo + "/" + wlRegNo,
			successCallback: root.addPayWhiteListInfo
		});
    };
    module.addPayWhiteListInfo = function(data){
    	$("#info").show();
    	$("#info #wlRegNo").html(data.wlRegNo);	// 등록번호
    	$("#info #payrNm").html(data.payrNm);	// 이름
    	$("#info #mphnNo").html(utils.hiddenToTel(data.mphnNo));	// 휴대폰 번호
    	$("#info #mphnNo").attr('title',data.mphnNo);
    	$("#info #regDt").html(data.regDt+" / "+data.delDt);
    	$("#info #delDt").html(data.delDt);
    	$("#info #regClfCd").html(data.regClfCd);// 등록 구분
    	$("#info #regRsn").val(data.regRsn);	// 등록 사유
    	$("#info #delRsn").val(data.delRsn);	// 삭제 사유
    	$("#info #regr").html(data.regr);		// 상담원
    };
    
	return module;
}(window.addPayWhiteListMng || {}, $));