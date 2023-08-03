package com.team5.secondhand.api.item.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class CategoryList {
    private final List<Long> id;

    public static CategoryList of(List<Long> category) {
        return CategoryList.builder()
                .id(category)
                .build();
    }
}
