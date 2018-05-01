/*
   Copyright (c) 2013 SK planet.
   All right reserved.

   This software is the confidential and proprietary information of SK planet.
   You shall not disclose such Confidential Information and
   shall use it only in accordance with the terms of the license agreement
   you entered into with SK planet.
 */
(function($){
	var defaults = {totalCount:0, pageNo:1, pageSize:10, pageBlockSize:10}	
    $.fn.paging = function(opts){
        return this.each(function(){
            var options = $.extend({}, defaults, opts || {});
            var $el = $(this);            
            makePaging($el, options);
        });
    };
    
    function makePaging($el, opts){
    	var totalPage = Math.ceil(opts.totalCount/opts.pageSize);
    	var totalBlock = Math.ceil(totalPage/opts.pageBlockSize);
        var block = Math.ceil(opts.pageNo/opts.pageBlockSize);
        var startPage =  ( (block - 1) * opts.pageBlockSize ) + 1; 
        var endPage = startPage + opts.pageBlockSize - 1;
        if (endPage > totalPage) {
        	endPage = totalPage;
        }
        $el.empty();
        
        if(opts.pageNo <= 1){
        	$el.append('<a href="javascript:void(0);"><span class="pag-btn first"><span class="hide">처음 리스트로 이동</span></span></a>');
        } else {
        	var item = document.createElement('a');
        	$(item).attr('href', 'javascript:void(0);');
        	$(item).attr('pageno', 1);
        	item.onclick = function(){
        		opts.onSelectPage($(this).attr('pageno'));
    	    };
    	    $(item).append('<span class="pag-btn first"><span class="hide">처음 리스트로 이동</span></span>');    	    
        	$el.append(item);
        }
        
        if(block <= 1){
        	$el.append('<a href="javascript:void(0);"><span class="pag-btn prev"><span class="hide">이전 리스트로 이동</span></span></a>');
        } else {
        	var item = document.createElement('a');
        	$(item).attr('href', 'javascript:void(0);');
        	$(item).attr('pageno', startPage-1);
        	item.onclick = function(){
        		opts.onSelectPage($(this).attr('pageno'));
    	    };
    	    $(item).append('<span class="pag-btn prev"><span class="hide">이전 리스트로 이동</span></span>');    	    
        	$el.append(item);
        }
        
        $el.append('<span class="pag-num"></span>');
        for(var i=startPage; i <= endPage; i++){
        	var item = document.createElement('a');
        	if(opts.pageNo == i){
        		$(item).addClass('pag-selected').append(i);
        	} else {
	        	$(item).attr('href', 'javascript:void(0);').append(i);
	        	$(item).attr('pageno', i);
	        	item.onclick = function(){
	        		opts.onSelectPage($(this).attr('pageno'));
	    	    };
        	}
        	$el.find('span.pag-num').append(item);
        }
        
        if(block >= totalBlock){
        	$el.append('<a href="javascript:void(0);"><span class="pag-btn next"><span class="hide">다음 리스트로 이동</span></span></a>');
        } else {	
        	var item = document.createElement('a');
        	$(item).attr('href', 'javascript:void(0);');
        	$(item).attr('pageno', endPage+1);
        	item.onclick = function(){
        		opts.onSelectPage($(this).attr('pageno'));
    	    };
    	    $(item).append('<span class="pag-btn next"><span class="hide">다음 리스트로 이동</span></span>');    	    
        	$el.append(item);
        }
        
        if(opts.pageNo >= totalPage){
        	$el.append('<a href="javascript:void(0);"><span class="pag-btn last"><span class="hide">마지막 리스트로 이동</span></span></a>');
        } else {
        	var item = document.createElement('a');
        	$(item).attr('href', 'javascript:void(0);');
        	$(item).attr('pageno', totalPage);
        	item.onclick = function(){
        		opts.onSelectPage($(this).attr('pageno'));
    	    };
    	    $(item).append('<span class="pag-btn last"><span class="hide">마지막 리스트로 이동</span></span>');    	    
        	$el.append(item);
        }
    }
})(jQuery);
