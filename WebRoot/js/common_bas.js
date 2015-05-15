$(function(){
	var F=$("#sidebox"),H=$("#arrowbox");$w=$("#sidemenu");
	
	var D="side-collapsed",
	
	A=$w.hasClass(D),
	
	C=$(".sideBarButton"),
	arrow=$("<section class=\"btn\">&nbsp;</section>").css("cursor","pointer").click(G).hover(function(){

	C.addClass("sidebarButton-over")},

	function(){
		C.removeClass("sidebarButton-over")
	});
	C.append(arrow);
	
	function G(){
			if(A){
				C.removeClass("sidebarButton-over");
			if(navigator.userAgent.indexOf("MSIE 6")>-1&&navigator.userAgent.indexOf("MSIE 8")<0)F.animate({left:"240"});
			$w.animate({paddingLeft:"240"},function(){
				$w.toggleClass(D)
			});
			A=false;
				}else{
				C.removeClass("sidebarButton-over");
			if(navigator.userAgent.indexOf("MSIE 6")>-1&&navigator.userAgent.indexOf("MSIE 8")<0)F.animate({left:0});
			$w.animate({paddingLeft:0},function(){
				$w.toggleClass(D)
				});
			A=true;
		};
	};
	
	
	$("#sidebox h3").click(function(){
        $(this).next(".tab-box").toggle();
        return false; 
    });
	
	$(".sub-title h3").click(function(){
        $(this).next(".sub-tab-box").toggle();
        return false; 
    });
	
//	$(".button.white").click(function(){
//		$("input[name=canDis]").removeClass(".button white");
//		$("input[name=canDis]").attr("disabled","disabled");
//	})
	
	$("tr:even").css({background:"#f2f1f1"});
	
	$(".ajax_hash").hover(function(){
		$(".ajax_box").show();
		$(".ajax_box").css("border-top", "none");
		},function(){
		$(".ajax_box").hide();	
	});

	$(".popup_box").hover(function(){
		$(this).find(".options_box").show();
		$(this).find(".filter_inside").css("border-bottom","1px solid #fff");
		},function(){
		$(this).find(".options_box").hide();
		$(this).find(".filter_inside").css("border-bottom", "1px solid #bfbfbf");				
	});
	
	$(".filter-chosen-box").click(function(){
		$(".wrapfix").toggle();
		return false;
	});
	
	$(".wrapfix").next(".wrapfix").css("border-top","none");
});
function disableButton(buttonId,className){
	$("input[name="+buttonId+"]").removeClass(className);
	$("input[name="+buttonId+"]").attr("disabled","disabled");
}

function enableButton(buttonId,className){
	$("input[name="+buttonId+"]").addClass(className);
	$("input[name="+buttonId+"]").removeAttr("disabled");
}
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
} 

function showDiv(bgId,divId){
	$("#"+bgId).css({display:"block",height:$(document).height()});
  	var yscroll =document.documentElement.scrollTop;
  	$("#"+divId).css("top","100px");
 	$("#"+divId).css("display","block");
  	document.documentElement.scrollTop=0;
	
}

