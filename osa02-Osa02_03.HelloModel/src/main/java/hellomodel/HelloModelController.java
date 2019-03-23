package hellomodel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloModelController {
    
    @RequestMapping("/")
    public String home(Model model, @RequestParam String title, @RequestParam String person) {
        model.addAttribute("title", title);
        model.addAttribute("person", person);
        return "index";
    }

}
