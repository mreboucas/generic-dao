package br.com.mreboucas.genericdao.validator.validacao;

/**
 * @author Marcelo Rebou�as May 9, 2014 - 1:56:30 PM
 */
public class CommonValidaPermissao {

	public static final CommonValidaPermissao INSTANCE = new CommonValidaPermissao();
	public final String USARIO_SEM_PERMISSAO_ACESSO = "Usu�rio sem permiss�o";
	
	/*public <X> void validaPermissaoUsuarioProjeto(Map<X, X> params) {
		
		if (params != null) {
			
			Boolean isAdmnistradorSGP = Boolean.FALSE; 
			Boolean	isGerenteUnidadeGestoraSGP = Boolean.FALSE;
			Boolean isPatrocinadorProjeto = Boolean.FALSE;
			Boolean isResponsavelProjeto = Boolean.FALSE;
			Boolean possuiPermissao = Boolean.FALSE;
			String colCodMatricula = null;
			Long prjCodProjeto = null;
			
			if (Util.containsKeyAndKeyIsNotnull(params, "colCodMatricula")) {
				colCodMatricula = (String) params.get("colCodMatricula");
			}
			
			if (Util.containsKeyAndKeyIsNotnull(params, "prjCodProjeto")) {
				prjCodProjeto = Long.valueOf(params.get("prjCodProjeto").toString());
			}

			//Adm SGP
			if (Util.containsKeyAndKeyIsNotnull(params, PermissaoUsuarioProjetoEnum.ADMINISTRADOR_SGP) &&
					Util.containsKeyAndKeyIsNotnull(params, "session") && colCodMatricula != null) {
				
				HttpSession session = (HttpSession) params.get("session");
				isAdmnistradorSGP = PrsColaboradorBase.INSTANCE.usuarioIsAdministradorSGP(session);
				
			}
			
			//Gerente unidade gestora SGP
			if (Util.containsKeyAndKeyIsNotnull(params, PermissaoUsuarioProjetoEnum.GERENTE_UNIDADE_GESTORA_SGP) &&
					colCodMatricula != null) {
				
				isGerenteUnidadeGestoraSGP = PrsColaboradorBase.INSTANCE.usuarioIsGerenteUnidadeGestoraSGP(colCodMatricula);
				
			}

			//Patrcoinador projeto
			if (Util.containsKeyAndKeyIsNotnull(params, PermissaoUsuarioProjetoEnum.PATROCINADOR_PROJETO) &&
					colCodMatricula != null && prjCodProjeto != null) {
				
				isPatrocinadorProjeto = PrsProjetoUsuarioBase.INSTANCE.usuarioIsPatrocinadorProjeto(prjCodProjeto, colCodMatricula);
				
			}
			//Responsavel
			if (Util.containsKeyAndKeyIsNotnull(params, PermissaoUsuarioProjetoEnum.RESPONSAVEL_PROJETO) &&
					colCodMatricula != null && prjCodProjeto != null) {
				
				isResponsavelProjeto = PrsProjetoUsuarioBase.INSTANCE.usuarioIsResponsavelProjeto(prjCodProjeto, colCodMatricula);
				
			}

			if (isAdmnistradorSGP || isGerenteUnidadeGestoraSGP || isPatrocinadorProjeto || isResponsavelProjeto) {
				possuiPermissao = Boolean.TRUE;
			}
			
			if (!possuiPermissao)
				throw new UsuarioSemPermissaoException(USARIO_SEM_PERMISSAO_ACESSO);
			
		} else {
			throw new UsuarioSemPermissaoException(USARIO_SEM_PERMISSAO_ACESSO);
		}
	}*/
}