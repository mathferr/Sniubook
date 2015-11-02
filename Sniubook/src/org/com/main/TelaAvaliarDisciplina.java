package org.com.main;

import java.util.ArrayList;

import org.com.adapter.AdapterDisciplina;
import org.com.model.Comentarios;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class TelaAvaliarDisciplina extends Activity {
	
	SQLiteDatabase BancoDados;
	
	TextView tvNomeDisciplinaAvaliacao;
	RatingBar ratingDisciplina, ratingDisciplinaGeral;
	Button btConfirmarRateDisciplina, btVoltarAvaliarDisciplina;
	ListView listComentDisciplina;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_avaliar_disciplina);
		
		inicializarComponentes();
		
//		AdapterDisciplina adapterDisciplina = new AdapterDisciplina(this, comentarios);
//		listComentDisciplina.setAdapter(adapterDisciplina);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_avaliar_disciplina, menu);
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
	
	public void inicializarComponentes() {
		tvNomeDisciplinaAvaliacao = (TextView) findViewById(R.id.tvNomeDisciplinaAvaliacao);
		ratingDisciplina = (RatingBar) findViewById(R.id.ratingDisciplina);
		ratingDisciplinaGeral = (RatingBar) findViewById(R.id.ratingDisciplinaGeral);
		btConfirmarRateDisciplina = (Button) findViewById(R.id.btConfirmarRateDisciplina);
		btVoltarAvaliarDisciplina = (Button) findViewById(R.id.btVoltarAvaliarDisciplina);
		listComentDisciplina = (ListView) findViewById(R.id.listComentDisciplina);
	}
}
