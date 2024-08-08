package vn.cmcglobal.trial.payload.dto;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.cmcglobal.trial.entity.comment.Comment;
import vn.cmcglobal.trial.entity.user.User;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    long id;
    String title;
    String content;
    Timestamp createdAt;
    Timestamp updatedAt;
    String thumbnail;
    User user;
    List<Comment> comments;
}
