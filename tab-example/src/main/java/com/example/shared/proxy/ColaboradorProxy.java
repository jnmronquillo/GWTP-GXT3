package com.example.shared.proxy;

import com.example.server.domain.Colaborador;
import com.example.server.locator.EntityLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Colaborador.class, locator = EntityLocator.class)
public interface ColaboradorProxy extends EntityProxy {
	String getCodigo();
	void setCodigo(String codigo);
	String getNombres();
	void setNombres(String nombres);
	String getApellidos();
	void setApellidos(String apellidos);
	Integer getEdad();
	void setEdad(Integer edad); 
	Long getId();
}
