package com.supinfo.suplink.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.supinfo.suplink.util.PersistenceManager;
import com.supinfo.suplink.dao.jpa.JpaUserDao;
import com.supinfo.suplink.entity.User;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf;
	JpaUserDao jpa;
       
    public LoginServlet() {
        super();
    }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String email= request.getParameter("email");
		String password = request.getParameter("password");
		if(email!=null && password!=null)
		{
			User user = jpa.authenticateUser(email, password);
			if(user!=null)
			{
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getId());
				session.setAttribute("user", user);
				response.sendRedirect("auth/home");
			}
			else
			{
				PrintWriter printWriter = response.getWriter();
				printWriter.println("Bad email or password");
			}
		}
		else
		{
			PrintWriter printWriter = response.getWriter();
			printWriter.println("You must specify email and password");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		if(session.getAttribute("userId")!=null)
		{
			resp.sendRedirect("auth/home");
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
	
	public void init() throws ServletException
    {
		emf = PersistenceManager.getEmf();
		jpa = new JpaUserDao(emf);
	}
	
	protected void destroy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersistenceManager.closeEmf();
	}
}
