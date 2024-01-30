package com.dubbi.blogplatform.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class HisId implements Serializable {
    @Column(name = "ID")
    private Long id;

    @Column(name = "VERSION")
    private Long version;

    private HisId(Long id, Long version) {
        this.id = id;
        this.version = version;
    }

    public static HisId of(Long id, Long version) {
        return new HisId(id, version);
    }
}
