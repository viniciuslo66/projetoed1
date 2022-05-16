package com.viniciuslo66.projetoed1.model.repository;

import com.viniciuslo66.projetoed1.model.entity.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{
  
}
