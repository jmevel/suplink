package com.supinfo.suplink.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.suplink.dao.jpa.JpaUserDao;
import com.supinfo.suplink.util.PersistenceManager;


public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf;
	JpaUserDao jpa;

    public AddUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher("/WEB-INF/adduser.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String email= request.getParameter("email");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if(!password1.equals(password2))
		{
			PrintWriter printWriter = response.getWriter();
			printWriter.println("Your password confirmation is different than first password");
		}
		else
		{
			boolean addUserResult = jpa.addUser(email, password1);
			if(addUserResult==false)
			{
				PrintWriter printWriter = response.getWriter();
				printWriter.println("Error: This email already exists!");
			}
			else
			{
				PrintWriter printWriter = response.getWriter();
				printWriter.println("An email confirmation was sent to you. Please click on the confirmation link to valid your inscription");
			}
		}
	}
	
	public void init() throws ServletException 
	{
		emf = PersistenceManager.getEmf();
		jpa = new JpaUserDao(emf);
	}
	
	protected void destroy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PersistenceManager.closeEmf();
	}
}
