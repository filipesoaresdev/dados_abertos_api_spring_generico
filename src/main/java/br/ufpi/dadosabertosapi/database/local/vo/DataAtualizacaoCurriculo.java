package br.ufpi.dadosabertosapi.database.local.vo;

import java.time.LocalDateTime;

public class DataAtualizacaoCurriculo {
	
	private LocalDateTime dataAtualizacao;

	public DataAtualizacaoCurriculo(LocalDateTime dataAtualizacao) {
		super();
		this.dataAtualizacao = dataAtualizacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	

}
