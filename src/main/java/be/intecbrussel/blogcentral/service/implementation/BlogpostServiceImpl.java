package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import be.intecbrussel.blogcentral.repository.BlogpostRepository;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
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
    public List<BlogPost> getAllBlogPosts(String field) {
        return blogpostRepository.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    @Override
    public List<BlogPost> getAllBlogPostsDescending(String field) {
        return blogpostRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    @Override
    public List<BlogPost> getAllBlogPostFromAuthor(Author author) {
        return blogpostRepository.findByAuthor(author);
    }

    // TODO: TRYING OUT PAGINATION AUTHOR PAGE - NOT WORKING YET - PLEASE KEEP
    @Override
    public Page<BlogPost> findPageForAuthor(Author author, int pageNumber,
                                         String field) {
        List<BlogPost> postsForAuthor = getAllBlogPostFromAuthor(author);

        Page<BlogPost> pageForAuthor = new PageImpl<>(postsForAuthor);

        Pageable pageable = pageForAuthor.getPageable();

        pageable = PageRequest.of(pageNumber - 1,6, Sort.by(Sort.Direction.ASC, field));

        return blogpostRepository.findAll(pageable);
    }

    @Override
    public List<BlogPost> getAllBlogpostsByTitleContaining(String title) {
        return blogpostRepository.findAllByTitleContaining(title);
    }

    @Override
    public List<BlogPost> getAllBlogpostsByTagContaining(Tag tagName) {
        return blogpostRepository.getAllByTagsContaining(tagName);
    }

    @Override
    public Page<BlogPost> findPage(int pageNumber, String field){
        Pageable pageable = PageRequest.of(pageNumber - 1,6, Sort.by(Sort.Direction.ASC, field));

        Page<BlogPost> allBlogPosts = blogpostRepository.findAll(pageable);
        return blogpostRepository.findAll(pageable);
    }
}
