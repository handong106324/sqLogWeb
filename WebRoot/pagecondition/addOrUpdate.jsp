<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYpE HTML>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-Ua-compatible" content="IE=edge,chrome=1" />
<meta name="google-site-verification" content="eatjgvS3mKo14nYRl3eWmb5h5ERRKfvsgjarQazQSsw" />
<title>神奇时代日志查询系统</title>
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
<script type="text/javascript" >
	
	
	
	function doSubmit(){
		
		$.post("${basePath}/pagec/addOrUpdate",{
			"id":$('#id').val(),
			"roleName":$('#roleName').val()
		},function(data){
			if(data.result == 'success'){
				jAlert("提示信息","操作成功!",function(){document.location.href="${basePath}/role/list";});
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
							您的位置：首页 » 新增条件
						</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>新增条件</span>
						</h2>
					<font>${error }</font>
					<form action="${basePath }/pagecondition/addOrUpdate" method="post" id="form1" class="form_box form_box_ad">
							<p>
								<label>游戏：</label>
								<input class="small-inp" type="text" id="game" name="game" value="${bean.game }"/>
								<input type="hidden" id="id" name="id" value="${bean.id}"/>
							</p>
							<p>
								<label>类型：</label>
								<input class="small-inp" type="text" id="type" name="type" value="${bean.type }"/>
							</p>
							<p>
								<label>代码操作符：</label>
								<input class="small-inp" type="text" id="name" name="name" value="${bean.name }"/>
							</p>
							<p>
								<label>页面展示标签：</label>
								<input class="small-inp" type="text" id="showName" name="showName" value="${bean.showName }"/>
							</p>
							<p>
								<label>排序号：</label>
								<input class="small-inp" type="text" id="sortIndex" name="sortIndex" value="${bean.sortIndex }"/>
							</p>
							<p>
								<input class="button white" type="submit" value="保存"/>
			    				<input class="button white" type="button" value="取消" onclick="javascript:history.back()"/>
							</p>
						</form>
					</section>	
					
				</section>
			</section>
		</section>
	</section>
</body>
</html>
