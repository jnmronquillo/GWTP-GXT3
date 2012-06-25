package com.example.shared.service;

import java.util.List;

import com.example.server.dao.ColaboradorDao;
import com.example.server.requestfactory.InjectingServiceLocator;
import com.example.server.resultbean.ColaboradorPagingLoadResultBean;
import com.example.shared.proxy.ColaboradorProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

@Service(value = ColaboradorDao.class, locator = InjectingServiceLocator.class)
public interface ColaboradorService extends RequestContext {
	
    Request<ColaboradorProxy> persist(ColaboradorProxy colaborador);
    Request<Void> remove(List<ColaboradorProxy> colaborador);
    
    @ProxyFor(value = ColaboradorPagingLoadResultBean.class)
    public interface ColaboradorPagingLoadResultProxy extends ValueProxy, PagingLoadResult<ColaboradorProxy> {
        public List<ColaboradorProxy> getData();
    }
     
    Request<ColaboradorPagingLoadResultProxy> list(int offset, int limit, List<? extends SortInfo> sortInfo, List<? extends FilterConfig> filterConfig);
}
