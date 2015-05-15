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
<script src="${basePath }/js/common_bas.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript" >
	
	
	function doSearch(pageIndex){
		$("#pageNumber").val(pageIndex);	
		$('#searchform').submit();
	}
	function goPage(pageIndex){
		$("#pageIndex").val(pageIndex);
		$('#searchform').submit();
	}
	function deleteBean(id){
		if(confirm("你确定要删除该记录吗?")){
			$.ajax({
				type: "get",
				url: "${basePath}/user/delete?id=" + id,
				beforeSend: function(XMLHttpRequest){},
				success: function(data){
					if(data.result == 'success'){
						alert("删除成功!");
						document.location.href="${basePath}/user/list";
					}else{
						alert("删除失败，请联系管理!");
					}
				},
				complete: function(XMLHttpRequest, textStatus){},
				error: function(){
					jAlert("提示信息","删除失败，请与管理员联系");
				}
			});
		}
	}
	function resetPassword(id){
		if(confirm("你确定要重置该记录的密码为123456吗?")){
			$.ajax({
				type: "get",
				url: "${basePath}/user/resetPass?id=" + id,
				beforeSend: function(XMLHttpRequest){},
				success: function(data){
					if(data.result == 'success'){
						jAlert("提示信息","操作成功!新的密码为：123456");
					}else{
						jAlert("提示信息","操作失败，请联系管理!");
					}
				},
				complete: function(XMLHttpRequest, textStatus){},
				error: function(){
					jAlert("提示信息","删除失败，请与管理员联系");
				}
			});
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
							您的位置：首页 » 用户列表
						</span>
					</section>	
					<section class="sub_area">
						<h2 class="filter-box">
							<span>用户列表</span>
						</h2>
						<form id="searchform" class="form_box" method="post" action="${basePath}/user/list">
							<input type="hidden" id="pageNumber" name="pageNumber" value="1"/>
							<section class="clearfix">
								<p>
									<label class="fwq_search">组名称：</label>
									<select id="groupId" name="groupId">
										<option value="">请选择</option>
										<c:forEach items="${groupList}" var="group">
											<option value="${group.id}" ${group.id==param.groupId? 'selected' : ''}>${group.groupName}</option>
										</c:forEach>
									</select>
								</p>
								<p>
									<label class="fwq_search">员工姓名：</label>
									<input type="text" name="realName" id="realName" value="${param.realName}" class="small-inp"/>
								</p>				
								<p>
									<input class="button white" type="button" value="查询" onclick="doSearch('1')"/>
								</p>
							</section>
						</form>
						<span class="blank20"></span>
						<p><a class="button white" href="${basePath }/user/showAddOrUpdate">新增+</a></p>
						<br>
						<table name="table1" id="table1" class="table_form">
							<thead>
								<th>登录名</th>
								<th>真实姓名</th>
								<th>组</th>
								<th>角色</th>
								<th>邮件地址</th>
								<th width="10%">部门</th>
								<th>是否有效</th>
								<th>电话</th>
								<th>最后登陆时间</th>
								<th width="10%">操作</th>
							</thead>
							<tbody>
								<c:forEach items="${page.list }" var="user">
									<tr>
										  <td>${user.loginName }</td>
									      <td>${user.realName }</td>
									      <td style="text-align:left">
									      	<c:forEach items="${user.groupUserList}" var="gu">
											  	<div>${gu.groupName}${gu.isManager == 1 ? '(组长)' : ''}</div>
									      	</c:forEach>
										  </td>
									      <td>${user.roleName}</td>
									      <td>${user.email}</td>
									      <td>
									      	<c:if test="${fn:length(platfromMap[user.id]) >0 }">
										      	<c:forEach items="${platfromMap[user.id]}" var="pf">
													${pf.realName}				<br>					      		
										      	</c:forEach>
									      	</c:if>
									      	<c:if test="${fn:length(platfromMap[user.id]) == 0 }">
										      	未分配部门
									      	</c:if>
									      </td>
									      <td>${user.isValid ==0?'有效':'无效'}</td>
									      <td>${user.phone}</td>
									      <td>&nbsp;<fmt:formatDate value="${user.lastLoginTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									      <td>
									      		<a href="${basePath }/user/showAddOrUpdate/${user.id}">修改</a>
									      	&nbsp;
									      		<a href="#" onclick="deleteBean('${user.id}')">删除</a>
									      	&nbsp;
									      	<c:if test="${user.roleId == 1}">
									      		<br><a href="#" onclick="resetPassword('${user.id}')">重置密码</a>
									      	</c:if>
									      </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<section class="pages">
							共${page.totalRow }条记录
							<a href="#"  onclick="doSearch('1')">首页</a>
							<c:if test="${page.pageNumber>1}"><a href="#"  onclick="doSearch('${page.pageNumber-1}')">上一页</a></c:if>
							<span>${page.pageNumber}/${page.totalPage}</span>
							<c:if test="${page.pageNumber < page.totalPage && page.totalPage>1}"><a href="#"  onclick="doSearch('${page.pageNumber+1}')">下一页</a></c:if>
							<a href="#"  onclick="doSearch('${page.totalPage}')">尾页</a>
							&nbsp;&nbsp;
						</section>
					</section>
				</section>
			</section>
		</section>
	</section>
</body>
</html>
