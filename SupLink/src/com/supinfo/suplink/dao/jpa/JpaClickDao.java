package com.supinfo.suplink.dao.jpa;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.supinfo.suplink.dao.ClickDao;
import com.supinfo.suplink.entity.Click;

public class JpaClickDao implements ClickDao
{
	EntityManagerFactory emf;
	
	public JpaClickDao(EntityManagerFactory emf)
	{
		this.emf = emf;
	}
	
	@Override
	public void addClick(Click click) 
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try
		{
			transaction.begin();
			em.persist(click);
			transaction.commit();
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
	public Click getClickById(long clickId)
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c from Click c WHERE c.id=:id");
		query.setParameter("id", clickId);
		
		try
		{
			@SuppressWarnings("unchecked")
			List<Click> clicks = Collections.checkedList(query.getResultList(), Click.class) ;			
			return clicks.get(0);
		}
		finally
		{
			em.close();
		}
	}
	
	@Override
	public void removeClick(long clickId)
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("DELETE FROM Click AS click WHERE click.id=:id");
		query.setParameter("id", clickId);
		
		try
		{
			query.executeUpdate();
		}
		finally
		{
			em.close();
		}
	}
}
