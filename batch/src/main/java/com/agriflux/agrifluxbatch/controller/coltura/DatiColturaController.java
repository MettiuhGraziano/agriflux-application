package com.agriflux.agrifluxbatch.controller.coltura;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.coltura.ColturaConsumoIdricoDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.service.coltura.DatiColturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/coltura")
@Tag(name = "Dati Coltura Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi alle Colture")
public class DatiColturaController implements DatiColturaService {
	
	private final DatiColturaService datiColturaService;
	
	DatiColturaController(DatiColturaService datiColturaService) {
		this.datiColturaService = datiColturaService;
	}
	
	@Override
	@GetMapping("/findAllColturaSortById")
	@Operation(summary = "Recupera tutte le Colture coltivate", description = "Restituisce una lista di colture")
	public List<ColturaDTO> findAllColturaSortById() {
		return datiColturaService.findAllColturaSortById();
	}

	@Override
	@GetMapping("/countOrtaggioColtura")
	@Operation(summary = "Conta le Colture raggruppate per nome Ortaggio", description = "Restituisce una mappa in cui la chiave è il tipo di Ortaggio e il valore è il numero di occorrenze trovate")
	public Map<String, Long> countOrtaggioColtura() {
		return datiColturaService.countOrtaggioColtura();
	}

	@Override
	@GetMapping("/countFamigliaOrtaggioColtura")
	@Operation(summary = "Conta le Colture raggruppate per Famiglia di Ortaggio", description = "Restituisce una mappa in cui la chiave è la Famiglia associata a quell'Ortaggio e il valore è il numero di occorrenze trovate")
	public Map<String, Long> countFamigliaOrtaggioColtura() {
		return datiColturaService.countFamigliaOrtaggioColtura();
	}
	
	@Override
	@GetMapping("/findPrezziAndDateRaccoltoColtura")
	@Operation(summary = "Mostra la lista di prezzi e date raccolto relative alle singole Colture", description = "Restituisce una mappa in cui la chiave è la Coltura e il valore è un oggetto contenente la lista di Date Raccolto e Prezzo al Kg relativi a quella Coltura")
	public Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateColtura() {
		return datiColturaService.findPrezziAndDateColtura();
	}

	@Override
	@GetMapping("/findColturaConsumoIdrico")
	@Operation(summary = "Mostra la lista relativa al consumo idrico per singola Coltura", description = "Restituisce una lista di DTo contenenti l'id della Coltura, la data di raccolto, il nome dell'Ortaggio, il consumo idrico di quella produzione e"
			+ " il consumo idrico medio relativo a quell'ortaggio")
	public List<ColturaConsumoIdricoDTO> findColturaConsumoIdrico() {
		return datiColturaService.findColturaConsumoIdrico();
	}

}
