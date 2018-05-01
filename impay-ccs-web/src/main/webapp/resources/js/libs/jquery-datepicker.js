/*
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
 */
jQuery(function() {
	var defaultMinDate = new Date(2010, 0, 1);
	var defaultMaxDate = new Date(new Date().getFullYear(), 11, 31);
	
    $(".calendar").datepicker({
        changeMonth: true,
        changeYear: true
    });
    $.datepicker.regional['ko'] = {
            closeText: '닫기',
            prevText: '이전달',
            nextText: '다음달',
            currentText: '오늘',
            monthNames: ['1','2','3','4','5','6','7','8','9','10','11','12'],
            monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
            dayNames: ['일','월','화','수','목','금','토'],
            dayNamesShort: ['일','월','화','수','목','금','토'],
            dayNamesMin: ['일','월','화','수','목','금','토'],
            weekHeader: 'Wk',
            dateFormat: 'yy.mm.dd',
            firstDay: 0,
            isRTL: false,
            showMonthAfterYear: true,
            changeMonth: true,
            changeYear: true,
            minDate : defaultMinDate,
            maxDate : defaultMaxDate,
            yearSuffix: '.',
            yearRange: 'c-80:c+20'
    };
    $.datepicker.setDefaults($.datepicker.regional['ko']);
	$('.timepicker').datetimepicker({
		   timeText: '시간',
		   controlType: 'select',
		   oneLine: true,
		   timeFormat: 'HH:mm',
		   ampm: true,
		   showButtonPanel :false,
		   minDate : defaultMinDate,
           maxDate : defaultMaxDate,
	       changeMonth: true,
	       changeYear: true
		});    
	var op = {
		closeText: '닫기',
		prevText: '이전',
		nextText: '다음',
		currentText: '금일',
		//monthNames: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		//monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['01월','02월','03월','04월','05월','06월','07월','08월','09월','10월','11월','12월'],
		minDate : defaultMinDate,
        maxDate : defaultMaxDate,
		dateFormat: 'yy.mm',
		yearSuffix: ''
	};
	$.ympicker.setDefaults(op);
	$('.ympicker').ympicker();
	$('.ympicker-en').ympicker();
	
    $(".icon-cal").click(function(e){
        e.stopPropagation();
        $(this).closest("a").prev(".calendar").focus();
        $(this).closest("a").prev(".ympicker").focus();
		$(this).closest('a').prev('.timepicker').focus();
    });
});
