package org.com.main;

import java.util.ArrayList;

import org.com.model.Comentarios;
import org.com.model.Disciplina;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class TelaAvaliarDisciplina extends Activity {
	
	SQLiteDatabase BancoDados;
	
	TextView tvNomeDisciplinaAvaliacao;
	RatingBar ratingDisciplina, ratingDisciplinaGeral;
	EditText txtComentarioDisciplina;
	Button btConfirmarRateDisciplina, btVoltarAvaliarDisciplina;
	ListView listComentDisciplina;
	
	ArrayList<Disciplina> disciplinas = TelaDisciplinasCurso.disciplinas;
	int posicao = TelaDisciplinasCurso.posicao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_avaliar_disciplina);
		
		inicializarComponentes();
		
		btVoltarAvaliarDisciplina.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaAvaliarDisciplina.this, TelaDisciplinasCurso.class);
				TelaAvaliarDisciplina.this.startActivity(telaAnterior);
				TelaAvaliarDisciplina.this.finish();
			}
		});

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
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		tvNomeDisciplinaAvaliacao.setText(disciplinas.get(posicao).getNome());
		ratingDisciplinaGeral.setRating(disciplinas.get(posicao).getRate());
		try {
			BancoDados= openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String search = "SELECT * FROM avaliacao_disciplina";
			Cursor result = BancoDados.rawQuery(search, null);
			if (result.getCount() > 0) {
				String sql = "SELECT AVG(nota) FROM avaliacao_disciplina WHERE codigo_disciplina_fk = " + disciplinas.get(posicao).getCodigo();
				Cursor cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				ratingDisciplinaGeral.setRating(cursor.getFloat(0));
			}
			
			search = "SELECT nota FROM avaliacao_disciplina WHERE registro_aluno_fk = " + TelaMainActivity.perfil.getRegistroAcademico();
			result = BancoDados.rawQuery(search, null);
			if (result.getCount() > 0) {
				result.moveToFirst();
				ratingDisciplina.setRating(result.getFloat(0));
			}
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao exibir avaliacao geral.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}
	
	public void inicializarComponentes() {
		tvNomeDisciplinaAvaliacao = (TextView) findViewById(R.id.tvNomeDisciplinaAvaliacao);
		ratingDisciplina = (RatingBar) findViewById(R.id.ratingDisciplina);
		ratingDisciplinaGeral = (RatingBar) findViewById(R.id.ratingDisciplinaGeral);
		btConfirmarRateDisciplina = (Button) findViewById(R.id.btConfirmarRateDisciplina);
		btVoltarAvaliarDisciplina = (Button) findViewById(R.id.btVoltarAvaliarDisciplina);
		listComentDisciplina = (ListView) findViewById(R.id.listComentDisciplina);
		txtComentarioDisciplina = (EditText) findViewById(R.id.txtComentarioDisciplina);
		ratingDisciplinaGeral.setEnabled(false);
	}
	
	public ArrayList<Comentarios> getListaComentarios() {
		ArrayList<Comentarios> comentarios = new ArrayList<Comentarios>();
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT a.nome, cd.comentario, ad.nota FROM comentarios_disciplina cd, aluno a, avaliacao_disciplina ad "
					+ "WHERE cd.codigo_disciplina_fk = " + disciplinas.get(posicao).getCodigo()
					+ "AND a.registro_academico = cd.registro_aluno_fk "
					+ "AND cd.registro_aluno_fk = ad.registro_aluno_fk "
					+ "ORDER BY cd.codigo DESC LIMIT 5";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				comentarios.add(new Comentarios(cursor.getString(0), cursor.getString(1), cursor.getFloat(2)));
			}
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao preencher a lista de comentarios.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
		return comentarios;
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
