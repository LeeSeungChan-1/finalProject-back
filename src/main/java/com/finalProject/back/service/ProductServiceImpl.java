package com.finalProject.back.service;

import com.finalProject.back.domain.Product;
import com.finalProject.back.domain.ProductImage;
import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.ProductDto;
import com.finalProject.back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(),
                Sort.by("productId").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        List<ProductDto> dtoList = result.get().map(arr -> {
            ProductDto productDto = null;

            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            productDto = ProductDto.builder()
                    .id(product.getProductId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .desc(product.getDescription())
                    .build();

            String imageStr = productImage.getFileName();
            productDto.setUploadFileNames(List.of(imageStr));

            return productDto;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDto.<ProductDto>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDto(pageRequestDto)
                .build();
    }

    @Override
    public Long register(ProductDto productDto) {
        Product product = dtoToEntity(productDto);
        return productRepository.save(product).getProductId();
    }

    @Override
    public ProductDto get(Long productId) {

        Product result = productRepository.findById(productId).orElseThrow();

        return entityToDto(result);
    }

    @Override
    public void modify(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow();

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDesc());
        product.setDelFlag(productDto.isDelFlag());

        List<String> uploadFileNames = productDto.getUploadFileNames();

        product.clearList();

        if(uploadFileNames != null & !uploadFileNames.isEmpty()) {
            uploadFileNames.forEach(uploadFileName -> {
                product.addImageString(uploadFileName);
            });
        }
        productRepository.save(product);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    private ProductDto entityToDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .id(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .desc(product.getDescription())
                .delFlag(product.isDelFlag())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if(imageList == null && imageList.isEmpty()) {
            return productDto;
        }

        List<String> fileNameList = imageList.stream().map(productImage -> productImage.getFileName()).toList();

        productDto.setUploadFileNames(fileNameList);


        return productDto;
    }

    private Product dtoToEntity(ProductDto productDto) {

        Product product = Product.builder()
                .productId(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDesc())
                .build();

        List<String> uploadFileNames = productDto.getUploadFileNames();

        if(uploadFileNames != null && uploadFileNames.size() == 0){
            return product;
        }

        uploadFileNames.forEach(uploadFileName -> {
            product.addImageString(uploadFileName);
        });
        return product;
    }

}
