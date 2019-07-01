package br.com.mreboucas.genericdao.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import br.com.mreboucas.genericdao.enums.EnumRestrictionsCriteria;
import br.com.mreboucas.genericdao.validator.exception.CampoObrigatorioException;
import br.com.mreboucas.genericdao.validator.validacao.CommonValidator;


	
/**
 * @author: Marcelo Rebou�as - Mar 4, 2013 - 2:40:32 PM
 * @description: Classe respons�vel por implementar as opera��es de banco de dados	
 */
public class GenericDaoImpl implements GenericDao {
	
	private Session session = null;
	private Transaction tx = null;
	
	public GenericDaoImpl(Session session) {
		super();
		this.session = session;
		this.tx = session.getTransaction();
	}

	public GenericDaoImpl() {
	}
	
	//Inicia opera��o (Sess�o e transa��o)
	private void beginTransaction() {
		if (session == null || tx == null) {
			this.session = HibernateFactory.getSession();
			this.tx = session.beginTransaction();
		}
	}
	//Manipula exce��o
	private void handleException(Exception e) throws HibernateException {
		HibernateFactory.handleException(e, tx);
		throw new HibernateException(e);
	}
	
	private void printLogRowsAffected(int linesAffected) {
		System.out.println(linesAffected + " rows affected");
	}
	//Fecha a sess�o
	public void closeSession(){
		HibernateFactory.closeSession(session);
	}
	
	public <M> Serializable save(M entity) throws Exception {
		
		CommonValidator.validaCamposObrigatorios(entity);
		
		Serializable id = null;
		try {
			
			id = session.save(entity);
			
		} catch (HibernateException hEx) {
			throw new HibernateException(hEx); 
		} catch (Exception ex) {
			throw new Exception(ex); 
        } 
		
		return id;
	}

	public <M> Serializable save2(M entity) throws CampoObrigatorioException {
		
		CommonValidator.validaCamposObrigatorios(entity);
		
		Serializable id = null;
		try {
			
			beginTransaction();
			id = session.save(entity);
			tx.commit();
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
		} finally {
			closeSession();
		}
		
		return id;
	}
	
	@Override
	public <M> void saveOrUpdate2(M entity) throws CampoObrigatorioException {
		
		CommonValidator.validaCamposObrigatorios(entity);
		
		try {
			
			beginTransaction();
			session.saveOrUpdate(entity);
			tx.commit();
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
        } finally {
        	closeSession();
		}
	}

	@Override
	public <M> void delete(M entity) throws Exception {
		
		try {
			
			session.delete(entity);
			
		} catch (HibernateException hEx) {
			throw new HibernateException(hEx);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public <M> void update(M entity) throws CampoObrigatorioException {
		
		CommonValidator.validaCamposObrigatorios(entity);
		
		try {
			
			session.update(entity);
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
        } 
	}
	
	@Override
	public <M> void update2(M entity) throws CampoObrigatorioException {

		CommonValidator.validaCamposObrigatorios(entity);
		
		try {
			
			beginTransaction();
			session.update(entity);
			tx.commit();
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
		} finally {
			closeSession();
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public <M> M findById(Class clazz, Long id) {
		Object object = null;
		try {
			
			beginTransaction();
			//object = session.load(clazz, id);
			object = session.get(clazz, id);
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
        } 
		return (M) object;
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <M> M listAll(Class clazz, String[] orders, Boolean asc) {
		List<M> list = null;
		try {
			
			beginTransaction();
			
			Criteria criteria = session.createCriteria(clazz);
			
			//Par�metros de ordena��o
			if (orders != null && orders.length > 0) {
				
				for (String ordem : orders) {
					//Ascendente
					if (asc) 
						criteria.addOrder(Order.asc(ordem));
					//Descendente
					 else 
						 criteria.addOrder(Order.desc(ordem));
					
				}
			}
			
			list = criteria.list();
		
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
        } finally {
        	closeSession();
		}
		
		return (M) list;
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public <M> M listAll(Class clazz) {
    	List<M> list = null;
    	try {
    		
    		beginTransaction();
    		
			Query query = session.createQuery("from " + clazz.getName());
			
			list = query.list();
    		
    	} catch (HibernateException hEx) {
    		handleException(hEx);
    	} catch (Exception ex) {
    		handleException(ex);
    	}
    	
    	return (M) list;
    }

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <M> M listByNamedQuery(String namedQuery, Object[] parameters) {
		List list = null;
		try {
			
			beginTransaction();

			Query query = session.getNamedQuery(namedQuery);

			setParametersQuery(parameters, query);
			
			list = query.list();
			
		} catch (HibernateException HEx) {
			handleException(HEx);
		} catch (Exception ex) {
			handleException(ex);
        } 
		return (M) list;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <M> M listByNamedQuery(String namedQuery, Map<String, M> parameterHash) {
	    List list = null;
	    try {
	        
	        beginTransaction();
	        
	        Query query = session.getNamedQuery(namedQuery);
	        
	        setQueryParameterHash(query, parameterHash);
	        
	        list = query.list();
	        
	    } catch (HibernateException HEx) {
	        handleException(HEx);
	    } catch (Exception ex) {
	        handleException(ex);
	    } 
	    return (M) list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public <M> M findByName(Class clazz, String condicaoValue, String paramConsulta) {
		List list = null;
		
		try {
			
			beginTransaction();
			
			Query query = session.createQuery("from " + clazz.getName() + " where " + condicaoValue + " like :param");
			query.setString("param", "%" + paramConsulta + "%");
			
			list = query.list();
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception e) {
			handleException(e);
		} 
		return (M) list;
	}
	
    @Override
    public <M> void saveOrUpdate(M entity) throws Exception {
    	
    	CommonValidator.validaCamposObrigatorios(entity);
    	
        try{
            
            session.saveOrUpdate(entity);
            //session.flush();
            
        }catch (HibernateException hEx){
            throw new HibernateException(hEx);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

	@Override
	public <M> void delete2(M entity) {
		try {
			
			beginTransaction();
			session.delete(entity);
			
			if (!tx.wasCommitted())
				tx.commit();
			
		} catch (HibernateException hEx) {
			handleException(hEx);
		} catch (Exception ex) {
			handleException(ex);
        } finally {
        	closeSession();
		}
	}
			
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <M> M findByCriteria(Class<?> clazz, Map<String, M> params) {
		List list = null;
        
        try{
        
        	beginTransaction();
            Criteria criteria = session.createCriteria(clazz);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            setCriteriaParameteres(criteria, params);
            /*if (params != null){
            	//Retorna os parmametros
            	Iterator<String> keys = params.keySet().iterator();
                
            	while (keys.hasNext()){
                
            		String key = keys.next();
                    Object param = params.get(key);
                    
                    //Monta os par�metros para realizar a consulta
                    if (param != null) {
                       
                    	if (param instanceof String) {
                            if (Constants.IS_NULL.equals(param)) {
                                criteria.add(Restrictions.isNull(key));
                            } else if (Constants.IS_NOT_NULL.equals(param)) {
                                criteria.add(Restrictions.isNotNull(key));
                            } else {
                                criteria.add(Restrictions.ilike(key, "%" + param + "%"));
                            }
                        } else {
                            criteria.add(Restrictions.eq(key, param));
                        }
                    }
                }
            }*/
         
            list = criteria.list();

        }catch (HibernateException hEx){
            handleException(hEx);
        }catch (Exception ex){
            handleException(ex);
        }
        return (M) list;
    }
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <M> M findByCriteria(Class<?> clazz, Map<String, M> params, String[] orders, Boolean asc) {
    	List list = null;
    	
    	try {
    		
    		beginTransaction();
    		Criteria criteria = session.createCriteria(clazz);
    		
    		setCriteriaParameteres(criteria, params);
    		
    		/*if (params != null) {
    			//Retorna os parametros
    			Iterator<String> keys = params.keySet().iterator();
    			
    			while (keys.hasNext()){
    				
    				String key = keys.next();
    				Object param = params.get(key);
    				
    				//Monta os parametros para realizar a consulta
    				if (param != null) {
    					
    					if (param instanceof String) {
    						if (Constants.IS_NULL.equals(param)) {
    							criteria.add(Restrictions.isNull(key));
    						} else if (Constants.IS_NOT_NULL.equals(param)) {
    							criteria.add(Restrictions.isNotNull(key));
    						} else {
    							criteria.add(Restrictions.ilike(key, "%" + param + "%"));
    						}
    					} else {
    						criteria.add(Restrictions.eq(key, param));
    					}
    				}
    			}
    		}*/
    		//Ordena��o
    		if (orders != null && orders.length > 0) {
    			
    			for (String ordem : orders) {
    				
    				if (asc) 
    					criteria.addOrder(Order.asc(ordem));
    				else
    					criteria.addOrder(Order.desc(ordem));
    				
    			}	
    		}
    		
    		list = criteria.list();
    		
    	}catch (HibernateException hEx){
    		handleException(hEx);
    	}catch (Exception ex){
    		handleException(ex);
    	}
    	return (M) list;
    }

	/**
	 * @author Marcelo Rebou�as 17 de ago de 2017 - 10:42:42 [marceloreboucas10@gmail.com]
	 * @param criteria 
	 * @return void
	 */
	private <I> void setCriteriaParameteres(Criteria criteria, Map<String, I> params) {
		
		if (params != null) {
			//Retorna os parametros
			Iterator<String> keys = params.keySet().iterator();
			
			while (keys.hasNext()){
				
				String attributeName = keys.next();
				Object value = params.get(attributeName);
				String[] keyArray = attributeName.split("_");
				
				attributeName = keyArray[0];
				String restriction = "";
				EnumRestrictionsCriteria enumRestriction = null;
				
				if (keyArray.length > 1) {
					/**@EXAMPLE_TO_USE
					 * params.put("vncNumContrato" + EnumRestriction.EQUAL.getProperty(), (M) codContratoParameter);
					 **/
					restriction = keyArray[1];
					enumRestriction = EnumRestrictionsCriteria.getEnumByProperty(restriction);
					
				} else {
					
					/**@EXAMPLE_TO_USE
					 * params.put("vncNumContrato", (M) EnumRestriction.IS_NOT_NULL.getRestiction);
					 **/
					if (value.toString().contains("_")) {
						enumRestriction = EnumRestrictionsCriteria.getEnumByProperty(value.toString());
						
					} else {
						
						/**@EXAMPLE_TO_USE
						 * params.put("vncNumContrato", (M) EnumRestriction.IS_NOT_NULL);
						 **/
						if (value instanceof EnumRestrictionsCriteria) {
							enumRestriction = (EnumRestrictionsCriteria) value;
						} else {
							enumRestriction = EnumRestrictionsCriteria.UNKOWN;
						}
					}
				}
				
				switch (enumRestriction) {
				
					case IS_NULL:
						
						criteria.add(Restrictions.isNull(attributeName));
						break;
						
					case IS_NOT_NULL:
						
						criteria.add(Restrictions.isNotNull(attributeName));
						break;
						
					case ILIKE:
						
						criteria.add(Restrictions.ilike(attributeName, "%" + value + "%"));
						break;
					
					case LIKE:
	
						criteria.add(Restrictions.like(attributeName, "%" + value + "%"));
						break;
						
					case GREATER_THAN_OR_EQUAL:
						
						criteria.add(Restrictions.ge(attributeName, value));
						break;
						
					case GREATER_THAN:
						
						criteria.add(Restrictions.gt(attributeName, value));
						break;
						
					case LESS_THAN_OR_EQUAL:
						
						criteria.add(Restrictions.le(attributeName, value));
						break;
						
					case LESS_THAN:
						
						criteria.add(Restrictions.lt(attributeName, value));
						break;
					
					case EQUAL:
						
						criteria.add(Restrictions.eq(attributeName, value));
						break;
						
					case NOT_EQUAL:
						
						criteria.add(Restrictions.ne(attributeName, value));
						break;
						
					case IS_EMPTY:
						
						criteria.add(Restrictions.isEmpty(attributeName));
						break;
						
					case IS_NOT_EMPTY:
						
						criteria.add(Restrictions.isNotEmpty(attributeName));
						break;
						
					default:
						criteria.add(Restrictions.eq(attributeName, value));;
				}
			}
		}
	}
    
    @Override
    @SuppressWarnings({ "rawtypes"})
    public <M> List listByNativeSql(Class clazz, String sql, Map<String, M> params) {
        List list = null;
        try {
            
            beginTransaction();
            
            Query query = session.createSQLQuery(sql).addEntity(clazz);

            //Seta os parametros
           	//setParametersQuery(parameters, query);
            setQueryParameterHash(query, params);
            
           	list = query.list();
            
        } catch (HibernateException HEx) {
            handleException(HEx);
        } catch (Exception ex) {
            handleException(ex);
        } 
        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
	@Override
    public <D, J> J findByUntiedId(Class c, D id) {
		J j = null;

		j = (J) session.get(c, (Serializable) id);

		return (J) j;
	}

    //Seta par�metros no obj query
	private void setParametersQuery(Object [] parameters, Query query) {
		
		if (parameters != null && parameters.length > 0) {
			
			Object param;
			
		    //percorre os parâmetros da consulta
            for (int m = 0; m < parameters.length; m ++) {
            	
            	param = parameters[m];
            	query.setParameter(m, parameters[m]);
            }
		}
	}

	/**
	 * @description: tem o objetivo de efeturar transa��es (insert, update e delete) by namedQuery
	 */
	@Override
	public void executeNamedQuery(String namedQuery, Object[] parameters) throws Exception {
		
		try {
            
            Query query = session.getNamedQuery(namedQuery);
           
            //Seta os par�metros
           	setParametersQuery(parameters, query);
            
           	Integer rowsAffected = query.executeUpdate();
           	
           	printLogRowsAffected(rowsAffected);
            
		}catch (HibernateException hEx){
	            throw new HibernateException(hEx);
        }catch (Exception ex){
            throw new Exception(ex);
        }
	}

	/**
	 * Executa determinada named query que possua par�metros chaveados por strings.
	 * 
	 * @param namedQuery
	 * @param parameterHash
	 * 
	 * @author Forge.
	 */
	public <X> void executeParameterHashNamedQuery(String namedQuery, Map<String, X> parameterHash) throws HibernateException {
		try {
			Query query = session.getNamedQuery(namedQuery);

			setQueryParameterHash(query, parameterHash);

			Integer rowsAffected = query.executeUpdate();

			printLogRowsAffected(rowsAffected);

		} catch(RuntimeException rex) {
			throw new RuntimeException(rex.getMessage());
		}
	}

	/**
	 * Define os par�metros chaveados por strings em determinada query.
	 * @param query
	 * @param parameterHash
	 */
	@SuppressWarnings("rawtypes")
	private <X> void setQueryParameterHash(Query query, Map<String, X> parameterHash) {
		
		if (parameterHash != null && parameterHash.size() > 0) {
		
			for (Entry<String, X> entry : parameterHash.entrySet()) {
				if (entry.getValue() instanceof Integer) {
					query.setInteger(entry.getKey(), (Integer) entry.getValue());
					continue;
				}
	
				if (entry.getValue() instanceof Long) {
					query.setLong(entry.getKey(), (Long) entry.getValue());
					continue;
				}
	
				if (entry.getValue() instanceof BigDecimal) {
					query.setBigDecimal(entry.getKey(), (BigDecimal) entry.getValue());
					continue;
				}
	
				if (entry.getValue() instanceof String) {
					query.setString(entry.getKey(), (String) entry.getValue());
					continue;
				}
	
				if (entry.getValue() instanceof Calendar) {
					query.setCalendar(entry.getKey(), (Calendar) entry.getValue());
					continue;
				}
				
				if (entry.getValue() instanceof Date) {
					query.setDate(entry.getKey(), (Date) entry.getValue());
					continue;
				}
	
				if (entry.getValue() instanceof Timestamp) {
					query.setTimestamp(entry.getKey(), (Timestamp) entry.getValue());
					continue;
				}
				
				if (entry.getValue() instanceof Boolean) {
				    query.setBoolean(entry.getKey(), (Boolean) entry.getValue());
				    continue;
				}
				
				if (entry.getValue() instanceof Collection) {
					query.setParameterList(entry.getKey(), (Collection) entry.getValue());
				}
				
			}
		}
	}

	@Override
	public <M> M listByHql(String sql, Map<String, M> params) {
		
		List list = null;
        
		try {
            
            beginTransaction();
            
            Query query = session.createQuery(sql);
            //Seta os par�metros
            setQueryParameterHash(query, params);
            
           	list = query.list();
            
        } catch (HibernateException HEx) {
            handleException(HEx);
        } catch (Exception ex) {
            handleException(ex);
        } 
        return (M) list;
	}

	@Override
	public <L> void executeSqlQuery(String sql, Map<String, L> params) throws HibernateException {
		
		try {
			
			beginTransaction();
			
			Query query = session.createSQLQuery(sql);
			//Seta os par�metros
			setQueryParameterHash(query, params);
			
			//executa a opera��o
			Integer rowsAffected = query.executeUpdate();
			
			printLogRowsAffected(rowsAffected);
			
		} catch (HibernateException e) {
			handleException(e);
		} catch (Exception e) {
			handleException(e);
		}
	}

	@Override
	public <L> List listByNativeSqlWithResultTransformer(Class clazz, String sql, Map<String, L> params, Map<String, L> aliasCampos) throws HibernateException {
		
		List list = null;
		
		try {
			
			beginTransaction();
			
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			
			/**
			 * Seta a estrat�gia para montagem/transforma��o do objeto. Nesse caso
			 * a estrat�gia � para montagem do objeto a partir dos alias passados por 
			 * par�metro no aliasCampos  
			 */
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));;
			
			/**
			 * Adicionar a consulta os campos em forma de alias para montagem do objeto
			 * apenas com os campos especificados no SQL.
			 * Ver exemplo de uso na classe PrsFinanceiro. java m�todo: consultarPrestacaoByDataLiberacaoAndCodEmpreendimento(...);
			 */
			
			if (aliasCampos != null && aliasCampos.size() > 0) {
				
				for (Entry<String, L> entry : aliasCampos.entrySet()) {

					sqlQuery.addScalar(entry.getKey(), (Type) entry.getValue());
					
				}
			}
			
			//Seta os par�metros
			setQueryParameterHash(sqlQuery, params);
			
			list = sqlQuery.list();
			
		} catch (HibernateException e) {
			handleException(e);
		} catch (Exception e) {
			handleException(e);
		}
		
		return list;
		
	}
	
	@Override
	public Session getSession() {
		return session;
	}
}