package com.finalProject.back.controller;

import com.finalProject.back.dto.PageRequestDto;
import com.finalProject.back.dto.PageResponseDto;
import com.finalProject.back.dto.ProductDto;
import com.finalProject.back.service.ProductService;
import com.finalProject.back.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final CustomFileUtil customFileUtil;
    private final ProductService productService;


//    @PostMapping("/")
//    public Map<String, String> register(ProductDto productDto){
//
//        log.info("register: " + productDto);
//
//        List<MultipartFile> files = productDto.getFiles();
//
//        List<String> uploadedFileNames = customFileUtil.saveFiles(files);
//
//        productDto.setUploadFileNames(uploadedFileNames);
//
//        log.info(uploadedFileNames);
//
//        return Map.of("RESULT", "SUCCESS");
//    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName){
        return customFileUtil.getFile(fileName);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public PageResponseDto<ProductDto> list(PageRequestDto pageRequestDto){

        log.info(productService.getList(pageRequestDto).toString());

        return productService.getList(pageRequestDto);
    }

    @PostMapping("/")
    public Map<String, Long> insert(ProductDto productDto){
        List<MultipartFile> files = productDto.getFiles();
        List<String> uploadFileNames = customFileUtil.saveFiles(files);
        productDto.setUploadFileNames(uploadFileNames);
        Long productId = productService.register(productDto);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("result", productId);
    }

    @GetMapping("/{productId}")
    public ProductDto get(@PathVariable("productId") Long productId){
        return productService.get(productId);
    }

    @PutMapping("/{productId}")
    public Map<String, String> update(@PathVariable("productId") Long productId, ProductDto productDto){
        productDto.setId(productId);

        ProductDto oldProductDto = productService.get(productId);

        List<MultipartFile> files = productDto.getFiles();
        List<String> currentUploadFileNames = customFileUtil.saveFiles(files);

        List<String> uploadedFileNames = productDto.getUploadFileNames();

        if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()){
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        productService.modify(productDto);

        List<String> oldFileNames = oldProductDto.getUploadFileNames();

        if(oldFileNames != null && !oldFileNames.isEmpty()){
            List<String> removeFiles =
                    oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());

            customFileUtil.deleteFiles(removeFiles);
        }

        return Map.of("RESULT", "success");
    }

    @DeleteMapping("/{productId}")
    public Map<String, String> delete(@PathVariable("productId") Long productId){
        List<String> oldFileNames = productService.get(productId).getUploadFileNames();

        productService.delete(productId);

        customFileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "success");
    }
}
