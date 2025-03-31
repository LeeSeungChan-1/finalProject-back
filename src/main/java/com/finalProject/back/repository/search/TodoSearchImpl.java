package com.finalProject.back.repository.search;

import com.finalProject.back.domain.QTodo;
import com.finalProject.back.domain.Todo;
import com.finalProject.back.dto.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch{
    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1(PageRequestDto pageRequestDto) {
        log.info("search1");

        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> query = from(todo);

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("todoId").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> todoList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(todoList, pageable, total);
    }
}
