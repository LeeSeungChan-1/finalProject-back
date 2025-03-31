package com.finalProject.back.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Setter
    @Column(length = 50, nullable = false)
    private String title;

    @Setter
    private String writer;

    @Setter
    private boolean complete;

    @Setter
    private LocalDate dueDate;


}
