package org.com.main;

import java.util.ArrayList;

import org.com.adapter.AdapterCurso;
import org.com.model.Comentarios;

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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class TelaAvaliarCurso extends Activity {

	SQLiteDatabase BancoDados;
	
	TextView tvNomeCursoAvaliacao;
	RatingBar ratingCurso, ratingCursoGeral;
	Button btConfirmarRateCurso, btVoltarAvaliarCurso;
	ListView listComentCurso;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_avaliar_curso);
		
		inicializarComponentes();
		ArrayList<Comentarios> comentarios = new ArrayList<Comentarios>();
		for (int i = 0; i < 10; i++) {
			comentarios.add(new Comentarios());
		}
		
		AdapterCurso adapterCurso = new AdapterCurso(this, comentarios);
		listComentCurso.setAdapter(adapterCurso);
		
		btConfirmarRateCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					if (ratingCurso.getRating() < 1) {
						exibirMensagem("Erro", "Você deve avaliar o curso de 1 a 5 estrelas");
					} else {
						BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
						String search = "SELECT nota FROM avaliacao_curso WHERE registro_aluno_fk = " + TelaMainActivity.perfil.getRegistroAcademico();
						Cursor result = BancoDados.rawQuery(search, null);
						if (result.getCount() > 0) {
							reavaliarCurso(ratingCurso.getRating(), TelaMainActivity.perfil.getRegistroAcademico());
						} else {
							avaliarCurso(ratingCurso.getRating(), TelaMainActivity.perfil.getRegistroAcademico(), TelaMainActivity.perfil.getCurso());
						}
					}
				} catch (Exception erro) {
					exibirMensagem("Erro", "Erro ao avaliar o curso.\n" + erro.toString());
				} finally {
					BancoDados.close();
				}
			}
		});
		
		btVoltarAvaliarCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaAvaliarCurso.this, TelaPrincipal.class);
				TelaAvaliarCurso.this.startActivity(telaAnterior);
				TelaAvaliarCurso.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_avaliar_curso, menu);
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
		tvNomeCursoAvaliacao = (TextView) findViewById(R.id.tvNomeCursoAvaliacao);
		ratingCurso = (RatingBar) findViewById(R.id.ratingCurso);
		ratingCursoGeral = (RatingBar) findViewById(R.id.ratingCursoGeral);
		btConfirmarRateCurso = (Button) findViewById(R.id.btConfirmarRateCurso);
		btVoltarAvaliarCurso = (Button) findViewById(R.id.btVoltarAvaliarCurso);
		listComentCurso = (ListView) findViewById(R.id.listComentCurso);
	}
	
	public void avaliarCurso(float rate, int registroAluno, String curso) {
		String sql = "INSERT INTO avaliacao_curso (registro_aluno_fk, nota, codigo_curso_fk) VALUES "
				+ "(" + registroAluno + ", " + rate + ", '" + curso.toUpperCase() + "')";
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "O curso foi avaliado com sucesso.");
	}
	
	public void reavaliarCurso(float rate, int registroAluno) {
		String sql = "UPDATE avaliacao_curso SET nota = " + rate + " WHERE registro_aluno_fk = " + registroAluno;
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "Você reavaliou o curso.");
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		tvNomeCursoAvaliacao.setText(TelaPrincipal.curso);
		ratingCursoGeral.setEnabled(false);
		try {
			BancoDados= openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String search = "SELECT * FROM avaliacao_curso";
			Cursor result = BancoDados.rawQuery(search, null);
			if (result.getCount() > 0) {
				String sql = "SELECT AVG(nota) FROM avaliacao_curso WHERE codigo_curso_fk = '" + TelaMainActivity.perfil.getCurso() + "'";
				Cursor cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				ratingCursoGeral.setRating(cursor.getFloat(0));
			}
			
			search = "SELECT nota FROM avaliacao_curso WHERE registro_aluno_fk = " + TelaMainActivity.perfil.getRegistroAcademico();
			result = BancoDados.rawQuery(search, null);
			if (result.getCount() > 0) {
				result.moveToFirst();
				ratingCurso.setRating(result.getFloat(0));
			}
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao exibir avaliacao geral.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
