package com.team5.secondhand.api.item.controller.v1.dto.request;

import com.team5.secondhand.api.item.service.dto.ItemListFilter;
import com.team5.secondhand.api.member.dto.response.MemberDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class MyItemsRequest {
    @Size(min = 0)
    private final int page;
    private final Boolean isSales;

    public ItemListFilter toFilter(MemberDetails loginMember) {
        return ItemListFilter.of(null, loginMember.getId(), isSales, null);
    }
}
