package com.finalProject.back.repository;

import com.finalProject.back.domain.Product;
import com.finalProject.back.dto.PageRequestDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void InsertTest(){

        for(int i = 1; i <= 10; i++){
            Product product = Product.builder()
                    .name("test" + i)
                    .description("test desc" + i)
                    .price(1000 * i)
                    .build();

            product.addImageString(UUID.randomUUID() + "_" + "Image1_" + i + ".jpg");
            product.addImageString(UUID.randomUUID() + "_" + "Image2_" + i + ".jpg");

            productRepository.save(product);
        }

    }

    @Test
    public void SelectTest(){
        Long productId = 1L;
        Optional<Product> productOptional = productRepository.selectOne(productId);
        Product product = productOptional.orElseThrow();
        log.info(product);
        log.info(product.getImageList());
    }


    @Test
    @Commit
    @Transactional
    public void DeleteTest(){
        Long productId = 1L;
        productRepository.updateToDelete(true, productId);
    }

    @Test
    public void UpdateTest(){
        Product product = productRepository.selectOne(1L).orElseThrow();

        product.setName("update test");
        product.setDescription("update test desc");
        product.setPrice(2000);
        product.setDelFlag(true);
        product.clearList();

        product.addImageString(UUID.randomUUID() + "_" + "updateImage1.jpg");
        product.addImageString(UUID.randomUUID() + "_" + "updateImage2.jpg");
        product.addImageString(UUID.randomUUID() + "_" + "updateImage3.jpg");

        productRepository.save(product);

    }

    @Test
    public void ListTest(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("productId").descending());
        Page<Object[]> result = productRepository.selectList(pageable);
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void searchTest(){

        PageRequestDto pageRequestDto = PageRequestDto.builder().build();

        productRepository.searchList(pageRequestDto);
    }




























}
