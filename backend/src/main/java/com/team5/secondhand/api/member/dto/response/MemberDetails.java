package com.team5.secondhand.api.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team5.secondhand.api.member.domain.Member;
import com.team5.secondhand.api.member.domain.Oauth;
import com.team5.secondhand.api.member.dto.request.BasedRegionSummary;
import com.team5.secondhand.api.region.exception.EmptyBasedRegionException;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetails {

    private Long id;
    private String memberId;
    private String profileImgUrl;
    private Oauth oauth;
    private List<BasedRegionSummary> regions;

    @Builder
    protected MemberDetails(Long id, String memberId, String profileImgUrl, Oauth oauth, List<BasedRegionSummary> regions) {
        this.id = id;
        this.memberId = memberId;
        this.profileImgUrl = profileImgUrl;
        this.oauth = oauth;
        this.regions = regions;
    }

    public static MemberDetails fromMember(Member member) {
        List<BasedRegionSummary> regions = member.getRegions().stream()
                .map(BasedRegionSummary::of)
                .collect(Collectors.toList());

        return MemberDetails.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .profileImgUrl(member.getProfileImgUrl())
                .oauth(member.getOauth())
                .regions(regions)
                .build();
    }

    @JsonIgnore
    public static MemberDetails empty() {
        return MemberDetails.builder().build();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.id == null;
    }
}
