package br.ufpi.dadosabertosapi.database.local.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;

public interface DataSetInfoRepository extends JpaRepository<DataSetInfo, Long> {

	List<DataSetInfo> findByNomePackage(String nomePackage);
	
	List<DataSetInfo> findByNomePackageOrNomeOrDescricao(String nomePackage, String nome, String descricao);
	
}
