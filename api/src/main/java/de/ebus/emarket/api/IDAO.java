package de.ebus.emarket.api;

import java.util.List;

import javax.persistence.EntityManager;

import de.ebus.emarket.persistence.entities.AEntity;

public interface IDAO {

	public abstract Class<? extends AEntity> getEntityClass();

	public abstract <T extends AEntity> T create(T entity);

	public abstract void delete(AEntity entity);

	public abstract void deleteAll();

	public abstract <T extends AEntity> T read(long id);

	public abstract <T extends AEntity> List<T> readAll(boolean alsoDeleted);

	public abstract <T extends AEntity> List<T> readByJPQL(String jpqString);

	public abstract Object readSingleResultByJPQL(String jpqString);

	public abstract void remove(AEntity entity);

	public abstract void setEntityManager(EntityManager entityManager);

	public abstract <T extends AEntity> T update(T entity);

}
