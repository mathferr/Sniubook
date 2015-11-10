package org.com.main;

import org.com.model.Aluno;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
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

public class TelaMainActivity extends Activity {
	
	public static Aluno perfil;
	SQLiteDatabase BancoDados;
	Button btRegistrar, btLogin;
	EditText tvUsuarioLogin, tvUsuarioSenha;
	//String APP_ID = getString(R.string.facebook_app_id);
	
	//ImageView img;
	
	//Ctrl-Shift-O
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_main);
		
		criarBanco();

		btLogin = (Button) findViewById(R.id.btLogin);
		btRegistrar = (Button) findViewById(R.id.btRegistrar);
		tvUsuarioLogin = (EditText) findViewById(R.id.tvUsuarioLogin);
		tvUsuarioSenha = (EditText) findViewById(R.id.tvUsuarioSenha);
		
		btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fazerLogin(tvUsuarioLogin.getText().toString(), tvUsuarioSenha.getText().toString());
			}
		});
		
		btRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent proximaTela = new Intent(TelaMainActivity.this, TelaRegistrar.class);
				TelaMainActivity.this.startActivity(proximaTela);
				TelaMainActivity.this.finish();
			}
		});
		
		//img = (ImageView) findViewById(R.id.prof_pick);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_main, menu);
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

    public void criarBanco() {
        try {
//       	deleteDatabase("sniubook");
       	
            BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
            
//          String sql = "DROP TABLE curso";
            String sql = "CREATE TABLE IF NOT EXISTS curso (codigo TEXT PRIMARY KEY, nome TEXT, duracao INTEGER)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE campus";
            sql = "CREATE TABLE IF NOT EXISTS campus (codigo TEXT PRIMARY KEY, nome TEXT, lista_curso TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE aluno";
            sql = "CREATE TABLE IF NOT EXISTS aluno (registro_academico INTEGER PRIMARY KEY, nome TEXT, cpf INT8, "
            		+ "email TEXT, campus TEXT, "
            		+ "turma TEXT, periodo TEXT, codigo_curso_fk TEXT, turno TEXT, senha TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE ex_aluno";
            sql = "CREATE TABLE IF NOT EXISTS ex_aluno (cpf INT8 PRIMARY KEY, nome TEXT, "
            		+ "email TEXT, campus TEXT, "
            		+ "codigo_curso_fk TEXT, turno TEXT, senha TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE disciplina";
            sql = "CREATE TABLE IF NOT EXISTS disciplina (codigo INTEGER PRIMARY KEY, nome TEXT, carga_horaria INTEGER"
            		+ ", rate FLOAT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE professor";
            sql = "CREATE TABLE IF NOT EXISTS professor (registro_profissional INTEGER PRIMARY KEY, nome TEXT, cpf INT8, "
            		+ "email TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE curso_disciplina";
            sql = "CREATE TABLE IF NOT EXISTS curso_disciplina (codigo_curso_fk TEXT, "
            		+ "codigo_disciplina_fk INTEGER)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE professor_disciplina";
            sql = "CREATE TABLE IF NOT EXISTS professor_disciplina (registro_profissional_fk INTEGER, "
            		+ "codigo_disciplina_fk INTEGER)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE avaliacao_curso";
            sql = "CREATE TABLE IF NOT EXISTS avaliacao_curso (registro_aluno_fk INTEGER PRIMARY KEY, nota FLOAT, codigo_curso_fk TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE avaliacao_disciplina";
            sql = "CREATE TABLE IF NOT EXISTS avaliacao_disciplina (registro_aluno_fk INTEGER PRIMARY KEY, nota FLOAT, codigo_disciplina_fk TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE avaliacao_professor";
            sql = "CREATE TABLE IF NOT EXISTS avaliacao_professor (registro_aluno_fk INTEGER PRIMARY KEY, nota FLOAT, codigo_professor_fk TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE comentarios_curso";
            sql = "CREATE TABLE IF NOT EXISTS comentarios_curso (codigo INTEGER PRIMARY KEY, registro_aluno_fk INTEGER, "
            		+ "codigo_curso_fk TEXT, comentario TEXT)";
            BancoDados.execSQL(sql);
            
//          String sql = "DROP TABLE comentarios_disciplina";
            sql = "CREATE TABLE IF NOT EXISTS comentarios_disciplina (codigo INTEGER PRIMARY KEY, registro_aluno_fk INTEGER, "
            		+ "codigo_disciplina_fk TEXT, comentario TEXT)";
            
//          String sql = "DROP TABLE comentarios_professor";
//          sql = "CREATE TABLE IF NOT EXISTS comentarios_professor (codigo INTEGER PRIMARY KEY, registro_aluno_fk INTEGER, "
//          		+ "registro_profissional_fk TEXT, comentario TEXT)";
/*            
            sql = "INSERT INTO curso(codigo, nome, duracao) VALUES ('CIC', 'CIENCIA DA COMPUTACAO', 4)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO curso(codigo, nome, duracao) VALUES ('SIS', 'SISTEMAS DE INFORMACAO', 4)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO campus(codigo, nome, lista_curso) VALUES ('ES', 'ESTORIL', 'CIC-SIS')";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO campus(codigo, nome, lista_curso) VALUES ('CM', 'CRISTIANO MACHADO', 'CIC')";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO campus(codigo, nome, lista_curso) VALUES ('AC', 'ANTONIO CARLOS', 'SIS')";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO disciplina(codigo, nome, carga_horaria, rate) VALUES (1, 'ANALISE E PROJETO ORIENTADO A OBJETO', 120, 0)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO disciplina(codigo, nome, carga_horaria, rate) VALUES (2, 'TEORIA DOS GRAFOS', 140, 0)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO disciplina(codigo, nome, carga_horaria, rate) VALUES (3, 'TRABALHO INTERDISCIPLINAR DE GRADUACAO VI', 60, 0)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO curso_disciplina(codigo_curso_fk, codigo_disciplina_fk) VALUES ('CIC', 1)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO curso_disciplina(codigo_curso_fk, codigo_disciplina_fk) VALUES ('CIC', 2)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO curso_disciplina(codigo_curso_fk, codigo_disciplina_fk) VALUES ('CIC', 3)";
            BancoDados.execSQL(sql);
            
            sql = "INSERT INTO curso_disciplina(codigo_curso_fk, codigo_disciplina_fk) VALUES ('SIS', 3)";
            BancoDados.execSQL(sql);//*/
            
        } catch (Exception erro) {
            exibirMensagem("Erro", "Ocorreu um erro na ao iniciar o Banco de Dados\n" + erro.toString());
        } finally {
            BancoDados.close();
        }
    }
    
    public void fazerLogin(String login, String senha) {
    	try {
    		BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
    		String sql = "SELECT * FROM aluno WHERE registro_academico = " + login + " AND senha = '" + senha + "'";
    		Cursor cursor = BancoDados.rawQuery(sql, null);
    		if (cursor.getCount() > 0) {
    			cursor.moveToFirst();
    			Intent proximaTela = new Intent(TelaMainActivity.this, TelaPrincipal.class);
    			BancoDados.close();
    			perfil = new Aluno(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
    					cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
    			TelaMainActivity.this.startActivity(proximaTela);
    			TelaMainActivity.this.finish();
    		} else {
    			sql = "SELECT * FROM ex_aluno WHERE cpf = " + login + " AND senha = '" + senha + "'";
    			cursor = BancoDados.rawQuery(sql, null);
    			if (cursor.getCount() > 0) {
    				cursor.moveToFirst();
    				Intent proximaTela = new Intent(TelaMainActivity.this, TelaPrincipal.class);
        			BancoDados.close();
        			perfil = new Aluno(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
        					cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        			TelaMainActivity.this.startActivity(proximaTela);
        			TelaMainActivity.this.finish();
    			} else {
    				exibirMensagem("Erro", "Login ou senha incorretos!\nSe você é novo aqui registre-se para poder logar.");
    			}
    		}
    	} catch (Exception erro) {
    		exibirMensagem("Erro", "Erro ao realizar login.\n" + erro.toString());
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
