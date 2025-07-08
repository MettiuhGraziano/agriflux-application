package com.agriflux.agrifluxbatch.controller.terreno;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.terreno.TerrenoDTO;
import com.agriflux.agrifluxshared.service.terreno.DatiRilevazioneTerrenoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/terreno")
@Tag(name = "Dati Rilevazione Terreno Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi alle Rilevazioni Morfologiche")
public class DatiRilevazioneTerrenoController implements DatiRilevazioneTerrenoService {
	
	private final DatiRilevazioneTerrenoService datiRilevazioneTerrenoService;
	
	DatiRilevazioneTerrenoController(DatiRilevazioneTerrenoService datiRilevazioneTerrenoService) {
		this.datiRilevazioneTerrenoService = datiRilevazioneTerrenoService;
	}
	
	@Override
	@GetMapping("/findAllRilevazioneTerrenoSortById")
	@Operation(summary = "Recupera tutte le Rilevazioni effettuate sulle Particelle", description = "Restituisce una lista di Rilevazioni del Terreno")
	public List<TerrenoDTO> findAllRilevazioneTerrenoSortById() {
		return datiRilevazioneTerrenoService.findAllRilevazioneTerrenoSortById();
	}

}
