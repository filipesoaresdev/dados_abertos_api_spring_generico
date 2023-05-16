package br.ufpi.dadosabertosapi.utils;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.dadosabertosapi.database.local.model.CampoDicionario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CampoDicionarioDataSource implements JRDataSource{
	
	List<CampoDicionario> campos = new ArrayList<CampoDicionario>();
	
	private int counter = -1;
	
	public CampoDicionarioDataSource(List<CampoDicionario> campos) {
		this.campos = campos;
	}

	@Override
	public boolean next() throws JRException {
		if(counter < campos.size()-1) {
			counter++;
			return true;
		}
		return false;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if(jrField.getName().equals("nome")) {
			return campos.get(counter).getNome();
		}else if(jrField.getName().equals("descricao")) {
			return campos.get(counter).getDescricao();
		}
		return "";
	}
	
	
	
}

