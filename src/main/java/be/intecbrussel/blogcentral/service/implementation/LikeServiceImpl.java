package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Like;
import be.intecbrussel.blogcentral.repository.BlogpostRepository;
import be.intecbrussel.blogcentral.repository.LikeRepository;
import be.intecbrussel.blogcentral.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private BlogpostRepository blogpostRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, BlogpostRepository blogpostRepository) {
        this.likeRepository = likeRepository;
        this.blogpostRepository = blogpostRepository;
    }

    @Override
    public Like getLikesForPost(BlogPost blogPost) {
        return likeRepository.countByBlogPost_Id(blogPost.getId());
    }

    @Override
    public void likePost(BlogPost blogPost) {
        Like like = new Like();
        like.setBlogPost(blogPost);
        likeRepository.save(like);
    }

    @Override
    public void removeLike(Like like) {
        likeRepository.delete(like);
    }
}
