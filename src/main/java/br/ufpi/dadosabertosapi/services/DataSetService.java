package br.ufpi.dadosabertosapi.services;

import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.CampoDicionarioException;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;

public interface DataSetService {

	DataSetInfo salvar(DataSetInfo dataSetInfo) throws DataSetInfoSaveException;

	DataSetInfo addCampoDicionario(Long idDataSet, CampoDicionario campoDicionario) throws CampoDicionarioException;

	DataSetInfo removeCampoDicionario(Long id) throws CampoDicionarioException, DataSetInfoSaveException;

}
