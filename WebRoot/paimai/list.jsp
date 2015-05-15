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
<script src="${basePath }/js/dateTool/WdatePicker.js"
	type="text/javascript"></script>
<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript" >
	function queryData(){
		location.replace("${basePath}/paimai/list?userName="+$("#userName").val()+"&playerName="+//$("#playerName").val()+
		"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&type="+$("#type").find("option:selected").val());
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
							您的位置：首页 » 拍卖行查询
						</span>
					</section>	
					<section class="sub_area">
						<h2 class="filter-box">
							<span>寄售/求购查询</span>
						</h2>
						<p>
						<select id="type">
							<option value="0">寄售</option>
							<option value="1">求购</option>
						</select>
						开始时间： <input class="small-inp" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})"
								type="text" id="startTime" name="endTime" value="${startTime }" />
							结束时间： <input class="small-inp"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})"
								type="text" id="endTime" name="" value="${endTime }" /> 账号<input
								type="text" id="userName" value="${userName }"> 
								<!--  
								角色名<input
								type="text" id="playerName" value="${playerName }"> 
								-->
								 <input
								type="button" id="qb" class="button white" value="查询"
								onclick="queryData()"> 
								</p>
						<table name="table1" id="table1" class="table_form">
							<thead>
								<th>服务器名</th>
								<th>账号</th>
								<th>物品名称</th>
								<th>金额</th>
								<th>数量</th>
								<th>具体时间</th>
							</thead>
							<tbody>
							<c:if test="${fn:length(list) ==0 }">
										<tr>
											<td colspan="6">没有查到数据</td>
										</tr>
									</c:if>
									<c:if test="${ fn:length(list) >0 }">
								<c:forEach items="${list }" var="bean">
									
										<tr>
											  <td>${bean.SERVERNAME}</td>
											  <td>${bean.USERNAME}</td>
											  <td>${bean.ARTICLENAME }</td>
											  <td>${bean.MONEY }</td>
											  <td>${bean.NUM }</td>
											   <td><fmt:formatDate value="${bean.CREATETIME }" type = "both"/>  </td>
										</tr>
									
								</c:forEach>
									</c:if>
							</tbody>
						</table>
					</section>
				</section>
			</section>
		</section>
	</section>
</body>
</html>
