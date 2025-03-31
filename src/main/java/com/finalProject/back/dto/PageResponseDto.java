package com.finalProject.back.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDto<E> {
    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDto pageRequestDto;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;


    @Builder(builderMethodName = "withAll")
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDto, long totalCount) {

        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = (int) totalCount;

        int end = (int) (Math.ceil(pageRequestDto.getPage() / 10.0)) * 10;
        int start = end - 9;
        int last = (int)(Math.ceil(this.totalCount / (double) pageRequestDto.getSize()));
        end = end > last ? last : end;
        System.out.println(end + " " + last);
        this.prev = start != 1;
        this.next = totalCount > end * pageRequestDto.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;

    }
}
