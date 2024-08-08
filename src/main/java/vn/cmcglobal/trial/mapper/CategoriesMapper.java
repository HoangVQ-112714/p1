package vn.cmcglobal.trial.mapper;

import vn.cmcglobal.trial.dto.CategoriesDTO;
import vn.cmcglobal.trial.entity.Categories;

public class CategoriesMapper {
    private static CategoriesMapper INSTANCE;

    public static CategoriesMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesMapper();
        }

        return INSTANCE;
    }

    public Categories toEntity(CategoriesDTO dto) {
        Categories categories = new Categories();
        categories.setName(dto.getName());
        categories.setDescription(dto.getDescription());

        return categories;
    }

    public CategoriesDTO toDTO(Categories categories) {
        CategoriesDTO dto = new CategoriesDTO();
        dto.setId(categories.getId());
        dto.setName(categories.getName());
        dto.setDescription(categories.getDescription());

        return dto;
    }
}
