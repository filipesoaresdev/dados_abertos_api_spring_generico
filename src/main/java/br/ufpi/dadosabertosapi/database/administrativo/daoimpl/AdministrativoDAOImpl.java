//package br.ufpi.dadosabertosapi.database.administrativo.daoimpl;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.stereotype.Repository;
//
//import br.ufpi.dadosabertosapi.database.administrativo.dao.AdministrativoDAO;
//import br.ufpi.dadosabertosapi.database.administrativo.vo.CurriculoPorCargoComissaoVO;
//
//@Repository
//public class AdministrativoDAOImpl implements AdministrativoDAO{
//
//	@PersistenceContext(unitName="administrativoContext")
//	private EntityManager entityManager;
//
//	@Override
//	public EntityManager getEntity() {
//		// TODO Auto-generated method stub
//		return entityManager;
//	}
//	
//	/**
//	 * Esse método retorna uma lista  dos cargos comissionados para gerar posteriormente o link de curriculo de cada um
//	 * é uma pesquisa de apoio para depois pesquisar em outro banco pelo link de cada um
//	 */
//	@Override 
//	public List<CurriculoPorCargoComissaoVO> getCurriculoLinkPorCargoComissao(){
//		
//		String sql = "	select pes.nome as Nome, pes.cpf_cnpj as CPF, ati.descricao as Descricao, un.nome as Unidade, nd.sigla as Nivel, \r\n" + 
//				"	un2.nome as UnidadeResponsavel, cast(un2.id_unidade as varchar) as ID_Unidade\r\n" + 
//				"	from rh.designacao as Designacao\r\n" + 
//				"	 join rh.servidor as s on (s.id_servidor = Designacao.id_servidor)\r\n" + 
//				"	 join comum.pessoa as pes on (s.id_pessoa = pes.id_pessoa)\r\n" + 
//				"	 join rh.atividade as ati on (ati.id_atividade = Designacao.id_atividade)\r\n" + 
//				"	 join comum.unidade as un on (Designacao.id_unidade = un.id_unidade)\r\n" + 
//				"	 join comum.unidade as un2 on ( CAST(SPLIT_PART(un.hierarquia, '.',3) AS INTEGER) = un2.id_unidade)\r\n" + 
//				"	 join funcional.nivel_designacao as nd on (nd.id_nivel_designacao = Designacao.id_nivel_designacao)\r\n" + 
//				"	where Designacao.inicio < CURRENT_DATE AND Designacao.fim > CURRENT_DATE\r\n" + 
//				"	AND Designacao.remuneracao = true ORDER BY UnidadeResponsavel, Nivel, Descricao , Nome asc ";
//		
//		List<Object[]> listResult = getResult(sql);
//		List<CurriculoPorCargoComissaoVO> listCurriculoPorCargo = new ArrayList<CurriculoPorCargoComissaoVO>();
//		
//		
//		for(Object[] result : listResult){
//			CurriculoPorCargoComissaoVO c = new CurriculoPorCargoComissaoVO();
//			c.setNome((String)result[0]);
//			BigInteger i  = (BigInteger)result[1];
//			
//			c.setCpf(String.format("%011d", i));
//			c.setFuncao((String)result[2]);
//			c.setUnidade((String)result[3]);
//			listCurriculoPorCargo.add(c);
//		}
//		
//		return listCurriculoPorCargo;
//	}
//	
//
//	
//}
