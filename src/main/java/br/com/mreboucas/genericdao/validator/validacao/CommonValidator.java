package br.com.mreboucas.genericdao.validator.validacao;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import br.com.mreboucas.genericdao.util.Util;
import br.com.mreboucas.genericdao.validator.exception.CampoObrigatorioException;

/**
 * @author: Marcelo Rebou�as - Apr 30, 2013 - 10:13:10 AM
 * @description: respons�vel por realizar valida��es de campos obrigat�rios. Se comporta com todos os beans 
 * 				 que possuam a anota��o notnull (messagem="campo XYZ obrigat�rio"). 
 * @api: java.lang.reflect (Reflex�o)
 */
public class CommonValidator {
	
	/**
	 * @author: Marcelo Rebou�as - Apr 23, 2013 - 2:36:42 PM
	 * @description: verifica campos obrigat�rios de um bean mapeados com notnull
	 * @return: void
	 */
	public static <M extends Object> void validaCamposObrigatorios(M obj) throws CampoObrigatorioException {
		
		StringBuilder msgCampoObrigatorio = new StringBuilder();
		String msg = null;
		
		PropertyDescriptor[] propertyList = null;
		
		try {
			
			//Retorna a lista de atributos do objeto e seus m�todos de acesso
			propertyList = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
			
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		
		//Percorre os atributos
		for (PropertyDescriptor property : propertyList) {
			
			//Convert PropertyDescriptor to Field
			Field field = Util.getField(property.getName(), obj.getClass());
			
			if (field != null) {
			
				//Verifica se o campo est� mapeado com a anota��o notnull
				boolean existsAnnotationNotNull = field.isAnnotationPresent(NotNull.class);
				
					if (existsAnnotationNotNull) {
						
						//retorna mensagem de obrigatoriedade
						msg = verificaSeAtributoIsNull(obj, field, property);
						
						//Concatena a(s) mensagem(ns)
						if (msg != null) {
							msgCampoObrigatorio.append(msg + "\n");
						}
					}
			}
		}		
		if (msgCampoObrigatorio.length() > 0) {
			
			throw new CampoObrigatorioException(msgCampoObrigatorio.toString());
			
		}
	}
	 /**
	  * @author: Marcelo Rebou�as - Apr 23, 2013 - 2:44:39 PM
	  * @description: verifica se atributo est� preenchido ou n�o
	  * @return: String
	  */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <M extends Object> String verificaSeAtributoIsNull(M obj, Field field, PropertyDescriptor property) {
		
		String mensagem = null;
		//Retorna o tipo/valor do m�todo get
		M valorMetodoGet;
		
		try {
			valorMetodoGet = (M) property.getReadMethod().invoke(obj, null);
		
			if (valorMetodoGet == null) {
				
				mensagem = getMensagemObrigatoriedade(field);
				
			//###### Verifica o tipo de retorno com o instanceof######
			} else {

				if (valorMetodoGet instanceof Collection) {
					
					Collection collection = (Collection) valorMetodoGet;
					
					if (collection.size() == 0) {
						mensagem = getMensagemObrigatoriedade(field);
					} 
					
				} else if (valorMetodoGet instanceof Set) {
					
					Set set = (Set) valorMetodoGet;
					
					if (set.size() == 0) {
						mensagem = getMensagemObrigatoriedade(field);
					}
					
				} else if (valorMetodoGet instanceof Map) {
					
					Map map = (Map) valorMetodoGet;
					
					if (map.size() == 0) {
						mensagem = getMensagemObrigatoriedade(field);
					}
					
				} else if (valorMetodoGet instanceof String) {
					
					String string = (String) valorMetodoGet;
					
					if (string.trim().equals("")) {
						mensagem = getMensagemObrigatoriedade(field);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return mensagem;
	}
	
	/**
	 * @author: Marcelo Rebou�as - May 9, 2013 - 4:05:56 PM
	 * @description: retorna a mensagem mapeada na anota��o notnull
	 * @returns: String - mensagem do campo obrigat�rio
	 */
	private static String getMensagemObrigatoriedade(Field field) {

		//Captura a mensagem da anota��o notnull do atributo
		return field.getAnnotation(NotNull.class).message();
		
	}
}