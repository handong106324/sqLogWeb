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
	var leaveFlag = false;
	function doSubmit(){
		if($('#loginName').val() == ''){
			alert("用户名不能为!");
			return false;
		}
		if($('#password').length > 0 && $('#password').val()==''){
			alert('初始密码不能为空!');
			return false;
		}
		if($("#realName").val() == ''){
			alert("真实姓名不能为空!");
			return false;
		}
		var groupId = "";
		var isManager = "";
		var groupIdObj = document.getElementsByName("groupId");
		for(var i = 0;i<groupIdObj.length;i++){
			var obj = groupIdObj[i];
			if(obj.checked==true){
				groupId +="," + obj.value;
			}
		}
		var isManagerObj = document.getElementsByName("isManager");
		for(var i = 0; i < isManagerObj.length; i ++){
			var obj = isManagerObj[i];
			if(obj.checked==true){
				isManager +="," + obj.value;
			}
		}
		
		if(groupId.length == 0){
			alert("请选色所属组!");
			return false;
		}else{
			groupId = groupId.substring(1);
		}
		if(isManager.length >0){
			isManager = isManager.substring(1);
		}
		$.post("${basePath}/user/addOrUpdate",{
			"id":$('#id').val(),
			"realName":$('#realName').val(),
			"loginName":$('#loginName').val(),
			"password":$('#password').val(),
			"email":$('#email').val(),
			"groupId":groupId,
			"roleId":$('#roleId').val(),
			"phone":$("#phone").val(),
			"isValid":$("#isValid").find("option:selected").val(),
			"isManager":isManager
		},function(data){
			if(data.result == 'success'){
				alert("保存成功!");
						leaveFlag = false;
						document.location.href="${basePath}/user/list";
			}else{
				alert("提示信息",data.result);
			}
			
		});
		
		
	}
	
function valueChange(){
	leaveFlag = true;
}
window.onbeforeunload = function(){ 
   if(leaveFlag){
   	return "您保存了吗？";
   }  
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
							您的位置：首页 » 新增用户
						</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>新增用户</span>
						</h2>
					
					<form action="" method="post" id="form1" class="form_box form_box_ad">
							<p>
								<label>用户名：</label>
								<input onchange="valueChange()" type="text" id="loginName" name="loginName" value="${bean.loginName}"/>
								
								
								<input type="hidden" id="id" name="id" value="${bean.id}"/>
							</p>

							<c:if test="${bean.id == null || bean.id ==0}">
							<p>
								<label>密码：</label>
								<input  onchange="valueChange()"  class="small-inp" type="password" id="password" name="password" />
							</p>
							</c:if>

							<p>
								<label>真实姓名：</label>
								<input  onchange="valueChange()"  class="small-inp" type="text" id="realName" name="realName" value="${bean.realName }"/>
							</p>
							<p>
								<label>邮件地址：</label>
								<input  onchange="valueChange()"  class="small-inp" type="text" id="email" name="email" value="${bean.email }"/>
							</p>
							<p>
								<label>手机：</label>
								<input  onchange="valueChange()"  class="small-inp" type="text" id="phone" name="email" value="${bean.phone }"/>
							</p>
							<p>
								<label>是否有效：</label>
								<select id="isValid" onchange="valueChange()">
									<option value="0" 
									<c:if test="${bean.isValid eq 0 }">selected='selected'</c:if>
									>有效</option>
									<option value="2"
									<c:if test="${bean.isValid eq 2 }">selected='selected'</c:if>
									>无效</option>
								</select>
							</p>
							<p>
								<label>角色：</label>
								<select id="roleId" name="roleId"  onchange="valueChange()" >
									<option value="0">请选择</option>
						      		<c:forEach items="${roleList}" var="role">
										<option value="${role.id}" ${bean.roleId==role.id ? 'selected' : ''}>${role.roleName}</option>
									</c:forEach>
						      	</select>
							</p>
							<p align="center">
								<table>
									<thead><th>组名</th>
									<tbody>
										<c:forEach items="${groupList}" var="group">
											<tr>
												<td><input type="checkbox" name="groupId" value="${group.id}" ${group.flag >0  ? 'checked' : ''}/>${group.groupName}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</p>
							<!-- 
							<p>
								<label>组：</label>
								<select id="groupId" name="groupId">
						      		<c:forEach items="${groupList}" var="group">
										<option value="${group.id}" ${group.id==user.groupId ? 'selected':'' }>${group.groupName}</option>
									</c:forEach>
						      	</select>
							</p>
							<p>
								<label>是否组长：</label>
								<select id="isGroupManager" name="isGroupManager">
										<option value="0" ${0==user.groupId ? 'selected':'' }>否</option>
										<option value="1" ${1==user.groupId ? 'selected':'' }>是</option>
						      	</select>
							</p>
							
							 -->
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
