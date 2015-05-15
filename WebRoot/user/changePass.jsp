<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	pageContext.setAttribute("menuName","系统管理");
%>
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
<script src="${basePath }/js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="${basePath }/js/common_bas.js" type="text/javascript"></script>
<script src="${basePath}/liucunuser/baseData.js" type="text/javascript"></script>
<script src="${basePath}/js/tiaojianxuanze.js" type="text/javascript"></script>
<script src="${basePath }/js/dateTool/WdatePicker.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript" >
	function doSubmit(){
			var password = $('#password').val();
			var rePass = $('#rePass').val();	
			var confirmPass = $('#confirmPass').val();		
			if(password == ''){
				jAlert("提示信息","原始密码不能为空");
				return false;
			}
			if(rePass == ''){
				jAlert("提示信息","新密码不能为空!");
				return false;
			}
			if(confirmPass == ''){
				jAlert("提示信息","确认密码不能为空");
				return false;
			}
			if(rePass != confirmPass){
				jAlert("提示信息","两次密码输入不同");
				return false;
			}	
			$.post("${basePath}/user/changePass",
			{
					"password":password,
					"rePass":rePass,
					"confirmPass":confirmPass
				},
				function(data){
					if(data.result == 'success'){
						jAlert("提示信息","密码修改成功！");
						//document.location.href="${basePath}/user/modifyPassword.do";
					}else{
						jAlert("提示信息",data.result);
					}
			});
		}
	
</script>
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
							您的位置：首页 » 修改密码
						</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>修改密码</span>
						</h2>
					
						<form  method="post" id="form1" name="form1" class="form_box form_box_ad">
						<p>
							<label>原始密码：</label>
							<input class="small-inp" type="password" id="password" name="password"/>
						</p>
						<br/><br/>
						<p>
							<label>新密码：</label>
							<input  class="small-inp" type="password" id="rePass" name="rePass" />						
						</p>
						<p>
							<label>确认密码：</label>
							<input class="small-inp" type="password" id="confirmPass" name="confirmPass" />
						</p>					
						<p>
							<input class="button white" type="button" value="确认" onclick="doSubmit()"/>
		    				<input class="button white" type="reset" value="重置" />
		    				<input class="button white" type="button" onclick="history.back()" value="返回" />
						</p>
					</form>
					</section>	
					
				</section>
			</section>
		</section>
	</section>
</body>
</html>
