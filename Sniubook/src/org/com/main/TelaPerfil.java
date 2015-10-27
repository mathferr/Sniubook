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
import android.widget.TextView;

public class TelaPerfil extends Activity {
	
	SQLiteDatabase BancoDados;
	Button btDeslogar;
	TextView tvRegistroAcademico, tvNome, tvCPF, tvCurso, tvPeriodo, tvCampus, tvTurma;

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
		tvRegistroAcademico = (TextView) findViewById(R.id.tvRegistroAcademico);
		tvNome = (TextView) findViewById(R.id.tvNome);
		tvCPF = (TextView) findViewById(R.id.tvCPF);
		tvCurso = (TextView) findViewById(R.id.tvCurso);
		tvPeriodo = (TextView) findViewById(R.id.tvPeriodo);
		tvCampus = (TextView) findViewById(R.id.tvCampus);
		tvTurma = (TextView) findViewById(R.id.tvTurma);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		String curso, campus;
		Aluno perfil = TelaMainActivity.perfil;
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT c.nome, cp.nome FROM curso c, campus cp WHERE c.codigo = '" + perfil.getCurso()
			+ "' AND cp.codigo = '" + perfil.getCampus() + "'";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				curso = cursor.getString(0);
				campus = cursor.getString(1);
				tvCurso.setText(curso);
				tvCampus.setText(campus);
			} else {
				exibirMensagem("Erro", "Erro ao preencher curso e campus.");
			}
		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro no banco de dados.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
		
		tvRegistroAcademico.setText(perfil.getRegistroAcademico() + "");
		tvNome.setText(perfil.getNome() + "");
	    tvCPF.setText(perfil.getCpf() + "");
		tvPeriodo.setText(perfil.getPeriodo() + "");
		tvTurma.setText(perfil.getTurma());
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
