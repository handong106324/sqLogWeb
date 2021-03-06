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
						<section title="关闭左栏导航" class="sideBarButton"></section>
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
							<input type="hidden" value="${type }" id="type" >
							<input type="hidden" value="${conds }" id="conds" >
							${servers }
							<c:if test="${game !='wx' }">
							<select id="serverName">
								<c:forEach items="${projects }" var="pro">
									<option value="${pro.serverName }"
										<c:if test="${serverName eq pro.serverName }">selected='selected'</c:if>>${pro.serverName
										}
								</c:forEach>
								</select>
							
							</c:if>
							<c:if test="${game =='wx' }">
								<input type="search" value="" id="serverName" name="serverName" oninput="searchServers()">
							</c:if>
							<label id="la">${start_time }：</label><input class="small-inp"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})"
								type="text" id="startTime" name="endTime" value="${startTime }" />
							${end_time }： <input class="small-inp" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',alwaysUseStartDate:true})"
								type="text" id="endTime" name="" value="${endTime }" />
								<input type="hidden" value="${game }" id="game">
								<c:forEach items="${conditions }" var="cond">
									<label>${conditonShowMap[cond.showName] }</label><input	type="text" id="${cond.name }" value="${request.cond.name }"> 
								</c:forEach>
								<%--  账号<input
								type="text" id="userName" value="${userName }"> 角色名<input
								type="text" id="playerName" value="${playerName }">  --%>
								 <input	type="button" id="qb" class="button white" value="${query }" onclick="queryData('1')">
								 <br> <font style="color: red" id="log">${msg }</font> 
								<c:if test="${showDetail =='true' }">
									 <a href="#" id="sh" onclick="showSch()">查看进度</a> 
								
								</c:if>
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
							<c:if test="${showDetail }">
							记录时间
							</c:if>
							<c:if test="${!showDetail }">
							기록시간
							</c:if>
							</th>
							</thread>
							<tbody id="tbody">


								<c:forEach items="${basicList }" var="bean">
									<tr>
										<c:forEach items="${pros }" var="pro">
											<c:if test="${pro.type =='key' }">
												<td>${bean.mapInfos[pro.colVals]}</td>
											</c:if>
											<c:if test="${pro.type =='value' }">
												<td>${bean.mapInfos[pro.colName]}</td>
											</c:if>
										</c:forEach>
										<td>${bean.time}</td>
									</tr>
								</c:forEach>
							</tbody>


						</table>
					</section>
				</section>
			</section>
			<script type="text/javascript">
			
			var tip;
			function searchServers(){
				var ser = $("#serverName").val();
				if(ser==null || ser ==''){
					return;
				}
				$("#servers").css("display","block");
				$.post("${basePath}/server/queryServers",
				{"serverName":ser,
				"type":'whcl'},
				function(edata){
					var data = edata.data;
					var len = data.length;
					var names = "";
					for(var i =0; i < len ; i ++){
						names += "<a  onclick=toText('"+data[i].serverName+"') href='#'>"+data[i].listName+"</a><br>";
					}
					tip = layer.tips(names, $("#la"), {
						style : [ 'background-color:white; color:#fff',
								'#78BA32' ],
						maxWidth : 480,
						time : 0,
						fix:false,
						area :['130px', '130px'],
						closeBtn : [ 0, false ]
					});
				}
				);
			}
			
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
					var qb = $("#qb").val();
					if (qb == "查询中") {
						
						
					} else {
						
						var gameId = $("#serverName").val();
						var startTime = $("#startTime").val();
						var endTime = $("#endTime").val();
						if (startTime == '' || startTime == null
								|| endTime == '' || endTime == null) {
							alert("时间范围不能为空")
							return;
						}
				/* 		var username = $("#userName").val();
						var playerName = $("#playerName").val();
						
						
						if((username == null ||  username == '') && (playerName == null || playerName == '')){
							alert("条件不够精确");
							return;
						}
						var article = $("#articleName").val(); */
						
						var conds = $("#conds").val().split(",");
						var len = conds.length;
						var paramstr ='{';// '{"firstname":"Jesper","surname":"Aaberg","phone":["555-0100","555-0120"]}';  
						for(var i = 0 ; i < len ; i ++){
							paramstr+=('"'+conds[i]+'":"'+$('#'+conds[i]).val()+'",');
						}
						paramstr+='"serverName":"'+ gameId+'",';
						paramstr+='"type":"'+ $('#type').val()+'",';
						paramstr+='"startTime":"'+ startTime+'",';
						paramstr+='"endTime":"'+endTime+'",';
						paramstr+='"game":"'+$("#game").val()+'"';
						paramstr += '}';
						//alert(paramstr);
						var params = JSON.parse(paramstr);
						$("#qb").attr("disabled","disabled");
						$("#log").html('${querying}'+"……");
						var load = layer.load();
						$("#tbody").empty();		
						$.ajax({
							type : "get",
							url : "${basePath}/center/listAjax",
							data : 
								params
							,
							dataType : "json",
							success : function(res) {
								if(res.data=='请重新登录'){
									location.replace("${basePath}/login.jsp");
								}else if(res.data != 'success'){
									alert(res.data);
									if(load)layer.close(load);
									clearInterval(b);
								}
							}
						});
						//	location.replace("${basePath}/article/list?serverName="+gameId+"&startTime="+startTime+"&pageNumber="+pageNumber+
						//	"&endTime="+endTime+"&userName="+username+"&playerName="+playerName+"&articleName="+article);
						b = setInterval(function() {
							$.ajax({
								type : "get",
								url : "${basePath}/center/getSessionVal",
								data : {
									"type" : '${type}',
									"game" : $("#game").val()
								},
								dataType : "json",
								success : function(res) {
								if(res.data=='请重新登录'){
									location.replace("${basePath}/login.jsp");
								}
									var showDetail = '${showDetail}';
									if(load)layer.close(load);
									load=	layer.load(res.data.scheduleMsg);
										if(showDetail=='true')
											showSchedule(res.data.msg);
									if (res.data.result != 'success') {
										addTableInfo(res.data.datas);
										if(load)layer.close(load);
									} else {
										addTableInfo(res.data.datas);
										if(showDetail=='true')showSchedule("进度：" + res.data.msg
												+ "<br>共查到" + res.data.count
												+ "条数据,查询结束");
										$("#log").html(res.data.resMsg);
										clearInterval(b);
										if(load)layer.close(load);
									//remove();
										$("#qb").removeAttr("disabled");
										$("#qb").val("${query }");
									}
								}
							});
						}, 1000);
					}

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
