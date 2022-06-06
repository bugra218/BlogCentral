package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Like countByBlogPost_Id(int id);
}
