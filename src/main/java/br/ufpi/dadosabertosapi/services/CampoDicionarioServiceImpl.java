package br.ufpi.dadosabertosapi.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import br.ufpi.dadosabertosapi.database.local.dao.CampoDicionarioRepository;
import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.CampoDicionarioException;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;
import br.ufpi.dadosabertosapi.uploadfile.UploadFileAPICKAN;
import br.ufpi.dadosabertosapi.utils.CampoDicionarioDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * 
 * @author FilipeSoares-STI
 */
@Service
public class CampoDicionarioServiceImpl implements CampoDicionarioService {
	
	@Autowired
	private CampoDicionarioRepository campoDicionarioRepository;
	
	@Autowired
	private DataSetService dataSetService;

	@Override
	public CampoDicionario save(CampoDicionario campo) {
		
		campo = campoDicionarioRepository.save(campo);
		return campo;
	}
	
	@Override
	public DataSetInfo gerarDicionarioDados(DataSetInfo dataSet) throws ClientProtocolException, IOException, JSONException, JRException, DataSetInfoSaveException, CampoDicionarioException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("titulo", dataSet.getDescricao());
		
		InputStream iufpi = this.getClass().getResourceAsStream("/images/ufpi.png");
		params.put("ufpiicon",ImageIO.read(iufpi));
		InputStream idados = this.getClass().getResourceAsStream("/images/dados.png");
		params.put("dadosicon",ImageIO.read(idados));
		
		InputStream is = this.getClass().getResourceAsStream("/reports/dicionariodados.jasper");
			//JasperReport report = JasperCompileManager.compileReport("src/main/resources/reports/dicionariodados.jrxml");

			JasperPrint print = JasperFillManager.fillReport(is, params,new CampoDicionarioDataSource(dataSet.getListCampoDicionarios()));
			byte[] filePdf = JasperExportManager.exportReportToPdf(print);
			
			dataSet = UploadFileAPICKAN.addDicionarioDados(filePdf, dataSet, "pdf");
		
		return dataSetService.salvar(dataSet);
	}
	
	
}
