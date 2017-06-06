package br.com.fiap.dao.impl;

import javax.persistence.EntityManager;

import br.com.fiap.model.Order;

public class OrderDaoJpaImpl extends GenericDaoJpaImpl<Order, Long> {

	public OrderDaoJpaImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
