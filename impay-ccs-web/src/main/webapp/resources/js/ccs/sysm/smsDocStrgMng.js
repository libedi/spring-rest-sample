var smsDocStrgMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = {search: '/sysm/smsDocStrgMng/getList',
				smsWord: '/sysm/smsDocStrgMng/getSmsWord',
				addSmsWord: '/sysm/smsDocStrgMng/addSmsWord',
				delSmsWord:	'/sysm/smsDocStrgMng/deleteSmsWord',
				updSmsWord:	'/sysm/smsDocStrgMng/updateSmsWord',};
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
	// SMS 문서보관함 조회
	module.search = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.search,
			successCallback: root.bindList
		});
	};
	module.bindList = function(data){
		if(data.total > 0){
			$("#listTotal", context).html(data.total);
			$("#result", context).html(tmpl("tmpl-smsDocList" , data.content));			
		}
	};
	// CLICK 한 문구 조회
	module.getSmsWord = function(charStrgNo){
		$("#updateBtn",context).show();
		$("#addBtn",context).hide();
		$("#charStrgNo", context).val(charStrgNo);
		impay.sendGet({
			requestUri: options.contextPath + uris.smsWord + "/" + charStrgNo,
			successCallback: root.bindSmsWord
		});
	};
	module.bindSmsWord = function(data){
		$("#ctntCount").html(Common.getByteString(data.charCtnt));
		$("#charCtnt",context).val(data.charCtnt);
	};
	// SMS 문서 삭제
	module.deleteSmsWord = function(charStrgNo){
		impay.sendGet({
			requestUri: options.contextPath + uris.delSmsWord + "/" + charStrgNo,
			successCallback: root.successDelete
		});
	};
	module.successDelete = function(data){
		if(data.message == "success"){
			alert(messages.deleteOk);
		}else if(data.message == "fail"){
			alert(messages.error);
		}
		$("#addBtn").show();
		$("#updateBtn").hide();
		root.resetForm();
		root.search();
	};
	// SMS 문서 등록
	module.addSmsWord = function(){
    	impay.sendPost({
			requestUri: options.contextPath + uris.addSmsWord,
			data: $('#'+ forms.smsWordForm, context).serialize(),
			dataType: 'json',
			successCallback: root.successAdd
		});
	};
	module.successAdd = function(data){
		if(data.message == "success"){
			alert(messages.insertOk);
			root.resetForm();
		}else if(data.message == "fail"){
			alert(messages.error);
		}
		root.search();
	};
	// SMS 문서 수정
	module.updateSmsWord = function(){
		impay.sendPost({
			requestUri: options.contextPath + uris.updSmsWord,
			data: $('#'+ forms.smsWordForm, context).serialize(),
			dataType: 'json',
			successCallback: root.successUpdate
		});
	};
	module.successUpdate = function(data){
		if(data.message == "success"){
			alert(messages.updateOk);
		}else if(data.message == "fail"){
			alert(messages.error);
		}
		root.search();
	};
	module.resetForm = function(){
		$("#charCtnt, #charStrgNo").val("");
		$("#ctntCount").html("0");
	};
	return module;
}(window.smsDocStrgMng || {}, $));