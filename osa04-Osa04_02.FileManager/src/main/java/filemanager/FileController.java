package filemanager;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    /*
    - Kun käyttäjä tekee GET-tyyppisen pyynnön osoitteeseen /files, pyyntöön lisätään 
    tietokannasta löytyvät tiedostot ja käyttäjä ohjataan sivulle files.html.
     */
    @GetMapping("/files")
    private String view(Model model) {
        List<File> files = fileRepository.findAll();

        model.addAttribute("files", files);
        return "files";
    }

    /*
    - Kun käyttäjä lähettää lomakkeella tiedoston osoitteeseen /files, pyynnöstä 
    otetaan talteen kaikki tiedot mitä näkymässä halutaan näyttää, ja tallennetaan 
    ne tietokantaan. Pyyntö ohjataan lopuksi uudelleen osoitteeseen /files.
     */
    @PostMapping("/files")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        File f = new File();

        f.setName(file.getOriginalFilename());
        f.setContentType(file.getContentType());
        f.setContentLength(file.getSize());
        f.setContent(file.getBytes());

        fileRepository.save(f);

        return "redirect:/files";
    }

    /*
    - Kun käyttäjä klikkaa yksittäiseen tiedostoon liittyvää nimeä sen lataamista 
    varten, tulee tiedosto lähettää käyttäjälle. Aseta pyyntöön datan lisäksi myös 
    tiedoston mediatyyppi että ja ehdotus tiedoston tallennusnimestä.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        File f = fileRepository.getOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(f.getContentType()));
        headers.setContentLength(f.getContentLength());
        headers.add("Content-Disposition", "attachment; filename=" + f.getName());

        return new ResponseEntity<>(f.getContent(), headers, HttpStatus.CREATED);
    }
}
