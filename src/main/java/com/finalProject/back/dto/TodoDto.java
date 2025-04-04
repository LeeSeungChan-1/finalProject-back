package com.finalProject.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private Long todoId;

    private String title;

    private String writer;

    private boolean complete;

    private LocalDate dueDate;
}
