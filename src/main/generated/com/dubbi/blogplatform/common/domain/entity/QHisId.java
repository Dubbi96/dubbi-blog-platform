package com.dubbi.blogplatform.common.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHisId is a Querydsl query type for HisId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QHisId extends BeanPath<HisId> {

    private static final long serialVersionUID = 1347303071L;

    public static final QHisId hisId = new QHisId("hisId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QHisId(String variable) {
        super(HisId.class, forVariable(variable));
    }

    public QHisId(Path<? extends HisId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHisId(PathMetadata metadata) {
        super(HisId.class, metadata);
    }

}

