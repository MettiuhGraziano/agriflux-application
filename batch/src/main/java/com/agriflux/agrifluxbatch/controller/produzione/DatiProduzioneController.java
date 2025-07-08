package com.agriflux.agrifluxbatch.controller.produzione;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneParticellaColturaOrtaggioDTO;
import com.agriflux.agrifluxshared.service.produzione.DatiProduzioneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/produzione")
@Tag(name = "Dati Produzione Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi alle Produzioni")
public class DatiProduzioneController implements DatiProduzioneService {
	
	private final DatiProduzioneService datiProduzioneService;
	
	public DatiProduzioneController(DatiProduzioneService datiProduzioneService) {
		this.datiProduzioneService = datiProduzioneService;
	}
	
	@Override
	@GetMapping("/findAllProduzioneSortById")
	@Operation(summary = "Recupera tutti i dati relativi alle Produzioni", description = "Restituisce una lista di Produzioni")
	public List<ProduzioneDTO> findAllProduzioneSortById() {
		return datiProduzioneService.findAllProduzioneSortById();
	}

	@Override
	@GetMapping("/findProduzioneQuantitaJoinColtura")
	@Operation(summary = "Mostra una mappa delle quantita' di raccolto prodotte e del fatturato raggruppate per nome ortaggio e anno di riferimento", description = "Restituisce una mappa in cui la chiave è il nome dell'Ortaggio e il valore è un mappa "
			+ " con in chiave l'anno di riferimento e come valore un oggetto contenente le quantità di raccolto prodotte ed il fatturato")
	public Map<String, Map<String, ProduzioneColturaDTO>> findProduzioneQuantitaJoinColtura() {
		return datiProduzioneService.findProduzioneQuantitaJoinColtura();
	}

	@Override
	@GetMapping("/findProduzioneJoinColturaTempi")
	@Operation(summary = "Mostra una mappa della lista dei tempi medi da semina a raccolto delle singole Colture per anno di riferimento", description = "Restituisce una mappa in cui la chiave è l'anno di riferimento e il valore è una lista "
			+ " di oggetti contenenti il prodotto coltivato e la lista delle medie dei tempi da semina a raccolto")
	public Map<String, List<ProduzioneColturaTempiDTO>> findProduzioneJoinColturaTempi() {
		return datiProduzioneService.findProduzioneJoinColturaTempi();
	}

	@Override
	@GetMapping("/findProduzioneParticellaColturaOrtaggio")
	@Operation(summary = "Mostra una mappa dei dati relativi alla Particella e al prodotto coltivato raggruppati per Produzione", description = "Restituisce una mappa in cui la chiave è la Produzione e il valore è un oggetto "
			+ " contenente i dati relativi alla Particella e al prodotto coltivato riferito a quella Produzione")
	public Map<Long, ProduzioneParticellaColturaOrtaggioDTO> findProduzioneParticellaColturaOrtaggio() {
		return datiProduzioneService.findProduzioneParticellaColturaOrtaggio();
	}

}
