package vn.cmcglobal.trial.service.post;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.cmcglobal.trial.payload.request.post.CommentRequest;
import vn.cmcglobal.trial.repository.post.ICommentRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImplement implements ICommentService {
    ICommentRepository commentRepository;

    /**
     * @param postId:  long
     * @param comment: CommentRequest
     */
    @Override
    public void createComment(Long postId, CommentRequest comment) {

    }

    /**
     * @param commentID : long
     */
    @Override
    public void deleteComment(Long commentID) {
        commentRepository.deleteById(commentID);
    }
}
