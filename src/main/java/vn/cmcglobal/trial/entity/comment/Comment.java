package vn.cmcglobal.trial.entity.comment;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import vn.cmcglobal.trial.entity.post.Post;
import vn.cmcglobal.trial.entity.user.User;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;
    Timestamp createdAt;
    Timestamp updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Post post;

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @PrePersist
    public void prePersist() {
        this.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }
}
