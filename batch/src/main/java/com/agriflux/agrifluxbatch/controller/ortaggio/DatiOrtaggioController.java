package com.agriflux.agrifluxbatch.controller.ortaggio;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;
import com.agriflux.agrifluxshared.service.ortaggio.DatiOrtaggioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/ortaggio")
@Tag(name = "Dati Ortaggio Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi agli Ortaggi")
public class DatiOrtaggioController implements DatiOrtaggioService {
	
	private final DatiOrtaggioService datiOrtaggioService;
	
	DatiOrtaggioController(DatiOrtaggioService datiOrtaggioService) {
		this.datiOrtaggioService = datiOrtaggioService;
	}
	
	@Override
	@GetMapping("/findAllOrtaggioSortById")
	@Operation(summary = "Recupera i dati relativi agli Ortaggi", description = "Restituisce una lista di DTO contenenti i dati relativi agli Ortaggi")
	public List<OrtaggioDTO> findAllOrtaggioSortById() {
		return datiOrtaggioService.findAllOrtaggioSortById();
	}

	@Override
	@GetMapping("/findAllOrtaggioRangeStagione")
	@Operation(summary = "Recupera i dati relativi agli Ortaggi con i mesi minimo e massimo per la semina", description = "Restituisce una lista di DTO contenenti i dati relativi agli Ortaggi con i mesi minimo e massimo per la semina "
			+ "per il singolo Ortaggio")
	public List<OrtaggioRangeStagioneSumDTO> findAllOrtaggioRangeStagione() {
		return datiOrtaggioService.findAllOrtaggioRangeStagione();
	}

}
