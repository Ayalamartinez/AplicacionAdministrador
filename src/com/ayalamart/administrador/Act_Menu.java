package com.ayalamart.administrador;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ayalamart.adapter.CustomListAdapter;
import com.ayalamart.adapter.CustomListAdapter.BtnClickListener;
import com.ayalamart.helper.AppController;
import com.ayalamart.modelo.Plato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class Act_Menu extends Activity{
	
	private static final String TAG = Act_Menu.class.getSimpleName(); 
	
	private static String Url = "http://api.androidhive.info/json/movies.json"; 
	 private ProgressDialog pDialog;
	 private List<Plato> listaPlato = new ArrayList<Plato>(); 
	 private CustomListAdapter adapter; 
	 private ListView listView; 
	 private int j; 
	 private TextView total; 
	 
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__menu);
		
		listView = (ListView)findViewById(R.id.LV_menu); 
		adapter = new CustomListAdapter(this, listaPlato, new BtnClickListener() {
			
			@Override
			public void onBtnClick(int position) {
				
			/*	Plato plato = new Plato(); 
				j = plato.getPrecio(); 
				String k = total.getText().toString(); 
				if (k != "00") {
					int l = Integer.parseInt(k); 
					j = j + l; 
					total.setText(j);
				}
				else{
					total.setText(k);
				}*/
			}
		}); 
		listView.setAdapter(adapter);
		
		pDialog = new ProgressDialog(this); 
		pDialog.setMessage("Cargando...");
		pDialog.show(); 
		
		
		JsonArrayRequest platoReq = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
			
			public void onResponse(JSONArray response){
				Log.d(TAG, response.toString()); 
				hidePDialog();
				
				for (int i = 0; i < response.length(); i++) {
					try {
						final JSONObject obj = response.getJSONObject(i); 
						Plato plato = new Plato(); 
						plato.setTitulo(obj.getString("title"));
						plato.setThumbnail(obj.getString("image"));
						plato.setDescripcion(obj.getString("title"));
						plato.setPrecio(obj.getInt("releaseYear"));
						listaPlato.add(plato);		
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error){
				VolleyLog.d(TAG, "Error:" + error.getMessage());
				hidePDialog(); 
			}
		});
		AppController.getInstance().addToRequestQueue(platoReq);
	}

@Override
protected void onDestroy() {
	super.onDestroy();
	hidePDialog();
}
private void hidePDialog(){
	if (pDialog != null) {
		pDialog.dismiss();
		pDialog = null; 
	}
}


}
