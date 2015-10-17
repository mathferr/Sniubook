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
import android.widget.ImageView;

public class TelaMainActivity extends Activity {
	
	Button btRegistrar;
	String APP_ID = getString(R.string.APP_ID);
	
	//ImageView img;
	
	//Ctrl-Shift-O
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_main);
		
		btRegistrar = (Button) findViewById(R.id.btRegistrar);
		
		btRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent proximaTela = new Intent(TelaMainActivity.this, TelaRegistrar.class);
				TelaMainActivity.this.startActivity(proximaTela);
				TelaMainActivity.this.finish();
			}
		});
		
		//img = (ImageView) findViewById(R.id.prof_pick);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_main, menu);
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
