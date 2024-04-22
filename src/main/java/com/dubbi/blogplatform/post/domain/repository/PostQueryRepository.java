package com.dubbi.blogplatform.post.domain.repository;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.imageserver.domain.entity.QImage;
import com.dubbi.blogplatform.post.domain.entity.Post;
import com.dubbi.blogplatform.post.domain.entity.QPost;
import com.dubbi.blogplatform.post.domain.entity.QPostCategory;
import com.dubbi.blogplatform.post.domain.entity.QPostImage;
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
    private final PostImageRepository postImageRepository;
    QPost post = QPost.post;
    QPostCategory postCategory = QPostCategory.postCategory;
    QPostImage postImage = QPostImage.postImage;
    QImage  image = QImage.image;

    public List<Post> findAllPostsWithDetails(User creator) {
        return query
                .selectDistinct(post)
                .from(post)
                .leftJoin(post.postCategory).fetchJoin()
                .leftJoin(post.creator).fetchJoin()
                .leftJoin(post.postImage, postImage).fetchJoin()
                .leftJoin(postImage.image,image).fetchJoin()
                .where(notDeleted().and(eqUser(creator)))
                .fetch();
    }


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
