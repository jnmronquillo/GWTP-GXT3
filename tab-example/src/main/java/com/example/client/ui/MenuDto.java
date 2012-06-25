package com.example.client.ui;

public class MenuDto {
	private Long id;
	private String descripcion;
	private String token;
	public MenuDto(Long id, String descripcion, String token){
		this.id = id;
		this.descripcion = descripcion;
		this.token = token;
	}
	public Long getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getToken() {
		return token;
	}
}
