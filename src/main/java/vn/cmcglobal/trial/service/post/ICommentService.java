package vn.cmcglobal.trial.service.post;

import vn.cmcglobal.trial.payload.request.post.CommentRequest;

public interface ICommentService {
    public void createComment(Long postId, CommentRequest comment);

    public void deleteComment(Long commentID);
}
