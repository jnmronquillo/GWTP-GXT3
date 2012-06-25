package com.example.server.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the colaboradores database table.
 * 
 */
@Entity
@Table(name="colaboradores")
public class Colaborador extends EntityBase {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String apellidos;

	private Integer edad;

	private String nombres;

    public Colaborador() {
    }
    
    public String getCodigo() {
		return codigo;
	}
    
    public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Integer getEdad() {
		return this.edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

}