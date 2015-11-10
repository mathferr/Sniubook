package org.com.main;

import java.util.ArrayList;

import org.com.adapter.AdapterListaDisciplinas;
import org.com.model.Disciplina;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TelaDisciplinasCurso extends Activity {
	
	SQLiteDatabase BancoDados;
	TextView tvNomeCursoD;
	ListView listDisciplinas;
	Button btVoltarDisciplinasCurso, btAvaliarDisciplina;
	
	public static int posicao;
	public static ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_disciplinas_curso);
		
		inicializarComponentes();
		
		disciplinas = getListaDisciplinas();
		
		AdapterListaDisciplinas adapterListaDisciplinas = new AdapterListaDisciplinas(this, disciplinas);
		listDisciplinas.setAdapter(adapterListaDisciplinas);
		
		
		
		btAvaliarDisciplina.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (posicao != android.widget.AdapterView.INVALID_POSITION) {
					Intent proximaTela = new Intent(TelaDisciplinasCurso.this, TelaAvaliarDisciplina.class);
					TelaDisciplinasCurso.this.startActivity(proximaTela);
					TelaDisciplinasCurso.this.finish();
				} else {
					exibirMensagem("Erro", "Selecione alguma disciplina para avalia-la.");
				}
			}
		});
		
		btVoltarDisciplinasCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaDisciplinasCurso.this, TelaPrincipal.class);
				TelaDisciplinasCurso.this.startActivity(telaAnterior);
				TelaDisciplinasCurso.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_disciplinas_curso, menu);
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
		tvNomeCursoD.setText(TelaPrincipal.curso);
	}
	
	public ArrayList<Disciplina> getListaDisciplinas() {
		ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
			String sql = "SELECT d.* FROM disciplina d, curso_disciplina cd, curso c "
					+ "WHERE cd.codigo_disciplina_fk = d.codigo AND "
					+ "cd.codigo_curso_fk = c.codigo AND "
					+ "c.codigo = '" + TelaMainActivity.perfil.getCurso() + "'";
			Cursor cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() > 0 ) {
				while (cursor.moveToNext()) {
					disciplinas.add(new Disciplina(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getFloat(3)));
				}
			} else {
				exibirMensagem("Erro", "Lista de disciplinas retornou vazio.");
			}
		} catch (Exception erro) {
			exibirMensagem("Erro", "Erro ao preencher a lista de disciplinas.\n" + erro.toString());
		} finally {
			BancoDados.close();
		}
		return disciplinas;
	}
	
	public void inicializarComponentes() {
		tvNomeCursoD = (TextView) findViewById(R.id.tvNomeCursoD);
		btVoltarDisciplinasCurso = (Button) findViewById(R.id.btVoltarDisicplinasCurso);
		btAvaliarDisciplina = (Button) findViewById(R.id.btAvaliarDisciplina);
		listDisciplinas = (ListView) findViewById(R.id.listDisciplinasCurso);
	}
	
	public void exibirMensagem(String tituloMensagem, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloMensagem);
        builder.setMessage(mensagem);
        builder.setNeutralButton("OK", null);
        builder.show();
    }
}
