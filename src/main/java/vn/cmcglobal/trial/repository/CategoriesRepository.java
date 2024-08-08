package vn.cmcglobal.trial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcglobal.trial.entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
