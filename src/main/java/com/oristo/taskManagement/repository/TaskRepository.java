package com.oristo.taskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oristo.TaskManagement.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByTitleContainingIgnoreCase(String title);
}
