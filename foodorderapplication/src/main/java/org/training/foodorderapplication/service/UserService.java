package org.training.foodorderapplication.service;

import java.util.Optional;

import org.training.foodorderapplication.entity.Users;

public interface UserService {

	Optional<Users> findById(int userId);

}
