package br.ufpi.dadosabertosapi.uploadfile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.CampoDicionarioException;
import br.ufpi.dadosabertosapi.utils.ConstantesCKAN;
import br.ufpi.dadosabertosapi.vo.CKANProperties;


public class UploadFileAPICKAN {
	
	
	private static CKANProperties ckanProperties;
	
	public static void setCkanProperties(CKANProperties cp) {
		UploadFileAPICKAN.ckanProperties = cp;
	}
	
	
	public static void criarRecursoComArquivo(byte[] file, DataSetInfo dataSetInfo,String formato) throws ClientProtocolException, IOException{
		
		String key = ckanProperties.getCkankey();
		
		HttpPost post = new HttpPost(ckanProperties.getResourceCreateUrl());

		String boundary = "---------------"+UUID.randomUUID().toString();
		
		post.addHeader("X-CKAN-API-Key",key);
		post.addHeader("Content-Type",ContentType.MULTIPART_FORM_DATA.getMimeType()+";boundary="+boundary);
		
		LocalDate data = LocalDate.now();
		
		MultipartEntityBuilder  builder = MultipartEntityBuilder.create().setCharset(StandardCharsets.UTF_8).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.setBoundary(boundary);
		builder.addBinaryBody("upload", file ,ContentType.APPLICATION_OCTET_STREAM.withCharset("UTF-8"),criaNomeRecurso(dataSetInfo.getNome(), dataSetInfo.getIntervaloAtualizacao(), data).replace(" ", "")+"."+formato.toLowerCase() );
		builder.addTextBody("package_id",dataSetInfo.getNomePackage());
		builder.addTextBody("format",formato); 
		builder.addTextBody("name", criaNomeRecurso(dataSetInfo.getDescricao(), dataSetInfo.getIntervaloAtualizacao(), data),ContentType.TEXT_PLAIN.withCharset("UTF-8"));
		builder.addTextBody("created",data.format(DateTimeFormatter.ISO_DATE));
		
		
		HttpEntity multipart = builder.build();
		post.setEntity(multipart);
		CloseableHttpClient client = HttpClients.createDefault();
		client.execute(post);
		client.close();
	}
	
	public static DataSetInfo addDicionarioDados(byte[] file, DataSetInfo dataSetInfo,String formato) throws ClientProtocolException, IOException, JSONException, CampoDicionarioException{
		
		String key = ckanProperties.getCkankey();
		
		HttpPost post = null;

		String boundary = "---------------"+UUID.randomUUID().toString();
		
		MultipartEntityBuilder  builder = MultipartEntityBuilder.create().setCharset(StandardCharsets.UTF_8).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		if(dataSetInfo.getIdResourceDicionario() != null && !dataSetInfo.getIdResourceDicionario().isEmpty()) {
			
			builder.addTextBody("id",dataSetInfo.getIdResourceDicionario());
			post = new HttpPost(ckanProperties.getResourceUpdateUrl());
		}else {
			post = new HttpPost(ckanProperties.getResourceCreateUrl());
		}
		
		post.addHeader("X-CKAN-API-Key",key);
		post.addHeader("Content-Type",ContentType.MULTIPART_FORM_DATA.getMimeType()+";boundary="+boundary);
		
		LocalDate data = LocalDate.now();
		
		
		builder.setBoundary(boundary);
		builder.addBinaryBody("upload", file ,ContentType.APPLICATION_OCTET_STREAM.withCharset("UTF-8"),criaNomeRecurso(dataSetInfo.getNome(), dataSetInfo.getIntervaloAtualizacao(), data).replace(" ", "")+"."+formato.toLowerCase() );
		builder.addTextBody("package_id",dataSetInfo.getNomePackage());
		builder.addTextBody("format",formato); 
		builder.addTextBody("name", "Dicionário de Dados",ContentType.TEXT_PLAIN.withCharset("UTF-8"));
		builder.addTextBody("created",data.format(DateTimeFormatter.ISO_DATE));
		
		
		HttpEntity multipart = builder.build();
		post.setEntity(multipart);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(post);
		
		if(response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entityRespose = response.getEntity();
			if(entityRespose != null) {
				
				String r = EntityUtils.toString(entityRespose);
				JSONObject json = new JSONObject(r);
				dataSetInfo.setIdResourceDicionario(json.getJSONObject("result").getString("id"));
				
			}
		}else {
			response.close();
			client.close();
			throw new CampoDicionarioException("Erro ao cadastrar dicionário. Código : " + response.getStatusLine().getStatusCode()  );
		}
		
		response.close();
		client.close();
		return dataSetInfo;
	}
	private static String criaNomeRecurso(String nome, String intervaloAtualizacao, LocalDate data){
		
		switch (intervaloAtualizacao) {
		case ConstantesCKAN.EXECUTA_POR_ANO:
			return nome+" "+String.valueOf(data.getYear());
		case ConstantesCKAN.EXECUTA_POR_MES:
			return nome+" "+String.valueOf(data.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt","BR")))+"/"+String.valueOf(data.getYear());
		case ConstantesCKAN.EXECUTA_POR_SEMESTRE:
			return nome+" "+getSemestre(data) ;
		case ConstantesCKAN.EXECUTA_POR_BIMESTRE:
			return nome+" "+getBimestre(data);
		default:
			return "-";
		}
		
	}

	private static String getSemestre(LocalDate data){
		
		int semestre = (int)Math.ceil(data.getMonthValue()/6.0);
		return semestre+"º Semestre";
	}
	private static String getBimestre(LocalDate data){
		
		int semestre = (int)Math.ceil(data.getMonthValue()/2.0);
		return semestre+"º Bimestre";
	}
	
	
}
