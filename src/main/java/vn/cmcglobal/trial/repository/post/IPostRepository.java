package vn.cmcglobal.trial.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcglobal.trial.entity.post.Post;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
}
