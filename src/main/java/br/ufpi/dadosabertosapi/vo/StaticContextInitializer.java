package br.ufpi.dadosabertosapi.vo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpi.dadosabertosapi.uploadfile.UploadFileAPICKAN;

@Component
public class StaticContextInitializer {

	@Autowired
	private CKANProperties ckanProperties;
	
	@PostConstruct
	public void init() {
		UploadFileAPICKAN.setCkanProperties(ckanProperties);
		
	}
	
}
