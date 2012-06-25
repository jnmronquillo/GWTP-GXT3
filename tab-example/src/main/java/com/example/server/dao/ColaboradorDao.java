package com.example.server.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.server.domain.Colaborador;
import com.example.server.resultbean.ColaboradorPagingLoadResultBean;
import com.example.server.util.Paginate;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;

public class ColaboradorDao implements GenericDao<Colaborador> {

	@Inject Provider<EntityManager> emProvider;
	@Inject Paginate<Colaborador> pag;
	
	@Transactional
	public Colaborador persist(Colaborador entity) {
		emProvider.get().persist(entity);
		return entity;
	}

	public ColaboradorPagingLoadResultBean list(int offset, int limit, List<SortInfoBean> sortInfo, List<FilterConfigBean> filterConfig) {

		List<Colaborador> list = pag.paginate(Colaborador.class, offset, limit, sortInfo, filterConfig);
		Long count = pag.count(Colaborador.class);
		return new ColaboradorPagingLoadResultBean(list, count.intValue(), offset);
	}

	@Transactional
	public void remove(List<Colaborador> entities) {
		for(Colaborador entity: entities){
			emProvider.get().remove(entity);
		}		
	}

}
