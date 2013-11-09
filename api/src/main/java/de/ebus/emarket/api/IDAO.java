package de.ebus.emarket.api;

import java.util.List;

import javax.persistence.EntityManager;

import de.ebus.emarket.persistence.entities.AEntity;

public interface IDAO {

	public abstract <T extends AEntity> T create(T entity);

	public abstract void delete(AEntity entity);

	public abstract void deleteAll(Class<? extends AEntity> clazz);

	public abstract <T extends AEntity> T read(Class<? extends T> clazz, long id);

	public abstract <T extends AEntity> List<T> readAll(
			Class<? extends T> clazz, boolean alsoDeleted);

	public abstract <T extends AEntity> List<T> readByJPQL(String jpqString);

	public abstract void remove(AEntity entity);

	public abstract void setEntityManager(EntityManager entityManager);

	// TODO: also creates
	public abstract <T extends AEntity> T update(T entity);

}
