package org.com.main;

import org.com.model.Aluno;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class TelaRegistrar extends Activity {
	
	Button btCancelar, btConfirmar;
	EditText tvAlunoRegistro, tvAlunoNome, tvAlunoCpf, tvAlunoEmail, tvSenha, tvConfSenha;
	Spinner spCurso, spPeriodo, spCampus, spTurno;
	RadioButton rbAluno, rbExAluno;
	
	SQLiteDatabase BancoDados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_registro);
		
		inicializarComponentesGraficos();
		
		btCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaRegistrar.this, TelaMainActivity.class);
				TelaRegistrar.this.startActivity(telaAnterior);
				TelaRegistrar.this.finish();
			}
		});
		
		btConfirmar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!tvAlunoNome.getText().toString().equals("") ||
						!tvAlunoCpf.getText().toString().equals("") ||
						!tvAlunoEmail.getText().toString().equals("") ||
						(!tvAlunoRegistro.getText().toString().equals("") && rbAluno.isChecked()) ||
						!spCurso.getSelectedItem().toString().startsWith("(") ||
						(!spPeriodo.getSelectedItem().toString().startsWith("(") && rbAluno.isChecked()) ||
						!spCampus.getSelectedItem().toString().startsWith("(") ||
						!tvSenha.getText().toString().equals("")
					) {
					
					if (!tvSenha.getText().toString().equals(tvConfSenha.getText().toString())) {
						exibirMensagem("Erro", "A senha e sua confirmação estão diferentes.");
						return;
					}
					
					BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
					String sql = "SELECT curso.codigo FROM curso, campus WHERE curso.nome = "
							+ "'" + spCurso.getSelectedItem().toString().toUpperCase() + "' AND campus.codigo = "
							+ "'" + spCampus.getSelectedItem().toString().substring(0, 2).toUpperCase() + "'";
					Cursor cursor = BancoDados.rawQuery(sql, null);
					if (cursor.getCount() > 0) {
						cursor.moveToFirst();
						
						String turma = cursor.getString(0) + spPeriodo.getSelectedItem().toString() +
								spTurno.getSelectedItem().toString().charAt(0) + "-" +
								spCampus.getSelectedItem().toString().substring(0,2);
						
						Aluno aluno = new Aluno(
								Integer.parseInt(tvAlunoRegistro.getText().toString()),
								tvAlunoNome.getText().toString(), 
								tvAlunoCpf.getText().toString(), 
								tvAlunoEmail.getText().toString(),  
								spCampus.getSelectedItem().toString().substring(0,2),
								turma,
								spPeriodo.getSelectedItem().toString(), 
								cursor.getString(0),
								spTurno.getSelectedItem().toString(),
								tvSenha.getText().toString());
						estadoRadioButtonAluno(rbAluno);
						estadoRadioButtonAluno(rbExAluno);
						if (rbAluno.isChecked()) {
							registrarAluno(aluno);
						} else if (rbExAluno.isChecked()) {
							registrarExAluno(aluno);
						}
						
					} else {
						exibirMensagem("Erro", "Não encontrou o curso");
					}
				} else {
					exibirMensagem("Erro", "Todos os campos devem ser preechidos");
				}
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
	
	public void registrarAluno(Aluno aluno) {
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);
			String search = "SELECT * FROM aluno WHERE registro_academico = " + aluno.getRegistroAcademico();
			Cursor cursor = BancoDados.rawQuery(search, null);
			if (cursor.getCount() > 0) {
				exibirMensagem("Erro", "O Registro Academico já está cadastrado no sistema.");
				BancoDados.close();
				return;
			}			
			String sql = "INSERT INTO aluno (registro_academico, nome, cpf, email, codigo_curso_fk, turma, periodo, campus, turno, senha) VALUES "
					+ "(" + aluno.getRegistroAcademico() + ", "
					+ "'" + aluno.getNome().toUpperCase() + "', " + aluno.getCpf() + ", '" + aluno.getEmail().toLowerCase() + "', "
					+ "'" + aluno.getCurso().toUpperCase() + "', '" + aluno.getTurma().toUpperCase() + "', "
					+ "'" + aluno.getPeriodo().toUpperCase() + "', '" + aluno.getCampus().toUpperCase() + "', "
					+ "'" + aluno.getTurno().toUpperCase() + "', '" + aluno.getSenha() + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Cadastro realizado com sucesso.");

		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao cadastrar aluno.\n" + erro.toString());
		} finally {
			BancoDados.close();
			Intent telaAnterior = new Intent(TelaRegistrar.this, TelaMainActivity.class);
			TelaRegistrar.this.startActivity(telaAnterior);
			TelaRegistrar.this.finish();
		}
	}
	
	public void registrarExAluno(Aluno aluno) {
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);
			String search = "SELECT * FROM ex_aluno WHERE cpf = " + aluno.getCpf();
			Cursor cursor = BancoDados.rawQuery(search, null);
			if (cursor.getCount() > 0) {
				exibirMensagem("Erro", "O CPF já está cadastrado no sistema.");
				BancoDados.close();
				return;
			}	
			String sql = "INSERT INTO ex_aluno (cpf, nome, email, codigo_curso_fk, campus, turno, senha) VALUES "
					+ "(" + aluno.getCpf() + ", "
					+ "'" + aluno.getNome().toUpperCase() + "', '" + aluno.getEmail().toLowerCase() + "', "
					+ "'" + aluno.getCurso().toUpperCase() + "', "
					+ "'" + aluno.getCampus().toUpperCase() + "', "
					+ "'" + aluno.getTurno().toUpperCase() + "', '" + aluno.getSenha() + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Cadastro realizado com sucesso.");
			
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao cadastrar o ex-aluno");
		} finally {
			BancoDados.close();
			Intent telaAnterior = new Intent(TelaRegistrar.this, TelaMainActivity.class);
			TelaRegistrar.this.startActivity(telaAnterior);
			TelaRegistrar.this.finish();
		}
	}
	
	public void inicializarComponentesGraficos() {
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btConfirmar = (Button) findViewById(R.id.btConfirmarRegistro);
		tvAlunoRegistro = (EditText) findViewById(R.id.tvAlunoRegistro);
		tvAlunoNome = (EditText) findViewById(R.id.tvAlunoNome);
		tvAlunoCpf = (EditText) findViewById(R.id.tvAlunoCpf);
		tvAlunoEmail = (EditText) findViewById(R.id.tvAlunoEmail);
		spCurso = (Spinner) findViewById(R.id.spCurso);
		spPeriodo = (Spinner) findViewById(R.id.spPeriodo);
		spCampus = (Spinner) findViewById(R.id.spCampus);
		spTurno = (Spinner) findViewById(R.id.spTurno);
		rbAluno = (RadioButton) findViewById(R.id.rbAluno);
		rbExAluno = (RadioButton) findViewById(R.id.rbExAluno);
		tvSenha = (EditText) findViewById(R.id.tvSenha);
		tvConfSenha = (EditText) findViewById(R.id.tvConfSenha);
	}
	
	public void estadoRadioButtonAluno(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		
		switch (view.getId()) {
		case R.id.rbAluno:
			if (checked) {
				spPeriodo.setEnabled(true);
			}
			break;
		case R.id.rbExAluno:
			if (checked) {
				spPeriodo.setEnabled(false);
			}
			break;
		default:
			exibirMensagem("Erro", "Você deve selecionar Aluno ou Ex-Aluno.\n");
			break;
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
