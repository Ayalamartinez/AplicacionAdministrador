package com.ayalamart.modelo;

import java.lang.reflect.Array;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;

public class PostData implements Parcelable {

	private String titulo;
	private boolean escogido;
	private String cantidad; 
	private String cantstock; 

	public PostData(String Nombre, boolean checked, String Cantidad, String Cantstock) {
		this.titulo = Nombre;
		this.escogido = checked;
		this.cantidad = Cantidad; 
		this.cantstock = Cantstock; 
	}

	
	public PostData(Parcel in){
		this.titulo= in.readString();
		this.cantidad = in.readString(); 
		this.cantstock = in.readString(); 
		this.escogido = in.readInt() == 1 ? true:false;
		
	}

	public void setChecked(boolean value) {
		this.escogido = value;
	}

	public boolean getChecked() {
		return escogido;
	}

	public String getNombres() {
		return titulo;
	}
	public String getCantidad(){
		return cantidad; 
	}
	public String getCantStock() {
		return cantstock; 
	}

	public void setNombres(String Nombres) {
		this.titulo = Nombres;
	}
	public void setCantidad(String Cantidades){
		this.cantidad = Cantidades; 
	}
	public void setCantStock(String CantStock){
		this.cantstock = CantStock; 
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getNombres());
		dest.writeString(getCantStock());
		dest.writeInt(getChecked() ? 1 : 0);
	}

	public static final Parcelable.Creator<PostData> CREATOR = new Parcelable.Creator<PostData>() {
		public PostData createFromParcel(Parcel in) {
			return new PostData(in);
		}

		public PostData[] newArray(int size) {
			return new PostData[size];
		}
	};
}