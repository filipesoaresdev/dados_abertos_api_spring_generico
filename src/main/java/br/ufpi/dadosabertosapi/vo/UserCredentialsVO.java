package br.ufpi.dadosabertosapi.vo;

import org.springframework.security.core.userdetails.User;

import br.ufpi.dadosabertosapi.database.local.model.Usuario;

public class UserCredentialsVO extends User {

	public UserCredentialsVO(Usuario usuario) {
		
		super(usuario.getLogin(), usuario.getPassword(), usuario.getGrantedAuthoritiesList());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private String login;
//	private String password;
//	
//	public String getLogin() {
//		return login;
//	}
//	public void setLogin(String login) {
//		this.login = login;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	
	
	

}
