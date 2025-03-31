package com.finalProject.back.service;

import com.finalProject.back.domain.Todo;
import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.TodoDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class TodoServiceTests {

    @Autowired
    TodoService todoService;

    @Test
    public void findByIdTest() {
        Long id = 101L;

        log.info(todoService.findById(id));
    }

    @Test
    public void insertTest() {
        TodoDto todoDto = TodoDto.builder()
                .todoId(null)
                .title("insertTitle")
                .writer("insertContent")
                .complete(false)
                .dueDate(LocalDate.of(2024, 12, 30))
                .build();

        log.info(todoService.insert(todoDto));
    }

    @Test
    public void updateTest() {
        Long id = 102L;

        TodoDto todoDto = TodoDto.builder()
                .todoId(id)
                .title("updateTitle")
                .writer("updateContent")
                .complete(true)
                .dueDate(LocalDate.of(2025, 1, 1))
                .build();

        todoService.update(todoDto);
    }

    @Test
    public void deleteTest() {
        Long id = 102L;
        todoService.delete(id);
    }

    @Test
    public void selectTest(){
        PageRequestDto pageRequestDto = PageRequestDto.builder().page(11).build();

        log.info(todoService.selectAll(pageRequestDto));
    }
}
