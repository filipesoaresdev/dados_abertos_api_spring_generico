package br.ufpi.dadosabertosapi.exception;

public class CampoDicionarioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public CampoDicionarioException(String message) {
		super(message);
	}
    
    public CampoDicionarioException(String message, Throwable err) {
		super(message, err);
	}

}
