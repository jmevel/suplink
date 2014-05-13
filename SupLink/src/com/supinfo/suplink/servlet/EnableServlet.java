package com.supinfo.suplink.servlet;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.suplink.dao.jpa.JpaLinkDao;
import com.supinfo.suplink.entity.Link;
import com.supinfo.suplink.util.PersistenceManager;


public class EnableServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf;
	JpaLinkDao jpa;

    public EnableServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    String linkId =request.getParameter("linkId");
	    String isEnable = request.getParameter("isEnable");
	    
	    try
	    {
	    	long id= Long.parseLong(linkId);
	    	Link link = jpa.getLinkById(id);
	    	
	    	if(isEnable==null)
	    	{
	    		if(link.getIsEnable()==false)
	    		{
	    			response.sendRedirect("home");
	    		}
	    		else
	    		{
	    			link.setIsEnable(false);
	    			jpa.updateLink(link);
	    		}
		    	
	    	}
	    	//la checkbox est checked --> on "enable" le lien
	    	else
	    	{
	    		//le lien était déjà "enable" donc pas besoin de changer et merger
		    	if(link.getIsEnable()==true)
		    	{
		    		 response.sendRedirect("home");
		    	}
		    	else
		    	{
		    		link.setIsEnable(true);
		    		jpa.updateLink(link);
		    	}
	    	}
	    }
	    catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
	    response.sendRedirect("home");
	}

	public void init() throws ServletException
    {
		emf = PersistenceManager.getEmf();
		jpa = new JpaLinkDao(emf);
	}
	
	protected void destroy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersistenceManager.closeEmf();
	}
}
