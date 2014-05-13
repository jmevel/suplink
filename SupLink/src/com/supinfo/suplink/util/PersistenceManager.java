package com.supinfo.suplink.util;

import javax.persistence.*;




public class PersistenceManager 
{
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEmf()
	{
		if(emf==null)
		{
			
			emf = Persistence.createEntityManagerFactory("My-PU");
		}
		return emf;
	}
	
	public static void closeEmf()
	{
		emf.close();
	}
}