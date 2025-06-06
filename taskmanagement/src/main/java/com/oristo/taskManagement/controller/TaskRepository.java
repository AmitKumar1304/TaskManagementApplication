package com.oristo.taskManagement.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oristo.TaskManagement.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByTitleContainingIgnoreCase(String title);

}
