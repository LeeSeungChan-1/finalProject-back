package com.finalProject.back.repository;

import com.finalProject.back.domain.Todo;
import com.finalProject.back.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
