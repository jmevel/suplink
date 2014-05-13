package com.supinfo.suplink.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticateFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		Object userId = request.getSession().getAttribute("userId");
		if (userId!=null)
		{
			chain.doFilter(req, res);
			return;
		}
		else
		{		
			response.sendRedirect("../login");
			return;
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException
	{

	}
	
	public void destroy()
	{
		
	}
}
