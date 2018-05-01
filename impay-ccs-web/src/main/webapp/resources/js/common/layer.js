var layer = (function(module, $){
	"use strict";

	//private
	var fixed;
	var options;
	var root = module;
	var data;
	var parentModule;
	
	//public   
	module.init = function(opt){
		this.options = opt;
	};
	module.make = function(id, title, css) {
		$('body #layerholder').html($('body #layertempl').html());
		$('body #layerholder').find('.fixed-layer').attr("id", id);
		$('body #layerholder').find('.ly-wrap').css({'width': css.width, 'height': css.height});
		$('body #layerholder').find('.ly-header h4').html(title);
		$('body').addClass('stop-scrolling');
		
		this.fixed = $('body #layerholder').find("#"+id);		
		var a  = this.fixed.find("a.close");
		var bg = this.fixed.find('.bg-opacity'); 
		
		a.on('click', function(e) {
			root.close();
			e.preventDefault();
		});
		bg.on('click', function(e) {			
			root.close();
			e.preventDefault();
		});
	};
	module.load = function(url) {
		this.fixed.find('.ly-content').load(this.options.contextPath + '/common/openLayer?url='+url, function(){
			$('div.ui-datepicker').remove();
			$('.fixed-layer').on('focus', '.calendar', function(){
				$(this).removeClass('hasDatepicker').datepicker();
			});
		});
	};
	module.open = function() {
		var temp = this.fixed.find('.ly-wrap');
		
		this.fixed.fadeIn();
	
		/**Layer 위치*/
		if(temp.outerHeight() < $(document).height()) {
			temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
		} else {
			temp.css('top', '0px');
		}
		if(temp.outerWidth() < $(document).width()){
			temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		} else {
			temp.css('left', '0px');
		}
		parentModule = this.options.parentModule;
	};
	module.close = function(result) {
		this.fixed.fadeOut().remove();
		$('body').removeClass('stop-scrolling');
		$('.calendar').removeClass('hasDatepicker').datepicker();
		$('.ympicker').removeClass('hasYmpicker').ympicker();
		if(this.options !== null) {
			if(this.options.returnCallback !== 'undefined' && typeof this.options.returnCallback === 'function') {
				this.options.returnCallback(result);
			}
		}
	};
	module.getOptions = function() {
		return this.options;
	};
	
	/** 레이어 전달 데이터를 위한 변수 */
	module.getData = function(){
		return this.data;
	};
	module.setData = function(data){
		this.data = data;
	};
	
	return module;   
}(window.layer || {}, $));