package br.ufpi.dadosabertosapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufpi.dadosabertosapi.database.local.dao.DataBaseConnectionRepository;
import br.ufpi.dadosabertosapi.database.local.model.DataBaseConnection;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("databaseconnection")
public class DataBaseConnectionResource {
	
	@Autowired
	private DataBaseConnectionRepository dataBaseConnectionRepository;

	@ApiOperation(value = "Salva as configurações de uma conexão de banco de dados. Também utilizado para edição.")
	@RequestMapping(value="/salva",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataBaseConnection> salvarDataBaseConnection(@RequestBody DataBaseConnection dataBaseConnection, HttpServletResponse response){

		DataBaseConnection result;
		
		result = (DataBaseConnection) dataBaseConnectionRepository.save(dataBaseConnection);
		
		ResponseEntity<DataBaseConnection> dataSetResponse = new ResponseEntity<DataBaseConnection>(result, HttpStatus.OK);
		return dataSetResponse;
		
	}
	
	@ApiOperation(value = "Essa operação lista as conexões de banco de dados cadastradas com todos os seus dados.")
	@RequestMapping(value="/list",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<DataBaseConnection>> getListDataBases() throws Exception{

		List<DataBaseConnection> listResult = dataBaseConnectionRepository.findAll();
		ResponseEntity<List<DataBaseConnection>> response = new ResponseEntity<List<DataBaseConnection>>(listResult, HttpStatus.OK);
		return response;
		
	}
}
