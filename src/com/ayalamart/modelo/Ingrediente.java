package com.ayalamart.modelo;

public class Ingrediente {

	private String nomingrediente; 
	private String precioingrediente; 
	private String cantstock; 
	private String descingrediente; 
	private String estatus; 
	private String fecha; 
	private String idingrediente; 
	private String tipoingrediente; 

	public Ingrediente (String nom, String precio, String cantst,  String descr, String stat, String fech, String id, String tipo ){
		nom =  nomingrediente; 
		precio =  precioingrediente; 
		cantst = cantstock; 
		descr =descingrediente; 
		stat =estatus; 
		fech =fecha; 
		id =idingrediente; 
		tipo  =tipoingrediente; 
	}
	public String getId(){
		return idingrediente; 
	}
	public String getnombrIngr(){
		return nomingrediente; 
	}

	public String getdescIngr(){
		return descingrediente; 
	}
	public String getCantStock(){
		return cantstock; 
	}
	public String getprecioIngr(){
		return precioingrediente; 
	}
	public String getestatIngr(){
		return estatus; 
	}

	public String getFechaIngr(){
		return fecha; 
	}
	public String gettipoIngr(){
		return tipoingrediente; 
	}
	public void setId(String idingrediente){
	 this.idingrediente = idingrediente; 
	}
	public void setnombrIngr(String nomingrediente){
		 this.nomingrediente = nomingrediente;  ; 
	}

	public void setdescIngr(String descingrediente){
		 this.descingrediente = descingrediente; 
	}
	public void setCantStock(String cantstock){
		this.cantstock = cantstock; 
	}
	public void setprecioIngr(String precioingrediente){
		this.precioingrediente = precioingrediente; 
	}
	public void setestatIngr(String estatus){
		this.estatus = estatus; 
	}

	public void setFechaIngr(String fecha){
		this.fecha= fecha; 
	}
	public void settipoIngr(String tipoingrediente){
		this.tipoingrediente = tipoingrediente; 
	}

}
