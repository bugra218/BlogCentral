package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;

public interface LikeService {
    void likePost(BlogPost blogPost, Author author);
    void removeLike(BlogPost blogPost, Author author);
    int countLikeByBlogPost_Id(int id);
    Boolean UserLikedPost(BlogPost blogPost, Author author);
}
