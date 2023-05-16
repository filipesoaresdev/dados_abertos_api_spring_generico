package br.ufpi.dadosabertosapi.database.general;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.ufpi.dadosabertosapi.utils.QueryUtil;
/**
 * 
 * Interface that is implemented for each repository class
 * 
 * @version 1.0
 * @since 1.0
 * @author FilipeSoares-STI
 *
 */
public interface DAO {
	
	EntityManager getEntity();
	
	default List<Object[]> getResult(String path_query) {
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = (List<Object[]>) getEntity().createNativeQuery(path_query).getResultList();
		return resultList;
		
	}
	
	/**
	 * Method that return a list of results formated to be used to generate the CSV file.
	 * This method was made to be reused to any case
	 * @param pathQueryFile Path of the query located in src/main/resources/META-INF/queries
	 * These paths are organized in a class named ConstantsCKAN
	 * @return A list of String[]. Each String[] is a row representing each item of the result
	 * of the query
	 * 
	 * Essa consulta retorna uma lista onde o primeiro elemento é uma lista dos "Headers" e os outros elementos são
	 * listas dos resultados. A posição do header é a mesma referenciada nas outras listas, gerando assim uma lista de listas
	 * fácil de gerar uma tabela. Ou seja:
	 *             | Nome Header1       | Nome Header 2      | Nome Header 3    |    < - Primeiro elemento
	 *			   | Valor 1.1	        | Valor 1.2	         | Valor 1.3	    |    < - Segundo elemento (primeiro elemento do resultado em si)
	 *			   | Valor 2.1	        | Valor 2.2	         | Valor 2.3	    |    < - Segundo elemento (primeiro elemento do resultado em si)
	 *				(...)
	 *
	 */
	default List<String[]> listPureByQuery(String query){

		//String query = QueryBox.getInstace().getQuery(pathQueryFile);
		
		List<Object[]> listResult = getResult(query);
		List<String[]> listToReturn = new ArrayList<>();

		String[] headers = QueryUtil.getArrayAliases(query);
		
		listToReturn.add(headers);
		
		for(Object[] result : listResult){
			
			String[] rowAdd = new String[headers.length];
			for(int i=0; i < headers.length; i++){
				rowAdd[i] = (String)result[i];
			}
			
			listToReturn.add(rowAdd);
			
		}

		return listToReturn;
	}

}
