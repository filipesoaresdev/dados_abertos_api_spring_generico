package br.ufpi.dadosabertosapi.database.local.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufpi.dadosabertosapi.database.local.dao.LocalDAO;

@Repository
public class LocalDAOImpl implements LocalDAO {

	@PersistenceContext(unitName="localContext")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntity() {
		// TODO Auto-generated method stub
		return entityManager;
	}
	
}
