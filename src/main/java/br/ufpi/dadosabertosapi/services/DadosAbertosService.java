package br.ufpi.dadosabertosapi.services;

import java.util.List;
import java.util.SortedMap;

import br.ufpi.dadosabertosapi.database.administrativo.vo.CargoConfiancaVO;
import br.ufpi.dadosabertosapi.database.general.KeyVO;

public interface DadosAbertosService {

//	SortedMap<KeyVO, List<CargoConfiancaVO>> listCargoDeConfianca();

//	void uploadFileCargoConfiancaCKAN(String intervaloAtualizacao) throws ClientProtocolException, IOException;

	//List<String[]> listProgramaStrictoSenso();

	//void uploadFileProgramaStrictoSensoCKAN(String intervaloAtualizacao)
	//		throws ClientProtocolException, IOException, IllegalArgumentException, IllegalAccessException;

	//void uploadFileCursosGraduacaoCKAN(String intervaloAtualizacao)
	//		throws ClientProtocolException, IOException, IllegalArgumentException, IllegalAccessException;

	List<String[]> list(String path) throws Exception;

//	void uploadFile(String path)
//			throws ClientProtocolException, IllegalArgumentException, IllegalAccessException, IOException;

	void uploadAllFiles();

}
