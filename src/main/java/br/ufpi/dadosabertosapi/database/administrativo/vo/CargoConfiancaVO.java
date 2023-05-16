package br.ufpi.dadosabertosapi.database.administrativo.vo;

import br.ufpi.dadosabertosapi.database.general.NameCKAN;

/*select pes.nome, ati.descricao as descricao, un.nome as Unidade, nd.sigla as Nivel, nd.id_nivel_designacao as idNivel
from rh.designacao as d
	join rh.servidor as s on (s.id_servidor = d.id_servidor)
	join comum.pessoa as pes on (s.id_pessoa = pes.id_pessoa)
	join rh.atividade as ati on (ati.id_atividade = d.id_atividade)
	join comum.unidade as un on (d.id_unidade = un.id_unidade)
	join funcional.nivel_designacao as nd on (nd.id_nivel_designacao = d.id_nivel_designacao)
where d.inicio < CURRENT_DATE AND d.fim > CURRENT_DATE
AND d.remuneracao = true ORDER BY Unidade, idNivel, descricao,nome asc;*/


public class CargoConfiancaVO implements Comparable<CargoConfiancaVO>{
	
	@NameCKAN(name="Nome")
	private String nome;
	
	@NameCKAN(name="Atividade")
	private String descricaoAtividade;
	
	@NameCKAN(name="Unidade")
	private String unidade;
	
	@NameCKAN(name="Setor")
	private String setorSuperior;
	
	@NameCKAN(name="Nível")
	private String siglaNivelDesignacao;
	
	//utilizado para ordenação
	private String idNivel;

	
	public CargoConfiancaVO(String nome, String descricaoAtividade, String unidade, String siglaNivelDesignacao,
			String idNivel, String setorSuperior) {
		
		this.nome = nome;
		this.descricaoAtividade = descricaoAtividade;
		this.unidade = unidade;
		this.siglaNivelDesignacao = siglaNivelDesignacao;
		this.idNivel = idNivel;
		this.setorSuperior = setorSuperior;
	}
	
	public String[] toArray(){
		
		return new String[]{getNome(),getDescricaoAtividade(),getUnidade(),getSiglaNivelDesignacao()};
		
	}
	

	public String getSetorSuperior() {
		return setorSuperior;
	}


	public void setSetorSuperior(String setorSuperior) {
		this.setorSuperior = setorSuperior;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricaoAtividade() {
		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getSiglaNivelDesignacao() {
		return siglaNivelDesignacao;
	}

	public void setSiglaNivelDesignacao(String siglaNivelDesignacao) {
		this.siglaNivelDesignacao = siglaNivelDesignacao;
	}

	public String getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}


	@Override
	public int compareTo(CargoConfiancaVO o) {
		// TODO Auto-generated method stub
		return this.getIdNivel().toString().compareTo(o.getIdNivel().toString());
	}
	
	

}

