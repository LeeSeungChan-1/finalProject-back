package com.finalProject.back.service;

import com.finalProject.back.domain.Todo;
import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.TodoDto;
import jakarta.transaction.Transactional;

@Transactional
public interface TodoService {

    TodoDto findById(Long id);

    Long insert(TodoDto todoDto);

    void update(TodoDto todoDto);

    void delete(Long id);

    PageResponseDto<TodoDto> selectAll(PageRequestDto pageRequestDto);

    default TodoDto entityToDto(Todo todo){

        TodoDto todoDto =
                TodoDto.builder()
                        .todoId(todo.getTodoId())
                        .title(todo.getTitle())
                        .writer(todo.getWriter())
                        .complete(todo.isComplete())
                        .dueDate(todo.getDueDate())
                        .build();

        return todoDto;
    }

    default Todo dtoToEntity(TodoDto todoDto){
        Todo todo =
                Todo.builder()
                        .todoId(todoDto.getTodoId())
                        .title(todoDto.getTitle())
                        .writer(todoDto.getWriter())
                        .complete(todoDto.isComplete())
                        .dueDate(todoDto.getDueDate())
                        .build();
        return todo;
    }
}
