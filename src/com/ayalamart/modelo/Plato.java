package com.ayalamart.modelo;

public class Plato {

	
	private String titulo, thumbnailURL, descripcion; 
	private int precio; 
	
	public Plato(){
	}
	

	public Plato(String titulo,String thumbnailURL,String descripcion, int precio){
		this.titulo = titulo; 
		this.thumbnailURL = thumbnailURL; 
		this.descripcion = descripcion; 
		this.precio = precio; 
	}
	public String getTitulo(){
		return titulo; 
	}
	public String getThumbnail(){
		return thumbnailURL; 
	}
	public String getDescripcion(){
		return descripcion; 
	}
	public int getPrecio(){
		return precio; 
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo; 
	}
	public void setThumbnail(String thumbnail){
		this.thumbnailURL = thumbnail; 
	}
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion; 
	}
	public void setPrecio(int precio){
		this.precio = precio; 
	}
	
	



}