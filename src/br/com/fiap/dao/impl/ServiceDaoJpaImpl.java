package br.com.fiap.dao.impl;

import javax.persistence.EntityManager;

import br.com.fiap.model.Service;

public class ServiceDaoJpaImpl extends GenericDaoJpaImpl<Service, Long> {

	public ServiceDaoJpaImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
