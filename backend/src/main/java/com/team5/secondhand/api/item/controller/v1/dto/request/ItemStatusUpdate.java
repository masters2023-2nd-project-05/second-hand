package com.team5.secondhand.api.item.controller.v1.dto.request;

import com.team5.secondhand.api.item.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemStatusUpdate {
    private Status status;
}
