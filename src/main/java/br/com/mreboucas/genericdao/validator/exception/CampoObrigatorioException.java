package  br.com.mreboucas.genericdao.validator.exception;

/**
 * @author: Marcelo Rebou�as - Apr 30, 2013 - 10:26:06 AM
 */
@SuppressWarnings("serial")
public class CampoObrigatorioException extends Exception {
    
	public CampoObrigatorioException() {
		super();
	}
	
	public CampoObrigatorioException(String message) {
		super(message);
	}
}