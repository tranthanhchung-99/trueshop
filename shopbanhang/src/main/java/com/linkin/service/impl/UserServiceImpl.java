
package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.linkin.dao.UserDao;
import com.linkin.entity.User;
import com.linkin.model.UserDTO;
import com.linkin.model.UserPrincipal;
import com.linkin.service.UserService;
import com.linkin.utils.PasswordGenerator;

@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Override
	public void insert(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setAge(userDTO.getAge());
		user.setEnabled(userDTO.getEnabled());
		user.setRole(userDTO.getRole());
		user.setUsername(userDTO.getUsername());
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		user.setGender(userDTO.getGender());
		user.setAddress(userDTO.getAddress());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		
		userDao.insert(user);
	}

	@Override
	public void update(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setName(userDTO.getName());
			user.setAge(userDTO.getAge());
			user.setRole(userDTO.getRole());
			user.setUsername(userDTO.getUsername());
			user.setGender(userDTO.getGender());
			user.setAddress(userDTO.getAddress());
			user.setEnabled(userDTO.getEnabled());
			user.setPhone(userDTO.getPhone());
			user.setEmail(userDTO.getEmail());
			userDao.update(user);
		}
	}

	@Override
	public void delete(Long id) {
		User user = userDao.get(id);
		if (user != null && user.getRole()!="ROLE_ADMIN") {
			userDao.delete(user);
		}
	}

	@Override
	public UserDTO get(Long id) {
		User user = userDao.get(id);
		return convert(user);
	}

	@Override
	public UserDTO getByUserName(String userName) {
		User user = userDao.getByUserName(userName);
		return convert(user);
	}

	@Override
	public List<UserDTO> search(String name, int start, int length) {
		List<User> users = userDao.search(name, start, length);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (User user : users) {
			userDTOs.add(convert(user));
		}
		return userDTOs;
	}

	private UserDTO convert(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setAge(user.getAge());
		userDTO.setRole(user.getRole());
		userDTO.setUsername(user.getUsername());
		userDTO.setGender(user.getGender());
		userDTO.setAddress(user.getAddress());
		userDTO.setEnabled(user.getEnabled());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}

	@Override
	public void changePassword(UserDTO userDTO) {
	}

	@Override
	public void resetPassword(UserDTO accountDTO) {
	}

	@Override
	public void setupUserPassword(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
			userDao.update(user);
		}
	}

	@Override
	public void updateProfile(UserDTO userDTO) {
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUserName(username.trim());
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		UserPrincipal userPrincipal = new UserPrincipal(user.getUsername(), user.getPassword(), user.getEnabled(), true,
				true, true, authorities);

		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getName());
		userPrincipal.setRole(user.getRole());
		userPrincipal.setPhone(user.getPhone());
		userPrincipal.setEmail(user.getEmail());
		
		return userPrincipal;
	}

	@Override
	public UserDTO getByEmail(String email) {
		User user = userDao.getByEmail(email);
		return convert(user);
	}

}
