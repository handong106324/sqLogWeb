$(function(){
		 $(".close").click(function(){
		  $("#faqbg").css("display","none");
		  $("#faqdiv").css("display","none");
		 });
	});
	function disableButton(buttonId,className){
		$("input[name="+buttonId+"]").removeClass(className);
		$("input[name="+buttonId+"]").attr("disabled","disabled");
	}

	function enableButton(buttonId,className){
		$("input[name="+buttonId+"]").addClass(className);
		$("input[name="+buttonId+"]").removeAttr("disabled");
	}
