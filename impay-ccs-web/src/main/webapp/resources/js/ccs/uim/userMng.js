var userMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = { update : "/userMng/update" };
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
	
	// 사용자정보 수정
	module.update = function(){
		utils.applyTrim(forms.updateForm);
		impay.sendPost({
			requestUri: options.contextPath + uris.update,
			data: $("#"+ forms.updateForm, context).serialize(),
			dataType: "json",
			successCallback: root.bindValue
		});
	};
	// 결과
	module.bindValue = function(data){
		if(data.success === true){
			var result = data.result;
			$("#userSeq", context).val(result.userSeq);
			$("#cntcSeq", context).val(result.cntcInfo.cntcSeq);
			$("#savedMphnNo", context).text(result.cntcInfo.mphnNo);
			$("#mphnNo", context).val(result.cntcInfo.mphnNo);
			$("#savedEmail", context).text(result.cntcInfo.email);
			$("#email", context).val(result.cntcInfo.email);
			$("#savedDeptNm", context).text(result.cntcInfo.deptNm);
			$("#deptNm", context).val(result.cntcInfo.deptNm);
			alert(messages.updateOk);
			$(".update", context).hide();
			$(".saved", context).show();
		} else {
			alert(data.message);
		}
	};
	// 비밀번호 변경 레이어
	module.updatePwd = function() {
		var userSeq = $("#userSeq", context).val();
		var url = "/uim/layer/updatePwd";
		var title = messages.updateTitle;
		var css = {
				width : 360,
				height : 290
		};
		layer.init({contextPath:options.contextPath, parentModule:root, data:{userSeq:userSeq}});
		layer.make("fixed-layer", title, css);
		layer.load(url);
		layer.open();
	};
	
	return module;
}(window.userMng || {}, $));