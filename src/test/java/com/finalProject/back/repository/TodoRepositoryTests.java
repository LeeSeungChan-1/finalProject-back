package com.finalProject.back.repository;

import com.finalProject.back.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1(){
        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void InsertTest(){
        for(int i = 1; i <= 100; i++){
            Todo todo = Todo.builder()
                    .title("Title" + i)
                    .writer("Writer" + i)
                    .dueDate(LocalDate.of(2025, 1, 1).plusDays(i))
                    .build();

            Todo result = todoRepository.save(todo);

            log.info(result);
        }

    }

    @Test
    public void SelectTest(){
        Long id = 1L;
        Optional<Todo> result = todoRepository.findById(id);

        log.info(result);
    }

    @Test
    public void UpdateTest(){
        Long id = 1L;
        Optional<Todo> result = todoRepository.findById(id);

        Todo todo = result.orElseThrow();

        todo.setTitle("Update Title");
        todo.setWriter("Update Content");
        todo.setComplete(true);

        Todo UpdateResult = todoRepository.save(todo);

        log.info(UpdateResult);
    }

    @Test
    public void pagingTest(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("todoId").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());
        log.info(result.getContent());
    }

//    @Test
//    public void searchTest1(){
//        todoRepository.search1();
//    }

}























