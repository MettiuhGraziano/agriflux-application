package com.agriflux.agrifluxshared.service.fatturato;

import java.util.List;
import java.util.Map;

import com.agriflux.agrifluxshared.dto.fatturato.FatturatoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoRicaviSpeseDTO;

public interface DatiFatturatoService {
	
	/**
	 * Metodo che restituisce la lista di Fatturati ordinati in modo ASCENDENTE tramite l'ID
	 * 
	 * @return List di FatturatoDTO
	 */
	List<FatturatoDTO> findAllFatturatoSortById();
	
	/**
	 * Metodo che restituisce una mappa con in chiave l'id della Particella e come valore una lista
	 * di dto contenenti l'anno di riferimento, le spese e i ricavi
	 * 
	 * @return Map<Long, List<FatturatoRicaviSpeseDTO>>
	 */
	Map<Long, List<FatturatoRicaviSpeseDTO>> findFatturatoRicaviSpese();
	
}
