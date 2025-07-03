package com.agriflux.agrifluxshared.dto.coltura;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ColturaConsumoIdricoDTO {
	
	private long idColtura;
	
	private LocalDate dataRaccolto;
	
	private String nome;
	
	private BigDecimal consumoIdrico;
	private BigDecimal consumoIdricoMedio;
	
	public ColturaConsumoIdricoDTO(long idColtura, LocalDate dataRaccolto, String nome, BigDecimal consumoIdrico,
			BigDecimal consumoIdricoMedio) {
		this.idColtura = idColtura;
		this.dataRaccolto = dataRaccolto;
		this.nome = nome;
		this.consumoIdrico = consumoIdrico;
		this.consumoIdricoMedio = consumoIdricoMedio;
	}

	public ColturaConsumoIdricoDTO() {}
	
	public long getIdColtura() {
		return idColtura;
	}

	public void setIdColtura(long idColtura) {
		this.idColtura = idColtura;
	}
	
	public void setDataRaccolto(LocalDate dataRaccolto) {
		this.dataRaccolto = dataRaccolto;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setConsumoIdrico(BigDecimal consumoIdrico) {
		this.consumoIdrico = consumoIdrico;
	}
	
	public void setConsumoIdricoMedio(BigDecimal consumoIdricoMedio) {
		this.consumoIdricoMedio = consumoIdricoMedio;
	}

	public LocalDate getDataRaccolto() {
		return dataRaccolto;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getConsumoIdrico() {
		return consumoIdrico;
	}

	public BigDecimal getConsumoIdricoMedio() {
		return consumoIdricoMedio;
	}

}