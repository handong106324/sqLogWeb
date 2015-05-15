<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
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
<script src="${basePath }/js/common_bas.js" type="text/javascript"></script>
<script src="${basePath}/liucunuser/baseData.js" type="text/javascript"></script>
<script src="${basePath}/js/tiaojianxuanze.js" type="text/javascript"></script>
<script src="${basePath }/js/dateTool/WdatePicker.js" type="text/javascript"></script>
<script src="${basePath }/js/jquery-1.8.3.min.js" type="text/javascript"></script>

<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript" >
	
	
	
	function doSubmit(){
		if($('#resourceName').val() == ''){
			alert("角色名不能为!");
			return false;
		}
		
		$.post("${basePath}/resource/addOrUpdate",{
			"id":$('#id').val(),
			"resourceName":$('#resourceName').val(),
			"resourceUrl":$('#resourceUrl').val(),
			"parentId":$('#parentId').val()
			
		},function(data){
			if(data.result == 'success'){
				alert("操作成功!");
				document.location.href="${basePath}/resource/list";
			}else{
				alert("提示信息",data.msg);
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
							您的位置：首页 » 新增栏目
						</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>新增栏目</span>
						</h2>
					
					<form action="" method="post" id="form1" class="form_box form_box_ad">
							<p>
								<label>栏目名：</label>
								<input class="small-inp" type="text" id="resourceName" name="resourceName" value="${bean.resourceName }"/>
								<input type="hidden" id="id" name="id" value="${bean.id}"/>
							</p>
							<p>
								<label>URL：</label>
								<input class="small-inp" type="text" id="resourceUrl" name="resourceUrl" value="${bean.resourceUrl }"/>
							</p>
							<p>
								<label>父节点：</label>
								<select id="parentId" name="parentId">
									<option value="0">-</option>
									<c:forEach items="${list}" var ="resource">
										<option value="${resource.id}" ${bean.parentId==resource.id ? "selected" : ""}>${resource.resourceName}</option>
									</c:forEach>
								</select>
							</p>
							<p>
								<input class="button white" type="button" value="保存" onclick="doSubmit()"/>
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
