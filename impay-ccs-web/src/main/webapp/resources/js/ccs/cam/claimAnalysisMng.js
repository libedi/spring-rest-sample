var clmAnslMng = (function(module, $){
	"use strict";
	
	/**
	 * private
	 */
	var root = module;
	var options = { contextPath : "" };
	var uris = { searchCase : '/cam/claimAnslMng/searchCase',
				 searchType : '/cam/claimAnslMng/searchType',
				 searchTjur : '/cam/claimAnslMng/searchTjur',
				 caseExcelDown 	: '/cam/claimAnslMng/excelDown/case',
				 caseExcelCount	: '/cam/claimAnslMng/excelDown/case/count',
				 typeExcelDown 	: '/cam/claimAnslMng/excelDown/type',
				 typeExcelCount : '/cam/claimAnslMng/excelDown/type/count',
				 tjurExcelDown 	: '/cam/claimAnslMng/excelDown/tjur',
				 tjurExcelCount : '/cam/claimAnslMng/excelDown/tjur/count'}; 	
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
	// 가맹점 검색 Layer OPEN
	module.openTrgtCpSeachLayer = function() {		
		/** 검색코드, 검색명 */
		var searchClf = ''; 
		var searchWord = '';
		
		if($('#cpCd', context).val() !== "") {
			searchClf = 'cpCd';
			searchWord = $('#cpCd', context).val();
		} else if($('#cpNm', context).val() !== "") {
			searchClf = 'cpNm';
			searchWord = $('#cpNm', context).val();
		}
		layer.init({
			'data': {'searchClf': searchClf , 'searchWord':searchWord } ,
		    'returnCallback': function(data){
		    	if(data === undefined) return;
		     	$('#cpCd', context).val(data.cpCd);
		     	$('#cpNm', context).val(data.paySvcNm);
			}, 
			contextPath:options.contextPath
		});
		layer.make('fixed-layer', messages.searchCp, {width:850, height:600});
		layer.load('/common/layer/layerFindListCp');
		layer.open();
	};
	// 조회 기간 체크 ( 시작날짜 > 종료날짜 큰경우 ) & 조회 기간 1년 이상
	module.dateCheck = function(clf){
		var sttDd = $(".tab-conts input[id=stDate"+clf+"]").val();
		var endDd = $(".tab-conts input[id=endDate"+clf+"]").val();
		if( (sttDd !== null && sttDd !== '') && (endDd !== null && endDd !== '') ) {
			var result = DateUtil.getDiffDate(sttDd, endDd);
			if(result > 365) {
				alert(messages.dateCheck1);
				$(".tab-conts input[id=stDate"+clf+"]").focus();
				return false;
			}else if(result < 0 ){
				alert(messages.dateCheck2);
				$(".tab-conts input[id=stDate"+clf+"]").focus();
				return false;   
			}else{
				return true;
			} 
		}
	};
	/* TAB1 */
	// 건별 조회
	module.searchTab1 = function(){
		if(root.dateCheck("T1")){
			impay.sendPost({
				requestUri: options.contextPath + uris.searchCase,
				data: $('#'+ forms.searchFormTab1, context).serialize(),
				dataType: 'json',
				successCallback: root.bindList
			});
		}
	};
	module.bindList = function(data){
		$("#tab1-info",context).hide();
		$("#impay-cam-caseList").show();
		$("#impay-cam-caseList .pagination-wrap", context).css({'display': 'block'});
		if(data.total > 0){
			$("#caseResult", context).html(tmpl("tmpl-caseList" , data.clmAnlsCaseList));
			$("#pagination-tab1", context).paging({
	        	totalCount: data.total,
	        	pageSize: $('#tab1-rowCount', context).val(),
	        	pageNo: $("#tab1-pageIndex", context).val(),
	        	onSelectPage: root.goPageTab1
	        });
    	}else{
    		$("#impay-cam-caseList #caseResult", context).html("<tr><td colspan='17' align='center'>" + messages.noResult + "</td></tr>");
			$("#impay-cam-caseList .pagination-wrap", context).css({'display': 'none'});
    	}
		root.bindCaseChart(data);
	};
	module.goPageTab1 = function(pageNo){
		$('#tab1-pageIndex', context).val(pageNo);
		root.searchTab1();
	};
	// 건별 통계
	module.bindCaseChart = function(data){
		var chart = data.chartModel;
		var charDates = [];
		var tktk01 = [];
		var tktk02 = [];
		var tktk03 = [];
		var tktk04 = [];
		
		for(var i = 0; i<= chart.length-1; i++){
			charDates.push(chart[i].chartDates.substring(2));
			tktk01.push(chart[i].tktk01);
			tktk02.push(chart[i].tktk02);
			tktk03.push(chart[i].tktk03);
			tktk04.push(chart[i].tktk04);
		}
		$("#impay-case-chart",context).highcharts({
			chart : {
				type : 'line',
				zoomType: 'x'
			},
			title : {
				text: messages.chart1_header, 
				x   : -20
			},
			xAxis: {
				categories: charDates
	        },
	        yAxis: {
	        	title: {
	        		text : messages.chart_y, 
	        		align: 'high', 
	        		rotation: 0
	        	} ,
	        	labels:{
	        		formatter:function(){
	        			return this.value+messages.count;
	        		}
	        	},
	        	tickInterval:1		// Y축 interval 단위
	        	
	        }, 
	        tooltip: {
	        	valueSuffix: messages.count,
	            crosshairs: true,
	            shared: true
	        },
	        series: [{
	        		name : messages.chart1_label1,
	        		data : tktk01
	        	},{
	        		name : messages.chart1_label2,
	        		data : tktk02
	        		
	        	},{
	        		name : messages.chart1_label3,
	        		data : tktk03
	        	},{
	        		name : messages.chart1_label4,
	        		data : tktk04
	        	}]
		});
	};
	// 건별 목록 엑셀 다운로드
	module.caseExcelDown = function(){
		if(!confirm(messages.excelDown)) return;
		var data = $('#'+ forms.searchFormTab1, context).serialize();
		impay.sendGet({
			requestUri: options.contextPath + uris.caseExcelCount,
			data: data,
			successCallback: function(result){
				if(result > 10000){
					alert(messages.excelDownLimit);
					return;
				}
				$.fileDownload(options.contextPath + uris.caseExcelDown + "?" + data)
			   	 .done(function () { alert('File download a success!'); })
			   	 .fail(function () { alert('File download failed!'); });
			},
			errorCallback: function(err){
				alert(err);
			}
		});	
		return false;
	};
	/* TAB2 */
	// 유형별 조회
	module.searchTab2 = function(){
		if(root.dateCheck("T2")){
			impay.sendPost({
				requestUri: options.contextPath + uris.searchType,
				data: $('#'+ forms.searchFormTab2, context).serialize(),
				dataType: 'json',
				successCallback: root.bindTypeList
			});
		}
	};
	module.bindTypeList = function(data){
		$("#tab2-info",context).hide();
		$("#impay-cam-typeList").show();
		$("#impay-cam-typeList .pagination-wrap", context).css({'display': 'block'});
		if(data.total > 0){
			$("#typeResult", context).html(tmpl("tmpl-typeList" , data.clmAnlsTypeList));
			$("#pagination-tab2", context).paging({
	        	totalCount: data.total,
	        	pageSize: $('#tab2-rowCount', context).val(),
	        	pageNo: $("#tab2-pageIndex", context).val(),
	        	onSelectPage: root.goPageTab2
	        });
    	}else{
    		$("#impay-cam-typeList #typeResult", context).html("<tr><td colspan='10' align='center'>" + messages.noResult + "</td></tr>");
			$("#impay-cam-typeList .pagination-wrap", context).css({'display': 'none'});
    	}
		root.bindTypeChart(data);
	};
	module.goPageTab2 = function(pageNo){
		$('#tab2-pageIndex', context).val(pageNo);
		root.searchTab2();
	};
	// 차트
	module.bindTypeChart = function(data){
		var chart = data.chartModel;
		var charDates = [];
		var tkev01 = [];
		var tkev02 = [];
		var tkev03 = [];
		
		for(var i = 0; i<= chart.length-1; i++){
			charDates.push(chart[i].chartDates.substring(2));
			tkev01.push(chart[i].tkev01);
			tkev02.push(chart[i].tkev02);
			tkev03.push(chart[i].tkev03);
		}
		$("#impay-type-chart",context).highcharts({
			chart : {
				type : 'line',
				zoomType: 'x'
			},
			title : {
				text: messages.chart2_header, 
				x   : -20
			},
			xAxis: {
				categories: charDates
	        },
	        yAxis: {
	        	title: {
	        		text : messages.chart_y, 
	        		align: 'high', 
	        		rotation: 0
	        	} ,
	        	labels:{
	        		formatter:function(){
	        			return this.value+messages.count;
	        		}
	        	},
	        	tickInterval:1		// Y축 interval 단위
	        	
	        }, 
	        tooltip: {
	        	valueSuffix: messages.count,
	            crosshairs: true,
	            shared: true
	        },
	        series: [{
	        		name : messages.chart2_label1,
	        		data : tkev01
	        	},{
	        		name : messages.chart2_label2,
	        		data : tkev02
	        		
	        	},{
	        		name : messages.chart2_label3,
	        		data : tkev03
	        	}]
		});
	};
	// 유형별 목록 엑셀 다운
	module.typeExcelDown = function(){
		if(!confirm(messages.excelDown)) return;
		var data = $('#'+ forms.searchFormTab2, context).serialize();

		impay.sendGet({
			requestUri: options.contextPath + uris.typeExcelCount,
			data: data,
			successCallback: function(result){
				if(result > 10000){
					alert(messages.excelDownLimit);
					return;
				}
				$.fileDownload(options.contextPath + uris.typeExcelDown + "?" + data)
			   	 .done(function () { alert('File download a success!'); })
			   	 .fail(function () { alert('File download failed!'); });
			},
			errorCallback: function(err){
				alert(err);
			}
		});	
		return false;
	};
	/* TAB-3 */
	// 이관접수별 조회
	module.searchTab3 = function(){
		if(root.dateCheck("T3")){
			impay.sendPost({
				requestUri: options.contextPath + uris.searchTjur,
				data: $('#'+ forms.searchFormTab3, context).serialize(),
				dataType: 'json',
				successCallback: root.bindTjurList
			});
		}
	};
	module.bindTjurList = function(data){
		$("#tab3-info",context).hide();
		$("#impay-cam-tjurList").show();
		$("#impay-cam-tjurList .pagination-wrap", context).css({'display': 'block'});
		if(data.total > 0){
			$("#tjurResult", context).html(tmpl("tmpl-tjurList" , data.clmAnlsTjurRcptList));
			$("#pagination-tab3", context).paging({
	        	totalCount: data.total,
	        	pageSize: $('#tab3-rowCount', context).val(),
	        	pageNo: $("#tab3-pageIndex", context).val(),
	        	onSelectPage: root.goPageTab3
	        });
    	}else{
    		$("#impay-cam-tjurList #tjurResult", context).html("<tr><td colspan='11' align='center'>" + messages.noResult + "</td></tr>");
			$("#impay-cam-tjurList .pagination-wrap", context).css({'display': 'none'});
    	}
		root.bindTjurChart(data);
	};
	module.goPageTab3 = function(pageNo){
		$('#tab3-pageIndex', context).val(pageNo);
		root.searchTab3();
	};
	// 차트
	module.bindTjurChart = function(data){
		var chart = data.chartModel;
		var charDates = [];
		var cpCnt	  = [];	 		
		var atipbzCnt = [];
		var atipitCnt = [];
		var atipopCnt = [];
		
		for(var i = 0; i<= chart.length-1; i++){
			charDates.push(chart[i].chartDates.substring(2));
			cpCnt.push(chart[i].cpCnt);
			atipbzCnt.push(chart[i].atipbzCnt);
			atipitCnt.push(chart[i].atipitCnt);
			atipopCnt.push(chart[i].atipopCnt);
		}
		$("#impay-tjur-chart",context).highcharts({
			chart : {
				type : 'line',
				zoomType: 'x'
			},
			title : {
				text: messages.chart3_header, 
				x   : -20
			},
			xAxis: {
				categories: charDates
	        },
	        yAxis: {
	        	title: {
	        		text : messages.chart_y, 
	        		align: 'high', 
	        		rotation: 0
	        	} ,
	        	labels:{
	        		formatter:function(){
	        			return this.value+messages.count;
	        		}
	        	},
	        	tickInterval:1		// Y축 interval 단위
	        	
	        }, 
	        tooltip: {
	        	valueSuffix: messages.count,
	            crosshairs: true,
	            shared: true
	        },
	        series: [{
	        		name : messages.chart3_label1,
	        		data : cpCnt
	        	},{
	        		name : messages.chart3_label2,
	        		data : atipbzCnt
	        	},{
	        		name : messages.chart3_label3,
	        		data : atipitCnt
	        	},{
	        		name : messages.chart3_label4,
	        		data : atipopCnt
	        	}]
		});
	};
	module.tjurExcelDown = function(){
		if(!confirm(messages.excelDown)) return;
		var data = $('#'+ forms.searchFormTab3, context).serialize();

		impay.sendGet({
			requestUri: options.contextPath + uris.tjurExcelCount,
			data: data,
			successCallback: function(result){
				if(result > 10000){
					alert(messages.excelDownLimit);
					return;
				}
				$.fileDownload(options.contextPath + uris.tjurExcelDown + "?" + data)
			   	 .done(function () { alert('File download a success!'); })
			   	 .fail(function () { alert('File download failed!'); });
			},
			errorCallback: function(err){
				alert(err);
			}
		});	
		return false;
	};
	return module;
}(window.clmAnslMng || {}, $));