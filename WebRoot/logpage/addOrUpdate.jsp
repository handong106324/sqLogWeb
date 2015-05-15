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
		if($('#keyword').val() == ''){
			alert("项目名称不能为!");
			return false;
		}
		$.post("${basePath}/logpage/addOrUpdate",{
			"id":$('#id').val(),
			"keyword":$('#keyword').val(),
			"game":$("#game").find("option:selected").val(),
			"isUse":1
		},function(data){
			if(data.result == 'success'){
				alert("操作成功!");
				document.location.href="${basePath}/logpage/list";
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
					<font color="red">${msg }</font>
					<form action="${basePath }/logpage/addOrUpdate" method="post" id="form1" class="form_box form_box_ad">
							<input type="hidden" id="id" name="id" value="${bean.id}"/>
									<p>
									
									
							<label>类型</label>
							<input  id="key" name="key" value="">
							<%-- <select id="key" name="key">
								<c:forEach items="${types }" var="item">
									<option value="${item.intro }" 
										<c:if test="${item.intro == bean.key }">selected='selected'</c:if>
									>${item.intro }</option>
								</c:forEach>
							</select> --%>
						</p>
						<p>
						 <label>游戏</label>
						 <select id="game" name="game">
						 	<option value="whcl"
									 <c:if test="${bean.gameName =='whcl' }">selected='selected'</c:if>
									>卧虎藏龙</option>
									<option value="wx"
										<c:if test="${bean.gameName =='wx' }">selected='selected'</c:if>
									>忘仙</option>
						 </select>
						</p>
						<p>
							<label>该日志形式</label>
							<select id="type" name="type">
								<option value="key" 
									<c:if test="${bean.type =='key' }">selected="selected"</c:if>
								>键值对</option>
								<option value="keys" 
									<c:if test="${bean.type =='keys' }">selected="selected"</c:if>
								>键-多值对</option>
								<option value="value"
									<c:if test="${bean.type =='value' }">selected="selected"</c:if>
								>值</option>
								<option value="contain"
									<c:if test="${bean.type =='contain' }">selected="selected"</c:if>
								>包含</option>
							</select>
						</p>
							<p>
								<label>列显示名：</label>
								<input class="small-inp" type="text" id="colName" name="colName" value="${bean.colName }"/>
							</p>
								<p>
								<label>表头顺序编号</label>
								<input type="text" value="${bean.sortIndex }" onkeyup= "value=value.replace(/[^\d]/g, '')"  id="sortIndex" name="sortIndex">
							</p>
							
							<p>
								<label>域</label>
								<textarea id="colVals" name="colVals" rows="5" style="width: 100%">${bean.colVals }</textarea>逗号隔开；
							</p>
							<p>
								<label>整合到</label>
								<input type="text" value="${bean.attachTo }"  id="attachTo" name="attachTo">
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
