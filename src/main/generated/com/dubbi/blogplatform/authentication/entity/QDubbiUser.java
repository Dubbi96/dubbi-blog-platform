package com.dubbi.blogplatform.authentication.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDubbiUser is a Querydsl query type for DubbiUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDubbiUser extends EntityPathBase<DubbiUser> {

    private static final long serialVersionUID = 1758953496L;

    public static final QDubbiUser dubbiUser = new QDubbiUser("dubbiUser");

    public final com.dubbi.blogplatform.common.domain.entity.QBaseEntity _super = new com.dubbi.blogplatform.common.domain.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTs = _super.createTs;

    public final StringPath email = createString("email");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> endTs = _super.endTs;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyTs = _super.modifyTs;

    public final StringPath nickname = createString("nickname");

    public final StringPath picture = createString("picture");

    public final StringPath provider = createString("provider");

    public final EnumPath<com.dubbi.blogplatform.authorization.role.Role> role = createEnum("role", com.dubbi.blogplatform.authorization.role.Role.class);

    public final StringPath userName = createString("userName");

    public QDubbiUser(String variable) {
        super(DubbiUser.class, forVariable(variable));
    }

    public QDubbiUser(Path<? extends DubbiUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDubbiUser(PathMetadata metadata) {
        super(DubbiUser.class, metadata);
    }

}

