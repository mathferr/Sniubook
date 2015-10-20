package org.com.main;

import org.com.model.Aluno;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TelaPerfil extends Activity {
	
	SQLiteDatabase BancoDados;
	Button btDeslogar;
	TextView tvRegistroAcademico, tvNome, tvCPF, tvCurso, tvPeriodo, tvCampus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_perfil);
		
		inicializarTelaPerfil();
		
		btDeslogar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaPerfil.this, TelaMainActivity.class);
				TelaPerfil.this.startActivity(telaAnterior);
				TelaPerfil.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_perfil, menu);
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
	
	public void inicializarTelaPerfil() {
		btDeslogar = (Button) findViewById(R.id.btDeslogar);
		tvRegistroAcademico = (TextView) findViewById(R.id.tvRegistroAcademico);
		tvNome = (TextView) findViewById(R.id.tvNome);
		tvCPF = (TextView) findViewById(R.id.tvCPF);
		tvCurso = (TextView) findViewById(R.id.tvCurso);
		tvPeriodo = (TextView) findViewById(R.id.tvPeriodo);
		tvCampus = (TextView) findViewById(R.id.tvCampus);

	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		Aluno perfil = TelaMainActivity.perfil;

		tvRegistroAcademico.setText(perfil.getRegistroAcademico() + "");
		tvNome.setText(perfil.getNome() + "");
	    tvCPF.setText(perfil.getCpf() + "");
		tvCurso.setText(perfil.getCurso() + "");
		tvPeriodo.setText(perfil.getPeriodo() + "");
		tvCampus.setText(perfil.getCampus() + "");
	}
}
