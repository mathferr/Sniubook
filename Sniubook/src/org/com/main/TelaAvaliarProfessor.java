package org.com.main;

import java.util.ArrayList;

import org.com.adapter.AdapterDisciplina;
import org.com.adapter.AdapterProfessor;
import org.com.model.Aluno;
import org.com.model.Comentarios;
import org.com.model.Professor;

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

public class TelaAvaliarProfessor extends Activity {
	
	SQLiteDatabase BancoDados;
	
	TextView tvNomeProfessorAvaliacao;
	EditText txtComentarioProfessor;
	RatingBar ratingProfessor, ratingProfessorGeral;
	Button btConfirmarRateProfessor, btVoltarAvaliarProfessor;
	ListView listComentProfessor;
	
	int posicao = TelaProfessoresCurso.posicao;
	ArrayList<Professor> professores = TelaProfessoresCurso.professores;
	ArrayList<Comentarios> comentarios = new ArrayList<Comentarios>();
	Aluno perfil = TelaMainActivity.perfil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_avaliar_professor);
		
		inicializarComponentes();
		
		comentarios = getListaComentarios();
		
		AdapterProfessor adapterProfessor = new AdapterProfessor(this, comentarios);
		listComentProfessor.setAdapter(adapterProfessor);
		
		btVoltarAvaliarProfessor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaAvaliarProfessor.this, TelaProfessoresCurso.class);
				TelaAvaliarProfessor.this.startActivity(telaAnterior);
				TelaAvaliarProfessor.this.finish();
			}
			
		});
		
		btConfirmarRateProfessor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					if (ratingProfessor.getRating() < 0.5) {
						exibirMensagem("Erro", "Você deve avaliar o professor de 1 a 5 estrelas");
					} else {
						BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
						String search = "SELECT nota FROM avaliacao_professor WHERE registro_aluno_fk = " + perfil.getRegistroAcademico() + " "
								+ "AND registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
						Cursor result = BancoDados.rawQuery(search, null);
						if (result.getCount() > 0) {
							reavaliarProfessor(ratingProfessor.getRating(), TelaMainActivity.perfil.getRegistroAcademico());
						} else {
							search = "SELECT nota FROM avaliacao_professor WHERE registro_aluno_fk = " + perfil.getCpf() + " "
									+ "AND registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
							result = BancoDados.rawQuery(search, null);
							if (result.getCount() > 0) {
								reavaliarProfessor(ratingProfessor.getRating(), perfil.getCpf());
							} else {
								if (perfil.getRegistroAcademico() == 0) {
									avaliarProfessor(ratingProfessor.getRating(), perfil.getCpf(), professores.get(posicao).getRegistroProfissional());
								} else {
									avaliarProfessor(ratingProfessor.getRating(), perfil.getRegistroAcademico(), professores.get(posicao).getRegistroProfissional());
								}
							}
						}
						inicializarRatings();
					}
				} catch (Exception erro) {
					exibirMensagem("Erro", "Erro ao avaliar o professor.\n" + erro.toString());
				} finally {
					BancoDados.close();
				}				
				
				if (!txtComentarioProfessor.getText().toString().isEmpty()) {
					if (perfil.getRegistroAcademico() == 0) {
						enviarComentario(txtComentarioProfessor.getText().toString(), perfil.getCpf());
					} else {
						enviarComentario(txtComentarioProfessor.getText().toString(), perfil.getRegistroAcademico());
					}
				}
				
				comentarios = getListaComentarios();
				
				AdapterProfessor adapterProfessor = new AdapterProfessor(TelaAvaliarProfessor.this, comentarios);
				listComentProfessor.setAdapter(adapterProfessor);				
			}
			
		});
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
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		tvNomeProfessorAvaliacao.setText(professores.get(posicao).getNome());
		ratingProfessorGeral.setRating(professores.get(posicao).getRate());
		inicializarRatings();
	}
	
	public void inicializarRatings() {
		try {
			BancoDados= openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String search = "SELECT * FROM avaliacao_professor WHERE registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
			Cursor result = BancoDados.rawQuery(search, null);
			if (result.getCount() > 0) {
				String sql = "SELECT AVG(nota) FROM avaliacao_professor WHERE registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
				Cursor cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				ratingProfessorGeral.setRating(cursor.getFloat(0));
			}
			
			search = "SELECT nota FROM avaliacao_professor WHERE registro_aluno_fk = " + TelaMainActivity.perfil.getRegistroAcademico() + " AND "
					+ "registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
			result = BancoDados.rawQuery(search, null);
			if (result.getCount() > 0) {
				result.moveToFirst();
				ratingProfessor.setRating(result.getFloat(0));
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
			String sql = "SELECT DISTINCT a.nome, cp.comentario, ap.nota FROM comentarios_professor cp, aluno a, avaliacao_professor ap "
					+ "WHERE cp.registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional() + " "
					+ "AND a.registro_academico = cp.registro_aluno_fk "
					+ "AND cp.registro_aluno_fk = ap.registro_aluno_fk "
					+ "ORDER BY cp.codigo DESC LIMIT 5";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				comentarios.add(new Comentarios(cursor.getString(0), cursor.getString(1), cursor.getFloat(2)));
			}
			sql = "SELECT DISTINCT ex.nome, cp.comentario, ap.nota FROM comentarios_professor cp, ex_aluno ex, avaliacao_professor ap "
					+ "WHERE cp.registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional() + " "
					+ "AND ex.cpf = cp.registro_aluno_fk "
					+ "AND cp.registro_aluno_fk = ap.registro_aluno_fk "
					+ "ORDER BY cp.codigo DESC LIMIT 5";
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
	
	public void avaliarProfessor(float rate, int registroAluno, int professor) {
		String sql = "INSERT INTO avaliacao_professor (registro_aluno_fk, nota, registro_profissional_fk) VALUES "
				+ "(" + registroAluno + ", " + rate + ", " + professor + ")";
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "O professor foi avaliado com sucesso.");
	}
	
	public void avaliarProfessor(float rate, String cpfAluno, int professor) {
		String sql = "INSERT INTO avaliacao_professor (registro_aluno_fk, nota, registro_profissional_fk) VALUES "
				+ "(" + cpfAluno + ", " + rate + ", " + professor + ")";
		BancoDados.execSQL(sql);
		exibirMensagem("Sucesso", "O professor foi avaliado com sucesso.");
	}
	
	public void reavaliarProfessor(float rate, int registroAluno) {
		String sql = "UPDATE avaliacao_professor SET nota = " + rate + " WHERE registro_aluno_fk = " + registroAluno + " "
				+ "AND registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
		BancoDados.execSQL(sql);
	}
	
	public void reavaliarProfessor(float rate, String cpfAluno) {
		String sql = "UPDATE avaliacao_professor SET nota = " + rate + " WHERE registro_aluno_fk = " + cpfAluno + " "
				+ "AND registro_profissional_fk = " + professores.get(posicao).getRegistroProfissional();
		BancoDados.execSQL(sql);
	}
	
	public void enviarComentario(String comentario, int registroAluno) {
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);	
			String sql = "INSERT INTO comentarios_professor (registro_aluno_fk, registro_profissional_fk, comentario) VALUES ("
					+ registroAluno + ", " + professores.get(posicao).getRegistroProfissional() + ", '" + comentario + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Comentario enviado com sucesso");
			txtComentarioProfessor.setText("");
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro ao enviar o comentário.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}

	public void enviarComentario(String comentario, String cpfAluno) {
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);	
			String sql = "INSERT INTO comentarios_professor (registro_aluno_fk, registro_profissional_fk, comentario) VALUES ("
					+ cpfAluno + ", " + professores.get(posicao).getRegistroProfissional() + ", '" + comentario + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Comentario enviado com sucesso");
			txtComentarioProfessor.setText("");
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro ao enviar o comentário.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}
	
	public void inicializarComponentes() {
		tvNomeProfessorAvaliacao = (TextView) findViewById(R.id.tvNomeProfessorAvaliacao);
		ratingProfessor = (RatingBar) findViewById(R.id.ratingProfessor);
		ratingProfessorGeral = (RatingBar) findViewById(R.id.ratingProfessorGeral);
		btConfirmarRateProfessor = (Button) findViewById(R.id.btConfirmarRateProfessor);
		btVoltarAvaliarProfessor = (Button) findViewById(R.id.btVoltarAvaliarProfessor);
		listComentProfessor = (ListView) findViewById(R.id.listComentProfessor);
		txtComentarioProfessor = (EditText) findViewById(R.id.txtComentarioProfessor);
		ratingProfessorGeral.setEnabled(false);
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
