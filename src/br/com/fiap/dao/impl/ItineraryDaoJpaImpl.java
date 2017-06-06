package br.com.fiap.dao.impl;

import javax.persistence.EntityManager;

import br.com.fiap.model.Itinerary;

public class ItineraryDaoJpaImpl extends GenericDaoJpaImpl<Itinerary, Long> {

	public ItineraryDaoJpaImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
