var detail = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var urls = {
			depts	: "/cards/depts/",
			accnts	: "/cards/accnts/",
			apprvs	: "/cards/apprvs/",
			prjs	: "/cards/prjs/",
			tasks	: "/cards/tasks/",
			orgs	: "/cards/orgs/",
			types	: "/cards/types/",
			file 	: "/files/",
			reqAdj : "/cards/reqAdj/"
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
	
	// 파일첨부
	module.upload = function(i) {
		var formData = new FormData();
		formData.append("files", $(":file")[i].files[0]);
		rest.upload({
			formData : formData,
			success : function(result) {
				// file 초기화
				$(":file").val("");
				// 첨부파일 목록
				var html = tmpl("tmpl-fileList", result);
				$("ul.fileList").append(html);
			},
			error : function(xhr, status, err) {
				// file 초기화
				$(":file").val("");
				var msg = messages.get("errorUpload");
				if(xhr.responseJSON) {
					msg = xhr.responseJSON.errorMessage;
				}
				// 팝업 이벤트 초기화
        		$("#popAlert").popup({
        			afterclose : undefined
        		});
        		$("#alertMsg").text(msg);
        		$("#popAlert").popup("open");
			}
		});
	};
	
	// 파일 다운로드
	module.downloadView = function(url) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState === 4 && xhr.status === 200) {
				var blob = new Blob([xhr.response], {
					type : xhr.getResponseHeader("Content-Type")
				});
				var URL = window.URL || window.webkitURL;
				var imgUrl = URL.createObjectURL(blob);
				$(".photopopup img").attr("src", imgUrl);
				$(".photopopup").popup("option", "transition", "fade");
				setTimeout(function(){
					$(".photopopup").popup("open");
		        }, 100);
			}
		};
		xhr.responseType = "arraybuffer";
		xhr.open("GET", url, true);
		xhr.send();
	};
	
	// 파일 삭제
	module.deleteFile = function(id) {
		rest.del({
			url : contextPath + urls.file + id,
			success : function(result, status, xhr) {
				$("ul.fileList").children("li").each(function() {
					if(id == $(this).data("id")) {
						$(this).remove();
					}
				});
			}
		});
	};
	
	// 부서검색
	module.getDeptList = function() {
		rest.get({
			url : contextPath + urls.depts,
			data : {
				srchTxt : $(":text[name='srchTxt']").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-deptList", result);
					$("#deptContent").html(html);
					$(".no-result").addClass("hidden");
				} else {
					$("#deptContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// 계정검색
	module.getAccntList = function() {
		rest.get({
			url : contextPath + urls.accnts,
			data : {
				srchTxt	 : $(":text[name='srchTxt']").val(),
				deptCode : $("#deptTbl .row-index-"+$("#index").val()+" :hidden[name='deptCode']").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-accntList", result);
					$("#accntContent").html(html);
					$(".no-result").addClass("hidden");
				} else {
					$("#accntContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// 승인권자 검색
	module.getApprList = function() {
		rest.get({
			url : contextPath + urls.apprvs,
			data : {
				srchTxt : $(":text[name='srchTxt']").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-apprList", result);
					$("#apprContent").html(html);
					$(".no-result").addClass("hidden");
					//선택후 재검색 시 기존 선택한 내역표시 (css,text 변경)
					$("#apprSelContent tr").find(":hidden[name='empcd']").each(function(){
						var $seled = $("#apprContent tr").find(":hidden[name='empcd'][value='"+$(this).val()+"']")
										.parent().parent().parent().find("a.list-add-btn");
						$seled.addClass("checked");
						$seled.text("");
					});
				} else {
					$("#apprContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// 프로젝트 검색
	module.getPrjList = function() {
		rest.get({
			url : contextPath + urls.prjs,
			data : {
				srchTxt : $(":text[name='srchTxt']").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-prjList", result);
					$("#prjContent").html(html);
					$(".no-result").addClass("hidden");
				} else {
					$("#prjContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// Task 검색
	module.getTaskList = function() {
		rest.get({
			url : contextPath + urls.tasks,
			data : {
				srchTxt : $(":text[name='srchTxt']").val(),
				projectId : $("#projectId").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-taskList", result);
					$("#taskContent").html(html);
					$(".no-result").addClass("hidden");
				} else {
					$("#taskContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// 수행조직 검색
	module.getOrgList = function() {
		rest.get({
			url : contextPath + urls.orgs,
			data : {
				srchTxt : $(":text[name='srchTxt']").val(),
				projectId : $("#projectId").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-orgList", result);
					$("#orgContent").html(html);
					$(".no-result").addClass("hidden");
				} else {
					$("#orgContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// 원가유형 검색
	module.getTypeList = function() {
		rest.get({
			url : contextPath + urls.types,
			data : {
				srchTxt : $(":text[name='srchTxt']").val(),
				projectId : $("#projectId").val(),
				projectNumber : $("#projectNumber").val()
			},
			success : function(result, status, xhr) {
				if(result.length > 0) {
					var html = tmpl("tmpl-typeList", result);
					$("#typeContent").html(html);
					$(".no-result").addClass("hidden");
				} else {
					$("#typeContent").empty();
					$(".no-result").removeClass("hidden");
				}
			}
		});
	};
	
	// 승인요청
	module.reqAdj = function(cardno) {
		var val = $(":radio[name='adjustType']:checked").val();
		var frmId = "";
		if(val === "D") {
			frmId = "#deptFrm";
		} else if(val === "P") {
			frmId = "#prjFrm";
		}
		var frmData = $.extend($("#headerFrm").serializeObject(), $(frmId).serializeObject());
		frmData.jwt = sessionStorage.jwt;
		
		rest.post({
			url : contextPath + urls.reqAdj,
			data : root.normalizeObject(val, frmData),
			success : function(result, status, xhr) {
				$("#popAlert").popup({
					afterclose : function() {
						$.mobile.back();
					}
				});
				$("#alertMsg").text(messages.get("apprvOk"));
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
		        }, 500);
			}
		});
	};
	
	// 승인요청데이터 normalize
	module.normalizeObject = function(type, obj) {
		// 배열로 타입 변환
		if(!(obj.mainAccCode instanceof Array)) {
			obj.mainAccCode = [obj.mainAccCode];
		}
		if(!(obj.subAccCode instanceof Array)) {
			obj.subAccCode = [obj.subAccCode];
		}
		if(!(obj.attributeCategory instanceof Array)) {
			obj.attributeCategory = [obj.attributeCategory];
		}
		if(!(obj.description instanceof Array)) {
			obj.description = [obj.description];
		}
		if(!(obj.appramt instanceof Array)) {
			obj.appramt = [obj.appramt];
		}
		if(type === "D" && !(obj.deptCode instanceof Array)) {
			obj.deptCode = [obj.deptCode];
		} else if(type === "P" && !(obj.projectNumber instanceof Array)) {
			obj.projectName 			= [obj.projectName];
			obj.projectNumber 			= [obj.projectNumber];
			obj.projectType 			= [obj.projectType];
			obj.projectStatusCode 		= [obj.projectStatusCode];
			obj.projectStatusCodeNm 	= [obj.projectStatusCodeNm];
			obj.projectId 				= [obj.projectId];
			obj.taskId 					= [obj.taskId];
			obj.taskName 				= [obj.taskName];
			obj.taskNumber 				= [obj.taskNumber];
			obj.expOrgId 				= [obj.expOrgId];
			obj.expOrgName 				= [obj.expOrgName];
			obj.segment2 				= [obj.segment2];
			obj.expType 				= [obj.expType];
		}
		// 숫자 타입 변환
		for(var i=0; i<obj.appramt.length; i++) {
			obj.appramt[i] 			= Number(obj.appramt[i].replace(/\,/g, ""));
			if(type === "P") {
				obj.projectId[i] 	= Number(obj.projectId[i]);
				obj.taskId[i] 		= Number(obj.taskId[i]);
				obj.expOrgId[i] 	= Number(obj.expOrgId[i]);
			}
		}
		// 첨부파일 추가
		var tblId = type === "D" ? "#deptTbl" : "#prjTbl";
		if($(tblId).find("ul.fileList > li").length > 0) {
			var fileIdArray = [];
			var fileNameArray = [];
			$(tblId).find("ul.fileList > li").each(function(){
				fileIdArray.push($(this).data("id"));
				fileNameArray.push($(this).data("nm"));
			});
			obj.fileId = fileIdArray.join("|");
			obj.fileNm = fileNameArray.join("|");
		}
		return obj;
	};
	
	return module;
}(window.detail || {}, $));