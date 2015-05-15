function selectServer(id, name){
	$("#li_serverId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_serverId'>" +
			"<input type='hidden' name='serverId' id='serverId' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_serverId\")'><span>服务器:"+name+"</span></a>" +
		"</li>");
}
function selectXitong(id, name){
	$("#li_xitong").remove();
	$("#yixuantiaojian").append(
		"<li id='li_xitong'>" +
			"<input type='hidden' name='xitong' id='xitong' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_xitong\")'><span>系统:"+name+"</span></a>" +
		"</li>");
}
function selectBanben(id, name){
	$("#li_banben").remove();
	$("#yixuantiaojian").append(
		"<li id='li_banben'>" +
			"<input type='hidden' name='banben' id='banben' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_banben\")'><span>版本:"+name+"</span></a>" +
		"</li>");
}
function selectBanben_duoxuan(id, name){
	$("#li_banben_"+id).remove();
	$("#yixuantiaojian").append(
		"<li id='li_banben_"+id+"'>" +
			"<input type='hidden' name='banben' id='banben"+id+"' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_banben_"+id+"\")'><span>版本:"+name+"</span></a>" +
		"</li>");
}
function selectChannel(id, name){
	$("#li_channelId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_channelId'>" +
			"<input type='hidden' name='channel' id='channel' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_channelId\")'><span>渠道:"+name+"</span></a>" +
		"</li>");
}
function selectJixing(id, name){
	$("#li_jixingId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_jixingId'>" +
			"<input type='hidden' name='jixing' id='jixing' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_jixingId\")'><span>机型:"+name+"</span></a>" +
		"</li>");
}
function selectRji(id, name){
	$("#li_rji").remove();
	$("#yixuantiaojian").append(
		"<li id='li_rji'>" +
			"<input type='hidden' name='rji' id='rji' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_rji\")'><span>R级:"+name+"</span></a>" +
		"</li>");
}

function selectRji_duoxuan(id, name){
	$("#li_rji_"+id).remove();
	$("#yixuantiaojian").append(
		"<li id='li_rji_"+id+"'>" +
			"<input type='hidden' name='rji' id='rji"+id+"' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_rji_"+id+"\")'><span>R级:"+name+"</span></a>" +
		"</li>");
}
function selectNiandu(id, name){
	$("#li_niandu").remove();
	$("#yixuantiaojian").append(
		"<li id='li_niandu'>" +
			"<input type='hidden' name='niandu' id='niandu' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_niandu\")'><span>R级:"+name+"</span></a>" +
		"</li>");
}
function selectNiandu_duoxuan(id, name){
	$("#li_niandu_"+id).remove();
	$("#yixuantiaojian").append(
		"<li id='li_niandu_"+id+"'>" +
			"<input type='hidden' name='niandu' id='niandu"+id+"' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_niandu_"+id+"\")'><span>R级:"+name+"</span></a>" +
		"</li>");
}
function selectModel_duoxuan(id, name){
	$("#li_model_"+id).remove();
	$("#yixuantiaojian").append(
		"<li id='li_model_"+id+"'>" +
			"<input type='hidden' name='modelId' id='modelId"+id+"' value='"+id+"'>" +
			"<input type='hidden' name='modelName' id='modelName"+id+"' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_model_"+id+"\")'><span>功能模块:"+name+"</span></a>" +
		"</li>");
}

function selectItem_duoxuan(id, name){
	$("#li_item_"+id).remove();
	$("#yixuantiaojian2").append(
		"<li id='li_item_"+id+"'>" +
			"<input type='hidden' name='itemId' id='itemId"+id+"' value='"+id+"'>" +
			"<input type='hidden' name='itemName' id='itemName"+id+"' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_item_"+id+"\")'><span>"+name+"</span></a>" +
		"</li>");
}
function selectArticle_duoxuan(id, name){
	$("#li_article_"+id).remove();
	$("#yixuantiaojian2").append(
		"<li id='li_article_"+id+"'>" +
			"<input type='hidden' name='articleId' id='articleId"+id+"' value='"+id+"'>" +
			"<input type='hidden' name='articleName' id='articleName"+id+"' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_article_"+id+"\")'><span>"+name+"</span></a>" +
		"</li>");
}
function removeObject(id){
	$("#"+id).remove();
}
function selectDateType(value){
	
	if(value=='day'){
		document.getElementById("zhou_div").style.display="none";
		document.getElementById("yue_div").style.display="none";
		document.getElementById("time_div").style.display="";
		
		document.getElementById("targetzhou_div").style.display="none";
		document.getElementById("targetyue_div").style.display="none";
		document.getElementById("targettime_div").style.display="";
	}else if(value=='week'){
		document.getElementById("zhou_div").style.display="";
		document.getElementById("yue_div").style.display="none";
		document.getElementById("time_div").style.display="none";
		
		document.getElementById("targetzhou_div").style.display="";
		document.getElementById("targetyue_div").style.display="none";
		document.getElementById("targettime_div").style.display="none";
	}else if(value=='month'){
		document.getElementById("zhou_div").style.display="none";
		document.getElementById("yue_div").style.display="";
		document.getElementById("time_div").style.display="none";
		document.getElementById("targetzhou_div").style.display="none";
		document.getElementById("targetyue_div").style.display="";
		document.getElementById("targettime_div").style.display="none";
	}
}
function selectDateType2(value){
	
	if(value=='day'){
		document.getElementById("zhou_div").style.display="none";
		document.getElementById("yue_div").style.display="none";
		document.getElementById("time_div").style.display="";
	}else if(value=='week'){
		document.getElementById("zhou_div").style.display="";
		document.getElementById("yue_div").style.display="none";
		document.getElementById("time_div").style.display="none";
	}else if(value=='month'){
		document.getElementById("zhou_div").style.display="none";
		document.getElementById("yue_div").style.display="";
		document.getElementById("time_div").style.display="none";
	}
}
function selectWeidu(id, name){
	$("#li_weidu").remove();
	$("#yixuantiaojian").append(
		"<li id='li_weidu'>" +
			"<input type='hidden' name='weidu' id='weidu' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_weidu\")'><span>统计维度:"+name+"</span></a>" +
		"</li>");
}
function selectQuanbu(id, name){
	$("#li_quanbu").remove();
	$("#yixuantiaojian").append(
		"<li id='li_quanbu'>" +
			"<input type='hidden' name='quanbu' id='quanbu' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_quanbu\")'><span>"+name+"</span></a>" +
		"</li>");
}


function liSelectWeidu(id, name){
	$("#li_weidu").remove();
	$("#yixuantiaojian").append(
		"<li id='li_weidu'>" +
			"<input type='hidden' name='bean.countType' id='countType' value='"+id+"'>" +
			"<input type='hidden' name='bean.countTypeName' id='countTypeName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_weidu\")'><span>统计维度:"+name+"</span></a>" +
		"</li>");
}
function liSelectServer(id, name){
	$("#li_serverId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_serverId'>" +
			"<input type='hidden' name='bean.serverId' id='serverId' value='"+id+"'>" +
			"<input type='hidden' name='bean.serverName' id='serverName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_serverId\")'><span>服务器:"+name+"</span></a>" +
		"</li>");
}

function liSelectXitong(id, name){
	$("#li_xitong").remove();
	$("#yixuantiaojian").append(
		"<li id='li_xitong'>" +
			"<input type='hidden' name='bean.xitong' id='xitong' value='"+id+"'>" +
			"<input type='hidden' name='bean.xitongName' id='xitongName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_xitong\")'><span>系统:"+name+"</span></a>" +
		"</li>");
}
function liSelectBanben(id, name){
	$("#li_banben").remove();
	$("#yixuantiaojian").append(
		"<li id='li_banben'>" +
			"<input type='hidden' name='bean.banben' id='banben' value='"+id+"'>" +
			"<input type='hidden' name='bean.banbenName' id='banbenName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_banben\")'><span>版本:"+name+"</span></a>" +
		"</li>");
}

function liSelectChannel(id, name){
	$("#li_channelId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_channelId'>" +
			"<input type='hidden' name='bean.channel' id='channel' value='"+id+"'>" +
			"<input type='hidden' name='bean.channelName' id='channelName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_channelId\")'><span>渠道:"+name+"</span></a>" +
		"</li>");
}
function liSelectJixing(id, name){
	$("#li_jixingId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_jixingId'>" +
			"<input type='hidden' name='bean.jixing' id='jixing' value='"+id+"'>" +
			"<input type='hidden' name='bean.jixingName' id='jixingName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_jixingId\")'><span>机型:"+name+"</span></a>" +
		"</li>");
}
function liSelectRji(id, name){
	$("#li_rji").remove();
	$("#yixuantiaojian").append(
		"<li id='li_rji'>" +
			"<input type='hidden' name='bean.rji' id='rji' value='"+id+"'>" +
			"<input type='hidden' name='bean.rjiName' id='rjiName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_rji\")'><span>R级:"+name+"</span></a>" +
		"</li>");
}
/*function liSelectNiandu(id, name){
	$("#li_niandu").remove();
	$("#yixuantiaojian").append(
		"<li id='li_niandu'>" +
			"<input type='hidden' name='bean.niandu' id='niandu' value='"+id+"'>" +
			"<input type='hidden' name='bean.nianduName' id='nianduName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_niandu\")'><span>R级:"+name+"</span></a>" +
		"</li>");
}*/
function selectStatobj(id, name){
	$("#li_statobjId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_statobjId'>" +
			"<input type='hidden' name='statobj' id='statobj' value='"+id+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_statobjId\")'><span>统计对象:"+name+"</span></a>" +
		"</li>");
}
function liSelectStatobj(id, name){
	$("#li_statobjId").remove();
	$("#yixuantiaojian").append(
		"<li id='li_statobjId'>" +
			"<input type='hidden' name='bean.statobj' id='statobj' value='"+id+"'>" +
			"<input type='hidden' name='bean.statobjName' id='statobjName' value='"+name+"'>" +
			"<a href='javascript:void(null)' onclick='removeObject(\"li_statobjId\")'><span>统计对象:"+name+"</span></a>" +
		"</li>");
}