package vn.cmcglobal.trial.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.cmcglobal.trial.payload.request.post.CommentRequest;
import vn.cmcglobal.trial.service.post.CommentServiceImplement;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("admin/comments")
public class CommentController {
    CommentServiceImplement commentServiceImplement;

    @PostMapping("/{postId}")
    public String createComment(@PathVariable long postId, @RequestBody CommentRequest commentRequest) {
        return "redirect:/admin/posts/" + postId;
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable long id, @RequestParam long postId) {
        commentServiceImplement.deleteComment(id);
        return "redirect:/admin/posts/" + postId;
    }
}
