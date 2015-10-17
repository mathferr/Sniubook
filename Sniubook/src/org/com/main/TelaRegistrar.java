package org.com.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.Cursor;
import android.widget.Button;

public class TelaRegistrar extends Activity {
	
	Button btCancelar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_registro);
		
		btCancelar = (Button) findViewById(R.id.btCancelar);
		
		btCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaRegistrar.this, TelaMainActivity.class);
				TelaRegistrar.this.startActivity(telaAnterior);
				TelaRegistrar.this.finish();
			}
		});
		
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
