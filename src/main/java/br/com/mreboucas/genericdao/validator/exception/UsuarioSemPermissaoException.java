package  br.com.mreboucas.genericdao.validator.exception;

/**
 * @author Marcelo Rebou�as May 9, 2014 - 1:50:31 PM
 */
@SuppressWarnings("serial")
public class UsuarioSemPermissaoException extends RuntimeException {
	
	public UsuarioSemPermissaoException(String mensagem) {
		super(mensagem);
	}
}