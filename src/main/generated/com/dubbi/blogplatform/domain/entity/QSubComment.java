package com.dubbi.blogplatform.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubComment is a Querydsl query type for SubComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubComment extends EntityPathBase<SubComment> {

    private static final long serialVersionUID = 1979036086L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubComment subComment = new QSubComment("subComment");

    public final com.dubbi.blogplatform.common.domain.entity.QBaseEntity _super = new com.dubbi.blogplatform.common.domain.entity.QBaseEntity(this);

    public final QComment CommentId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTs = _super.createTs;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> endTs = _super.endTs;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyTs = _super.modifyTs;

    public final StringPath text = createString("text");

    public QSubComment(String variable) {
        this(SubComment.class, forVariable(variable), INITS);
    }

    public QSubComment(Path<? extends SubComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubComment(PathMetadata metadata, PathInits inits) {
        this(SubComment.class, metadata, inits);
    }

    public QSubComment(Class<? extends SubComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.CommentId = inits.isInitialized("CommentId") ? new QComment(forProperty("CommentId"), inits.get("CommentId")) : null;
    }

}

