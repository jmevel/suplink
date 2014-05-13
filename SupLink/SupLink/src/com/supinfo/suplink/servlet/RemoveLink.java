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

public class RemoveLink extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf;
	JpaLinkDao jpa;
       

    public RemoveLink() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String linkId =request.getParameter("removeLinkId");
		System.out.println(linkId);
		try
		{
		    long id= Long.parseLong(linkId);
		    Link link = jpa.getLinkById(id);
		    if(link!=null)
		    {
		    	jpa.removeLink(link.getId());
		    }
		    	
		}
		catch(Exception ex)
		{
			System.out.println("Error :"+ex.getMessage());
		}
		finally
		{
			response.sendRedirect("home");
		}
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
