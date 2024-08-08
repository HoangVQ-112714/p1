package vn.cmcglobal.trial.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.cmcglobal.trial.entity.comment.Comment;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
