/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.model;

import java.util.ArrayList;

/**
 *
 * @author SAMSUNG
 */
public class Curso {
	private String codigo;
    private String nome;
    private int duracao;
    private String turno;
    
	public Curso(String codigo, String nome, int duracao, String turno) {
		this.codigo = codigo;
		this.nome = nome;
		this.duracao = duracao;
		this.turno = turno;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public int getDuracao() {
		return duracao;
	}

	public String getTurno() {
		return turno;
	}
	
}
