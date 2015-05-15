<%@page import="java.net.URI"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=GBK"  %>
<%

	request.setCharacterEncoding("utf8");
	String userName = request.getParameter("userName");
	String serverName = request.getParameter("serverName");
	String vipLevel = request.getParameter("vipLevel");
	String playerName = request.getParameter("playerId");
	playerName = URLDecoder.decode(playerName,"UTF-8");
	serverName = URLDecoder.decode(serverName,"UTF-8");
	userName = URLDecoder.decode(userName,"UTF-8");
	
	//userName = URLEncoder.encode(userName,"GBK");
	//playerName = URLEncoder.encode(playerName,"GBK");
	//serverName = URLEncoder.encode(serverName,"GBK");
	System.out.println();
	System.out.println(serverName);
	System.out.println(vipLevel);
	System.out.println(playerName);
 %>
<!DOCTYpE HTML>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-Ua-compatible" content="IE=edge,chrome=1" />
<meta name="google-site-verification" content="eatjgvS3mKo14nYRl3eWmb5h5ERRKfvsgjarQazQSsw" />
<title>${sq_title }</title>
<meta name="title" content= />
<meta name="keywords" content= "神奇时代，手机游戏，mmorpg"/>
<meta name="description" content="手机pC全平台互通,随手可玩的武侠网游相约今夏"/>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<script type="text/javascript">
	function ld(){
		window.location.href = "http://w.sqage.com/m/service_index2.jsp?playerId=93&playerName=<%=playerName%>&userName=<%=userName%>&serverName=<%=serverName%>&vipLevel=7"
	}
</script>
</head>
<body onload="ld()">
</body>
</html>
