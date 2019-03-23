package todoapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoApplicationController {
    
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("items", this.taskRepository.findAll());
        return "index";
    }
    
    @PostMapping("/")
    public String create(@RequestParam String name) {
        taskRepository.save(new Task(name, 0));
        return "redirect:/";
    }
    
    @GetMapping("/{id}")
    public String getOne(Model model, @PathVariable Long id) {
        Task task = taskRepository.getOne(id);
        task.setChecked(task.getChecked() + 1);
        taskRepository.save(task);
        model.addAttribute("item", task);
        return "todo";
    }

}
