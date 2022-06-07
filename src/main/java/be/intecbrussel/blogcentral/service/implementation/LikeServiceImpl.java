package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
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
    public void likePost(BlogPost blogPost, Author author) {
        Like like = new Like();
        like.setBlogPost(blogPost);
        like.setAuthor(author);
        likeRepository.save(like);
    }

    @Override
    public void removeLike(BlogPost blogPost, Author author) {
        Like like = likeRepository.findLikeByBlogPostAndAuthor(blogPost, author);
        likeRepository.delete(like);
    }

    @Override
    public int countLikeByBlogPost_Id(int id) {
        return likeRepository.countLikeByBlogPost_Id(id);
    }

    @Override
    public Boolean UserLikedPost(BlogPost blogPost, Author author) {
        Like like = likeRepository.findLikeByBlogPostAndAuthor(blogPost, author);
        if (like == null) {
            return false;
        } else {
            return true;
        }
    }
}
