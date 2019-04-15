package movies;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieTest extends org.fluentlenium.adapter.junit.FluentTest {
    
    @LocalServerPort
    private Integer port;
    
    @Test
    public void test() {
        // Mennään elokuvasivulle
        goTo("http://localhost:" + port + "/movies");
        
        // Tarkistetaan ettei sivulla ole tekstiä "Uuno Epsanjassa"
        assertFalse(pageSource().contains("Uuno Epsanjassa"));
        
        // Tarkistetaan ettei sivulla ole tekstiä "Uuno Turhapuro"
        assertFalse(pageSource().contains("Uuno Turhapuro"));
        
        // Etsitään kenttä, jonka id on "name" ja lisätään siihen arvo "Uuno Epsanjassa"
        find("#name").fill().with("Uuno Epsanjassa");
        
        // Etsitään kenttä, jonka id on "lengthInMinutes" ja lisätään siihen arvo "92"
        find("#lengthInMinutes").fill().with("92");
        
        // Lähetetään lomake
        find("form").first().submit();
        
        // Tarkistetaan että sivulla on teksti "Uuno Epsanjassa"
        assertTrue(pageSource().contains("Uuno Epsanjassa"));
        
        // Tarkistetaan ettei sivulla ole tekstiä "Uuno Turhapuro"
        assertFalse(pageSource().contains("Uuno Turhapuro"));
        
        // Mennään näyttelijäsivulle
        goTo("http://localhost:" + port + "/actors");
        
        // Tarkistetaan ettei sivulla ole tekstiä "Uuno Turhapuro"
        assertFalse(pageSource().contains("Uuno Turhapuro"));
        
        // Etsitään kenttä, jonka id on "name" ja asetetaan kenttään teksti "Uuno Turhapuro"
        find("#name").fill().with("Uuno Turhapuro");
        
        // Lähetetään lomake
        find("form").first().submit();
        
        // Tarkistetaan että sivulla on teksti "Uuno Turhapuro"
        assertTrue(pageSource().contains("Uuno Turhapuro"));
        
        // Etsitään linkki, jossa on teksti "Uuno Turhapuro" ja klikataan sitä
        find("a", withText("Uuno Turhapuro")).click();
        
        // Etsitään nappi, jonka id on "add-to-movie" ja klikataan sitä.
        find("#add-to-movie").click();
        
        // Mennään elokuvasivulle
        goTo("http://localhost:" + port + "/movies");
        
        // Tarkistetaan että sivulla on teksti "Uuno Epsanjassa"
        assertTrue(pageSource().contains("Uuno Epsanjassa"));
        
        // Tarkistetaan että sivulla on teksti "Uuno Turhapuro"
        assertTrue(pageSource().contains("Uuno Turhapuro"));
    }

}




