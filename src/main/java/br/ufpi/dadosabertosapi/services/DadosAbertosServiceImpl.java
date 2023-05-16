package br.ufpi.dadosabertosapi.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpi.dadosabertosapi.database.local.dao.DataSetInfoRepository;
import br.ufpi.dadosabertosapi.database.local.model.DataBaseConnection;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.DataBaseNotFoundException;
import br.ufpi.dadosabertosapi.uploadfile.UploadFileAPICKAN;
import br.ufpi.dadosabertosapi.utils.CSVGenerator;
import br.ufpi.dadosabertosapi.utils.ConstantesCKAN;
import br.ufpi.dadosabertosapi.utils.QueryUtil;

/**
 * 
 * 
 * @author FilipeSoares-STI
 */
@Service
public class DadosAbertosServiceImpl implements DadosAbertosService {
	
	@Autowired
	private DataSetInfoRepository dataSetInfoRepository;

	
	@Override
	public List<String[]> list(String nome_package) throws Exception{
		List<DataSetInfo> dataSetList = dataSetInfoRepository.findByNomePackage(nome_package);
		if(dataSetList == null || dataSetList.isEmpty()) {
			throw new Exception("O Package - "+ nome_package + " não possui cadastro no banco.");
		}
		
		if(dataSetList.get(0).getDataBaseConnection() == null) {
			throw new Exception("O DataSet não possui uma conexão DB vinculada.");
		}
//		DAO dao = null;
//		switch (dataSetList.get(0).getTipoDataBase()) {
//			case ConstantesCKAN.BANCO_ADMINISTRATIVO:
//				dao = administrativoDAO;
//				break;
//			case ConstantesCKAN.BANCO_REDMINE:
//				dao = redmineDAO;
//				break;
//			case ConstantesCKAN.BANCO_SIGAA:
//				dao = sigaaDAO;
//				break;
//			case ConstantesCKAN.BANCO_SISTEMASCOMUM:
//				dao = sistemasComumDAO;
//				break;
//			default:
//				throw new DataBaseNotFoundException("Banco de consulta inválido");
//		}
		return listPureByQuery(dataSetList.get(0).getDataBaseConnection(), dataSetList.get(0).getQuery());
	}
	
	
	@Override
	public void uploadAllFiles() {
		
		
		List<DataSetInfo> list = dataSetInfoRepository.findAll();
		
		list.forEach(data -> {
			try {
				if(data.getAtualizaCkan() && data.isAvailableToUpload())
					uploadFile(data);
			} catch (DataBaseNotFoundException | IllegalArgumentException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	protected void uploadFile( DataSetInfo dataSetInfo ) throws  ClientProtocolException, IllegalArgumentException, IllegalAccessException, IOException, DataBaseNotFoundException {
//		DAO dao = null;
		
//		switch (dataSetInfo.getTipoDataBase()) {
//			case ConstantesCKAN.BANCO_ADMINISTRATIVO:
//				dao = administrativoDAO;
//				break;
//			case ConstantesCKAN.BANCO_REDMINE:
//				dao = redmineDAO;
//				break;
//			case ConstantesCKAN.BANCO_SIGAA:
//				dao = sigaaDAO;
//				break;
//			case ConstantesCKAN.BANCO_SISTEMASCOMUM:
//				dao = sistemasComumDAO;
//				break;
//			default:
//				throw new DataBaseNotFoundException("Banco de consulta inválido");
//		}
		
		byte[] file = CSVGenerator.gerarCSV(listPureByQuery(dataSetInfo.getDataBaseConnection(), dataSetInfo.getQuery()) );
		
		
		UploadFileAPICKAN.criarRecursoComArquivo(file,dataSetInfo,ConstantesCKAN.FORMAT_CSV);
		
		UploadFileAPICKAN.criarRecursoComArquivo(CSVGenerator.csvToExcel(file),dataSetInfo,ConstantesCKAN.FORMAT_XLS);
		
		dataSetInfo.setLastUpdate(LocalDate.now());
		
		dataSetInfoRepository.save(dataSetInfo);	
	}
	
	
	private List<Object[]> getResult(DataBaseConnection dbConnection, String path_query) {
		List<Object[]> resultList = new ArrayList<Object[]>();
		
		try (Connection sqlConnection  =  DriverManager.getConnection(dbConnection.getUrl(), dbConnection.getUserName(), dbConnection.getPassword());
		         PreparedStatement ps = sqlConnection.prepareStatement(path_query);
				
		         ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {
							
							List<Object> listObj = new ArrayList<>();
							for(int i=0; i < rs.getMetaData().getColumnCount(); i++) {
								listObj.add(rs.getObject(i));
							}
							
							resultList.add(listObj.toArray());
						}
		         // process the resultset here, all resources will be cleaned up

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	
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
	private List<String[]> listPureByQuery(DataBaseConnection dbConnection, String query){

		//String query = QueryBox.getInstace().getQuery(pathQueryFile);
		
		List<Object[]> listResult = getResult(dbConnection,query);
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
