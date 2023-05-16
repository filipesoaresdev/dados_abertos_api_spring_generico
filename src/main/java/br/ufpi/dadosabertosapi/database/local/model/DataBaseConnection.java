package br.ufpi.dadosabertosapi.database.local.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpi.dadosabertosapi.utils.PasswordEncDec;

@Entity
public class DataBaseConnection {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "A URL da conexão é obrigatória.")
	private String url;
	@NotNull(message = "O nome do usuário é obrigatório.")
	private String userName;
	@NotNull(message = "A senha é obrigatória.")
	private String password;
	@NotNull(message = "O dialect da conexão é obrigatório.")
	private String dialect;
	@NotNull(message = "O nome da conexão é obrigatório.")
	private String nome;
	
	private String descricao;
	
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dataBaseConnection")
//	private List<DataSetInfo> listDataSetInfos = new ArrayList<DataSetInfo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPassword() {
		try {
			return PasswordEncDec.decryptPass(password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			LoggerFactory.getLogger(DataBaseConnection.class).error(e.getMessage(), e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setPassword(String password) {
		try {
			this.password = PasswordEncDec.encryptPass(password);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

//	public List<DataSetInfo> getListDataSetInfos() {
//		return listDataSetInfos;
//	}
//
//	public void setListDataSetInfos(List<DataSetInfo> listDataSetInfos) {
//		this.listDataSetInfos = listDataSetInfos;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataBaseConnection other = (DataBaseConnection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
