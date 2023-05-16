package br.ufpi.dadosabertosapi.services;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.apache.http.client.ClientProtocolException;
import org.springframework.boot.configurationprocessor.json.JSONException;

import br.ufpi.dadosabertosapi.database.administrativo.vo.CargoConfiancaVO;
import br.ufpi.dadosabertosapi.database.general.KeyVO;
import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.CampoDicionarioException;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;
import net.sf.jasperreports.engine.JRException;

public interface CampoDicionarioService {

	CampoDicionario save(CampoDicionario campo);

	DataSetInfo gerarDicionarioDados(DataSetInfo dataSet)
			throws ClientProtocolException, IOException, JSONException, JRException, DataSetInfoSaveException, CampoDicionarioException;

	

}
