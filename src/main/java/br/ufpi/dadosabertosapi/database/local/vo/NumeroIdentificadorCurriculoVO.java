package br.ufpi.dadosabertosapi.database.local.vo;

public class NumeroIdentificadorCurriculoVO {
	
	private String numeroIdentificacao;
		
	private String cpf;

	public NumeroIdentificadorCurriculoVO(String numeroIdentificacao) {
		super();
		this.numeroIdentificacao = numeroIdentificacao;
	}
	public NumeroIdentificadorCurriculoVO(String numeroIdentificacao,String cpf) {
		super();
		this.numeroIdentificacao = numeroIdentificacao;
		this.cpf = cpf;
	}
	public String getNumeroIdentificacao() {
		return numeroIdentificacao;
	}

	public void setNumeroIdentificacao(String numeroIdentificacao) {
		this.numeroIdentificacao = numeroIdentificacao;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
	

}
