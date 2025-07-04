package com.agriflux.agrifluxshared.dto.coltura;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ColturaDTO {
	
	private long idColtura;
	private BigDecimal prezzoKg;
	private LocalDate dataSemina;
	private LocalDate dataRaccolto;
	private String nomeOrtaggio;
	
	public long getIdColtura() {
		return idColtura;
	}
	
	public void setIdColtura(long idColtura) {
		this.idColtura = idColtura;
	}
	
	public BigDecimal getPrezzoKg() {
		return prezzoKg;
	}
	
	public void setPrezzoKg(BigDecimal prezzoKg) {
		this.prezzoKg = prezzoKg;
	}
	
	public LocalDate getDataSemina() {
		return dataSemina;
	}
	
	public void setDataSemina(LocalDate dataSemina) {
		this.dataSemina = dataSemina;
	}
	
	public LocalDate getDataRaccolto() {
		return dataRaccolto;
	}
	
	public void setDataRaccolto(LocalDate dataRaccolto) {
		this.dataRaccolto = dataRaccolto;
	}

	public String getNomeOrtaggio() {
		return nomeOrtaggio;
	}

	public void setNomeOrtaggio(String nomeOrtaggio) {
		this.nomeOrtaggio = nomeOrtaggio;
	}
}
