package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Particella;
import com.agriflux.agrifluxbatch.repository.projection.particella.ParticellaColturaRilevazioneProjection;
import com.agriflux.agrifluxbatch.repository.projection.particella.ParticellaIdAnnoProjection;

@Repository
public interface DatiParticellaRepository extends CrudRepository<Particella, Long>, PagingAndSortingRepository<Particella, Long> {
	
	@Query("SELECT p.idParticella AS idParticella, p.annoInstallazione AS annoInstallazione"
			+ " FROM Particella p")
	List<ParticellaIdAnnoProjection> findAllParticellaIdAnnoProjection();
	
	@Query("SELECT p.idParticella AS idParticella, c.idColtura AS idColtura, o.nome AS nomeProdotto, r.dataRilevazione AS dataRilevazioneTerreno "
			+ "FROM Particella p JOIN p.colture c JOIN c.ortaggio o JOIN p.rilevazioniTerreno r "
			+ "WHERE r.dataRilevazione BETWEEN c.dataSemina AND c.dataRaccolto")
	List<ParticellaColturaRilevazioneProjection> findAllParticellaColturaRilevazioneProjection();
	
}
