var annoDescReg = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {search: '/sysm/annoDescRegMng/search',
				add : 	'/sysm/annoDescRegMng/add',
				update : '/sysm/annoDescRegMng/update',
				annoDescInfo: '/sysm/annoDescRegMng/annoDescInfo'}; 	
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
	// 처음 모든 조건 없이 조회 
	module.firstSearch = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.search,
			data: {'pageParam.pageIndex':'1', 'pageParam.rowCount':'5'},
			dataType: 'json',
			successCallback: root.bindList
		});
	};
	module.search = function(){
		// 조회 기간 조건 체크(최대 12개월)
        var sttDd = $("#stDate",context).val();
        var endDd = $("#endDate",context).val();
        if( (sttDd !== null && sttDd !== '') && (endDd !== null && endDd !== '') ) {
           var result = DateUtil.getDiffDate(sttDd, endDd);
           if(result > 365) {
              alert(messages.dateCheck1);
              return;
           }else if(result < 0 ){
        	   alert(messages.dateCheck2);
           		return;   
           } 
        }
		impay.sendPost({
			requestUri: options.contextPath + uris.search,
			data: $('#'+ forms.searchForm, context).serialize(),
			dataType: 'json',
			successCallback: root.bindList
		});
	};
	module.bindList = function(data){
		$("#impay-csm-annoDesc .pagination-wrap", context).css({'display': 'block'});
		if(data.total > 0){
			$("#result", context).html(tmpl("tmpl-annoDescList" , data.content));
			$("#pagination", context).paging({
	        	totalCount: data.total,
	        	pageSize: $('#rowCount', context).val(),
	        	pageNo: $("#pageIndex", context).val(),
	        	onSelectPage: root.goPage
	        });
			root.getAnnoDescInfo(data.content[0].clctBordSeq);
    	}else{
    		$("#infoDiv",context).hide();
    		$("#impay-csm-annoDesc #result", context).html("<tr><td colspan='4' align='center'>" + messages.noResult + "</td></tr>");
			$("#impay-csm-annoDesc .pagination-wrap", context).css({'display': 'none'});
    	}
	};
	module.goPage = function(pageNo){
		$('#pageIndex',context).val(pageNo);
		root.search();
	};
	// 공지 상세 내역
	module.getAnnoDescInfo = function(clctBordSeq){
		$("#infoDiv",context).show();
		$("#newTitle, #regBtn").hide();
		$("#updateBtn").show();
		$("#clctBordSeq", context).val(clctBordSeq);		
    	var target = $(event.target);
		$(target).parent().parent().find('tr').removeClass('row-selected');
		$(target).parent().addClass('row-selected');
		impay.sendGet({
			requestUri: options.contextPath + uris.annoDescInfo + "/" + clctBordSeq,
			successCallback: root.bindAnnoDescInfo
		});
	};
	module.bindAnnoDescInfo = function(data){
		root.formReset();
		$("#clctBordSeq", context).val(data.clctBordSeq);
		$("#titl", context).val(data.titl);
		$("#regDt", context).html(data.regDt);
		$("#regr", context).html(data.regr);
		$("#ctnt", context).val(data.ctnt);
		$('#clf > option[value="'+data.bordClfCd+'"]', context).attr("selected", "selected");
		if(data.titlBldYn=="Y"){
			$("#titlBldYn", context).prop("checked",true);
		}
	};
	// 상세폼 초기화
	module.formReset = function(){
		$("#infoForm", context).each(function(){
			this.reset();
		});
		$("#clctBordSeq", context).val("");
		$("#clf > option:eq(0)", context).attr("selected", "selected");
		$("#regr", context).html($("#userNm",context).val());
	};
	// 신규 등록
	module.addAnnoDescReg = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.add,
			data: $('#'+ forms.infoForm, context).serialize(),
			dataType: 'json',
			successCallback: root.successAdd
		});
	};
	module.successAdd = function(data){
		if(data.message == "success"){
			alert(messages.insertOk);
		}else if(data.message == "fail"){
			alert(messages.error);
		}
		root.search();
		root.formReset();
	};
	// 수정
	module.updateAnnoDescReg = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.update,
			data: $('#'+ forms.infoForm, context).serialize(),
			dataType: 'json',
			successCallback: root.successUdate
		});
	};
	module.successUdate = function(data){
		if(data.message == "success"){
			alert(messages.updateOk);
		}else if(data.message == "fail"){
			alert(messages.error);
		}
		root.search();
		root.formReset();
	};
	
	return module;
}(window.annoDescReg || {}, $));