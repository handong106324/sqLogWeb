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

<link href="${basePath }/css/base_bas.css" media="screen" rel="stylesheet" type="text/css" />
<script src="${basePath }/chat/playerTalkMonitor.js" type="text/javascript"></script>
<!--[if IE]>
	<script src="js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
</head>
<body onbeforeunload="stopChat()">
	<!-- <input type="button" value="滚动" onclick="gundong()"> -->
	<input type="hidden" name="lastFetchTime" id="lastFetchTime" />
	<input type="hidden" name="suopin" id="suopin" /> 
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
				<h2 class="filter_box">
					<span>服务器聊天监控</span>
				</h2>
				<label id="us"></label>
				<section class="system_talk_box">
					<div  style="border:1px solid #cacaca;PADDING:0px;MARGIN:0px;OVERFLOW-Y:scroll;WIDTH:80%;HEIGHT:410px;background-color:#fff;" id = "chat_div">
					</div>
					<ul>
						<li><input id="op" class="button white" type="button" value="启动"   onclick="stopGet()"/>
							服务器
								<c:forEach items="${ servers}" var="s">
									<input type="checkbox" name="serverName" value="${s.serverName }">${s.serverName }
								</c:forEach>
							频道
							<select id="channel">
								<c:forEach items="${channels }" var="s">
									<option value="${s }">${s }
								</c:forEach>
							</select>
						
							<select id="count" style="display: none">
									<option value="10">10
									<option value="20" selected="selected">20
									<option value="30">30
									<option value="40">40
									<option value="50">50
									<option value="60">60
									<option value="70">70
									<option value="80">80
									<option value="90">90
									<option value="100">100
									<option value="110">110
							</select>
							<input id="suopinflag" class="button white" type="button" value="锁频"   onclick="suopin()"/>
							</li>
							</ul><br><ul>
							<li>
							服务器名：<input class="small-inp"  type="text" name="serverName" id="serverName" readonly="readonly"/>
							用户名：<input class="small-inp" type="text" name="userName" id="userName" readonly="readonly"/>
							角色ID：<input class="small-inp" type="text" name="playerId" id="playerId" readonly="readonly"/>
							角色名：<input class="small-inp" type="text" name="playerName" id="playerName" readonly="readonly"/>
							vip等级：<input class="small-inp" type="text" name="viplevel" id="viplevel" readonly="readonly"/>
							<input type="hidden" id="message" name="message" value=""/>
							<input type="hidden" id="time" name="time"  value=""/> 
							</li>
							</ul><br><ul>
							<li><a class="button white boxy" title="禁言" href="javascript:void(0)" onclick="showPage('jinyan_box','312','80')">禁言</a>
							<a class="button white boxy" title="封停"  href="javascript:void(0)"  onclick="showPage('forbidden','362','80')">封停</a>
							<a class="button white boxy" title="沉默" href="javascript:void(0)"  onclick="showPage('chenmo_box','312','80')">沉默</a>
							<a class="button white boxy" title="踢下线" href="javascript:void(null)" onclick="kickPlayer();">踢下线</a>
						<!-- 	<a class="button white boxy" title="聊天" href="javascript:void(0);" onclick="gmChatPlayer();">聊天</a>
							<a class="button white boxy" title="弹出消息" href="javascript:void(0)" onclick="showPage('show_msg','582','130')">弹出消息</a></li>
							 -->
					</ul>
					<section class="operate_bar clearfix">
						
						<span class="blank10"></span>
						<ul class="clearfix">
							<li>
							<!-- <a class="button white" href="javascript:void(null)" onclick="suopin()" >锁频/滚动</a> -->
							</li>
							
						</ul>
					</section>
				</section>
		</section>
			<section id="jinyan_box"  style="display: none;background-color:#eae9e9;">
				<h3 class="filter_bar" style="text-align: center;">
					禁言
				</h3>
				<table>
					<tr>
						<td>封禁时间：</td>
						<td><input class="small-inp" type="text" name="bantime" id="bantime" onkeyup="value=value.replace(/[^\d]/g,'') "  />小时，0为永久</td>
					</tr>
					<tr>
						<td>原因：</td>
						<td><input class="small-inp" type="text" id="banReason" name="banReason" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input class="button white" type="button" value="确定" onclick="banchat()" /></td>
					</tr>
				</table>
			</section>
			<section id="chenmo_box"   style="display: none;background-color:#eae9e9;">
				<h3 class="filter_bar" style="text-align: center;">
					沉默
				</h3>
				<table>
					<tr>
						<td>沉默时间：</td>
						<td><input class="small-inp" type="text" name="chenmoTime" id="chenmoTime" />小时，0为永久</td>
					</tr>
					<tr>
						<td>沉默级别：</td>
						<td><select name="chenmoLevel" id="chenmoLevel">
								<option value="1">普通沉默</option>
								<option value="2">2级沉默</option>					
							</select></td>
					</tr>
					<tr>
						<td>原因：</td>
						<td><input class="small-inp" type="text" id="reason" name="reason" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input class="button white" type="button" value="该功能未开发" disabled="disabled" onclick="chenmo()" /></td>
					</tr>
				</table>
			</section>
			<section id="forbidden" class="form_box"   style="display: none;background-color:#eae9e9;">
				<h3 class="filter_bar" style="text-align: center;">
					封停
				</h3>
				<p>
					<label>对外原因：</label>
					<input class="small-inp" type="text" id="desc" name="desc" />
				</p>
				<p>
					<label>对内原因：</label>
					<input class="small-inp" type="text" name="innerReason" id="innerReason"/>
				</p>
				<p>
					<label>限制时间：</label>
					<input class="small-inp" type="text" value="0" name="hours" id="hours"/><em>填0为永久封禁</em>
				</p>
				<p  style="text-align: center;">
					<input class="button white" type="button" value="封停"  onclick="forbidUser()"/>
				</p>
			</section>
			
			<section id="show_msg" class="form_box" style="display: none;background-color:#eae9e9;">
				<h3 class="filter_bar" style="text-align: center;">
					发送消息
				</h3>
				<table>
					<tr>
						<td>消息内容</td>
						<td><textarea id="sendms" rows="3" cols="80"></textarea></td>
					</tr>
					<tr>
						<td colspan="2">
						<input class="button white" type="button" value="发送" onclick="showMsg()"/>
						</td>
					</tr>
				</table>
			</section>
			<script type="text/javascript">
			function fetchPlayerChat(){
				var count = $("#count").find("option:selected").val();
				var channel = $("#channel").find("option:selected").val();
				var serverNames = document.getElementsByName("serverName");
				var serverName = "";
				for(var i = 0 ; i < serverNames.length;i++){
					if(serverNames[i].checked){
						serverName+=","+serverNames[i].value;
					}
				}
				$.post("${basePath}/chat/list",
				{	"count":count,
					"channel":channel,
					"serverName":serverName
				},
				function(data){
					if(data.data){
						if(data.data =='false')return;
						var arra  = data.data;
						var us = data.chatUsers;
						$("#us").html(us);
						var char_div = document.getElementById("chat_div");
						
						if(char_div.childNodes.length > 500){
							char_div.removeChild(char_div.firstChild);
						}
						//$("#log").val(char_div.childNodes.length)
						var newDiv = document.createElement("div");
						for(var i=0;i<arra.length;i++){
							var talk = arra[i];
							var lt = new Date(talk.time);
							
							var dateTime = lt.getFullYear()+"-"+(lt.getMonth()+1)+"-"+lt.getDate()+" "+lt.getHours()+":"+lt.getMinutes()+":"+lt.getSeconds();
				      		newDiv.innerHTML += "<div style='text-align:left;' onclick='setPlayerInfo(\""+talk.serverName+"\",\""+talk.playerName+"\",\""+talk.playerId+"\",\""+talk.userName+"\",\""+dateTime+"\",\""+talk.content+"\",\""+talk.vipLevel+"\")'>" +
				      			"<font style='color:blue;font-weight:blod;'>"+talk.serverName+"-- "+talk.playerName+"("+dateTime+")</font>" +
				      			"<p style='text-indent:2em;margin-bottom:10px;'>"+talk.content+"</p>"+
				      		"</div>";
					}
					
			     	var t1 = char_div.scrollTop;
					char_div.appendChild(newDiv);
					
			     	var suopin = $("#suopin").val();
			     	if(suopin == 'suopin'){
			     		char_div.scrollTop = t1;
			     	}else{
						char_div.scrollTop = char_div.scrollHeight;
			     	}
				}
		
					
				});
				
				
			}
			
			
			var query ;
			function stopGet(){
				var serverNames = document.getElementsByName("serverName");
				var serverName = "";
				for(var i = 0 ; i < serverNames.length;i++){
					if(serverNames[i].checked){
						serverName+=","+serverNames[i].value;
					}
				}
				if(serverName.length ==0){
					alert("请选择服务器");
					return;
				}
				if($("#op").val() =='启动'){
					query = setInterval(fetchPlayerChat, 1000);
					$("#op").val("暂停");
				}else if($("#op").val() =='暂停'){
					clearInterval(query);
					stopChat();
					$("#op").val("启动");
				}
			}
			
			function stopChat(){
				$.post("${basePath}/chat/close",
					{	},function(data){});
			}
			function clearData(){
				var char_div = document.getElementById("chat_div");
			}
			
			function showPage(id,width,height){
				 i = $.layer({
				    type : 1,
				    title : false,
				    offset:['150px' , ''],
				    border : false,
				    area : [width+'px',height+'px'],
				    page : {dom : '#'+id}
				});
			}
			
			function banchat() {//禁言
				var playerId = $("#playerId").val();
				if(playerId ==''){
					alert("请选择一个玩家");
					return;
				}
				var bantime = $("#bantime").val();
				if(bantime == ''){
					alert("请输入禁言时间");
					return;
				}
				$.post("${basePath}/chat/jinyan",{
						"bantime":$("#bantime").val(),
						"playerId":playerId,
						"serverName":$("#serverName").val(),
						"playerName":$("#playerName").val(),
						"time":$("#time").val(),
						"content":$("#message").val(),
						"userName":$("#userName").val()
					},function(data){
					if(data.result.result == 'success'){
						alert("操作成功!");
					}else{
						alert(data.result.result);
					}
					layer.close(i);
					//$("#jinyan_box").hide();
				});
			}
			
			function kickPlayer() {
			 	if(window.confirm("您确定要踢此人下线？")) {
			 		var playerId = $("#playerId").val();
			 		if(playerId == ''){
			 			alert("请选择一个玩家");
			 			return;
			 		}
				 	$.post("${basePath}/chat/kick",{
				 		"playerId":playerId,
						"serverName":$("#serverName").val(),
						"time":$("#time").val(),
						"userName":$("#userName").val(),
						"playerName":$("#playerName").val(),
						"content":$("#message").val()
				 	},function(data){
				 		if(data.result.result == "success"){
				 			alert("操作成功！");
				 		}else{
				 			alert("操作失败");
				 		}
				 	});
				 }
			}

function chenmo(){
	if(!validateNum($("#chenmoTime").val())){
		alert("请输入数字");
		return false;
	}
	$.post("${basePath}/chat/chenmo",{
		"chenmoTime":$("#chenmoTime").val(),
		"playerId":$("#playerId").val(),
		"serverUrl":$("#serverUrl").val(),
		"from":"chat",
		"chatTime":$("#chatTime").val(),
		"message":$("#message").val(),
		"playerName":$("#playerName").val(),
		"serverName":$("#serverName").val(),
		"userName":$("#userName").val(),
		"chenmoLevel":$("#chenmoLevel").val(),		
		"reason":$("#reason").val()
		},function(data){
		if(data.result == 'success'){
			alert("操作成功!");
		}else{
			alert("操作失败");
		}
		
		$(".boxy-wrapper").hide();
	});
}

function forbidUser(){
	
	var innerReason = $("#innerReason").val();
	var reason = $("#desc").val();
	var hours = $("#hours").val();
	var username = $("#userName").val();
	var playerId = $("#playerId").val();
	var playerName = $("#playerName").val();
	var time = $("#time").val();
	if(username == ''){
		alert("用户名不能为空");
		return ;
	}
	if(reason == ''){
		alert("对外原因不能空");
		return ;
	}
	if(innerReason == ''){
		alert("对内原因不能为空");
		return ;
	}
	if(hours == ''){
		alert("时间不能为空");
		return ;
	}
	$.post("${basePath}/chat/sealPassport",{
		"userName":username,
		"innerReason":innerReason,
		"reason":reason,
		"hours":hours,
		"playerId":playerId,
		"time":time,
		"playerName":playerName,
		"serverName":$("#serverName").val()
		},function(data){
		if(data.result.result == 'success'){
			alert("操作成功！");
			layer.close(i);
		}else{
			alert(data.result.result);
		}
	});
}

function openUser(){
	
	$.post("${basePath}/chat/liftSealPassport",{
		"userName":username,
		"serverName":$("#servers").find("option:selected").val(),
		},function(data){
		if(data.result.result == 'success'){
			alert("操作成功！");
		}else{
			alert("操作失败！");
		}
	});
}

			</script>
		</section>
	</section>
	</section>
</body>
</html>


