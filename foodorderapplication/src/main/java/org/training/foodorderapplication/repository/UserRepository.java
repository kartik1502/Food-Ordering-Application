package org.training.foodorderapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findById(int userId);

}
