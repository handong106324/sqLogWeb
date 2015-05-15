<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/layercommon.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYpE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-Ua-compatible" content="IE=edge,chrome=1" />
<meta name="google-site-verification"
	content="eatjgvS3mKo14nYRl3eWmb5h5ERRKfvsgjarQazQSsw" />
<title>${ sq_title}</title>
<meta name="title" content= />
<meta name="keywords" content="神奇时代，手机游戏，mmorpg" />
<meta name="description" content="手机pC全平台互通,随手可玩的武侠网游相约今夏" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link href="${basePath }/css/base_bas.css" media="screen"	rel="stylesheet" type="text/css" />
<script src="${basePath }/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${basePath }/js/common_bas.js" type="text/javascript"></script>
<script src="${basePath }/js/dateTool/WdatePicker.js"	type="text/javascript"></script>

<!--[if IE 6]>
<script src="js/DD_belatedpNG_0.0.8a-min.js" mce_src="js/DD_belatedpNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedpNG.fix('img,.png');
	</script>
<![endif]-->
<script type="text/javascript">
</script>
</head>
<body>
	<section id="wrapper">
		<section id="hd">
			<%@ include file="/top.jsp"%>
		</section>
		<section id="bd">
			<section class="container">
				<section id="sidemenu">
					<%@ include file="/welcome.jsp"%>
					<%@ include file="/menu.jsp"%>
					<section id="arrowbox">
						<section title="close" class="sideBarButton"></section>
					</section>
				</section>
				<section class="area">
					<section class="noitce_bar">
						<span> ${weizhi }</span>
					</section>
					<section class="sub_area">
						<h2 class="filter-box">
							<span>${showtype }</span>
						</h2>
						<p>
							<input type="hidden" value="${wh_type }" id="wh_type" >
							<input type="hidden" value="${conds }" id="conds" >
							<label id="la">${start_time }：</label><input class="small-inp"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})"
								type="text" id="startTime" name="endTime" value="${startTime }" />
							${end_time }： <input class="small-inp" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})"
								type="text" id="endTime" name="" value="${endTime }" />
								<input type="hidden" value="${game }" id="game">
								<c:forEach items="${conditions }" var="cond">
									<label>${conditonShowMap[cond.showName] }</label>
									<c:if test="${cond.name == 'serverid'}">
										<select id="serverid">
											<option value="">---ALL---</option>
											<c:forEach items="${projects }" var="pro">
												<option value="${pro.id}"
													<c:if test="${cond.lastVal == pro.id }">selected='selected'</c:if>
													>${pro.servername	}
											</c:forEach>
											</select>
									</c:if>
									<c:if test="${cond.name != 'serverid'}">
									<input	type="text" id="${cond.name }" value="${cond.lastVal }"> 
									</c:if>
								</c:forEach>
								<%--  账号<input
								type="text" id="userName" value="${userName }"> 角色名<input
								type="text" id="playerName" value="${playerName }">  --%>
								 <input	type="button" id="qb" class="button white" value="${query }" onclick="queryData('1')">
								 <br> <font style="color: red" id="log">${errMsg }</font> 
								 <input type="hidden" value="" id="sch">
						<table name="table1" id="table1" style="text-align: left;"
							class="table_form">
							<%-- 		<c:if test="${fn:length(list) ==0 }">
							<tr>
								<td>木有数据哎╮(╯▽╰)╭</td>
							</tr>
						</c:if> --%>
							<thread> <c:forEach items="${pros }" var="pp">
								<th>${colMessageMap[pp.colName]}</th>
							</c:forEach>
							<th>
							</th>
							</thread>
							<tbody id="tbody">


								<c:forEach items="${datas }" var="bean">
									<tr>
										<c:forEach items="${pros }" var="pro">
											<td align="cleft">${bean[pro.colName]}</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>


						</table>
					</section>
				</section>
			</section>
			<script type="text/javascript">
			
			var tip;
			
			function toText(text){
				if(tip)layer.close(tip);
				$("#serverName").val(text);
			}
			var b;
				function showSch() {
					layer.tips($("#sch").val(), $("#log"), {
						style : [ 'background-color:#78BA32; color:#fff',
								'#78BA32' ],
						maxWidth : 480,
						time : 0,
						closeBtn : [ 0, true ]
					});
				}

				function queryData(pageNumber) {
						
						var gameId = $("#serverName").find("option:selected").val();
						
						var startTime = $("#startTime").val();
						var endTime = $("#endTime").val();
						if (startTime == '' || startTime == null
								|| endTime == '' || endTime == null) {
							alert("기간범위 있어야 합니다")
							return;
						}
						
						var conds = $("#conds").val().split(",");
						var len = conds.length;
						var paramstr ="";
						for(var i = 0 ; i < len ; i ++){
							if(conds[i] == 'serverid'){
								paramstr+=(conds[i]+'='+$('#'+conds[i]).find("option:selected").val()+'&');
							}else {
								paramstr+=(conds[i]+'='+$('#'+conds[i]).val()+'&');
							}
						}
						paramstr+='serverName='+ gameId+'&';
						paramstr+='wh_type='+ $('#wh_type').val()+'&';
						paramstr+='startTime='+ startTime+'&';
						paramstr+='endTime='+endTime+'&';
						paramstr+='game='+$("#game").val()+'';
						location.replace("${basePath}/center/list_kor?"+paramstr);

				}

				function showSchedule(t) {
					layer.tips(t, $("#log"), {
						style : [ 'background-color:#78BA32; color:#fff',
								'#78BA32' ],
						maxWidth : 480,
						time : 3,
						closeBtn : [ 0, true ]
					});
					$("#sch").val(t);
				}

				function remove() {
				
					$.ajax({
						type : "get",
						url : "${basePath}/center/cancelQuery",
						data : {
							"type" : '${type}'
						},
						dataType : "json",
						success : function(res) {
								alert(res.result);
						}
					});
				}
				function addTableInfo(tr) {
					$("#tbody").append(tr);
				}
			</script>
		</section>
	</section>
</body>
</html>
