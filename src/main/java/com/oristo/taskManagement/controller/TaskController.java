package com.oristo.taskManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.oristo.TaskManagement.model.Task;
import com.oristo.TaskManagement.model.User;
import com.oristo.TaskManagement.repository.TaskRepository;
import com.oristo.TaskManagement.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "view-tasks";
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "create-task";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        User user = userRepository.findById(1L).orElseGet(() -> {
            User newUser = new User();
            newUser.setName("Test User");
            return userRepository.save(newUser);
        });
        task.setCreatedBy(user);
        task.setLastUpdatedBy(user);
        task.setCreatedOn(LocalDateTime.now());
        task.setLastUpdatedOn(LocalDateTime.now());
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow();
        model.addAttribute("task", task);
        return "edit-task";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, @ModelAttribute Task task) {
        Task existingTask = taskRepository.findById(id).orElseThrow();
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setRemarks(task.getRemarks());
        existingTask.setLastUpdatedOn(LocalDateTime.now());
        existingTask.setLastUpdatedBy(userRepository.findById(1L).orElseThrow());
        taskRepository.save(existingTask);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/search")
    public String searchTasksForm(Model model) {
        model.addAttribute("tasks", List.of());
        return "search-tasks";
    }

    @PostMapping("/search")
    public String searchTasks(@RequestParam String searchTerm, Model model) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(searchTerm);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searchTerm", searchTerm);
        return "search-tasks";
    }
}