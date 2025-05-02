package com.example.magazine.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class MagazineDto {

    @NotBlank(message = "Magazine name cannot be empty.")
    private String name;

    private String description;

    @Positive(message = "Base price must be greater than zero.")
    @Min(value = 1, message = "Base price must be at least 1.")
    private long base_price;

    private String image_url;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBase_price() {
        return base_price;
    }

    public void setBase_price(long base_price) {
        this.base_price = base_price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}