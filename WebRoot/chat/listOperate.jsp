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
<script type="text/javascript" >
	
	
	function doSearch(pageIndex){
		queryData(pageIndex);
	}
	function goPage(pageIndex){
		$("#pageIndex").val(pageIndex);
		$('#searchform').submit();
	}
	function delBean(id){
			if(confirm("你确定要删除该记录吗?")){
				$.ajax({
					type: "get",
					url: "${basePath }/channel/showAddOrUpdate?id=" + id,
					beforeSend: function(XMLHttpRequest){},
					success: function(data){
						if(data.result == 'success'){
							jAlert("提示信息","删除成功!",function(){document.location.href="${basePath}/channel/list";});
						}else{
							jAlert("提示信息","删除失败，请联系管理!");
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
							您的位置：首页 » 聊天操作日志查询
						</span>
					</section>	
					<section class="sub_area">
						<h2 class="filter-box">
							<span>聊天操作日志查询</span>
						</h2>
						<p>筛选：<select id="serverName">
								<option value="">全部
								<c:forEach items="${projects }" var="pro">
									<option value="${pro.serverName }"
										<c:if test="${serverName eq pro }">selected='selected'</c:if>
									>${pro.serverName }
								</c:forEach>
							</select>	
					开始时间：	<input class="small-inp" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})" type="text" id="startTime" name="endTime" value="${startTime }" />
						结束时间：	<input class="small-inp" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})" type="text" id="endTime" name="" value="${endTime }" />
						账号<input type="text" id="userName" value="${userName }">
						角色名<input type="text" id="playerName" value="${playerName }"> 
						类型<input type="text" id="operateType" value="${operateType }"> 
						操作者<input type="text" id="operator" value="${operator }"> 
						<input type="button" class="button white" value="查询" onclick="queryData('1')">	
						<br>
						
						<table name="table1" id="table1" class="table_form">
						<c:if test="${fn:length(page) ==0 }">
							<tr>
								<td>木有数据哎╮(╯▽╰)╭</td>
							</tr>
						</c:if>
							<c:if test="${fn:length(page) >0 }">
								<thead>
									<th width="10%">服务器</th>
									<th width="10%">角色名</th>
									<th width="10%">角色Id</th>
									<th width="10%">账号</th>
									<th width="10%">操作类型</th>
									
									<th width="10%">操作人</th>
									<th width="10%">聊天时间</th>
									<th width="10%">内容</th>
									<th width="10%">操作描述</th>
									<th width="10%">操作时间</th>
								</thead>
								<tbody>
									<c:forEach items="${page.list }" var="bean">
										<tr>
											  <td>${bean.serverName}</td>
											  <td>${bean.playerName}</td>
											   <td>${bean.playerId}</td>
											  <td>${bean.userName}</td>
											   <td>${bean.operateType}</td>
											  <td>${bean.operator}</td>
											   <td>${bean.time}</td>
											  <td>${bean.content}</td>
											  <td>${bean.desc}</td>
											   <td>${bean.createTime}</td>
										</tr>
									</c:forEach>
								</tbody>
							</c:if>
						
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
			
			<script type="text/javascript">
							function queryData(pageNumber){
								var serverName = $("#serverName").find("option:selected").val();
								 var startTime = $("#startTime").val();
								var endTime = $("#endTime").val();
								/* if(startTime =='' || startTime == null || endTime =='' || endTime == null){
									alert("时间范围不能为空")
									return;
								}  */
								layer.load("操作中");
								var username = $("#userName").val();
								var playerName = $("#playerName").val();
								var operator = $("#operator").val();
								var operateType = $("#operateType").val();
								
								
								location.replace("${basePath}/chat/listOperate?serverName="+serverName+"&userName="+username+"&pageNumber="+pageNumber+
								"&playerName="+playerName+"&operateType="+operateType+"&operator="+operator+"&startTime="+startTime+"&endTime="+endTime);
							}
						</script>
		</section>
	</section>
</body>
</html>
