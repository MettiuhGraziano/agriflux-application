package com.agriflux.agrifluxweb.service.dashboard;

import java.util.List;
import java.util.Map;

import com.agriflux.agrifluxshared.dto.ambiente.VariazioneValoriParametriAmbienteDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaConsumoIdricoDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoRicaviSpeseDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneParticellaColturaOrtaggioDTO;
import com.agriflux.agrifluxshared.dto.terreno.ParticellaColturaTerrenoDTO;

/**
 * Interfaccia che mostra il contratto che deve rispettare il FE per la visualizzazione dei dati
 * nei grafici
 */
public interface DataChartService {
	
	/**
	 * Metodo che restituisce una mappa con in chiave la tipologia di prodotto e come valore il numero di
	 * volte nelle quali quel prodotto è stato coltivato
	 * 
	 * @return Map<String, Long>
	 */
	Map<String, Long> countOrtaggioColtura();
	
	/**
	 * Metodo che restituisce una mappa con in chiave la famiglia relativa alla tipologia di prodotto e come valore il numero di
	 * volte nelle quali quella famiglia è stata coltivata
	 * 
	 * @return Map<String, Long>
	 */
	Map<String, Long> countFamigliaOrtaggioColtura();
	
	/**
	 * Metodo che restituisce una mappa con in chiave la tipologia di prodotto e come valore un oggetto
	 * che ha le liste di prezzi e date raccolto relative a quel determinato prodotto
	 * 
	 * @return Map di ColturaListPrezzoDataRaccoltoDTO
	 */
	Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateRaccoltoColtura();
	
	/**
	 * Metodo che restituisce una lista di oggetti contenenti l'id della coltura, 
	 * il nome dell'ortaggio coltivato, la data raccolto, il consumo idrico per quella produzione ed 
	 * il consumo idrico medio per quell'ortaggio
	 * 
	 * @return List di ColturaConsumoIdricoDTO
	 */
	List<ColturaConsumoIdricoDTO> findColturaConsumoIdrico();
	
	/**
	 * Metodo che restituisce una mappa con in chiave l'id della particella e come valore una lista di oggetti
	 * contenenti l'id della coltura associata, il prodotto coltivato e la lista di date rilevazioni associate
	 * 
	 * @return Map di ColturaListPrezzoDataRaccoltoDTO
	 */
	Map<Long, List<ParticellaColturaTerrenoDTO>> findParticellaJoinColturaTerreno();
	
	/**
	 * Metodo che restituisce una mappa con chiave la tipologia di prodotto e come valore un'altra mappa con chiave
	 * l'anno di riferimento e come valore un oggetto contenente i dati sulla quantita' del raccolto ed il fatturato
	 * relativo a quell'anno
	 * 
	 * @return Map -> key : Prodotto | value : Map -> key : Anno Riferimento | value : {@code ProduzioneColturaDTO}
	 */
	Map<String, Map<String, ProduzioneColturaDTO>> findProduzioneQuantitaJoinColtura();
	
	/**
	 * Metodo che restituisce una mappa con in chiave l'anno di semina e come valore una lista di oggetti
	 * contenenti il prodotto coltivato e la lista delle medie dei tempi dalla semina alla raccolta per il 
	 * singolo prodotto
	 * 
	 * @return Map di ProduzioneColturaTempiDTO
	 */
	Map<String, List<ProduzioneColturaTempiDTO>> findProduzioneJoinColturaTempi();
	
	/**
	 * Metodo che restituisce una mappa con in chiave l'id della produzione e come valore un oggetto
	 * che contiene i dati relativi alla morfologia del terreno e l'id della coltura con la data di semina 
	 * 
	 * @return Map di ProduzioneMorfologiaColturaDTO
	 */
	Map<Long, ProduzioneParticellaColturaOrtaggioDTO> findProduzioneParticellaColturaOrtaggio();
	
	/**
	 * Metodo che restituisce una mappa con in chiave i nomi dei parametri calcolati sull'Ambiente e come valore una lista di oggetti
	 * contenente il valore del campo, la data di rilevazione e la percentuale di variazione rispetto l'anno precedente
	 * 
	 * @return Map<String, List<VariazioneValoriParametriAmbienteDTO>>
	 */
	Map<String, List<VariazioneValoriParametriAmbienteDTO>> getVariazioneValoriParametriAmbiente();
	
	/**
	 * Metodo che restituisce una mappa con in chiave l'id della Particella e come valore una lista
	 * di dto contenenti l'anno di riferimento, le spese e i ricavi
	 * 
	 * @return Map<Long, List<FatturatoRicaviSpeseDTO>>
	 */
	Map<Long, List<FatturatoRicaviSpeseDTO>> findFatturatoRicaviSpese();
	
}
