package com.ayalamart.administrador;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class Act_confCrearPlato extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_conf_crear_plato);
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.editTextGroupLayout);
	    EditText editTextView = new EditText(this);
	    editTextView.setGravity(Gravity.LEFT);
	    

	    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
	        LayoutParams.WRAP_CONTENT, 1);

	    editTextView.setLayoutParams(params);

	    linearLayout.addView(editTextView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_conf_crear_plato, menu);
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
