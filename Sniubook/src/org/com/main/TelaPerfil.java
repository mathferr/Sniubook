package org.com.main;

import java.util.ArrayList;

import org.com.model.Aluno;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaPerfil extends Activity {
	
	SQLiteDatabase BancoDados;
	ImageView imageTelaPerfil;
	Button btDeslogar, btPerfilAlterarSenha, btAlterarPerfil;
	TextView tvPerfilRegistroAcademico, tvPerfilCPF, tvPerfilTurma;
	EditText txtPerfilNome, txtPerfilEmail;
	Spinner spPerfilCurso, spPerfilPeriodo, spPerfilCampus, spPerfilTurno;
	
	Aluno perfil = TelaMainActivity.perfil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_perfil);
		
		inicializarTelaPerfil();
		
		spPerfilCurso.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String curso = spPerfilCurso.getSelectedItem().toString().substring(0, 3);
				try {
					BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
					String sql = "SELECT duracao FROM curso WHERE codigo = '" + curso + "'";
					Cursor cursor = BancoDados.rawQuery(sql, null);
					if (cursor.getCount() > 0) {
						cursor.moveToFirst();
						ArrayList<String> listaPeriodos = new ArrayList<String>();
						for (int i = 1; i <= cursor.getInt(0); i++) {
							listaPeriodos.add(i+"A");
							listaPeriodos.add(i+"B");
						}

						ArrayAdapter<String> periodos = new ArrayAdapter<String>(TelaPerfil.this, android.R.layout.simple_spinner_item, listaPeriodos);
						spPerfilPeriodo.setAdapter(periodos);
						
						if (perfil.getPeriodo() != null) {
							spPerfilPeriodo.setEnabled(true);
							for (int i = 0; i < spPerfilPeriodo.getCount(); i++) {
								if (spPerfilPeriodo.getItemAtPosition(i).toString().contains(perfil.getPeriodo())) {
									spPerfilPeriodo.setSelection(i);
								}
							}
						} else {
							spPerfilPeriodo.setEnabled(false);
						}
						
					}
				} catch (Exception erro) {
					exibirMensagem("Erro", "Ocorreu um erro ao preencher os periodos.\n" + erro.toString());
				} finally {
					BancoDados.close();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btDeslogar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaPerfil.this, TelaPrincipal.class);
				TelaPerfil.this.startActivity(telaAnterior);
				TelaPerfil.this.finish();
			}
			
		});
		
		btAlterarPerfil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Aluno aluno = new Aluno(Integer.parseInt(tvPerfilRegistroAcademico.getText().toString()),
										txtPerfilNome.getText().toString(),
										tvPerfilCPF.getText().toString(),
										txtPerfilEmail.getText().toString(),
										spPerfilCampus.getSelectedItem().toString().substring(0, 2),
										null,
										spPerfilPeriodo.getSelectedItem().toString(),
										spPerfilCurso.getSelectedItem().toString().substring(0, 3),
										(spPerfilTurno.getSelectedItem().toString().charAt(0) + "").toUpperCase(),
										null);
				alterarPerfil(aluno);
				Intent proximaTela = new Intent(TelaPerfil.this, TelaPrincipal.class);
				TelaPerfil.this.startActivity(proximaTela);
				TelaPerfil.this.finish();
			}
			
		});
		
		btPerfilAlterarSenha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent proximaTela = new Intent(TelaPerfil.this, TelaAlterarSenha.class);
				TelaPerfil.this.startActivity(proximaTela);
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
		tvPerfilRegistroAcademico = (TextView) findViewById(R.id.tvPerfilRegistroAcademico);
		txtPerfilNome = (EditText) findViewById(R.id.txtPerfilNome);
		tvPerfilCPF = (TextView) findViewById(R.id.tvPerfilCPF);
		tvPerfilTurma = (TextView) findViewById(R.id.tvPerfilTurma);
		spPerfilCurso = (Spinner) findViewById(R.id.spPerfilCurso);
		spPerfilPeriodo = (Spinner) findViewById(R.id.spPerfilPeriodo);
		spPerfilCampus = (Spinner) findViewById(R.id.spPerfilCampus);
		spPerfilTurno = (Spinner) findViewById(R.id.spPerfilTurno);
		imageTelaPerfil = (ImageView) findViewById(R.id.imageTelaPerfil);
		btPerfilAlterarSenha = (Button) findViewById(R.id.btPerfilAlterarSenha);
		btAlterarPerfil = (Button) findViewById(R.id.btAlterarPerfil);
		txtPerfilEmail = (EditText) findViewById(R.id.txtPerfilEmail);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		String curso, campus;
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT c.codigo, cp.codigo FROM curso c, campus cp WHERE c.codigo = '" + perfil.getCurso()
			+ "' AND cp.codigo = '" + perfil.getCampus() + "'";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				curso = cursor.getString(0);
				campus = cursor.getString(1);
				for (int i = 0; i < spPerfilCurso.getCount(); i++) {
					if (spPerfilCurso.getItemAtPosition(i).toString().startsWith(curso)) {
						spPerfilCurso.setSelection(i);
					}
				}
				for (int i = 0; i < spPerfilCampus.getCount(); i++) {
					if (spPerfilCampus.getItemAtPosition(i).toString().startsWith(campus)) {
						spPerfilCampus.setSelection(i);
					}
				}
			} else {
				exibirMensagem("Erro", "Erro ao preencher curso e campus.");
			}
			
			sql = "SELECT imagem FROM ";
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro no banco de dados.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
		
		tvPerfilRegistroAcademico.setText(perfil.getRegistroAcademico() + "");
		if (perfil.getRegistroAcademico() == 0) tvPerfilRegistroAcademico.setText("(Ex-Aluno)");
		txtPerfilNome.setText(perfil.getNome() + "");
		tvPerfilCPF.setText(perfil.getCpf() + "");
		if (perfil.getPeriodo() != null) {
			spPerfilPeriodo.setEnabled(true);
			for (int i = 0; i < spPerfilPeriodo.getCount(); i++) {
				if (spPerfilPeriodo.getItemAtPosition(i).toString().contains(perfil.getPeriodo())) {
					spPerfilPeriodo.setSelection(i);
				}
			}
		} else {
			spPerfilPeriodo.setEnabled(false);
		}
		for (int i = 0; i < spPerfilTurno.getCount(); i++) {
			if (spPerfilTurno.getItemAtPosition(i).toString().toLowerCase().startsWith(perfil.getTurno().toLowerCase())) {
				spPerfilTurno.setSelection(i);
			}
		}
		tvPerfilTurma.setText(perfil.getTurma());
		if (perfil.getTurma() == null) tvPerfilTurma.setText("(Ex-Aluno)");
		txtPerfilEmail.setText(perfil.getEmail());
	}
	
	public void alterarPerfil(Aluno aluno) {
		String turma = aluno.getCurso() + aluno.getPeriodo() + aluno.getTurno() + "-" + aluno.getCampus();
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);
			String sql;
			if (aluno.getRegistroAcademico() != 0) {
				sql = "UPDATE aluno SET nome = '" + aluno.getNome().toUpperCase() + "', "
						+ "cpf = " + aluno.getCpf() + ", "
						+ "email = '" + aluno.getEmail().toLowerCase() + "', "
						+ "campus = '" + aluno.getCampus() + "', "
						+ "turma = '" + turma.toUpperCase() + "', "
						+ "periodo = '" + aluno.getPeriodo() + "', "
						+ "turno = '" + aluno.getTurno() + "'"
						+ "WHERE registro_academico = " + aluno.getRegistroAcademico();
			} else {
				sql = "UPDATE ex_aluno SET nome = '" + aluno.getNome().toUpperCase() + "', "
						+ "email = '" + aluno.getEmail() + "', "
						+ "campus = '" + aluno.getCampus() + "', "
						+ "turno = '" + aluno.getTurno() + "'"
						+ "WHERE cpf = " + aluno.getCpf();
			}
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Perfil alterado com sucesso.");
			TelaMainActivity.perfil.setNome(aluno.getNome().toUpperCase());
			TelaMainActivity.perfil.setEmail(aluno.getEmail().toLowerCase());
			TelaMainActivity.perfil.setCurso(aluno.getCurso());
			TelaMainActivity.perfil.setCampus(aluno.getCampus());
			TelaMainActivity.perfil.setPeriodo(aluno.getPeriodo());
			TelaMainActivity.perfil.setTurno(aluno.getTurno());
			TelaMainActivity.perfil.setTurma(turma.toUpperCase());
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro ao alterar o perfil.\n" + erro.toString());
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
