package com.supinfo.suplink.dao;

import com.supinfo.suplink.dao.jpa.JpaClickDao;
import com.supinfo.suplink.dao.jpa.JpaLinkDao;
import com.supinfo.suplink.dao.jpa.JpaUserDao;
import com.supinfo.suplink.util.PersistenceManager;

public class DaoFactory 
{
	private DaoFactory() {
	}
	
	public UserDao getProductDao()
	{
		UserDao userDao = new JpaUserDao(PersistenceManager.getEmf());
		return userDao;
	}	
	
	public LinkDao getLinkDao()
	{
		LinkDao linkDao = new JpaLinkDao(PersistenceManager.getEmf());
		return linkDao;
	}
	
	public ClickDao getClickDao()
	{
		ClickDao clickDao = new JpaClickDao(PersistenceManager.getEmf());
		return clickDao;
	}
}
