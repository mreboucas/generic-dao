package br.com.mreboucas.genericdao.dao;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author: Marcelo Rebou�as - Mar 4, 2013 - 2:38:46 PM
 * @description: Classe respons�vel por realizar opera��es b�sicas do hibernate	
 */
public class HibernateFactory {

	//protected static final Logger LOGGER = LoggerFactory.getLogger(HibernateFactory.class);
	
	private static SessionFactory sessionFactory;
	/**
	 * Setar em RUNTIME
	 */
	private static final String AMBIENTE = "";
	//private static ServiceRegistry serviceRegistry;
	
	
	//public static void buildSessionFactory() {
	static {
		
		try {
			
			Configuration configuration = new Configuration();
			//configuration.configure();
			configuration.setProperties(readHibernateProperties());
			ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
			//serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			final ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
			sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
			System.out.println("session factory criado");
		} catch (HibernateException hEx) {
			hEx.printStackTrace();
		} catch (Exception ex) {
        	ex.printStackTrace();
        }
		
	}
	//Abre a sess�o
	private static Session openSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (Exception e) {
			session = sessionFactory.openSession();
		}
		return session;
	}
	
	//Fecha a sess�o
	public static void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			try {
				session.close();
				//LOGGER.info("...Session fechado...");
			} catch (HibernateException hEx) {
				hEx.printStackTrace();
			} catch (Exception ex) {
	        	ex.printStackTrace();
	        }
		}
	}
	
	//Realiza rollback
	public static void rollback(Transaction tx) {
        try {
           
        	if (tx != null) {
                tx.rollback();
                //LOGGER.info("...Rollback efetuado...");
            }
        } catch (HibernateException hEx) {
            hEx.printStackTrace();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
	
	//Captura a sess�o aberta
	public static Session getSession() {
		return openSession();
	}
	
	public static void handleException(Exception ex, Transaction tx) {
		rollback(tx);
		//LOGGER.error(ex.getMessage());
	}
	/**
	 * @author: Marcelo Rebouças - Mar 5, 2013 - 8:18:16 AM
	 * @throws IOException 
	 * @description: recupera o arquivo hibernate.properties
	 * @returns: Properties
	 */
	public static Properties readHibernateProperties() throws IOException {
		
		//Recupera o arquivo hibernate.properties
		Properties properties = new Properties(System.getProperties());
		properties.load(HibernateFactory.class.getClassLoader().getResourceAsStream("hibernate.properties"));
		DataBaseTypeEnum dataBaseTypeEnum = DataBaseTypeEnum.getEnumByAmbiente(AMBIENTE);
		properties.put("hibernate.connection.username", dataBaseTypeEnum.getUser());
		properties.put("hibernate.connection.password", dataBaseTypeEnum.getKey());
		properties.put("hibernate.connection.url", dataBaseTypeEnum.getUrlConnection());
			
		return properties;
	}
	
	/**
	 * @author Marcelo Rebouças 5 de set de 2017 - 16:27:34 [marceloreboucas10@gmail.com]
	 */
	public enum DataBaseTypeEnum {

		PRODUCAO("producao", "jdbc:oracle:thin:@srvoracle:1521:cgora2","USER","KEY"), 
		TESTE("teste","jdbc:oracle:thin:@srvoracleds:1521:cgora2ds","USER","KEY"), 
		HOMOLOGACAO("homologacao","jdbc:oracle:thin:@srvoracleds:1521:cgora2ds","USER","KEY");

		private String tipoDb;
		private String urlConnection;
		
		private DataBaseTypeEnum(String tipoDb, String urlConnection, String user, String key) {
			this.tipoDb = tipoDb;
			this.urlConnection = urlConnection;
			this.user = user;
			this.key = key;
		}

		private String user;
		private String key;
		
		public static DataBaseTypeEnum getEnumByAmbiente(String ambiente) {
			return StringUtils.isNotBlank(ambiente) ? DataBaseTypeEnum.valueOf(ambiente) : null;
		}


		public String getTipoDb() {
			return tipoDb;
		}
		
		public String getTipoDbUpper() {
			return tipoDb.toUpperCase();
		}
		
		public static boolean isProducao(DataBaseTypeEnum dataBaseTypeEnum) {
			return dataBaseTypeEnum == PRODUCAO ? Boolean.TRUE : Boolean.FALSE;
		}
		
		public static boolean isDesenvolvimento(DataBaseTypeEnum dataBaseTypeEnum) {
			return dataBaseTypeEnum == TESTE ? Boolean.TRUE : Boolean.FALSE;
		}
		
		public static boolean isHomologacao(DataBaseTypeEnum dataBaseTypeEnum) {
			return dataBaseTypeEnum == HOMOLOGACAO ? Boolean.TRUE : Boolean.FALSE;
		}

		private DataBaseTypeEnum(String tipoDb) {
			this.tipoDb = tipoDb;
		}

		public String getUrlConnection() {
			return urlConnection;
		}

		public String getUser() {
			return user;
		}

		public String getKey() {
			return key;
		}
	}
}