package br.com.mreboucas.genericdao.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marcelo Rebou�as Mar 30, 2017 - 3:27:39 PM [marceloreboucas10@gmail.com]
 */
public class EngineDao {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(EngineDao.class);
	private Session session;
	private Transaction transaction = null;
	
	
	public EngineDao() {
		
		/*this.session = HibernateFactory.getSession();
		this.transaction = session.getTransaction();*/
		beginTransaction();
	}
	
	private void beginTransaction() {
		
		if (session == null || !session.isOpen()) {
			
			this.session = HibernateFactory.getSession();
			this.transaction = session.beginTransaction();
			
		}
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @return the transaction
	 */
	public Transaction getTransaction() {
		return transaction;
	}
	
	/**
	 * @author: Marcelo Rebou�as - Apr 3, 2013 - 9:29:32 AM
	 * @description: manipula a exce��o, realizando o rollback e seta a vari�vel de retorno do m�todo da classe de controle
	 */
	public Boolean handleException(Exception ex) {
		return handleException(ex, this.transaction);
	}
	
	public static Boolean handleException(Exception ex, Transaction transaction) {
		HibernateFactory.handleException(ex, transaction);
		return Boolean.FALSE;
	}
	
	public void closeSession() {
		closeSession(this.session);
	}
	
	public static void closeSession(Session session) {
		HibernateFactory.closeSession(session);
	}
	
	/**
	 * @author: Marcelo Rebou�as - Apr 3, 2013 - 9:30:47 AM
	 */
	public Boolean commit() {
		return commit(this.transaction);
	}
	
	public static Boolean commit(Transaction transaction) {
		transaction.commit();
		LOGGER.info("...Commit efetuado...");
		return Boolean.TRUE;
	}
}
