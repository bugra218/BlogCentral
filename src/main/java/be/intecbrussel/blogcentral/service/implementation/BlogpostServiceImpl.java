package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.repository.BlogpostRepository;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        Timestamp ts = Timestamp.from(Instant.now());
        String ts_created = String.format("%1$Td %1$Tb %1$Ty, %1$TR", ts);
        blogPost.setTimestampCreatedDisplay(ts_created);
        blogPost.setTimestampUpdatedDisplay(ts_created);
        blogpostRepository.save(blogPost);
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {
        Timestamp ts = Timestamp.from(Instant.now());
        blogPost.setTimestampUpdated(ts);
        String ts_updated = String.format("%1$Td %1$Tb %1$Ty, %1$TR", ts);
        blogPost.setTimestampUpdatedDisplay(ts_updated);
        blogpostRepository.save(blogPost);
    }

    @Override
    public void deleteBlogPost(BlogPost blogPost) {
        blogpostRepository.delete(blogPost);
    }

    @Override
    public BlogPost getBlogPostById(int id) {
        BlogPost blogPost = blogpostRepository.findById(id).get();
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

//    private BlogPost timestampConverter(BlogPost blogPost) {
//        Timestamp timestampDB = blogPost.getTimestampCreated();
//
//
//        return null; // placeholder
//    }
}
