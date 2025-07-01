package com.agriflux.agrifluxshared.dto.fatturato;

import java.math.BigDecimal;

public class FatturatoRicaviSpeseDTO {
	
	private String annoRiferimento;
	
	private BigDecimal ricaviVendita;
	private BigDecimal speseGenerali;
	
	public FatturatoRicaviSpeseDTO(BigDecimal ricaviVendita, BigDecimal speseGenerali, String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
		this.ricaviVendita = ricaviVendita;
		this.speseGenerali = speseGenerali;
	}
	
	public FatturatoRicaviSpeseDTO() {}

	public BigDecimal getSpeseGenerali() {
		return speseGenerali;
	}

	public void setSpeseGenerali(BigDecimal speseGenerali) {
		this.speseGenerali = speseGenerali;
	}

	public BigDecimal getRicaviVendita() {
		return ricaviVendita;
	}

	public void setRicaviVendita(BigDecimal ricaviVendita) {
		this.ricaviVendita = ricaviVendita;
	}

	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	
}
