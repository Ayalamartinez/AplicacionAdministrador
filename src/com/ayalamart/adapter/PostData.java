package com.ayalamart.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class PostData implements Parcelable {

	private String titulo;
	private boolean escogido;

	public PostData(String Nombre, boolean checked) {
		this.titulo = Nombre;
		this.escogido = checked;

	}
	
	public PostData(Parcel in){
		this.titulo= in.readString();
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

	public void setNombres(String Nombres) {
		this.titulo = Nombres;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getNombres());
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
