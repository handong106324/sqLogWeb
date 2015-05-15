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
<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript" >
	
	
	function doSearch(pageIndex){
		var keyword = $("#keyword").val();
		location.replace("${basePath}/logpage/list?keyword="+keyword+"&pageNumber="+pageIndex);
	}
	function goPage(pageIndex){
		$("#pageIndex").val(pageIndex);
		$('#searchform').submit();
	}
	function deleteBean(id){
			if(confirm("你确定要删除该记录吗?")){
				$.ajax({
					type: "get",
					url: "${basePath}/logpage/delete?id=" + id,
					beforeSend: function(XMLHttpRequest){},
					success: function(data){
						if(data.result == 'success'){
							layer.alert("删除成功!",6,function(){document.location.href="${basePath}/keyword/list";});
						}else{
							layer.alert("删除失败，请联系管理!",8);
						}
					},
					complete: function(XMLHttpRequest, textStatus){},
					error: function(){
						layer.alert("删除失败，请与管理员联系",8);
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
							您的位置：首页 » 项目列表 <a href="${basePath }/logpage.doc"><font color="red">查看规则</font></a>
						</span>
					</section>	
					<section class="sub_area">
						<h2 class="filter-box">
							<span>关键字列表</span>
						</h2>
						<p><a class="button white small-inp" href="${basePath }/logpage/showAddOrUpdate">新增+</a></p>
						
					
						<p>
							<label>类型</label>
							<input type="text" value="${ keyword}" id="keyword">
							<input type="button" class="button white" value="查询" onclick="doSearch('1')">
						</p>
						<table name="table1" id="table1" class="table_form">
							<thead>
								<th>日志</th>
								<th>显示名</th>
								<th>域</th>
								<th>显示类型</th>
								<th>表头顺序</th>
								<th>整合到</th>
								<th>操作</th>
							</thead>
							<tbody>
								<c:forEach items="${page.list }" var="bean">
									<tr><td>${bean.key }
										 
										    <td>${bean.colName }</td>
										    <td>${bean.colVals }</td>
										     <td>${bean.type}</td>
										     <td>${bean.sortIndex }</td>
										     <td>${bean.attachTo }</td>
										  <td>
									      	<a href="${basePath }/logpage/showAddOrUpdate?id=${bean.id}">修改</a>
									      	<a href="${basePath }/logpage/delete?id=${bean.id}">删除</a>
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
