package vn.cmcglobal.trial.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import response.UserResponse;
import vn.cmcglobal.trial.dto.user.CreateRequest;
import vn.cmcglobal.trial.dto.user.UpdateRequest;
import vn.cmcglobal.trial.entity.user.EnumRoles;
import vn.cmcglobal.trial.entity.user.User;
import vn.cmcglobal.trial.service.BreadcrumbService;
import vn.cmcglobal.trial.service.UserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    BreadcrumbService breadcrumbService;
    UserService userService;

    @GetMapping
    public String userList(Model model,
       @RequestParam("page") Optional<Integer> page,
       @RequestParam("size") Optional<Integer> size) {
        handleBreadcrumb(model,"list");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        Page<User> users = userService.pagination(PageRequest.of(currentPage - 1, pageSize));

        int totalPage = users.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("users", users);

        return "backend/user/list";
    }

    @GetMapping("/create")
    public String userCreate(Model model) {
        handleBreadcrumb(model,"create");

        model.addAttribute("user", new User());
        model.addAttribute("action", "/users/create");
        model.addAttribute("roles", EnumRoles.values());

        return "backend/user/create";
    }

    @PostMapping("/create")
    public String userPost(@Valid @ModelAttribute("user") CreateRequest request,
                           BindingResult result,
                           Model model,
                           @RequestParam("avatar") MultipartFile file,
                           RedirectAttributes redirectAttributes
    ) {
        try {
            handleBreadcrumb(model,"create");
            model.addAttribute("user", request);
            model.addAttribute("roles", EnumRoles.values());

            if (!request.getEmail().isEmpty()) {
                Boolean checkUnique = userService.checkUniqueColumn("email", "", request.getEmail());

                if (!checkUnique) {
                    result.rejectValue("email", "error.email.unique", "Email already exists.");
                }
            }

            if (!request.getUsername().isEmpty()) {
                Boolean checkUnique = userService.checkUniqueColumn("username", "", request.getEmail());

                if (!checkUnique) {
                    result.rejectValue("username", "error.username.unique", "Username already exists.");
                }
            }

            if (result.hasErrors()) {
                return "backend/user/create";
            }

            userService.createUser(request, file);
            redirectAttributes.addFlashAttribute("success", "Update user successfully.");
            return "redirect:/users";
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    @GetMapping("/edit/{id}")
    public String userDetail(Model model, @PathVariable String id) {
        try {
            UserResponse user = userService.userDetail(id);

            handleBreadcrumb(model,"edit");

            model.addAttribute("user", user);
            model.addAttribute("action", "/users/update/"+id);
            model.addAttribute("roles", EnumRoles.values());

            return "backend/user/create";
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    @PostMapping("/update/{id}")
    public String userUpdate(@PathVariable String id, @Valid @ModelAttribute("user") UpdateRequest request,
                             BindingResult result,
                             Model model,
                             @RequestParam("avatar") MultipartFile file,
                             RedirectAttributes redirectAttributes
    ) {
        try {
            if (!request.getEmail().isEmpty()) {
                Boolean checkUnique = userService.checkUniqueColumn("email", request.getId(), request.getEmail());

                if (!checkUnique) {
                    result.rejectValue("email", "error.email.unique", "Email already exists.");
                }
            }

            if (!request.getUsername().isEmpty()) {
                Boolean checkUnique = userService.checkUniqueColumn("username", request.getId(), request.getEmail());

                if (!checkUnique) {
                    result.rejectValue("username", "error.username.unique", "Username already exists.");
                }
            }

            if (result.hasErrors()) {
                handleBreadcrumb(model,"edit");
                model.addAttribute("user", request);
                model.addAttribute("roles", EnumRoles.values());

                return "backend/user/create";
            }

            redirectAttributes.addFlashAttribute("success", "Update user successfully.");

            userService.updateUser(request, file, id);

            return "redirect:/users";
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    @GetMapping("/{id}")
    public String postDelete(@PathVariable String id,
           @RequestParam("page") int page,
           @RequestParam("size") int size
    ) {
        try {
            userService.deleteUser(id);

            return "redirect:/users?page="+page+"&size="+size;
        } catch (Exception e) {
            return "backend/notfound";
        }
    }

    private void handleBreadcrumb(Model model, String view) {
        Map<String, String> breadcrumbs = new LinkedHashMap<>();
        breadcrumbs.put("/home", "Home");
        breadcrumbs.put("/users", "Users");

        switch(view) {
            case "create":
                breadcrumbs.put("", "Create");
                break;
            case "edit":
                breadcrumbs.put("", "Edit");
                break;
            default:
                breadcrumbs.put("", "List");
        }
        breadcrumbService.addBreadcrumb(breadcrumbs);

        model.addAttribute("breadcrumb", breadcrumbService.getBreadcrumbs());
    }
}