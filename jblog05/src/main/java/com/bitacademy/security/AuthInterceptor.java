package com.bitacademy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bitacademy.jblog.controller.BlogController;
import com.bitacademy.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Log LOGGER = LogFactory.getLog(BlogController.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		if(auth == null) {
			return true;
		}
		
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("auth");
		
		if(authUser == null) {	
			response.sendRedirect(request.getContextPath());
			return false;
		}
			
		if ("OWN".equals(auth.value())) {
			String id = authUser.getId();
			String path = request.getServletPath().split("/")[1];
			if(!id.equals(path)) {
				LOGGER.info(id + " 계정으로 " + path + " 계정에  잘못된 접근을 시도했습니다.");
				response.sendRedirect(request.getContextPath());
				return false;
			}
		}
			
		return true;
	}
}
