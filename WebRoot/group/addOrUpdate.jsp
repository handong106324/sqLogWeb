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
<script src="${basePath }/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<link href="${basePath }/css/base_bas.css" media="screen" rel="stylesheet" type="text/css" />
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
		if($('#groupName').val() == ''){
			jAlert("提示信息","组不能为!");
			return false;
		}
		var userIdObj = document.getElementsByName("userId");
		var userId = "";
		for(var i = 0;i < userIdObj.length; i++){
			if(userIdObj[i].checked == true){
				userId +=","+userIdObj[i].value;
			}
		}
		if(userId.length >0){
			userId  = userId.substring(1);
		}
		var isManagerObj = document.getElementsByName("isManager");
		var isManager = "";
		for(var i = 0;i < isManagerObj.length; i++){
			if(isManagerObj[i].checked == true){
				isManager =isManagerObj[i].value;
				break;
			}
		}
		$.post("${basePath}/group/addOrUpdate",{
			"id":$('#id').val(),
			"groupName":$('#groupName').val(),
			"userId":userId,
			"isManager":isManager
		},function(data){
			if(data.result == 'success'){
				jAlert("提示信息","操作成功!",function(){document.location.href="${basePath}/group/list";});
			}else{
				jAlert("提示信息",data.msg);
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
							您的位置：首页 » 新增组
						</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>新增组</span>
						</h2>
					
					<form action="" method="post" id="form1" class="form_box form_box_ad">
							<p>
								<label>组名：</label>
								<input class="small-inp" type="text" id="groupName" name="groupName" value="${bean.groupName }"/>
								<input type="hidden" id="id" name="id" value="${bean.id}"/>
							</p>
							<p>
								<input class="button white" type="button" value="保存" onclick="doSubmit()"/>
			    				<input class="button white" type="button" value="取消" onclick="javascript:history.back()"/>
							</p>
							<table>
								<thead>
									<th>用户</th>
									<th>是否组长</th>
								</thead>
								<tbody>
									<c:forEach items = "${userList}" var = "user">
										<tr>
											<td><input type="checkbox" name="userId" value="${user.id}" ${user.flag > 0 ? 'checked' : ''}/>${user.realName}</td>
											<td><input type="radio" name="isManager" value="${user.id}" ${user.flag == 2 ? 'checked' : ''}/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</section>	
					
				</section>
			</section>
		</section>
	</section>
</body>
</html>
