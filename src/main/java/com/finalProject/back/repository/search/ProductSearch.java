package com.finalProject.back.repository.search;

import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.ProductDto;

public interface ProductSearch {

    PageResponseDto<ProductDto> searchList(PageRequestDto pageRequestDto);
}
