package com.agriflux.agrifluxweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agriflux.agrifluxshared.dto.ambiente.VariazioneValoriParametriAmbienteDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaConsumoIdricoDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoRicaviSpeseDTO;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneParticellaColturaOrtaggioDTO;
import com.agriflux.agrifluxshared.dto.terreno.ParticellaColturaTerrenoDTO;
import com.agriflux.agrifluxweb.service.dashboard.DashboardServiceImpl;
import com.agriflux.agrifluxweb.service.dashboard.DataChartService;
import com.agriflux.agrifluxweb.service.job.JobLauncherClientServiceImpl;

import jakarta.servlet.http.HttpSession;

/**
 * Controller per la gestione, comunicazione e recupero dati tra pagine html
 */
@Controller
public class DashboardController implements DataChartService {
	
	private final DashboardServiceImpl dashboardServiceImpl;
	
	private final JobLauncherClientServiceImpl jobLauncherServiceImpl;
	
	public DashboardController(DashboardServiceImpl dashboardService, JobLauncherClientServiceImpl jobLauncherServiceImpl) {
		this.dashboardServiceImpl = dashboardService;
		this.jobLauncherServiceImpl = jobLauncherServiceImpl;
	}
	
	@GetMapping("/login")
	public String login() {
	    return "login";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/login";
	}
	
	@PostMapping("/dashboard")
	public String dashboard(@RequestParam() String username, HttpSession session, Model model) {
		session.setAttribute("username", username);
		session.setAttribute("azienda", dashboardServiceImpl.findAzienda());
	    return "dashboard";
	}
	
	@GetMapping("/homepage")
	public String homepage(Model model) {
	    return "fragments/homepage :: homepagePage";
	}
	
	@PostMapping("/startSimulationJob")
	public ResponseEntity<Void> startSimulationJob() {
		return jobLauncherServiceImpl.startSimulationJob();
	}
	
	@GetMapping("/isJobExecuted")
	@ResponseBody
	public Boolean isJobExecuted() {
		return jobLauncherServiceImpl.isJobExecuted();
	}
	
	//COLTURA
	
	@GetMapping("/coltura")
	public String getColtureDataModel(Model model) {
		List<ColturaDTO> listaColture = dashboardServiceImpl.findAllColturaSortById();
		model.addAttribute("colture", listaColture);

		return "fragments/coltura :: colturaPage";
	}
	
	@Override
	@GetMapping("/countOrtaggioColtura")
	@ResponseBody
	public Map<String, Long> countOrtaggioColtura() {
		return dashboardServiceImpl.countOrtaggioColtura();
	}

	@Override
	@GetMapping("/countFamigliaOrtaggioColtura")
	@ResponseBody
	public Map<String, Long> countFamigliaOrtaggioColtura() {
		return dashboardServiceImpl.countFamigliaOrtaggioColtura();
	}
	
	@Override
	@GetMapping("/findPrezziAndDateRaccoltoColtura")
	@ResponseBody
	public Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateRaccoltoColtura() {
		return dashboardServiceImpl.findPrezziAndDateRaccoltoColtura();
	}
	
	@Override
	@GetMapping("/findColturaConsumoIdrico")
	@ResponseBody
	public List<ColturaConsumoIdricoDTO> findColturaConsumoIdrico() {
		return dashboardServiceImpl.findColturaConsumoIdrico();
	}
	
	//PARTICELLA

	@GetMapping("/terreno")
	public String getParticelleDataModel(Model model) {
		model.addAttribute("particelle", dashboardServiceImpl.findAllParticellaSortById());
		model.addAttribute("rilevazioniTerreno", dashboardServiceImpl.findAllRilevazioneTerrenoSortById());
	    return "fragments/terreno :: terrenoPage";
	}
	
	@Override
	@GetMapping("/findParticellaJoinColturaTerreno")
	@ResponseBody
	public Map<Long, List<ParticellaColturaTerrenoDTO>> findParticellaJoinColturaTerreno() {
		return dashboardServiceImpl.findParticellaJoinColturaTerreno();
	}
	
	//PRODUZIONE

	@GetMapping("/produzione")
	public String getProduzioniDataModel(Model model) {
		model.addAttribute("produzioni", dashboardServiceImpl.findAllProduzioneSortById());
	    return "fragments/produzione :: produzionePage";
	}
	
	@Override
	@GetMapping("/findProduzioneQuantitaJoinColtura")
	@ResponseBody
	public Map<String, Map<String, ProduzioneColturaDTO>> findProduzioneQuantitaJoinColtura() {
		return dashboardServiceImpl.findProduzioneQuantitaJoinColtura();
	}
	
	@Override
	@GetMapping("/findProduzioneJoinColturaTempi")
	@ResponseBody
	public Map<String, List<ProduzioneColturaTempiDTO>> findProduzioneJoinColturaTempi() {
		return dashboardServiceImpl.findProduzioneJoinColturaTempi();
	}
	
	@Override
	@GetMapping("/findProduzioneParticellaColturaOrtaggio")
	@ResponseBody
	public Map<Long, ProduzioneParticellaColturaOrtaggioDTO> findProduzioneParticellaColturaOrtaggio() {
		return dashboardServiceImpl.findProduzioneParticellaColturaOrtaggio();
	}
	
	//AMBIENTE
	
	@GetMapping("/ambiente")
	public String getAmbientiDataModel(Model model) {
		model.addAttribute("ambienti", dashboardServiceImpl.findAllAmbienteSortById());
	    return "fragments/ambiente :: ambientePage";
	}
	
	@GetMapping("/getListaParametriAmbientali")
	@ResponseBody
	public List<String> getListaParametriAmbientali() {
		
		return dashboardServiceImpl.getListaParametriAmbiente();
	}
	
	@Override
	@GetMapping("/getVariazioneValoriParametriAmbiente")
	@ResponseBody
	public Map<String, List<VariazioneValoriParametriAmbienteDTO>> getVariazioneValoriParametriAmbiente() {
		return dashboardServiceImpl.getVariazioneValoriParametriAmbiente();
	}
	
	//FATTURATO
	
	@GetMapping("/fatturato")
	public String getFatturatoDataModel(Model model) {
		model.addAttribute("fatturati", dashboardServiceImpl.findAllFatturatoSortById());
	    return "fragments/fatturato :: fatturatoPage";
	}
	
	@Override
	@GetMapping("/findFatturatoRicaviSpese")
	@ResponseBody
	public Map<Long, List<FatturatoRicaviSpeseDTO>> findFatturatoRicaviSpese() {
		return dashboardServiceImpl.findFatturatoRicaviSpese();
	}
	
	//UTILS
	
	@GetMapping("/findListaProdottiColtivati")
	@ResponseBody
	public List<String> findListProdottiColture() {
		
		List<OrtaggioDTO> dtoList = dashboardServiceImpl.findAllOrtaggioSortById();
		
		List<String> response = new ArrayList<String>();
		
		for (OrtaggioDTO dto : dtoList) {
			response.add(dto.getNome());
		}
		
		return response;
	}

}
