package com.example.springwebmvcdemo1.dto;

import lombok.Builder;

@Builder
public record ProductResponse(int id,String title,String description,float price,String imageUrl) {
}
