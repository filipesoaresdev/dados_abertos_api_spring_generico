package br.ufpi.dadosabertosapi.database.local.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufpi.dadosabertosapi.database.local.model.DataBaseConnection;

public interface DataBaseConnectionRepository extends JpaRepository<DataBaseConnection, Long> {

	
}
