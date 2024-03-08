package com.dubbi.blogplatform.common.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "CREATE_TS")
    private LocalDateTime createTs;

    @LastModifiedDate
    @Column(name = "MODIFY_TS")
    private LocalDateTime modifyTs;

    @Column(name = "END_TS")
    private LocalDateTime endTs;

    public void delete() {
        this.endTs = LocalDateTime.now();
    }
}
