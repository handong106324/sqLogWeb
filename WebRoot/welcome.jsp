<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import="com.sq.entity.User" %>
<%@ include file="/common/simplecommon.jsp" %>

<%
	User user = (User)session.getAttribute("userInfo");
%>
<section class="user-box">
	<p class="clearfix">
		<span><%=user.getStr("realName") %></span>
		<a class="login-out" href="${basePath }/logout">
			${logout }
		</a>
	</p>
</section>