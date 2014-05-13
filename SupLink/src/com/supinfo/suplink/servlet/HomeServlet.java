package com.supinfo.suplink.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.supinfo.suplink.dao.jpa.JpaLinkDao;
import com.supinfo.suplink.dao.jpa.JpaUserDao;
import com.supinfo.suplink.entity.Link;
import com.supinfo.suplink.entity.User;
import com.supinfo.suplink.util.PersistenceManager;


public class HomeServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf;
	JpaLinkDao jpa;
	JpaUserDao userJpa;
       

    public HomeServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Long userId = Long.parseLong(session.getAttribute("userId").toString());
		List<Link> links = jpa.getLinksByUserId(userId);
		request.setAttribute("links", links);
		this.getServletContext().getRequestDispatcher("/WEB-INF/auth/home.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		

		String linkName= request.getParameter("linkName");
		String UrlTyped = request.getParameter("Url");
		if(!UrlTyped.startsWith("http://"))
		{
			UrlTyped = new String("http://").concat(UrlTyped);
		}
		try
		{
			URL originalUrl = new URL(UrlTyped);
			if(linkName!=null && originalUrl!=null)
			{
				Link newLink= new Link();
				newLink.setName(linkName);
				newLink.setOriginalURL(originalUrl);
				
				HttpSession session = request.getSession();
				Long userId = Long.parseLong(session.getAttribute("userId").toString());
				System.out.println(userId);
				User user = userJpa.getUserById(userId);
				newLink.setUser(user);
				
				jpa.addLink(newLink);;
				response.sendRedirect("home");
			}
			else
			{
				PrintWriter printWriter = response.getWriter();
				printWriter.println("You must specify a name and an URL");
			}
		}
		catch(Exception ex)
		{
			PrintWriter printWriter = response.getWriter();
			printWriter.println("URL you typed is probably wrong: "+ex.getMessage());
		}
	}

	public void init() throws ServletException
    {
		emf = PersistenceManager.getEmf();
		jpa = new JpaLinkDao(emf);
		userJpa = new JpaUserDao(emf);
	}
	
	protected void destroy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PersistenceManager.closeEmf();
	}
}
