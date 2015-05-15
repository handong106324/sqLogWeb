<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>

<%
	String menuName = (String)session.getAttribute("menuName");
	String sanjiName = (String)session.getAttribute("sanjiName");
	pageContext.setAttribute("menuName",menuName);
	pageContext.setAttribute("sanjiName",sanjiName);
%>
<section id="sidebox">
	<ul>
		<c:forEach items="${resourceSessionList}" var="menu">
		<li>
			<h3 class="title">${menu.resourceName }</h3>
			<c:if test="${menu.resourceName == menuName }">
				<section class="tab-box" style="display: ">
			</c:if>
			<c:if test="${menu.resourceName != menuName }">
				<section class="tab-box" style="display:none">
			</c:if>
			
				<section class="sub-title" >
					<c:forEach items="${menu.list}" var="erji">
					
					<c:if test="${erji.hasList eq 'hasList'}">
					<h3  class="sub-list">
						<a href="javascript:void(null)" onclick="cl('${erji.resourceName }')" >${erji.resourceName }</a> 
					</h3>
					<ul class="sub-tab-box">
						<c:forEach items="${erji.list}" var="sanji">
						<li><a href="${basePath }/${sanji.resourceUrl }">${sanji.resourceName }</a></li>
						</c:forEach>
					</ul>
					<%-- <p id="${erji.resourceName }" style="display: none">
						<c:forEach items="${erji.list}" var="sanji" varStatus="st">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="${basePath }/${sanji.resourceUrl }" class="sub-title">${sanji.resourceName }</a>
														<c:if test="${ (1+ st.index) == fn:length(erji.list)}"></c:if>	<hr>
						</c:forEach>
					</p> --%>
					</c:if>
					<c:if test="${erji.hasList !='hasList'}">
						<h4>
							<c:if test="${sanjiName == erji.resourceName }">
															<a target="_self" href="${basePath }/${erji.resourceUrl }"><span style="color: blue">${erji.resourceName }</span></a>
							</c:if>
							<c:if test="${sanjiName != erji.resourceName }">
															<a target="_self" href="${basePath }/${erji.resourceUrl }">${erji.resourceName }</a>
							</c:if>
						</h4>
					</c:if>
					</c:forEach>
					<script type="text/javascript">
						function cl(nid){
							var a = $("#"+nid);
							if(a.css("display") == 'none'){
								a.css("display","");
							}else {
								a.css("display","none");
							}
						}
					</script>
				</section>
			</section>
		</li>
		</c:forEach>
	</ul>
</section>
