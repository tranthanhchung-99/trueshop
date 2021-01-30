package com.linkin.dao;

import java.util.List;

import com.linkin.entity.User;

public interface UserDao {
	void insert(User user);

	void update(User user);

	void delete(User user);

	User get(Long id);

	User getByUserName(String userName);

	List<User> search(String findName, int start, int length);
	
	User getByEmail(String email);

}
