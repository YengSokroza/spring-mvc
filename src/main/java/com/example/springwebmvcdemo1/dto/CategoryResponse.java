package com.example.springwebmvcdemo1.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(int id,String title,String description) {
}
