package com.dubbi.blogplatform.oneliner.domain.vo;

import com.dubbi.blogplatform.oneliner.domain.entity.Oneliner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OnelinerVo {
    private long id;
    private String content;
    private List<OnelinerImageVo> onelinerImages;
    private Double latitude;
    private Double longitude;
    private Point point;

    public OnelinerVo(Oneliner oneliner) {
        id = oneliner.getId();
        content = oneliner.getContent();
        latitude = oneliner.getPoint().getX();
        longitude = oneliner.getPoint().getY();
        onelinerImages = oneliner.getOnelinerImages().stream()
                .map(OnelinerImageVo::new)
                .toList();
    }
}
