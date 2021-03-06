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
<script src="${basePath }/js/jquery-1.8.3.min.js" type="text/javascript"></script>
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
							<span>拷贝一个服务器配置</span>
						</h2>
						<form action="${basePath }/peizhi/add">
						<table>
						<tr>	
							<td>游戏名称</td>
							<td>
								<select id="gameName" name="gameName">
									<option value="whcl">卧虎藏龙</option>
								</select>
								<!-- <input id="gameName" type="text" name="gameName" value=""> -->
							<td>
						</tr>
						<tr>
							<td>日志服</td>
							
							<td>
								<input type="text" id="basePath" value="/data2/3dgame_log">
								<select id="logServerIp" name="logServerIp" onchange="getUrl()">
									<option value="">--请选择日志服--</option>
									<option value="30011">日志服1</option>
									<option value="30012">日志服2</option>
									<option value="30030">日志服3</option>
									<option value="36000">台服</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>服务器名称</td>
							<td><textarea rows="5" cols="100" name="serverName" id="serverName"></textarea></td>
						</tr>
							<tr>
							<td>服务器路径</td>
							<td id="dirTd"><!-- <textarea rows="5" cols="100" id="dir" name="dir"></textarea> -->
							
							<br></td>
						</tr>
						<tr>
							<td colspan="2"><input type="button" onclick="sub()" value="提交"></td>
						</tr>
						</table>
						</form>
						
						<p>
							<button></button>
						</p>
						<br>
						
						<script type="text/javascript">
							function sub(){
								var gameName = $("#gameName").val();
								var srcServer = $("#logServerIp").val();
								var serverName = $("#serverName").val();
								var dir = $('#basePath').val()+'/'+$("input[name='dir']:checked").val();
								if(gameName==null || gameName == ""){
									alert("游戏不能为空");
									return;
								}
								if(srcServer==null || srcServer == ""){
									alert("源游戏不能为空");
									return;
								}
								if(serverName==null || serverName == ""){
									alert("游戏名称不能为空");
									return;
								}
								if(dir==null || dir == ""){
									alert("路径不能为空");
									return;
								}
								
								$.post(
									"${basePath}/peizhi/add",
									{
										"gameName":gameName,
										"srcServer":srcServer,
										"serverName":serverName,
										"dir":dir
									},
									function(data){
										if(data.result == "success"){
											alert("成功");
											$("#dirTd").empty();
										}else {
											alert("失败");
											$("#dirTd").empty();
										}
									}
								).error(function(){alert("出错了")});
							}
						
							function getUrl(){
								var logPort = $("#logServerIp").find("option:selected").val();
								var basePath = $("#basePath").val();
								if(basePath == null || basePath ==''){
									alert("基础路径请填写");
									return;
								}
								if(logPort != null && logPort !=''){
									$.post("${basePath}/peizhi/getNewDir",
										{
											"port":logPort,
											"basePath":basePath
										},
										function (data){
											if(data.result.result == "success"){
												var dats = data.result.datas;
												$("#dirTd").empty();
												for(var i = 0 ; i < dats.length;i++){
													$("#dirTd").append("<br><input type='radio' name='dir' value='"+dats[i]+"'>"+dats[i]);
												}
											}
										}
									).error(function(res){alert("执行出错")});
									
								}
							}
						</script>
					</section>
				</section>
			</section>
		</section>
	</section>
</body>
</html>
