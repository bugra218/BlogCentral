package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.repository.BlogpostRepository;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BlogpostServiceImpl implements BlogpostService {
    private BlogpostRepository blogpostRepository;

    @Autowired
    public BlogpostServiceImpl(BlogpostRepository blogpostRepository) {
        this.blogpostRepository = blogpostRepository;
    }

    @Override
    public void createBlogPost(BlogPost blogPost) {
        java.util.Date date = new java.util.Date();
        Date now = new Date(date.getTime());
        blogPost.setTimestampCreated(now);
        blogpostRepository.save(blogPost);
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {
        blogpostRepository.save(blogPost);
    }

    @Override
    public void deleteBlogPost(BlogPost blogPost) {
        blogpostRepository.delete(blogPost);
    }

    @Override
    public BlogPost getBlogPostById(int id) {
        return blogpostRepository.findById(id).get();
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogpostRepository.findAll();
    }

    @Override
    public List<BlogPost> getAllBlogPostFromAuthor(Author author) {
        return blogpostRepository.findByAuthor(author);
    }
}
