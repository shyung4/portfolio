package com.naver.phone.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebFilter(urlPatterns="/phone/*") // 이 주소 이하로 오는것중에 session안에 등록된 userid가 있는지 없는지 확인하는 필터 
public class UserFilter implements Filter {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("Initiating UserFilter... ");
	}
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		
		logger.info("Request Filtering with UserFilter... ");
		
			if (session.getAttribute("userid")==null ) { // userid가 없는데 /phone/main으로 들어가려고 하면
//			UserWebController u = new UserWebController(null); 
//			 u.loginError(RedirectAttributes ); 
//			red.addFlashAttribute("error", "접근할수 없는 페이지 입니다");
			httpResponse.sendRedirect("/user/loginfail"); //로그인으로 redirect
			return; 
			} 

		
		chain.doFilter(request, response);
		logger.info("Response Filtering with UserFilter... ");
		
	}
	
	@Override
	public void destroy() { 
		logger.info("Destroying UserFilter... ");
	}

}
