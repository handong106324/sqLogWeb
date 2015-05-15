<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
 %>
<!DOCTYpE HTML>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-Ua-compatible" content="IE=edge,chrome=1" />
<meta name="google-site-verification" content="eatjgvS3mKo14nYRl3eWmb5h5ERRKfvsgjarQazQSsw" />
<title>${sq_title }</title>
<meta name="title" content= />
<meta name="keywords" content= "神奇时代，手机游戏，mmorpg"/>
<meta name="description" content="手机pC全平台互通,随手可玩的武侠网游相约今夏"/>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link href="${basePath }/css/base.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${basePath }/css/jquery-ui.css" rel="stylesheet" media="screen" type="text/css"/>
<link href="${basePath }/css/boxy.css" rel="stylesheet" media="screen" type="text/css"/>
<script src="${basePath }/js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="${basePath }/js/jquery-ui.js" type="text/javascript"></script>
<script src="${basePath }/js/jquery.boxy.js" type="text/javascript"></script>
<script src="${basePath }/js/common.js" type="text/javascript"></script>
<!--[if IE]>
	<script src="${basePath }/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
<script src="${basePath }/js/DD_belatedpNG_0.0.8a-min.js" mce_src="${basePath }/js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<style type="text/css">
	html{background-color:#d2f7c7;}
	#bgc-blue{height:100%;}
	.data_login{background:url(${basePath }/images/data_login_bg.png) no-repeat;width:375px;height:155px;margin:0 auto;margin-top:200px;padding:135px 0 0 125px;}
	.data_login .form_box p{height:50px;}
	.data_login .form_box p label{color:#fff;}
	.data_login .form_box p .small-inp{border:1px solid #10659e;width:180px;height:25px;}
	.data_login .form_box p .data{margin-left:50px;}
	.data_login .form_box p img{vertical-align:middle;}
</style>
<script type="text/javascript">
	function login(){
		$("#loginForm").submit();
	}
</script>
</head>
<body id="bgc-blue">
	<section id="wrapper">
		<section id="bd">
			<section class="container">
				<section class="data_login">
					<form class="form_box" id="loginForm" action="${basePath }/login" method="post">
						<font color="red">${msg }</font>
						<section class="clearfix">
							<p>
								<label>${userName }：</label>
								<input class="small-inp id" type="text" id="loginName" name="loginName" value="${loginName}"/>
				    			<c:if test="${nameInfo=='fail'}">
				    				<img alt="" src="${basePath}/images/icon_false.png">
				    			</c:if>
				    			<c:if test="${nameInfo=='success'}">
				    				<img alt="" src="${basePath}/images/icon_right.png">
				    			</c:if> 
							</p>
							<p>
								<label>${password }：</label>
								<input class="password small-inp" type="password" id="password" name="password" value=""/>
						    	<c:if test="${passInfo=='fail'}">
				    				<img alt="" src="${basePath}/images/icon_false.png">
				    			</c:if>
				    			<c:if test="${passInfo=='success'}">
				    				<img alt="" src="${basePath}/images/icon_right.png">
				    			</c:if>
							</p>
						</section>
						<section class="clearfix">
							<p>
								<input class="data" type="image" onclick="login()" src="${basePath }/images/btn_data.png"/>
							</p>
						</section>
					</form>
				</section>
			</section>
		</section>
	</section>
</body>
</html>
