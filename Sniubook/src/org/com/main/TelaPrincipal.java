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

public class TelaPrincipal extends Activity {
	
	SQLiteDatabase BancoDados;
	
	TextView tvPrincipalAluno, tvPrincipalCurso, tvPrincipalCampus;
	Button btTelaPerfil, btAvaliarCurso, btLogout;

	public static String curso;
	
	Aluno perfil = TelaMainActivity.perfil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_principal);
		
		inicializarComponentes();
		
		btTelaPerfil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent proximaTela = new Intent(TelaPrincipal.this, TelaPerfil.class);
				TelaPrincipal.this.startActivity(proximaTela);
				TelaPrincipal.this.finish();
			}
		});
		
		btAvaliarCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent proximaTela = new Intent(TelaPrincipal.this, TelaAvaliarCurso.class);
				TelaPrincipal.this.startActivity(proximaTela);
				TelaPrincipal.this.finish();
			}
		});
		
		btLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent deslogar = new Intent(TelaPrincipal.this, TelaMainActivity.class);
				TelaPrincipal.this.startActivity(deslogar);
				TelaPrincipal.this.finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_principal, menu);
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
		String campus;
		try{
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT c.nome, cp.nome FROM curso c, campus cp WHERE c.codigo = '" + perfil.getCurso()
				+ "' AND cp.codigo = '" + perfil.getCampus() + "'";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				curso = cursor.getString(0);
				campus = cursor.getString(1);
				tvPrincipalCurso.setText(curso);
				tvPrincipalCampus.setText(campus);
			} else {
				exibirMensagem("Erro", "Erro ao preencher curso e campus.");
			}
		
			tvPrincipalAluno.setText(perfil.getNome());
			
		} catch (Exception erro) {
			
		}
	}
	
	public void inicializarComponentes() {
		tvPrincipalAluno = (TextView) findViewById(R.id.tvNomeCursoAvaliacao);
		tvPrincipalCurso = (TextView) findViewById(R.id.tvPrincipalCurso);
		tvPrincipalCampus = (TextView) findViewById(R.id.tvPrincipalCampus);
		btAvaliarCurso = (Button) findViewById(R.id.btAvaliarCurso);
		btTelaPerfil = (Button) findViewById(R.id.btTelaPerfil);
		btLogout = (Button) findViewById(R.id.btLogout);
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
