package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Post;
import com.dubbi.blogplatform.domain.entity.QPost;
import com.dubbi.blogplatform.domain.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Post findPost(User creator, Long postId){
        return query.selectFrom(post)
                .where(notDeleted().and(eqUser(creator)).and(post.id.eq(postId)))
                .fetchOne();
    }
    public BooleanExpression notDeleted(){
        return post.is_deactivated.isFalse();
    }
    public BooleanExpression eqUser(User creator){
        return post.creator.id.eq(creator.getId());
    }
}
