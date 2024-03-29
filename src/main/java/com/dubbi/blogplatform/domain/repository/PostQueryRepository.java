package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Post;
import com.dubbi.blogplatform.domain.entity.QPost;
import com.dubbi.blogplatform.domain.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory query;
    QPost post = QPost.post;

    public List<Post> findAllPost(User creator){
        return query.selectFrom(post)
                .where(notDeleted().and(eqUser(creator)))
                //.offset(page.getOffset())
                //.limit(page.getPageSize())
                .fetch();
    }

    public Optional<Post> findPost(User creator, Long postId){
        return Optional.ofNullable(query.selectFrom(post)
                .where(notDeleted().and(eqUser(creator)).and(post.id.eq(postId)))
                .fetchOne());
    }

    public BooleanExpression notDeleted(){
        return post.isDeactivated.isFalse();
    }
    public BooleanExpression eqUser(User creator){
        return post.creator.id.eq(creator.getId());
    }
}
