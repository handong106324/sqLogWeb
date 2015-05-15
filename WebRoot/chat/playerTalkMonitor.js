var scroll_index = 0;

function gmChatPlayer(){
	var playerName = $('#playerName').val();
	var playerId = $('#playerId').val();
	var serverUrl = $('#serverUrl').val();
	alert(serverUrl);
	if(!playerName && !playerId){
		alert("请选择用户");
		return;
	}
	if(!serverUrl){
		alert("服务器地址不能为空!");
		return;
	}
	window.open("${basePath}/playerChatMonitor_gmChatPlayer.do?playerId="+playerId+"&playerName="+playerName+"&serverUrl="+serverUrl);
}
function setPlayerInfo(serverName,playerName,playerId,userName,chatTime,message,viplevel){
	$("#serverName").val(serverName);
	$("#playerName").val(playerName);
	$("#playerId").val(playerId);
	if(userName=='undefined'){
		userName = "";
	}
	$("#userName").val(userName);
	$("#time").val(chatTime);
	$("#message").val(message);
	if(viplevel=='undefined'){
		viplevel='';
	}
	$("#viplevel").val(viplevel);
}



/**
 * 向玩家发送弹出消息
 * @returns {Boolean}
 */
function showMsg(){
	var sendms = $("#sendms").val();
	var playerId = $("#playerId").val();
	if(sendms==""){
		alert("请输入发送内容");
		$("#sendms").focus();
		return false;
	}
	if(sendms.length>400){
		alert("发送的内容过多。");
		return false;
	}
	if(playerId == ''){
		alert("请选择玩家");
		return false;
	}
	$.post("${basePath}/playerChatMonitor_sendMessage.do",{		
		"sendms":sendms,
		"playerId":playerId,
		"playerName":$("#playerName").val(),
		"serverUrl":$("#serverUrl").val(),
		"type":"tanchu"
		},function(data){
		if(data.result == 'success'){
			alert("操作成功！");
		}else{
			alert("操作失败！");
		}
		$(".boxy-wrapper").hide();
	});
}
function suopin(){
	var suopin = $("#suopin").val();
	if("suopin" == suopin){
		$("#suopin").val("");
		$("#suopinflag").val("锁频");
	}else{
		$("#suopin").val("suopin");
		$("#suopinflag").val("滚动");
	}
}
/*var query ;
$(document).ready(function() {
	fetchPlayerChat();
	self.setInterval("fetchPlayerChat()",500);
	
});*/