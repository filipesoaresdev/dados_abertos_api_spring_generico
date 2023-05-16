package br.ufpi.dadosabertosapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpi.dadosabertosapi.database.local.dao.CampoDicionarioRepository;
import br.ufpi.dadosabertosapi.database.local.dao.DataSetInfoRepository;
import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.CampoDicionarioException;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;

@Service
public class DataSetServiceImpl implements DataSetService {
	
	@Autowired
	private DataSetInfoRepository dataSetInfoRepository;
	@Autowired
	private CampoDicionarioRepository campoDicionarioRepository;

	@Override
	public DataSetInfo salvar(DataSetInfo dataSetInfo) throws DataSetInfoSaveException {
		
		valida(dataSetInfo);
		
		dataSetInfo = dataSetInfoRepository.save(dataSetInfo);
		
		return dataSetInfo;
		
	}

	private void valida(DataSetInfo dataSetInfo) throws DataSetInfoSaveException {

		StringBuilder message = new StringBuilder();
		
		if(dataSetInfo==null) {
			throw new DataSetInfoSaveException("Nenhum DataSet está sendo enviado.");
		}
		
		if(dataSetInfo.getDescricao()==null || dataSetInfo.getDescricao().isEmpty()) {
			message.append("Informe uma descrição; \n");
		}
		
		if(dataSetInfo.getAtualizaCkan()) {
			if(dataSetInfo.getIntervaloAtualizacao()==null || dataSetInfo.getIntervaloAtualizacao().isEmpty()) {
				message.append("Informe um intervalor de atualização; \n");
			}
			
			if(dataSetInfo.getNome()==null || dataSetInfo.getNome().isEmpty()) {
				message.append("Informe um intervalor de atualização; \n");
			}
			
			if(dataSetInfo.getNomePackage()==null || dataSetInfo.getNomePackage().isEmpty()) {
				message.append("Informe o nome do package (dataset no CKAN); \n");
			}
			
			if(dataSetInfo.getQuery()==null || dataSetInfo.getQuery().isEmpty()) {
				message.append("Informe a query de consulta; \n");
			}
			
			if(dataSetInfo.getId() == null) {
				List<DataSetInfo> dataValidate = dataSetInfoRepository
												.findByNomePackageOrNomeOrDescricao(
														dataSetInfo.getNomePackage(), 
														dataSetInfo.getNome(), 
														dataSetInfo.getDescricao());
				if( dataValidate != null ) {
					for(DataSetInfo dsi : dataValidate) {
					
						if(dsi.getNome().equals(dataSetInfo.getNome())) {
							message.append("Esse nome de arquivo já existe. Informe outro.; \n");
						}
						if(dsi.getNomePackage().equals(dataSetInfo.getNomePackage())) {
							message.append("Esse nome do Package/DataSet já existe. Informe outro.; \n");
						}
						if(dsi.getDescricao().equals(dataSetInfo.getDescricao())) {
							message.append("Essa descrição já existe. Informe outro.; \n");
						}
				
					}
				}
			}
			
		}
		
		if(dataSetInfo.getDataBaseConnection()==null || dataSetInfo.getDataBaseConnection().getId() == null) {
			message.append("Informe a base de dados; \n");
		}
		
		if(message.length() > 0) {
			throw new DataSetInfoSaveException(message.toString());
		}
		
	}
	
	@Override
	public DataSetInfo addCampoDicionario(Long idDataSet, CampoDicionario campoDicionario) throws CampoDicionarioException {
		
	    DataSetInfo dataSetInfo = dataSetInfoRepository.findById(idDataSet).orElseThrow(() -> new CampoDicionarioException("Item não encontrado. Recarregue a página e tente novamente."));
		campoDicionario.setDataSetInfo(dataSetInfo);
//	    dataSetInfo.addCampoDicionario(campoDicionario);
//		dataSetInfo = dataSetInfoRepository.save(dataSetInfo);
		campoDicionario = campoDicionarioRepository.save(campoDicionario);
		dataSetInfo = dataSetInfoRepository.findById(idDataSet).orElseThrow(() -> new CampoDicionarioException("Item não encontrado. Recarregue a página e tente novamente."));
		
		return dataSetInfo;
	}

	@Override
	public DataSetInfo removeCampoDicionario( Long id) throws CampoDicionarioException, DataSetInfoSaveException {
		CampoDicionario campo = campoDicionarioRepository.findById(id).orElseThrow(() -> new CampoDicionarioException("Item não encontrado. Recarregue a página e tente novamente."));
		Long idDataSet = campo.getDataSetInfo().getId();
		campoDicionarioRepository.deleteById(id);
		
		return dataSetInfoRepository.findById(idDataSet).orElseThrow(() -> new DataSetInfoSaveException("DataSet não encontrado. Recarregue a página e tente novamente."));
			
	}
	
	
	
}
