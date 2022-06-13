package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByTagNameContaining(String tagName);
}
