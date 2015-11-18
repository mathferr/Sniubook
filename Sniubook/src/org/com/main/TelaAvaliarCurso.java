package org.com.main;

import java.util.ArrayList;

import org.com.adapter.AdapterCurso;
import org.com.model.Aluno;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class TelaAvaliarCurso extends Activity {

	SQLiteDatabase BancoDados;
	
	TextView tvNomeCursoAvaliacao;
	EditText txtComentarioCurso;
	RatingBar ratingCurso, ratingCursoGeral;
	Button btConfirmarRateCurso, btVoltarAvaliarCurso;
	ListView listComentCurso;
	
	ArrayList<Comentarios> comentarios = new ArrayList<Comentarios>();
	Aluno perfil = TelaMainActivity.perfil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_avaliar_curso);
		
		inicializarComponentes();
		
		comentarios = getListaComentarios();
		
		AdapterCurso adapterCurso = new AdapterCurso(this, comentarios);
		listComentCurso.setAdapter(adapterCurso);
		
		btConfirmarRateCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					if (ratingCurso.getRating() < 0.5) {
						exibirMensagem("Erro", "Você deve avaliar o curso de 1 a 5 estrelas");
					} else {
						BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
						String search = "SELECT nota FROM avaliacao_curso WHERE registro_aluno_fk = " + TelaMainActivity.perfil.getRegistroAcademico();
						Cursor result = BancoDados.rawQuery(search, null);
						if (result.getCount() > 0) {
							reavaliarCurso(ratingCurso.getRating(), perfil.getRegistroAcademico());
						} else {
							search = "SELECT nota FROM avaliacao_curso WHERE registro_aluno_fk = " + perfil.getCpf();
							result = BancoDados.rawQuery(search, null);
							if (result.getCount() > 0) {
								reavaliarCurso(ratingCurso.getRating(), perfil.getCpf());
							} else {
								if (perfil.getRegistroAcademico() == 0) {
									avaliarCurso(ratingCurso.getRating(), perfil.getCpf(), perfil.getCurso());
								} else {
									avaliarCurso(ratingCurso.getRating(), perfil.getRegistroAcademico(), perfil.getCurso());
								}
							}
						}
					}
				} catch (Exception erro) {
					exibirMensagem("Erro", "Erro ao avaliar o curso.\n" + erro.toString());
				} finally {
					BancoDados.close();
				}				
				if (!txtComentarioCurso.getText().toString().isEmpty()) {
					if (perfil.getRegistroAcademico() == 0) {
						enviarComentario(txtComentarioCurso.getText().toString(), perfil.getCpf());
					} else {
						enviarComentario(txtComentarioCurso.getText().toString(), perfil.getRegistroAcademico());
					}
				}
				
				comentarios = getListaComentarios();
				
				AdapterCurso adapterCurso = new AdapterCurso(TelaAvaliarCurso.this, comentarios);
				listComentCurso.setAdapter(adapterCurso);
				inicializarRatings();
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
		txtComentarioCurso = (EditText) findViewById(R.id.txtComentarioCurso);
	}
	
	public void avaliarCurso(float rate, int registroAluno, String curso) {
		String sql = "INSERT INTO avaliacao_curso (registro_aluno_fk, nota, codigo_curso_fk) VALUES "
				+ "(" + registroAluno + ", " + rate + ", '" + curso.toUpperCase() + "')";
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "O curso foi avaliado com sucesso.");
	}
	
	public void avaliarCurso(float rate, String cpfAluno, String curso) {
		String sql = "INSERT INTO avaliacao_curso (registro_aluno_fk, nota, codigo_curso_fk) VALUES "
				+ "(" + cpfAluno + ", " + rate + ", '" + curso.toUpperCase() + "')";
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "O curso foi avaliado com sucesso.");
	}
	
	public void reavaliarCurso(float rate, int registroAluno) {
		String sql = "UPDATE avaliacao_curso SET nota = " + rate + " WHERE registro_aluno_fk = " + registroAluno;
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "Você reavaliou o curso.");
	}
	
	public void reavaliarCurso(float rate, String cpfAluno) {
		String sql = "UPDATE avaliacao_curso SET nota = " + rate + " WHERE registro_aluno_fk = " + cpfAluno;
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "Você reavaliou o curso.");
	}	
	
	public void enviarComentario(String comentario, int registroAluno) {
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);	
			String sql = "INSERT INTO comentarios_curso (registro_aluno_fk, codigo_curso_fk, comentario) VALUES ("
					+ registroAluno + ", '" + TelaMainActivity.perfil.getCurso() + "', '" + comentario + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Comentario enviado com sucesso");
			txtComentarioCurso.setText("");
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro ao enviar o comentário.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}
	
	public void enviarComentario(String comentario, String cpfAluno) {
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);	
			String sql = "INSERT INTO comentarios_curso (registro_aluno_fk, codigo_curso_fk, comentario) VALUES ("
					+ cpfAluno + ", '" + TelaMainActivity.perfil.getCurso() + "', '" + comentario + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Comentario enviado com sucesso");
			txtComentarioCurso.setText("");
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro ao enviar o comentário.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		tvNomeCursoAvaliacao.setText(TelaPrincipal.curso);
		ratingCursoGeral.setEnabled(false);
		inicializarRatings();
	}
	
	public void inicializarRatings() {
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
			
			search = "SELECT nota FROM avaliacao_curso WHERE registro_aluno_fk = " + TelaMainActivity.perfil.getRegistroAcademico() + " "
					+ "OR registro_aluno_fk = " + perfil.getCpf();
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
	
	public ArrayList<Comentarios> getListaComentarios() {
		ArrayList<Comentarios> comentarios = new ArrayList<Comentarios>();
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT DISTINCT a.nome, cc.comentario, ac.nota FROM comentarios_curso cc, aluno a, avaliacao_curso ac "
					+ "WHERE cc.codigo_curso_fk = '" + TelaMainActivity.perfil.getCurso() + "' "
					+ "AND a.registro_academico = cc.registro_aluno_fk "
					+ "AND cc.registro_aluno_fk = ac.registro_aluno_fk "
					+ "ORDER BY cc.codigo DESC LIMIT 5";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				comentarios.add(new Comentarios(cursor.getString(0), cursor.getString(1), cursor.getFloat(2)));
			}
			sql = "SELECT DISTINCT ex.nome, cc.comentario, ac.nota FROM comentarios_curso cc, ex_aluno ex, avaliacao_curso ac "
					+ "WHERE cc.codigo_curso_fk = '" + TelaMainActivity.perfil.getCurso() + "' "
					+ "AND ex.cpf = cc.registro_aluno_fk "
					+ "AND cc.registro_aluno_fk = ac.registro_aluno_fk "
					+ "ORDER BY cc.codigo DESC LIMIT 5";
			cursor = BancoDados.rawQuery(sql, null);
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
