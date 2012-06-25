package com.example.server.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.server.domain.EntityBase;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;

public class Paginate<T extends EntityBase> {

	private Provider<EntityManager> emProvider;
	private List<FilterConfigBean> filterConfig;
	
	@Inject
	public Paginate(Provider<EntityManager> emProvider){
		this.emProvider = emProvider;		
	}
	
	public List<T> paginate(Class<T> clazz, int offset, int limit, List<SortInfoBean> sortInfo, List<FilterConfigBean> filterConfig){
		this.filterConfig = filterConfig;
		CriteriaBuilder cb = emProvider.get().getCriteriaBuilder();
		CriteriaQuery<T> c = cb.createQuery(clazz);
		Root<T> r = c.from(clazz);
				
		c.where(condition(cb, r).toArray(new Predicate[]{}));
				
		if(sortInfo.size() == 0)
			c.orderBy(cb.desc(r.get("id")));
		else{
			if(sortInfo.get(0).getSortDir() == SortDir.ASC)
				c.orderBy(cb.asc(r.get(sortInfo.get(0).getSortField())));
			if(sortInfo.get(0).getSortDir() == SortDir.DESC)
				c.orderBy(cb.desc(r.get(sortInfo.get(0).getSortField())));
		}		
		
		TypedQuery<T> q = emProvider.get().createQuery(c);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();		
	}
	
	public Long count(Class<T> clazz){

		CriteriaBuilder cb = emProvider.get().getCriteriaBuilder();
		CriteriaQuery<Long> c = cb.createQuery(Long.class);
		Root<T> r = c.from(clazz);
		c.where(condition(cb, r).toArray(new Predicate[]{}));
		c.select(cb.count(r));
		return emProvider.get().createQuery(c).getSingleResult();
	}
	
	private List<Predicate> condition(CriteriaBuilder cb, Root<T> r){
		List<Predicate> predicates = new ArrayList<Predicate>();
		for(FilterConfigBean s : filterConfig){
			
			if("contains".equals(s.getComparison())){				
				predicates.add(cb.like(cb.lower(r.<String>get(s.getField())), "%"+s.getValue().toLowerCase()+"%"));
			}
			if("gt".equals(s.getComparison())){
				predicates.add(cb.gt(r.<Double>get(s.getField()), Double.valueOf(s.getValue())));
			}
			if("lt".equals(s.getComparison())){
				predicates.add(cb.lt(r.<Double>get(s.getField()), Double.valueOf(s.getValue())));
			}
			if("eq".equals(s.getComparison())){
				predicates.add(cb.equal(r.<Double>get(s.getField()), Double.valueOf(s.getValue())));				
			}
			if("on".equals(s.getComparison())){				
			}
			if("after".equals(s.getComparison())){				
			}
			if("before".equals(s.getComparison())){				
			}
			
		}
		return predicates;
	}
}
