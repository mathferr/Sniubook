package org.com.main;

import org.com.model.Aluno;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaRegistrar extends Activity {
	
	Button btCancelar, btConfirmar;
	EditText tvAlunoRegistro, tvAlunoNome, tvAlunoCpf, tvAlunoEmail;
	Spinner spCurso, spPeriodo, spCampus;
	
	SQLiteDatabase BancoDados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_registro);
		
		inicializarAplicacao();
		
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
						!tvAlunoRegistro.getText().toString().equals("") ||
						!spCurso.getSelectedItem().toString().startsWith("(") ||
						!spPeriodo.getSelectedItem().toString().startsWith("(") ||
						!spCampus.getSelectedItem().toString().startsWith("(")
					) {
					Aluno aluno = new Aluno(
							tvAlunoNome.getText().toString(), 
							tvAlunoCpf.getText().toString(),
							tvAlunoEmail.getText().toString(),
							Integer.parseInt(tvAlunoRegistro.getText().toString()),
							spCurso.getSelectedItem().toString(),
							spPeriodo.getSelectedItem().toString(),
							spCampus.getSelectedItem().toString()
							);
					registrarAluno(aluno);
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
		try{
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);
			String search = "SELECT * FROM aluno WHERE email = '" + aluno.getEmail() + "'";
			Cursor cursor = BancoDados.rawQuery(search, null);
			if (cursor.getCount() > 0) {
				exibirMensagem("Erro", "O endereço de email já está cadastrado no sistema.");
				BancoDados.close();
				return;
			} else {
				search = "SELECT * FROM aluno WHERE _id = " + aluno.getRegistroAcademico();
				cursor = BancoDados.rawQuery(search, null);
				if (cursor.getCount() > 0) {
					exibirMensagem("Erro", "Este Registro Acadêmico já está cadastrado no sistema.");
					BancoDados.close();
					return;
				} else {
					search = "SELECT * FROM aluno WHERE cpf = '" + aluno.getCpf() + "'";
					cursor = BancoDados.rawQuery(search, null);
					if (cursor.getCount() > 0) {
						exibirMensagem("Erro", "Este CPF já está cadastrado no sistema.");
						BancoDados.close();
						return;
					}
				}
			}
			
			String sql = "INSERT INTO aluno (_id, nome, cpf, email, curso, campus, periodo) VALUES "
					+ "(" + aluno.getRegistroAcademico() + ", "
					+ "'" + aluno.getNome() + "', '" + aluno.getCpf() + "', '" + aluno.getEmail() + "', "
					+ "'" + aluno.getCurso() + "', '" + aluno.getCampus() + "', "
					+ "'" + aluno.getPeriodo() + "')";
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Cadastro realizado com sucesso.");
			
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao cadastrar aluno.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
	}
	
	public void inicializarAplicacao() {
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btConfirmar = (Button) findViewById(R.id.btConfirmarRegistro);
		tvAlunoRegistro = (EditText) findViewById(R.id.tvAlunoRegistro);
		tvAlunoNome = (EditText) findViewById(R.id.tvAlunoNome);
		tvAlunoCpf = (EditText) findViewById(R.id.tvAlunoCpf);
		tvAlunoEmail = (EditText) findViewById(R.id.tvAlunoEmail);
		spCurso = (Spinner) findViewById(R.id.spCurso);
		spPeriodo = (Spinner) findViewById(R.id.spPeriodo);
		spCampus = (Spinner) findViewById(R.id.spCampus);
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
