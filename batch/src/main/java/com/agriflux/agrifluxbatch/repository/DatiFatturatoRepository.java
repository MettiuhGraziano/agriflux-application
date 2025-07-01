package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Fatturato;
import com.agriflux.agrifluxbatch.repository.projection.fatturato.FatturatoRicaviSpeseProjection;

@Repository
public interface DatiFatturatoRepository extends CrudRepository<Fatturato, Long>, PagingAndSortingRepository<Fatturato, Long> {
	
	List<Fatturato> findAllByOrderByIdFatturatoAsc();
	
	@Query("SELECT f.annoRiferimento AS annoRiferimento, f.ricaviVendita AS ricaviVendita, "
			+ "f.speseGenerali AS speseGenerali, p.idParticella AS idParticella FROM Fatturato f "
			+ "JOIN f.particella p")
	List<FatturatoRicaviSpeseProjection> findFatturatoRicaviSpeseProjection();
}
