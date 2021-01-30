package com.linkin.service;

import java.util.List;

import com.linkin.model.UserDTO;

public interface UserService {
	void insert(UserDTO user);

	void update(UserDTO user);

	void delete(Long id);

	UserDTO get(Long id);

	UserDTO getByUserName(String userName);
	
	UserDTO getByEmail(String email);

	List<UserDTO> search(String name, int start, int length);

	void changePassword(UserDTO accountDTO);

	void resetPassword(UserDTO accountDTO);

	void setupUserPassword(UserDTO accountDTO);

	void updateProfile(UserDTO userDTO);
}
