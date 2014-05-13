package com.supinfo.suplink.dao;

import com.supinfo.suplink.entity.Click;

public interface ClickDao {
	public void addClick(Click click);
	public void removeClick(long clickId);
	public Click getClickById(long clickId);
}
