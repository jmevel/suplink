package com.supinfo.suplink.dao.jpa;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


import com.supinfo.suplink.dao.LinkDao;
import com.supinfo.suplink.entity.Click;
import com.supinfo.suplink.entity.Link;

public class JpaLinkDao implements LinkDao
{
	EntityManagerFactory emf;
	
	public JpaLinkDao(EntityManagerFactory emf)
	{
		this.emf = emf;
	}
	
	@Override
	public void addLink(Link link) 
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try
		{
			Date date = new Date();
			link.setCreationDate(date);
			List<Click> clickList = new ArrayList<Click>();
			List<Click> clicks = clickList;
			//link.setClicks(clicks);
			link.setIsEnable(true);
			
			try
			{
				String shortenedUrl = createShortenedUrl();
				URL shortUrl = new URL(shortenedUrl);
				link.setShortenedURL(shortUrl);
				transaction.begin();
				em.persist(link);
				transaction.commit();				
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			
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
	public void removeLink(long linkId) 
	{
		EntityManager em = emf.createEntityManager();
		//Query query = em.createQuery("DELETE FROM Link l WHERE l.id=:id");
		EntityTransaction transaction = em.getTransaction();
		try
		{
			transaction.begin();
			
			/*Link linkToRemove = em.merge(this.getLinkById(linkId));
			em.remove(linkToRemove);*/
			List<Click> clicks = this.getLinkById(linkId).getClicks();
			
			for (int i = 0; i < clicks.size(); i++) 
			{ 
			   Click clickToRemove = em.merge(clicks.get(i));
			   em.remove(clickToRemove);
			}
			transaction.commit();
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
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
	public Link getLinkById(long linkId) 
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT l from Link l WHERE l.id=:id");
		query.setParameter("id", linkId);
		
		try
		{
			@SuppressWarnings("unchecked")
			List<Link> links = Collections.checkedList(query.getResultList(), Link.class) ;			
			return links.get(0);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
		finally
		{
			em.close();
		}
	}
	
	@Override
	public List<Link> getLinksByUserId(long userId) 
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT l from Link l WHERE l.user.id=:id");
		query.setParameter("id", userId);
		
		@SuppressWarnings("unchecked")
		List<Link> links = Collections.checkedList(query.getResultList(), Link.class) ;	
		return links;
	}
	
	@Override
	public String createShortenedUrl()
	{
		EntityManager em = emf.createEntityManager();
		
		Boolean urlNotAlreadyUsed=false;
		String shortenedUrl = "http://localhost:8080/SupLink/l/";
		String shortUuid="";
		while(urlNotAlreadyUsed==false)
		{
			String uuid = UUID.randomUUID().toString();
			shortUuid = uuid.substring(0, 4);
			
			Query query = em.createQuery("SELECT l from Link l");
			
			@SuppressWarnings("unchecked")
			List<Link> links = Collections.checkedList(query.getResultList(), Link.class) ;	
			if(links.isEmpty())
			{
				urlNotAlreadyUsed=true;
				break;
			}
			for (int i = 0; i < links.size(); i++) 
			{
				if(links.get(i).getShortenedURL()!=null && links.get(i).getShortenedURL().toString().endsWith(shortUuid)==false && i==links.size()-1)
				{
					urlNotAlreadyUsed=true;
				}
			}
		}
		shortenedUrl = shortenedUrl.concat(shortUuid);
		return shortenedUrl;
	}
	
	@Override
	public Link getOriginalLink(String shortenedUrl) 
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT l from Link l WHERE l.shortenedURL=:shortUrl");
		try
		{
			URL url = new URL(shortenedUrl);
			query.setParameter("shortUrl", url);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	
		@SuppressWarnings("unchecked")
		List<Link> links = Collections.checkedList(query.getResultList(), Link.class) ;	
		if(links.isEmpty())
		{
			return null;
		}
		return links.get(0);
	}
	
	@Override
	public void updateLink(Link link)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try
		{
			transaction.begin();
			em.merge(link);
			transaction.commit();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
	}
}
