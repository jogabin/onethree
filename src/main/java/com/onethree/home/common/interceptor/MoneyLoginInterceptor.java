package com.onethree.home.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.onethree.home.user.vo.UserVO;

public class MoneyLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session  = request.getSession(true);
		
		UserVO loginUserVO = (UserVO)session.getAttribute("loginUserVO");
		
		if(loginUserVO==null || "".equals(loginUserVO.getUserId())){
			response.sendRedirect("/");
			return false;
		}
		
		//정회원 이상 , 회비연동 회원만 가능
		if(loginUserVO!=null && loginUserVO.getUserAuthor()!=9 
				&& (loginUserVO.getUserAuthor() < 2 || !"Y".equals(loginUserVO.getMoneyFlag()))){
			response.sendRedirect("/");
			return false;
		}
		
		return true;
	}
}
