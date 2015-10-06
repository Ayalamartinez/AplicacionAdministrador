package com.ayalamart.administrador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.ayalamart.helper.AppController;
import com.ayalamart.helper.GestionSesionesUsuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class Act_Ingredientes extends AppCompatActivity {
	private AutoCompleteTextView nombre_ingr; 
	private AutoCompleteTextView tipo_ingrediente; 
	private EditText descr_ingrediente; 
	private AutoCompleteTextView precio;
	private AutoCompleteTextView cantidad;
	private ProgressDialog pDialog;
	private static String TAG = Act_Ingredientes.class.getSimpleName(); 
	GestionSesionesUsuario sesion; 
	
	String URL_AGREG_INGREDS = "http://192.168.1.99:8080/Restaurante/rest/ingrediente/createIngrediente";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__ingredientes);
		sesion = new GestionSesionesUsuario(getApplicationContext());
		
		final HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
		final String idcliente = usuario.get(GestionSesionesUsuario.idcliente); 
		
		
	
	nombre_ingr = (AutoCompleteTextView)findViewById(R.id.nombre_ingrediente);
	tipo_ingrediente = (AutoCompleteTextView)findViewById(R.id.tipo_ingrediente);
	descr_ingrediente = (EditText)findViewById(R.id.descripcion_ingrediente);
	precio = (AutoCompleteTextView)findViewById(R.id.precio_ingrediente);
	cantidad = (AutoCompleteTextView)findViewById(R.id.cantidad_ingrediente);
	
	pDialog = new ProgressDialog(this); 
	
	
	Button butt_submit = (Button) findViewById(R.id.but_submit);
	butt_submit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			final String nombr_ingr_str = nombre_ingr.getText().toString();
			final String tipo_ing_str = tipo_ingrediente.getText().toString();
			final String descrip_ing_str = descr_ingrediente.getText().toString();
			final String precio_str = precio.getText().toString();
			final String cant_str = cantidad.getText().toString();
			Log.d(TAG, "sacó la data de los ET"); 
			
			Long idingrediente = new Long (0);
			
			
			Calendar rightnow =Calendar.getInstance();
			SimpleDateFormat fechaact = new SimpleDateFormat("dd-MMM-yyyy");
			String fecha = fechaact.format(rightnow.getTime());
			Log.d(TAG, "tomó la fecha del calendar"); 
			
			JSONObject js_ingr = new JSONObject();
			
			try {
				js_ingr.put("idingrediente", idingrediente);
				js_ingr.put("idCliente", idcliente);
				js_ingr.put("nomingrediente", nombr_ingr_str);
				js_ingr.put("descingrediente", descrip_ing_str);
				js_ingr.put("tipoingrediente", tipo_ing_str);
				js_ingr.put("precioingrediente", precio_str);
				js_ingr.put("fecha", fecha);
				js_ingr.put("cantstock", cant_str);
				js_ingr.put("estatus", "1");
				Log.d(TAG, "creó el objeto JSON"); 
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pDialog.setMessage("Cargando...");
			pDialog.show(); 
			
			JsonObjectRequest json_objreq = new JsonObjectRequest(Method.POST, URL_AGREG_INGREDS, js_ingr, null, new  Response.ErrorListener() {
				
				@Override
				public void onErrorResponse(VolleyError error) {
					VolleyLog.d(TAG, "Error: " + error.getMessage());

					Log.d(TAG, "Error: " + error.getMessage()); 
					/*Toast.makeText(getApplicationContext(),
									error.getMessage(), Toast.LENGTH_SHORT).show();
					 */
					// hide the progress dialog
					hidepDialog();
				}
				
			});
			Intent intent_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
			intent_ppal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent_ppal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(intent_ppal);
			finish(); 
			
			AppController.getInstance().addToRequestQueue(json_objreq);
			Log.d(TAG, "hizo la llamada del hilo secundario"); 
			
			
		
		
		}
	});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act__ingredientes, menu);
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
