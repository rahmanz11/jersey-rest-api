package com.karman.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.karman.model.User;
import com.karman.repo.UserRepository;
import com.karman.service.AuthenticationService;;

public class AuthenticationServiceImpl implements AuthenticationService {

	private UserRepository userRepository;

	@Inject
	public AuthenticationServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User update(User user) {
		return userRepository.update(user);
	}

}
