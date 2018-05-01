var transferRcpt = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {
			search 	: "/rpm/trxRcpt/search/tjurRcptList",
			count	: "/rpm/trxRcpt/search/tjurRcptListCount",
			excelDown : "/rpm/trxRcpt/search/tjurRcptList/excelDown"
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
	
	// 이관 접수 조회
	module.search = function(){
		impay.sendGet({
			requestUri: options.contextPath + uris.search,
			data: $("#"+ forms.searchForm, context).serialize(),
			dataType: "json",
			successCallback: root.bindList
		});
	};
	
	// 결과값 셋팅
	module.bindList = function(result){
		if(result != null){
			var html = "";
			if(result.total > 0){
				html = tmpl("tmpl-list", result.content);
				$("#paginationInner", context).paging({
		        	totalCount : result.total,
		    		pageSize: $("#rowCount", context).val(),
		    		pageNo: $("#pageIndex", context).val(),
		    		onSelectPage : root.goPage
		        });
				$("#paginationWrap", context).show();
			} else {
				html = "<tr><td colspan='17' align='center'>" + messages.noResult + "</td></tr>";
				$("#paginationInner", context).empty();
				$("#paginationWrap", context).hide();
			}
			$("#result", context).html(html);
			$(".search-info").hide();
			$(".panel-box").show();
		} else {
			alert(messages.error);
		}
	};
	
	module.goPage = function(pageNo){
		$("#pageIndex", context).val(pageNo);
		root.search();
	};
	
	// 이관접수 엑셀 다운로드
	module.excelDown = function(){
		if(!confirm(messages.excelDown)) return;
		var params = $("#"+ forms.searchForm, context).serialize();
		
		impay.sendGet({
			requestUri: options.contextPath + uris.count,
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
	
	// 업로드 이관 접수 팝업 오픈
	module.openLayerCnslUpldFile = function(rcptNo, hash) {
		var url = "rpm/layer/layerCounselView";
		var title = messages.layerCnslTitle;
		if(hash === "process-view"){
			title = messages.layerProcTitle;
		} else if(hash === "result-view"){
			title = messages.layerRsltTitle;
		}
		var css = {width : 900, height : 350};
		var data = {
				rcptNo : rcptNo,
				hash : hash,
				title : title
		};
		layer.init({contextPath:options.contextPath, parentModule:root, data:data, returnCallback:root.reloadPayItcpt});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	
	module.reloadPayItcpt = function(result){
		if(Common.getIsNull(result) === false && result === true){
			root.search();
		}
	};
	
	return module;
}(window.transferRcpt || {}, $));