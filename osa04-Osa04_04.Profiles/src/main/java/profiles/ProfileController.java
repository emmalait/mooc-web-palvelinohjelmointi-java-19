package profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfileController {

    @Autowired
    Environment env;

    @Value("${spring.profiles.active:Unknown}")
    public String activeProfile;

    @ResponseBody
    @GetMapping("/profile")
    public String getProfile() {
        return activeProfile;
    }

    @GetMapping("/")
    public String home(Model model) {
        String[] activeProfiles = env.getActiveProfiles();
        System.out.println("profiles:" + activeProfiles);
        System.out.println("profile:" + activeProfile);
        model.addAttribute("profiles", activeProfiles);
        model.addAttribute("profile", activeProfile.toString());
        return "index";
    }

}
