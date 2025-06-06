package com.oristo.taskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oristo.TaskManagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
