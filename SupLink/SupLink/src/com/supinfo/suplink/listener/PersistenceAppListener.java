package com.supinfo.suplink.listener;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.supinfo.suplink.util.PersistenceManager;

public class PersistenceAppListener implements ServletContextListener {
	private static EntityManagerFactory emf;
	
	public PersistenceAppListener() {
		emf = PersistenceManager.getEmf();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
