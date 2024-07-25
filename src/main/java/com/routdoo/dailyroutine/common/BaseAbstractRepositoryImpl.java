package com.routdoo.dailyroutine.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class BaseAbstractRepositoryImpl {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	@Autowired
	protected JPAQueryFactory jpaQueryFactory;
	
}
