package com.sq.log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.UrlEncoded;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.sq.cache.Cache;
import com.sq.entity.Resource;

public class UserInterceptor implements Interceptor {
	Logger logger = Logger.getLogger(UserInterceptor.class);
	List<Resource> resList ;
	Map<String,Resource> urlMap;
	Map<Integer,Resource> idMap;
	public void intercept(ActionInvocation ai) {
		if (ai.getController().getSession().getAttribute("userInfo") == null && !ai.getActionKey().contains("chat/list")
				&& !ai.getControllerKey().contains("shell")
				&& (!ai.getActionKey().equals("/login") )) {
			ai.getController().setAttr("userName", Cache.getMessage("账号"));
			ai.getController().setAttr("password", Cache.getMessage("密码"));
			if(ai.getActionKey().contains("listAjax") || ai.getActionKey().contains("getSessionVal")){
				ai.getController().renderJson("data","请重新登录");
				return;
			}
			ai.getController().setAttr("msg", Cache.getMessage("请重新登录"));
			ai.getController().render("/login.jsp");
		} else {
			try {
				handleMenu(ai);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long start = System.currentTimeMillis();
			ai.invoke();
			logger.info(ai.getActionKey() + " 的时间消耗ms："+ (System.currentTimeMillis() - start));
		}

	}

	private void handleMenu(ActionInvocation ai) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		getResList();
		String key = ai.getActionKey();
		String qs = ai.getController().getRequest().getQueryString();
		if(qs != null){
			key += "?"+URLDecoder.decode(qs,"UTF-8");
		}
		key = key.substring(1,key.length());
		Resource res = urlMap.get(key);
		if(res != null){
			ai.getController().setSessionAttr("sanjiName", res.getStr("resourceName"));
			Resource parRes = idMap.get(res.getInt("parentId"));
			if(parRes != null){
				ai.getController().setSessionAttr("menuName", parRes.getStr("resourceName"));
				ai.getController().setSessionAttr("menuUrl", parRes.getStr("resourceUrl"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void getResList(){
		if(resList == null){
			urlMap = new HashMap<String, Resource>();
			idMap = new HashMap<Integer, Resource>();
			resList = Resource.dao.findAll();
			for(Resource res : resList){
				urlMap.put(res.getStr("resourceUrl"), res);
				idMap.put(res.getInt("id"), res);
			}
			
		}
		
	}
	
}
