<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-Ua-compatible" content="IE=edge,chrome=1" />
	<meta name="google-site-verification" content="eatjgvS3mKo14nYRl3eWmb5h5ERRKfvsgjarQazQSsw" />
	<title>GM管理后台--分配栏目权限</title>
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
	
	<script type="text/javascript">
		function selectAreaAll(obj){		
			var list=$('input[id^=resource_]');
			for(i=0;i<list.length;i++){
				list[i].checked=obj.checked
			}
			
		}
		function goBack(){
			document.location.href="${basePath}/role/list";
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
								您的位置：首页 » 角色栏目管理
							</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>为角色（${roleName}）设置菜单权限</span>
						</h2>
				<form method="post" id="form1" action="${basePath}/role/setResource">
				<input type="hidden" id="roleId" name="roleId" value="${roleId}" />
				<input type="hidden" id="roleName" name="roleName" value="${roleName}" />
				<table >
					<thead>
						<th><input type="checkbox" onchange="selectAreaAll(this)"/>全选</th>
					</thead>
					<tbody>
					<c:forEach items="${list}" var="yiji">
						<tr>
							  <td><input type="checkbox" id="resource_${yiji.id }" name="resourceId" value="${yiji.id}" ${yiji.state==1 ? 'checked' : ''}/>${yiji.resourceName }</td>
						</tr>
						<c:forEach items="${yiji.list}" var="erji">
							<tr>
							  <td>&nbsp;&nbsp;----><input type="checkbox" id="resource_${erji.id }" name="resourceId" value="${erji.id}" ${erji.state==1 ? 'checked' : ''}/>${erji.resourceName }</td>
							</tr>
								<c:forEach items="${erji.list}" var="sanji">
									<tr>
									  <td>&nbsp;&nbsp;--------><input type="checkbox" id="resource_${sanji.id }" name="resourceId" value="${sanji.id}" ${sanji.state==1 ? 'checked' : ''}/>${sanji.resourceName }</td>
									</tr>
								</c:forEach>
						</c:forEach>
					</c:forEach>
					</tbody>
				</table>
				<p>
					<input type="submit" value="保存" />
					<input type="button" value="返回" onclick="goBack()"/>
				</p>
				</form>
			</section>
			</section>
		</section>
    </section>
  </body>
</html>
