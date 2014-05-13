package com.supinfo.suplink.dao.jpa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.supinfo.suplink.dao.UserDao;
import com.supinfo.suplink.entity.User;

public class JpaUserDao implements UserDao 
{
	EntityManagerFactory emf;
	
	public JpaUserDao(EntityManagerFactory emf)
	{
		this.emf=emf;
	}
	
	@Override
	public boolean addUser(String email, String password)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Query query = em.createQuery("SELECT u from User u WHERE u.email=:email");
		query.setParameter("email", email);
		try
		{
			@SuppressWarnings("unchecked")
			List<User> users = Collections.checkedList(query.getResultList(), User.class) ;			
			if(!users.isEmpty())
			{
				return false;
			}
			User newUser=new User();
			newUser.setEmail(email);
			newUser.setPassword(encryptPassword(password));
			newUser.setIsRegistered(false);
			
			transaction.begin();
			em.persist(newUser);
			transaction.commit();
			
			Query query2 = em.createQuery("SELECT u from User u WHERE u.email=:email");
			query2.setParameter("email", email);

			@SuppressWarnings("unchecked")
			List<User> users2 = Collections.checkedList(query2.getResultList(), User.class) ;			
			Long userId = users2.get(0).getId();
			
			String confirmationLink = createEmailConfirmation(userId.toString());
			users2.get(0).setConfirmationLink(confirmationLink);
			transaction.begin();
			em.merge(users2.get(0));
			transaction.commit();
			
			sendMailConfirmation(userId);
			return true;
		}
		finally
		{
			if(transaction.isActive())
			{
				transaction.rollback();
			}
			em.close();	
		}		
	}
	
	@Override
	public boolean registerUser(String userId)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		long id = Long.parseLong(userId);
		User user=getUserById(id);
		if(user==null)
		{
			return false;
		}
		if(user.getIsRegistered())
		{
			return false;
		}
		user.setIsRegistered(true);
		
		try
		{
			transaction.begin();
			em.merge(user);
			transaction.commit();
			return true;
		}
		finally
		{
			if(transaction.isActive())
			{
				transaction.rollback();
			}
			em.close();	
		}
	}
	
	@Override
	public User getUserById(long userId)
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT u from User u WHERE u.id=:id");
		query.setParameter("id", userId);
		
		try
		{
			@SuppressWarnings("unchecked")
			List<User> users = Collections.checkedList(query.getResultList(), User.class) ;	
			if(users.isEmpty())
			{
				return null;
			}
			return users.get(0);
		}
		finally
		{
			em.close();
		}
	}
	
	@Override
	public User authenticateUser(String email, String password)
	{
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password");
        query.setParameter("email", email);
        String encryptedPassword = encryptPassword(password);
        query.setParameter("password", encryptedPassword);
        try
        {
        	@SuppressWarnings("unchecked")
    		List<User> users = Collections.checkedList(query.getResultList(), User.class) ;
        	if(users.isEmpty())
        	{
        		return null;
        	}
    		User user = users.get(0);
    		return user;
        }
        finally
        {
    		em.close();
        }
	}
	
	@Override
	public void logoutUser(User user) {
		// TODO Auto-generated method stub
		
	}
	
	private String encryptPassword(String password)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException ex)
		{
			throw new Error("No MD5 support in this VM");
		}
		md.update(password.getBytes(), 0, password.length());
		String encryptedPassword = new BigInteger(1, md.digest()).toString(16);
		return encryptedPassword;
	}
	
	@Override
	public void sendMailConfirmation(long userId) 
	{
		 User user = getUserById(userId);
		 
		 Authenticator authenticator = new Authenticator();
		    
		 Properties properties = new Properties();
		 properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		 properties.setProperty("mail.smtp.auth", "true");
		 properties.setProperty("mail.smtp.host", "smtp.live.com");
		 properties.setProperty("mail.smtp.port", "587");
		 properties.setProperty("mail.smtp.starttls.enable","true");
		    
		 Session session = Session.getInstance(properties, authenticator);
		 
		 MimeMessage message = new MimeMessage(session);  
		 try
		 {
			 message.setFrom(new InternetAddress("jerome.mevel@hotmail.fr"));
			 message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
			 message.setSubject("SupLink Registration");
			 String textMessage = "<a href=\""+user.getConfirmationLink()+"\">Confirm password</a>";
			 message.setContent(textMessage, "text/html");
			 
			 Transport.send(message);
		 }
		 catch(Exception ex)
		 {
		  	ex.printStackTrace();
		 }
	}
	
	private class Authenticator extends javax.mail.Authenticator
	{
		private PasswordAuthentication authentication;
		
		public Authenticator() 
		{
			String username = "jerome.mevel@hotmail.fr";
			String password = "Un20EgalUnePinte";
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() 
		{
			return authentication;
		}

	}

	
	private String createEmailConfirmation(String userId)
	{
		String email = null;
		String localHostName = null;
		try
		{
			//localHostName = InetAddress.getLocalHost().getHostName();
			localHostName = "localhost:8080";
		}
		catch(Exception ex)
		{
			throw new Error("Cannot get local hostname");
		}
		email = "http://";
		email = email.concat(localHostName);
		email = email.concat("/suplink/register?id=");
		email = email.concat(userId);
		return email;
	}
}
