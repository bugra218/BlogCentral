package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;

import java.util.List;

public interface BlogpostService {
    void createBlogPost(BlogPost blogPost);
    void updateBlogPost(BlogPost blogPost);
    void deleteBlogPost(BlogPost blogPost);
    BlogPost getBlogPostById(int id);
    List<BlogPost> getAllBlogPosts(String field);
    List<BlogPost> getAllBlogPostsDescending(String field);
    List<BlogPost> getAllBlogPostFromAuthor(Author author);
    List<BlogPost> getAllBlogpostsByTitleLike(String title);
}
