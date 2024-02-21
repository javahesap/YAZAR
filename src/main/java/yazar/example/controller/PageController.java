package yazar.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/master")
    public String showMasterPage(Model model) {
        model.addAttribute("title", "Master Page");
        return "master";
    }

    @GetMapping("/child")
    public String showChildPage(Model model) {
        model.addAttribute("title", "Child Page");
        return "child";
    }

    @GetMapping("/anotherChild")
    public String showAnotherChildPage(Model model) {
        model.addAttribute("title", "Another Child Page");
        return "anotherChild";
    }
}
