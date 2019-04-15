
package airports;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportServiceTest {
    
    @Autowired
    private AirportService airportService;
    
    @Autowired
    private AirportRepository airportRepository;
    
    public AirportServiceTest() {
    }
    
    @Test
    public void testAirportCreation() {
        airportService.create("TST", "Test Airport");
        List<Airport> airports = airportService.list();
        boolean exists = false;
        for (Airport airport : airports) {
            if (airport.getIdentifier() == "TST") {
                exists = true;
                break;
            }
        }
        assertEquals(true, exists);  
    }
    
    @Test
    public void testAirportListing() {
        airportService.create("TST", "Test Airport");
        airportService.create("MDL", "Model Airport");
        
        List<Airport> airports = airportService.list();
        
        boolean firstExists = false;
        boolean secondExists = false;
        
        for (Airport airport : airports) {
            if (airport.getIdentifier() == "TST") {
                firstExists = true;
            }
            if (airport.getIdentifier() == "MDL") {
                secondExists = true;
            }
        }
        
        assertEquals(true, firstExists && secondExists);
    }
    
    @Test
    public void airportDuplicateCannotBeEntered() {
        airportService.create("TST", "Test Airport");
        airportService.create("TST", "Test Airport");
        
        List<Airport> airports = airportService.list();
        int counter = 0;
        
        for (Airport airport : airports) {
            if (airport.getIdentifier() == "TST" && airport.getName() == "Test Airport") {
                counter++;
            }
        }
        
        assertEquals(1, counter);
    }
}
