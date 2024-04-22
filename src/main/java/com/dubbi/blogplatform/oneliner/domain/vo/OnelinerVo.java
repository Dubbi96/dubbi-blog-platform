package com.dubbi.blogplatform.oneliner.domain.vo;

import com.dubbi.blogplatform.oneliner.domain.entity.Oneliner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OnelinerVo {
    private long id;
    private String content;
    private List<OnelinerImageVo> onelinerImages;

    public OnelinerVo(Oneliner oneliner) {
        id = oneliner.getId();
        content = oneliner.getContent();
        onelinerImages = oneliner.getOnelinerImages().stream()
                .map(OnelinerImageVo::new)
                .toList();
    }
}
