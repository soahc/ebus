package de.ebus.emarket.server.daos;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.ebus.emarket.api.IDAO;
import de.ebus.emarket.persistence.entities.AEntity;

public abstract class DAO implements IDAO {

	static Logger logger = Logger.getLogger(DAO.class.getName());
	private EntityManager entityManager;
	
	@Override
	public abstract Class<? extends AEntity> getEntityClass();
	
	
	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#setEntityManager(javax.persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#create(T)
	 */
	@Override
	public <T extends AEntity> T create(final T entity) {
		getEntityManager().persist(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#delete(com.yoc.gaming.publisher_webservice.entities.AEntity)
	 */
	@Override
	public void delete(final AEntity entity) {
		final EntityManager em = getEntityManager();
		em.remove(em.find(entity.getClass(), entity.getId()));
	}

	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#deleteAll(java.lang.Class)
	 */
	@Override
	public void deleteAll() {
		final EntityManager em = getEntityManager();
		final String jpqlString = "DELETE FROM " + getEntityClass().getName() + " c ";
		final Query q = em.createQuery(jpqlString);
		q.executeUpdate();
	}

	

	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#read(java.lang.Class, long)
	 */
	@Override
	public AEntity read(final long id) {
		return getEntityManager().find(getEntityClass(), id);
	}

	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#readAll(java.lang.Class, boolean)
	 */
	@Override
	public <T extends AEntity> List<T> readAll(boolean alsoDeleted) {
		final String constr = (!alsoDeleted) ? "WHERE c.removed = FALSE" : "";
		return readByJPQL("SELECT c FROM " + getClass().getName() + " c " + constr);
	}

	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#readByJPQL(java.lang.String)
	 */
	@Override
	public <T extends AEntity> List<T> readByJPQL(final String jpqString) {
		final EntityManager em = getEntityManager();
		final Query q = em.createQuery(jpqString);
		@SuppressWarnings("unchecked")
		final List<T> list = q.getResultList();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#remove(com.yoc.gaming.publisher_webservice.entities.AEntity)
	 */
	@Override
	public void remove(final AEntity entity) {
		final EntityManager em = getEntityManager();
		entity.setRemoved(true);
		em.merge(entity);
	}

	// TODO: also creates
	/* (non-Javadoc)
	 * @see com.yoc.gaming.publisher_webservice.daos.IDAO#update(T)
	 */
	@Override
	public <T extends AEntity> T update(final T entity) {
		return getEntityManager().merge(entity);
	}

	

	
}

