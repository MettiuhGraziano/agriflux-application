package com.agriflux.agrifluxbatch.repository.projection.coltura;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ColturaConsumoIdricoProjection {
	
	long getIdColtura();
	LocalDate getDataRaccolto();
	String getNome();
	BigDecimal getConsumoIdricoMedio();
	BigDecimal getConsumoIdrico();
	BigDecimal getEstensione();
	
}