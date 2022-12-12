package com.karman.service;

import java.util.List;
import java.util.Optional;

import com.karman.model.User;

public interface AuthenticationService {
	void save(User user);

	User update(User user);

	void deleteById(Long id);

	Optional<User> findById(Long id);
	
	Optional<User> findByName(String name);

	List<User> findAll();
}
