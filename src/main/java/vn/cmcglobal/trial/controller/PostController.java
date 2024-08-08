package vn.cmcglobal.trial.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.cmcglobal.trial.constant.PaginationConstant;
import vn.cmcglobal.trial.entity.post.Post;
import vn.cmcglobal.trial.payload.dto.PostDto;
import vn.cmcglobal.trial.payload.request.post.PostRequest;
import vn.cmcglobal.trial.service.post.PostServiceImplement;

import java.util.Optional;

@Controller
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostServiceImplement postServiceImplement;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> pageNo, @RequestParam("size") Optional<Integer> size) {
        int currentPage = pageNo.orElse(PaginationConstant.DEFAULT_PAGE);
        int pageSize = size.orElse(PaginationConstant.DEFAULT_PAGE_SIZE);

        Page<Post> posts = postServiceImplement.findPaginated(currentPage, pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", currentPage);
        return "backend/post/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Long id) {
        try {
            PostDto postDto = postServiceImplement.findById(id);
            model.addAttribute("postDto", postDto);
            return "backend/post/detail";
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        PostRequest postRequest = new PostRequest();
        String actionUrl = "/admin/posts/create";
        model.addAttribute("postDto", postRequest);
        model.addAttribute("actionUrl", actionUrl);
        return "backend/post/create";
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("postDto") PostRequest postRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("thumbnail") MultipartFile thumbnailFile) {
        if (thumbnailFile.isEmpty()) {
            bindingResult.rejectValue("thumbnail", "error.thumbnail.empty", "Please choose a thumbnail image.");
        }

        if (bindingResult.hasErrors()) {
            return "backend/post/create";
        }

        postServiceImplement.create(postRequest, thumbnailFile);

        return "redirect:/admin/posts";
    }

    @GetMapping("/edit/{id}")
    public String showUpdatePage(@PathVariable("id") long id, Model model) {
        try {
            PostDto postDto = postServiceImplement.findById(id);
            String actionUrl = String.format("/admin/posts/update/%d", id);
            model.addAttribute("postDto", postDto);
            model.addAttribute("actionUrl", actionUrl);

            return "backend/post/edit";
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid @ModelAttribute("postDto") PostRequest postRequest, BindingResult bindingResult, @RequestParam("thumbnail") MultipartFile thumbnailFile) {
        try {
            if (bindingResult.hasErrors()) {
                return "backend/post/edit";
            }
            postServiceImplement.update(id, postRequest, thumbnailFile);
            return "redirect:/admin/posts";
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") long id, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        try {
            int currentPage = page.orElse(PaginationConstant.DEFAULT_PAGE);
            int pageSize = size.orElse(PaginationConstant.DEFAULT_PAGE_SIZE);
            postServiceImplement.delete(id);
            return String.format("redirect:/admin/posts?page=%d&size=%d", currentPage, pageSize);
        } catch (Exception e) {
            return "backend/notfound";
        }
    }
}
