package com.agriflux.agrifluxbatch.controller.ambiente;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.dto.ambiente.VariazioneValoriParametriAmbienteDTO;
import com.agriflux.agrifluxshared.service.ambiente.DatiAmbienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/ambiente")
@Tag(name = "Dati Ambiente Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi alle rilevazioni Ambientali")
public class DatiAmbienteController implements DatiAmbienteService {
	
	private final DatiAmbienteService datiAmbienteService;
	
	DatiAmbienteController(DatiAmbienteService datiAmbienteService) {
		this.datiAmbienteService = datiAmbienteService;
	}
	
	@Override
	@GetMapping("/findAllAmbienteSortById")
	@Operation(summary = "Recupera tutti i dati Ambientali", description = "Restituisce una lista di dati Ambientali")
	public List<AmbienteDTO> findAllAmbienteSortById() {
		return datiAmbienteService.findAllAmbienteSortById();
	}

	@Override
	@GetMapping("/getListaParametriAmbiente")
	@Operation(summary = "Recupera la lista dei parametri Ambientali", description = "Restituisce la lista dei nomi dei parametri Ambientali calcolati")
	public List<String> getListaParametriAmbiente() {
		return datiAmbienteService.getListaParametriAmbiente();
	}

	@Override
	@GetMapping("/getVariazioneValoriParametriAmbiente")
	@Operation(summary = "Mostra una mappa con la variazione dei parametri ambientali nel corso del tempo", description = "Restituisce una mappa con in chiave il nome del parametro ambientale e come valore"
			+ " una lista di oggetti contenenti il valore del parametro, la data di rilevazione ambientale e la variazione percentuale rispetto all'anno precedente")
	public Map<String, List<VariazioneValoriParametriAmbienteDTO>> getVariazioneValoriParametriAmbiente() {
		return datiAmbienteService.getVariazioneValoriParametriAmbiente();
	}

}
