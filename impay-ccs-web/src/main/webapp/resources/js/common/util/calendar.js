/** 
 * 달력 공통 기능 모음 
 * */

var Calendar = {};

Calendar = function() {
	
	
};

Calendar.getDateFormatter = function(dateObject){
	dateObject.datebox({
		formatter: function(date){			
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			month = month < 10 ? "0" + month : month;
			var day = date.getDate();
			day = day < 10 ? "0" + day : day;
			
			var newDate = year + '-' + month + '-' + day;
			
			return newDate;
		}, 
		parser: function(s){
			if (!s){
				return new Date();
			}
            var ss = (s.split('-'));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            }else{
                return new Date();
            }
		}
	});
	
	return dateObject; 
};