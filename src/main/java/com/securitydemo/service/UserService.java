package com.securitydemo.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.securitydemo.model.UserEntity;

@Service
public class UserService {
	List<UserEntity> list = Arrays.asList(
			new UserEntity("1", "1", "1","ADMIN"),
			new UserEntity("2", "2", "2","NORMAL"), 
			new UserEntity("3", "3", "3","ADMIN"),
			new UserEntity("4", "4", "4","NORMAL")
		);

	public List<UserEntity> getAllUsers() {
		return list;
	}

	public UserEntity getUser(String userName) {
		return list.stream()
				.filter((user) -> user
									.getUserName()
									.equals(userName) && user.getUserRole()
									.equals("NORMAL"))
				.findFirst()
				.orElse(null);
	}

	public void addUser(UserEntity user) {
		list.add(user);
	}
	
	public List<UserEntity> getAdminUser() {
		return list.stream().filter((user) -> user.getUserRole().equals("ADMIN")).collect(Collectors.toList());
	}

	public List<String> getAllUserNames() {
		return list.stream().map((user) -> user.getUserName()).collect(Collectors.toList());
	}

}
