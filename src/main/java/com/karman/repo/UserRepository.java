package com.karman.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.karman.model.User;

public class UserRepository {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Karman");
	private EntityManager em;

	public UserRepository() {
		em = emf.createEntityManager();
	}

	public User save(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return user;
	}

	public Optional<User> findById(Long id) {
		User user = null;
		try {
			em.getTransaction().begin();
			user = em.find(User.class, id);
			em.getTransaction().commit();
		} catch (NoResultException nre) {
		}
		
		return user != null ? Optional.of(user) : Optional.empty();
	}

	public Optional<User> findByName(String name) {
		User user = null;
		try {
			user = (User) em.createQuery("from User where name = :name")
			.setParameter("name", name)
			.getSingleResult();
		} catch (NoResultException nre) {
		}
		
		return user != null ? Optional.of(user) : Optional.empty();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return em.createQuery("from User").getResultList();
	}

	public User update(User user) {
		em.getTransaction().begin();
		user = em.merge(user);
		em.getTransaction().commit();
		return user;
	}

	public void deleteById(Long id) {
		em.getTransaction().begin();
		em.remove(em.find(User.class, id));
		em.getTransaction().commit();
	}

	public void close() {
		emf.close();
	}
}
