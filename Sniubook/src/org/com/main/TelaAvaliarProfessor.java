package org.com.main;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class TelaAvaliarProfessor extends Activity {
	
	SQLiteDatabase BancoDados;
	
	TextView tvNomeProfessorAvaliacao;
	RatingBar ratingProfessor, ratingProfessorGeral;
	Button btConfirmarRateProfessor, btVoltarAvaliarProfessor;
	ListView listComentProfessor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_avaliar_professor);
		
		inicializarComponentes();
		
//		AdapterProfessor adapterProfessor = new AdapterProfessor(this, comentarios);
//		listComentProfessor.setAdapter(adapterProfessor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_avaliar_professor, menu);
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
		tvNomeProfessorAvaliacao = (TextView) findViewById(R.id.tvNomeDisciplinaAvaliacao);
		ratingProfessor = (RatingBar) findViewById(R.id.ratingDisciplina);
		ratingProfessorGeral = (RatingBar) findViewById(R.id.ratingDisciplinaGeral);
		btConfirmarRateProfessor = (Button) findViewById(R.id.btConfirmarRateDisciplina);
		btVoltarAvaliarProfessor = (Button) findViewById(R.id.btVoltarAvaliarDisciplina);
		listComentProfessor = (ListView) findViewById(R.id.listComentDisciplina);
	}
}
