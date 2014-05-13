package com.supinfo.suplink.dao;

import com.supinfo.suplink.entity.User;

public interface UserDao {
	public boolean addUser(String email, String password);
	public boolean registerUser(String userId);
	public User authenticateUser(String email, String password);
	public void logoutUser(User user);
	public User getUserById(long userId);
	public void sendMailConfirmation(long userId);
}
