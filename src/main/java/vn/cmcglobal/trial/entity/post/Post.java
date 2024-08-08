package vn.cmcglobal.trial.entity.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import vn.cmcglobal.trial.entity.comment.Comment;
import vn.cmcglobal.trial.entity.user.User;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String title;
    @Column(columnDefinition = "TEXT")
    String content;
    Timestamp createdAt;
    Timestamp updatedAt;
    String thumbnail;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments;

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
