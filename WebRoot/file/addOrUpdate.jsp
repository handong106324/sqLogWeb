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
		var serverId = $("#serverId").find("option:selected").val();
		var serverName = $("#serverName").val();//$("#serverId").find("option:selected").text();
		var ip = $("#ip").val();
		var port = ("#port").val();
		if($('#fileName').val() == ''){
			alert("文件名称不能为空");
			return ;
		}
		if($('#dir').val() == ''){
			alert("存放目录不能为空!");
			return ;
		}
		if($('#type').val() == ''){
			alert("类别不能为空!");
			return ;
		}
		if($('#ip').val() == ''){
			alert("ip不能为空!");
			return ;
		}
		if($('#port').val() == ''){
			alert("端口不能为空!");
			return ;
		}
		$.post("${basePath}/file/addOrUpdate",{
			"id":$('#id').val(),
			"dir":$('#dir').val(),
			"type":$("#type").find("option:selected").val(),
			"serverId":serverId,
			"serverName":serverName,
			"gameName":$("#game").find("option:selected").val(),
			"ip":ip,
			"port":port,
			"webIp":$("#webIp").val(),
			"webPort":$("#webPort").val(),
			"queryType":$("#queryType").val(),
			"queryWhere":$("#queryWhere").val(),
			"fileName":$("#fileName").val()
		},function(data){
			if(data.result == 'success'){
				alert("操作成功!");
				document.location.href="${basePath}/file/list";
			}else{
				alert("提示信息："+data.result);
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
							您的位置：首页 » 服务器
						</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<c:if test="${empty bean.id}">
								<span>新增</span>
							</c:if>
							<c:if test="${not empty bean.id}">
								<span>更新</span>
							</c:if>
						</h2>
					<font style="color: red">${msg }</font>
					<form action="${basePath }/file/addOrUpdate" method="post" id="form1" class="form_box form_box_ad">
							<input type="hidden" id="id" name="id" value="${bean.id}"/>
							<p>
								<label>游戏</label>
								<select id="game" name="gameName">
									<option value="whcl"
									 <c:if test="${bean.gameName =='whcl' }">selected='selected'</c:if>
									>卧虎藏龙</option>
									<option value="wx"
										<c:if test="${bean.gameName =='wx' }">selected='selected'</c:if>
									>忘仙</option>
								</select>
							</p>
							<p>
								<label>服务器</label>
								<input type="text" value="${bean.serverName }" name="serverName" id="serverName">
							<%-- 	<select id="serverName" name="serverName">
									<c:forEach items="${servers }" var="ser">
										<option value="${ser }"
											<c:if test="${bean.serverName == ser }">selected='selected'</c:if>
										>${ser }
									</c:forEach>
								</select> --%>
							</p>
							<p>
								<label>日志类别：</label>
								<select id="type" name="type">
									<c:forEach items="${types }" var="ser">
										<option value="${ser}"
											<c:if test="${bean.type == ser }">selected='selected'</c:if>
										>${ser }
									</c:forEach>
								</select>
							</p>
							<p>
								<label>SHELL_IP：</label>
								<input class="small-inp" type="text" id="ip" name="ip" value="${bean.ip }"/>
								
							</p>
							<p>
								<label>
								shell端口：
								</label>
								<input class="small-inp" type="text" id="port" name="port" value="${bean.port }"/>
								
							</p>
							<p>
								<label>WEB_IP：</label>
								<input class="small-inp" type="text" id="webIp" name="webIp" value="${bean.webIp }"/>
								
							</p>
							<p>
								<label>
								web端口：
								</label>
								<input class="small-inp" type="text" id="webPort" name="webPort" value="${bean.webPort }"/>
								
							</p>
							<p>
								<label>日志存放文件夹：</label>
								<input class="small-inp" type="text" id="dir" name="dir" value="${bean.dir }"/>
								
							</p>
							<p>
								<label>文件名：</label>
								<input class="small-inp" type="text" id="fileName" name="fileName" value="${bean.fileName }"/>
							</p>
								<p>
								<label>查询类型</label>
								<input class="small-inp" type="text" id="queryType" name="queryType" value="${bean.queryType }"/>
							</p>
								<p>
								<label>过滤命令</label>
								<textarea rows="5" cols="50" id="queryWhere" name="queryWhere">${bean.queryWhere }</textarea>
							</p>
							<p>
								<input class="button white" type="submit" value="保存" />
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
