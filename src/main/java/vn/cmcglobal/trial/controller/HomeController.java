package vn.cmcglobal.trial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping
    public String index() {
        return "frontend/index";
    }
}
