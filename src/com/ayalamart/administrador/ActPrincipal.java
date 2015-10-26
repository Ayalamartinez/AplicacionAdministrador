package com.ayalamart.administrador;

import com.ayalamart.helper.GestionSesionesUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActPrincipal extends AppCompatActivity {

	GestionSesionesUsuario sesion; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_principal);

		sesion = new GestionSesionesUsuario(getApplicationContext());
		if (sesion.verificarLogin()) {
			finish(); 
		}
		Button but_menu = (Button)findViewById(R.id.bt_gestMenu); 
		but_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent int_menu = new Intent(getApplicationContext(), Act_ConfMenu.class); 
				int_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				int_menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(int_menu);
				finish(); 
			}
		});



		Button but_ingredientes = (Button)findViewById(R.id.but_ingrecientes);
		but_ingredientes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent int_ingr = new Intent(getApplicationContext(), Act_Ingredientes.class); 
				startActivity(int_ingr);	
			}
		});
		Button but_platos = (Button)findViewById(R.id.but_platos); 
		but_platos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent int_plato = new Intent(getApplicationContext(), Act_PlatosMenu.class); 
				startActivity(int_plato); 

			}
		});

		Button but_cerrarsesion = (Button)findViewById(R.id.but_cerrarsesion); 
		but_cerrarsesion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				sesion.cerrarSesionUsuario();

			}
		});



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_principal, menu);
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
}