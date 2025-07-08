package com.agriflux.agrifluxbatch.controller.azienda;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.azienda.AziendaDTO;
import com.agriflux.agrifluxshared.service.azienda.DatiAziendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/azienda")
@Tag(name = "Dati Azienda Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi all'Azienda")
public class DatiAziendaController implements DatiAziendaService {

	private final DatiAziendaService datiAziendaService;
	
	DatiAziendaController(DatiAziendaService datiAziendaService) {
		this.datiAziendaService = datiAziendaService;
	}
	
	@Override
	@GetMapping("/findAzienda")
	@Operation(summary = "Recupera i dati Aziendali", description = "Restituisce un DTO contenente i dati Aziendali")
	public AziendaDTO findAzienda() {
		return datiAziendaService.findAzienda();
	}

}
