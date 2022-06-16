package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import be.intecbrussel.blogcentral.repository.BlogpostRepository;
import be.intecbrussel.blogcentral.repository.LikeRepository;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogpostServiceImpl implements BlogpostService {
    private BlogpostRepository blogpostRepository;
    private LikeRepository likeRepository;

    @Autowired
    public BlogpostServiceImpl(BlogpostRepository blogpostRepository, LikeRepository likeRepository) {
        this.blogpostRepository = blogpostRepository;
        this.likeRepository = likeRepository;
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
    public Page<BlogPost> findPage(int pageNumber, String field) {
        Pageable pageable;
        if (field.equals("recent")) {
            pageable = PageRequest.of(pageNumber - 1,6, Sort.by(Sort.Direction.DESC, "timestampCreated"));
        }
        else {
            pageable = PageRequest.of(pageNumber - 1,6, Sort.by(Sort.Direction.ASC, "timestampCreated"));
        }
        return blogpostRepository.findAll(pageable);
//        else if (field.equals("oldest")) {
//            pageable = PageRequest.of(pageNumber - 1,6, Sort.by(Sort.Direction.ASC, "timestampCreated"));
//            return blogpostRepository.findAll(pageable);
//        }
//        else {
//            List<BlogPost> allBlogPosts = blogpostRepository.findAll();
//            Map<Integer, Integer> unsortedLikes = new HashMap<>();
//
//            for (BlogPost post : allBlogPosts) {
//                unsortedLikes.put(post.getId(), likeRepository.countLikeByBlogPost_Id(post.getId()));
//            }
//
//            Map<Integer, Integer> result = unsortedLikes.entrySet().stream()
//                    .sorted(Map.Entry.comparingByValue())
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//            List<Integer> sortedBlogPostId = result.entrySet().stream()
//                    .sorted(Comparator.comparing(Map.Entry::getValue))
//                    .map(Map.Entry::getKey)
//                    .collect(Collectors.toList());
//
//            List<BlogPost> sortedBlogPosts = new ArrayList<>();
//            for (Integer postId : sortedBlogPostId) {
//                sortedBlogPosts.add(blogpostRepository.findById(postId).get());
//            }
//
//            pageable = PageRequest.of(pageNumber - 1,6);
//            PageImpl<BlogPost> pageableMostLikesPosts = new PageImpl<>(sortedBlogPosts, pageable, pageable.getPageNumber());
//
//            System.out.println("Map: ");
//            result.forEach((k,v)->System.out.println(k+"="+v));
//            System.out.println("List: ");
//            sortedBlogPostId.forEach(System.out::println);
//            System.out.println("BlogPostList: ");
//            sortedBlogPosts.forEach(System.out::println);
//            System.out.println("PageImpl: ");
//            pageableMostLikesPosts.forEach(System.out::println);
////            pageable = PageRequest.of(pageNumber - 1,6, Sort.by(Sort.Direction.DESC));
////            return PageRequest.of(pageNumber - 1,6);
//            return pageableMostLikesPosts;
//        }
    }
}
