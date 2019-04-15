package airports;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void statusOkAndModelHasAttributesAircraftsAndAirports() throws Exception {
        mockMvc.perform(get("/aircrafts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("aircrafts"))
                .andExpect(model().attributeExists("airports"));
    }

    @Test
    public void postingAircraftWorks() throws Exception {
        mockMvc.perform(post("/aircrafts")
                .param("name", "HA-LOL"))
                .andExpect(redirectedUrl("/aircrafts"));

        List<Aircraft> aircrafts = aircraftRepository.findAll();
        boolean exists = false;

        for (Aircraft aircraft : aircrafts) {
            if (aircraft.getName() == "HA-LOL") {
                exists = true;
            }
        }

        Assert.assertTrue(exists);
    }

    @Test
    public void aircraftsCorrect() throws Exception {
        mockMvc.perform(post("/aircrafts")
                .param("name", "XP-55"))
                .andExpect(redirectedUrl("/aircrafts"));
        
        MvcResult res = mockMvc.perform(get("/aircrafts"))
                .andReturn();

        List<Aircraft> aircrafts = (List) res.getModelAndView().getModel().get("aircrafts");
        boolean exists = false;
        
        for (Aircraft aircraft : aircrafts) {
            if (aircraft.getName() == "XP-55") {
                exists = true;
                break;
            }
        }
        
        Assert.assertTrue(exists);
    }
}
