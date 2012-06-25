package com.example.server.resultbean;

import java.util.List;

import com.example.server.domain.Colaborador;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
 
@SuppressWarnings("serial")
public class ColaboradorPagingLoadResultBean extends PagingLoadResultBean<Colaborador> {
    public ColaboradorPagingLoadResultBean(List<Colaborador> list, int totalLength, int offset) {
      super(list, totalLength, offset);
    }
    protected ColaboradorPagingLoadResultBean() {
        super();
    }
}
