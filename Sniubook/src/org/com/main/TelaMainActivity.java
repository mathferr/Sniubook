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

public class TelaMainActivity extends Activity {
	
	public static Aluno perfil;
	SQLiteDatabase BancoDados;
	Button btRegistrar, btLogin;
	EditText tvUsuarioEmail;
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
		tvUsuarioEmail = (EditText) findViewById(R.id.tvUsuarioEmail);
		
		btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fazerLogin(tvUsuarioEmail.getText().toString());
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
            BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
//            String sql = "DROP TABLE aluno";
            String sql = "CREATE TABLE IF NOT EXISTS aluno (_id INTEGER PRIMARY KEY, nome TEXT, cpf TEXT, email TEXT, "
            		+ "curso TEXT, "
            		+ "campus TEXT, periodo TEXT)";
            BancoDados.execSQL(sql);
//            exibirMensagem("Sucesso", "Não esqueçe daquele LIKE no app");
        } catch (Exception erro) {
            exibirMensagem("Erro", "Erro ao criar a tabela curso!\n" + erro);
        } finally {
            BancoDados.close();
        }
    }
    
    public void fazerLogin(String email) {
    	try{
	    	BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_READABLE, null);
	    	String sql = "SELECT * FROM aluno WHERE email LIKE '" + email + "'";
//	    	  String[] colunas = new String[]{"_id", "nome", "cpf", "email", "curso", "campus", "periodo"};
	    	Cursor cursor = BancoDados.rawQuery(sql, null);
	    	if (cursor.getCount() > 0) {
	    		cursor.moveToNext();
	    		perfil = new Aluno(cursor.getString(1), cursor.getString(2), email, cursor.getInt(0),
	    				cursor.getString(4), cursor.getString(5), cursor.getString(6));
	    		BancoDados.close();
	    		Intent proximaTela = new Intent(TelaMainActivity.this, TelaPerfil.class);
	    		TelaMainActivity.this.startActivity(proximaTela);
	    		TelaMainActivity.this.finish();
	    	} else {
	    		exibirMensagem("Usuario não existe", "O endereço de e-mail não está cadastrado.");
	    	}
    	} catch (Exception erro) {
    		exibirMensagem("Erro", "Erro ao realizar login\n" + erro.toString());
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
