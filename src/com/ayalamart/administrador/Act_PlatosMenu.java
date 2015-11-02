package com.ayalamart.administrador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayalamart.adapter.PostAdapter;
import com.ayalamart.helper.AppController;
import com.ayalamart.modelo.PostData;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Act_PlatosMenu extends ListActivity {
	String url_IngredientesAll_J = "http://192.168.1.99:8080/Restaurante/rest/ingrediente/getIngredientesAll"; 
	String url_Crearplato_J = "http://192.168.1.99:8080/Restaurante/rest/plato/createPlato"; 
	String url_IngredientesAll = "http://10.10.0.99:8080/Restaurante/rest/ingrediente/getIngredientesAll"; 
	String url_Crearplato = "http://10.10.0.99:8080/Restaurante/rest/plato/createPlato"; 
	String url_IngredientesAll_N = "http://10.0.2.2:8080/Restaurante/rest/ingrediente/getIngredientesAll"; 
	String url_Crearplato_N  = "http://10.0.2.2:8080/Restaurante/rest/plato/createPlato"; 
	private static String TAG = Act_PlatosMenu.class.getSimpleName();
	private ProgressDialog pDialog;	
	private PostAdapter adapter;
	private ArrayList<PostData> data;
	private JSONArray base; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plato__menu);
		pDialog = new ProgressDialog(this); 
		pDialog.setMessage("Cargando...");
		final AutoCompleteTextView nombrePlato = (AutoCompleteTextView)findViewById(R.id.ET_NombrePlato); 
		final AutoCompleteTextView URL_Ingr = (AutoCompleteTextView)findViewById(R.id.et_URLimg); 
		final TextView descripcionPlato = (TextView)findViewById(R.id.et_descripcionplato); 

		showpDialog();
		data = new ArrayList<PostData>();
		JsonArrayRequest ingredientesReq = new JsonArrayRequest(url_IngredientesAll, new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				Log.d(TAG, response.toString()); 
				try{

					for (int i = 0; i < response.length(); i++) {
						JSONObject ingrediente = (JSONObject)response.get(i);
						ingrediente.getString("cantstock"); 
						ingrediente.getString("nomingrediente"); 						
						ingrediente.getString("precioingrediente"); 						
						ingrediente.getString("descingrediente"); 						
						ingrediente.getString("estatus"); 						
						ingrediente.getString("fecha"); 						
						ingrediente.getString("idingrediente");						
						ingrediente.getString("tipoingrediente");

						String nombre = ingrediente.getString("nomingrediente").toString(); 
						String cantStock = ingrediente.getString("cantstock").toString(); 
						data.add(new PostData(nombre , false, "0", cantStock));
					}
					base = response; 

					setListAdapter(adapter);

				}catch(JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				hidepDialog();

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(),
						error.getMessage() + " error de respuesta del servidor", Toast.LENGTH_SHORT).show();
				hidepDialog();
			}
		}); 
		adapter = new PostAdapter(Act_PlatosMenu.this, data);
		AppController.getInstance().addToRequestQueue(ingredientesReq);

		Button but_crear = (Button)findViewById(R.id.but_agregar_plato); 
		but_crear.setOnClickListener(new OnClickListener() {
			JSONArray plato = new JSONArray(); 
			JSONObject ingred = new JSONObject(); 
			JSONObject plato_tot = new JSONObject(); 
			@Override
			public void onClick(View v) {
				showpDialog();

				if (adapter.haveSomethingSelected()) {

					int cuenta = data.size(); 
					Double subtotal = 0.0; 
					for (int i = 0; i < cuenta; i++) {
						String datosel = data.get(i).getNombres();
						if (data.get(i).getChecked())
						{
							try {
								ingred = base.getJSONObject(i);
								String precio_i = ingred.get("precioingrediente").toString(); 
								subtotal = Double.valueOf(precio_i).doubleValue() + subtotal; 
								plato.put(ingred); 
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}else
							Log.d(TAG, "ingrediente no seleccionado" + datosel); 
					}

					Double total = subtotal*0.35 + subtotal; 
					Log.d(TAG, total.toString()); 
					adapter.cancelSelectedPost();
					String nombreplato_str = nombrePlato.getText().toString(); 
					String urlplato_str = URL_Ingr.getText().toString(); 
					String desctipcionplato_str = descripcionPlato.getText().toString(); 
					Long idplato = new Long(0); 
					Calendar rightnow =Calendar.getInstance();
					SimpleDateFormat fechaact = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					String fecha = fechaact.format(rightnow.getTime());


					try {
						plato_tot.put("nomplato", nombreplato_str);
						plato_tot.put("precplato", total);
						plato_tot.put("descplato", desctipcionplato_str); 
						plato_tot.put("estatus", "1"); 
						plato_tot.put("idplato", idplato);
						plato_tot.put("fecha", fecha); 
						plato_tot.put("imgplato", urlplato_str); 
						plato_tot.put("ingredientes", plato); 

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					Log.d(TAG, "TODOELPLATO" +  plato_tot.toString()); 

					JsonObjectRequest AgregplatoREQ = new JsonObjectRequest(Method.POST, 
							url_Crearplato, plato_tot, null, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {

							VolleyLog.d(TAG, "Error: " + error.getMessage());

							Log.d(TAG, "Error CON S1: " + error.getMessage()); 
							hidepDialog();
						}
					}); 
					AppController.getInstance().addToRequestQueue(AgregplatoREQ);
					hidepDialog();
				}
				else{
					Toast.makeText(getApplicationContext(), "no ha seleccionado ingredientes", Toast.LENGTH_SHORT).show(); 
				}		

				Intent int_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
				startActivity(int_ppal);	
			}
		});

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		adapter.setCheck(position);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("savedData", data);
		super.onSaveInstanceState(outState);
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plato_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

}