package com.sq.log;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Before;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
@Before(SessionInViewInterceptor.class)
public class SessionInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		ai.invoke();

		HttpSession hs = ai.getController().getSession(false);
		if (hs != null) {
			Map session = new HashMap();
			ai.getController().setAttr("session", session);
			for (Enumeration<String> names = hs.getAttributeNames(); names
					.hasMoreElements();) {
				String name = names.nextElement();
				session.put(name, hs.getAttribute(name));
			}
		}
	}

}
