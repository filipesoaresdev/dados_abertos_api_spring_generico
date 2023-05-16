package br.ufpi.dadosabertosapi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ufpi.dadosabertosapi.database.local.dao.DataSetInfoRepository;
import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.CampoDicionarioException;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;
import br.ufpi.dadosabertosapi.services.CampoDicionarioService;
import br.ufpi.dadosabertosapi.services.DataSetService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("dataset")
public class DadosAbertosAdminResource {
	
	@Autowired
	private DataSetInfoRepository dataSetInfoRepository;
	@Autowired
	private CampoDicionarioService campoDicionarioService;
	
	@Autowired
	private DataSetService dataSetService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<DataSetInfo>> getCargosComissionados() throws Exception{

		List<DataSetInfo> listResult = dataSetInfoRepository.findAll();
		ResponseEntity<List<DataSetInfo>> response = new ResponseEntity<List<DataSetInfo>>(listResult, HttpStatus.OK);
		return response;
		
	} 
	
	@RequestMapping(value="/salva",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataSetInfo> salvarDataSetInfo(@RequestBody DataSetInfo dataSetInfo, HttpServletResponse response){

		DataSetInfo result;
		try {
			result = dataSetService.salvar(dataSetInfo);
		} catch (DataSetInfoSaveException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		ResponseEntity<DataSetInfo> dataSetResponse = new ResponseEntity<DataSetInfo>(result, HttpStatus.OK);
		return dataSetResponse;
		
	}
	
	@RequestMapping(value="/{id}/addCampo",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataSetInfo> addCampo(@RequestBody CampoDicionario campoDic, @PathVariable(value="id") Long idDataSet, HttpServletResponse response){

		DataSetInfo result;
		try {
			result = dataSetService.addCampoDicionario(idDataSet,campoDic);
		} catch (ConstraintViolationException e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			
		}catch ( CampoDicionarioException e) {
			
				// TODO Auto-generated catch block
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			
		}
	
			
		ResponseEntity<DataSetInfo> dataSetResponse = new ResponseEntity<DataSetInfo>(result, HttpStatus.OK);
		return dataSetResponse;
		
	}

	@RequestMapping(value="/removeCampo/{id}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataSetInfo> removeCampo(@PathVariable(value="id") Long id, HttpServletResponse response){

		DataSetInfo result;
		try {
			result = dataSetService.removeCampoDicionario(id);
		} catch ( CampoDicionarioException | DataSetInfoSaveException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		ResponseEntity<DataSetInfo> dataSetResponse = new ResponseEntity<DataSetInfo>(result, HttpStatus.OK);
		return dataSetResponse;
		
	}
	

	@RequestMapping(value="/{id}/saveCampo",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CampoDicionario> saveCampo(@RequestBody CampoDicionario campoDic,@PathVariable(value="id") Long id, HttpServletResponse response){

		CampoDicionario campo;
		try {
			DataSetInfo dataSetInfo = dataSetInfoRepository.findById(id).orElseThrow(() ->  new DataSetInfoSaveException("DataSet não encontrado"));
			campoDic.setDataSetInfo(dataSetInfo);
		} catch (DataSetInfoSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		campo = campoDicionarioService.save(campoDic);
		
		ResponseEntity<CampoDicionario> campoResponse = new ResponseEntity<CampoDicionario>(campo, HttpStatus.OK);
		return campoResponse;
		
	}
	
	@RequestMapping(value="/{id}/gerarDicionario",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataSetInfo> gerarDicionario(@PathVariable(value="id") Long id, HttpServletResponse response){
		DataSetInfo dataSetInfo;
		try {
			dataSetInfo = dataSetInfoRepository.findById(id).orElseThrow(() ->  new DataSetInfoSaveException("DataSet não encontrado"));
			dataSetInfo = campoDicionarioService.gerarDicionarioDados(dataSetInfo);
		} catch (IOException | JSONException | JRException | DataSetInfoSaveException | CampoDicionarioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		ResponseEntity<DataSetInfo> responseOk = new ResponseEntity<DataSetInfo>(dataSetInfo, HttpStatus.OK);
		return responseOk;
		
	}
}
