package com.team5.secondhand.api.region.dto;

import com.team5.secondhand.api.region.domain.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class RegionSummary implements Serializable {
    private final Long id;
    private final String city;
    private final String county;
    private final String district;

    public static RegionSummary fromRegion (Region region) {
        return new RegionSummary(region.getId(), region.getCity(), region.getCounty(), region.getDistrict());
    }
}
