package com.agriflux.agrifluxweb.service.dashboard;

import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.dto.ambiente.VariazioneValoriParametriAmbienteDTO;
import com.agriflux.agrifluxshared.dto.azienda.AziendaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaConsumoIdricoDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoRicaviSpeseDTO;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO;
import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneParticellaColturaOrtaggioDTO;
import com.agriflux.agrifluxshared.dto.terreno.ParticellaColturaTerrenoDTO;
import com.agriflux.agrifluxshared.dto.terreno.TerrenoDTO;
import com.agriflux.agrifluxweb.service.ambiente.DatiAmbienteClientServiceImpl;
import com.agriflux.agrifluxweb.service.azienda.DatiAziendaClientServiceImpl;
import com.agriflux.agrifluxweb.service.coltura.DatiColturaClientServiceImpl;
import com.agriflux.agrifluxweb.service.fatturato.DatiFatturatoClientServiceImpl;
import com.agriflux.agrifluxweb.service.ortaggio.DatiOrtaggioClientServiceImpl;
import com.agriflux.agrifluxweb.service.particella.DatiParticellaClientServiceImpl;
import com.agriflux.agrifluxweb.service.produzione.DatiProduzioneClientServiceImpl;
import com.agriflux.agrifluxweb.service.terreno.DatiRilevazioneTerrenoClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {

    @Mock
    private DatiColturaClientServiceImpl datiColturaService;
    @Mock
    private DatiParticellaClientServiceImpl datiParticellaService;
    @Mock
    private DatiRilevazioneTerrenoClientServiceImpl datiRilevazioneTerrenoService;
    @Mock
    private DatiProduzioneClientServiceImpl datiProduzioneService;
    @Mock
    private DatiOrtaggioClientServiceImpl datiOrtaggioService;
    @Mock
    private DatiAmbienteClientServiceImpl datiAmbienteService;
    @Mock
    private DatiAziendaClientServiceImpl datiAziendaService;
    @Mock
    private DatiFatturatoClientServiceImpl datiFatturatoService;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Test
    void findAllParticellaSortById() {
        List<DatiParticellaDTO> expected = Collections.emptyList();
        when(datiParticellaService.findAllParticellaSortById()).thenReturn(expected);
        List<DatiParticellaDTO> actual = dashboardService.findAllParticellaSortById();
        assertSame(expected, actual);
        verify(datiParticellaService).findAllParticellaSortById();
    }

    @Test
    void findParticellaJoinColturaTerreno() {
        Map<Long, List<ParticellaColturaTerrenoDTO>> expected = Collections.emptyMap();
        when(datiParticellaService.findParticellaJoinColturaTerreno()).thenReturn(expected);
        Map<Long, List<ParticellaColturaTerrenoDTO>> actual = dashboardService.findParticellaJoinColturaTerreno();
        assertSame(expected, actual);
        verify(datiParticellaService).findParticellaJoinColturaTerreno();
    }

    @Test
    void findAllColturaSortById() {
        List<ColturaDTO> expected = Collections.emptyList();
        when(datiColturaService.findAllColturaSortById()).thenReturn(expected);
        List<ColturaDTO> actual = dashboardService.findAllColturaSortById();
        assertSame(expected, actual);
        verify(datiColturaService).findAllColturaSortById();
    }

    @Test
    void countOrtaggioColtura() {
        Map<String, Long> expected = Collections.emptyMap();
        when(datiColturaService.countOrtaggioColtura()).thenReturn(expected);
        Map<String, Long> actual = dashboardService.countOrtaggioColtura();
        assertSame(expected, actual);
        verify(datiColturaService).countOrtaggioColtura();
    }

    @Test
    void countFamigliaOrtaggioColtura() {
        Map<String, Long> expected = Collections.emptyMap();
        when(datiColturaService.countFamigliaOrtaggioColtura()).thenReturn(expected);
        Map<String, Long> actual = dashboardService.countFamigliaOrtaggioColtura();
        assertSame(expected, actual);
        verify(datiColturaService).countFamigliaOrtaggioColtura();
    }

    @Test
    void findPrezziAndDateRaccoltoColtura() {
        Map<String, ColturaListPrezzoDataRaccoltoDTO> expected = Collections.emptyMap();
        when(datiColturaService.findPrezziAndDateColtura()).thenReturn(expected); // Corrected method name
        Map<String, ColturaListPrezzoDataRaccoltoDTO> actual = dashboardService.findPrezziAndDateRaccoltoColtura();
        assertSame(expected, actual);
        verify(datiColturaService).findPrezziAndDateColtura(); // Corrected method name
    }

    @Test
    void findColturaConsumoIdrico() {
        List<ColturaConsumoIdricoDTO> expected = Collections.emptyList();
        when(datiColturaService.findColturaConsumoIdrico()).thenReturn(expected);
        List<ColturaConsumoIdricoDTO> actual = dashboardService.findColturaConsumoIdrico();
        assertSame(expected, actual);
        verify(datiColturaService).findColturaConsumoIdrico();
    }

    @Test
    void findAllRilevazioneTerrenoSortById() {
        List<TerrenoDTO> expected = Collections.emptyList();
        when(datiRilevazioneTerrenoService.findAllRilevazioneTerrenoSortById()).thenReturn(expected);
        List<TerrenoDTO> actual = dashboardService.findAllRilevazioneTerrenoSortById();
        assertSame(expected, actual);
        verify(datiRilevazioneTerrenoService).findAllRilevazioneTerrenoSortById();
    }

    @Test
    void findAllProduzioneSortById() {
        List<ProduzioneDTO> expected = Collections.emptyList();
        when(datiProduzioneService.findAllProduzioneSortById()).thenReturn(expected);
        List<ProduzioneDTO> actual = dashboardService.findAllProduzioneSortById();
        assertSame(expected, actual);
        verify(datiProduzioneService).findAllProduzioneSortById();
    }

    @Test
    void findProduzioneQuantitaJoinColtura() {
        Map<String, Map<String, ProduzioneColturaDTO>> expected = Collections.emptyMap();
        when(datiProduzioneService.findProduzioneQuantitaJoinColtura()).thenReturn(expected);
        Map<String, Map<String, ProduzioneColturaDTO>> actual = dashboardService.findProduzioneQuantitaJoinColtura();
        assertSame(expected, actual);
        verify(datiProduzioneService).findProduzioneQuantitaJoinColtura();
    }

    @Test
    void findProduzioneJoinColturaTempi() {
        Map<String, List<ProduzioneColturaTempiDTO>> expected = Collections.emptyMap();
        when(datiProduzioneService.findProduzioneJoinColturaTempi()).thenReturn(expected);
        Map<String, List<ProduzioneColturaTempiDTO>> actual = dashboardService.findProduzioneJoinColturaTempi();
        assertSame(expected, actual);
        verify(datiProduzioneService).findProduzioneJoinColturaTempi();
    }

    @Test
    void findProduzioneParticellaColturaOrtaggio() {
        Map<Long, ProduzioneParticellaColturaOrtaggioDTO> expected = Collections.emptyMap();
        when(datiProduzioneService.findProduzioneParticellaColturaOrtaggio()).thenReturn(expected);
        Map<Long, ProduzioneParticellaColturaOrtaggioDTO> actual = dashboardService.findProduzioneParticellaColturaOrtaggio();
        assertSame(expected, actual);
        verify(datiProduzioneService).findProduzioneParticellaColturaOrtaggio();
    }

    @Test
    void findAllAmbienteSortById() {
        List<AmbienteDTO> expected = Collections.emptyList();
        when(datiAmbienteService.findAllAmbienteSortById()).thenReturn(expected);
        List<AmbienteDTO> actual = dashboardService.findAllAmbienteSortById();
        assertSame(expected, actual);
        verify(datiAmbienteService).findAllAmbienteSortById();
    }

    @Test
    void getListaParametriAmbiente() {
        List<String> expected = Collections.emptyList();
        when(datiAmbienteService.getListaParametriAmbiente()).thenReturn(expected);
        List<String> actual = dashboardService.getListaParametriAmbiente();
        assertSame(expected, actual);
        verify(datiAmbienteService).getListaParametriAmbiente();
    }

    @Test
    void getVariazioneValoriParametriAmbiente() {
        Map<String, List<VariazioneValoriParametriAmbienteDTO>> expected = Collections.emptyMap();
        when(datiAmbienteService.getVariazioneValoriParametriAmbiente()).thenReturn(expected);
        Map<String, List<VariazioneValoriParametriAmbienteDTO>> actual = dashboardService.getVariazioneValoriParametriAmbiente();
        assertSame(expected, actual);
        verify(datiAmbienteService).getVariazioneValoriParametriAmbiente();
    }

    @Test
    void findAllOrtaggioSortById() {
        List<OrtaggioDTO> expected = Collections.emptyList();
        when(datiOrtaggioService.findAllOrtaggioSortById()).thenReturn(expected);
        List<OrtaggioDTO> actual = dashboardService.findAllOrtaggioSortById();
        assertSame(expected, actual);
        verify(datiOrtaggioService).findAllOrtaggioSortById();
    }

    @Test
    void findAzienda() {
        AziendaDTO expected = new AziendaDTO(); // Or mock if it has complex structure
        when(datiAziendaService.findAzienda()).thenReturn(expected);
        AziendaDTO actual = dashboardService.findAzienda();
        assertSame(expected, actual);
        verify(datiAziendaService).findAzienda();
    }

    @Test
    void findAllFatturatoSortById() {
        List<FatturatoDTO> expected = Collections.emptyList();
        when(datiFatturatoService.findAllFatturatoSortById()).thenReturn(expected);
        List<FatturatoDTO> actual = dashboardService.findAllFatturatoSortById();
        assertSame(expected, actual);
        verify(datiFatturatoService).findAllFatturatoSortById();
    }

    @Test
    void findFatturatoRicaviSpese() {
        Map<Long, List<FatturatoRicaviSpeseDTO>> expected = Collections.emptyMap();
        when(datiFatturatoService.findFatturatoRicaviSpese()).thenReturn(expected);
        Map<Long, List<FatturatoRicaviSpeseDTO>> actual = dashboardService.findFatturatoRicaviSpese();
        assertSame(expected, actual);
        verify(datiFatturatoService).findFatturatoRicaviSpese();
    }
}
