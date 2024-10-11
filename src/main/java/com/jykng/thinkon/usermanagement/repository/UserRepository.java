package com.jykng.thinkon.usermanagement.repository;

import com.jykng.thinkon.usermanagement.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
