package org.training.foodorderapplication.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.repository.UserRepository;
import org.training.foodorderapplication.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public Optional<Users> findById(int userId) {
		return repository.findById(userId);
	}
}
