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
import com.ayalamart.adapter.PostData;
import com.ayalamart.helper.AppController;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Act_PlatosMenu extends ListActivity {
	String url_IngredientesAll = "http://192.168.1.99:8080/Restaurante/rest/ingrediente/getIngredientesAll"; 
	String url_Crearplato = "http://192.168.1.99:8080/Restaurante/rest/plato/createPlato"; 
	String url_IngredientesAll_n = "http://10.10.0.99:8080/Restaurante/rest/ingrediente/getIngredientesAll"; 
	String url_Crearplato_n = "http://10.10.0.99:8080/Restaurante/rest/plato/createPlato"; 

	private static String TAG = Act_PlatosMenu.class.getSimpleName();
	private ProgressDialog pDialog;
	JSONArray resultado; 

	Spinner campoIngr ;
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
						String cantstock = ingrediente.getString("cantstock").toString(); 
						ingrediente.getString("nomingrediente"); 
						String nombre = ingrediente.getString("nomingrediente").toString(); 
						ingrediente.getString("precioingrediente"); 
						String precio = ingrediente.getString("precioingrediente").toString();
						ingrediente.getString("descingrediente"); 
						String descingrediente = ingrediente.getString("descingrediente").toString(); 
						ingrediente.getString("estatus"); 
						String estatus = ingrediente.getString("estatus").toString(); 
						ingrediente.getString("fecha"); 
						String fecha = ingrediente.getString("fecha").toString(); 
						ingrediente.getString("idingrediente");
						String idingrediente = ingrediente.getString("idingrediente").toString(); 
						ingrediente.getString("tipoingrediente");
						String tipoingrediente = ingrediente.getString("tipoingrediente").toString(); 


						data.add(new PostData(nombre , false));
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
						error.getMessage(), Toast.LENGTH_SHORT).show();
				hidepDialog();
			}
		}); 
		adapter = new PostAdapter(Act_PlatosMenu.this, data);
		AppController.getInstance().addToRequestQueue(ingredientesReq);

		Button but_crear = (Button)findViewById(R.id.but_agregar_plato); 
		but_crear.setOnClickListener(new OnClickListener() {
			JSONObject plato = new JSONObject(); 
			JSONObject ingrediente_n = new JSONObject(); 
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
								JSONObject ingred = base.getJSONObject(i);
								String iding_str = ingred.get("idingrediente").toString(); 
								String nomingr_str = ingred.getString("nomingrediente").toString(); 
								ingrediente_n.put("nomingrediente", nomingr_str); 
								ingrediente_n.put("idingrediente", iding_str); 
								Log.d(TAG, "Objeto JSON"+ ingred.toString()); 
								String precio_i = ingred.get("precioingrediente").toString(); 
								subtotal = Double.valueOf(precio_i).doubleValue() + subtotal; 
								plato.put("ingrediente", ingrediente_n); 
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
					/*
					 * 	plato.setNomplato("Parrilla");
						plato.setPrecplato(new BigDecimal(769));
						plato.setDescplato("Parrilla");
						plato.setEstatus("1");
						plato.setIdplato(new Long(0));
						plato.setFecha("2015-09-19");
						plato.setImgplato("");*/

					try {
						plato.put("nomplato", nombreplato_str);
						plato.put("precplato", total);
						plato.put("descplato", desctipcionplato_str); 
						plato.put("estatus", "1"); 
						plato.put("idplato", idplato);
						plato.put("fecha", fecha); 
						plato.put("imgplato", urlplato_str); 

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

					JsonObjectRequest AgregplatoREQ = new JsonObjectRequest(Method.POST, 
							url_Crearplato, plato, null, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
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



	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plato__menu, menu);
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
	 */
	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

}
