package com.agriflux.agrifluxbatch.controller.fatturato;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.fatturato.FatturatoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoRicaviSpeseDTO;
import com.agriflux.agrifluxshared.service.fatturato.DatiFatturatoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/fatturato")
@Tag(name = "Dati Fatturato Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi al Fatturato")
public class DatiFatturatoController implements DatiFatturatoService {
	
	private final DatiFatturatoService datiFatturatoService;
	
	public DatiFatturatoController(DatiFatturatoService datiFatturatoService) {
		this.datiFatturatoService = datiFatturatoService;
	}
	
	@Override
	@GetMapping("/findAllFatturatoSortById")
	@Operation(summary = "Recupera i dati relativi al Fatturato", description = "Restituisce una lista di DTO contenenti i dati relativi al Fatturato")
	public List<FatturatoDTO> findAllFatturatoSortById() {
		return datiFatturatoService.findAllFatturatoSortById();
	}

	@Override
	@GetMapping("/findFatturatoRicaviSpese")
	@Operation(summary = "Mostra una mappa dei dati relativi al Fatturato di ogni Particella anno per anno", description = "Restituisce una mappa con in chiave l'id della Particella "
			+ "e come valore una lista di oggetti contenenti l'anno di riferimento del fatturato, i ricavi e i costi di spesa della particella")
	public Map<Long, List<FatturatoRicaviSpeseDTO>> findFatturatoRicaviSpese() {
		return datiFatturatoService.findFatturatoRicaviSpese();
	}

}
