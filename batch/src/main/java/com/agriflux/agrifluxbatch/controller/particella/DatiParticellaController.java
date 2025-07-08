package com.agriflux.agrifluxbatch.controller.particella;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.dto.terreno.ParticellaColturaTerrenoDTO;
import com.agriflux.agrifluxshared.service.particella.DatiParticellaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("api/data/particella")
@Tag(name = "Dati Particella Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per i dati relativi alle Particelle")
public class DatiParticellaController implements DatiParticellaService {
	
	private final DatiParticellaService datiParticellaService;
	
	public DatiParticellaController(DatiParticellaService datiParticellaService) {
		this.datiParticellaService = datiParticellaService;
	}
	
	@Override
	@GetMapping("/findAllParticellaSortById")
	@Operation(summary = "Recupera tutte le Particelle", description = "Restituisce una lista di Particelle")
	public List<DatiParticellaDTO> findAllParticellaSortById() {
		return datiParticellaService.findAllParticellaSortById();
	}

	@Override
	@GetMapping("/findParticellaJoinColturaTerreno")
	@Operation(summary = "Recupera alcuni dati relativi a Coltura e Rilevazione Terreno raggruppandoli per Particella", description = "Restituisce una mappa che ha come chiave l'id della Particella"
			+ " e come valore una lista di DTO che contengono l'id della Coltura, il prodotto coltivato e la lista di date rilevazioni associate a quella Coltura su quella Particella")
	public Map<Long, List<ParticellaColturaTerrenoDTO>> findParticellaJoinColturaTerreno() {
		return datiParticellaService.findParticellaJoinColturaTerreno();
	}
	
}
