package com.example.springwebmvcdemo1.dto;

import lombok.Builder;

@Builder
public record CategoryRequest(String title,String description) {
}
