package br.com.mreboucas.genericdao.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import  br.com.mreboucas.genericdao.validator.exception.CampoObrigatorioException;

/**
 * @author: Marcelo Rebou�as - Mar 4, 2013 - 2:35:23 PM
 * @description: Interface com assinaturas comuns implementadas na classe GenericDaoImpl	
 */
@SuppressWarnings("rawtypes") 
public interface GenericDao {

	public <M> Serializable save(M entity) throws Exception;

	public <M> Serializable save2(M entity) throws CampoObrigatorioException;

	public <M> void saveOrUpdate(M entity) throws Exception;

	public <M> void saveOrUpdate2(M entity) throws CampoObrigatorioException;

	public <M> void delete(M entity) throws Exception;

	public <M> void delete2(M entity);

	public <M> void update(M entity) throws CampoObrigatorioException;

	public <M> void update2(M entity) throws CampoObrigatorioException;

	public <M> M findById(Class clazz, Long id);

	public <M> M findByName(Class clazz, String condicaoValue, String paramConsulta);

	public <M> M findByCriteria(Class<?> clazz, Map<String, M> params);

	public <M> M findByCriteria(Class<?> clazz, Map<String, M> params, String[] orders, Boolean asc);

	public <M> M listAll(Class clazz, String[] orders, Boolean asc);

	public <M> M listAll(Class clazz);

	public <M> M listByNamedQuery(String namedQuery, Object[] parameters);

	public <M> M listByHql(String sql, Map<String, M> params);

	//public <M> M listByNativeSql(Class clazz, String sql, Object[] parameters);

	public <M extends Object> List listByNativeSql(Class clazz, String sql, Map<String, M> params);

    public void executeNamedQuery(String namedQuery, Object[] parameters) throws Exception;

    public <M> M listByNamedQuery(String namedQuery, Map<String, M> parameterHash);

    /**
	 * Executa determinada named query que possua par�metros chaveados por strings.
	 * 
	 * @param namedQuery
	 * @param parameterHash
	 * 
	 * @author Forge.
	 */
    public <X> void executeParameterHashNamedQuery(String namedQuery, Map<String, X> parameterHash) throws HibernateException;
    
    /**
     * @author Marcelo Rebou�as Jun 2, 2014 - 4:54:29 PM
     * @description m�todo para executar opera��es de insert, update ou delete a partir de uma sql query 		
     * @param sql
     * @param params
     * @throws HibernateException void
     */
    public <L> void executeSqlQuery(String sql, Map<String, L> params) throws HibernateException;

    /**
     * @author Marcelo Rebou�as Jun 6, 2014 - 2:00:25 PM
     * @description lista um determinado registro com todos os seus campos ou apenas alguns campos definidos no SQL  		
     * @param clazz
     * @param sql
     * @param params
     * @param aliasCampos - Define os campos que ser�o listados no SQL. Esse campo dever� ser representado da mesma forma
     * que est� declarado na classe java (btp).
     * @return
     * @throws HibernateException List<L>
     * @exemplo: Ver exemplo de uso na classe PrsFinanceiro m�todo: consultarPrestacaoByDataLiberacaoAndCodEmpreendimento(...);
     */
    public <L> List listByNativeSqlWithResultTransformer(Class clazz, String sql, Map<String, L> params, Map<String, L> aliasCampos) throws HibernateException;

    /**
     * Realiza uma consulta por id, desconsiderando a natureza de seu tipo.
     * 
     * @param c - classe referente ao objeto que dever� ser retornado.
     * @param id - c�dido identificador.
     * @return objeto com o registro encontrado, ou retorno nulo, caso n�o hajam registros.
     */
    public <D,J> J findByUntiedId(Class c, D id);
    
    public Session getSession();

}