package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;

import java.util.List;

public interface TagService {
    void createTag(Tag tag);
    void deleteTag(Tag tagToDelete);
    void addTagsToPost(List<Tag> tags, BlogPost blogPost);
    Tag getTagById(int tagId);
    List<Tag> getAllTagsFromPost(BlogPost blogPost);
    List<Tag> getAllTags();
    List<Tag> getAllTagsByTagNameContaining(String tagName);
}
