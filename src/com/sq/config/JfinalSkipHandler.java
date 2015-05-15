package com.sq.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class JfinalSkipHandler extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
//		if(target.indexOf(".jsp")>-1 
//				|| target.indexOf(".css") > -1 
//				|| target.indexOf(".js") > -1 
//				|| target.indexOf("/images/") > -1){
//			int index = target.lastIndexOf(".jsp");
//			if (index != -1)
//			target = target.substring(0, index);
//			nextHandler.handle(target, request, response, isHandled);
//		}
		request.getSession().removeAttribute("msg");
		nextHandler.handle(target, request, response, isHandled);
	}

}
