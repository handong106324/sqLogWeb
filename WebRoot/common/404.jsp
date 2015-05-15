<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作正确</title>
<link href="${basePath }/css/layout.css" rel="stylesheet" type="text/css" />
<style>
body{width:auto; height:auto; background:#fff;}
</style>
<script type="text/javascript">
function zidong(){
	var bodyheight=document.documentElement.clientHeight||document.body.clientHeight;
	var odiv=document.getElementById("youbianzhongjian");
	odiv.style.height=bodyheight+0+"px";
	var odiv2=document.getElementById("youbianzhongjian_01");
	odiv2.style.height=bodyheight-28+"px";
  
	}
window.onload=zidong;
window.onresize=zidong;
</script>
</head>
<body>
<div class="right_22"  id="youbianzhongjian">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">
<div class="Busin_01" ><!--位置-->
<div class="Busin_02"><div class="Busin_03"><img src="${basePath }/images/right_02.jpg" width="21" height="15"/></div><span class="Busin_04">当前位置：短信中心 &gt;&gt; 普通短信</span></div>
</div>   
<div class="Account_24"  id="youbianzhongjian_01"><!--内容-->
<div class="Correct_01"><!--提示框开始-->
<div class="Correct_02"></div>
<div class="Correct_03">
<div class=" Correct_05">提示框</div>
<div class="Correct_06"><img src="${basePath}/images/error.jpg" width="53" height="53" /></div>
<div class="Correct_07">

<div class="Correct_08">请求的页面无法找到!</div>
<div class="Correct_10">
<ul>
<li><a href="#" onclick="javascript:history.back()">确定</a></li>
<li style=" margin-left:8px;"><a href="#" onclick="javascript:history.back()">返回</a></li>
</ul>
</div>
</div>
</div>
<div class="Correct_04"></div>
</div><!--提示框结束-->
</div>
    </td>
  </tr>
</table>
</div>
</body>
</html>