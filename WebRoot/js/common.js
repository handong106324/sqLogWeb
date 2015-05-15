$(function(){
	$("#tabs").tabs();
	$( "#datepicker" ).datepicker();
	//日期
	$( "#from" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#to" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#to" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#from" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	//弹出框
	$(".boxy").boxy({
		actuator:false
	});
	//游戏选择
	$(".shop").hover(function(){
		$(".game_list").show();
		$(".shop span").css("border-bottom", "1px solid #fff");
	},function(){
		$(".game_list").hide();
		$(".shop span").css("border-bottom", "1px solid #dcdcdc");
	});
	$(".sub_navtop a:last-child").css("border-right", "none");
	$(".th").click(function(){
		$(this).next("section").toggle();
	});
	
	//判断浏览器分辨率
	if((screen.width == 1024) && (screen.height == 768))
	{
	$(".sub_area").css("width","780px");
	}else{
	$(".sub_area").css("width","77.2%");	
	};
	
	


});

function validateNum(num){
	var code;  
 for (var i = 0; i < num.length; i++) {  
     //charAt()获取指定位置字符串,charCodeAt()返回该字符串的编码  
	//0的ASCII是48,9的ASCII是57  
     var code = num.charAt(i).charCodeAt(0);  
     if (code < 48 || code > 57) {  
         return false; 
     }  
     else {  
         return true; 
     }  
 } 
}