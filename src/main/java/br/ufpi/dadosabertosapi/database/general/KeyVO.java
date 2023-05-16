package br.ufpi.dadosabertosapi.database.general;

public class KeyVO implements Comparable<KeyVO>{
	
	private String key;
	private String ordenador;
	
	

	
	
	public KeyVO(String key, String ordenador) {
		super();
		this.key = key;
		this.ordenador = ordenador;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOrdenador() {
		return ordenador;
	}
	public void setOrdenador(String ordenador) {
		this.ordenador = ordenador;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		KeyVO other = (KeyVO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	@Override
	public int compareTo(KeyVO o) {
		// TODO Auto-generated method stub
		return this.getKey().compareTo(o.getKey());
	}
	
	@Override
	public String toString() {
		return getOrdenador();
	}
	
	

}
