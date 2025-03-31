package com.finalProject.back.service;

import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.ProductDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Test
    public void listTest(){
        PageRequestDto pageRequestDto = PageRequestDto.builder().build();

        PageResponseDto<ProductDto> productDtoPageResponseDto = productService.getList(pageRequestDto);

        log.info(productDtoPageResponseDto.getDtoList());
    }

    @Test
    public void insertTest(){
        ProductDto productDto = ProductDto.builder()
                .name("insert Test")
                .desc("insert Test")
                .price(1000)
                .build();

        productDto.setUploadFileNames(
                java.util.List.of(
                        UUID.randomUUID() + "_" + "insert1.jpg",
                        UUID.randomUUID() + "_" + "insert2.jpg")
        );
        productService.register(productDto);
    }
}
