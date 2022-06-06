package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Like;

public interface LikeService {
    Like getLikesForPost(BlogPost blogPost);
    void likePost(BlogPost blogPost);
    void removeLike(Like like);
}
