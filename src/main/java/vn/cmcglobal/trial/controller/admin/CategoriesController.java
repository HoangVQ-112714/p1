package vn.cmcglobal.trial.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.cmcglobal.trial.dto.CategoriesDTO;
import vn.cmcglobal.trial.entity.Categories;
import vn.cmcglobal.trial.mapper.CategoriesMapper;
import vn.cmcglobal.trial.repository.CategoriesRepository;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoriesController {
    @Autowired
    public CategoriesRepository categoriesRepository;

    @GetMapping
    public String findAll(Model model, @RequestParam(defaultValue = "1") int page) {
        Pageable firstPageWithTwoElements = PageRequest.of(page - 1, 3);
        Page<Categories> listCategories = categoriesRepository.findAll(firstPageWithTwoElements);
        int totalPages = listCategories.getTotalPages();
        model.addAttribute("categories", categoriesRepository.findAll(firstPageWithTwoElements).stream()
                .map(category -> CategoriesMapper.getInstance().toDTO(category))
                .collect(Collectors.toList()));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "backend/categories/index";
    }

    @GetMapping("/create")
    public String showSignUpForm(Categories categories) {
        return "backend/categories/add";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("categories") CategoriesDTO categoriesDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "backend/categories/add";
        }
        Categories categories = CategoriesMapper.getInstance().toEntity(categoriesDTO);
        categoriesRepository.save(categories);

        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") long id, Model model) {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("categories", categories);

        return "backend/categories/update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") long id, @Valid @ModelAttribute("categories") CategoriesDTO categoriesDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            categoriesDTO.setId(id);

            return "backend/categories/update";
        }

        Categories categories = categoriesRepository.findById(id).get();
        categories.setName(categoriesDTO.getName());
        categories.setDescription(categoriesDTO.getDescription());
        categoriesRepository.save(categories);

        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoriesRepository.deleteById(id);

        return "redirect:/admin/categories";
    }
}
