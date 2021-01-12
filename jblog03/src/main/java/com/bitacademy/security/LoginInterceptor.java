package com.bitacademy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bitacademy.jblog.service.UserService;
import com.bitacademy.jblog.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserVo requestVo = createRequestUserVo(request);
		UserVo authUser = userService.find(requestVo);
		
		if(authUser == null) {
			request.setAttribute("userVo", requestVo);
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("auth", authUser);
		response.sendRedirect(request.getContextPath());

		return false;
	}

	private UserVo createRequestUserVo(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo userVo = new UserVo();
		userVo.setId(id);
		userVo.setPassword(password);
		return userVo;	
	}
}
