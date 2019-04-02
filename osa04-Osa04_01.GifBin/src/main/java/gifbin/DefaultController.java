package gifbin;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DefaultController {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/gifs";
    }

    @GetMapping("/gifs")
    public String redirectFromGifs() {
        return "redirect:/gifs/1";
    }

    @GetMapping("/gifs/{id}")
    public String ViewGif(Model model, @PathVariable Long id) {
        Long count = fileObjectRepository.count();
        Long previous;
        Long next;
        
        if (id - 1 < 1) {
            previous = null;
        } else {
            previous = id - 1;
        }
        
        if (id + 1 > count) {
            next = null;
        } else {
            next = id + 1;
        }
        
        model.addAttribute("count", count);
        model.addAttribute("current", id);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        return "gifs";
    }

    @PostMapping("/gifs")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getContentType().equals("image/gif")) {
            FileObject fo = new FileObject();
            fo.setContent(file.getBytes());
            fileObjectRepository.save(fo);
        }

        return "redirect:/gifs";
    }

    @GetMapping(path = "/gifs/{id}/content", produces = "image/gif")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return fileObjectRepository.getOne(id).getContent();
    }

}
