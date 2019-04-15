package movies;

import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActorTest extends org.fluentlenium.adapter.junit.FluentTest {
    @Autowired
    private ActorRepository actorRepository;
    
    @LocalServerPort
    private Integer port;

    @Test
    public void creationAndDeletionWorks() {
        // Mennään näyttelijäsivulle
        goTo("http://localhost:" + port + "/actors");
        
        // Tarkistetaan ettei sivulla ole tekstiä "Uuno Turhapuro"
        assertFalse(pageSource().contains("Uuno Turhapuro"));
        
        // Etsitään kenttä, jonka id on "name" ja asetetaan kenttään teksti "Uuno Turhapuro".
        find("#name").fill().with("Uuno Turhapuro");
        
        // Lähetetään lomake.
        find("form").first().submit();
        
        // Tarkistetaan että sivulla on teksti "Uuno Turhapuro"
        assertTrue(pageSource().contains("Uuno Turhapuro"));
        
        // Klikataan "Uuno Turhapuro"on liittyvää poista-nappia
        List<Actor> actors = actorRepository.findAll();
        int id = 1;
        
        for (Actor actor : actors) {
            if (actor.getName() == "Uuno Turhapuro") {
                id = (int) (long) actor.getId();
                break;
            }
        }
        
        find("#remove-" + String.valueOf(id)).click();
        
        // Tarkistetaan ettei sivulla ole tekstiä "Uuno Turhapuro"
        assertFalse(pageSource().contains("Uuno Turhapuro"));
    }
    
}