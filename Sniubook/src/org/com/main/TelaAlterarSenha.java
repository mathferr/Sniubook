package org.com.main;

import org.com.model.Aluno;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TelaAlterarSenha extends Activity {

	SQLiteDatabase BancoDados;
	Button bTConfirmaSenha, bTCancelaSenha;
	EditText eTSenhaAtual, eTNovaSenha, eTConfirmaSenha;
	
	Aluno perfil = TelaMainActivity.perfil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_alterar_senha);
		
		InicializarTelaSenha();
		
		bTCancelaSenha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAnterior = new Intent(TelaAlterarSenha.this, TelaPerfil.class);
				TelaAlterarSenha.this.startActivity(telaAnterior);
				TelaAlterarSenha.this.finish();
				
			}
		});
		
		
		bTConfirmaSenha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String senha;
				senha = perfil.getSenha();
				alterarSenha(senha);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_alterar_senha, menu);
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
	
	public void InicializarTelaSenha(){
		
		bTCancelaSenha = (Button) findViewById(R.id.bTCancelaSenha);
		bTConfirmaSenha = (Button) findViewById(R.id.bTConfirmaSenha);
		eTSenhaAtual = (EditText) findViewById(R.id.eTSenhaAtual);
		eTNovaSenha = (EditText) findViewById(R.id.eTNovaSenha);
		eTConfirmaSenha = (EditText) findViewById(R.id.eTConfirmaSenha);
		
	}
	
	
	public void alterarSenha(String senhaAtual){
		try {
			BancoDados = openOrCreateDatabase("sniubook", MODE_WORLD_WRITEABLE, null);
			String sql;
			if(eTSenhaAtual.getText().toString().equals(senhaAtual)){
				if(eTNovaSenha.getText().toString().equals(eTConfirmaSenha.getText().toString())){
					if (perfil.getRegistroAcademico() != 0) {
						sql = "UPDATE aluno SET senha = '" + eTNovaSenha.getText().toString() + "' WHERE registro_academico = " + perfil.getRegistroAcademico();
					} else {
						sql = "UPDATE ex_aluno SET senha = '" + eTNovaSenha.getText().toString() + "' WHERE cpf = " + perfil.getCpf();
					}
				}else {
					exibirMensagem("Erro", "As senhas não conferem!");
					return;
				}
			}else {
				exibirMensagem("Erro", "Senha atual invalida!");
				return;
			}
			BancoDados.execSQL(sql);
			exibirMensagem("Sucesso", "Senha alterada com sucesso.");

		} catch (Exception erro) {
			exibirMensagem("Erro", "Ocorreu um erro ao alterar a senha.\n" + erro.toString());
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
