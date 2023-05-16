package br.ufpi.dadosabertosapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufpi.dadosabertosapi.doc.SwaggerConfig;
import br.ufpi.dadosabertosapi.services.DadosAbertosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("dadosabertos")
@Api(tags = {SwaggerConfig.DADOS_ABERTOS})
public class DadosAbertosResource {
	
	@Autowired
	private DadosAbertosService dadosAbertosService;
	
	@ApiOperation(value = "Gera lista com os dados que serão montados como uma tabela no frontend.",
				   notes = " O 1º elemento da lista são nomes das colunas. Recebe como PathVariable o nome do pacote ao qual se refere em dados.ufpi.br.")
	@RequestMapping(value="/{package}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<String[]>> getListDataSet(@PathVariable(value="package") String nome_package) throws Exception{

		List<String[]> listResult = dadosAbertosService.list(nome_package);
		ResponseEntity<List<String[]>> response = new ResponseEntity<List<String[]>>(listResult, HttpStatus.OK);
		return response;
		
	}

}
