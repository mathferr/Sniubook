package org.com.main;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TelaProfessoresCurso extends Activity {
	
	SQLiteDatabase BancoDados;
	TextView tvProfNomeCurso;
	ListView listProfessoresCurso;
	Button btVoltarProfessoresCurso;
	
	public static int posicao;
	public static ArrayList<Professor> professores = new ArrayList<Professor>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_professores_curso);
		
		inicializarComponentes();
		
		professores = getListaProfessores();
		ArrayList<String> nomeProf = new ArrayList<String>();
		
		for (Professor p : professores) {
			nomeProf.add(p.getNome());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomeProf);
		listProfessoresCurso.setAdapter(adapter);
		
		listProfessoresCurso.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				posicao = position;
				Intent proximaTela = new Intent(TelaProfessoresCurso.this, TelaAvaliarProfessor.class);
				TelaProfessoresCurso.this.startActivity(proximaTela);
				TelaProfessoresCurso.this.finish();
			}
			
		});
		
		btVoltarProfessoresCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaProfessoresCurso.this, TelaPrincipal.class);
				TelaProfessoresCurso.this.startActivity(telaAnterior);
				TelaProfessoresCurso.this.finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_professores_curso, menu);
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
		tvProfNomeCurso.setText(TelaPrincipal.curso);
	}
	
	public ArrayList<Professor> getListaProfessores() {
		ArrayList<Professor> professores = new ArrayList<Professor>();
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT p.* FROM professor p, curso_professor cp, curso c "
					+ "WHERE cp.registro_profissional_fk = p.registro_profissional AND "
					+ "cp.codigo_curso_fk = c.codigo AND "
					+ "c.codigo = '" + TelaMainActivity.perfil.getCurso() + "'";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() > 0 ) {
				while (cursor.moveToNext()) {
					professores.add(new Professor(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getFloat(4)));
				}
			} else {
				exibirMensagem("Erro", "Lista de professores retornou vazio.");
			}
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao preencher a lista de professores.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
		return professores;
	}
	
	public void inicializarComponentes() {
		tvProfNomeCurso = (TextView) findViewById(R.id.tvProfNomeCurso);
		listProfessoresCurso = (ListView) findViewById(R.id.listProfessoresCurso);
		btVoltarProfessoresCurso = (Button) findViewById(R.id.btVoltarProfessoresCurso);
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
