package com.agriflux.agrifluxweb.controller;

import com.agriflux.agrifluxshared.dto.azienda.AziendaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxweb.service.dashboard.DashboardServiceImpl;
import com.agriflux.agrifluxweb.service.job.JobLauncherClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardServiceImpl dashboardService;

    @MockBean
    private JobLauncherClientServiceImpl jobLauncherService;

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLogout() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "testuser");

        mockMvc.perform(post("/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(request().sessionAttributeDoesNotExist("username"));
    }

    @Test
    void testDashboard() throws Exception {
        AziendaDTO mockAzienda = new AziendaDTO();
        mockAzienda.setRagioneSociale("Test Azienda"); // Corrected method name
        when(dashboardService.findAzienda()).thenReturn(mockAzienda);

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/dashboard")
                        .param("username", "testuser")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(request().sessionAttribute("username", "testuser"))
                .andExpect(request().sessionAttribute("azienda", mockAzienda));
    }

    @Test
    void testHomepage() throws Exception {
        AziendaDTO mockAzienda = new AziendaDTO();
        mockAzienda.setRagioneSociale("Test Azienda From Session");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("azienda", mockAzienda); // Simulate azienda being in session

        mockMvc.perform(get("/homepage").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/homepage :: homepagePage"));
    }

    @Test
    void testStartSimulationJob() throws Exception {
        when(jobLauncherService.startSimulationJob()).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));

        mockMvc.perform(post("/startSimulationJob"))
                .andExpect(status().isOk());
    }

    @Test
    void testIsJobExecuted() throws Exception {
        when(jobLauncherService.isJobExecuted()).thenReturn(true);
        mockMvc.perform(get("/isJobExecuted"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void testGetColtureDataModel() throws Exception {
        List<ColturaDTO> mockColtureList = Collections.singletonList(new ColturaDTO());
        when(dashboardService.findAllColturaSortById()).thenReturn(mockColtureList);

        mockMvc.perform(get("/coltura"))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/coltura :: colturaPage"))
                .andExpect(model().attributeExists("colture"))
                .andExpect(model().attribute("colture", mockColtureList));
    }

    @Test
    void testCountOrtaggioColtura() throws Exception {
        Map<String, Long> mockData = new HashMap<>();
        mockData.put("Tomato", 120L);
        mockData.put("Carrot", 80L);
        when(dashboardService.countOrtaggioColtura()).thenReturn(mockData);

        mockMvc.perform(get("/countOrtaggioColtura"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Tomato", is(120)))
                .andExpect(jsonPath("$.Carrot", is(80)));
    }

    @Test
    void testGetListaParametriAmbientali() throws Exception {
        List<String> mockParams = List.of("Temperature", "Humidity");
        when(dashboardService.getListaParametriAmbiente()).thenReturn(mockParams);

        mockMvc.perform(get("/getListaParametriAmbientali"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]", is("Temperature")))
                .andExpect(jsonPath("$[1]", is("Humidity")));
    }

    @Test
    void testFindListaProdottiColtivati() throws Exception {
        // This method has logic inside, so we test that logic too
        // For simplicity, mock the service call it makes
        // OrtaggioDTO has a 'nome' field
        com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO ortaggio1 = new com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO();
        ortaggio1.setNome("Tomato");
        com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO ortaggio2 = new com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO();
        ortaggio2.setNome("Carrot");

        List<com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO> mockOrtaggi = List.of(ortaggio1, ortaggio2);
        when(dashboardService.findAllOrtaggioSortById()).thenReturn(mockOrtaggi);

        mockMvc.perform(get("/findListaProdottiColtivati"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0]", is("Tomato")))
            .andExpect(jsonPath("$[1]", is("Carrot")));
    }
}
