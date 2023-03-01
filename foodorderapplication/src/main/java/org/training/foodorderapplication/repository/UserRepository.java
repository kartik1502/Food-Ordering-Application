package org.training.foodorderapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
