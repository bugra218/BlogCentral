package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import be.intecbrussel.blogcentral.repository.TagRepository;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;
    private BlogpostService blogpostService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, BlogpostService blogpostService) {
        this.tagRepository = tagRepository;
        this.blogpostService = blogpostService;
    }

    @Override
    public void createTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Tag tagToDelete) {
        tagRepository.delete(tagToDelete);
    }

    @Override
    public void addTagsToPost(List<Tag> tags, BlogPost blogPost) {
        blogPost.setTags(tags);
        blogpostService.updateBlogPost(blogPost);
    }

    @Override
    public Tag getTagById(int tagId) {
        return tagRepository.findById(tagId).get();
    }

    @Override
    public List<Tag> getAllTagsFromPost(BlogPost blogPost) {
        return blogPost.getTags();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
