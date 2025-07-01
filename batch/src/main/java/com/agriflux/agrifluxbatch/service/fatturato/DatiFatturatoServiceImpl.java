package com.agriflux.agrifluxbatch.service.fatturato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Fatturato;
import com.agriflux.agrifluxbatch.repository.DatiFatturatoRepository;
import com.agriflux.agrifluxbatch.repository.projection.fatturato.FatturatoRicaviSpeseProjection;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoDTO;
import com.agriflux.agrifluxshared.dto.fatturato.FatturatoRicaviSpeseDTO;
import com.agriflux.agrifluxshared.service.fatturato.DatiFatturatoService;

@Service
public class DatiFatturatoServiceImpl implements DatiFatturatoService {
	
	private final DatiFatturatoRepository datiFatturatoRepository;
	
	private final ModelMapper mapper;
	
	public DatiFatturatoServiceImpl(DatiFatturatoRepository datiFatturatoRepository, ModelMapper mapper) {
		this.datiFatturatoRepository = datiFatturatoRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<FatturatoDTO> findAllFatturatoSortById() {
		
		List<FatturatoDTO> dtoList = new ArrayList<FatturatoDTO>();
		
		List<Fatturato> listFatturato = datiFatturatoRepository.findAllByOrderByIdFatturatoAsc();
		
		if (null != listFatturato && !listFatturato.isEmpty()) {
			for (Fatturato fatturato : listFatturato) {
				FatturatoDTO dto = mapper.map(fatturato, FatturatoDTO.class);
				dtoList.add(dto);
			}
		}
		
		return dtoList;
	}

	@Override
	public Map<Long, List<FatturatoRicaviSpeseDTO>> findFatturatoRicaviSpese() {
		
		Map<Long, List<FatturatoRicaviSpeseDTO>> response = new HashMap<Long, List<FatturatoRicaviSpeseDTO>>();
		
		List<FatturatoRicaviSpeseProjection> projectionList = datiFatturatoRepository.findFatturatoRicaviSpeseProjection();
		
		for (FatturatoRicaviSpeseProjection projection : projectionList) {
			if (null != response.get(projection.getIdParticella())) {
				response.get(projection.getIdParticella()).add(new FatturatoRicaviSpeseDTO(
						projection.getRicaviVendita(), projection.getSpeseGenerali(), projection.getAnnoRiferimento()));
			} else {
				List<FatturatoRicaviSpeseDTO> dtoList = new ArrayList<FatturatoRicaviSpeseDTO>();
				dtoList.add(new FatturatoRicaviSpeseDTO(
						projection.getRicaviVendita(), projection.getSpeseGenerali(), projection.getAnnoRiferimento()));
				response.put(projection.getIdParticella(), dtoList);
			}
		}
		
		return response;
	}

}
