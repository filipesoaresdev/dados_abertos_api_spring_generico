//package br.ufpi.dadosabertosapi.database.sistemascomum.daoimpl;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import br.ufpi.dadosabertosapi.database.sistemascomum.dao.SistemasComumDAO;
//
//@Repository
//public class SistemasComumDAOImpl implements SistemasComumDAO{
//
//	@PersistenceContext(unitName="sistemasComumContext")
//	private EntityManager entityManager;
//	
//	@Override
//	public EntityManager getEntity() {
//		// TODO Auto-generated method stub
//		return entityManager;
//	}
//	
//	@Override
//	public List<Long> getAllCpfsDocentes(){
//		
//		
//		String sql = "select p.cpf_cnpj as cpf \r\n" + 
//				" from comum.usuario as u \r\n" + 
//				" inner join comum.pessoa as p on (p.id_pessoa = u.id_pessoa) \r\n" + 
//				" inner join rh.servidor as s on (p.id_pessoa = s.id_pessoa) \r\n" + 
//				" inner join rh.classe_funcional as cf on (s.id_classe_funcional = cf.id_classe_funcional) \r\n" + 
//				" inner join rh.tipo_categoria as tcat on (cf.id_tipo_categoria = tcat.id_tipo_categoria) \r\n" + 
//				" where tcat.denominacao = 'Docente' \r\n" + 
//				" and u.inativo = false \r\n" + 
//				" and s.id_ativo in (1,9,7,8)";
//		
//
//		List<Long> listCpf = new ArrayList<Long>();
//		List<BigInteger> list = getEntity().createNativeQuery(sql).getResultList();
//		
//		for(BigInteger object : list) {
//			listCpf.add(object.longValue());
//		}
//		
//		return listCpf;
//		
//	}
//	@Override
//	public List<Long> getAllCpfsDiscentes(){
//		
//		
//		String sql = " ";
//		
//
//		List<Long> listCpf = new ArrayList<Long>();
//		List<BigInteger> list = getEntity().createNativeQuery(sql).getResultList();
//		
//		for(BigInteger object : list) {
//			listCpf.add(object.longValue());
//		}
//		
//		return listCpf;
//		
//	}
//	@Override
//	public List<Long> getAllCpfsTecnicos(){
//		
//		String sql = "select p.cpf_cnpj as cpf \r\n" + 
//				" from comum.usuario as u \r\n" + 
//				" inner join comum.pessoa as p on (p.id_pessoa = u.id_pessoa) \r\n" + 
//				" inner join rh.servidor as s on (p.id_pessoa = s.id_pessoa) \r\n" + 
//				" inner join rh.classe_funcional as cf on (s.id_classe_funcional = cf.id_classe_funcional) \r\n" + 
//				" inner join rh.tipo_categoria as tcat on (cf.id_tipo_categoria = tcat.id_tipo_categoria) \r\n" + 
//				" where tcat.id_tipo_categoria = 2 \r\n" + 
//				" and u.inativo = false \r\n" + 
//				" and s.id_ativo in (1,9,7,8)";
//		
//
//		List<Long> listCpf = new ArrayList<Long>();
//		List<BigInteger> list = getEntity().createNativeQuery(sql).getResultList();
//		
//		for(BigInteger object : list) {
//			listCpf.add(object.longValue());
//		}
//		
//		return listCpf;
//		
//	}
//}
