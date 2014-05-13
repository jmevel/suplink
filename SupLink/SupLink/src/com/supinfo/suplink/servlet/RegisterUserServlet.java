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


public class RegisterUserServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf;
	JpaUserDao jpa;

    public RegisterUserServlet() 
    {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("doget");
		String userId =(String)request.getParameter("id");
		boolean wasNotRegistered = jpa.registerUser(userId);
		if(wasNotRegistered==false)
		{
			PrintWriter printWriter = response.getWriter();
			printWriter.println("This account does not exist or is already confirmed");
		}
		else
		{
			PrintWriter printWriter = response.getWriter();
			printWriter.println("Thanks for your registration, you can now login");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

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
