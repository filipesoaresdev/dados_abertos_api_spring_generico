package br.ufpi.dadosabertosapi.exception;

public class DataBaseNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public DataBaseNotFoundException(String message) {
		super(message);
	}
    
    public DataBaseNotFoundException(String message, Throwable err) {
		super(message, err);
	}

}
