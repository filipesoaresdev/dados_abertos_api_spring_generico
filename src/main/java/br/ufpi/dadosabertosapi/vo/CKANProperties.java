package br.ufpi.dadosabertosapi.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="ckan")
public class CKANProperties {


	private String ckankey;
	private String resourceCreateUrl;
	private String resourceUpdateUrl;
	
	public String getCkankey() {
		return ckankey;
	}
	public void setCkankey(String ckankey) {
		this.ckankey = ckankey;
	}
	public String getResourceCreateUrl() {
		return resourceCreateUrl;
	}
	public void setResourceCreateUrl(String resourceCreateUrl) {
		this.resourceCreateUrl = resourceCreateUrl;
	}
	public String getResourceUpdateUrl() {
		return resourceUpdateUrl;
	}
	public void setResourceUpdateUrl(String resourceUpdateUrl) {
		this.resourceUpdateUrl = resourceUpdateUrl;
	}
	
	
	
}
