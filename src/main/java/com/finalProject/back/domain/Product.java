package com.finalProject.back.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "imageList")
@Table(name = "tbl_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @Setter
    private String name;

    @Setter
    private int price;

    @Setter
    private String description;

    @Setter
    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();



    public void addImage(ProductImage image) {
        image.setOrd(imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName) {

        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);
    }

    public void clearList(){
        imageList.clear();
    }
}
