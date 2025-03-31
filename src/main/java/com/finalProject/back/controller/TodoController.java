package com.finalProject.back.controller;


import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.TodoDto;
import com.finalProject.back.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;


    @GetMapping("/{id}")
    public TodoDto select(@PathVariable("id") Long id){
        return todoService.findById(id);
    }

    @GetMapping("/list")
    public PageResponseDto<TodoDto> selectAll(PageRequestDto pageRequestDto){
        return todoService.selectAll(pageRequestDto);
    }

    @PostMapping("/")
    public Map<String, Long> insert(@RequestBody TodoDto todoDto){
        Long todoId = todoService.insert(todoDto);

        return Map.of("todoId", todoId);
    }

    @PutMapping("/{id}")
    public Map<String, String> update(@PathVariable("id") Long id, @RequestBody TodoDto todoDto){

        todoDto.setTodoId(id);
        todoService.update(todoDto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id){
        todoService.delete(id);
        return Map.of("RESULT", "SUCCESS");
    }
}
