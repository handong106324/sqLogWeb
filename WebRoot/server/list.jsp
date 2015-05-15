<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/layercommon.jsp" %>
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
<script src="${basePath }/js/dateTool/WdatePicker.js" type="text/javascript"></script>

<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
</head>
<body>
	<section id="wrapper">
		<section id="hd">
			<%@ include file="/top.jsp" %>
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
							您的位置：首页 » 日志队列查询
						</span>
					</section>	
					<section class="sub_area">
						<h2 class="filter-box">
							<span>日志队列</span>
						</h2>
						<p>队列：<select id="proName">
								<option value="0" 
									>物品</option>
								<option value="1"
								>货币</option>
								<option value="2"
								>邮件</option>
							</select>	
							状态：<select id="operateType">
								<option value="0" 
									>关闭</option>
								<option value="1"
								>开启</option>
							</select>	

								<input type="button" class="button white" value="操作" onclick="operate()">	
						<br><br><br><hr>
						<input type="button" class="button white" value="查询监控" onclick="query()">	
						<p>
							<label>物品队列</label>
							[<label id="articleSize"></label>]
						</p>
							<p>
							<label>货币队列</label>
							[<label id="currencySize"></label>]
						</p>
						
							<p>
							<label>内存</label>
							[<label id="memory"></label>]
						</p>
					</section>
				</section>
			</section>
			<script type="text/javascript">
							function operate(){
								var operateType = $("#operateType").find("option:selected").val();
								var proName = $("#proName").find("option:selected").val();
								$.post(
									"${basePath}/server/operateServer",
									{
										"operateType":operateType,
										"proName":proName
									},
									function(data){
										if(data.result == 'success'){
											alert("命令已经发出");
										}
									}
								);
							}
							
								function query(){
								var l = layer.load("请骚等……");
								$.post(
									"${basePath}/server/querySize",
									{
									},
									function(data){
										layer.close(l);
										$("#articleSize").html(data.result.articleQueueSize);
										$("#memory").html(data.result.memory);
										$("#currencySize").html(data.result.currencyQueueSize);
									}
								);
							}
						</script>
		</section>
	</section>
</body>
</html>
