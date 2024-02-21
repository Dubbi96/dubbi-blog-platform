package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.PostImage;
import com.dubbi.blogplatform.domain.entity.QPostImage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostImageQueryRepository {
    private final JPAQueryFactory query;
    QPostImage postImage = QPostImage.postImage;

    public List<PostImage> findAllPostImage(Long postId){
        return query.selectFrom(postImage)
                .where(eqPost(postId))
                .fetch();
    }

    public BooleanExpression eqPost(Long postId){return postImage.post.id.eq((postId));}
}
