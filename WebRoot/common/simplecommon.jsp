<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<c:set var="basePath" scope="request">${pageContext.request.contextPath}</c:set>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<html>
<!--[if IE]>
	<script src="${basePath }/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
<script src="${basePath }/js/DD_belatedPNG_0.0.8a-min.js" mce_src="${basePath }/js/DD_belatedPNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedPNG.fix('img,.png');
	</script>
<![endif]-->
<script src="${basePath }/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="../js/layer-1.8.5/layer/layer.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		var allWidth = document.body.offsetWidth - 241;
		$(".sub_area").css("width",allWidth *0.95);
	});
$(window).resize(function(){
		var allWidth = document.body.offsetWidth - 241;
		$(".sub_area").css("width",allWidth *0.95);
		if(demo){
			$("#demo").css("width",allWidth *0.9);
			demo.reinitSize(allWidth*0.9,450)
		}
}); 
function showDiv(width,height,html){
	$.layer({
		type: 1,   //0-4的选择,
		title: false,
		border: [0],
		closeBtn: [0,true],
		shadeClose: false,
		area: [width+'px', height+'px'],
		page: {
		      html: html
		}
	});
}
	function showPage(srcUrl,title,width,height,callback){
				
				 $.layer({
			            type : 2,
			            title: title,
			            shadeClose: false,
			            maxmin: false,
			            fix : false,  
			            area: [width+'px', height],                     
			            iframe: {
			                src : srcUrl
			            },
			           close: callback
			     }); 
			}
		</script>
</script>
</html>