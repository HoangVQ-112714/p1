package vn.cmcglobal.trial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import response.UserResponse;
import vn.cmcglobal.trial.service.UserService;

@Controller
public class ContractController {
    @RequestMapping("/user/{id}")
    public String userShow( Model model, @PathVariable String id ) {
        UserResponse user = userService.userDetail(id);
        model.addAttribute("user", user);
        return "backend/user/detail";
    }
}
