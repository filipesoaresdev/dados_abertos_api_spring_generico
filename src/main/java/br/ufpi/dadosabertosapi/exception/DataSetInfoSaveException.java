package br.ufpi.dadosabertosapi.exception;

public class DataSetInfoSaveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public DataSetInfoSaveException(String message) {
		super(message);
	}
    
    public DataSetInfoSaveException(String message, Throwable err) {
		super(message, err);
	}

}
