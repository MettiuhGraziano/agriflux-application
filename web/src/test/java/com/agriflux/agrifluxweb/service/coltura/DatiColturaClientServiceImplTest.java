package com.agriflux.agrifluxweb.service.coltura;

import com.agriflux.agrifluxshared.dto.coltura.ColturaConsumoIdricoDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DatiColturaClientServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private DatiColturaClientServiceImpl datiColturaClientService;

    private final String testBatchUrl = "http://localhost:8081/api/batch";

    @BeforeEach
    void setUp() {
        datiColturaClientService = new DatiColturaClientServiceImpl(restTemplate, testBatchUrl);
    }

    @Test
    void findAllColturaSortById() {
        String expectedUrl = testBatchUrl + "/findAllColturaSortById";
        List<ColturaDTO> expectedResponseData = Collections.singletonList(new ColturaDTO());
        ResponseEntity<List<ColturaDTO>> mockResponseEntity = new ResponseEntity<>(expectedResponseData, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<ColturaDTO>>>any())
        ).thenReturn(mockResponseEntity);

        List<ColturaDTO> actualResponseData = datiColturaClientService.findAllColturaSortById();

        assertSame(expectedResponseData, actualResponseData);
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<ColturaDTO>>>any()
        );
    }

    @Test
    void countOrtaggioColtura() {
        String expectedUrl = testBatchUrl + "/countOrtaggioColtura";
        Map<String, Long> expectedResponseData = Collections.singletonMap("Tomato", 100L);
        ResponseEntity<Map<String, Long>> mockResponseEntity = new ResponseEntity<>(expectedResponseData, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Long>>>any())
        ).thenReturn(mockResponseEntity);

        Map<String, Long> actualResponseData = datiColturaClientService.countOrtaggioColtura();

        assertSame(expectedResponseData, actualResponseData);
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Long>>>any()
        );
    }

    @Test
    void countFamigliaOrtaggioColtura() {
        String expectedUrl = testBatchUrl + "/countFamigliaOrtaggioColtura";
        Map<String, Long> expectedResponseData = Collections.singletonMap("Solanaceae", 50L);
        ResponseEntity<Map<String, Long>> mockResponseEntity = new ResponseEntity<>(expectedResponseData, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Long>>>any())
        ).thenReturn(mockResponseEntity);

        Map<String, Long> actualResponseData = datiColturaClientService.countFamigliaOrtaggioColtura();

        assertSame(expectedResponseData, actualResponseData);
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Long>>>any()
        );
    }

    @Test
    void findPrezziAndDateColtura() {
        String expectedUrl = testBatchUrl + "/findPrezziAndDateRaccoltoColtura";
        Map<String, ColturaListPrezzoDataRaccoltoDTO> expectedResponseData = Collections.singletonMap("Carrot", new ColturaListPrezzoDataRaccoltoDTO());
        ResponseEntity<Map<String, ColturaListPrezzoDataRaccoltoDTO>> mockResponseEntity =
                new ResponseEntity<>(expectedResponseData, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, ColturaListPrezzoDataRaccoltoDTO>>>any())
        ).thenReturn(mockResponseEntity);

        Map<String, ColturaListPrezzoDataRaccoltoDTO> actualResponseData = datiColturaClientService.findPrezziAndDateColtura();

        assertSame(expectedResponseData, actualResponseData);
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, ColturaListPrezzoDataRaccoltoDTO>>>any()
        );
    }

    @Test
    void findColturaConsumoIdrico() {
        String expectedUrl = testBatchUrl + "/findColturaConsumoIdrico";
        List<ColturaConsumoIdricoDTO> expectedResponseData = Collections.singletonList(new ColturaConsumoIdricoDTO());
        ResponseEntity<List<ColturaConsumoIdricoDTO>> mockResponseEntity =
                new ResponseEntity<>(expectedResponseData, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<ColturaConsumoIdricoDTO>>>any())
        ).thenReturn(mockResponseEntity);

        List<ColturaConsumoIdricoDTO> actualResponseData = datiColturaClientService.findColturaConsumoIdrico();

        assertSame(expectedResponseData, actualResponseData);
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<ColturaConsumoIdricoDTO>>>any()
        );
    }
}
