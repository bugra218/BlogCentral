package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogpostService {
    void createBlogPost(BlogPost blogPost);
    void updateBlogPost(BlogPost blogPost);
    void deleteBlogPost(BlogPost blogPost);
    BlogPost getBlogPostById(int id);

    BlogPost getBlogPostByTitle(String title);

    List<BlogPost> getAllBlogPosts(String field);
    List<BlogPost> getAllBlogPostsDescending(String field);
    List<BlogPost> getAllBlogPostFromAuthor(Author author);
    List<BlogPost> getAllBlogpostsByTitleContaining(String title);
    List<BlogPost> getAllBlogpostsByTagContaining(Tag tagName);
    Page<BlogPost> findPage(int pageNumber, String field);
    Page<BlogPost> findPageForAuthor(Author author, int pageNumber, String field);
}
