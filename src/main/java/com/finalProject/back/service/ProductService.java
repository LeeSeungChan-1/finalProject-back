package com.finalProject.back.service;

import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.ProductDto;
import jakarta.transaction.Transactional;

@Transactional
public interface ProductService {

    PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto);

    Long register(ProductDto productDto);

    ProductDto get(Long productId);

    void modify(ProductDto productDto);

    void delete(Long productId);
}
