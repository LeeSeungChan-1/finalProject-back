package com.finalProject.back.service;

import com.finalProject.back.domain.Todo;
import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.TodoDto;
import com.finalProject.back.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    public TodoDto findById(Long id) {

        Todo result = todoRepository.findById(id).orElseThrow();

        return entityToDto(result);
    }

    @Override
    public Long insert(TodoDto todoDto) {
        Todo todo = dtoToEntity(todoDto);

        Todo result = todoRepository.save(todo);

        return result.getTodoId();
    }

    @Override
    public void update(TodoDto todoDto) {

        Todo todo = todoRepository.findById(todoDto.getTodoId()).orElseThrow();

        todo.setTitle(todoDto.getTitle());
        todo.setWriter(todoDto.getWriter());
        todo.setComplete(todoDto.isComplete());
        todo.setDueDate(todoDto.getDueDate());

        todoRepository.save(todo);
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public PageResponseDto<TodoDto> selectAll(PageRequestDto pageRequestDto) {

        Page<Todo> result = todoRepository.search1(pageRequestDto);

        List<TodoDto> todoDtoList = result.get().map(todo -> entityToDto(todo)).collect(Collectors.toList());

        PageResponseDto<TodoDto> responseDto = PageResponseDto
                .<TodoDto>withAll()
                .dtoList(todoDtoList)
                .pageRequestDto(pageRequestDto)
                .totalCount(result.getTotalElements())
                .build();

        return responseDto;
    }


}
