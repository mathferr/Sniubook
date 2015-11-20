package org.com.main;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaPerfil extends Activity {
	
	SQLiteDatabase BancoDados;
	ImageView imageTelaPerfil;
	Button btDeslogar, btPerfilAlterarSenha;
	TextView tvPerfilRegistroAcademico, tvPerfilCPF, tvPerfilTurma;
	EditText txtPerfilNome;
	Spinner spPerfilCurso, spPerfilPeriodo, spPerfilCampus, spPerfilTurno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_perfil);
		
		inicializarTelaPerfil();
		
		btDeslogar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaPerfil.this, TelaPrincipal.class);
				TelaPerfil.this.startActivity(telaAnterior);
				TelaPerfil.this.finish();
			}
			
		});
		
		btPerfilAlterarSenha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		String curso, campus;
		Aluno perfil = TelaMainActivity.perfil;
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
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
