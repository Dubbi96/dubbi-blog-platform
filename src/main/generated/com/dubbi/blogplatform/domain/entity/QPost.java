package com.dubbi.blogplatform.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -673357929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.dubbi.blogplatform.common.domain.entity.QBaseEntity _super = new com.dubbi.blogplatform.common.domain.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTs = _super.createTs;

    public final QUser creator;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> endTs = _super.endTs;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath is_deactivated = createBoolean("is_deactivated");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyTs = _super.modifyTs;

    public final QPostCategory postCategory;

    public final ListPath<PostImage, QPostImage> postImage = this.<PostImage, QPostImage>createList("postImage", PostImage.class, QPostImage.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUser(forProperty("creator")) : null;
        this.postCategory = inits.isInitialized("postCategory") ? new QPostCategory(forProperty("postCategory"), inits.get("postCategory")) : null;
    }

}

