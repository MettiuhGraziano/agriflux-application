package com.agriflux.agrifluxbatch.repository.projection.fatturato;

import java.math.BigDecimal;

public interface FatturatoRicaviSpeseProjection {
	
	String getAnnoRiferimento();
	BigDecimal getRicaviVendita();
	BigDecimal getSpeseGenerali();
	long getIdParticella();
	
}