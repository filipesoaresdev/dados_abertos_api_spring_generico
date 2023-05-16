package br.ufpi.dadosabertosapi.database.administrativo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CurriculoPorCargoComissaoVO {
	
	@JsonIgnore
	private String cpf;
	
	private String funcao;
	
	private String linkCurriculo;
	
	private String unidade;
	
	private String nome;
	
	

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getLinkCurriculo() {
		return linkCurriculo;
	}

	public void setLinkCurriculo(String linkCurriculo) {
		this.linkCurriculo = linkCurriculo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurriculoPorCargoComissaoVO other = (CurriculoPorCargoComissaoVO) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
	
	

}
