package com.dubbi.blogplatform.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -673205566L;

    public static final QUser user = new QUser("user");

    public final com.dubbi.blogplatform.common.domain.entity.QBaseEntity _super = new com.dubbi.blogplatform.common.domain.entity.QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath city = createString("city");

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

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<com.dubbi.blogplatform.enumeratedClasses.Role> role = createEnum("role", com.dubbi.blogplatform.enumeratedClasses.Role.class);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.dubbi.blogplatform.enumeratedClasses.SocialType> socialType = createEnum("socialType", com.dubbi.blogplatform.enumeratedClasses.SocialType.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

