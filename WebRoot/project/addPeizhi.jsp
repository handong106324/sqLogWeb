<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYpE HTML>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-Ua-compatible" content="IE=edge,chrome=1" />
<meta name="google-site-verification" content="eatjgvS3mKo14nYRl3eWmb5h5ERRKfvsgjarQazQSsw" />
<title>神奇时代统计分析平台</title>
<meta name="title" content= />
<meta name="keywords" content= "神奇时代，手机游戏，mmorpg"/>
<meta name="description" content="手机pC全平台互通,随手可玩的武侠网游相约今夏"/>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link href="${basePath }/css/base_bas.css" media="screen" rel="stylesheet" type="text/css" />
<script src="${basePath }/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${basePath }/js/common_bas.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript" >
	
	
	function doSearch(pageIndex){
		location.replace("${basePath}/project/list?pageNumber="+pageIndex);
	}
	function goPage(pageIndex){
		$("#pageIndex").val(pageIndex);
		$('#searchform').submit();
	}
	function deleteBean(id){
			if(confirm("你确定要删除该记录吗?")){
				$.ajax({
					type: "get",
					url: "${basePath}/project/delete?id=" + id,
					beforeSend: function(XMLHttpRequest){},
					success: function(data){
						if(data.result == 'success'){
							layer.alert("删除成功!",6,function(){document.location.href="${basePath}/project/list";});
						}else{
							layer.alert("删除失败，请联系管理!",8);
						}
					},
					complete: function(XMLHttpRequest, textStatus){},
					error: function(){
						layer.alert("删除失败，请与管理员联系",8);
					}
				});
			}
		}
		function upCache(){
				$.ajax({
					type: "get",
					url: "${basePath}/project/updateCache",
					beforeSend: function(XMLHttpRequest){},
					success: function(data){
						if(data.result == 'success'){
							layer.alert("更新成功!",6);
						}else{
							layer.alert("删除失败，请联系管理!",8);
						}
					},
					complete: function(XMLHttpRequest, textStatus){},
					error: function(){
						layer.alert("更新失败，请与管理员联系",8);
					}
				});
			}
</script>
</head>
<body>
	<section id="wrapper">
		<section id="hd">
			e<%@ include file="/top.jsp" %>
		</section>
		<section id="bd">
			<section class="container">
				<section id="sidemenu">
					<%@ include file="/welcome.jsp" %>
					<%@ include file="/menu.jsp" %>
					<section id="arrowbox">
						<section title="关闭左栏导航" class="sideBarButton"></section>
					</section>
				</section>
				<section class="area">
					<section class="noitce_bar">
						<span>
							您的位置：首页 » 项目列表
						</span>
					</section>	
					<section class="sub_area">
						<h2 class="filter-box">
							<span>新增一个服务器配置</span>
						</h2>
						<form action="${basePath }/project/addPeizhi">
						<table>
						<tr>	
							<td>游戏名称</td>
							<td><input id="gameName" type="text" name="gameName" value=""><td>
						</tr>
						<tr>
							<td>文件名</td>
							<td><input id="fileName" type="text" name="fileName" value=""></td>
						</tr>
						<tr>
							<td>类型</td>
							<td><input id="type" type="text" name="type" value=""></td>
						</tr>
						<tr>
							<td>服务器s</td>
							<td><textarea rows="5" cols="100" name="serverNames" id="serverNames"></textarea></td>
						</tr>
							<tr>
							<td>urls</td>
							<td><textarea rows="5" cols="100" id="urls" name="urls"></textarea></td>
						</tr>
						<tr>
							<td>端口</td>
							<td><input id="port" name="port" type="text" value=""></td>
						</tr>
							<tr>
							<td>过滤词</td>
							<td><textarea rows="5" cols="100" id="queryWhere" name="queryWhere"></textarea></td>
						</tr>
						<tr>
							<td>查询类型</td>
							<td><input id="queryType" name="queryType" type="text" value=""></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="提交"></td>
						</tr>
						</table>
						</form>
						
						<p>
							<button></button>
						</p>
						<br>
					</section>
				</section>
			</section>
		</section>
	</section>
</body>
</html>
