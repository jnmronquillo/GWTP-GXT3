package com.example.server.locator;

import javax.persistence.EntityManager;

import com.example.server.domain.EntityBase;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Generic @Locator for objects that extend EntityBase
 */
public class EntityLocator extends Locator<EntityBase, Long> {

	@Inject Injector injector;
	@Inject Provider<EntityManager> emProvider;
	
	@Override
	public EntityBase create(Class<? extends EntityBase> clazz) {
		return injector.getInstance(clazz);
	}

	@Override
	public EntityBase find(Class<? extends EntityBase> clazz, Long id) {
		return emProvider.get().find(clazz, id);
	}

	/** 
	* it's never called 
	*/
	@Override
	public Class<EntityBase> getDomainType() {
		throw new UnsupportedOperationException();
		// or return null;
	}

	@Override
	public Long getId(EntityBase domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Object getVersion(EntityBase domainObject) {
		return domainObject.getVersion();
	}

}
