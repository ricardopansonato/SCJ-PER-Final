package br.com.fiap.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import br.com.fiap.dao.GenericDao;

public class GenericDaoJpaImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	protected Class<T> entityClass;

	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public T create(T t) {
		this.entityManager.getTransaction().begin(); 
		this.entityManager.persist(t);
		this.entityManager.getTransaction().commit(); 
		return t;
	}

	@Override
	public T read(PK id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	public T update(T t) {
		this.entityManager.getTransaction().begin(); 
		T returned = this.entityManager.merge(t);
		this.entityManager.getTransaction().commit(); 
		return returned;
	}

	@Override
	public void delete(T t) {
		this.entityManager.getTransaction().begin(); 
		t = this.entityManager.merge(t);
		this.entityManager.remove(t);
		this.entityManager.getTransaction().commit(); 
	}
}