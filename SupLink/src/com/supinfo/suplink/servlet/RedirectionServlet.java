package com.supinfo.suplink.servlet;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.suplink.dao.jpa.JpaClickDao;
import com.supinfo.suplink.dao.jpa.JpaLinkDao;
import com.supinfo.suplink.entity.Click;
import com.supinfo.suplink.entity.Link;
import com.supinfo.suplink.util.PersistenceManager;

public class RedirectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JpaLinkDao jpa;
	JpaClickDao jpaClick;
	EntityManagerFactory emf;
       
    public RedirectionServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String requestUrl = request.getRequestURL().toString();
		requestUrl = requestUrl.trim();
		Link originalLink = jpa.getOriginalLink(requestUrl);
		if(originalLink!=null && originalLink.getIsEnable()==true)
		{
			Click newClick = new Click();
			newClick.setCountry(request.getLocale().getCountry());
			newClick.setReferer(request.getHeader("referer"));
			newClick.setClickDate(new Date());
			//newClick.setLink(originalLink);
			jpaClick.addClick(newClick);
			response.sendRedirect(originalLink.getOriginalURL().toString());
		}
		else
		{
			response.sendRedirect("../404");
			return;
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	public void init() throws ServletException
    {
		emf = PersistenceManager.getEmf();
		jpa = new JpaLinkDao(emf);
		jpaClick = new JpaClickDao(emf);
	}
	
	protected void destroy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PersistenceManager.closeEmf();
	}
}
