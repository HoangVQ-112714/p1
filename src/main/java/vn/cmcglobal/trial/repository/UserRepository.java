package vn.cmcglobal.trial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcglobal.trial.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmailAndIdNot(String email, String id);
    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String email, String id);
    boolean existsByUsername(String email);
}
