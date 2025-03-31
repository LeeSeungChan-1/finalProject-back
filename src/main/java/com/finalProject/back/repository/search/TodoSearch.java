package com.finalProject.back.repository.search;

import com.finalProject.back.domain.Todo;
import com.finalProject.back.dto.PageRequestDto;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDto pageRequestDto);
}
