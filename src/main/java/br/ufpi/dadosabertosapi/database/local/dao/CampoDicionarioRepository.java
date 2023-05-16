package br.ufpi.dadosabertosapi.database.local.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;

public interface CampoDicionarioRepository extends JpaRepository<CampoDicionario, Long> {
	
	
}
