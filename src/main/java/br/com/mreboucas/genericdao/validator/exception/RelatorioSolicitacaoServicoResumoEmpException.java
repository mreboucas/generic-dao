package  br.com.mreboucas.genericdao.validator.exception;

/**
 * 
 * @author Marcelo Reboucas[marceloreboucas10@gmail.com] 17/05/2013 - 14:07:26.
 *
 */
@SuppressWarnings("serial")
public class RelatorioSolicitacaoServicoResumoEmpException extends RuntimeException {
    
    public final static String MSG_ERRO_RELATORIO = "Erro ao Gerar o relat�rio de solicita��o";
    
	public RelatorioSolicitacaoServicoResumoEmpException(String message) {
		super(message);
	}

}
